package handlers;

import common.UserInput;
import models.Alarm;
import models.Task;
import validators.AlarmInput;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

public class AlarmHandler {

    private final UserInput userInput = new UserInput();
    private final AlarmInput validator = new AlarmInput();
    private final Filterer filterer = new Filterer();

    public int generateAlarmId(Map<Integer, Alarm> alarms) {
        return alarms.keySet().isEmpty() ? 1 : Collections.max(alarms.keySet()) + 1;
    }

    public void readUserInputAndCreateAlarm(Task task, Map<Integer, Alarm> alarms) throws Exception {

        int id = generateAlarmId(alarms);

        String dateTimeString = userInput.read("Digite a data digite a data e horário para a terefa (ex. dd-MM-yyyy HH:MM): ");
        LocalDateTime date = validator.handleDateTime(dateTimeString, task.getDueDate());

        Alarm alarm = create(id, task.getId(), date);
        insert(alarm, alarms);
    }

    public void displayAlarms(Map<Integer, Alarm> alarms, Map<Integer, Task> tasks) {
        LocalDateTime now = validator.handleNowDateTime();
        Map<Integer, Alarm> alarmsForNow = filterer.byDateTime(now, alarms);

        System.out.println("========== TAREFAS PARA AGORA ==============");
        for (Map.Entry<Integer, Alarm> alarm : alarmsForNow.entrySet()) {
            System.out.println("Alarme: " + alarm);

            Task task = tasks.get(alarm.getValue().getTaskId());
            System.out.println("Tarefa: " + task);
            System.out.println();
        }
        System.out.println("==================================");

    }

    public Alarm create(
            Integer id,
            Integer taskId,
            LocalDateTime dateTime
    ) {
        Alarm alarm = new Alarm();
        alarm.setId(id);
        alarm.setTaskId(taskId);
        alarm.setDate(dateTime);

        return alarm;
    }

    public void insert(Alarm alarm, Map<Integer, Alarm> alarms) {
        alarms.put(alarm.getId(), alarm);
    }

    public void delete(Integer alarmId, Map<Integer, Alarm> alarms) {
        alarms.remove(alarmId);
    }
}
