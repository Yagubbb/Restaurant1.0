package com.company;

public class Chief extends Person {
    private int payment;

    public Chief(String name, String surname, String address, int age,int payment) {
        super(name, surname, address, age);
        this.payment = payment;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }
}
