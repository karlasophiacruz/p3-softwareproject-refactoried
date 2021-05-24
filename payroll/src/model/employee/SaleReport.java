package model.employee;

import java.time.LocalDate;

public class SaleReport {
    private LocalDate saleDate;
    private Double saleValue;

    public SaleReport(LocalDate saleDate, Double saleValue){
        this.saleDate = saleDate;
        this.saleValue = saleValue;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }
    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public Double getSaleValue() {
        return saleValue;
    }
    public void setSaleValue(Double saleValue) {
        this.saleValue = saleValue;
    }

    @Override
    public String toString() {
        return "\nSALE REPORT\n" +
                "Sale Value = " + getSaleValue() + "  |  Sale Date = " + getSaleDate() + "\n";
    }
}
