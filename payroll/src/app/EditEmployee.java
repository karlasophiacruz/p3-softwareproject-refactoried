package app;

import java.util.List;
import java.util.Scanner;

import model.employee.Employee;
import model.payment.PayMethod;
import model.syndicate.Syndicate;

public class EditEmployee {

    protected List<Employee> emploList;
    protected List<Syndicate> syndiList;

    AuxApp auxApp;
    PaymentApp payApp;

    public EditEmployee(List<Employee> emploList, List<Syndicate> syndiList){
        this.emploList = emploList;
        this.syndiList = syndiList;
        auxApp = new AuxApp(emploList, syndiList);
        payApp = new PaymentApp(emploList, syndiList);
    }
    
    public void ChangeEmployee(Scanner input){
        int index = auxApp.SearchEmployee(input);

        if(index != -1){
            System.out.println("Ok! What data do you wanna change?");
            int option = auxApp.ChooseChangeEmployee(input);
            
            switch (option) {
                case 1:
                    changeName(input, emploList.get(index));
                    System.out.println("Employee's name changed sucessfully!");
                    break;

                case 2:
                    changeAdress(input, emploList.get(index));
                    System.out.println("Employee's adress changed sucessfully!");
                    break;

                case 3:
                    changeType(input, index);
                    break;

                case 4:
                    changePayMethod(input, index);
                    break;

                case 5:
                    changeIsUnion(input, index);
                    break;

                case 6:
                    changeUnionistId(input, index);
                    break;

                case 7:
                    changeMontlyFee(input, index);
                    break;

                default:

                    break;
            }
        }
    }
    
    protected void changeName(Scanner input, Employee employee){
        String name = auxApp.auxFunctions.readString(input, "Enter the employee's name: ");
        employee.setName(name);
    }

    protected void changeAdress(Scanner input, Employee employee){
        String adress = auxApp.auxFunctions.readString(input, "Enter the employee's adress: ");
        employee.setAdress(adress);
    }

    protected void changeGrossSalary(Scanner input, Employee employee){
        Double grossSalary = auxApp.auxFunctions.readDouble(input, "Enter the gross salary: ");
        employee.getPayCheck().setGrossSalary(grossSalary);
    }

    protected void changeType(Scanner input, int index){
        Employee empaux = emploList.get(index);
        System.out.println("Your employee is a " + emploList.get(index).typeofEmployee());

        empaux = auxApp.ChooseEmployeeType(input);
        changeGrossSalary(input, empaux);
        auxApp.paycheckDefault(empaux, empaux.getPayCheck().getPayData(), empaux.getPayCheck().getPayMethod());

        emploList.set(index, empaux);
        System.out.println("Employee's type changed sucessfully!");
    }

    protected void changePayMethod(Scanner input, int index){
        PayMethod payMethod = payApp.ChoosePayMethod(input);
        emploList.get(index).getPayCheck().setPayMethod(payMethod);
        System.out.println("Employee's payment method changed sucessfully!");
    }

    protected int addSyndicate(Scanner input, Employee employee){
        System.out.println("Do you wanna add to the syndicate?");
        int answer3 = auxApp.auxFunctions.readBetween(input, "1 - YES\n2 - NO\n", 1, 2);
        if(answer3 == 1){
            Syndicate unionist = auxApp.AddUnionist(input);
            employee.setUnionist(unionist);
            return 1;
        }
        return 0;
    }

    protected Syndicate turnUnionist(Scanner input, int index){
        Syndicate unionist = emploList.get(index).getUnionist();
        if(unionist == null || unionist.isUnion() == false){
            System.out.println("Your employee isn't a unionist.");
            if(addSyndicate(input, emploList.get(index)) == 1)
                System.out.println("Unionist added sucessfully!");
        }
        return unionist;
    }

    protected void changeIsUnion(Scanner input, int index){
        Syndicate unionist = turnUnionist(input, index);
        if(unionist != null){
            System.out.println("Your employee " + unionist.unionist());
            System.out.println("Do you wanna change?");
            int answer = auxApp.auxFunctions.readBetween(input, "1 - YES\n2 - NO\n", 1, 2);
            if(answer == 1){
                unionist.setUnion(!unionist.isUnion());
                System.out.println("Employee's Unionist changed sucessfully!");
                return;
            }
        }
    }

    protected void changeUnionistId(Scanner input, int index){
        Syndicate unionist = turnUnionist(input, index);
        if(unionist != null){
            System.out.println("The employee's monthly fee is " + unionist.getMonthlyFee());
            System.out.println("Do you wanna change?");
            int answer = auxApp.auxFunctions.readBetween(input, "1 - YES\n2 - NO\n", 1, 2);

            if(answer == 1){
                int validId = -1, id, oldId = unionist.getId();
                id = auxApp.auxFunctions.readInt(input, "Enter the new syndicate's id: ");
                validId = auxApp.AddUnionist(id, unionist.getMonthlyFee());

                while(validId == -1){
                    id = auxApp.auxFunctions.readInt(input, "You can't use this Id!\nTry again.");
                    validId = auxApp.AddUnionist(id, unionist.getMonthlyFee());
                } 

                System.out.println("You can use this Id!");
                unionist = syndiList.get(id);
                syndiList.get(oldId).setMonthlyFee(-1.0);
                syndiList.get(oldId).setUnion(false);
                System.out.println("Syndicate's id changed sucessfully!");
            }
        }
    }

    protected void addMonthlyFee(Scanner input, Syndicate unionist){
        Double monFee = auxApp.auxFunctions.readDouble(input, "Enter the new monthly fee: ");
        unionist.setMonthlyFee(monFee);
    }

    protected void changeMontlyFee(Scanner input, int index){
        Syndicate unionist = turnUnionist(input, index);
        if(unionist != null){
            System.out.println("The employee's monthly fee is " + unionist.getMonthlyFee());
            System.out.println("Do you wanna change?");
            int answer = auxApp.auxFunctions.readBetween(input, "1 - YES\n2 - NO\n", 1, 2);
            if(answer == 1){
                addMonthlyFee(input, unionist);
                System.out.println("Monthly fee changed sucessfully!");
            }
        }
    }
}
