package com.gft.challenge;

import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class CalendarIteratorTest {

    private static final String DATE_STRING = "2016-09-19";
    private Calendar calendar;

    @Before
    public void setUp() {
        calendar = new Calendar(LocalDate.parse(DATE_STRING));
    }

    @Test
    public void hasNextShouldReturnTrueWhenStartDateNotNull() {
        Iterator iterator = calendar.iterator();
        assertThat(iterator.hasNext()).isTrue();
    }

    @Test
    public void hasNextShouldReturnTrueWhenStartDateIsNull() {
        Calendar localCalendar = new Calendar(null);
        Iterator<LocalDate> iterator = localCalendar.iterator();
        assertThat(iterator.hasNext()).isTrue();
    }

    @Test
    public void returnedDateShouldBeMondayOrFriday() {
        Iterator<LocalDate> iterator = calendar.iterator();
        assertThat(iterator.next().getDayOfWeek()).isIn(DayOfWeek.FRIDAY,DayOfWeek.TUESDAY);
        assertThat(iterator.next().getDayOfWeek()).isIn(DayOfWeek.FRIDAY,DayOfWeek.TUESDAY);
    }

    @Test
    public void shouldBeAbleToIterateOverFewMondaysAndFridays() {
        Iterator<LocalDate> iterator = calendar.iterator();
        List<LocalDate> correctDates = Arrays.asList(
                LocalDate.of(2016,9,20),
                LocalDate.of(2016,9,23),
                LocalDate.of(2016,9,27),
                LocalDate.of(2016,9,30)
        );
        for(LocalDate localDate : correctDates) {
            assertThat(localDate).isEqualTo(iterator.next());
        }
    }

    @Test
    public void shouldThrowUnsupportedOperationException() {
        Iterator iterator = calendar.iterator();
        assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(iterator::remove)
                .withMessage("Not able to remove date");
    }
}
