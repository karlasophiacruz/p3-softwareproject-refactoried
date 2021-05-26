package app;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import model.employee.Commissioned;
import model.employee.Employee;
import model.employee.Hourly;
import model.employee.SaleReport;
import model.employee.TimeCard;
import model.payment.PayMethod;
import model.payment.PaymentData;
import model.syndicate.ServiceTax;
import model.syndicate.Syndicate;

public class EmployeeApp {
    
    protected List<Employee> emploList;
    protected List<Syndicate> syndiList;

    AuxApp auxApp;
    PaymentApp payApp;
    EditEmployee editEmp;
    EmployeeLists empList;

    public EmployeeApp(List<Employee> emploList, List<Syndicate> syndiList){
        this.emploList = emploList;
        this.syndiList = syndiList;
        auxApp = new AuxApp(emploList, syndiList);
        payApp = new PaymentApp(emploList, syndiList);
        editEmp = new EditEmployee(emploList, syndiList);
        empList = new EmployeeLists(emploList, syndiList);
    }

    public void AddEmployee(Scanner input){
        Employee employee = null;

        System.out.println("Ok! Let's add an employee.");
        System.out.println("What type of employee do you wanna add?");
        employee = auxApp.ChooseEmployeeType(input);
        editEmp.changeName(input, employee);
        editEmp.changeAdress(input, employee);
        editEmp.changeGrossSalary(input, employee);
        editEmp.addSyndicate(input, employee);
        employee.setId(emploList.size());

        PayMethod payMethod = payApp.ChoosePayMethod(input);
        PaymentData payData = payApp.CreatePaymentData(input);

        auxApp.paycheckDefault(employee, payData, payMethod);
        
        emploList.add(employee);
        System.out.println("Employee added sucessfully!");
        return;
    }

    public void RemoveEmployee(Scanner input){
        int empty = empList.CheckEmployeeList(emploList);

        if(empty != -1){
            int removed = auxApp.RemovewithId(input);
            if(removed == 0){
                removed = auxApp.RemovewithName(input);    
            } if(removed == 0){
                removed = auxApp.SearchEmployeeList(input);
                if(removed == 0){
                    System.out.println("Ops! Employee not found.");
                    System.out.println("Leaving Remove Employee.");
                } if(removed == 1){
                    RemoveEmployee(input);
                }
            }
        }
    }
    
    public void SetTimeCard(Scanner input){
        int index = auxApp.SearchEmployee(input);
        if(index != -1){
            if(emploList.get(index) instanceof Hourly){
                Hourly hourAux = null;

                LocalDate date = auxApp.auxFunctions.CheckDate(input);
                
                LocalTime in = auxApp.auxFunctions.SetTime(input, "Enter the Time In: ");
                LocalTime out = auxApp.auxFunctions.SetTime(input, "Enter the Time Out: ");

                TimeCard timeCard = new TimeCard(date, in, out);
                
                hourAux = (Hourly)emploList.get(index);
                hourAux.getTimeCardList().add(timeCard);

                System.out.println(hourAux.getTimeCardList());

                System.out.println("Time Card added sucessfully!");
                return;
            }
            System.out.println("Your employee isn't a Hourly.");
        }
    }

    public void SetSaleResult(Scanner input){
        int index = auxApp.SearchEmployee(input);
        if(index != -1){
            if(emploList.get(index) instanceof Commissioned){
                Commissioned comisAux = null;

                LocalDate saleDate = auxApp.auxFunctions.CheckDate(input);

                Double saleValue = auxApp.auxFunctions.readDouble(input, "Enter the Sale Value: ");

                SaleReport saleReport = new SaleReport(saleDate, saleValue);

                comisAux = (Commissioned)emploList.get(index);
                comisAux.getSalereportList().add(saleReport);

                System.out.println(comisAux.getSalereportList());

                System.out.println("Sale Report added sucessfully!");
                return;
            }
            System.out.println("Your employee isn't a Comissioned.");
        }
    }

    public void SetServiceTax(Scanner input){
        int index = auxApp.SearchEmployee(input);
        if(index != -1){
            if(emploList.get(index).getUnionist() != null){
                if(emploList.get(index).getUnionist().isUnion() == true){
                    LocalDate dateInsert = auxApp.auxFunctions.CheckDate(input);

                    Double servTax = auxApp.auxFunctions.readDouble(input, "Enter the Service Tax: ");

                    ServiceTax serviceTax = new ServiceTax(servTax, dateInsert);

                    emploList.get(index).getUnionist().getServTaxList().add(serviceTax);

                    System.out.println(emploList.get(index).getUnionist().getServTaxList());

                    System.out.println("Service Tax added sucessfully!");
                    return;
                }
                System.out.println("Your employee isn't an active unionist.");
                System.out.println("Do you wanna change?");
                int answer = auxApp.auxFunctions.readBetween(input, "1 - YES\n2 - NO\n", 1, 2);

                if(answer == 2)
                    editEmp.changeIsUnion(input, index);
            }
        } else
            System.out.println("Your employee isn't an unionist.");
    }
}
