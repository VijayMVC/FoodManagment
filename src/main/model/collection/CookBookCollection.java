package main.model.collection;

import main.model.book.CookBook;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/*
 * https://www.tutorialspoint.com/java/java_serialization.htm
 */
public class CookBookCollection implements Serializable {

    private Map<String,CookBook> cookBooks;
    private String collectionName;

    public CookBookCollection() {
        this("unnamed", new HashMap<>());
    }

    public CookBookCollection(Map<String,CookBook> cookBooks) {
        this("unnamed", cookBooks);
    }


    public CookBookCollection(String collectionName) {
        this(collectionName, new HashMap<>());
    }

    public CookBookCollection(String collectionName, Map<String,CookBook> cookBooks) {
        this.cookBooks = cookBooks;
        this.collectionName = collectionName;
    }

    public void serialize(String outputFilePath) throws IOException, UnnamedCollectionException {
        if(this.collectionName.equals("unnamed"))
            throw new UnnamedCollectionException();
        File file = new File(outputFilePath);
        FileOutputStream fileOut = new FileOutputStream(file);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(this);
        out.close();
        fileOut.close();
    }

    public static CookBookCollection deserialize(String inputFilePath)
            throws ClassNotFoundException, IOException {
        FileInputStream fileIn = new FileInputStream(inputFilePath);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        CookBookCollection importedCollection = (CookBookCollection) in.readObject();
        in.close();
        fileIn.close();
        return importedCollection;
    }


    public Map<String, CookBook> getCookBooks() {
        return cookBooks;
    }

    public void merge(CookBookCollection other) throws MergeSameNamedCollectionsException {
        if(this.collectionName.equals(other.collectionName))
            throw new MergeSameNamedCollectionsException();
        for(CookBook cookBook : other.getCookBooks().values()){
            CookBook temp = cookBooks.get(cookBook.getCookBookName());
            if( temp == null)
                cookBooks.put(cookBook.getCookBookName(), cookBook);
            else{
                if(!cookBook.equals(temp)) {
                    cookBook.setCookBookName(other.getCollectionName() + "." + cookBook.getCookBookName());
                    temp.setCookBookName(this.getCollectionName() + "." + temp.getCookBookName());
                    cookBooks.put(cookBook.getCookBookName(), cookBook);
                }
            }
        }
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public void renameCookBook(String oldName, String newName) throws CookBookNotFoundException, DuplicateCookBookException {
        CookBook cookBook = cookBooks.get(oldName);
        if(cookBook == null)
            throw new CookBookNotFoundException();
        if(cookBooks.get(newName)!=null)
            throw new DuplicateCookBookException();
        cookBooks.remove(oldName);
        cookBook.setCookBookName(newName);
        cookBooks.put(newName,cookBook);
    }
}
