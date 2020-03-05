package com.michal.util;

import com.michal.entities.Category;
import com.michal.entities.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Component
public class FileManager {

    @Value("${shop.root_directory}")
    private String ROOT_DIRECTORY;

    private static final String CATEGORY_DIRECTORY = "categories";
    private static final String PRODUCTS_DIRECTORY = "products";

    public File getOrCreateRootDirectory(){
        File rootDir = new File(ROOT_DIRECTORY);
        if(!rootDir.exists()){
            rootDir.mkdirs();
        }
        return rootDir;
    }

    public File getOrCreateCategoryDirectory(Category c){
        File categoryDir = Paths.get(ROOT_DIRECTORY, CATEGORY_DIRECTORY, c.getId().toString()).toFile();
        if(!categoryDir.exists()){
            categoryDir.mkdirs();
        }
        File productsDir = new File(categoryDir, PRODUCTS_DIRECTORY);
        if(!productsDir.exists()){
            productsDir.mkdir();
        }
        return categoryDir;
    }

    public String getBase64Image(Path path) throws IOException {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] file = Files.readAllBytes(path);
        return encoder.encodeToString(file);
    }

    public Path getCategoryImagePath(Category c){
        return Paths.get(ROOT_DIRECTORY, CATEGORY_DIRECTORY, c.getId().toString(), c.getFilename());
    }

    public Path getProductImagePath(Product p){
        return Paths.get(ROOT_DIRECTORY, CATEGORY_DIRECTORY, p.getCategory().getId().toString(), PRODUCTS_DIRECTORY, p.getFilename());
    }

    public File getCategoriesFile(){
        return Paths.get(ROOT_DIRECTORY, CATEGORY_DIRECTORY).toFile();
    }
}
