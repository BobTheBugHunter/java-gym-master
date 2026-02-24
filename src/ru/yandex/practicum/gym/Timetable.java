package ru.yandex.practicum.gym;

import java.util.*;
/*В первый раз торопился и случайно замержил без проверки, щас откатил до init*/

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable = new HashMap<>();
    private HashMap<Coach, Integer> coachHash = new HashMap<>();
    private int countOfSessions = 0;

    public void addNewTrainingSession(TrainingSession trainingSession) {

        TreeMap<TimeOfDay, ArrayList<TrainingSession>> timeTree = timetable.get(trainingSession.getDayOfWeek());
        if (timeTree == null) {
            timeTree = new TreeMap<>();
            timetable.put(trainingSession.getDayOfWeek(), timeTree);
        } else {
            timetable.put(trainingSession.getDayOfWeek(), timeTree);
        }

        ArrayList<TrainingSession> trainingSessions = timeTree.get(trainingSession.getTimeOfDay());
        if (trainingSessions == null) {
            trainingSessions = new ArrayList<>();
            timeTree.put(trainingSession.getTimeOfDay(), trainingSessions);
        }
        trainingSessions.add(trainingSession);

    }

    public ArrayList<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {

        ArrayList<TrainingSession> trainingSessionsForDay = new ArrayList<>();
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> keyDay = timetable.get(dayOfWeek);
        if (keyDay != null) {
            Collection<ArrayList<TrainingSession>> trainingSessions = keyDay.values();
            trainingSessions.forEach(trainingSessionsForDay::addAll);
            return trainingSessionsForDay;
        } else {
            return null;
        }
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {

        TreeMap<TimeOfDay, ArrayList<TrainingSession>> keyDay = timetable.get(dayOfWeek);
        if (keyDay != null) {
            return keyDay.get(timeOfDay);
        } else {
            return null;
        }
    }

    public ArrayList<Integer> getCountByCoaches(Coach coach) {
        for (DayOfWeek day : DayOfWeek.values()) {
            TreeMap<TimeOfDay, ArrayList<TrainingSession>> key = timetable.get(day);

            if (key == null) {
                continue;
            }

            Collection<ArrayList<TrainingSession>> trainings = key.values();

            for (ArrayList<TrainingSession> trainingSessions : trainings) {
                for (int i = 0; i < trainingSessions.size(); i++) {
                    if (trainingSessions.get(i).getCoach().equals(coach)) {
                        countOfSessions++;
                    }
                }
            }
        }

        coachHash.put(coach, countOfSessions);
        countOfSessions = 0;
        ArrayList<Integer> listWithSessions = new ArrayList<>(coachHash.values());
        listWithSessions.sort(Collections.reverseOrder());

        return listWithSessions;
    }

}
