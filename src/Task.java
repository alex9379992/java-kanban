import java.util.Objects;

public class Task {
    String nameTask;
    String taskDescription;
    int id;
    boolean isNew;
    boolean isInProgress;
    boolean isDone;

    public Task(String nameTask, String taskDescription, int id) {
        this.nameTask = nameTask;
        this.taskDescription = taskDescription;
        this.id = id;
        isNew =true;
        isInProgress = false;
        isDone = false;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && isNew == task.isNew && isInProgress == task.isInProgress && isDone == task.isDone && Objects.equals(nameTask, task.nameTask) && Objects.equals(taskDescription, task.taskDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameTask, taskDescription, id, isNew, isInProgress, isDone);
    }

    @Override
    public String toString() {
        return "Task{" +
                "nameTask='" + nameTask + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", id=" + id +
                ", isNew=" + isNew +
                ", isInProgress=" + isInProgress +
                ", isDone=" + isDone +
                '}';
    }
}

