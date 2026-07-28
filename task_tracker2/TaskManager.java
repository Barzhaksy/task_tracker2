package task_tracker;

import java.util.List;

public interface TaskManager {
    void createTask(Task task);
    List<Task> getAllTasks();
    Task getTask(int id);
    void updateTask(Task task);
    void deleteTask(int id);

    void createEpic(Epic epic);
    List<Epic> getAllEpics();
    Epic getEpic(int id);
    void updateEpic(Epic epic);
    void deleteEpic(int id);

    void createSubtask(Subtask subtask);
    List<Subtask> getAllSubtasks();
    Subtask getSubtask(int id);
    void updateSubtask(Subtask subtask);
    void deleteSubtask(int id);
    List<Subtask> getEpicSubtasks(int epicId);

    List<Task> getHistory();
}
