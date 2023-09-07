package handlers;

import common.UserInput;
import models.Alarm;
import models.Task;
import validators.TaskInput;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

public class TaskHandler {
    private final Sorter sorter = new Sorter();
    private final Filterer filterer = new Filterer();
    private final AlarmHandler alarmHandler = new AlarmHandler();
    private final UserInput userInput = new UserInput();
    private final TaskInput validator = new TaskInput();

    public int generateTaskId(Map<Integer, Task> tasks) {
        return tasks.keySet().isEmpty() ? 1 : Collections.max(tasks.keySet()) + 1;
    }

    public void readUserInputAndCreateTask(Map<Integer, Task> tasks, Map<Integer, Alarm> alarms) throws Exception {

        int id = generateTaskId(tasks);

        String title = userInput.read("Digite o título: ");
        String description = userInput.read("Digite a descrição: ");

        String dueDateString = userInput.read("Digite a data de expiração (ex. dd-MM-yyyy): ");
        LocalDate dueDate = validator.handleDueDate(dueDateString);

        String status = userInput.read("Digite o Status (FAZER, FAZENDO, FEITO): ");
        status = validator.handleStatus(status);

        String category = userInput.read("Digite a Categoria (ESTUDOS, TRABALHO, LAZER, SAUDE): ");
        category = validator.handleCategory(category);

        String priorityLevelString = userInput.read("Digite o Nível de Prioridade (1-5): ");
        Integer priorityLevel = validator.handlePriorityLevel(priorityLevelString);

        Task task = create(id, title, description, dueDate, status, category, priorityLevel);
        insert(task, tasks);

        String enableAlarm = userInput.read("A tarefa vai ter alarme? [S] [N]");
        if (enableAlarm.equals("S")) {
            alarmHandler.readUserInputAndCreateAlarm(task, alarms);
        }
    }

    public void readUserInputAndUpdateTask(Map<Integer, Task> tasks) throws Exception {

        Task task = new Task();

        String idString = userInput.read("Digite o ID da tarefa que deseja exlcuir: ");
        Integer id = validator.handleTaskId(idString, tasks);

        String title = userInput.read("Digite o NOVO título ou 0 para NÃO alterar: ");
        if (!validator.skipChangeAttribute(title)) {
            task.setTitle(title);
        }

        String description = userInput.read("Digite a descrição: ");
        if (!validator.skipChangeAttribute(description)) {
            task.setDescription(description);
        }

        String dueDateString = userInput.read("Digite a NOVA data de expiração (ex. dd-MM-yyyy) ou 0 para NÃO alterar: ");
        if (!validator.skipChangeAttribute(dueDateString)) {
            LocalDate dueDate = validator.handleDueDate(dueDateString);
            task.setDueDate(dueDate);
        }

        String status = userInput.read("Digite o NOVO Status (FAZER, FAZENDO, FEITO) ou 0 para NÃO alterar: ");
        if (!validator.skipChangeAttribute(status)) {
            status = validator.handleStatus(status);
            task.setStatus(status);
        }

        String category = userInput.read("Digite a NOVO Categoria (ESTUDOS, TRABALHO, LAZER, SAUDE) ou 0 para NÃO alterar: ");
        if (!validator.skipChangeAttribute(category)) {
            category = validator.handleCategory(category);
            task.setCategory(category);
        }

        String priorityLevelString = userInput.read("Digite o NOVO Nível de Prioridade (1-5) ou 0 para NÃO alterar: ");
        if (!validator.skipChangeAttribute(priorityLevelString)) {
            Integer priorityLevel = validator.handlePriorityLevel(priorityLevelString);
            task.setPriorityLevel(priorityLevel);
        }

        update(id, task, tasks);
    }

    public void readUserInputAndDeleteTask(Map<Integer, Task> tasks, Map<Integer, Alarm> alarms) throws Exception {
        String idString = userInput.read("Digite o ID da tarefa que deseja exlcuir: ");
        Integer id = validator.handleTaskId(idString, tasks);
        delete(id, tasks);

        // delete its alarms
        Map<Integer, Alarm> taskAlarms = filterer.byTaskId(id, alarms);
        for (Alarm alarm : taskAlarms.values()) {
            alarmHandler.delete(alarm.getId(), alarms);
        }

    }

    public void readUserInputAndFilterByDueDate(Map<Integer, Task> tasks) throws Exception {
        String dueDateString = userInput.read("Digite a data de expiração (ex. dd-MM-yyyy): ");
        LocalDate dueDate = validator.handleDueDate(dueDateString);

        Map<Integer, Task> formattedTasks = filterer.byDueDate(dueDate, tasks);
        filterer.displayResults(formattedTasks);
    }

    public void readUserInputAndFilterByPriorityLevel(Map<Integer, Task> tasks) throws Exception {
        String priorityLevelString = userInput.read("Digite o Nível de Prioridade (1-5): ");
        Integer priorityLevel = validator.handlePriorityLevel(priorityLevelString);

        Map<Integer, Task> formattedTasks = filterer.byPriorityLevel(priorityLevel, tasks);
        filterer.displayResults(formattedTasks);
    }

    public void readUserInputAndFilterByStatus(Map<Integer, Task> tasks) throws Exception {
        String status = userInput.read("Digite o Status (FAZER, FAZENDO, FEITO): ");
        status = validator.handleStatus(status);

        Map<Integer, Task> formattedTasks = filterer.byStatus(status, tasks);
        filterer.displayResults(formattedTasks);
    }

    public void readUserInputAndFilterByCategory(Map<Integer, Task> tasks) throws Exception {
        String category = userInput.read("Digite a Categoria (ESTUDOS, TRABALHO, LAZER, SAUDE): ");
        category = validator.handleCategory(category);

        Map<Integer, Task> formattedTasks = filterer.byCategory(category, tasks);
        filterer.displayResults(formattedTasks);
    }

    public void readUserInputAndCountByStatus(Map<Integer, Task> tasks) throws Exception {
        String status = userInput.read("Digite o Status (FAZER, FAZENDO, FEITO): ");
        status = validator.handleStatus(status);

        Map<Integer, Task> formattedTasks = filterer.byStatus(status, tasks);
        System.out.println("Quantidade de tarefas: " + formattedTasks.size());
    }

    public Task create(
            Integer id,
            String title,
            String description,
            LocalDate dueDate,
            String status,
            String category,
            Integer priorityLevel
    ) {
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
