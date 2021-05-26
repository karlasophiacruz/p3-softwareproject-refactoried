package app.scheduleManager;

import java.time.*;
import java.util.Scanner;

import app.AuxFunctions;

public class BiweeklySchedule implements ScheduleType {
    public boolean execute(Scanner input, String schedule, AuxFunctions auxFunctions){
        LocalDate dayToday = auxFunctions.CheckDate(input);

        if(schedule.equals(dayToday.getDayOfWeek().name())){
            if((dayToday.getDayOfMonth()/7) == 2 || (dayToday.getDayOfMonth()/7) == 4)
                return true;
        }
        return false;
    }
}
