package handlers;

import app.TodoList;
import models.Task;
import validators.UserInput;

import java.time.LocalDate;
import java.util.*;

public class CreateTask {

    public static void displayOptions() {
        System.out.println();
        System.out.println("Para CRIAR uma nova tarefa, siga o modelo abaixo e aperte ENTER:");
        System.out.println("Nome,Descrição,Data de Expiração (dd-MM-AAAA),Status (FAZER, FAZENDO, FEITO),Categoria,Nível de Prioridade (1-5)");
        System.out.println("Digite 0 para CANCELAR.");

    }

    public static void recreate(String taskAsString) {
        String[] taskString = taskAsString.split(",");

        String id = taskString[0];
        String title = taskString[1];
        String description = taskString[2];
        String dueDate = taskString[3];
        String status = taskString[4].toUpperCase();
        String category = taskString[5];
        String priorityLevel = taskString[6];

        Task task = Task.createTask(Integer.parseInt(id), title, description, LocalDate.parse(dueDate), status, category, Integer.parseInt(priorityLevel));

        int indexPosition = SortTask.getIndexToInsert(task.getPriorityLevel(), task.getDueDate());
        SortTask.insertObjectAndReorder(indexPosition, task);
    }

    public static void execute() {
        while (true) {
            System.out.println();
            System.out.println("Digite as informações da tarefa: ");
            Scanner input = new Scanner(System.in);

            String userInput = input.nextLine();

            if (userInput.equals("0")) {
                System.out.println("Cancelando operação...");
                break;
            }

            String[] taskString = userInput.split(",");

            if (!UserInput.isTaskInfoValid(taskString)) {
                continue;
            }

            Set<Integer> taskIds = TodoList.tasks.keySet();
            Integer maxTaskId;

            if (taskIds.isEmpty()) {
                maxTaskId = 0;
            } else {
                maxTaskId = Collections.max(taskIds);
            }

            int id = maxTaskId + 1;
            String title = taskString[0];
            String description = taskString[1];
            String dueDate = taskString[2];
            String status = taskString[3].toUpperCase();
            String category = taskString[4];
            String priorityLevel = taskString[5];

            String dateFormat = "dd-MM-yyyy";

            if (!UserInput.isDueDateValid(dateFormat, dueDate, true)) {
                continue;
            }

            if (!UserInput.isStatusValid(status)) {
                continue;
            }

            if (!UserInput.isPriorityLevelValid(priorityLevel)) {
                continue;
            }

            Task task = Task.createTask(id, title, description, UserInput.parseAsDate(dateFormat, dueDate), status, category, Integer.parseInt(priorityLevel));
            System.out.println("Tarefa criada com sucesso!");

            int indexPosition = SortTask.getIndexToInsert(task.getPriorityLevel(), task.getDueDate());
            SortTask.insertObjectAndReorder(indexPosition, task);
        }

    }
}
