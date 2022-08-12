package Tasks;

import java.util.Objects;

public class Task {
    protected String name;
    protected String description;
    protected Integer id;
    protected Status status;
    protected TaskType type;

    public Task(String name, String description, int id) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = Status.NEW;
        this.type = TaskType.TASK;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) && Objects.equals(description, task.description) && Objects.equals(id, task.id) && Objects.equals(status, task.status);
    }



    @Override
    public String toString() {
        return  "Имя задачи - " + name +
                ", Краткое описание - " + description +
                ", Индекс - " + id +
                ", Статус задачи - " + status + ";";
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public TaskType getType() {
        return type;
    }
}

