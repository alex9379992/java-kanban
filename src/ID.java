import java.util.Objects;

public class ID {
    private int id = 0;

    protected int generator() {
        id += 1;
        int newId = id;
        return newId;
    }
}
