package handlers;

import models.Task;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class FilterTask {

    public void displayResults(Map<Integer, Task> tasks) {
        System.out.println();
        System.out.println(" ======== Tarefas ========");
        for (Task task : tasks.values()) {
            System.out.println(task);
        }
        System.out.println(" ========================");
        System.out.println();
    }

    public Map<Integer, Task> byStatus(String status, Map<Integer, Task> tasks) {

        Map<Integer, Task> filteredList = new LinkedHashMap<>();

        for (Map.Entry<Integer, Task> task : tasks.entrySet()) {
            if (Objects.equals(task.getValue().getStatus(), status)) {
                filteredList.put(task.getKey(), task.getValue());
            }
        }

        return filteredList;
    }

    public Map<Integer, Task> byDueDate(LocalDate dueDate, Map<Integer, Task> tasks) {

        Map<Integer, Task> filteredList = new LinkedHashMap<>();

        for (Map.Entry<Integer, Task> task : tasks.entrySet()) {
            if (Objects.equals(task.getValue().getDueDate(), dueDate)) {
                filteredList.put(task.getKey(), task.getValue());
            }
        }

        return filteredList;
    }

    public Map<Integer, Task> byCategory(String category, Map<Integer, Task> tasks) {

        Map<Integer, Task> filteredList = new LinkedHashMap<>();

        for (Map.Entry<Integer, Task> task : tasks.entrySet()) {
            if (Objects.equals(task.getValue().getCategory(), category)) {
                filteredList.put(task.getKey(), task.getValue());
            }
        }

        return filteredList;
    }

    public Map<Integer, Task> byPriorityLevel(Integer priorityLevel, Map<Integer, Task> tasks) {

        Map<Integer, Task> filteredList = new HashMap<>();

        for (Map.Entry<Integer, Task> task : tasks.entrySet()) {
            if (Objects.equals(task.getValue().getPriorityLevel(), priorityLevel)) {
                filteredList.put(task.getKey(), task.getValue());
            }
        }

        return filteredList;
    }
}
