package com.company;

import com.sun.xml.internal.ws.util.StringUtils;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Restaurant {
    private String passwordStaff, passwordManager;
    private double dailyBalance, generalBalance;
    Menu Menu;
    String order;
    private Staff staff;

    public Restaurant(String passwordStaff, String passwordManager,
                      double dailyBalance, Menu Menu, Staff staff) {

        this.passwordStaff = passwordStaff;
        this.passwordManager = passwordManager;
        this.dailyBalance = dailyBalance;
        this.Menu = Menu;
        this.staff = staff;
    }

    public String getPasswordStaff() {
        return passwordStaff;
    }
    public void setPasswordStaff(String passwordStaff) {
        this.passwordStaff = passwordStaff;
    }

    public String getPasswordManager() {
        return passwordManager;
    }
    public void setPasswordManager(String passwordManager) {
        this.passwordManager = passwordManager;
    }

    public double getDailyBalance() {
        return dailyBalance;
    }
    public void setDailyBalance(double dailyBalance) {
        this.dailyBalance = dailyBalance;
    }
    public void addDailyBalance(double income){
        this.dailyBalance += income;
    }

    public double getGeneralBalance() {
        return generalBalance;
    }
    public void setGeneralBalance(double generalBalance) {
        this.generalBalance = generalBalance;
    }

    public String getOrder() {
        return order;
    }
    public void setOrder(String order) {
        this.order = order;
    }

    public Staff getStaff() {
        return staff;
    }
    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public double takeOrder(HashMap<String, Integer> order, double total) {
        Scanner scanner = new Scanner(System.in);
        double copyTotal = total;

            System.out.print("\nChoose food (-1 for back): ");
            String meal = scanner.next();
            if (meal.equals("-1")){
                total = -1.0;
            }
            else if (Menu.getMealList().containsKey(meal)) {
                for (String i : Menu.getMealList().keySet()) {
                    if (meal.equals(i)) {
                        System.out.print("Quantity: ");
                        try {
                            int quantity = scanner.nextInt();
                            double cost = quantity * Menu.getMealList().get(i);
                            System.out.println("Total: " + copyTotal + " + " + cost + " = " + (copyTotal + cost));
                            total = copyTotal + cost;
                            order.put(i, quantity);
                        }catch (InputMismatchException e){

                        }
                    }
                }
            } else if (Menu.getBeverageList().containsKey(meal)) {
                for (String i : Menu.getBeverageList().keySet()) {
                    if (meal.equals(i)) {
                        System.out.print("Quantity: ");
                        int quantity = scanner.nextInt();
                        double cost = quantity * Menu.getBeverageList().get(i);
                        System.out.println("Total: " + total + " + " + cost + " = " + (total + cost));
                        total += cost;
                        order.put(i,quantity);
                    }
                }
            }

            else {
                System.out.println("Wrong choice");
            }
            //total = copyTotal;
        return total;
    }

    public void bill(HashMap<String , Integer> order, double total, int dayNumber){


        System.out.println("*********************");
        System.out.println("       RECEIPT       ");
        System.out.println("*********************");
        System.out.println("Day " + dayNumber);
        System.out.println("---------------------");

        for (String a: order.keySet()){
            double cost = 0;
            try {
                cost = order.get(a) * Menu.getMealList().get(a);
            }catch (NullPointerException e){
            }

            try {
                cost = order.get(a) * Menu.getBeverageList().get(a);

            }catch (NullPointerException e){
            }

            System.out.print(order.get(a) + " x " + a);
            int len = Integer.toString(order.get(a)).length();
            double len2 = Double.toString(cost).length();

            for (int b = 0; b < (21 - (4 + len + a.length() +  len2)); b++){
                System.out.print(" ");
            }

            System.out.println("$" + cost);
            System.out.println("");
        }
        System.out.println("---------------------");

        System.out.print("TOTAL AMOUNT");
        for (int b = 0; b < (16 - 12); b++){
            System.out.print(" ");
        }

        System.out.println("$" + total);
        System.out.println("");
        System.out.println("******THANK YOU******");
    }


}




