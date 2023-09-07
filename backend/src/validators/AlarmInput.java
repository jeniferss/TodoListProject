package validators;

import common.Validator;
import models.Alarm;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class AlarmInput {
    private final String dateTimeFormat = "dd-MM-yyyy HH:mm";
    private final Validator validator = new Validator();

    public LocalDateTime handleDateTime(String dateTimeString, LocalDate dueDate) throws Exception {
        if (validator.isStringDateTimeFormatValid(dateTimeString, dateTimeFormat)) {
            LocalDateTime dateTime = validator.parseAsDateTime(dateTimeFormat, dateTimeString);

            if (validator.isDateTimeBeforeDate(dateTime, dueDate)) {
                return dateTime;
            }
        }

        throw new Exception("A data informada não é válida.");
    }

    public Integer handleAlarmId(String idString, Map<Integer, Alarm> alarms) throws Exception {

        if (validator.isAIntString(idString)) {
            Integer taskId = validator.parseStringAsInt(idString);
            if (validator.isIdValid(taskId, alarms)) {
                return taskId;
            }
        }

        throw new Exception("O ID informado não é válido.");
    }

    public LocalDateTime handleNowDateTime() {
        String format = "yyyy-MM-dd HH:mm";

        String nowString = DateTimeFormatter.ofPattern(format).format(LocalDateTime.now());
        LocalDateTime now = validator.parseAsDateTime(format, nowString);

        return now;
    }
}
