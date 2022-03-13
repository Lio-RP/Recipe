package com.springframework.webdevelopment.recipeproject.service;

import com.springframework.webdevelopment.recipeproject.commands.IngredientCommand;
import com.springframework.webdevelopment.recipeproject.converters.IngredientCommandToIngredient;
import com.springframework.webdevelopment.recipeproject.converters.IngredientToIngredientCommand;
import com.springframework.webdevelopment.recipeproject.domain.Ingredient;
import com.springframework.webdevelopment.recipeproject.domain.Recipe;
import com.springframework.webdevelopment.recipeproject.repositories.IngredientRepository;
import com.springframework.webdevelopment.recipeproject.repositories.RecipeRepository;
import com.springframework.webdevelopment.recipeproject.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand convertToIngredientCommand;
    private final IngredientCommandToIngredient convertToIngredient;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand convertToIngredientCommand, IngredientCommandToIngredient convertToIngredient, UnitOfMeasureRepository unitOfMeasureRepository, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.convertToIngredientCommand = convertToIngredientCommand;
        this.convertToIngredient = convertToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public IngredientCommand findByIngredientIdAndRecipeId(Long recipeId, Long ingredientId) {

        // //Find the Recipe based on the receipId pass as parameter, We //get an Optional of Recipe
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(!recipeOptional.isPresent()){
            log.debug("recipe id not found. Id" + recipeId);
        }

        //We get the Recipe from the Optional
        Recipe recipe = recipeOptional.get();

        //Recipe contains a Set of Ingredients which we convert to a
        //stream
        Optional<IngredientCommand> optionalIngredientCommand = recipe.getIngredients().stream()
                //We filter out that particular Ingredient having the
                // same ID as ingredientId passed as method parameter
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                //We convert the Ingredient we got to an Optional of IngredientCommand object
                .map(ingredient -> convertToIngredientCommand.convert(ingredient)).findFirst();

        if(!optionalIngredientCommand.isPresent()){
            log.debug("Ingredient Not Found.");
        }

        IngredientCommand ingredientCommand = optionalIngredientCommand.get();

        return ingredientCommand;
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(ingredientCommand.getRecipeId());

        if(!optionalRecipe.isPresent()){
            log.debug("Recipe not Found for id " + ingredientCommand.getRecipeId());
            return new IngredientCommand();
        }else{
            //Get the recipe value from the optional
            Recipe recipe = optionalRecipe.get();


            Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                    //Recipe contains a Set of Ingredients which we convert to a stream
                    .stream()
                    ////We filter out that particular Ingredient having the
                    // same ID as the id of the command object passed in the method parameter
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    //We got an optional of ingredient object
                    .findFirst();
            if(ingredientOptional.isPresent()){


                //Get the ingredient Optional object
                Ingredient ingredientFound = ingredientOptional.get();

                //Update
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setAmount(ingredientCommand.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository.findById(ingredientCommand.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND.")));
            }else{

                //if the ingredient does not exist;
                //add new ingredient
                Ingredient ingredient = convertToIngredient.convert(ingredientCommand);
                ingredient.setRecipe(recipe); //NB: this is also already done addIngredient Method
                recipe.addIngredient(ingredient);
                //log.debug("Not persisted Ingredient's id that gets passed in the method 2: " + ingredientCommand.getId());

            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            //Check by Description, amount and uom:
           if(!savedIngredientOptional.isPresent()){
                //not totally safe:
                savedIngredientOptional = savedRecipe.getIngredients()
                        .stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(ingredientCommand.getUom().getId()))
                        .findFirst();
               // log.debug("the condition of the not persisted ingredient gets executed!.");

            }

            //log.debug("Not persisted Ingredient's id that gets passed in the method 3: " + ingredientCommand.getId());
            //log.debug("persisted ingredient id: " + savedIngredientOptional.get().getId());




            return convertToIngredientCommand.convert(savedIngredientOptional.get());

        }
    }

    @Override
    @Transactional
    public void deleteById(Long recipeId, Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(!recipeOptional.isPresent()){
            throw new RuntimeException("recipe not found with id " + recipeId);
        }

        //get the value:
        Recipe recipe = recipeOptional.get();

        Optional<Ingredient> optionalIngredient = recipe.getIngredients()
                        .stream()
                                .filter(ingredient -> ingredient.getId().equals(id))
                                        .findFirst();

        if(!optionalIngredient.isPresent()){
            throw new RuntimeException("Ingredient Not Found with id " + id);
        }
        Ingredient ingredient = optionalIngredient.get();
        recipe.getIngredients().remove(ingredient);
        ingredientRepository.delete(ingredient);
        recipeRepository.save(recipe);
        log.debug("deleted the Ingredient with the id " + ingredient.getId());



    }
}
