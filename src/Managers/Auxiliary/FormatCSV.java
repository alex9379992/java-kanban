package Managers.Auxiliary;
import Interfaces.HistoryManager;
import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;
import Tasks.TaskType;
import java.util.ArrayList;
import java.util.List;
public class FormatCSV {

    public static String recordingFields(){
        return "id,type,name,status,description,epic\n";
    }

    public static String fromHistoryToString(HistoryManager manager){
        List<Task> history = manager.getHistory();
        List<String> idTasks = new ArrayList<>();
        for (Task task : history) {
            int id = task.getId();
            idTasks.add(String.valueOf(id));
        }
        return String.join(",", idTasks);
    }

    public static List<Integer> historyFromString(String value) {
        String[] values = value.split(",");
        List<Integer> historyList = new ArrayList<>();
        for (String number : values) {
            historyList.add(Integer.parseInt(number));
        }
        return historyList;
    }

    public static Task fromString(String value){
        String[] values = value.split(",");
        if(values[1].equals("EPIC")) {
            return new Epic(values[2], values[3], Integer.parseInt(values[0]));
        } else if (values[1].equals("SUBTASK")) {
            return new Subtask(values[2], values[4], Integer.parseInt(values[0]), Integer.parseInt(values[5]));

        } else {
            return new Task(values[2], values[4], Integer.parseInt(values[0]));
        }
    }

    public static String toStringCSV(Task task) {
        return  task.getId() + ","
                + TaskType.TASK + ","
                + task.getName() + ","
                + task.getStatus() + ","
                + task.getDescription()
                + "\n";
    }

    public static String toStringCSV(Subtask subtask) {
        return   subtask.getId() + ","
                + TaskType.SUBTASK + ","
                + subtask.getName() + ","
                + subtask.getStatus() + ","
                + subtask.getDescription() + ","
                + subtask.getIdEpic()
                + "\n";
    }

    public static String toStringCSV(Epic epic) {
        return   epic.getId() + ","
                + TaskType.EPIC + ","
                + epic.getName() + ","
                + epic.getStatus() + ","
                + epic.getDescription() + ","
                + "\n";
    }
}