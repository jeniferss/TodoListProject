package app;

import common.UserInput;
import handlers.FileHandler;
import handlers.TaskHandler;
import models.Task;

import java.util.LinkedHashMap;
import java.util.Map;

public class TodoList {
    public static Map<Integer, Task> tasks = new LinkedHashMap<>();

    private final UserInput userInput = new UserInput();
    private final TaskHandler taskHandler = new TaskHandler();
    private final FileHandler fileHandler = new FileHandler();
    private boolean isApplicationRunning = true;

    public String menuText() {
        return String.join(
                System.getProperty("line.separator"),
                "Escolha a opção que desaja realizar: ",
                "Digite 0 para CANCELAR.",
                "Digite 1 para CRIAR UMA TAREFA.",
                "Digite 2 para ALTERAR UMA TAREFA.",
                "Digite 3 para DELETAR UMA TAREFA.",
                "Digite 4 para MOSTRAR TODAS AS TAREFAS POR DATA.",
                "Digite 5 para MOSTRAR TODAS AS TAREFAS POR NIVEL DE PRIORIDADE.",
                "Digite 6 para MOSTRAR TODAS AS TAREFAS POR STATUS.",
                "Digite 7 para MOSTRAR TODAS AS TAREFAS POR CATEGORIA.",
                "Digite 8 para MOSTAR O NÚMERO DE TAREFAS POR STATUS."
        );
    }

    public void executeApplication() {
        while (isApplicationRunning) {
            fileHandler.readTasks(TodoList.tasks, taskHandler);

            String action = userInput.read(menuText());
            switch (action) {
                case "0":
                    System.out.println("Cancelando operação...");
                    isApplicationRunning = false;
                    break;
                case "1":
                    try {
                        taskHandler.readUserInputAndCreateTask(TodoList.tasks);
                    } catch (Exception error) {
                        System.out.println(error.getMessage());
                    }
                    break;
                case "2":
                    try {
                        taskHandler.readUserInputAndUpdateTask(TodoList.tasks);
                    } catch (Exception error) {
                        System.out.println(error.getMessage());
                    }
                    break;
                case "3":
                    try {
                        taskHandler.readUserInputAndDeleteTask(TodoList.tasks);
                    } catch (Exception error) {
                        System.out.println(error.getMessage());
                    }
                    break;
                case "4":
                    try {
                        taskHandler.readUserInputAndFilterByDueDate(TodoList.tasks);
                    } catch (Exception error) {
                        System.out.println(error.getMessage());
                    }
                    break;
                case "5":
                    try {
                        taskHandler.readUserInputAndFilterByPriorityLevel(TodoList.tasks);
                    } catch (Exception error) {
                        System.out.println(error.getMessage());
                    }
                    break;
                case "6":
                    try {
                        taskHandler.readUserInputAndFilterByStatus(TodoList.tasks);
                    } catch (Exception error) {
                        System.out.println(error.getMessage());
                    }
                    break;
                case "7":
                    try {
                        taskHandler.readUserInputAndFilterByCategory(TodoList.tasks);
                    } catch (Exception error) {
                        System.out.println(error.getMessage());
                    }
                    break;
                case "8":
                    try {
                        taskHandler.readUserInputAndCountByStatus(TodoList.tasks);
                    } catch (Exception error) {
                        System.out.println(error.getMessage());
                    }
                    break;
                default:
                    System.out.println("A opção escolhida não é válida. Selecione outra.");
                    break;
            }

            fileHandler.saveTasks(TodoList.tasks);
        }
    }

}
