package app;

import handlers.*;
import models.Task;

import java.util.*;

public class TodoList {
    public static Map<Integer, Task> tasks = new LinkedHashMap<>();
    private static boolean isApplicationRunning = true;

    public static void displayOptions() {
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

    public static void executeApplication() {
        while (isApplicationRunning) {
            FileHandler.readTasks();
            System.out.println(tasks);

            displayOptions();
            Scanner input = new Scanner(System.in);

            String userInput = input.nextLine();

            List<String> allowedOptions = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

            if (!allowedOptions.contains(userInput)) {
                System.out.println("A opção escolhida não é válida. Selecione outra.");
                continue;
            }

            String message;
            Map<Integer, Task> formattedTasks;

            switch (userInput) {
                case "0":
                    System.out.println("Cancelando operação");
                    isApplicationRunning = false;
                    break;
                case "1":
                    CreateTask.displayOptions();
                    CreateTask.execute();
                    break;
                case "2":
                    UpdateTask.execute();
                    break;
                case "3":
                    DeleteTask.execute();
                    break;
                case "4":
                    message = "Digite a data de expiração (ex. dd-MM-yyyy): ";
                    String dueDate = readUserInput(message);
                    formattedTasks = FilterTask.byDueDate(dueDate);
                    FilterTask.displayResults(formattedTasks);
                    break;
                case "5":
                    message = "Digite o nível de prioridade: ";
                    String priorityLevel = readUserInput(message);
                    formattedTasks = FilterTask.byPriorityLevel(priorityLevel);
                    FilterTask.displayResults(formattedTasks);
                    break;
                case "6":
                    message = "Digite o status: ";
                    String status = readUserInput(message);
                    formattedTasks = FilterTask.byStatus(status);
                    FilterTask.displayResults(formattedTasks);
                    break;
                case "7":
                    message = "Digite a categoria: ";
                    String category = readUserInput(message);
                    formattedTasks = FilterTask.byCategory(category);
                    FilterTask.displayResults(formattedTasks);
                    break;
                case "8":
                    message = "Digite o status: ";
                    String statusForQuantity = readUserInput(message);
                    formattedTasks = FilterTask.byStatus(statusForQuantity);
                    System.out.println(formattedTasks.size());
                    break;
            }

            FileHandler.saveTasks();

        }

    }

    public static String readUserInput(String message) {
        System.out.println(message);
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }
}
