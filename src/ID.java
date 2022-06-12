import java.util.Objects;

public class ID {
    private int id = 0;

    public int generator() {
        return ++id;
    }
}
