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
        TreeMap<TimeOfDay, List<TrainingSession>> result = timetable.get(dayOfWeek);
        if (result == null) {
            return new TreeMap<TimeOfDay, List<TrainingSession>>();
        }
        return result;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        Map<TimeOfDay, List<TrainingSession>> sessionsForDay = timetable.get(dayOfWeek);
        if (sessionsForDay == null) {
            return new ArrayList<TrainingSession>();
        }
        List<TrainingSession> result = sessionsForDay.get(timeOfDay);
        if (result == null) {
            return new ArrayList<TrainingSession>();
        }
        return result;
    }

    public List<CoachTrainingStat> getCoachStatsSortedByCountDesc() {
        Map<Coach, Integer> count = new HashMap<>();
        for (TreeMap<TimeOfDay, List<TrainingSession>> sessionsForDay : timetable.values()) {
            for (List<TrainingSession> sessionAtTime : sessionsForDay.values()) {
                for (TrainingSession trainingSession : sessionAtTime) {
                    Coach coach = trainingSession.getCoach();
                    count.put(coach, count.getOrDefault(coach, 0) + 1);
                }
            }
        }
        List<CoachTrainingStat> result = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : count.entrySet()) {
            result.add(new CoachTrainingStat(entry.getKey(), entry.getValue()));
        }
        result.sort((a, b) -> Integer.compare(b.getCount(), a.getCount()));
        return result;
    }
}