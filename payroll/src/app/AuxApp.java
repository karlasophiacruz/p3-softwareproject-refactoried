package app;

import java.util.*;
import model.employee.Commissioned;
import model.employee.Employee;
import model.employee.Hourly;
import model.employee.Salaried;
import model.payment.PayCheck;
import model.payment.PayMethod;
import model.payment.PaymentData;
import model.syndicate.Syndicate;

public class AuxApp {

    protected List<Employee> emploList;
    protected List<Syndicate> syndiList;

    public AuxFunctions auxFunctions;
    EmployeeLists empList;

    public AuxApp(List<Employee> emploList, List<Syndicate> syndiList){
        this.emploList = emploList;
        this.syndiList = syndiList;
        auxFunctions = new AuxFunctions();
        empList = new EmployeeLists(emploList, syndiList);
    }

    public Employee ChooseEmployeeType(Scanner input){
        Employee employee = null;
        System.out.println("\n 1 - Hourly;");
        System.out.println(" 2 - Salaried;");
        System.out.println(" 3 - Comissioned;");

        int type = auxFunctions.readBetween(input,"Choose the type of Employee: ", 1, 3);

        PayCheck payCheck = new PayCheck(0., null, null, null);

        if(type == 1)
            employee = new Hourly("", "", null, 0, payCheck, false);
        if(type == 2)
            employee = new Salaried("", "", null, 0, payCheck, false);
        if(type == 3){
            Double commission = auxFunctions.readDouble(input, "Enter the Sale Commission: ");
            employee = new Commissioned("", "", null, 0, payCheck, commission);
        }

        return employee;
    }
    
    public void paycheckDefault(Employee employee, PaymentData payData, PayMethod payMethod){
        PayCheck payCheck = null;
        if (employee instanceof Hourly){
            payCheck = new PayCheck(employee.getPayCheck().getGrossSalary(), payMethod, "WEEKLY FRIDAY", payData);
        } else if (employee instanceof Salaried){
            payCheck = new PayCheck(employee.getPayCheck().getGrossSalary(), payMethod, "MONTHLY $", payData);
        } else if (employee instanceof Commissioned){

            payCheck = new PayCheck(employee.getPayCheck().getGrossSalary(), payMethod, "BIWEEKLY FRIDAY", payData);
        } 
        employee.setPayCheck(payCheck);
    }

    protected int SearchSyndiId(int index){
        if(index == syndiList.size()){
            return -4;
        } if(index > syndiList.size()){
            return -2;
        } if(syndiList.get(index).getMonthlyFee() == -1.0){
            return index;
        }
        return -1;
    }

    protected int SearchSyndiId(){
        int i = 0;
        while(i < syndiList.size()){
            int aux = SearchSyndiId(i);
            if(aux != -1){
                return aux;
            }
            i++;
        }
        return i;
    }

    protected int AddUnionist(int index, Double monfee){
        Syndicate unionist;
        int check = SearchSyndiId(index);
        if(check == -2){
            unionist = new Syndicate(-1.0, false, syndiList.size());
            syndiList.add(syndiList.size(), unionist);
            check = AddUnionist(index, monfee);
        } else if(check == index){
            unionist = new Syndicate(monfee, true, index);
            syndiList.set(index, unionist);
        } else if(check == -4){
            unionist = new Syndicate(monfee, true, index);
            syndiList.add(index, unionist);
        } else if(check == -1){
            return -1;
        }
        return -3;
    }

    public Syndicate AddUnionist(Scanner input){
        Syndicate unionist;
        Double monFee = auxFunctions.readDouble(input, "Please, enter the Monthly Fee: ");
        int index = SearchSyndiId();
        unionist = new Syndicate(monFee, true, index);
        if(syndiList.size() != index){
            syndiList.set(index, unionist);
        } else{
            syndiList.add(unionist);
        }
        
        return unionist;
    }

    protected int SearchEmployeeList(Scanner input){
        System.out.println("I will show the Employee List");
        empList.PrintEmployeeList(emploList);
        System.out.println("Did you found the employee?");
        int answer = auxFunctions.readBetween(input, "1 - YES\n2 - NO\n", 1, 2);
        if(answer == 1){
            System.out.println("Now you did, let's restart!");
            return 1;
        }
        return 0;
    }

    protected Employee SearchEmployeeName(Scanner input){
        Employee empaux = null;
        System.out.println("Do you know the employee's name?");
        int answer = auxFunctions.readBetween(input, "1 - YES\n2 - NO\n", 1, 2);

        if(answer == 1){
            String name = auxFunctions.readString(input, "Please, enter the employee's name: ");
            int i = 0;

            while(i < emploList.size()){
                if(emploList.get(i).getName().equals(name)){
                    emploList.get(i).printInfo();
    
                    int answer2 = auxFunctions.IstheEmployee(input);
    
                    if(answer2 == 1){
                        empaux = emploList.get(i);
                        break;
                    }
                }
                i++;
            }
        }
        return empaux;
    }

    protected Employee SearchEmployeeId(Scanner input){
        Employee empaux = null;
        System.out.println("Do you know the employee's id?");
        int answer2 = auxFunctions.readBetween(input, " 1 - YES\n 2 - NO\n", 1, 2);

        if(answer2 == 1){
            int index = auxFunctions.readInt(input, "Please, enter the employee's id: ");

            if(emploList.get(index).isremoved() == false){
                emploList.get(index).printInfo();

                int answer = auxFunctions.IstheEmployee(input);
                if(answer == 1){
                    empaux = emploList.get(index);
                }
            }
        }
        return empaux;
    }
 
    public int SearchEmployee(Scanner input){
        int aux = 0, empty = empList.CheckEmployeeList(emploList);

        if(empty != -1){
            Employee empaux = SearchEmployeeId(input);
            if(empaux == null)
                empaux = SearchEmployeeName(input);
            if(empaux == null){
                aux = SearchEmployeeList(input);
                if(aux == 0 && empaux == null){
                    System.out.println("Ops! Employee not found.");
                    return -1;
                }
                aux = SearchEmployee(input);
            }
            return empaux.getId();
        }
        return -1;
    }
    
    protected int RemovewithId(Scanner input){
        int removed = 0;

        Employee empaux = SearchEmployeeId(input);

        if(empaux != null){
            if((empaux.getUnionist() != null)){
                emploList.get(empaux.getId()).getUnionist().setUnion(false);
            }
            emploList.get(empaux.getId()).setremoved(true);
            System.out.println("Employee removed sucessfully!");
            removed = 1;
        }
        return removed;
    }

    protected int RemovewithName(Scanner input){
        int removed = 0;

        Employee empaux = SearchEmployeeName(input);

        if(empaux != null){
            if((empaux.getUnionist() != null)){
                emploList.get(empaux.getId()).getUnionist().setUnion(false);
            }
            emploList.get(empaux.getId()).setremoved(true);
            System.out.println("Employee removed sucessfully!");
            removed = 1;
        }
        return removed;
    }

    public int ChooseChangeEmployee(Scanner input){
        String message = auxFunctions.printChangeEmployee();

        return auxFunctions.readBetween(input, message, 0, 7);
    }
}
