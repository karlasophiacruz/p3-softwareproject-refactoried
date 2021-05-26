package app;

import java.time.LocalDate;
import java.util.*;

import app.scheduleManager.*;
import model.employee.Commissioned;
import model.employee.Employee;
import model.employee.Hourly;
import model.payment.PayMethod;
import model.payment.PaymentData;
import model.payment.PaymentSchedule;
import model.syndicate.Syndicate;

public class PaymentApp {

    PaymentSchedule paySchedule;
    AuxApp auxApp;
    EmployeeLists empList;
    protected List<Employee> emploList;
    protected List<Syndicate> syndiList;

    public PaymentApp(List<Employee> emploList, List<Syndicate> syndiList){
        paySchedule = new PaymentSchedule();
        auxApp = new AuxApp(emploList, syndiList);
        empList = new EmployeeLists(emploList, syndiList);
        this.emploList = emploList;
        this.syndiList = syndiList;      
    }

    public PayMethod ChoosePayMethod(Scanner input){
        PayMethod payMethod = new PayMethod();

        payMethod.PrintPayMethod();
        payMethod.setOption(auxApp.auxFunctions.readInt(input, "Choose the payment method: "));

        return payMethod;
    }

    protected PaymentData CreatePaymentData(Scanner input){
        PaymentData payData = null;
        
        int bank = auxApp.auxFunctions.readInt(input, "Enter the Bank number: ");
        int agency = auxApp.auxFunctions.readInt(input, "Enter the Agency number: ");
        int account = auxApp.auxFunctions.readInt(input, "Enter the Account number: ");

        payData = new PaymentData(bank, agency, account);

        return payData;
    }

    public void CreatePaymentSchedule(Scanner input){
        System.out.println("[Format ('MONTHLY X') -> Month's day number]");
        System.out.println("[Format ('WEEKLY week_day') -> in week_day once a week]");
        System.out.println("[Format ('BIWEEKLY week_day') -> in week_day twice a week]");
        String option = auxApp.auxFunctions.readString(input, "Enter the new Payment Schedule: ");

        paySchedule.getOptions().add(option);

        paySchedule.printOptions();

        System.out.println("Payment Schedule added sucessfully!"); 
    }

    public void ChangePaymentSchedule(Scanner input){
        int index = auxApp.SearchEmployee(input);
        if(index != -1){
            paySchedule.printOptions();
            int option = auxApp.auxFunctions.readInt(input, "Choose the new Payment Schedule: ");

            emploList.get(index).getPayCheck().setPaySchedule(paySchedule.getOptions().get(option));

            System.out.println(emploList.get(index).getPayCheck());

            System.out.println("Employee's payment schedule changed sucessfully!");
        }
    }

    protected void makeDeductions(int index, Double rateTax){
        double deductions = emploList.get(index).getPayCheck().getDeductions();
        deductions += rateTax;
        if(emploList.get(index).getUnionist() != null){
            if(emploList.get(index).getUnionist().isUnion() == true && 
                                emploList.get(index).getUnionist().getServTaxList() != null){

                deductions += emploList.get(index).getUnionist().getMonthlyFee();

                for(int i = 0 ; i < emploList.get(index).getUnionist().getServTaxList().size() ; i++){
                    deductions += emploList.get(index).getUnionist().getServTaxList().get(i).getServiceTax();
                }
            }
        }
        emploList.get(index).getPayCheck().setDeductions(deductions);
    }

    protected Double calcNetSalary(Employee employee){
        return employee.getPayCheck().getGrossSalary() * (1 - (employee.getPayCheck().getDeductions()/100));
    }

    public void MakePaycheck(Scanner input, int index){
        System.out.println("PAYING THE EMPLOYEE\n");
        emploList.get(index).printInfo();

        Double rateTax = auxApp.auxFunctions.readDouble(input, "Enter the rate Tax: ");
        Double ehsalary = 0.;

        makeDeductions(index, rateTax);

        if(emploList.get(index) instanceof Hourly){
            Hourly hourAux = (Hourly) emploList.get(index);
            hourAux.setExtraHours();

            Double salary = hourAux.getPayCheck().getGrossSalary() * hourAux.getHours();
            ehsalary = hourAux.getPayCheck().getGrossSalary() * (1.5) * hourAux.getExtraHours();
            hourAux.getPayCheck().setGrossSalary(salary);          
        } 
        else if(emploList.get(index) instanceof Commissioned){
            Commissioned comiAux = (Commissioned) emploList.get(index);
            comiAux.setTotalSale();

            ehsalary = comiAux.getTotalSale() * (comiAux.getSaleComission()/100);
        } 
        emploList.get(index).getPayCheck().setGrossSalary(emploList.get(index).getPayCheck().getGrossSalary() + ehsalary);
        emploList.get(index).getPayCheck().setNetSalary(calcNetSalary(emploList.get(index)));
        emploList.get(index).getPayCheck().setPaid(true);
    }

    protected void payingEmployee(Scanner input, int index){
        LocalDate dayofpayment = LocalDate.now();
        MakePaycheck(input, index);
        emploList.get(index).getPayCheck().setPaidDay(dayofpayment);
        System.out.println(emploList.get(index));
        emploList.get(index).restartEmployee();
    }

    public void RunPayroll(Scanner input){
        int check = empList.CheckEmployeeList(emploList);
        ScheduleType scheduleType = null;

        if(check != -1){
            for(int j = 0 ; j < emploList.size() ; j++){
                if(emploList.get(j).isremoved() == false && emploList.get(j).getPayCheck().isPaid() == false){
                    String schedule[] = emploList.get(j).getPayCheck().getPaySchedule().split(" ");

                    if(schedule[0].equals("MONTHLY"))
                        scheduleType = new MonthlySchedule();
 
                    else if(schedule[0].equals("WEEKLY")) 
                        scheduleType = new WeeklySchedule();

                    else if(schedule[0].equals("BIWEEKLY"))
                        scheduleType = new BiweeklySchedule();

                    if(scheduleType.execute(input, schedule[1], auxApp.auxFunctions))
                        payingEmployee(input, j);
                }
            }
        }
    }
}
