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
import org.springframework.transaction.annotation.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles(profiles = "test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@AutoConfigureMockMvc
@Transactional
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
    void cleanDirectories() throws IOException {
        File dir = fileManager.getCategoriesFile();
        FileUtils.deleteDirectory(dir);
    }
}
