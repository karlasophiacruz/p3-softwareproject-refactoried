package model.syndicate;

import java.time.LocalDate;

public class ServiceTax {
    private Double serviceTax;
    private LocalDate dateInsert;

    public ServiceTax(Double serviceTax, LocalDate dateInsert){
        this.serviceTax = serviceTax;
        this.dateInsert = dateInsert;
    }

    public LocalDate getDateInsert() {
        return dateInsert;
    }
    public void setDateInsert(LocalDate dateInsert) {
        this.dateInsert = dateInsert;
    }

    public Double getServiceTax() {
        return serviceTax;
    }
    public void setServiceTax(Double serviceTax) {
        this.serviceTax = serviceTax;
    }

    @Override
    public String toString() {
        return "SERVICE TAX\n" +
                "Service Tax = " + getServiceTax() + " Date Insert = " + getDateInsert() + "\n";
    }
}
