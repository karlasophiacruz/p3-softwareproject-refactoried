package model.employee;

import java.time.LocalTime;
import java.time.LocalDate;

public class TimeCard {
    private LocalTime in, out;
    private LocalDate date;
    private int hourWorked;

    public TimeCard(LocalDate date, LocalTime in, LocalTime out){
        this.setDate(date);
        this.setIn(in);
        this.setOut(out);

        int diference = out.minusHours(in.getHour()).minusMinutes(in.getMinute()).getHour();
        setHourWorked(diference);
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getHourWorked() {
        return hourWorked;
    }
    public void setHourWorked(int hourWorked) {
        this.hourWorked = hourWorked;
    }

    public LocalTime getIn() {
        return in;
    }
    public void setIn(LocalTime in) {
        this.in = in;
    }

    public LocalTime getOut() {
        return out;
    }
    public void setOut(LocalTime out) {
        this.out = out;
    }

    @Override
    public String toString() {
        return "\nTIME CARD\n" +
                "Date = " + this.getDate() + "\n" +
                "In = " + this.getIn() + "  |  Out = " + this.getOut() +
                "\nWorkedHours = " + this.getHourWorked() + "\n";
    }
}
