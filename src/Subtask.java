import java.util.HashMap;



public class Subtask extends Task{

    HashMap<Integer, Task> taskList = new HashMap<>();
    public Subtask(String nameTask, String taskDescription, int id) {
        super(nameTask, taskDescription, id);

    }
}
