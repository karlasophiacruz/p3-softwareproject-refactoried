package app.scheduleManager;

import java.time.*;
import java.util.Scanner;

import app.AuxFunctions;

public class WeeklySchedule implements ScheduleType {
    public boolean execute(Scanner input, String schedule, AuxFunctions auxFunctions){
        LocalDate dayToday = auxFunctions.CheckDate(input);

        if(schedule.equals(dayToday.getDayOfWeek().name()))
            return true;

        return false;
    }
}
