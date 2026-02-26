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
            return new ArrayList<>();
        }
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {

        TreeMap<TimeOfDay, ArrayList<TrainingSession>> keyDay = timetable.get(dayOfWeek);
        if (keyDay != null) {
            ArrayList<TrainingSession> sessions = keyDay.get(timeOfDay);
            if (sessions != null) {
                return new ArrayList<>(sessions);
            }
    }
        return  new ArrayList<>();
    }

    public ArrayList<CoachStatistic> getCountByCoaches() {

        Map<Coach, Integer> coachHash = new HashMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            TreeMap<TimeOfDay, ArrayList<TrainingSession>> key = timetable.get(day);

            if (key == null) {
                continue;
            }

            for (ArrayList<TrainingSession> trainingSessions : key.values()) {
                for (TrainingSession trainingSession : trainingSessions) {
                    Coach coach = trainingSession.getCoach();
                    coachHash.put(coach, coachHash.getOrDefault(coach, 0) + 1);
                }
            }
        }

        ArrayList<CoachStatistic> coachStatistics = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : coachHash.entrySet()) {
            coachStatistics.add(new CoachStatistic(entry.getKey(), entry.getValue()));
        }


        Collections.sort(coachStatistics, new Comparator<CoachStatistic>() {
            @Override
            public int compare(CoachStatistic o1, CoachStatistic o2) {
                return o2.getTrainingCount() - o1.getTrainingCount();
            }
        });
        return coachStatistics;
    }

}
