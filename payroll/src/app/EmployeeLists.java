package app;

import java.util.*;

import model.employee.Commissioned;
import model.employee.Employee;
import model.employee.Hourly;
import model.employee.Salaried;
import model.syndicate.Syndicate;

public class EmployeeLists {

    protected List<Employee> emploList;
    protected List<Syndicate> syndiList;

    public EmployeeLists(List<Employee> emploList, List<Syndicate> syndiList){
        this.emploList = emploList;
        this.syndiList = syndiList;
    }

    protected int CheckEmployeeList(List<Employee> emploList){
        int cont = 0;
        if(emploList.isEmpty()){
            System.out.println("You don't have employees.");
            return -1;
        }
        for(int i = 0 ; i < emploList.size() ; i++){
            if(emploList.get(i).isremoved())
                cont++;
        }
        if(cont == emploList.size()){
            System.out.println("You don't have employees.");
            return -1;
        }
        return 0;
    }

    protected void PrintEmployeeList(List<Employee> emploList){
        int empty = CheckEmployeeList(emploList);
        if(empty != -1){
            for(int i = 0 ; i < emploList.size() ; i++){
                if(!emploList.get(i).isremoved()){
                    System.out.println(emploList.get(i).printInfo());
                }
            }
        }
    }

    protected void PrintSyndiList(){
        int empty = CheckEmployeeList(emploList);
        if(empty != -1){
            for(int i = 0 ; i < syndiList.size() ; i++){
                if(syndiList.get(i).isUnion())
                    System.out.println(syndiList.get(i).toString());
            }
        }
    }

    protected List<Employee> SalariedList(){
        List<Employee> salaList = new ArrayList<>();
        int empty = CheckEmployeeList(emploList);
        if(empty != -1){
            for(int i = 0 ; i < emploList.size() ; i++){
                if(!emploList.get(i).isremoved() && emploList.get(i) instanceof Salaried)
                    salaList.add(emploList.get(i));
            }   
        }
        return salaList; 
    }

    protected List<Employee> HourList(){
        List<Employee> hourList = new ArrayList<>();
        int empty = CheckEmployeeList(emploList);
        if(empty != -1){
            for(int i = 0 ; i < emploList.size() ; i++){
                if(!emploList.get(i).isremoved() && emploList.get(i) instanceof Hourly)
                    hourList.add(emploList.get(i));
            }   
        }
        return hourList; 
    }

    protected List<Employee> ComissionedList(){
        List<Employee> comiList = new ArrayList<>();
        int empty = CheckEmployeeList(emploList);
        if(empty != -1){
            for(int i = 0 ; i < emploList.size() ; i++){
                if(!emploList.get(i).isremoved() && emploList.get(i) instanceof Commissioned)
                    comiList.add(emploList.get(i));
            }   
        }
        return comiList; 
    }
}
