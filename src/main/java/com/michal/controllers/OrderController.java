package com.michal.controllers;

import com.michal.entities.Order;
import com.michal.entities.OrderDetails;
import com.michal.entities.User;
import com.michal.impl.OrderServiceImpl;
import com.michal.util.Cart;
import com.michal.validators.OrderDetailsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/order")
@SessionAttributes(names = {"cart", "user"})
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private OrderDetailsValidator orderDetailsValidator;

    @InitBinder("orderDetails")
    protected void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(orderDetailsValidator);
    }

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
    public String placeOrder(@Valid @ModelAttribute("orderDetails") OrderDetails orderDetails,
                             BindingResult bindingResult, User user, Cart cart){
        if(bindingResult.hasFieldErrors()){
            return "order";
        }
        Order order = new Order(user, orderDetails, cart.getProducts());
        orderService.save(order);
        cart.clear();
        return "redirect:/order/details";
    }
}
