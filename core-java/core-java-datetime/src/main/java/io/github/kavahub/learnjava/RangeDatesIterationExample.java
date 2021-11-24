package io.github.kavahub.learnjava;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期范围迭代器，日期循环
 * 
 */
public class RangeDatesIterationExample {
    public void iterateBetweenDatesJava9(LocalDate startDate, LocalDate endDate) {

        startDate.datesUntil(endDate)
            .forEach(this::processDate);
    }

    public void iterateBetweenDatesJava8(LocalDate start, LocalDate end) {
        for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
            processDate(date);
        }
    }

    public void iterateBetweenDatesJava7(Date start, Date end) {
        Date current = start;

        while (current.before(end)) {
            processDate(current);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(current);

            calendar.add(Calendar.DATE, 1);

            current = calendar.getTime();
        }
    }

    private void processDate(LocalDate date) {
        System.out.println(date);
    }

    private void processDate(Date date) {
        System.out.println(date);
    }    


    public static void main(String[] args) {
        System.out.println("===givenIterateBetweenDatesJava9_WhenStartDateAsTodayAndEndDateAs10DaysAhead===");
        givenIterateBetweenDatesJava9_WhenStartDateAsTodayAndEndDateAs10DaysAhead();

        System.out.println("===givenIterateBetweenDatesJava8_WhenStartDateAsTodayAndEndDateAs10DaysAhead===");
        givenIterateBetweenDatesJava8_WhenStartDateAsTodayAndEndDateAs10DaysAhead();

        System.out.println("===givenIterateBetweenDatesJava7_WhenStartDateAsTodayAndEndDateAs10DaysAhead===");
        givenIterateBetweenDatesJava7_WhenStartDateAsTodayAndEndDateAs10DaysAhead();
    }

    public static void givenIterateBetweenDatesJava9_WhenStartDateAsTodayAndEndDateAs10DaysAhead() {
        LocalDate start = LocalDate.now();
        LocalDate end = start.plus(10L, ChronoUnit.DAYS);

        RangeDatesIterationExample iteration = new RangeDatesIterationExample();

        iteration.iterateBetweenDatesJava9(start, end);
    }

    public static void givenIterateBetweenDatesJava8_WhenStartDateAsTodayAndEndDateAs10DaysAhead() {
        LocalDate start = LocalDate.now();
        LocalDate end = start.plus(10L, ChronoUnit.DAYS);

        RangeDatesIterationExample iteration = new RangeDatesIterationExample();

        iteration.iterateBetweenDatesJava8(start, end);
    }

    public static void givenIterateBetweenDatesJava7_WhenStartDateAsTodayAndEndDateAs10DaysAhead() {
        Calendar today = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DATE));
        Date start = calendar.getTime();

        calendar.add(Calendar.DATE, 10);
        Date end = calendar.getTime();

        RangeDatesIterationExample iteration = new RangeDatesIterationExample();

        iteration.iterateBetweenDatesJava7(start, end);
    }
}
