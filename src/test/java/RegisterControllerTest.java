import com.michal.Application;
import com.michal.entities.User;
import com.michal.impl.UserServiceImpl;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles(profiles = "test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@AutoConfigureMockMvc
@Transactional
class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserServiceImpl userService;

    @Test
    void registerPageTest() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @ParameterizedTest
    @MethodSource("Provider#getValidUsers")
    void registrationSuccessTest(User user) throws Exception {
        mockMvc.perform(post("/register")
                .param("name", user.getName())
                .param("surname", user.getSurname())
                .param("login", user.getLogin())
                .param("password", user.getPassword()))
                .andExpect(flash().attributeExists("success"))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/register"));
    }

    @ParameterizedTest
    @MethodSource("Provider#getInvalidUsers")
    void registrationFailureTest(User user, String ...fieldNames) throws Exception {
        mockMvc.perform(post("/register")
                .param("name", user.getName())
                .param("surname", user.getSurname())
                .param("login", user.getLogin())
                .param("password", user.getPassword()))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeHasFieldErrors("user", fieldNames));
    }

}
