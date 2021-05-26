package model.syndicate;

import java.util.*;

public class Syndicate {
    private Double monthlyFee;
    private boolean union;
    private int id;

    private List<ServiceTax> servTaxList = new ArrayList<>();

    public Syndicate(Double monthlyFee, boolean union, int id){
        this.setUnion(union);
        this.setMonthlyFee(monthlyFee);
        this.setId(id);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Double getMonthlyFee() {
        return monthlyFee;
    }
    public void setMonthlyFee(Double monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    public boolean isUnion() {
        return union;
    }
    public void setUnion(boolean union) {
        this.union = union;
    }
    

    public String unionist(){
        if(isUnion()){
            return "is a unionist.";
        } else{
            return "isn't a unionist.";
        }
    }

    public List<ServiceTax> getServTaxList() {
        return servTaxList;
    }
    public void setServTaxList(List<ServiceTax> servTaxList) {
        this.servTaxList = servTaxList;
    }
    
    @Override
    public String toString() {
        return "\nUNIONIST\n" + "Syndicate's Id = " + this.getId() + "\nMonthlyFee = " + this.getMonthlyFee()
        + "\nIsUnion? " + this.isUnion() + "\n";
    }
}
