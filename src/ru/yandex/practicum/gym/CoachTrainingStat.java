package ru.yandex.practicum.gym;

public class CoachTrainingStat {
    private final Coach coach;
    private final int count;

    public CoachTrainingStat(Coach coach, int count) {
        this.coach = coach;
        this.count = count;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return coach + " - " + count;
    }
}
