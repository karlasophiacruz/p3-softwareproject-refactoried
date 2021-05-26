package model.payment;

import java.time.LocalDate;

public class PayCheck {
    
    private Double netSalary, grossSalary, deductions;
    private LocalDate paidDay;
    private String paySchedule;
    private PayMethod payMethod;
    private PaymentData payData;
    private boolean paid;
    

    public PayCheck(Double grossSalary, PayMethod payMethod, String paySchedule, PaymentData payData){
        this.deductions = 0.0;
        this.grossSalary = grossSalary;
        this.netSalary = grossSalary;
        this.payMethod = payMethod;
        this.paySchedule = paySchedule;
        this.payData = payData;
        this.paid = false;
    }

    public Double getGrossSalary() {
        return grossSalary;
    }
    public void setGrossSalary(Double grossSalary) {
        this.grossSalary = grossSalary;
    }

    public Double getNetSalary() {
        return netSalary;
    }
    public void setNetSalary(Double netSalary) {
        this.netSalary = netSalary;
    }

    public LocalDate getPaidDay() {
        return paidDay;
    }
    public void setPaidDay(LocalDate paidDay) {
        this.paidDay = paidDay;
    }

    public PayMethod getPayMethod() {
        return payMethod;
    }
    public void setPayMethod(PayMethod payMethod) {
        this.payMethod = payMethod;
    }

    public String getPaySchedule() {
        return paySchedule;
    }
    public void setPaySchedule(String paySchedule) {
        this.paySchedule = paySchedule;
    }

    public Double getDeductions() {
        return deductions;
    }
    public void setDeductions(Double deductions) {
        this.deductions = deductions;
    }

    public PaymentData getPayData() {
        return payData;
    }
    public void setPayData(PaymentData payData) {
        this.payData = payData;
    }

    public boolean isPaid() {
        return paid;
    }
    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String printNetSalary() {
        if(isPaid()){
            return "    |   NetSalary = " + getNetSalary();
        }
        return "";
    }

    @Override
    public String toString() {
        return "\nPAYMENT CHECK \n" +
                "Payment Day = " + getPaidDay() +
                "\nGross salary = " + getGrossSalary() + printNetSalary() +
                "\nPayment Schedule = " + getPaySchedule() + getPayData() + getPayMethod();
    }
}
