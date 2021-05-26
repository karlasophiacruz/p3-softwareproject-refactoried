package model.employee;

import model.payment.PayCheck;
import model.syndicate.Syndicate;

public class Salaried extends Employee {

    public Salaried(String name, String adress, Syndicate unionist, int id, PayCheck payCheck, boolean removed){
        super(name, adress, unionist, id, payCheck, removed);
    }

    @Override
    public String typeofEmployee(){
        return "Salaried";
    }

    @Override
    public String toString() {
        return super.toString();
    }
    
}