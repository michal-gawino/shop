package com.michal.controllers;

import com.michal.entities.Product;
import com.michal.util.Cart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/cart")
@SessionAttributes("cart")
public class CartController {

    @GetMapping
    public String getCartView(){
        return "cart";
    }

    @PostMapping("/{id}")
    public String addProduct(@PathVariable("id") Product product, Cart cart,
                             @RequestHeader(value = "referer", required = false) final String referrer){
        cart.addProduct(product);
        return "redirect:"+referrer;
    }

    @DeleteMapping("/{id}")
    public String removeProduct(@PathVariable("id") Product product, Cart cart,
                                @RequestHeader(value = "referer", required = false) final String referrer){
        cart.removeProduct(product);
        return "redirect:"+referrer;
    }

}
