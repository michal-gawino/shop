package com.michal.controllers;

import com.michal.entities.Product;
import com.michal.impl.ProductServiceImpl;
import com.michal.util.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private FileManager fileManager;

    @PostMapping
    public String create(Product product, MultipartFile image) throws IOException {
        BufferedImage img = ImageIO.read(image.getInputStream());
        if(img!= null){
            product.setFilename(image.getOriginalFilename());
            File imgDest = new File(fileManager.getProductImagePath(product).getParent().toString(), image.getOriginalFilename());
            image.transferTo(imgDest);
        }
        productService.save(product);
        return "redirect:/admin/products";
    }

    @DeleteMapping
    public String delete(@RequestParam(value = "productsToDelete", required = false) List<Long> ids){
        if(ids != null){
            productService.delete(ids);
        }
        return "redirect:/admin/products";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Product product, Product newProduct, MultipartFile image) throws IOException {
        if(product != null){
            if(newProduct.getName() != null){
                product.setName(newProduct.getName());
            }
            if(newProduct.getBrand() != null){
                product.setBrand(newProduct.getBrand());
            }
            if(newProduct.getPrice() != null){
                product.setPrice(newProduct.getPrice());
            }
            if(newProduct.getCategory() != null){
                product.setCategory(newProduct.getCategory());
            }
            if(image != null){
                BufferedImage img = ImageIO.read(image.getInputStream());
                if(img != null){
                    Path productImagePath = fileManager.getProductImagePath(product);
                    Files.delete(productImagePath);
                    File imgDest = Paths.get(productImagePath.getParent().toString(), image.getOriginalFilename()).toFile();
                    image.transferTo(imgDest);
                    product.setFilename(image.getOriginalFilename());
                }
            }
            productService.save(product);
        }
        return "redirect:/admin/products";
    }
}
