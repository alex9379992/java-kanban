import java.util.Objects;


public class Subtask extends Task{

    private final int idEpic;

    public Subtask(String name, String taskDescription, int id, int idEpic) {
        super(name, taskDescription, id);
        this.idEpic = idEpic;
    }

    @Override
    public String toString() {
        return "Имя подзадачи = " + name +
                ", Краткое описание = " + description + '\'' +
                ", Номер = " + id +
                ", Статус задачи - " + status + ";";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return idEpic == subtask.idEpic;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idEpic);
    }
}
