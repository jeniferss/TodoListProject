package handlers;

import models.Task;

import java.time.LocalDate;
import java.util.*;

public class SortTask {

    private final FilterTask filterTask = new FilterTask();

    static Map<Integer, Task> byDueDate(Map<Integer, Task> tasks) {

        Map<Integer, Task> reversedTasks = new LinkedHashMap<>();

        List<Map.Entry<Integer, Task>> taskObjects = new ArrayList<>(tasks.entrySet());
        taskObjects.sort(Comparator.comparing(current -> current.getValue().getDueDate()));

        List<Integer> keysAsList = new ArrayList<>(tasks.keySet());
        Collections.reverse(keysAsList);

        for (Integer key : keysAsList) {
            reversedTasks.put(key, tasks.get(key));
        }

        return reversedTasks;
    }

    public int getIndexToInsert(int priorityLevel, LocalDate dueDate, Map<Integer, Task> tasks) {
        int index = 0;

        Map<Integer, Task> sortedTasks = byPriorityLevel(tasks);
        List<Task> tasksAsList = new ArrayList<>(sortedTasks.values());

        boolean canCompare = true;

        if (!tasksAsList.isEmpty()) {
            while (canCompare) {
                if (index == tasksAsList.size()) {
                    break;
                }

                canCompare = priorityLevel <= tasksAsList.get(index).getPriorityLevel();

                if (priorityLevel == tasksAsList.get(index).getPriorityLevel()) {
                    if (dueDate.isBefore(tasksAsList.get(index).getDueDate()) || dueDate.isEqual(tasksAsList.get(index).getDueDate())) {
                        break;
                    }
                }

                index++;
            }
        }

        return index;
    }

    public void insertObjectAndReorder(int indexPosition, Task task, Map<Integer, Task> tasks) {
        Map<Integer, Task> sortedTasks = byPriorityLevel(tasks);

        List<Task> tasksAsList = new ArrayList<>(sortedTasks.values());

        tasks.clear();

        for (int index = 0; index < indexPosition; index++) {
            tasks.put(tasksAsList.get(index).getId(), tasksAsList.get(index));
        }

        tasks.put(task.getId(), task);

        if (indexPosition < tasksAsList.size()) {
            for (int index = indexPosition; index < tasksAsList.size(); index++) {
                tasks.put(tasksAsList.get(index).getId(), tasksAsList.get(index));
            }
        }

    }

    public Map<Integer, Task> byPriorityLevel(Map<Integer, Task> tasks) {

        Map<Integer, Task> sortedTasks = new LinkedHashMap<>();

        for (int priorityLevel = 5; priorityLevel > 0; priorityLevel--) {
            Map<Integer, Task> currentTasks = filterTask.byPriorityLevel(priorityLevel, tasks);

            if (!currentTasks.isEmpty()) {
                currentTasks = byDueDate(currentTasks);
                sortedTasks.putAll(currentTasks);
            }
        }

        return sortedTasks;
    }
}
