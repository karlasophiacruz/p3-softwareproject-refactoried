package model.payment;

public class PayMethod {

    private String payMethod[] = {"Hands Check", "Mail Check", "Bank Deposit"};
    private int option;

    public PayMethod(){
    }

    public int getOption() {
        return option;
    }
    public String[] getPayMethod() {
        return payMethod;
    }

    public void setOption(int option) {
        this.option = option;
    }
    public void setPayMethod(String[] payMethod) {
        this.payMethod = payMethod;
    }

    public void PrintPayMethod() {
        for(int i = 0 ; i < 3 ; i++){
           System.out.println(" " + i + " -> " + this.payMethod[i]);
        }
    }

    @Override
    public String toString() {
       return "\nPayment Method = " + this.payMethod[getOption()] + "\n";
    }
    
}
