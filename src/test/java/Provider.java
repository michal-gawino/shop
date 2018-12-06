import com.michal.entities.User;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

class Provider {

    static Stream<User> getValidUsers() {
        return Stream.of(
                new User("name1", "ab", "testlogin", "secret"),
                new User("xy", "aq", "user_test", "1w#e1AZpolL!"),
                new User("test_name", "abcdef", "013asd1k1", "p13ZxAA112")
        );
    }

    static Stream<Arguments> getInvalidUsers() {
        return Stream.of(
                arguments(new User("name1", "ab", "test", "secret"), new String[]{"login"}),
                arguments(new User("xy", "aq", "user_test", "L!"), new String[]{"password"}),
                arguments(new User("test_name", "abcdef", "1k1", "12"), new String[]{"login", "password"})
        );
    }
}
