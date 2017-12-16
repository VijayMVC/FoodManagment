package main.view;

public class HelpViewer {

    public void printHelp() {
        System.out.println("Available global functions: " +
                "\n - /help" +
                "\n - /context" +
                "\n - /showBooks" +
                "\n - /newBook bookName" +
                "\n - /selectBook BookName" +
                "\n - /import filePath " +
                "\n - /export filePath" +
                "\n - /setCollectionName collectionName" +
                "\n - /showCollectionName" +
                "\n - /unfocus" +
                "\n - /quit");
    }

    public void printCookBookHelp() {
        System.out.println("Available context functions: " +
                "\n - showTableOfContents" +
                "\n - setCookBookName" +
                "\n - addRecipe" +
                "\n - removeRecipe" +
                "\n - selectRecipe" +
                "\n - showVegetarianMeals" +
                "\n - showNutFreeMeals" +
                "\n - showLactoseFreeMeals" +
                "\n - showIngredients" +
                "\n - showRecipesDoableWith [Ingredients]");
    }

    public void printRecipeHelp() {
        System.out.println("Available context functions: " +
                "\n - setRecipeName" +
                "\n - showRecipe" +
                "\n - addDirection [Direction]" +
                "\n - removeDirection index" +
                "\n - addIngredient quantity measure ingredient" +
                "\n - removeIngredient index" +
                "\n - selectIngredient index" +
                "\n - setPreparationTime hours minutes (int)" +
                "\n - setCookingTime hours minutes (int)" +
                "\n - setTip [Tip]" +
                "\n - showIngredients" +
                "\n - showMeasures" +
                "\n - return" );
    }

    public void printRecipeIngredientHelp() {
        System.out.println("Available context functions: " +
                "\n - setQuantity quantity" +
                "\n - convertTo measure" +
                "\n - return" );
    }

    public void printUnfocusedHelp() {
        System.out.println("Available context functions: " +
                "\n - removeCookBook");
    }
}