import com.michal.Application;
import com.michal.entities.Category;
import com.michal.impl.CategoryServiceImpl;
import com.michal.util.FileManager;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
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
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles(profiles = "test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private FileManager fileManager;

    @WithMockUser(authorities = {"ADMIN", "CUSTOMER"})
    @Test
    void categoryPageTest() throws Exception {
        mockMvc.perform(get("/category"))
                .andExpect(status().isOk())
                .andExpect(view().name("category"))
                .andExpect(model().attribute("categories", hasSize(categoryService.findAll().size())));
    }

    @WithMockUser(authorities = {"ADMIN"})
    @ParameterizedTest
    @MethodSource("Provider#getValidCategories")
    void createCategoryTest(Category c, File image) throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("image",image.getName(),
                MediaType.MULTIPART_FORM_DATA_VALUE, Files.readAllBytes(image.toPath()));
        mockMvc.perform(multipart("/category")
                .file(multipartFile)
                .param("name", c.getName()))
                .andExpect(redirectedUrl("/category"));
        Category category = categoryService.findByName(c.getName());
        File categoryImage = fileManager.getCategoryImagePath(category).toFile();
        File productsDirectory = new File(categoryImage.getParent(), "products");
        assertAll(
                ()-> assertTrue(categoryImage.exists()),
                ()-> assertTrue(productsDirectory.isDirectory())
        );
    }

    @WithMockUser(authorities = {"CUSTOMER", "ADMIN"})
    @ParameterizedTest
    @MethodSource("Provider#getValidCategories")
    void productsPageTest(Category c, File image) throws Exception {
        Category category = categoryService.findByName(c.getName());
        mockMvc.perform(get(String.format("/category/%d/products", category.getId())))
                .andExpect(status().isOk())
                .andExpect(view().name("product"))
                .andExpect(model().attributeExists("products"));
    }



    @WithMockUser(authorities = {"ADMIN"})
    @ParameterizedTest
    @MethodSource("Provider#getInvalidCategories")
    void createCategoryFailureTest(Category c, File image) throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("image",image.getName(),
                MediaType.MULTIPART_FORM_DATA_VALUE, Files.readAllBytes(image.toPath()));
        mockMvc.perform(multipart("/category")
                .file(multipartFile)
                .param("name", c.getName()))
                .andExpect(redirectedUrl("/category"));
        Category category = categoryService.findByName(c.getName());
        assertNull(category);
    }

    @AfterAll
    void cleanUp() throws IOException {
        List<Category> categories = categoryService.findAll();
        for(Category c : categories){
            File dir = fileManager.getOrCreateCategoryDirectory(c);
            FileUtils.deleteDirectory(dir);
            categoryService.delete(c);
        }
    }
}
