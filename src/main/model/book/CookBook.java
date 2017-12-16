package main.model.book;

import main.model.collection.DuplicateCookBookException;
import main.model.food.Ingredient;
import main.model.recipe.IRecipeChecker;
import main.model.recipe.Recipe;

import java.io.Serializable;
import java.util.*;

/**
 * Class represents Cook Book which aggregate Recipes and provide functionality to get specified
 * recipes.
 */
public class CookBook implements Serializable {
    private String cookBookName;
    private Map<String ,Recipe> recipes;

    /**
     *
     * @param cookBookName
     * @param recipes map of recipes where the key is recipe name
     */
    public CookBook(String cookBookName,Map<String, Recipe> recipes){
        this.cookBookName = cookBookName;
        if(recipes == null)
            throw new IllegalArgumentException("Map can not be a null");
        this.recipes = recipes;
    }

    public CookBook(String cookBookName){
        this(cookBookName, new HashMap<>());
    }

    /**
     *
     * @param recipe recipe to add
     * @throws DuplicateRecipeException
     */
    public void addRecipe(Recipe recipe) throws DuplicateRecipeException {
        if(recipes.get(recipe.getRecipeName()) != null)
            throw new DuplicateRecipeException();
        recipes.put(recipe.getRecipeName(), recipe);
    }

    /**
     *
     * @param recipeName name(key) of the recipe to remove.
     */
    public void removeRecipe(String recipeName){
        recipes.remove(recipeName);
    }

    /**
     * choose subset of Recipe Map according to checker func.
     *
     * @param checker interface instance or lambda expression by which subset of CookBook is chosen
     * @return Map of recipes containing chosen recipes.
     */
    public Map<String, Recipe> getRecipes(IRecipeChecker checker){
        Map<String, Recipe> recipeMap = new HashMap<>();
        for(Recipe recipe : recipes.values()){
            if(checker.check(recipe))
                recipeMap.put(recipe.getRecipeName(),recipe);
        }
        return recipeMap;
    }

    /**
     *choose subset of Recipe Map according to presence of Ingredient on Ingredient list
     *
     * @param ingredients List of ingredients being also a condition of function
     * @return subset of Recipe Map
     */
    public Map<String, Recipe> getRecipes(List<Ingredient> ingredients) {
        Map<String, Recipe> recipeMap = new HashMap<>();
        for(Recipe recipe : recipes.values()){
            if(recipe.canBeMadeWith(ingredients))
                recipeMap.put(recipe.getRecipeName(),recipe);
        }
        return recipeMap;
    }

    /**
     * merge two cookbooks
     *
     * @param other Cookbook from which recipes are taken to merge
     */
    public void merge(CookBook other) throws MergeSameNamedCookBooks {
        if(this.getCookBookName().equals(other.getCookBookName()))
            throw new MergeSameNamedCookBooks();
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


    public Recipe getRecipe(String recipeName){
        return recipes.get(recipeName);
    }

    public String getCookBookName() {
        return cookBookName;
    }

    public void setCookBookName(String cookBookName) {
        this.cookBookName = cookBookName;
    }

    public List<String> getTableOfContents(){
        List<String> tableOfContents = new ArrayList<String>(recipes.keySet());
        Collections.sort(tableOfContents, String::compareTo);
        return tableOfContents;
    }

    /**
     * describes if two objects are equals. Two CookBooks are considered equals if and only if they have
     * the same names and the same Recipes.
     *
     * @param obj compared object
     * @return true if obj is equal to CookBook from which method was invoked.
     */
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

    /**
     * rename recipe contained in Cook Book
     *
     * @param oldName old name of recipe
     * @param newName new name of recipe
     * @throws RecipeNotFoundException
     * @throws DuplicateRecipeException
     */
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
