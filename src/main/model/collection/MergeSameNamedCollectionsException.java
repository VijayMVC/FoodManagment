package main.model.collection;

/**
 * Thrown when harassing rule: Merging same named Collection can make some unwanted errors, it is forbidden.
 */
public class MergeSameNamedCollectionsException extends Exception {
    public MergeSameNamedCollectionsException() {
        super(" current cookBookCollection name and name of" +
                "\n merged collection are the same. This operation is forbidden, because of safety measures," +
                "\n make sure if you want continue operation and change one of collection names"
        );
    }
    }
