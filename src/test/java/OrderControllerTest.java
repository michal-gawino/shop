import com.michal.Application;
import com.michal.entities.Order;
import com.michal.entities.OrderDetails;
import com.michal.entities.Product;
import com.michal.entities.User;
import com.michal.impl.OrderServiceImpl;
import com.michal.impl.ProductServiceImpl;
import com.michal.impl.UserServiceImpl;
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
import java.util.List;

import static org.hamcrest.Matchers.empty;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles(profiles = "test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@AutoConfigureMockMvc
@Transactional
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private ProductServiceImpl productService;

    @Test
    @WithMockUser(authorities = {"CUSTOMER"})
    void ordersViewTest() throws Exception {
        mockMvc.perform(get("/order"))
                .andExpect(status().isOk())
                .andExpect(view().name("order"));
    }

    @ParameterizedTest
    @MethodSource("Provider#getValidUsers")
    @WithMockUser(authorities = {"CUSTOMER"})
    void orderDetailsViewTest(User user) throws Exception {
        if(user.getId() == null){
            user = userService.createUser(user);
        }
        mockMvc.perform(get("/order/details")
                .sessionAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(view().name("order_details"))
                .andExpect(model().attribute("orders", empty()));
    }

    @ParameterizedTest
    @MethodSource("Provider#getValidOrderDetails")
    @WithMockUser(authorities = {"CUSTOMER"})
    void placeOrderTest(User u, OrderDetails orderDetails, List<Product> products) throws Exception {
        if(u.getId() == null){
            u = userService.createUser(u);
        }
        Cart cart = new Cart();
        cart.getProducts().addAll(products);
        mockMvc.perform(post("/order")
                .param("country", orderDetails.getCity())
                .param("city", orderDetails.getCity())
                .param("street", orderDetails.getStreet())
                .param("postalCode", orderDetails.getPostalCode())
                .param("creditCardNumber", orderDetails.getCreditCardNumber())
                .sessionAttr("user", u)
                .sessionAttr("cart", cart));
        List<Order> orders = orderService.findByUser(u);
        assertAll(
                () -> assertEquals(orders.size(), 1),
                () -> assertTrue(cart.getProducts().isEmpty()));
    }
}
