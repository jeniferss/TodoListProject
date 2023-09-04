package handlers;

import models.Task;
import validators.TaskValidator;

import java.io.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FileHandler {
    private final String path = "./database/DATA.csv";
    private final TaskValidator validator = new TaskValidator();
    private final SortTask sorter = new SortTask();

    public void saveTasks(Map<Integer, Task> tasks) {
        try {
            File file = new File(path);

            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            tasks = sorter.byPriorityLevel(tasks);

            for (Map.Entry<Integer, Task> task : tasks.entrySet()) {
                bufferedWriter.write(task.getValue().toString());
                bufferedWriter.newLine();
            }

            bufferedWriter.close();

        } catch (Exception error) {
            System.out.println("Falha ao salvar tarefas no arquivo DATA.csv");
        }
    }

    public void readTasks(Map<Integer, Task> tasks, TaskHandler taskHandler) {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {

            String task = "";

            while ((task = bufferedReader.readLine()) != null) {
                List<String> taskParts = Arrays.asList(task.split(";"));

                if (taskParts.isEmpty()) {
                    break;
                }

                Integer id = validator.parseStringAsInt(taskParts.get(0).trim());
                String title = taskParts.get(1).trim();
                String description = taskParts.get(2).trim();
                String dateFormat = "yyyy-MM-dd";
                LocalDate dueDate = validator.parseAsDate(dateFormat, taskParts.get(3).trim());
                String status = taskParts.get(4).trim();
                String category = taskParts.get(5).trim();
                Integer priorityLevel = validator.parseStringAsInt(taskParts.get(6).trim());


                Task newtask = taskHandler.create(id, title, description, dueDate, status, category, priorityLevel);
                taskHandler.insert(newtask, tasks);
            }

        } catch (IOException e) {
            System.out.println("Falha ao ler as tarefas no arquivo DATA.csv");
        }
    }

}
