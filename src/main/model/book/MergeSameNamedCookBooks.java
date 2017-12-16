package main.model.book;

/**
 * Thrown when harassing rule: Merging same named Cook Books can make some unwanted errors, it is forbidden.
 */
public class MergeSameNamedCookBooks extends Throwable {
    public MergeSameNamedCookBooks() {
        super(" name of merged cook books" +
                "\n are the same. This operation is forbidden, because of safety measures," +
                "\n make sure if you want continue operation and change one of cook book names"
        );
    }
}
