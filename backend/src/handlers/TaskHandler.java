package handlers;

import models.Task;
import validators.TaskValidator;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;

public class TaskHandler {
    private final String dateFormat = "dd-MM-yyyy";
    private final TaskValidator validator = new TaskValidator();
    private final SortTask sorter = new SortTask();
    private final FilterTask filterer = new FilterTask();

    public int generateTaskId(Map<Integer, Task> tasks) {
        return tasks.keySet().isEmpty() ? 1 : Collections.max(tasks.keySet()) + 1;
    }

    public void readUserInputAndCreateTask(Map<Integer, Task> tasks) {

        Scanner inputReader = new Scanner(System.in);

        Integer id = generateTaskId(tasks);

        System.out.println("Digite o Título: ");
        String title = inputReader.nextLine().trim();

        System.out.println("Digite a Descrição: ");
        String description = inputReader.nextLine().trim();

        System.out.println("Digite a Data de Expiração (dd-MM-AAAA): ");
        String dueDateString = inputReader.nextLine().trim();
        if (!validator.isStringDateFormatValid(dueDateString, dateFormat)) {
            System.out.println("A data informada não possui formato válido.");
            return;
        }
        LocalDate dueDate = validator.parseAsDate(dateFormat, dueDateString);

        System.out.println("Digite o Status (FAZER, FAZENDO, FEITO): ");
        String status = inputReader.nextLine().trim();
        if (!validator.isStatusValid(status)) {
            System.out.println("O status informado não é válido.");
            return;
        }

        System.out.println("Digite a Categoria (ESTUDOS, TRABALHO, LAZER, SAUDE): ");
        String category = inputReader.nextLine().trim();
        if (!validator.isCategoryValid(category)) {
            System.out.println("A categoria informada não é válida.");
            return;
        }

        System.out.println("Digite o Nível de Prioridade (1-5): ");
        String priorityLevelString = inputReader.nextLine().trim();
        if (!validator.isAIntString(priorityLevelString)) {
            System.out.println("O nível de prioridade informado não possui formato válido.");
            return;
        }

        Integer priorityLevel = validator.parseStringAsInt(priorityLevelString);
        if (!validator.isPriorityLevelValid(priorityLevel)) {
            System.out.println("O nível de prioridade informado não é válido.");
            return;
        }

        Task task = create(id, title, description, dueDate, status, category, priorityLevel);
        insert(task, tasks);
    }

    public void readUserInputAndUpdateTask(Map<Integer, Task> tasks) {

        Scanner inputReader = new Scanner(System.in);
        Task task = new Task();

        System.out.println("Digite o ID da tarefa que deseja alterar: ");
        String idString = inputReader.nextLine().trim();
        if (!validator.isAIntString(idString)) {
            System.out.println("O ID informado não é válido.");
            return;
        }

        Integer taskId = validator.parseStringAsInt(idString);
        if (!validator.isTaskIdValid(taskId, tasks)) {
            System.out.println("O ID informado não é válido.");
            return;
        }

        System.out.println("Digite o NOVO Título ou 0 para não alterar: ");
        String title = inputReader.nextLine().trim();
        if (!title.equals("0")) {
            task.setTitle(title);
        }

        System.out.println("Digite a NOVA Descrição ou 0 para não alterar: ");
        String description = inputReader.nextLine().trim();
        if (!description.equals("0")) {
            task.setDescription(description);
        }

        System.out.println("Digite a NOVA Data de Expiração (dd-MM-AAAA) ou 0 para não alterar: ");
        String dueDateString = inputReader.nextLine().trim();
        if (!dueDateString.equals("0")) {
            if (!validator.isStringDateFormatValid(dueDateString, dateFormat)) {
                System.out.println("A data informada não possui formato válido.");
                return;
            }
            task.setDueDate(validator.parseAsDate(dateFormat, dueDateString));
        }

        System.out.println("Digite o NOVO Status (FAZER, FAZENDO, FEITO) ou 0 não alterar: ");
        String status = inputReader.nextLine().trim();
        if (!status.equals("0")) {
            if (!validator.isStatusValid(status)) {
                System.out.println("O status informado não é válido.");
                return;
            }
            task.setStatus(status);
        }

        System.out.println("Digite a NOVA Categoria ou 0 para não alterar: ");
        String category = inputReader.nextLine().trim();
        if (!category.equals("0")) {
            if (!validator.isCategoryValid(category)) {
                System.out.println("A categoria informada não é válida.");
                return;
            }
            task.setCategory(category);
        }

        System.out.println("Digite o NOVO Nível de Prioridade (1-5) ou 0 para não alterar: ");
        String priorityLevelString = inputReader.nextLine().trim();
        if (!priorityLevelString.equals("0")) {
            if (!validator.isAIntString(priorityLevelString)) {
                System.out.println("O nível de prioridade informado não possui formato válido.");
                return;
            }

            Integer priorityLevel = validator.parseStringAsInt(priorityLevelString);
            if (!validator.isPriorityLevelValid(priorityLevel)) {
                System.out.println("O nível de prioridade informado não é válido.");
                return;
            }

            task.setPriorityLevel(priorityLevel);
        }

        update(taskId, task, tasks);
    }

