package main.view;

import main.model.book.CookBook;

import java.util.Map;

public class DataViewer {

    public void showBooks(Map<String, CookBook> cookBooks) {
        if (cookBooks.isEmpty())
            System.out.println("There are no available books");
        else {
            System.out.println("Available books:");
            for (CookBook cookBook : cookBooks.values())
                System.out.println("  * " + cookBook.toString());
        }
    }
}
