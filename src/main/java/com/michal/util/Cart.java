package com.michal.util;

import com.michal.entities.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cart {
    private List<Product> products;

    public Cart() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product p){
        products.add(p);
    }

    public void removeProduct(Product product){
        Optional<Product> prod = products.stream().filter(p->p.getId().equals(product.getId())).findFirst();
        prod.ifPresent(products::remove);
    }

    public double getTotalCartValue(){
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void clear(){
        products.clear();
    }
}
