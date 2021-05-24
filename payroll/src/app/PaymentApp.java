package app;

import java.time.LocalDate;
import java.util.*;

import model.employee.Commissioned;
import model.employee.Employee;
import model.employee.Hourly;
import model.employee.Salaried;
import model.payment.PaymentSchedule;
import model.syndicate.Syndicate;

public class PaymentApp {

    PaymentSchedule paySchedule;
    AuxApp auxApp;
    protected List<Employee> emploList;
    protected List<Syndicate> syndiList;

    public PaymentApp(List<Employee> emploList, List<Syndicate> syndiList){
        paySchedule = new PaymentSchedule();
        auxApp = new AuxApp(emploList, syndiList);
        this.emploList = emploList;
        this.syndiList = syndiList;      
    }

    public void CreatePaymentSchedule(Scanner input){
        System.out.println("Enter the new Payment Schedule:\n");
        System.out.println("[Format ('Monthly X') -> month day's number]");
        System.out.println("[Format ('Weekly X week_day') -> In week_day every X weeks]");
        System.out.println("(Enter in one just line)");

        String option = input.nextLine();

        paySchedule.getOptions().add(option);

        paySchedule.printOptions();
        
        System.out.println("Payment Schedule added sucessfully!");
        
    }

    public void ChangePaymentSchedule(Scanner input){
        int index = auxApp.SearchEmployee(input);
        if(index != -1){
            System.out.println("Choose the new Payment Schedule\n");
            paySchedule.printOptions();

            int option = Integer.parseInt(input.nextLine());

            emploList.get(index).getPayCheck().setPaySchedule(paySchedule.getOptions().get(option));

            System.out.println(emploList.get(index).getPayCheck());

            System.out.println("Employee's payment schedule changed sucessfully!");
        }
    }

    public void MakePaycheck(Scanner input, int index){
        System.out.println("PAYING THE EMPLOYEE\n");
        emploList.get(index).printInfo();
        System.out.println("Enter the rate Tax:");
        Double rateTax = Double.parseDouble(input.nextLine());

        auxApp.makeDeductions(index, rateTax);

        if(emploList.get(index).typeofEmployee().equals("Hourly")){
            Hourly hourAux = (Hourly) emploList.get(index);
            hourAux.setExtraHours();
            Double salary = hourAux.getPayCheck().getGrossSalary() * hourAux.getHours();
            Double ehSalary = hourAux.getPayCheck().getGrossSalary() * (1.5) * hourAux.getExtraHours();

            hourAux.getPayCheck().setNetSalary((salary + ehSalary) * (1 - (hourAux.getPayCheck().getDeductions()/100)));  
            
        } else if(emploList.get(index).typeofEmployee().equals("Comissioned")){
            Commissioned comiAux = (Commissioned) emploList.get(index);
            comiAux.setTotalSale();
            Double comiSalary = comiAux.getTotalSale() * (comiAux.getSaleComission()/100);

            comiAux.getPayCheck().setNetSalary((comiAux.getPayCheck().getGrossSalary() + comiSalary) * 
                            (1 - (comiAux.getPayCheck().getDeductions()/100)));

        } else if(emploList.get(index).typeofEmployee().equals("Salaried")){
            Salaried salAux = (Salaried) emploList.get(index);

            Double salary = salAux.getPayCheck().getGrossSalary() * (1 - (salAux.getPayCheck().getDeductions()/100));

            salAux.getPayCheck().setNetSalary(salary);
        }
        emploList.get(index).getPayCheck().setPaid(true);
    }

    public void RunPayroll(Scanner input){
        int check = auxApp.CheckEmployeeList();
        if(check != -1){
            System.out.println("Do you wanna run Pay Roll today?");
            int answer = auxApp.auxFunctions.WrongAnswer(input);
            LocalDate dayToday;

            if(answer == 1){
                dayToday = LocalDate.now();
            } else{
                dayToday = auxApp.auxFunctions.SetDate(input);
            }

            LocalDate lastDaymonth = LocalDate.of(dayToday.getYear(), dayToday.getMonthValue(), dayToday.getMonth().maxLength());

            lastDaymonth = auxApp.auxFunctions.LastUtilDayMonth(lastDaymonth); 

            String dayofweek = dayToday.getDayOfWeek().name();

            for(int j = 0 ; j < emploList.size() ; j++){
                System.out.println("roda");
                if(emploList.get(j).isremoved() == false && emploList.get(j).getPayCheck().isPaid() == false){
                    System.out.println("roda 2");
                    String schedule[] = emploList.get(j).getPayCheck().getPaySchedule().split(" ");
                    if(schedule[0].equals("MONTHLY")){
                        if(schedule[1].equals("$") && dayToday.equals(lastDaymonth)){
                            System.out.println("Today is the last util day of month.");
                            MakePaycheck(input, j);
                            emploList.get(j).getPayCheck().setPaidDay(dayToday);
                            System.out.println(emploList.get(j));
                            emploList.get(j).restartEmployee();
                        } else if(Integer.parseInt(schedule[1]) == dayToday.getDayOfMonth()){
                            System.out.println("Today is " + dayToday);
                            MakePaycheck(input, j);
                            emploList.get(j).getPayCheck().setPaidDay(dayToday);
                            System.out.println(emploList.get(j));
                            emploList.get(j).restartEmployee();
                        }
                    } else if(schedule[0].equals("WEEKLY")){ 
                        int week = Integer.parseInt(schedule[1]);
                        if(schedule[2].equals(dayofweek)){
                            if(week == 1){
                                System.out.println("Today is " + dayofweek);
                                MakePaycheck(input, j);
                                emploList.get(j).getPayCheck().setPaidDay(dayToday);
                                System.out.println(emploList.get(j));
                                emploList.get(j).restartEmployee();
                            } else if(week == 2 && (dayToday.getDayOfMonth()/7) == 2 || (dayToday.getDayOfMonth()/7) == 4){
                                System.out.println("Today is " + dayofweek);
                                MakePaycheck(input, j);
                                emploList.get(j).getPayCheck().setPaidDay(dayToday);
                                System.out.println(emploList.get(j));
                                emploList.get(j).restartEmployee();
                            }
                        }
                    }
                }
            }
        }
    }
}
