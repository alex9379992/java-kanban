import java.util.ArrayList;

public interface HistoryManager<T extends Task> {

    void add(Task task);

    ArrayList<T> getHistory();

}
