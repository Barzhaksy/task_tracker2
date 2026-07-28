package task_tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager{

    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private Map<Integer, Subtask> subtasks = new HashMap<>();

    private final HistoryManager historyManager = Managers.getDefaultHistory();

    private int nextId = 1;


    private int generateId() {
        return nextId++;
    }

    @Override
    public void createTask(Task task) {
        int id = generateId();
        task.setId(id);
        tasks.put(id, task);
    }
    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }
    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }
    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }
    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
    }
    @Override
    public void createEpic(Epic epic) {
        int id = generateId();
        epic.setId(id);
        epics.put(id, epic);
    }
    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }
    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }
    @Override
    public void updateEpic(Epic epic) {
        Epic stored = epics.get(epic.getId());
        stored.setTitle(epic.getTitle());
        stored.setDescription(epic.getDescription());
        recalcEpicStatus(stored.getId());
    }
    @Override
    public void deleteEpic(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (int subId : epic.getSubtaskIds()) {
                subtasks.remove(subId);
            }
        }
    }
    @Override
    public void createSubtask(Subtask subtask) {
        int epicId = subtask.getEpicId();

        if (!epics.containsKey(epicId)) return;

        int id = generateId();
        subtask.setId(id);
        subtasks.put(id, subtask);

        epics.get(epicId).addSubtaskId(id);
        recalcEpicStatus(epicId);
    }
    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }
    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            historyManager.add(subtask);
        }
        return subtask;
    }
    @Override
    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        recalcEpicStatus(subtask.getEpicId());
    }
    @Override
    public ArrayList<Subtask> getEpicSubtasks(int epicId) {
        ArrayList<Subtask> result = new ArrayList<>();
        Epic epic = epics.get(epicId);
        if (epic == null) return result;

        for (int id : epic.getSubtaskIds()) {
            result.add(subtasks.get(id));
        }
        return result;
    }
    @Override
    public void deleteSubtask(int id) {
        Subtask sub = subtasks.remove(id);
        if (sub != null) {
            int epicId = sub.getEpicId();
            Epic epic = epics.get(epicId);
            if (epic != null) {
                epic.removeSubtaskId(id);
                recalcEpicStatus(epicId);
            }
        }
    }
    private void recalcEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) return;

        ArrayList<Integer> subIds = epic.getSubtaskIds();

        if (subIds.isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }

        boolean allNew = true;
        boolean allDone = true;

        for (int id : subIds) {
            Subtask sub = subtasks.get(id);

            if (sub == null) continue;

            TaskStatus st = sub.getStatus();

            if (!st.equals(TaskStatus.NEW)) allNew = false;
            if (!st.equals(TaskStatus.DONE)) allDone = false;
        }

        if (allNew) epic.setStatus(TaskStatus.NEW);
        else if (allDone) epic.setStatus(TaskStatus.DONE);
        else epic.setStatus(TaskStatus.IN_PROGRESS);
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}
