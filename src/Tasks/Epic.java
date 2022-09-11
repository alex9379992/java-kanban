package Tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;

public class Epic extends Task {
    private final HashMap<Integer, Subtask> subtaskData = new HashMap<>();
    private LocalDateTime endTime;

    public Epic(String name, String description, int id) {
        super(name, description, id);
        this.type = TaskType.EPIC;
        this.startTime = LocalDateTime.now();
        this.duration = Duration.ofMinutes(0);
    }

    @Override
    public String toString() {
        return "Имя сложной задачи - " + name +
                ", Краткое описание - " + description +
                ", Индекс - " + id +
                ", Колличество подзадач - " + subtaskData.size() +
                ", Статус задачи - " + status + ";";
    }

    public HashMap<Integer, Subtask> getSubtaskData() {
        return subtaskData;
    }

    public LocalDateTime getStartTame() {
        return startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setStartTame(LocalDateTime startTame) {
        this.startTime = startTame;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }


    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }
}