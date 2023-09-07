package handlers;

import models.Alarm;
import models.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Filterer {

    public void displayResults(Map<Integer, ?> results) {
        System.out.println();
        System.out.println(" =======================");
        for (Object result : results.values()) {
            System.out.println(result);
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

    public Map<Integer, Alarm> byTaskId(Integer taskId, Map<Integer, Alarm> alarms) {
        Map<Integer, Alarm> filteredList = new HashMap<>();

        for (Map.Entry<Integer, Alarm> alarm : alarms.entrySet()) {
            if (Objects.equals(alarm.getValue().getTaskId(), taskId)) {
                filteredList.put(alarm.getKey(), alarm.getValue());
            }
        }

        return filteredList;
    }

    public Map<Integer, Alarm> byDateTime(LocalDateTime date, Map<Integer, Alarm> alarms) {
        Map<Integer, Alarm> filteredList = new HashMap<>();

        for (Map.Entry<Integer, Alarm> alarm : alarms.entrySet()) {
            if (Objects.equals(alarm.getValue().getDateTime(), date)) {
                filteredList.put(alarm.getKey(), alarm.getValue());
            }
        }

        return filteredList;
    }
}
