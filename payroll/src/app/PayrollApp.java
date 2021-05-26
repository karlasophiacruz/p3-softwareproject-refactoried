package app;
import java.time.LocalTime;
import java.util.*;

import model.employee.Employee;
import model.syndicate.Syndicate;

public class PayrollApp {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);

        List<Employee> emploList = new ArrayList<>();
        List<Syndicate> syndiList = new ArrayList<>();
   
        EmployeeApp empApp = new EmployeeApp(emploList, syndiList);
        PaymentApp payApp = new PaymentApp(emploList, syndiList);

        int option = -1;
        LocalTime now = LocalTime.now();

        AuxFunctions.clearconsole();

        if(now.getHour() >= 0 && now.getHour() <= 12){
            System.out.println("Hey! Good Morning!");
        } else if(now.getHour() > 12 && now.getHour() < 18){
            System.out.println("Hey! Good Afternoon!");
        } else if(now.getHour() >= 18 && now.getHour() <= 23){
            System.out.println("Hey! Good evening!");
        }
        System.out.println("Welcome to PayRoll App!");
        System.out.println();

        while(option != 0){
            System.out.println();
            System.out.println(" 1 - Play PayRoll's Today");
            System.out.println(" 2 - Add an Employee");
            System.out.println(" 3 - Remove an Employee");
            System.out.println(" 4 - Change an Employee's Data");
            System.out.println(" 5 - Set a Time Card");
            System.out.println(" 6 - Set a Sales Result");
            System.out.println(" 7 - Set a Service Tax");
            System.out.println(" 8 - Payment Schedule");
            System.out.println(" 9 - Create a Payment Schedule");
            System.out.println(" 10 - Show the Employee List");
            System.out.println(" 11 - Show the Unionist List");;
            System.out.println(" 0 - Exit PayRoll");
            System.out.println("\nWhat do you wanna do?");

            option = empApp.auxApp.auxFunctions.readBetween(input, "Chose the option: ", 0, 11);
            AuxFunctions.clearconsole();
            
            switch (option) {
                case 1:
                    payApp.RunPayroll(input);
                    break;
                case 2:
                    empApp.AddEmployee(input);
                    break;
                case 3:
                    empApp.RemoveEmployee(input);
                    break;
                case 4:
                    empApp.editEmp.ChangeEmployee(input);
                    break;
                case 5:
                    empApp.SetTimeCard(input);
                    break;
                case 6:
                    empApp.SetSaleResult(input);
                    break;
                case 7:
                    empApp.SetServiceTax(input);
                    break;
                case 8:
                    payApp.ChangePaymentSchedule(input);
                    break;
                case 9:
                    payApp.CreatePaymentSchedule(input);
                    break;
                case 10:
                    empApp.empList.PrintEmployeeList(emploList);
                    break;
                case 11:
                    empApp.empList.PrintSyndiList();
                    break;
                default:
                    break;
            }
        }
        AuxFunctions.clearconsole();
        input.close();
        System.out.println("See you later! Bye!");
    }
}
