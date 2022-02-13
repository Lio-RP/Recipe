package com.springframework.webdevelopment.recipeproject;

import com.springframework.webdevelopment.recipeproject.controllers.IndexController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class RecipeProjectApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(RecipeProjectApplication.class, args);

        IndexController indexController = ctx.getBean(IndexController.class);
        indexController.getIndexPage();
    }

}
