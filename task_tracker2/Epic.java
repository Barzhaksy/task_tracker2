package task_tracker;


import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> subtaskIds = new ArrayList<>();

    public Epic(String title, String description) {
        super(title, description);
        setStatus(TaskStatus.NEW);
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public void addSubtaskId(int id) {
        if (!subtaskIds.contains(id)) subtaskIds.add(id);
    }

    public void removeSubtaskId(int id) {
        subtaskIds.remove(Integer.valueOf(id));
    }

    @Override
    public String toString() {
        return "Эпик ID: " + getId() + ", Заголовок: '" + getTitle() + ", Описание: " + getDescription() +
                "', Статус: " + getStatus() + ", Подзадача: " + subtaskIds + " --- |";
    }
}