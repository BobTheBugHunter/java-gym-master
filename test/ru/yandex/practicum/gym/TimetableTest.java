package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TimetableTest {

    private Timetable timetable;
    private Coach coach;
    private Group groupChild;
    private Group groupAdult;

    @BeforeEach
    public void setUp() {
        timetable = new Timetable();
        coach = new Coach("Васильев", "Николай", "Сергеевич");
        groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
    }

    @Test
    public void testGetTrainingSessionsForDaySingleSession() {

        TrainingSession singleTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());

        Assertions.assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.WEDNESDAY).size());

        //Проверить, что за понедельник вернулось одно занятие
        //Проверить, что за вторник не вернулось занятий
    }

    @Test
    public void testGetTrainingSessionsForDayMultipleSessions() {

        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());

        Assertions.assertEquals(2, timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).size());

        Assertions.assertEquals(new TimeOfDay(13, 0),
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).getFirst().getTimeOfDay());

        Assertions.assertEquals(new TimeOfDay(20, 0),
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).getLast().getTimeOfDay());

        Assertions.assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
        // Проверить, что за понедельник вернулось одно занятие
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        // Проверить, что за вторник не вернулось занятий
    }

    @Test
    public void testGetTrainingSessionsForDayAndTimeSingleSession() {

        TrainingSession singleTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        Assertions.assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)).size());

        Assertions.assertEquals(0, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(14, 0)).size());

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        //Проверить, что за понедельник в 14:00 не вернулось занятий
    }


    @Test
    public void testGetTrainingSessionsForDayAndTimeMultipleSessions() {

        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));
        TrainingSession saturdayChildTrainingSession2 = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(11, 0));
        TrainingSession saturdayChildTrainingSession3 = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(12, 0));
        TrainingSession fridayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(12, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession2);
        timetable.addNewTrainingSession(saturdayChildTrainingSession3);
        timetable.addNewTrainingSession(fridayChildTrainingSession);

        Assertions.assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.FRIDAY,
                new TimeOfDay(12, 0)).size());

        Assertions.assertEquals(2, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)).size());

        Assertions.assertEquals(new TimeOfDay(10, 0),
                timetable.getTrainingSessionsForDay(DayOfWeek.SATURDAY).getFirst().getTimeOfDay());

        Assertions.assertEquals(new TimeOfDay(12, 0),
                timetable.getTrainingSessionsForDay(DayOfWeek.SATURDAY).getLast().getTimeOfDay());

        Assertions.assertEquals(0, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.TUESDAY,
                new TimeOfDay(9, 0)).size());

        //Проверить что за Пятницу в 12:00 вернулось 1 занятие
        //Проверить что за Понедельник в 13:00 вернулось 2 занятия
        //Проверить что за Субботу сначала вернулось занятие в 10:00 а последним вернулось занятие в 12:00
        //Проверить что во Вторник в 9:00 не вернется занятий
    }

    @Test
    public void testGetCountOfSessionsForSingleCouch() {
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        Assertions.assertEquals(4, timetable.getCountByCoaches().getFirst().getTrainingCount());

        Coach coach2 = new Coach("Егор", "Николай", "Сергеевич");

        Assertions.assertFalse(
                timetable.getCountByCoaches().contains(coach2));


        //проверить что вернется 4 занятия для тренера которого передаем в метод
        //проверить что у нового тренера нет занятий
    }


    @Test
    public void testGetCountOfSessionsForMultipleCouch() {

        Coach coach2 = new Coach("Боб", "Николай", "Сергеевич");
        Coach coach3 = new Coach("Святой", "Патрик", "Сергеевич");

        Group groupAdult2 = new Group("Акробатика для взрослых", Age.ADULT, 120);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        TrainingSession wednesdayAdultTraningSession = new TrainingSession(groupAdult2, coach2,
                DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(wednesdayAdultTraningSession);
        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild2 = new Group("Акробатика для детей", Age.CHILD, 30);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        TrainingSession wednesdayChildTraningSession = new TrainingSession(groupChild2, coach2,
                DayOfWeek.WEDNESDAY, new TimeOfDay(11, 0));

        timetable.addNewTrainingSession(wednesdayChildTraningSession);
        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        Assertions.assertEquals(4, timetable.getCountByCoaches().getFirst().getTrainingCount());
        Assertions.assertEquals(2, timetable.getCountByCoaches().get(1).getTrainingCount());
        Assertions.assertFalse(timetable.getCountByCoaches().contains(coach3));

        //Проверить что для первого тренера вернется 4 занятия
        //Проверить что для второго тренера вернется 2 занятия
        //Проверить что для нового тренера не будет занятий
    }

    @Test
    public void testGetTrainingSessionsForDayAndForDayAndTimeWithEmptyList() {
        TrainingSession morningSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(morningSession);

        ArrayList<TrainingSession> emptyTimeSessions = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(11, 0));

        Assertions.assertNotNull(emptyTimeSessions);
        Assertions.assertTrue(emptyTimeSessions.isEmpty());

        ArrayList<TrainingSession> emptyDaySessions = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.TUESDAY, new TimeOfDay(10, 0));

        Assertions.assertNotNull(emptyDaySessions, "Для дня без сессий должен возвращаться пустой список");
        Assertions.assertTrue(emptyDaySessions.isEmpty(), "Список должен быть пустым");
    }

}
