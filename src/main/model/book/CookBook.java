package main.model.book;

import main.model.food.Ingredient;
import main.model.recipe.IRecipeChecker;
import main.model.recipe.Recipe;

import java.io.Serializable;
import java.util.*;

public class CookBook implements Serializable {
    private String cookBookName;
    private Map<String ,Recipe> recipes;

    public CookBook(String cookBookName, Map<String, Recipe> recipes){
        this.cookBookName = cookBookName;
        if(recipes == null)
            throw new IllegalArgumentException("Map can not be a null");
        this.recipes = recipes;
    }

    public CookBook(String cookBookName){
        this(cookBookName, new HashMap<String, Recipe>());
    }

    public CookBook(String cookBookName, List<Recipe> recipeList) throws DuplicateRecipeException {
        this(cookBookName, new HashMap<String, Recipe>());
        for(Recipe recipe: recipeList){
            this.addRecipe(recipe);
        }
    }

    public void addRecipe(Recipe recipe) throws DuplicateRecipeException {
        if(recipes.get(recipe.getRecipeName()) != null)
            throw new DuplicateRecipeException();
        recipes.put(recipe.getRecipeName(), recipe);
    }

    public void removeRecipe(String recipeName){
        recipes.remove(recipeName);
    }

    public List<Recipe> getRecipes(IRecipeChecker checker){
        List<Recipe> recipeList = new ArrayList<>();
        for(Recipe recipe : recipes.values()){
            if(checker.check(recipe))
                recipeList.add(recipe);
        }
        return recipeList;
    }

    public List<Recipe> getRecipes(List<Ingredient> ingredients)
    {
        List<Recipe> recipeList = new ArrayList<>();
        for(Recipe recipe : recipes.values()){
            if(recipe.canBeMadeWith(ingredients))
                recipeList.add(recipe);
        }
        return recipeList;
    }

    public Recipe getRecipe(String recipeName){
        return recipes.get(recipeName);
    }

    public List<String> getTableOfContents(){
        List<String> tableOfContents = new ArrayList<String>(recipes.keySet());
        Collections.sort(tableOfContents, String::compareTo);
        return tableOfContents;
    }

    @Override
    public String toString() {
        return cookBookName;
    }
}
