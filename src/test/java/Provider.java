import com.michal.entities.Category;
import com.michal.entities.OrderDetails;
import com.michal.entities.Product;
import com.michal.entities.User;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

class Provider {

    private static final String VALID_CATEGORIES_DIRECTORY = "valid_categories";
    private static final String INVALID_FILES_DIRECTORY = "invalid_files";
    private static final String VALID_PRODUCTS_DIRECTORY = "valid_products";

    static Stream<User> getValidUsers() {
        return Stream.of(
                new User("name1", "ab", "testlogin", "secret"),
                new User("xy", "aq", "user_test", "1w#e1AZpolL!"),
                new User("test_name", "abcdef", "013asd1k1", "p13ZxAA112")
        );
    }

    static Stream<Arguments> getInvalidUsers() {
        return Stream.of(
                arguments(new User("name1", "ab", "test", "secret"),
                        new String[]{"login"}),
                arguments(new User("xy", "aq", "user_test", "L!"),
                        new String[]{"password"}),
                arguments(new User("test_name", "abcdef", "1k1", "12"),
                        new String[]{"login", "password"})
        );
    }

    static Stream<Arguments> getValidOrderDetails() {
        return Stream.of(
                arguments(new User("name1", "ab", "test13", "secret"),
                        new OrderDetails("PL", "test_city", "street", "99-999",
                                "1111222233334444"), Arrays.asList(new Product("product1",
                                "brand", 12.5), new Product("product2", "test_brand", 122.35)))
        );
    }

    static Stream<Arguments> getValidCategories() throws IOException {
        File validCategoriesDirectory = new ClassPathResource(VALID_CATEGORIES_DIRECTORY).getFile();
        return Arrays.stream(validCategoriesDirectory.listFiles()).map(f-> arguments(new Category(f.getName()), f));
    }

    static Stream<Arguments> getInvalidCategories() throws IOException {
        File validCategoriesDirectory = new ClassPathResource(INVALID_FILES_DIRECTORY).getFile();
        return Arrays.stream(validCategoriesDirectory.listFiles()).map(f-> arguments(new Category(f.getName()), f));
    }

    static Stream<Arguments> getValidProducts() throws IOException {
        File validProductsDirectory = new ClassPathResource(VALID_PRODUCTS_DIRECTORY).getFile();
        return Arrays.stream(validProductsDirectory.listFiles())
                .map(f-> arguments(new Product(f.getName(), "test", 12.0), f));
    }

    static MultipartFile getCategoryTestImage() throws IOException {
        File image = new ClassPathResource(VALID_CATEGORIES_DIRECTORY).getFile().listFiles()[0];
        return new MockMultipartFile("image", image.getName(),
                MediaType.MULTIPART_FORM_DATA_VALUE, Files.readAllBytes(image.toPath()));
    }
}
