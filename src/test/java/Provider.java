import com.michal.entities.User;
import java.util.stream.Stream;

class Provider {
    static Stream<User> getTestUser() {
        return Stream.of(new User("test_name", "test_surname", "test_login2", "secret"));
    }
}
