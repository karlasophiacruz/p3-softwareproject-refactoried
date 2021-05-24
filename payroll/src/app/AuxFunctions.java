package app;

import java.time.*;
import java.util.*;

import model.employee.Employee;
import model.syndicate.Syndicate;

public class AuxFunctions {

    protected List<Employee> emploList;
    protected List<Syndicate> syndiList;

    public AuxFunctions(List<Employee> emploList, List<Syndicate> syndiList){
        this.emploList = emploList;
        this.syndiList = syndiList;
    }

    protected int WrongAnswer(Scanner input){
        System.out.println("(Please, choose a number)");
        System.out.printf(" 1 - Yes\n 2 - No\n");
        int answer = Integer.parseInt(input.nextLine());

        if(answer != 1 && answer != 2){
            System.out.println("Sorry, I didn't understand! :(");
            System.out.println("Can you repeat, please?");
            System.out.println();
            answer = WrongAnswer(input);
        }
        return answer;
    }

    protected LocalDate SetDate(Scanner input){
        System.out.println("Enter the day number:");
        int day = Integer.parseInt(input.nextLine());
        System.out.println();

        System.out.println("Enter the month number:");
        int month = Integer.parseInt(input.nextLine());
        System.out.println();

        System.out.println("Enter the year number:");
        int year = Integer.parseInt(input.nextLine());
        System.out.println();

        return LocalDate.of(year, month, day);
    }

    protected LocalTime SetTime(Scanner input){
        System.out.println("(Format 24-hour clock)");
        System.out.println("Enter the hour number:");
        int hour = Integer.parseInt(input.nextLine());

        System.out.println("Enter the minute number:");
        int minute = Integer.parseInt(input.nextLine());

        return LocalTime.of(hour, minute);
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
