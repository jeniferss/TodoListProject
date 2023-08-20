package handlers;

import app.TodoList;
import validators.UserInput;

import java.util.Scanner;

public class DeleteTask {
    public static void execute() {
        while (true) {
            System.out.println();
            System.out.println("Digite o id da tarefa que deseja excluir: ");
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
            TodoList.tasks.remove(taskId);

            System.out.println("Tarefa deletada com sucesso!");
        }

    }

}
