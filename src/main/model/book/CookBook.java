package main.model.book;

import main.model.collection.CookBookNotFoundException;
import main.model.collection.DuplicateCookBookException;
import main.model.food.Ingredient;
import main.model.recipe.IRecipeChecker;
import main.model.recipe.Recipe;

import java.io.Serializable;
import java.util.*;

public class CookBook implements Serializable {
    private String cookBookName;
    private Map<String ,Recipe> recipes;

    public CookBook(String cookBookName,Map<String, Recipe> recipes){
        this.cookBookName = cookBookName;
        if(recipes == null)
            throw new IllegalArgumentException("Map can not be a null");
        this.recipes = recipes;
    }

    public CookBook(String cookBookName){
        this(cookBookName, new HashMap<>());
    }

    public void addRecipe(Recipe recipe) throws DuplicateRecipeException {
        if(recipes.get(recipe.getRecipeName()) != null)
            throw new DuplicateRecipeException();
        recipes.put(recipe.getRecipeName(), recipe);
    }

    public void removeRecipe(String recipeName){
        recipes.remove(recipeName);
    }

    public Map<String, Recipe> getRecipes(IRecipeChecker checker){
        Map<String, Recipe> recipeMap = new HashMap<>();
        for(Recipe recipe : recipes.values()){
            if(checker.check(recipe))
                recipeMap.put(recipe.getRecipeName(),recipe);
        }
        return recipeMap;
    }

    public Map<String, Recipe> getRecipes(List<Ingredient> ingredients) {
        Map<String, Recipe> recipeMap = new HashMap<>();
        for(Recipe recipe : recipes.values()){
            if(recipe.canBeMadeWith(ingredients))
                recipeMap.put(recipe.getRecipeName(),recipe);
        }
        return recipeMap;
    }

    public Recipe getRecipe(String recipeName){
        return recipes.get(recipeName);
    }

    public List<String> getTableOfContents(){
        List<String> tableOfContents = new ArrayList<String>(recipes.keySet());
        Collections.sort(tableOfContents, String::compareTo);
        return tableOfContents;
    }

    public void merge(CookBook other){
        for(Recipe recipe : other.recipes.values()){
            Recipe tempRecipe = recipes.get(recipe.getRecipeName());
            if(tempRecipe == null) // not in map
                recipes.put(recipe.getRecipeName(), recipe);
            else if (!tempRecipe.equals(recipe)){
                tempRecipe.setRecipeName(this.cookBookName + "." + tempRecipe.getRecipeName());
                recipe.setRecipeName(other.cookBookName + "." + recipe.getRecipeName());
                recipes.put(recipe.getRecipeName(), recipe);
            }
        }
    }

    public String getCookBookName() {
        return cookBookName;
    }

    public void setCookBookName(String cookBookName) {
        this.cookBookName = cookBookName;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(!(obj instanceof CookBook))
            return false;
        if(!this.toString().equals(obj.toString()))
            return false;
        if(this.recipes.size() != ((CookBook) obj).recipes.size())
            return false;
        for(String recipeName : this.recipes.keySet()) {
            if(((CookBook) obj).recipes.get(recipeName) == null )
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return cookBookName;
    }

    public void renameRecipe(String oldName, String newName) throws RecipeNotFoundException, DuplicateRecipeException {
        Recipe recipe = recipes.get(oldName);
        if(recipe == null)
            throw new RecipeNotFoundException();
        if(recipes.get(newName)!=null)
            throw new DuplicateRecipeException();
        recipes.remove(oldName);
        recipe.setRecipeName(newName);
        recipes.put(newName,recipe);
    }
}
