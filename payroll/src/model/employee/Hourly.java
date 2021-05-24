package model.employee;

import model.payment.PayCheck;
import model.syndicate.Syndicate;

import java.util.*;

public class Hourly extends Employee {

    private int extraHours, hours;

    private List<TimeCard> timeCardList = new ArrayList<>();

    public Hourly(String name, String adress, Syndicate unionist, int id, PayCheck payCheck) {
        super(name, adress, unionist, id, payCheck);
        this.extraHours = 0;
        hours = 0;
    }

    public Hourly(String name, String adress, Syndicate unionist, int id, PayCheck payCheck, 
                                        boolean removed, int extraHours, int hours) {
        super(name, adress, unionist, id, payCheck, removed);
        this.extraHours = extraHours;
        this.hours = hours;
    }

    public int getExtraHours() {
        return extraHours;
    }
    public void setExtraHours() {
        for(int i = 0 ; i < getTimeCardList().size() ; i++){
            this.extraHours += (getTimeCardList().get(i).getHourWorked() - 8);
            getTimeCardList().get(i).setHourWorked(8);
            hours += getTimeCardList().get(i).getHourWorked(); 
        }
    }

    public List<TimeCard> getTimeCardList() {
        return timeCardList;
    }

    public void setTimeCardList(List<TimeCard> timeCardList) {
        this.timeCardList = timeCardList;
    }

    public void printTimeCard(){
        System.out.println(getTimeCardList());
    }

    public int getHours() {
        return hours;
    }
    public void setHours(int hours) {
        this.hours = hours;
    }

    @Override
    public void restartEmployee() {
        super.restartEmployee();
        this.extraHours = 0;
        this.hours = 0;
        this.getTimeCardList().clear();
    }

    @Override
    public String typeofEmployee(){
        return "Hourly";
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
