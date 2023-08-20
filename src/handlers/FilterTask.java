package handlers;

import app.TodoList;
import models.Task;
import validators.UserInput;

import java.util.*;

public class FilterTask {

    public static void displayResults(Map<Integer, Task> tasks) {
        for (Task task : tasks.values()) {
            System.out.println(task);
        }
    }

    public static Map<Integer, Task> byPriorityLevel(String priorityLevel) {

        Map<Integer, Task> filteredList = new HashMap<>();

        if (UserInput.isPriorityLevelValid(priorityLevel)) {
            Integer priorityLevelAsInt = Integer.parseInt(priorityLevel);
            for (Map.Entry<Integer, Task> task : TodoList.tasks.entrySet()) {
                if (Objects.equals(task.getValue().getPriorityLevel(), priorityLevelAsInt)) {
                    filteredList.put(task.getKey(), task.getValue());
                }
            }
        }

        return filteredList;
    }

    public static Map<Integer, Task> byStatus(String status) {

        Map<Integer, Task> filteredList = new LinkedHashMap<>();

        if (UserInput.isStatusValid(status)) {
            for (Map.Entry<Integer, Task> task : TodoList.tasks.entrySet()) {
                if (Objects.equals(task.getValue().getStatus(), status)) {
                    filteredList.put(task.getKey(), task.getValue());
                }
            }
        }

        return filteredList;
    }

    public static Map<Integer, Task> byDueDate(String dueDate) {

        Map<Integer, Task> filteredList = new LinkedHashMap<>();
        String format = "dd-MM-yyyy";

        if (UserInput.isDueDateValid(format, dueDate, false)) {
            for (Map.Entry<Integer, Task> task : TodoList.tasks.entrySet()) {
                if (Objects.equals(task.getValue().getDueDate(), UserInput.parseAsDate(format, dueDate))) {
                    filteredList.put(task.getKey(), task.getValue());
                }
            }
        }

        return filteredList;
    }

    public static Map<Integer, Task> byCategory(String category) {

        Map<Integer, Task> filteredList = new LinkedHashMap<>();

        for (Map.Entry<Integer, Task> task : TodoList.tasks.entrySet()) {
            if (Objects.equals(task.getValue().getCategory(), category)) {
                filteredList.put(task.getKey(), task.getValue());
            }
        }

        return filteredList;
    }
}
