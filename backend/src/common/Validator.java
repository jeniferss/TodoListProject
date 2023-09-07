package common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Validator {
    public LocalDate parseAsDate(String dateFormat, String dateValue) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(dateFormat);
        return LocalDate.parse(dateValue, format);
    }

    public LocalDateTime parseAsDateTime(String dateFormat, String dateValue) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(dateFormat);
        return LocalDateTime.parse(dateValue, format);
    }

    public Integer parseStringAsInt(String intString) {
        return Integer.parseInt(intString);
    }

    public boolean isAIntString(String intString) {
        try {
            parseStringAsInt(intString);
        } catch (NumberFormatException error) {
            return false;
        }
        return true;
    }

    public boolean isStringDateFormatValid(String stringDate, String format) {
        try {
            parseAsDate(format, stringDate);
        } catch (DateTimeParseException error) {
            return false;
        }
        return true;
    }

    public boolean isStringDateTimeFormatValid(String stringDate, String format) {
        try {
            parseAsDateTime(format, stringDate);
        } catch (DateTimeParseException error) {
            return false;
        }
        return true;
    }

    public boolean isIdValid(Integer id, Map<Integer, ?> hashMap) {
        List<Integer> keys = new ArrayList<>(hashMap.keySet());
        return keys.contains(id);
    }

    public boolean isPriorityLevelValid(Integer priorityLevel) {
        return (priorityLevel >= 1 && priorityLevel <= 5);
    }

    public boolean isStatusValid(String status) {
        List<String> allowedStatus = Arrays.asList("FAZER", "FAZENDO", "FEITO");
        return allowedStatus.contains(status);
    }

    public boolean isCategoryValid(String status) {
        List<String> allowedCategories = Arrays.asList("ESTUDOS", "TRABALHO", "LAZER", "SAUDE");
        return allowedCategories.contains(status);
    }

    public boolean isDateTimeBeforeDate(LocalDateTime dateTime, LocalDate date) {
        return dateTime.toLocalDate().isBefore(date) || dateTime.toLocalDate().isEqual(date);
    }

}
