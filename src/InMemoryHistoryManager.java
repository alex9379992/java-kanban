import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager  {

    private ArrayList<Task> viewsHistory = new ArrayList<>();


    @Override
    public void add(Task task) {
        if(viewsHistory.size() < 10) {
            viewsHistory.add(0, (Task) task);
        }
        if(viewsHistory.size() >= 10) {
            viewsHistory.add(0, (Task) task);
            viewsHistory.remove(11);
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        if(viewsHistory.isEmpty()) {
            System.out.println("Список истории задач пуст");

        }
        return viewsHistory;
    }
}
