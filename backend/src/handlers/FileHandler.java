package handlers;

import common.Validator;
import models.Alarm;
import models.Task;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FileHandler {
    private final Validator validator = new Validator();
    private final Sorter sorter = new Sorter();

    public void saveTasks(Map<Integer, Task> tasks) {
        try {
            final String path = "./database/TASKS.csv";

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
            System.out.println("Falha ao salvar tarefas no arquivo TASKS.csv");
        }
    }

    public void readTasks(Map<Integer, Task> tasks, TaskHandler taskHandler) {

        final String path = "./database/TASKS.csv";

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
            System.out.println("Falha ao ler as tarefas no arquivo TASKS.csv");
        }
    }

    public void saveAlarms(Map<Integer, Alarm> alarms) {
        try {
            final String path = "./database/ALARMS.csv";

            File file = new File(path);

            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (Map.Entry<Integer, Alarm> alarm : alarms.entrySet()) {
                bufferedWriter.write(alarm.getValue().toString());
                bufferedWriter.newLine();
            }

            bufferedWriter.close();

        } catch (Exception error) {
            System.out.println("Falha ao salvar alarmes no arquivo ALARMS.csv");
        }
    }

    public void readAlarms(Map<Integer, Alarm> alarms, AlarmHandler alarmHandler) {
        final String path = "./database/ALARMS.csv";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {

            String alarm = "";

            while ((alarm = bufferedReader.readLine()) != null) {
                List<String> alarmParts = Arrays.asList(alarm.split(";"));

                if (alarmParts.isEmpty()) {
                    break;
                }

                Integer id = validator.parseStringAsInt(alarmParts.get(0).trim());
                Integer taskId = validator.parseStringAsInt(alarmParts.get(1).trim());

                String dateFormat = "yyyy-MM-dd'T'HH:mm";
                String dateString = alarmParts.get(2).trim();
                LocalDateTime dateTime = validator.parseAsDateTime(dateFormat, dateString);

                Alarm newAlarm = alarmHandler.create(id, taskId, dateTime);
                alarmHandler.insert(newAlarm, alarms);
            }

        } catch (IOException e) {
            System.out.println("Falha ao ler os alarmes no arquivo ALARMS.csv");
        }
    }

}
