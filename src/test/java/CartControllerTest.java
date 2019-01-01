import com.michal.Application;
import com.michal.entities.Product;
import com.michal.impl.ProductServiceImpl;
import com.michal.util.Cart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles(profiles = "test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@AutoConfigureMockMvc
@Transactional
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductServiceImpl productService;

    @WithMockUser(authorities = {"CUSTOMER", "ADMIN"})
    @Test
    void cartViewTest() throws Exception {
        mockMvc.perform(get("/cart")
                .sessionAttr("cart", new Cart()))
                .andExpect(view().name("cart"))
                .andExpect(request().sessionAttribute("cart", isA(Cart.class)))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = {"CUSTOMER"})
    @ParameterizedTest
    @MethodSource("Provider#getValidProducts")
    void addProductToCartTest(Product p) throws Exception {
        Cart cart = new Cart();
        Product product = productService.save(p);
        mockMvc.perform(post(String.format("/cart/%d", product.getId()))
                .sessionAttr("cart", cart));
        assertEquals(1, cart.getProducts().size());
    }

    @WithMockUser(authorities = {"CUSTOMER"})
    @ParameterizedTest
    @MethodSource("Provider#getValidProducts")
    void removeProductFromCartTest(Product p) throws Exception {
        Cart cart = new Cart();
        Product product = productService.save(p);
        cart.getProducts().add(product);
        mockMvc.perform(delete(String.format("/cart/%d", product.getId()))
                .sessionAttr("cart", cart));
        assertTrue(cart.getProducts().isEmpty());
    }
}
