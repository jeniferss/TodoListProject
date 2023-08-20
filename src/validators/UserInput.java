package validators;

import app.TodoList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserInput {

    public static boolean isTaskIdValid(String taskId) {
        try {
            int taskIdAsInt = Integer.parseInt(taskId);
            List<Integer> keysAsList = new ArrayList<>(TodoList.tasks.keySet());

            if (!keysAsList.contains(taskIdAsInt)) {
                throw new Exception("O ID informado não existe.");
            }

            return true;

        } catch (NumberFormatException error) {
            System.out.println("O nível da prioridade não é um número válido.");
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        return false;

    }

    public static LocalDate parseAsDate(String dateFormat, String dateValue) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(dateFormat);
        return LocalDate.parse(dateValue, format);
    }

    public static boolean isPriorityLevelValid(String priorityLevel) {
        try {
            int priorityLevelAsInt = Integer.parseInt(priorityLevel);
            if (!(priorityLevelAsInt >= 1 && priorityLevelAsInt <= 5)) {
                throw new Exception("O nível da prioridade está fora do range.");
            }
            return true;

        } catch (NumberFormatException error) {
            System.out.println("O nível da prioridade não é um número válido.");
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
        return false;
    }

    public static boolean isDueDateValid(String dateFormat, String dateValue, boolean higherThanToday) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(dateFormat);
        try {
            LocalDate date = LocalDate.parse(dateValue, format);
            LocalDate today = LocalDate.now();

            if (!date.isAfter(today) && higherThanToday) {
                throw new Exception("A data de expiração deve ser superior ao dia de hoje.");
            }
            return true;
        } catch (DateTimeParseException error) {
            System.out.println("O formato da data está incorreto. Verfique-a e tente novamente.");
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        return false;
    }

    public static boolean isStatusValid(String status) {
        List<String> allowedStatus = Arrays.asList("FAZER", "FAZENDO", "FEITO");

        if (allowedStatus.contains(status)) {
            return true;
        }

        System.out.println("O status informado está incorreto. Verifique-o e tente novamente.");
        return false;
    }

    public static boolean isTaskInfoValid(String[] taskString) {
        if (taskString.length == 6) {
            return true;
        }

        System.out.println("As informações da tarefa estão incompletas. Verfique-as e tente novamente.");
        return false;
    }
}
