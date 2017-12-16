package main.model.collection;

import main.model.book.CookBook;

import java.io.*;
import java.util.*;

/*
 * https://www.tutorialspoint.com/java/java_serialization.htm
 */

/**
 * Class represents Collection of CookBooks and provide functionality to save cookbooks to file and import
 * them. It also give access to cook books contained in it by map instance.
 */
public class CookBookCollection implements Serializable {

    private Map<String,CookBook> cookBooks;
    private String collectionName;

    /**
     *
     * @param collectionName
     * @param cookBooks - instance of Map containing cook books where the key is cook book name
     */
    public CookBookCollection(String collectionName, Map<String,CookBook> cookBooks) {
        this.cookBooks = cookBooks;
        this.collectionName = collectionName;
    }

    public CookBookCollection() {
        this("unnamed", new HashMap<>());
    }

    public CookBookCollection(Map<String,CookBook> cookBooks) {
        this("unnamed", cookBooks);
    }

    public CookBookCollection(String collectionName) {
        this(collectionName, new HashMap<>());
    }




    /**
     *
     * @param cookBook cook book to add
     * @throws DuplicateCookBookException cook book with that name already exists
     */
    public void addCookBook(CookBook cookBook) throws DuplicateCookBookException {
        if(cookBooks.get(cookBook.getCookBookName())!=null)
            throw new DuplicateCookBookException();
        else cookBooks.put(cookBook.getCookBookName(),cookBook);
    }

    /**
     *
     * @param cookBookName
     * @return cook book with given cook book name
     * @throws CookBookNotFoundException thrown where there is no such book in collection
     */
    public CookBook getCookBook(String cookBookName) throws CookBookNotFoundException {
        CookBook cookBook = cookBooks.get(cookBookName);
        if(cookBook == null)
            throw new CookBookNotFoundException();
        else
            return cookBook;
    }

    public void removeCookBook(String cookBookName){
        cookBooks.remove(cookBookName);
    }

    /**
     *
     * @param oldName old cook book name
     * @param newName new cook book name
     * @throws CookBookNotFoundException
     * @throws DuplicateCookBookException
     */
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



    /**
     * Save the object to file.
     *
     * @param outputFilePath path to destination along with file name where should the file be created
     * @throws IOException
     * @throws UnnamedCollectionException
     */
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

    /**
     * import data from file
     *
     * @param inputFilePath path to saved object file
     * @return Cook book previously serialized to file
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static CookBookCollection deserialize(String inputFilePath)
            throws ClassNotFoundException, IOException {
        FileInputStream fileIn = new FileInputStream(inputFilePath);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        CookBookCollection importedCollection = (CookBookCollection) in.readObject();
        in.close();
        fileIn.close();
        return importedCollection;
    }




    public List<String> getTableOfContents(){
        List<String> tableOfContents = new ArrayList<>(cookBooks.keySet());
        Collections.sort(tableOfContents, String::compareTo);
        return tableOfContents;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    /**
     * merge other collection to collection from the method was invoked.
     *
     * @param other collection which from components are taken
     * @throws MergeSameNamedCollectionsException
     */
    public void merge(CookBookCollection other) throws MergeSameNamedCollectionsException {
        if(this.collectionName.equals(other.collectionName))
            throw new MergeSameNamedCollectionsException();
        for(CookBook cookBook : other.cookBooks.values()){
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



}
