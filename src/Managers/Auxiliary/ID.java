package Managers.Auxiliary;

public class ID {
    private int id = 0;

    public int generator() {
        return id++;
    }

    public int getId() {
        return id;
    }
}
