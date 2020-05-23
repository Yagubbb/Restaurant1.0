package com.company;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Staff  {
    private ArrayList<Cashier> cashier;
    private ArrayList<Chief> chief;
    private ArrayList<Waiter> waiter;

    public Staff(ArrayList<Cashier> cashier, ArrayList<Chief> chief, ArrayList<Waiter> waiter) {
        this.cashier = cashier;
        this.chief = chief;
        this.waiter = waiter;
    }

    public ArrayList<Cashier> getCashier() {
        return cashier;
    }
    public void setCashier(ArrayList<Cashier> cashier) {
        this.cashier = cashier;
    }
    public void addCashier(Cashier cashier1){
        this.cashier.add(cashier1);
    }


    public ArrayList<Chief> getChief() {
        return chief;
    }
    public void setChief(ArrayList<Chief>chief) {
        this.chief = chief;
    }
    public void addChief(Chief chief1){
        this.chief.add(chief1);
    }



    public ArrayList<Waiter> getWaiter() {
        return waiter;
    }
    public void setWaiter(ArrayList<Waiter> waiter) {
        this.waiter = waiter;
    }
    public void addWaiter(Waiter waiter1){
        this.waiter.add(waiter1);}



    public void getStaffList(Restaurant restaurant){
        System.out.println("All staff:");
        System.out.println("Cashier:");
        for(Cashier a: restaurant.getStaff().getCashier()){
            System.out.println("\t" + a.getName() + " " + a.getSurname());


        }
        System.out.println("Chief:");
        for(Chief a: restaurant.getStaff().getChief()){
            System.out.println("\t" + a.getName() + " " + a.getSurname());

        }
        System.out.println("Waiter:");
        for(Waiter a: restaurant.getStaff().getWaiter()){
            System.out.println("\t" + a.getName() + " " + a.getSurname());


        }
    }

    public boolean findStaff(String name, String surname, Restaurant restaurant){
        boolean flag= false;
        for(Cashier a: restaurant.getStaff().getCashier()){
            if (name.equals(a.getName()) && surname.equals(a.getSurname())){
                flag = true;
                System.out.println("Full name: " + a.getName() + " " +  a.getSurname());
                System.out.println("Age: " + a.getAge());
                System.out.println("Address: " + a.getAddress());
                System.out.println("Payment: " + a.getPayment());
            }

        }
        for(Chief a: restaurant.getStaff().getChief()){
            if (name.equals(a.getName()) && surname.equals(a.getSurname())){
                flag = true;
                System.out.println("Full name: " + a.getName() + " " +  a.getSurname());
                System.out.println("Age: " + a.getAge());
                System.out.println("Address: " + a.getAddress());
                System.out.println("Payment: " + a.getPayment());
            }

        }
        for(Waiter a: restaurant.getStaff().getWaiter()){
            if (name.equals(a.getName()) && surname.equals(a.getSurname())){
                flag = true;
                System.out.println("Full name: " + a.getName() + " " +  a.getSurname());
                System.out.println("Age: " + a.getAge());
                System.out.println("Address: " + a.getAddress());
                System.out.println("Payment: " + a.getPayment());
            }

        }
    return flag;
    }

    public Person editStaff(String name, String surname, Restaurant restaurant){
        Scanner scanner = new Scanner(System.in);
        for(Cashier a: restaurant.getStaff().getCashier()){
            if (name.equals(a.getName()) && surname.equals(a.getSurname())){

                System.out.println("Change");
                ChangeAge:
                while(true){
                  try {
                      System.out.println("New Age(0 to skip, -1 to return):");
                      int newAge = scanner.nextInt();
                      if (newAge == -1) break ChangeAge;
                      else if (newAge == 0){
                      }
                      else if (newAge < 0 ){
                          System.out.println("Inappropriate age");
                          continue ;
                      }
                      else a.setAge(newAge);

                      ChangeAddress:
                      while (true){
                          System.out.println("New Address(0 to skip, -1 to return):");
                          String newAddress = scanner.next();
                          if (newAddress.equals("-1")) break ChangeAddress;
                          else if (newAddress.equals("0")){
                          }
                          else a.setAddress(newAddress);

                          ChangePayment:
                          while (true){
                              try {
                                  System.out.println("New Payment(0 to skip, -1 to return):");
                                  int newPayment = scanner.nextInt();
                                  if (newPayment == -1)
                                      break ChangePayment;
                                  else if (newPayment == 0) {
                                  }
                                  else if (newPayment < 0) {
                                      System.out.println("Inappropriate Payment");
                                      continue;

                                  } else a.setPayment(newPayment);

                                  break ChangeAge;
                              }catch (InputMismatchException e){
                                  System.out.println("Inappropriate input");
                                  continue ;
                              }
                          }
                      }
                  }catch (InputMismatchException e){
                      System.out.println("Inappropriate input");
                      continue ;
                  }

              }
                return a;
            }

        }
        for(Chief a: restaurant.getStaff().getChief()){
            if (name.equals(a.getName()) && surname.equals(a.getSurname())){
                System.out.println("Change");
                ChangeAge:
                while(true){
                    try {
                        System.out.println("New Age(0 to skip, -1 to return):");
                        int newAge = scanner.nextInt();
                        if (newAge == -1) break ChangeAge;
                        else if (newAge == 0){
                        }
                        else if (newAge < 0 ){
                            System.out.println("Inappropriate age");
                            continue ;
                        }
                        else a.setAge(newAge);

                        ChangeAddress:
                        while (true){
                            System.out.println("New Address(0 to skip, -1 to return):");
                            String newAddress = scanner.next();
                            if (newAddress.equals("-1")) break ChangeAddress;
                            else if (newAge == 0){
                            }
                            else a.setAddress(newAddress);

                            ChangePayment:
                            while (true){
                                try {
                                    System.out.println("New Payment(0 to skip, -1 to return):");
                                    int newPayment = scanner.nextInt();
                                    if (newPayment == -1) break ChangePayment;
                                    else if (newPayment == 0) {
                                    } else if (newPayment < 0) {
                                        System.out.println("Inappropriate Payment");
                                        continue;
                                    } else a.setPayment(newPayment);
                                    break ChangeAge;
                                }catch (InputMismatchException e){
                                    System.out.println("Inappropriate input");
                                    continue ;
                                }
                            }
                        }
                    }catch (InputMismatchException e){
                        System.out.println("Inappropriate input");
                        continue ;
                    }

                }
                return a;            }

        }
        for(Waiter a: restaurant.getStaff().getWaiter()){
            if (name.equals(a.getName()) && surname.equals(a.getSurname())){
                System.out.println("Change");
                ChangeAge:
                while(true){
                    try {
                        System.out.println("New Age(0 to skip, -1 to return):");
                        int newAge = scanner.nextInt();
                        if (newAge == -1) break ChangeAge;
                        else if (newAge == 0){
                        }
                        else if (newAge < 0 ){
                            System.out.println("Inappropriate age");
                            continue ;
                        }
                        else a.setAge(newAge);

                        ChangeAddress:
                        while (true){
                            System.out.println("New Address(0 to skip, -1 to return):");
                            String newAddress = scanner.next();
                            if (newAddress.equals("-1")) break ChangeAddress;
                            else if (newAge == 0){
                            }
                            else a.setAddress(newAddress);

                            ChangePayment:
                            while (true){
                                try {
                                    System.out.println("New Payment(0 to skip, -1 to return):");
                                    int newPayment = scanner.nextInt();
                                    if (newPayment == -1) break ChangePayment;
                                    else if (newPayment == 0) {
                                    } else if (newPayment < 0) {
                                        System.out.println("Inappropriate Payment");
                                        continue;
                                    } else a.setPayment(newPayment);
                                    break ChangeAge;
                                }catch (InputMismatchException e){
                                    System.out.println("Inappropriate input");
                                    continue ;
                                }
                            }
                        }
                    }catch (InputMismatchException e){
                        System.out.println("Inappropriate input");
                        continue ;
                    }

                }
                return a;            }

        }


        return null;
    }


}
