import com.michal.Application;
import com.michal.entities.User;
import com.michal.impl.UserServiceImpl;
import com.michal.util.Cart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.hamcrest.Matchers.isA;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles(profiles = "test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@AutoConfigureMockMvc
@Transactional
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserServiceImpl userService;

    @Test
    void loginPageTest() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @ParameterizedTest
    @MethodSource("Provider#getValidUsers")
    void loginFailureTest(User user) throws Exception {
        User u = userService.findByLogin(user.getLogin());
        if(u != null){
            userService.delete(u);
        }
        mockMvc.perform(post("/login")
                .param("username", user.getLogin())
                .param("password", user.getPassword()))
                .andExpect(unauthenticated())
                .andExpect(redirectedUrl("/login?error=true"));
    }

    @ParameterizedTest
    @MethodSource("Provider#getValidUsers")
    void loginFailureWithBadCredentialsTest(User u) throws Exception {
        String password = u.getPassword().concat("test");
        User user = userService.findByLogin(u.getLogin());
        if(user == null){
            userService.createUser(u);
        }
        mockMvc.perform(post("/login")
                .param("username", u.getLogin())
                .param("password", password))
                .andExpect(unauthenticated())
                .andExpect(redirectedUrl("/login?error=true"));
    }

    @ParameterizedTest
    @MethodSource("Provider#getValidUsers")
    void loginSuccessTest(User u) throws Exception {
        String password = u.getPassword();
        User user = userService.findByLogin(u.getLogin());
        if(user == null){
            userService.createUser(u);
        }
        mockMvc.perform(post("/login")
                .param("username", u.getLogin())
                .param("password", password))
                .andExpect(authenticated())
                .andExpect(redirectedUrl(""))
                .andExpect(request().sessionAttribute("user", isA(User.class)))
                .andExpect(request().sessionAttribute("cart", isA(Cart.class)));
    }

}
