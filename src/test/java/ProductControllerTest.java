import com.michal.Application;
import com.michal.entities.Category;
import com.michal.entities.Product;
import com.michal.impl.CategoryServiceImpl;
import com.michal.impl.ProductServiceImpl;
import com.michal.util.FileManager;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

@ActiveProfiles(profiles = "test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private FileManager fileManager;

    @WithMockUser(authorities = {"ADMIN"})
    @ParameterizedTest
    @MethodSource("Provider#getValidProducts")
    void createProductTest(Product p, File image) throws Exception {
        Category category = categoryService.create(new Category("test"), Provider.getCategoryTestImage());
        MockMultipartFile multipartFile = new MockMultipartFile("image", image.getName(),
                MediaType.MULTIPART_FORM_DATA_VALUE, Files.readAllBytes(image.toPath()));
        mockMvc.perform(multipart("/product")
                .file(multipartFile)
                .param("name", p.getName())
                .param("price", p.getPrice().toString())
                .param("brand", p.getBrand())
                .param("category", category.getId().toString()));
        Product product = productService.findByName(p.getName());
        File productImage = fileManager.getProductImagePath(product).toFile();
        assertTrue(productImage.exists());
    }


}
