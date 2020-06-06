package com.company;


import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Restaurant {
    private String passwordStaff, passwordManager;
    private double dailyBalance, generalBalance;
    private Menu menu;
    private String order;
    private Staff staff;

    public Restaurant(String passwordStaff, String passwordManager,
                      double dailyBalance, Menu Menu, Staff staff) {

        this.passwordStaff = passwordStaff;
        this.passwordManager = passwordManager;
        this.dailyBalance = dailyBalance;
        this.menu = Menu;
        this.staff = staff;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public String getPasswordStaff() {
        return passwordStaff;
    }

    public String getPasswordManager() {
        return passwordManager;
    }

    public double getDailyBalance() {
        return dailyBalance;
    }

    public void addDailyBalance(double income) {
        this.dailyBalance += income;
    }


    public Staff getStaff() {
        return staff;
    }

    public double takeOrder(HashMap<String, Integer> order, double total) {
        Scanner scanner = new Scanner(System.in);
        double copyTotal = total;

        System.out.print("\nChoose food (-1 for back): ");
        String meal = scanner.next();
        if (meal.equals("-1")) {
            total = -1.0;
        } else if (menu.getMealList().containsKey(meal)) {
            for (String i : menu.getMealList().keySet()) {
                if (meal.equals(i)) {
                    System.out.print("Quantity: ");
                    try {
                        int quantity = scanner.nextInt();
                        double cost = quantity * menu.getMealList().get(i);
                        System.out.println("Total: " + copyTotal + " + " + cost + " = " + (copyTotal + cost));
                        total = copyTotal + cost;
                        order.put(i, quantity);
                    } catch (InputMismatchException e) {

                    }
                }
            }
        } else if (menu.getBeverageList().containsKey(meal)) {
            for (String i : menu.getBeverageList().keySet()) {
                if (meal.equals(i)) {
                    System.out.print("Quantity: ");
                    int quantity = scanner.nextInt();
                    double cost = quantity * menu.getBeverageList().get(i);
                    System.out.println("Total: " + total + " + " + cost + " = " + (total + cost));
                    total += cost;
                    order.put(i, quantity);
                }
            }
        } else {
            System.out.println("Wrong choice");
        }
        //total = copyTotal;
        return total;
    }

    public void bill(HashMap<String, Integer> order, double total, int dayNumber) {


        System.out.println("*********************");
        System.out.println("       RECEIPT       ");
        System.out.println("*********************");
        System.out.println("Day " + dayNumber);
        System.out.println("---------------------");

        for (String a : order.keySet()) {
            double cost = 0;
            try {
                cost = order.get(a) * menu.getMealList().get(a);
            } catch (NullPointerException e) {
            }

            try {
                cost = order.get(a) * menu.getBeverageList().get(a);

            } catch (NullPointerException e) {
            }

            System.out.print(order.get(a) + " x " + a);
            int len = Integer.toString(order.get(a)).length();
            double len2 = Double.toString(cost).length();

            for (int b = 0; b < (21 - (4 + len + a.length() + len2)); b++) {
                System.out.print(" ");
            }

            System.out.println("$" + cost);
            System.out.println("");
        }
        System.out.println("---------------------");

        System.out.print("TOTAL AMOUNT");
        for (int b = 0; b < (16 - 12); b++) {
            System.out.print(" ");
        }

        System.out.println("$" + total);
        System.out.println("");
        System.out.println("******THANK YOU******");
    }


}




