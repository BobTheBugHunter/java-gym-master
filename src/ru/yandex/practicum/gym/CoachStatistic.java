package ru.yandex.practicum.gym;

public class CoachStatistic {
    private Coach coach;
    private int trainingCount;

    public CoachStatistic(Coach coach, int trainingCount) {
        this.coach = coach;
        this.trainingCount = trainingCount;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getTrainingCount() {
        return trainingCount;
    }

    @Override
    public String toString() {
        return "CoachStatistic{" +
                "coach=" + coach.getSurname() + " " + coach.getName() + " " + coach.getMiddleName() +
                ", trainingCount=" + trainingCount +
                '}';
    }
}
