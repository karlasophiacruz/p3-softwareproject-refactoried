package app.scheduleManager;

import java.util.Scanner;

import app.AuxFunctions;

public interface ScheduleType {
    public boolean execute(Scanner input, String schedule, AuxFunctions auxFunctions);
}
