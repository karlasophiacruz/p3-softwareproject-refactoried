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

    public EmployeeApp(List<Employee> emploList, List<Syndicate> syndiList){
        this.emploList = emploList;
        this.syndiList = syndiList;
        auxApp = new AuxApp(emploList, syndiList);
    }

    public void AddEmployee(Scanner input){
        Employee employee = null;
        Syndicate unionist = null;
        int id = emploList.size();

        System.out.println("Ok! Let's add an employee.");
        System.out.println("What type of employee do you wanna add?");
        int type = auxApp.WAEmployeeType(input, employee);

        if(type != 0){
            System.out.println("\nWhat's the employee's name?");
            System.out.printf("(Please, enter in one just line)\n");

            String name = input.nextLine();
            System.out.println();

            System.out.println("What's the employee's adress?");
            System.out.printf("(Please, enter in one just line)\n");

            String adress = input.nextLine();
            System.out.println();

            System.out.println("Is the employee a member of Syndicate?");
            int member = auxApp.auxFunctions.WrongAnswer(input);

            if(member == 1){
                unionist = auxApp.AddUnionist(input);
            }

            PaymentData payData = null;
            PayMethod payMethod = new PayMethod();
    
            System.out.println("\nEnter the gross salary:");
            Double grossSalary = Double.parseDouble(input.nextLine());

            System.out.println("\nChoose the payment method:");
            System.out.println("(Please, enter a number)\n");
            payMethod.PrintPayMethod();

            payMethod.setOption(Integer.parseInt(input.nextLine()));
            
            System.out.println("\nEnter the Bank number:");
            int bank = Integer.parseInt(input.nextLine());

            System.out.println("\nEnter the Agency number:");
            int agency = Integer.parseInt(input.nextLine());

            System.out.println("\nEnter the Account number:");
            int account = Integer.parseInt(input.nextLine());

            payData = new PaymentData(bank, agency, account);

            employee = auxApp.AddEmployeeP(type, name, adress, unionist, id, grossSalary, payData, payMethod);
            emploList.add(employee);
    
            System.out.println("Employee added sucessfully!");
        } else {
            System.out.println("Leaving Add Employee.");
        }
    }

    public void RemoveEmployee(Scanner input){
        int empty = auxApp.CheckEmployeeList();

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

    public void ChangeEmployee(Scanner input){
        int index = auxApp.SearchEmployee(input);
        if(index != -1){
            System.out.println("Ok! What data do you wanna change?");
            System.out.println("(Please, choose a number)");
            int option = auxApp.WAChangeEmployee(input);
            
            if(option == 1){
                System.out.println("Ok! Enter the new employee's name.");
                System.out.printf("(Please, enter in one just line)\n");
                String name = input.nextLine();
                emploList.get(index).setName(name);
                System.out.println("Employee's name changed sucessfully!");
            } 
            else if(option == 2){
                System.out.println("Ok! Enter the new employee's adress.");
                System.out.printf("(Please, enter in one just line)\n");
                String adress = input.nextLine();
                emploList.get(index).setAdress(adress);
                System.out.println("Employee's adress changed sucessfully!");
            } 
            else if(option == 3){
                System.out.println("Your employee is a " + emploList.get(index).typeofEmployee());
                System.out.println("What's the new type?");
                int type = auxApp.WAEmployeeType(input, emploList.get(index));

                System.out.println("\nEnter the new gross salary:");
                Double grossSalary = Double.parseDouble(input.nextLine());

                emploList.get(index).getPayCheck().setGrossSalary(grossSalary);

                if(type != 0){
                    Employee empaux = auxApp.AddEmployeeP(type, emploList.get(index).getName(), 
                    emploList.get(index).getAdress(), emploList.get(index).getUnionist(), index, 
                    emploList.get(index).getPayCheck().getGrossSalary(), emploList.get(index).getPayCheck().getPayData(),
                    emploList.get(index).getPayCheck().getPayMethod());

                    emploList.set(index, empaux);
                    System.out.println("Employee's type changed sucessfully!");
                }
            }
            else if(option == 4){
                PayMethod payMethod = new PayMethod();
                System.out.println("Choose the new Payment Method:");
                System.out.println("(Please, enter a number)\n");
                System.out.println(payMethod.getPayMethod());

                payMethod.setOption(Integer.parseInt(input.nextLine()));

                emploList.get(index).getPayCheck().setPayMethod(payMethod);

                System.out.println("Employee's payment method changed sucessfully!");

            }
            else if(option == 5 || option == 6 || option == 7){
                Syndicate unionist = emploList.get(index).getUnionist();
                if(unionist == null){
                    System.out.println("Your employee isn't a unionist.");
                    System.out.println("Do you wanna add to the syndicate?");
                    int answer3 = auxApp.auxFunctions.WrongAnswer(input);
                    if(answer3 == 1){
                        unionist = auxApp.AddUnionist(input);
                        emploList.get(index).setUnionist(unionist);
                        System.out.println("Unionist added sucessfully!");
                    }
                }
                else if(unionist != null){
                    if(option == 5 || unionist.isUnion() == false){
                        System.out.println("Your employee " + unionist.unionist());
                    }
                    else if(option == 6){
                        System.out.println("The employee's syndicate id is " + unionist.getId());
                    }
                    else if(option == 7){
                        System.out.println("The employee's monthly fee is " + unionist.getMonthlyFee());
                    }
                    System.out.println("Do you wanna change?");
                    int answer = auxApp.auxFunctions.WrongAnswer(input);

                    if(answer == 1){
                        unionist = auxApp.ChangeSyndicalist(input, option, unionist);
                        emploList.get(index).setUnionist(unionist);    
                    }
                }
            }
            else if(option == 0){
                System.out.println("Leaving Change Employee's Data");
            } 
        }
    }
    
    public void SetTimeCard(Scanner input){
        int index = auxApp.SearchEmployee(input);
        if(index != -1){
            if(emploList.get(index).typeofEmployee().equals("Hourly")){
                Hourly hourAux = null;
                System.out.println("Ok! Is today's date?");
                int answer = auxApp.auxFunctions.WrongAnswer(input);
                LocalDate date;
                if(answer == 2){
                    date = auxApp.auxFunctions.SetDate(input);
                } else{
                    date = LocalDate.now();
                }
                System.out.println("Now, enter the Time In:\n");
                LocalTime in = auxApp.auxFunctions.SetTime(input);

                System.out.println("\nEnter the Time Out\n");
                LocalTime out = auxApp.auxFunctions.SetTime(input);

                TimeCard timeCard = new TimeCard(date, in, out);
                
                hourAux = (Hourly)emploList.get(index);
                hourAux.getTimeCardList().add(timeCard);

                System.out.println(hourAux.getTimeCardList());

                System.out.println("Time Card added sucessfully!");
            } else{
                System.out.println("Your employee isn't a Hourly.");
            }
        }
    }

    public void SetSaleResult(Scanner input){
        int index = auxApp.SearchEmployee(input);
        if(index != -1){
            if(emploList.get(index).typeofEmployee().equals("Comissioned")){
                Commissioned comisAux = null;
                System.out.println("Is today's date?");
                int answer = auxApp.auxFunctions.WrongAnswer(input);
                LocalDate saleDate;
                if(answer == 2){
                    saleDate = auxApp.auxFunctions.SetDate(input);
                } else{
                    saleDate = LocalDate.now();
                }
                System.out.println("Enter the sale Value:");
                Double saleValue = Double.parseDouble(input.nextLine());

                SaleReport saleReport = new SaleReport(saleDate, saleValue);

                comisAux = (Commissioned)emploList.get(index);
                comisAux.getSalereportList().add(saleReport);

                System.out.println(comisAux.getSalereportList());

                System.out.println("Sale Report added sucessfully!");
            } else{
                System.out.println("Your employee isn't a Comissioned.");
            }
        }
    }

    public void SetServiceTax(Scanner input){
        int index = auxApp.SearchEmployee(input);
        if(index != -1){
            if(emploList.get(index).getUnionist() != null){
                if(emploList.get(index).getUnionist().isUnion() == true){
                    System.out.println("Is today's date?");
                    int answer = auxApp.auxFunctions.WrongAnswer(input);
                    LocalDate dateInsert;
                    if(answer == 2){
                        dateInsert = auxApp.auxFunctions.SetDate(input);
                    } else{
                        dateInsert = LocalDate.now();
                    }
                    System.out.println("Enter the Service Tax:");
                    Double servTax = Double.parseDouble(input.nextLine());

                    ServiceTax serviceTax = new ServiceTax(servTax, dateInsert);

                    emploList.get(index).getUnionist().getServTaxList().add(serviceTax);

                    System.out.println(emploList.get(index).getUnionist().getServTaxList());

                    System.out.println("Service Tax added sucessfully!");
                } else{
                    System.out.println("Your employee isn't an active unionist.");
                    System.out.println("Do you wanna change?");
                    int answer = auxApp.auxFunctions.WrongAnswer(input);
                    if(answer == 2){
                        emploList.get(index).setUnionist(auxApp.ChangeSyndicalist(input, 5, emploList.get(index).getUnionist()));
                    }
                }
            } else{
                System.out.println("Your employee isn't an unionist.");
            }
        }
    }

    /*public void UndoRedo(Scanner input){
        System.out.println("Do you wanna undo or redo?");
        System.out.println("1 - Undo\n2 - Redo");

        int option = Integer.parseInt(input.nextLine());

        if(option == 1){
            ur.Undo();
        } else if(option == 2){
            ur.Redo();
        } else{
            System.out.println("Sorry, I didn't understand! :(");
            System.out.println("Can you repeat, please?");
            UndoRedo(input);
        }
    }*/
}
