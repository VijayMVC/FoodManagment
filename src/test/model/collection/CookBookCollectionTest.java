package test.model.collection;

import main.model.book.CookBook;
import main.model.collection.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CookBookCollectionTest {
    @Test
    void serialize() throws IOException, UnnamedCollectionException {
        CookBookCollection collection = new CookBookCollection();
        assertThrows(UnnamedCollectionException.class, () -> collection.serialize("ala123.ser"));
        CookBookCollection collection2 = new CookBookCollection("ala123");
        assertThrows(IOException.class, () -> collection2.serialize("home/xd/as/qw//ala123.ser"));
        collection2.serialize("ala123.ser");
    }

    @Test
    void deserialize() throws IOException, UnnamedCollectionException, ClassNotFoundException {
        String filepath = "ala123.ser";
        CookBookCollection collection2 = new CookBookCollection("ala123");
        assertThrows(IOException.class , ()->CookBookCollection.deserialize(filepath));
        collection2.serialize(filepath);
        collection2 = CookBookCollection.deserialize(filepath);
    }

    @Test
    void merge() throws MergeSameNamedCollectionsException {
        CookBookCollection collection = new CookBookCollection();
        CookBookCollection collection2 = new CookBookCollection("ala123");
        CookBook testCookBook = new CookBook("dinners");
        collection2.getCookBooks().put(testCookBook.getCookBookName(),testCookBook);
        CookBookCollection collection3 = new CookBookCollection("ala123");
        assertThrows(MergeSameNamedCollectionsException.class , ()-> collection2.merge(collection3));
        collection.merge(collection2);
        assertFalse(collection.getCookBooks().isEmpty());
        assertEquals(collection.getCookBooks().get("dinners"),testCookBook );

    }

    @Test
    void renameCookBook() throws CookBookNotFoundException, DuplicateCookBookException {
        CookBookCollection collection2 = new CookBookCollection("ala123");
        CookBook testCookBook = new CookBook("dinners");
        collection2.getCookBooks().put(testCookBook.getCookBookName(),testCookBook);
        CookBook testCookBook2 = new CookBook("suppers");
        collection2.getCookBooks().put(testCookBook2.getCookBookName(),testCookBook2);
        assertThrows(DuplicateCookBookException.class, ()->collection2.renameCookBook("dinners","suppers"));
        assertThrows(CookBookNotFoundException.class, ()->collection2.renameCookBook("dinnerss","suppers"));
        collection2.renameCookBook("dinners", "breakfast");
        assertEquals(collection2.getCookBooks().get("breakfast"), testCookBook);
    }

}