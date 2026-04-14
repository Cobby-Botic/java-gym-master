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

    public /* непонятно, что возвращать */ getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
    }

    public /* непонятно, что возвращать */ getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
    }
}
