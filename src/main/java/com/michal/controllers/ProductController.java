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
import java.nio.file.*;
import java.util.List;

@Controller
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private FileManager fileManager;

    @PostMapping
    public String create(Product product, MultipartFile image,
                         @RequestHeader(value = "referer", required = false) final String referrer) throws IOException {
        BufferedImage img = ImageIO.read(image.getInputStream());
        if(img!= null){
            product.setFilename(image.getOriginalFilename());
            File imgDest = new File(fileManager.getProductImagePath(product).getParent().toString(), image.getOriginalFilename());
            image.transferTo(imgDest);
        }
        productService.save(product);
        System.out.println(referrer);
        return "redirect:" + referrer;
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Product product, Product newProduct, MultipartFile image,
                         @RequestHeader(value = "referer", required = false) final String referrer) throws IOException {
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
                Path sourceImage = fileManager.getProductImagePath(product);
                newProduct.setFilename(product.getFilename());
                Path dest = fileManager.getProductImagePath(newProduct);
                Files.move(sourceImage, dest, StandardCopyOption.ATOMIC_MOVE);
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
        return "redirect:" + referrer;
    }
}
