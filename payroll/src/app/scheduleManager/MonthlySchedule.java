package app.scheduleManager;

import java.time.*;
import java.util.Scanner;

import app.AuxFunctions;

public class MonthlySchedule implements ScheduleType {
    public boolean execute(Scanner input, String schedule, AuxFunctions auxFunctions){
        LocalDate dayToday = auxFunctions.CheckDate(input);

        LocalDate lastDaymonth = LocalDate.of(dayToday.getYear(), dayToday.getMonthValue(), dayToday.getMonth().maxLength());
        lastDaymonth = LastUtilDayMonth(lastDaymonth); 

        if(schedule.equals("$") && dayToday.equals(lastDaymonth)){
            System.out.println("Today is the last util day of month.");
            return true;
        }
        else if(Integer.parseInt(schedule) == dayToday.getDayOfMonth()){
            System.out.println("Today is " + dayToday.getDayOfMonth());
            return true;
        }
        return false;
    }

    protected LocalDate LastUtilDayMonth(LocalDate lastDay){
        if(lastDay.getDayOfWeek() == DayOfWeek.SUNDAY){
            return lastDay.minusDays(2);
        } else if(lastDay.getDayOfWeek() == DayOfWeek.SATURDAY){
            return lastDay.minusDays(1);
        }
        return lastDay;
    }
}
