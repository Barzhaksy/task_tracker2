package task_tracker;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        InMemoryTaskManager manager = new InMemoryTaskManager();
        Scanner scanner = new Scanner(System.in);
        Task t1 = new Task("Переезд", "Упаковать вещи");
        manager.createTask(t1);

        Epic epic1 = new Epic("Ремонт", "Сделать ремонт в комнате");
        manager.createEpic(epic1);

        Subtask sub1 = new Subtask("Покраска", "Купить краску", epic1.getId());
        manager.createSubtask(sub1);

        while (true) {
            System.out.println("Меню");
            System.out.print("1 — Создать задачу; ");
            System.out.print("2 — Создать эпик; ");
            System.out.print("3 — Создать подзадачу; ");
            System.out.print("4 — Показать все задачи; ");
            System.out.print("5 — Показать все эпики; ");
            System.out.println("6 — Показать все подзадачи; ");

            System.out.print("7 - Удалить задачу; ");
            System.out.print("8 - Удалить эпик; ");
            System.out.println("9 - Удалить подзадачу; ");

            System.out.print("10 — Изменить статус задачи или подзадачи; ");
            System.out.print("11 — Редактировать задачу; ");
            System.out.print("12 — Редактировать подзадачу; ");
            System.out.println("13 — Редактировать эпик; ");

            System.out.println("0 — Выход");
            System.out.print("Выбор: ");

            int command = Integer.parseInt(scanner.nextLine());

            switch (command) {

                case 1:
                    System.out.print("Название задачи: ");
                    String tTitle = scanner.nextLine();
                    System.out.print("Описание задачи: ");
                    String tDesc = scanner.nextLine();
                    Task task = new Task(tTitle, tDesc);
                    manager.createTask(task);
                    System.out.println("Задача создана! ID: " + task.getId());
                    break;

                case 2:
                    System.out.print("Название эпика: ");
                    String eTitle = scanner.nextLine();
                    System.out.print("Описание эпика: ");
                    String eDesc = scanner.nextLine();
                    Epic epic = new Epic(eTitle, eDesc);
                    manager.createEpic(epic);
                    System.out.println("Эпик создан! ID: " + epic.getId());
                    break;

                case 3:
                    System.out.print("Название подзадачи: ");
                    String sTitle = scanner.nextLine();
                    System.out.print("Описание подзадачи: ");
                    String sDesc = scanner.nextLine();
                    System.out.print("ID эпика: ");
                    int epicId = Integer.parseInt(scanner.nextLine());
                    Subtask sub = new Subtask(sTitle, sDesc, epicId);
                    manager.createSubtask(sub);
                    System.out.println("Подзадача создана! ID: " + sub.getId());
                    break;

                case 4:
                    System.out.println("Все задачи:");
                    System.out.println(manager.getAllTasks());
                    break;

                case 5:
                    System.out.println("Все эпики:");
                    System.out.println(manager.getAllEpics());
                    break;

                case 6:
                    System.out.println("Все подзадачи:");
                    System.out.println(manager.getAllSubtasks());
                    break;
                case 7:
                    System.out.print("Введите ID задачи для удаления: ");
                    int taskId = Integer.parseInt(scanner.nextLine());
                    manager.deleteTask(taskId);
                    System.out.println("Задача удалена!");
                    break;
                case 8:
                    System.out.print("Введите ID эпика для удаления: ");
                    int deleteEpicId = Integer.parseInt(scanner.nextLine());
                    manager.deleteEpic(deleteEpicId);
                    System.out.println("Эпик и его подзадачи удалены!");
                    break;
                case 9:
                    System.out.print("Введите ID подзадачи для удаления: ");
                    int subId = Integer.parseInt(scanner.nextLine());
                    manager.deleteSubtask(subId);
                    System.out.println("Подзадача удалена!");
                    break;
                case 10:
                    System.out.print("Введите ID задачи или подзадачи: ");
                    int idToUpdate = Integer.parseInt(scanner.nextLine());

                    Task found = manager.getTask(idToUpdate);
                    Subtask foundSub = manager.getSubtask(idToUpdate);


                    if (found != null) {
                        System.out.println("Выберите статус:");
                        System.out.println("1 — NEW");
                        System.out.println("2 — IN_PROGRESS");
                        System.out.println("3 — DONE");

                        String statusChoice = scanner.nextLine();

                        if (statusChoice.equals("1")) found.setStatus(TaskStatus.NEW);
                        else if (statusChoice.equals("2")) found.setStatus(TaskStatus.IN_PROGRESS);
                        else if (statusChoice.equals("3")) found.setStatus(TaskStatus.DONE);

                        manager.updateTask(found);
                        System.out.println("Статус задачи обновлён!");
                    } else if (foundSub != null) {
                        System.out.println("Выберите статус:");
                        System.out.println("1 — NEW");
                        System.out.println("2 — IN_PROGRESS");
                        System.out.println("3 — DONE");

                        String statusChoice = scanner.nextLine();
                        if (statusChoice.equals("1")) foundSub.setStatus(TaskStatus.NEW);
                        else if (statusChoice.equals("2")) foundSub.setStatus(TaskStatus.IN_PROGRESS);
                        else if (statusChoice.equals("3")) foundSub.setStatus(TaskStatus.DONE);

                        manager.updateSubtask(foundSub);
                        System.out.println("Статус подзадачи обновлён!");
                    } else {
                        System.out.println("Задача с таким ID не найдена!");
                    }

                    break;

                case 11:
                    System.out.print("Введите ID задачи для редактирования: ");
                    int editTaskId = Integer.parseInt(scanner.nextLine());

                    Task editTask = manager.getTask(editTaskId);

                    if (editTask == null) {
                        System.out.println("Задача с таким ID не найдена!");
                        break;
                    }

                    System.out.print("Новое название (оставьте пустым чтобы не менять): ");
                    String newTitleT = scanner.nextLine();
                    if (!newTitleT.isEmpty()) editTask.setTitle(newTitleT);

                    System.out.print("Новое описание (оставьте пустым чтобы не менять): ");
                    String newDescT = scanner.nextLine();
                    if (!newDescT.isEmpty()) editTask.setDescription(newDescT);

                    manager.updateTask(editTask);
                    System.out.println("Задача обновлена!");
                    break;

                case 12:
                    System.out.print("Введите ID подзадачи для редактирования: ");
                    int editSubId = Integer.parseInt(scanner.nextLine());

                    Subtask editSub = manager.getSubtask(editSubId);

                    if (editSub == null) {
                        System.out.println("Подзадача с таким ID не найдена!");
                        break;
                    }

                    System.out.print("Новое название (оставьте пустым чтобы не менять): ");
                    String newTitleS = scanner.nextLine();
                    if (!newTitleS.isEmpty()) editSub.setTitle(newTitleS);

                    System.out.print("Новое описание (оставьте пустым чтобы не менять): ");
                    String newDescS = scanner.nextLine();
                    if (!newDescS.isEmpty()) editSub.setDescription(newDescS);

                    manager.updateSubtask(editSub);
                    System.out.println("Подзадача обновлена!");
                    break;

                case 13:
                    System.out.print("Введите ID эпика для редактирования: ");
                    int editEpicId = Integer.parseInt(scanner.nextLine());

                    Epic editEpic = manager.getEpic(editEpicId);

                    if (editEpic == null) {
                        System.out.println("Эпик с таким ID не найден!");
                        break;
                    }

                    System.out.print("Новое название (оставьте пустым чтобы не менять): ");
                    String newTitleE = scanner.nextLine();
                    if (!newTitleE.isEmpty()) editEpic.setTitle(newTitleE);

                    System.out.print("Новое описание (оставьте пустым чтобы не менять): ");
                    String newDescE = scanner.nextLine();
                    if (!newDescE.isEmpty()) editEpic.setDescription(newDescE);

                    manager.updateEpic(editEpic);
                    System.out.println("Эпик обновлён!");
                    break;


                case 0:
                    System.out.println("Выход.");
                    return;

                default:
                    System.out.println("Неизвестная команда!");
            }
        }
    }
}

