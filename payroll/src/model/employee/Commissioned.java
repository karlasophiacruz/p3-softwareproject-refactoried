package model.employee;

import model.payment.PayCheck;
import model.syndicate.Syndicate;
import java.util.*;

public class Commissioned extends Employee {
    private Double saleComission, totalSale;
    private List<SaleReport> salereportList = new ArrayList<>();
    
    public Commissioned(String name, String adress, Syndicate unionist, int id, PayCheck payCheck, Double saleCommission){
        super(name, adress, unionist, id, payCheck, false);
        this.totalSale = 0.;
        this.saleComission = saleCommission;
    }

    public Double getSaleComission() {
        return saleComission;
    }
    public void setSaleComission(Double saleComission) {
        this.saleComission = saleComission;
    }

    public List<SaleReport> getSalereportList() {
        return salereportList;
    }
    public void setSalereportList(List<SaleReport> salereportList) {
        this.salereportList = salereportList;
    }

    public Double getTotalSale() {
        return totalSale;
    }
    public void setTotalSale() {
        if(getSalereportList() != null){
            for(int i = 0 ; i < getSalereportList().size() ; i++){
                totalSale += getSalereportList().get(i).getSaleValue();
            }
        }
    }

    @Override
    public void restartEmployee() {
        super.restartEmployee();
        this.totalSale = 0.0;
        this.getSalereportList().clear();
    }
    
    @Override
    public String typeofEmployee(){
        return "Comissioned";
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
