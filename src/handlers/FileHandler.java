package handlers;

import app.TodoList;
import models.Task;

import java.io.*;
import java.util.Map;

public class FileHandler {
    private static final String path = "./src/app/DATA.csv";

    public static void saveTasks() {
        try {
            File file = new File(path);

            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (Map.Entry<Integer, Task> task : TodoList.tasks.entrySet()) {
                bufferedWriter.write(task.getValue().toString());
                bufferedWriter.newLine();
            }

            bufferedWriter.close();

        } catch (Exception error) {
            System.out.println("Falha ao salvar tarefas...");
        }
    }

    public static void readTasks() {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {

            String task = "";

            while ((task = bufferedReader.readLine()) != null) {
                task = task.replace(";", ",");
                CreateTask.recreate(task);
            }

        } catch (IOException e) {
            System.out.println("Falha ao ler as tarefas no arquivo DATA.csv");
        }
    }


}
