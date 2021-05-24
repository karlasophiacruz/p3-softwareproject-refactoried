package model.payment;

public class PaymentData {

    private int bank, agency, account;

    public PaymentData(int bank, int agency, int account){
        this.bank = bank;
        this.agency = agency;
        this.account = account;
    }

    public int getAccount() {
        return account;
    }
    public void setAccount(int account) {
        this.account = account;
    }

    public int getAgency() {
        return agency;
    }
    public void setAgency(int agency) {
        this.agency = agency;
    }

    public int getBank() {
        return bank;
    }
    public void setBank(int bank) {
        this.bank = bank;
    }

    @Override
    public String toString() {
        return "\nPAYMENT DATA\n" +
                "Bank = " + getBank() + " | Agency = " + getAgency() + " | Account = " + getAccount();
    }
}
