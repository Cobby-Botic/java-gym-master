package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, List<TrainingSession>> sessionsForDay = timetable.get(day);

        if (sessionsForDay == null) {
            sessionsForDay = new TreeMap<>();
            timetable.put(day, sessionsForDay);
        }

        List<TrainingSession> sessionsAtTime = sessionsForDay.get(time);
        if (sessionsAtTime == null) {
            sessionsAtTime = new ArrayList<>();
            sessionsForDay.put(time, sessionsAtTime);
        }

        sessionsAtTime.add(trainingSession);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.get(dayOfWeek);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        Map<TimeOfDay, List<TrainingSession>> sessionsForDay = timetable.get(dayOfWeek);
        return sessionsForDay.get(timeOfDay);
    }
}
