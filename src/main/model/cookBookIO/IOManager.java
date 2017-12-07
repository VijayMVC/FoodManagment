package main.model.cookBookIO;

import main.model.book.CookBook;

import java.io.*;
import java.util.List;

public class IOManager {
    public static void serialzie(List<CookBook> cookBookList, String outputFilePath) throws IOException {
        File file = new File(outputFilePath);
        FileOutputStream fileOut = new FileOutputStream(file);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(cookBookList);
        out.close();
        fileOut.close();
    }

    public static List<CookBook> deserialzie(String inputFilePath) throws IOException, ClassCastException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(inputFilePath);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        List<CookBook> cookBookList = (List<CookBook>) in.readObject();
        in.close();
        fileIn.close();
        return cookBookList;
    }
}
