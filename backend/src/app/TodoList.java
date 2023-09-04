package app;

import handlers.FileHandler;
import handlers.FilterTask;
import handlers.TaskHandler;
import models.Task;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class TodoList {
    public static Map<Integer, Task> tasks = new LinkedHashMap<>();
    private final TaskHandler taskHandler = new TaskHandler();
    private final FileHandler fileHandler = new FileHandler();
    private final FilterTask filterTask = new FilterTask();
    private boolean isApplicationRunning = true;

    public void displayOptions() {
        System.out.println();
        System.out.println("Escolha a opção que desaja realizar: ");
        System.out.println("Digite 0 para CANCELAR.");
        System.out.println("Digite 1 para CRIAR UMA TAREFA.");
        System.out.println("Digite 2 para ALTERAR UMA TAREFA.");
        System.out.println("Digite 3 para DELETAR UMA TAREFA.");
        System.out.println("Digite 4 para MOSTRAR TODAS AS TAREFAS POR DATA.");
        System.out.println("Digite 5 para MOSTRAR TODAS AS TAREFAS POR NIVEL DE PRIORIDADE.");
        System.out.println("Digite 6 para MOSTRAR TODAS AS TAREFAS POR STATUS.");
        System.out.println("Digite 7 para MOSTRAR TODAS AS TAREFAS POR CATEGORIA.");
        System.out.println("Digite 8 para MOSTAR O NÚMERO DE TAREFAS POR STATUS.");
    }

    public void executeApplication() {
        while (isApplicationRunning) {
            fileHandler.readTasks(TodoList.tasks, taskHandler);
            filterTask.displayResults(TodoList.tasks);

            displayOptions();
            Scanner inputReader = new Scanner(System.in);
            String action = inputReader.nextLine();

            switch (action) {
                case "0":
                    System.out.println("Cancelando operação...");
                    isApplicationRunning = false;
                    break;
                case "1":
                    taskHandler.readUserInputAndCreateTask(TodoList.tasks);
                    break;
                case "2":
                    taskHandler.readUserInputAndUpdateTask(TodoList.tasks);
                    break;
                case "3":
                    taskHandler.readUserInputAndDeleteTask(TodoList.tasks);
                    break;
                case "4":
                    taskHandler.readUserInputAndFilterByDueDate(TodoList.tasks);
                    break;
                case "5":
                    taskHandler.readUserInputAndFilterByPriorityLevel(TodoList.tasks);
                    break;
                case "6":
                    taskHandler.readUserInputAndFilterByStatus(TodoList.tasks);
                    break;
                case "7":
                    taskHandler.readUserInputAndFilterByCategory(TodoList.tasks);
                    break;
                case "8":
                    taskHandler.readUserInputAndCountByStatus(TodoList.tasks);
                    break;
                default:
                    System.out.println("A opção escolhida não é válida. Selecione outra.");
                    break;
            }

            fileHandler.saveTasks(TodoList.tasks);
        }
    }

}
