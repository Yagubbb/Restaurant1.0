package com.company;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static HashMap<String, Double> beverageList = new HashMap();
    static HashMap<String, Double> mealList = new HashMap();

    //* Creating Staff

    static ArrayList<Cashier> listCashier = new ArrayList<>();
    static ArrayList<Chief> listChief = new ArrayList<>();
    static ArrayList<Waiter> listWaiter = new ArrayList<>();
    static Staff staff = new Staff(listCashier, listChief, listWaiter);

    public static void main(String[] args) throws IOException, ParseException {

        JSONParser jsonParser = new JSONParser();

        File waiterFile = new File("src/Staff/Waiters.json");
        FileReader fileReader = new FileReader(waiterFile.getAbsolutePath());
        JSONArray waiterArray = (JSONArray) jsonParser.parse(fileReader);

        for (int countStaff = 0; countStaff < waiterArray.size(); countStaff++) {
            String waiterKey = "waiter" + Integer.toString(countStaff + 1);

            JSONObject waiterObj = (JSONObject) waiterArray.get(countStaff);
            JSONObject waiterProp = (JSONObject) waiterObj.get(waiterKey);
            String sWage = waiterProp.get("age").toString();
            int wAge = Integer.parseInt(sWage);
            String sPayment = waiterProp.get("payment").toString();
            int wPayment = Integer.parseInt(sPayment);

            Waiter waiter = new Waiter((String) waiterProp.get("name"), (String) waiterProp.get("surname"),
                    (String) waiterProp.get("address"), wAge, wPayment);

            staff.addWaiter(waiter);
        }

        File chiefFile = new File("src/Staff/Chiefs.json");
        fileReader = new FileReader(chiefFile.getAbsolutePath());
        JSONArray chiefArray = (JSONArray) jsonParser.parse(fileReader);

        for (int countStaff = 0; countStaff < chiefArray.size(); countStaff++) {
            String chiefKey = "chief" + Integer.toString(countStaff + 1);

            JSONObject chiefObj = (JSONObject) chiefArray.get(countStaff);
            JSONObject chiefProp = (JSONObject) chiefObj.get(chiefKey);
            String sWage = chiefProp.get("age").toString();
            int cAge = Integer.parseInt(sWage);
            String sPayment = chiefProp.get("payment").toString();
            int cPayment = Integer.parseInt(sPayment);

            Chief chief = new Chief((String) chiefProp.get("name"), (String) chiefProp.get("surname"), (String) chiefProp.get("address"),
                    cAge, cPayment);
            staff.addChief(chief);
        }

        File cashierFile = new File("src/Staff/Cashiers.json");
        fileReader = new FileReader(cashierFile.getAbsolutePath());
        JSONArray cashierArray = (JSONArray) jsonParser.parse(fileReader);

        for (int countStaff = 0; countStaff < cashierArray.size(); countStaff++) {
            String cashierKey = "cashier" + Integer.toString(countStaff + 1);

            JSONObject cashierObj = (JSONObject) cashierArray.get(countStaff);
            JSONObject cashierProp = (JSONObject) cashierObj.get(cashierKey);
            String sWage = cashierProp.get("age").toString();
            int cAge = Integer.parseInt(sWage);
            String sPayment = cashierProp.get("payment").toString();
            int cPayment = Integer.parseInt(sPayment);

            Cashier cashier = new Cashier((String) cashierProp.get("name"), (String) cashierProp.get("surname"), (String) cashierProp.get("address"),
                    cAge, cPayment);
            staff.addCashier(cashier);
        }

        // Creating menu
        mealList.put("Sandwich", 2.5);
        mealList.put("Burger", 2.5);
        mealList.put("Hotdog", 1.5);
        mealList.put("Pizza", 5.0);
        mealList.put("Nuggets", 1.0);
        mealList.put("Donut", 2.0);
        mealList.put("Fries", 1.0);
        beverageList.put("Cola", 3.0);
        beverageList.put("Coffee", 1.5);
        beverageList.put("Water", 0.5);
        Menu menu = new Menu(mealList, beverageList);

        // Creating Restaurant
        Restaurant fastFoodRestaurant = new Restaurant("1234", "yaqub123", 0.0, menu, staff);

        //New day
        Day day = new Day();
        ArrayList<Double> lstReport = new ArrayList<>();

        int dayNumber = 0;
        try (Stream<Path> files = Files.list(Paths.get("src/Days"))) {
            dayNumber = (int) files.count();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Helper helper = new Helper(fastFoodRestaurant);

        String Return;
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        while (true) {

            System.out.println("\t\tWELCOME");

            String choose = choose("Staff", "Manager");
            switch (choose) {
                case "Staff":
                    while (true) {
                        System.out.print("\tEnter code for staff:\nPassword(-1 or \"Back\" to return): ");
                        String password = scanner.next();

                        if (password.equals(fastFoodRestaurant.getPasswordStaff())) {
                            somelabel:
                            while (true) {


                                choose = choose("Order", "Exit");
                                switch (choose) {
                                    case "Order":
                                        fastFoodRestaurant.getMenu().getMenu();
                                        HashMap<String, Integer> order = new HashMap<>();
                                        double total = 0.0;

                                        Return = helper.order(fastFoodRestaurant, total, order, lstReport, dayNumber);
                                        if (Return.equals("Cancel")) {
                                            continue somelabel;
                                        } else break;


                                    case "Exit":
                                        System.out.println("End of day");

                                        helper.exit(fastFoodRestaurant, day, lstReport, dayNumber);

                                    case "Back":
                                        break somelabel;
                                    default:
                                        System.out.println("Wrong choice");

                                }
                            }

                        } else if (password.equals("Back") || password.equals("-1")) {
                            break;
                        } else {
                            System.out.println("Wrong password");
                            continue;
                        }

                    }
                    break;

                case "Manager":
                    while (true) {
                        System.out.print("\tEnter code for manager:\nPassword(-1 or \"Back\" to return):");
                        String password = scanner.next();
                        if (password.equals(fastFoodRestaurant.getPasswordManager())) {
                            Manager:
                            while (true) {
                                choose = choose("Financial", "Staff");
                                FinancialStaff:
                                switch (choose) {
                                    case "Financial":
                                        helper.financial(dayNumber);
                                        break;
                                    case "Staff":
                                        Return = helper.staff(fastFoodRestaurant);
                                        if (Return.equals("Back")) {
                                            break FinancialStaff;
                                        } else {
                                            break;
                                        }
                                    case "Back":
                                        break Manager;

                                    default:
                                        System.out.println("Wrong choice");
                                        break;
                                }

                            }
                        } else if (password.equals("Back") || password.equals("-1")) {
                            break;
                        } else {
                            System.out.println("Wrong password");
                            continue;
                        }
                    }
                    break;

                case "Back":
                    System.out.println("You can not go back");
                    break;

                default:
                    System.out.println("Wrong choice\n");
                    System.out.println(choose);
                    break;
            }
        }

    }


    static String choose(String... chooses) {

        int count = 1;
        System.out.println("\t");
        for (String a : chooses) {
            System.out.print(Integer.toString(count) + "." + a + "\t\t");
            count += 1;
        }

        System.out.print("\nChoose(" + "-1" + " or \"Back\" to return): ");
        String choose = scanner.next();

        String result = " ";

        if (choose.equals("Back") || choose.equals("-1")) {
            result = "Back";
        } else {
            count = 1;
            for (String b : chooses) {

                if (choose.equals(b) || choose.equals(Integer.toString(count))) {
                    result = b;
                    break;

                } else {
                    count += 1;
                    continue;
                }
            }

        }


        return result;
    }


}