    public void readUserInputAndDeleteTask(Map<Integer, Task> tasks) {
        Scanner inputReader = new Scanner(System.in);

        System.out.println("Digite o ID da tarefa que deseja deletar: ");
        String idString = inputReader.nextLine().trim();
        if (!validator.isAIntString(idString)) {
            System.out.println("O ID informado não é válido.");
            return;
        }

        Integer taskId = validator.parseStringAsInt(idString);
        if (!validator.isTaskIdValid(taskId, tasks)) {
            System.out.println("O ID informado não é válido.");
            return;
        }

        delete(taskId, tasks);
    }

    public void readUserInputAndFilterByDueDate(Map<Integer, Task> tasks) {
        Scanner inputReader = new Scanner(System.in);

        System.out.println("Digite a data de expiração (ex. dd-MM-yyyy): ");
        String dueDateString = inputReader.nextLine().trim();
        if (!validator.isStringDateFormatValid(dueDateString, dateFormat)) {
            System.out.println("A data de expiração informado não é válida.");
            return;
        }

        LocalDate dueDate = validator.parseAsDate(dateFormat, dueDateString);
        Map<Integer, Task> formattedTasks = filterer.byDueDate(dueDate, tasks);
        filterer.displayResults(formattedTasks);
    }

    public void readUserInputAndFilterByPriorityLevel(Map<Integer, Task> tasks) {
        Scanner inputReader = new Scanner(System.in);

        System.out.println("Digite o Nível de Prioridade (1-5): ");
        String priorityLevelString = inputReader.nextLine().trim();

        if (!validator.isAIntString(priorityLevelString)) {
            System.out.println("O nível de prioridade informado não possui formato válido.");
            return;
        }

        Integer priorityLevel = validator.parseStringAsInt(priorityLevelString);
        if (!validator.isPriorityLevelValid(priorityLevel)) {
            System.out.println("O nível de prioridade informado não é válido.");
            return;
        }

        Map<Integer, Task> formattedTasks = filterer.byPriorityLevel(priorityLevel, tasks);
        filterer.displayResults(formattedTasks);
    }

    public void readUserInputAndFilterByStatus(Map<Integer, Task> tasks) {
        Scanner inputReader = new Scanner(System.in);

        System.out.println("Digite o Status (FAZER, FAZENDO, FEITO): ");
        String status = inputReader.nextLine().trim();

        if (!validator.isStatusValid(status)) {
            System.out.println("O status informado não é válido.");
            return;
        }

        Map<Integer, Task> formattedTasks = filterer.byStatus(status, tasks);
        filterer.displayResults(formattedTasks);
    }

    public void readUserInputAndFilterByCategory(Map<Integer, Task> tasks) {
        Scanner inputReader = new Scanner(System.in);

        System.out.println("Digite a Categoria (ESTUDOS, TRABALHO, LAZER, SAUDE): ");
        String category = inputReader.nextLine().trim();
        if (!validator.isCategoryValid(category)) {
            System.out.println("A categoria informada não é válida.");
            return;
        }

        Map<Integer, Task> formattedTasks = filterer.byCategory(category, tasks);
        filterer.displayResults(formattedTasks);
    }

    public void readUserInputAndCountByStatus(Map<Integer, Task> tasks) {
        Scanner inputReader = new Scanner(System.in);

        System.out.println("Digite o Status (FAZER, FAZENDO, FEITO): ");
        String status = inputReader.nextLine().trim();

        if (!validator.isStatusValid(status)) {
            System.out.println("O status informado não é válido.");
            return;
        }

        Map<Integer, Task> formattedTasks = filterer.byStatus(status, tasks);
        System.out.println("Quantidade de tarefas: " + formattedTasks.size());
    }

    public Task create(Integer id, String title, String description, LocalDate dueDate, String status, String category, Integer priorityLevel) {
        Task task = new Task();
        task.setId(id);
        task.setTitle(title);
        task.setDescription(description);
        task.setDueDate(dueDate);
        task.setStatus(status);
        task.setCategory(category);
        task.setPriorityLevel(priorityLevel);

        return task;
    }

    public void insert(Task task, Map<Integer, Task> tasks) {
        int indexToInsert = sorter.getIndexToInsert(task.getPriorityLevel(), task.getDueDate(), tasks);
        sorter.insertObjectAndReorder(indexToInsert, task, tasks);
    }

    public void delete(int taskId, Map<Integer, Task> tasks) {
        tasks.remove(taskId);
    }

    public void update(Integer taskId, Task task, Map<Integer, Task> tasks) {
        Task outdatedTask = tasks.get(taskId);

        if (validator.changeAttributeValue(outdatedTask.getTitle(), task.getTitle())) {
            outdatedTask.setTitle(task.getTitle());
        }
        if (validator.changeAttributeValue(outdatedTask.getDescription(), task.getDescription())) {
            outdatedTask.setDescription(task.getDescription());
        }
        if (validator.changeAttributeValue(outdatedTask.getDueDate(), task.getDueDate())) {
            outdatedTask.setDueDate(task.getDueDate());
        }
        if (validator.changeAttributeValue(outdatedTask.getStatus(), task.getStatus())) {
            outdatedTask.setStatus(task.getStatus());
        }
        if (validator.changeAttributeValue(outdatedTask.getCategory(), task.getCategory())) {
            outdatedTask.setCategory(task.getCategory());
        }
        if (validator.changeIntAttributeValue(outdatedTask.getPriorityLevel(), task.getPriorityLevel())) {
            outdatedTask.setPriorityLevel(task.getPriorityLevel());
        }

    }
}
