package com.springframework.webdevelopment.recipeproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class RecipeProjectApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(RecipeProjectApplication.class, args);
    }

}
