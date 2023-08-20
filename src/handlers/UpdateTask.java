package handlers;

import app.TodoList;
import models.Task;
import validators.UserInput;

import java.util.Scanner;

public class UpdateTask {

    public static void displayOptions() {
        System.out.println();
        System.out.println("Escolha a opção que desaja realizar: ");
        System.out.println("Digite 0 para CANCELAR.");
        System.out.println("Digite 1 para EDITAR O NOME DA TAREFA.");
        System.out.println("Digite 2 para EDITAR A DESCRIÇÃO DA TAREFA.");
        System.out.println("Digite 3 para EDITAR A DATA DE EXPIRAÇÃO DA TAREFA.");
        System.out.println("Digite 4 para EDITAR O STATUS DA TAREFA.");
        System.out.println("Digite 5 para EDITAR A CATEGORIA DA TAREFA.");
        System.out.println("Digite 6 para EDITAR O NÍVEL DE PRIORIDADE DA TAREFA.");

    }

    public static void execute() {
        while (true) {
            System.out.println();
            System.out.println("Digite o id da tarefa que deseja alterar: ");
            System.out.println("Digite 0 para CANCELAR.");
            Scanner input = new Scanner(System.in);

            String userInput = input.nextLine();

            if (userInput.equals("0")) {
                System.out.println("Cancelando operação...");
                break;
            }

            if (!UserInput.isTaskIdValid(userInput)) {
                continue;
            }

            int taskId = Integer.parseInt(userInput);
            Task task = TodoList.tasks.get(taskId);
            System.out.println(task);

            displayOptions();
            Scanner optionInput = new Scanner(System.in);
            String optionUserInput = optionInput.nextLine();

            if (optionUserInput.equals("0")) {
                System.out.println("Cancelando operação...");
                break;
            }

            System.out.println();
            System.out.println("Digite a nova informação da tarefa: ");
            Scanner updateInput = new Scanner(System.in);
            String updateUserInput = updateInput.nextLine();

            if (updateUserInput.equals("0")) {
                System.out.println("Cancelando operação...");
                break;
            }

            String dateFormat = "dd-MM-yyyy";

            switch (optionUserInput) {
                case "1":
                    task.setTitle(updateUserInput);
                    break;
                case "2":
                    task.setDescription(updateUserInput);
                    break;
                case "3":
                    if (UserInput.isDueDateValid(dateFormat, updateUserInput, true)) {
                        task.setDueDate(UserInput.parseAsDate(dateFormat, updateUserInput));
                    }
                    break;
                case "4":
                    if (UserInput.isStatusValid(updateUserInput)) {
                        task.setStatus(updateUserInput);
                    }
                    break;
                case "5":
                    task.setCategory(updateUserInput);
                    break;
                case "6":
                    if (UserInput.isPriorityLevelValid(updateUserInput)) {
                        task.setPriorityLevel(Integer.parseInt(updateUserInput));
                    }
                    break;

            }

            int indexPosition = SortTask.getIndexToInsert(task.getPriorityLevel(), task.getDueDate());
            SortTask.insertObjectAndReorder(indexPosition, task);
        }
    }
}
