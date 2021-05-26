package model.payment;

import java.util.ArrayList;
import java.util.List;

public class PaymentSchedule {
    private List<String> options = new ArrayList<>();

    public PaymentSchedule(){
        this.options.add("MONTLHY $");
        this.options.add("BIWEEKLY FRIDAY");
        this.options.add("WEEKLY FRIDAY");
    }

    public List<String> getOptions() {
        return options;
    }
    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void printOptions(){
        System.out.println("PAYMENT SCHEDULE\n");
        for(int i = 0; i < getOptions().size() ; i++){
            System.out.println(" " + i + " -> " + getOptions().get(i));
        }
    }
}
