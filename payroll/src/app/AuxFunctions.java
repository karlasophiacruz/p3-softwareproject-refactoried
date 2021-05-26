package app;

import java.io.IOException;
import java.time.*;
import java.util.*;

public class AuxFunctions {

    public static void clearconsole(){
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    protected void printMessage(String message){
        if(!message.isEmpty())
            System.out.print(message);
    }

    protected boolean isInt(String in){
        return in != null && in.matches("\\d+");
    }

    protected boolean isDouble(String in){
        return in != null && in.matches("\\d+(\\.\\d+)?");
    }

    public String readString(Scanner input, String message){
        while(true){
            printMessage(message);

            String in = input.nextLine();
            try {
                if(in.length() > 0){
                    System.out.println();
                    return in;
                }
                printMessage("Sorry, I didn't understand! :(\nPlease, enter something.\n\n");
            } catch(InputMismatchException ex){
                printMessage("Sorry, I didn't understand! :(\nPlease, enter something.\n\n");
            }
        }
    }

    protected int readInt(Scanner input, String message){
        while(true){
            printMessage(message);

            String in = input.nextLine();

            if(isInt(in)) {
                System.out.println();
                try {
                    return Integer.parseInt(in);
                } catch(NumberFormatException ex) {
                    printMessage("Sorry, I didn't understand! :(\nPlease, enter an int number.\n\n");
                }
            }
            printMessage("Sorry, I didn't understand! :(\nPlease, enter an int number.\n\n");
        }
    }

    public Double readDouble(Scanner input, String message){
        while(true){
            printMessage(message);

            String in = input.nextLine();

            if(isDouble(in)){
                System.out.println();
                try {
                    return Double.parseDouble(in);
                } catch(NumberFormatException ex) {
                    printMessage("Sorry, I didn't understand! :(\nPlease, enter a double number.\n\n");
                }
            }
            printMessage("Sorry, I didn't understand! :(\nPlease, enter a double number.\n\n");
        }  
    }

    public int readBetween(Scanner input, String message, int start, int end){
        while(true){
            int option = readInt(input, message);

            if(option >= start && option <= end){
                System.out.println();
                return option;
            }
            System.out.println("Sorry, I didn't understand! :(");
            System.out.println("Please, enter an int number between " + start + " and " + end + "\n");
        } 
    }

    protected LocalDate SetDate(Scanner input){
        while(true){
            try {
                int day = readBetween(input, "Enter the day number:", 1, 31);
                int month = readBetween(input, "Enter the month number:", 1, 12);
                int year = readInt(input, "Enter the year number:");

                LocalDate date = LocalDate.of(year, month, day);
                return date;
            } 
            catch (DateTimeException ex){
                printMessage("The date isn't valid. :(\nLet's start over!\n\n");
            }
        }
    }

    protected LocalTime SetTime(Scanner input, String message){
        while(true) {
            printMessage(message);
            System.out.println("Format 24-hour clock");
            try {
                int hour = readBetween(input, "Enter the hour number:", 0, 23);
                int minute = readBetween(input, "Enter the minute number:", 0, 59);

                LocalTime time = LocalTime.of(hour, minute);
                return time;
            }
            catch (Exception ex){
                printMessage("The time isn't valid. :(\nLet's start over!\n\n");
            }
        }
    }

    public LocalDate CheckDate(Scanner input){
        System.out.println("Is today's date?");
        int answer = readBetween(input, "1 - YES\n2 - NO\n", 1, 2);
        if(answer == 2)
            return SetDate(input);
        return LocalDate.now();
    }

    protected int IstheEmployee(Scanner input){
        System.out.println("Is that the employee?");
        return readBetween(input, "1 - YES\n2 - NO\n", 1, 2);
    }

    protected String printChangeEmployee(){
        return "1 - Name\n" + " 2 - Adress\n" + " 3 - Type of Employee(Hourly, Salaried, Comissioned)\n" +
                " 4 - Payment Method\n" + " 5 - Is a unionist?\n" + " 6 - Syndicate's id\n" + " 7 - Monthly Fee\n" +
                " 0 - Exit Change Employee's Data";
    }
}
