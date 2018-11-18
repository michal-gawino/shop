package com.michal.controllers;

import com.michal.entities.Product;
import com.michal.util.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/cart")
@SessionAttributes("cart")
public class CartController {

    @Autowired
    HttpSession session;

    @GetMapping
    public String getCartView(){
        return "cart";
    }

    @PostMapping("/{id}")
    public String addProduct(@PathVariable("id") Product product, Cart cart,
                             @RequestHeader(value = "referer", required = false) final String referrer){
        cart.addProduct(product);
        session.setAttribute("cart", cart);
        return "redirect:"+referrer;
    }

    @DeleteMapping("/{id}")
    public String removeProduct(@PathVariable("id") Product product, Cart cart,
                                @RequestHeader(value = "referer", required = false) final String referrer){
        cart.removeProduct(product);
        session.setAttribute("cart", cart);
        return "redirect:"+referrer;
    }

}
