package handlers;

import models.Task;
import validators.TaskValidator;

import java.time.LocalDate;
import java.util.Map;

public class TaskAttributesHandler {
    private final String dateFormat = "dd-MM-yyyy";
    private final TaskValidator validator = new TaskValidator();

    public Integer handleTaskId(String idString, Map<Integer, Task> tasks) throws Exception {

        if (validator.isAIntString(idString)) {
            Integer taskId = validator.parseStringAsInt(idString);
            if (validator.isTaskIdValid(taskId, tasks)) {
                return taskId;
            }
        }

        throw new Exception("O ID informado não é válido.");
    }

    public LocalDate handleDueDate(String dueDateString) throws Exception {
        if (validator.isStringDateFormatValid(dueDateString, dateFormat)) {
            return validator.parseAsDate(dateFormat, dueDateString);
        }

        throw new Exception("A data informada não possui formato válido.");
    }

    public String handleStatus(String status) throws Exception {
        if (validator.isStatusValid(status)) {
            return status;
        }

        throw new Exception("O status informado não é válido.");
    }

    public String handleCategory(String category) throws Exception {
        if (validator.isCategoryValid(category)) {
            return category;
        }

        throw new Exception("A categoria informada não é válida.");
    }

    public Integer handlePriorityLevel(String priorityLevelString) throws Exception {

        if (validator.isAIntString(priorityLevelString)) {
            Integer priorityLevel = validator.parseStringAsInt(priorityLevelString);
            if (validator.isPriorityLevelValid(priorityLevel)) {
                return priorityLevel;
            }
        }

        throw new Exception("O nível de prioridade informado não possui formato válido.");
    }

    public boolean changeAttributeValue(Object oldValue, Object newValue) {
        return !oldValue.equals(newValue) && !(newValue == null);
    }

    public boolean changeIntAttributeValue(Integer oldValue, Integer newValue) {
        return !oldValue.equals(newValue) && !(newValue == 0);
    }

    public boolean skipChangeAttribute(String value) {
        return value.equals("0");
    }

}
