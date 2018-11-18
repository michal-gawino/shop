package com.michal.controllers;

import com.michal.entities.Order;
import com.michal.entities.OrderDetails;
import com.michal.entities.User;
import com.michal.impl.OrderServiceImpl;
import com.michal.util.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import java.util.List;

@Controller
@RequestMapping(value = "/order")
@SessionAttributes(names = {"cart", "user"})
public class OrderController {

    @Autowired
    OrderServiceImpl orderService;

    @GetMapping
    public String getOrderView(){
        return "order";
    }

    @GetMapping("/details")
    public String getDetailsView(Model model, User user){
        List<Order> orders = orderService.findByUser(user);
        model.addAttribute("orders", orders);
        return "order_details";
    }

    @PostMapping
    public String placeOrder(OrderDetails orderDetails, User user, Cart cart){
        Order order = new Order(user, orderDetails, cart.getProducts());
        orderService.save(order);
        cart.clear();
        return "redirect:/order/details";
    }
}
