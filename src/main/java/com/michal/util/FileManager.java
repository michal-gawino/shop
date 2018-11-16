package com.michal.util;

import com.michal.entities.Category;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    public String getBase64CategoryImage(Category c) throws IOException {
        BASE64Encoder encoder = new BASE64Encoder();
        Path path = Paths.get(ROOT_DIRECTORY, CATEGORY_DIRECTORY, c.getId().toString(), c.getFilename());
        byte[] file = Files.readAllBytes(path);
        return encoder.encode(file);
    }

    public Path getCategoryImagePath(Category c){
        return Paths.get(ROOT_DIRECTORY, CATEGORY_DIRECTORY, c.getId().toString(), c.getFilename());
    }
}
