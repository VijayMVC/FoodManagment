package main.model.cookBookIO;

import main.model.book.CookBook;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/*
 * https://www.tutorialspoint.com/java/java_serialization.htm
 */
public class IOManager {
    public static void serialzie(Map<String, CookBook> cookBookMap, String outputFilePath) throws IOException {
        File file = new File(outputFilePath);
        FileOutputStream fileOut = new FileOutputStream(file);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(cookBookMap);
        out.close();
        fileOut.close();
    }

    public static Map<String, CookBook> deserialzie(String inputFilePath) throws ClassNotFoundException, IOException {
        FileInputStream fileIn = new FileInputStream(inputFilePath);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Map<String, CookBook> cookBookMap = (Map<String, CookBook>) in.readObject();
        in.close();
        fileIn.close();
        return cookBookMap;
    }

    public static void main(String args[]) throws IOException, ClassNotFoundException {
        serialzie(new HashMap<String, CookBook>() ,"alaasd");
        System.out.println("sad");
        //Map<String , CookBook>  aa= deserialzie("/home/jakub/alasad");
    }
}
