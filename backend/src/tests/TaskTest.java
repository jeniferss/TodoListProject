package tests;

import handlers.TaskHandler;
import models.Task;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class TaskTest {

    private final Map<Integer, Task> tasks = new LinkedHashMap<Integer, Task>();
    private final TaskHandler handler = new TaskHandler();
    private Task task;

    public void methodCreateTest() {
        String testStatus = "SUCESSO";

        System.out.println("-----------------------------");
        System.out.println("Teste para Classe 'Task' e Método 'create'");
        System.out.println();

        try {
            /* GIVEN */
            int id = 1;
            String title = "Título Tarefa de Teste";
            String description = "Descrição Tarefa de Teste";
            LocalDate dueDate = LocalDate.of(2023, 9, 13);
            String status = "FEITO";
            String category = "ESTUDOS";
            int priorityLevel = 5;

            /* GIVEN */
            task = new TaskHandler().create(id, title, description, dueDate, status, category, priorityLevel);

            /* THEN */
            if (!(task.getId() == id)) {
                throw new Exception();
            }
            if (!(Objects.equals(task.getTitle(), title))) {
                throw new Exception();
            }
            if (!(Objects.equals(task.getDescription(), description))) {
                throw new Exception();
            }
            if (!(task.getDueDate() == dueDate)) {
                throw new Exception();
            }
            if (!(Objects.equals(task.getStatus(), status))) {
                throw new Exception();
            }
            if (!(Objects.equals(task.getCategory(), category))) {
                throw new Exception();
            }
            if (!(task.getPriorityLevel() == priorityLevel)) {
                throw new Exception();
            }

        } catch (Exception error) {
            testStatus = "FALHOU";
        }

        System.out.println("Status do Teste: " + testStatus + " !");
        System.out.println("-----------------------------");
        System.out.println();

    }

    public void methodInsertTest() {
        String testStatus = "SUCESSO";

        System.out.println("-----------------------------");
        System.out.println("Teste para Classe 'Task' e Método 'insert'");
        System.out.println();

        try {
            /* GIVEN */

            /* WHEN */
            handler.insert(task, tasks);

            /* THEN */
            if (!(tasks.size() == 1)) {
                throw new Exception();
            }

        } catch (Exception error) {
            testStatus = "FALHOU";
        }

        System.out.println("Status do Teste: " + testStatus + " !");
        System.out.println("-----------------------------");
        System.out.println();

    }

    public void methodUpdateTest() {
        String testStatus = "SUCESSO";

        System.out.println("-----------------------------");
        System.out.println("Teste para Classe 'Task' e Método 'update'");
        System.out.println();

        try {

            /* GIVEN */
            int id = 2;
            String title = "Título Tarefa de Teste Modificado";
            String description = "Descrição Tarefa de Teste";
            LocalDate dueDate = LocalDate.of(2023, 9, 13);
            String status = "FEITO";
            String category = "ESTUDOS";
            int priorityLevel = 5;

            /* WHEN */
            Task updatedTask = handler.create(id, title, description, dueDate, status, category, priorityLevel);
            handler.update(1, updatedTask, tasks);

            /* THEN */
            if (!(Objects.equals(task.getDescription(), description))) {
                throw new Exception();
            }

        } catch (Exception error) {
            testStatus = "FALHOU";
        }

        System.out.println("Status do Teste: " + testStatus + " !");
        System.out.println("-----------------------------");
        System.out.println();

    }

    public void methodDeleteTest() {
        String testStatus = "SUCESSO";

        System.out.println("-----------------------------");
        System.out.println("Teste para Classe 'Task' e Método 'delete'");
        System.out.println();

        try {
            /* GIVEN */
            int id = 1;

            /* WHEN */
            handler.delete(id, tasks);

            /* THEN */
            if (!(tasks.isEmpty())) {
                throw new Exception();
            }

        } catch (Exception error) {
            testStatus = "FALHOU";
        }

        System.out.println("Status do Teste: " + testStatus + " !");
        System.out.println("-----------------------------");
        System.out.println();

    }

    public void run() {
        methodCreateTest();
        methodInsertTest();
        methodUpdateTest();
        methodDeleteTest();
    }

}
