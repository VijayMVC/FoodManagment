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
                "\n - showLactoseFreeMeals");
    }

    public void printRecipeHelp() {
        System.out.println("Available context functions: " +
                "\n - setRecipeName" +
                "\n - addDirection" +
                "\n - removeDirection");
    }
}