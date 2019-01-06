import com.michal.Application;
import com.michal.entities.User;
import com.michal.enumerated.UserRole;
import com.michal.impl.UserServiceImpl;
import com.michal.security.CustomUserDetails;
import com.michal.util.PasswordChangeForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles(profiles = "test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Test
    @WithMockUser(authorities = {"CUSTOMER"})
    void profileViewTest() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"))
                .andExpect(model().attribute("user", isA(User.class)));
    }

    @Test
    @WithMockUser(authorities = {"CUSTOMER"})
    void changePasswordViewTest() throws Exception {
        mockMvc.perform(get("/user/password"))
                .andExpect(status().isOk())
                .andExpect(view().name("change_password"))
                .andDo(print());
    }

    @WithMockUser(authorities = {"ADMIN"})
    @ParameterizedTest
    @MethodSource("Provider#getValidUsers")
    void createUserTest(User user) throws Exception {
        mockMvc.perform(post("/user")
                .param("name", user.getName())
                .param("surname", user.getSurname())
                .param("login", user.getLogin())
                .param("password", user.getPassword()));
        assertNotNull(userService.findByLogin(user.getLogin()));
    }

    @WithMockUser(authorities = {"ADMIN"})
    @ParameterizedTest
    @MethodSource("Provider#getValidUsers")
    void deleteUserTest(User user) throws Exception {
        User u = userService.findByLogin(user.getLogin());
        if(u == null){
            u = userService.createUser(user);
        }
        mockMvc.perform(delete("/user")
                .param("usersToDelete", u.getId().toString()));
        assertNull(userService.findByLogin(user.getLogin()));
    }

    @WithMockUser(authorities = {"ADMIN"})
    @ParameterizedTest
    @MethodSource("Provider#getValidUsers")
    void updateUserTest(User user) throws Exception {
        User newUser = new User("newName", "test444", "newlog11");
        User u = userService.findByLogin(user.getLogin());
        if(u == null){
            u = userService.createUser(user);
        }
        mockMvc.perform(put(String.format("/user/%d", u.getId()))
                .param("name", newUser.getName())
                .param("login", newUser.getLogin())
                .param("surname", newUser.getSurname()));
        User updatedUser = userService.findByLogin(newUser.getLogin());
        assertAll(
                ()-> assertNotNull(updatedUser),
                ()-> assertEquals(updatedUser.getName(), newUser.getName()),
                ()-> assertEquals(updatedUser.getSurname(), newUser.getSurname())
        );
    }

    @ParameterizedTest
    @MethodSource("Provider#getValidUsers")
    void changePasswordFailureTest(User user) throws Exception {
        user.setRole(UserRole.CUSTOMER);
        String currentPassword = user.getPassword();
        String newPassword = "testpwd";
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        PasswordChangeForm form = new PasswordChangeForm(currentPassword.concat("test"), newPassword,
                newPassword.concat("abc"));
        mockMvc.perform(post("/user/password")
                .with(user(customUserDetails))
                .param("currentPassword", form.getCurrentPassword())
                .param("newPassword", form.getNewPassword())
                .param("newPasswordConfirmation", form.getNewPasswordConfirmation()))
                .andExpect(model().attributeHasFieldErrors("passwordChangeForm", "currentPassword",
                        "newPasswordConfirmation"));
    }

}
