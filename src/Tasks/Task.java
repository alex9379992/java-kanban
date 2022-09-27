package Tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    protected String name;
    protected String description;
    protected Integer id;
    protected Status status;
    protected TaskType type;
    protected LocalDateTime startTime;
    protected Duration duration;


    public Task(String name, String description, int id, LocalDateTime dataTime, long minutes) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = Status.NEW;
        this.type = TaskType.TASK;
        setStartTime(dataTime, minutes);
    }

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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    public void setStartTime(LocalDateTime dateTime, long minutes) {
        this.startTime = dateTime;
        this.duration = Duration.ofMinutes(minutes);
    }
}