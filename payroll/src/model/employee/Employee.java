package model.employee;

import model.syndicate.Syndicate;

import model.payment.PayCheck;

public abstract class Employee {
    private String name, adress;
    private Syndicate unionist;
    private int id;
    private boolean removed;
    private PayCheck payCheck; 

    public Employee(String name, String adress, Syndicate unionist, int id, PayCheck payCheck, boolean removed) {
        this.name = name;
        this.adress = adress;
        this.unionist = unionist;
        this.id = id;
        this.removed = removed;
        this.payCheck = payCheck;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAdress() {
        return adress;
    }
    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Syndicate getUnionist() {
        return unionist;
    }
    public void setUnionist(Syndicate unionist) {
        this.unionist = unionist;
    }

    public boolean isremoved() {
        return removed;
    }
    public void setremoved(boolean removed) {
        this.removed = removed;
    }

    public String typeofEmployee(){
        return "Employee";    
    }

    public PayCheck getPayCheck() {
        return payCheck;
    }
    public void setPayCheck(PayCheck payCheck) {
        this.payCheck = payCheck;
    }

    public String printInfo(){
        return "\n------------------EMPLOYEE----------------\n" +
                "    Name = " + this.getName() +
                "    Type of Employee = " + typeofEmployee() +
                "    Id = " + this.getId();
    }

    public void restartEmployee(){
        getPayCheck().setNetSalary(0.);
        getPayCheck().setPaid(false);
    }
   
    @Override
    public String toString() {
        System.out.println("------------------------------------------\n");
        System.out.println(this.printInfo());

        if(this.getUnionist() != null){
            System.out.println("\n" + getUnionist() + "\n");
        }
        return  "\n" + getPayCheck() +
                "\n-------------------------------------------\n";
    }
}
