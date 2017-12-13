package main.model.collection;

public class MergeSameNamedCollectionsException extends Throwable {
    public MergeSameNamedCollectionsException() {
        super(" current cookBookCollection name and name of" +
                "\n merged collection are the same. This operation is forbidden, because of safety measures," +
                "\n make sure if you want continue operation and change one of collection names"
        );
    }
    }
