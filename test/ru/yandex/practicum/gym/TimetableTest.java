package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        // Проверить, что за вторник не вернулось занятий (пустая карта)
        Map<TimeOfDay, List<TrainingSession>> tuesdaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertNotNull(tuesdaySessions);
        Assertions.assertTrue(tuesdaySessions.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        TreeMap<TimeOfDay, List<TrainingSession>> thursdaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);

        Assertions.assertEquals(2, thursdaySessions.size());

        // Проверяем порядок ключей (времён)
        List<TimeOfDay> times = new ArrayList<>(thursdaySessions.keySet());
        Assertions.assertEquals(new TimeOfDay(13, 0), times.get(0));
        Assertions.assertEquals(new TimeOfDay(20, 0), times.get(1));

        // Проверить, что за вторник не вернулось занятий (пустая карта)
        Map<TimeOfDay, List<TrainingSession>> tuesdaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertNotNull(tuesdaySessions);
        Assertions.assertTrue(tuesdaySessions.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        // Есть одно занятие в 13:00
        Assertions.assertEquals(1, timetable
                .getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0))
                .size());

        // В 14:00 занятий нет (пустой список)
        List<TrainingSession> sessionsAt14 =
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        Assertions.assertNotNull(sessionsAt14);
        Assertions.assertTrue(sessionsAt14.isEmpty());
    }

    @Test //если нет тренировок, список пуст
    void testGetCoachStatsWhenNoSessions() {
        Timetable timetable = new Timetable();
        List<CoachTrainingStat> stats = timetable.getCoachStatsSortedByCountDesc();
        Assertions.assertNotNull(stats);
        Assertions.assertTrue(stats.isEmpty());
    }

    @Test //Один тренер, несколько тренировок в разные дни/времена
    void testGetCoachStatsSingleCoachMultipleSessions() {
        Timetable timetable = new Timetable();
        Coach coach = new Coach("Иванов", "Иван", "Иванович");
        Group group = new Group("Акробатика", Age.ADULT, 60);
        timetable.addNewTrainingSession(new TrainingSession(
                group, coach, DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(
                group, coach, DayOfWeek.MONDAY, new TimeOfDay(12, 0)));
        timetable.addNewTrainingSession(new TrainingSession(
                group, coach, DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0)));
        List<CoachTrainingStat> stats = timetable.getCoachStatsSortedByCountDesc();

        Assertions.assertEquals(1, stats.size());

        CoachTrainingStat stat = stats.get(0);
        Assertions.assertEquals(coach, stat.getCoach());
        Assertions.assertEquals(3, stat.getCount());
    }

    @Test //Несколько тренеров, разное количество тренировок
    void testGetCoachStatsMultipleCoaches() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Иванов", "Иван", "Иванович");
        Coach coach2 = new Coach("Петров", "Пётр", "Петрович");
        Group group = new Group("Акробатика", Age.ADULT, 60);

        // coach1 – 3 тренировки
        timetable.addNewTrainingSession(new TrainingSession(
                group, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(
                group, coach1, DayOfWeek.TUESDAY, new TimeOfDay(11, 0)));
        timetable.addNewTrainingSession(new TrainingSession(
                group, coach1, DayOfWeek.WEDNESDAY, new TimeOfDay(12, 0)));

        // coach2 – 1 тренировка
        timetable.addNewTrainingSession(new TrainingSession(
                group, coach2, DayOfWeek.MONDAY, new TimeOfDay(13, 0)));

        List<CoachTrainingStat> stats = timetable.getCoachStatsSortedByCountDesc();

        Assertions.assertEquals(2, stats.size());

        CoachTrainingStat first = stats.get(0);
        CoachTrainingStat second = stats.get(1);

        Assertions.assertEquals(coach1, first.getCoach());
        Assertions.assertEquals(3, first.getCount());

        Assertions.assertEquals(coach2, second.getCoach());
        Assertions.assertEquals(1, second.getCount());
    }
}