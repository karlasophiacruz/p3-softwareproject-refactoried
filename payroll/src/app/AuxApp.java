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

    AuxFunctions auxFunctions;

    public AuxApp(List<Employee> emploList, List<Syndicate> syndiList){
        this.emploList = emploList;
        this.syndiList = syndiList;
        auxFunctions = new AuxFunctions(emploList, syndiList);
    }

    protected int CheckEmployeeList(){
        int cont = 0;
        if(emploList.isEmpty()){
            System.out.println("You don't have employees.");
            return -1;
        } else{
            for(int i = 0 ; i < emploList.size() ; i++){
                if(emploList.get(i).isremoved()){
                    cont++;
                }
            }
            if(cont == emploList.size()){
                System.out.println("You don't have employees.");
                return -1;
            }
        }
        return 0;
    }

    protected void PrintEmployeeList(List<Employee> emploList){
        int empty = CheckEmployeeList();
        if(empty != -1){
            for(int i = 0 ; i < emploList.size() ; i++){
                if(!emploList.get(i).isremoved()){
                    emploList.get(i).printInfo();
                }
            }
        }
    }

    protected void PrintSyndiList(List<Syndicate> syndiList){
        int empty = CheckEmployeeList();
        if(empty != -1){
            for(int i = 0 ; i < syndiList.size() ; i++){
                if(syndiList.get(i).isUnion()){
                    System.out.println(syndiList.get(i).toString());
                }
            }
        }
    }

    protected int WAEmployeeType(Scanner input, Employee test){
        System.out.println("(Please, choose a number)");
        if(test != null){
            if(test.typeofEmployee().equals("Hourly")){
                System.out.println("\n 2 - Salaried;");
                System.out.println(" 3 - Comissioned;");
                System.out.printf(" 0 - Exit.\n");
            } else if(test.typeofEmployee().equals("Salaried")){
                System.out.println("\n 1 - Hourly;");
                System.out.println(" 3 - Comissioned;");
                System.out.printf(" 0 - Exit.\n");
            } else if(test.typeofEmployee().equals("Comissioned")){
                System.out.println("\n 1 - Hourly;");
                System.out.println(" 2 - Salaried;");
                System.out.printf(" 0 - Exit.\n");
            }
        } else{
            System.out.println("\n 1 - Hourly;");
            System.out.println(" 2 - Salaried;");
            System.out.println(" 3 - Comissioned;");
            System.out.printf(" 0 - Exit.\n");
        }

        int type = Integer.parseInt(input.nextLine());

        if(type != 1 && type != 2 && type != 3 && type != 0){
            System.out.println("Sorry, I didn't understand! :(");
            System.out.println("Can you repeat, please?");

            type = WAEmployeeType(input, test);
        }
        return type;
    }
    
    protected Employee AddEmployeeP(int type, String name, String adress, 
    Syndicate unionist, int id, double grossSalary, PaymentData payData, PayMethod payMethod){
        Employee employee = null;
        PayCheck payCheck = null;
        if (type == 1){
            payCheck = new PayCheck(grossSalary, payMethod, "WEEKLY 1 FRIDAY", payData);
            employee = new Hourly(name, adress, unionist, id, payCheck);
        } else if (type == 2){
            payCheck = new PayCheck(grossSalary, payMethod, "MONTHLY $", payData);
            employee = new Salaried(name, adress, unionist, id, payCheck);
        } else if (type == 3){
            payCheck = new PayCheck(grossSalary, payMethod, "WEEKLY 2 FRIDAY", payData);
            employee = new Commissioned(name, adress, unionist, id, payCheck);
        } 
        return employee;
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

    protected Syndicate AddUnionist(Scanner input){
        Syndicate unionist;
        System.out.printf("Please, enter the Monthly Fee:\n");
        System.out.println("(Enter a number)");
        Double monFee = Double.parseDouble(input.nextLine());
        System.out.println();
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
        PrintEmployeeList(emploList);
        System.out.println("Did you found the employee?");
        int answer = auxFunctions.WrongAnswer(input);
        if(answer == 1){
            System.out.println("Now you did, let's restart!");
            return 1;
        }
        return 0;
    }

    protected Employee SearchEmployeeName(Scanner input){
        Employee empaux = null;
        System.out.println("Do you know the employee's name?");
        int answer = auxFunctions.WrongAnswer(input);

        if(answer == 1){
            System.out.printf("Please, enter the employee's name.\n");
            String name = input.nextLine();
            int i = 0;

            while(i < emploList.size()){
                if(emploList.get(i).getName().equals(name)){
                    emploList.get(i).printInfo();
    
                    System.out.println("Is that the employee?");
                    int answer2 = auxFunctions.WrongAnswer(input);
    
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
        int answer2 = auxFunctions.WrongAnswer(input);

        if(answer2 == 1){
            System.out.println("Please, enter the employee's id.");
            int index = Integer.parseInt(input.nextLine());

            if(emploList.get(index).isremoved() == false){
                emploList.get(index).printInfo();
                System.out.println("Is that the employee?");
                int answer = auxFunctions.WrongAnswer(input);

                if(answer == 1){
                    empaux = emploList.get(index);
                }
            }
        }
        return empaux;
    }
 
    protected int SearchEmployee(Scanner input){
        int index = 0, aux = 0, empty = CheckEmployeeList();

        if(empty != -1){
            Employee empaux = SearchEmployeeId(input);
            if(empaux != null){
                index = empaux.getId();
            } if(empaux == null){
                empaux = SearchEmployeeName(input);
            } if(empaux != null){
                index = empaux.getId();
            } if(empaux == null){
                aux = SearchEmployeeList(input);
            } if(aux == 1){
                aux = SearchEmployee(input);
            } else if(aux == 0 && empaux == null){
                System.out.println("Ops! Employee not found.");
                return -1;
            }
            return index;
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

    protected int WAChangeEmployee(Scanner input){
        System.out.println("\n 1 - Name");
        System.out.println(" 2 - Adress");
        System.out.println(" 3 - Type of Employee(Hourly, Salaried, Comissioned)");
        System.out.println(" 4 - Payment Method");
        System.out.println(" 5 - Is a unionist?");
        System.out.println(" 6 - Syndicate's id");
        System.out.println(" 7 - Monthly Fee");
        System.out.println(" 0 - Exit Change Employee's Data");

        int option = Integer.parseInt(input.nextLine());

        if(option != 1 && option != 2 && option != 3 && option != 4 &&
            option != 5 && option != 6 && option != 7 && option != 0){
            System.out.println("Sorry, I didn't understand! :(");
            System.out.println("Can you repeat, please?");
            System.out.println();
            option = WAChangeEmployee(input);
        }
        return option;
    }
    
    protected Syndicate ChangeSyndicalist(Scanner input, int option, Syndicate unionist){
        if(option == 5 || unionist.isUnion() == false){
            unionist.setUnion(!unionist.isUnion());
            System.out.println("Employee's Unionist changed sucessfully!");
        }
        else if(option == 6){
            System.out.println("Enter the new syndicate's id");
            System.out.println("(Please, enter a number)");

            int validId = -1, id, oldId = unionist.getId();
            id = Integer.parseInt(input.nextLine());
            validId = AddUnionist(id, unionist.getMonthlyFee());

            while(validId == -1){
                System.out.println("You can't use this Id!");
                System.out.println("Try again.");
                id = Integer.parseInt(input.nextLine());
                validId = AddUnionist(id, unionist.getMonthlyFee());
            } 

            System.out.println("You can use this Id!");
            unionist = syndiList.get(id);
            syndiList.get(oldId).setMonthlyFee(-1.0);
            syndiList.get(oldId).setUnion(false);
            System.out.println("Syndicate's id changed sucessfully!");
        }
        else if(option == 7){
            System.out.println("Enter the new monthly fee");
            System.out.println("(Please, enter in one just line)");
            Double monFee = Double.parseDouble(input.nextLine());
            unionist.setMonthlyFee(monFee);
            System.out.println("Monthly fee changed sucessfully!");
        }   
        return unionist;
    }

    protected void makeDeductions(int index, Double rateTax){
        double deductions = emploList.get(index).getPayCheck().getDeductions();
        deductions += rateTax;
        if(emploList.get(index).getUnionist() != null){
            if(emploList.get(index).getUnionist().isUnion() == true){
                deductions += emploList.get(index).getUnionist().getMonthlyFee();
                if(emploList.get(index).getUnionist().getServTaxList() != null){
                    for(int i = 0 ; i < emploList.get(index).getUnionist().getServTaxList().size() ; i++){
                        deductions += emploList.get(index).getUnionist().getServTaxList().get(i).getServiceTax();
                    }
                }
            }
        }
        emploList.get(index).getPayCheck().setDeductions(deductions);
    }

}
