package com.company;

import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Helper {
    Restaurant restaurant;

    public Helper(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    String balancePath = "src/Days/balance.json";
    String cashiersPath = "src/Staff/Cashiers.json";
    String waitersPath = "src/Staff/Waiters.json";
    String chiefsPath =  "src/Staff/Chiefs.json";

    Scanner scanner = new Scanner(System.in);

    public String order(Restaurant fastFoodRestaurant, double total, HashMap<String, Integer> order, ArrayList<Double> lstReport, int dayNumber) {
        label:
        while (true) {

            total = fastFoodRestaurant.takeOrder(order, total);
            if (total == -1.0) break;

            while (true) {
                String choose = choose("Clear", "Continue", "Complete", "Cancel");
                switch (choose) {
                    case "Clear":
                        total = 0.0;
                        order.clear();
                        continue label;
                    case "Continue":
                        continue label;
                    case "Complete":
                        break label;
                    case "Back":
                    case "Cancel":
                        return "Cancel";
                    default:
                        System.out.println("Wrong choice");
                        break;

                }
            }
        }

        if (total == -1) return "Break";
        if (order.size() == 0) return "Break";

        fastFoodRestaurant.bill(order, total, dayNumber);
        fastFoodRestaurant.addDailyBalance(total);
        lstReport.add(total);
        return "Break";
    }

    public void exit(Restaurant fastFoodRestaurant, Day day, ArrayList<Double> lstReport, int dayNumber) {
        day.setReport(lstReport);
        day.setIncome(fastFoodRestaurant.getDailyBalance());

        Gson json = new Gson();

        String report = json.toJson(day);

        String n = Integer.toString(dayNumber);
        try {
            FileWriter fileWriter = new FileWriter("C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Days\\day" + n + ".json");
            fileWriter.write(report);
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String fileName = balancePath;
        File file = new File(fileName);
        JSONParser parser = new JSONParser();

        try {
            FileReader reader = new FileReader(file.getAbsolutePath());
            Object obj = parser.parse(reader);
            JSONObject jsonObj = (JSONObject) obj;
            double balance = (double) jsonObj.get("General balance");
            balance += fastFoodRestaurant.getDailyBalance();

            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();

            jsonObj.put("General balance", balance);

            String dBalance = jsonObj.toJSONString();

            try {
                FileWriter fileWriter = new FileWriter(balancePath);
                fileWriter.write(dBalance);
                fileWriter.flush();
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.exit(0);
    }

    public void financial(int dayNumber) throws IOException, ParseException {
        String fileName = balancePath;
        File file = new File(fileName);
        JSONParser parser = new JSONParser();

        try {
            FileReader reader = new FileReader(file.getAbsolutePath());
            Object obj = parser.parse(reader);
            JSONObject jsonObj = (JSONObject) obj;
            double balance = (double) jsonObj.get("General balance");
            System.out.println("\nGeneral balance: " + balance);

        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("\nAll reports");
        for (int a = 1; a < dayNumber; a++) {
            File dayFile = new File("C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Days\\day" + a + ".json");
            FileReader reader = new FileReader(dayFile.getAbsolutePath());
            Object obj = parser.parse(reader);
            JSONObject jsonObj = (JSONObject) obj;
            double income = (double) jsonObj.get("income");
            System.out.println("Day " + a + " : " + income);
        }
        dayLabel:
        while (true) {
            System.out.print("Choose day number to see information(-1 or \"Back\" to return): ");
            try {
                String dayChoice = scanner.next();
                if (dayChoice.equals("-1") || dayChoice.equals("Back"))
                    break dayLabel;

                File dayFile = new File("C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Days\\day" + dayChoice + ".json");
                FileReader reader = new FileReader(dayFile.getAbsolutePath());
                Object obj = parser.parse(reader);
                JSONObject jsonObject = (JSONObject) obj;
                ArrayList<Double> jsonArray = (ArrayList<Double>) jsonObject.get("report");
                for (int orderNum = 0; orderNum < jsonArray.size(); orderNum++) {
                    System.out.println("Customer " + (orderNum + 1) + " : " + jsonArray.get(orderNum));
                }


            } catch (FileNotFoundException e) {
                System.out.println("Wrong choice");

            }


        }
    }

    public String staff(Restaurant fastFoodRestaurant) throws IOException, ParseException {
        String choose;
        fastFoodRestaurant.getStaff().getStaffList(fastFoodRestaurant);

        while (true) {
            choose = choose("Information", "Add", "Remove");

            Staff:
            switch (choose) {
                case "Information":
                    Info:
                    while (true) {
                        System.out.print("Name(-1 or \"Back\" to return):");
                        String Name = scanner.next();
                        if (Name.equals("-1") || Name.equals("Back"))
                            return "Back";//break FinancialStaff;

                        System.out.print("Surname(-1 or \"Back\" to return):");
                        String Surname = scanner.next();
                        if (Surname.equals("-1") || Surname.equals("Back"))
                            return "Back";

                        else if (fastFoodRestaurant.getStaff().findStaff(Name, Surname, fastFoodRestaurant)) {
                            Edit:
                            while (true) {

                                choose = choose("Edit");
                                System.out.println(choose);
                                switch (choose) {
                                    case "Edit":

                                        Person person = fastFoodRestaurant.getStaff().editStaff(Name, Surname, fastFoodRestaurant);
                                        saveEdit(person);
                                        break Staff;

                                    case "Back":
                                        break Staff;

                                    default:
                                        System.out.println("Wrong choice");
                                }
                            }
                        } else System.out.println("There is no person like that");
                    }
                case "Add":
                    while (true) {
                        int newStaffAge, newStaffPayment;
                        System.out.println("\nName(-1 to return): ");
                        String newStaffName = scanner.next();
                        if (newStaffName.equals("-1")) break;

                        System.out.println("Surname(-1 to return): ");
                        String newStaffSurname = scanner.next();
                        if (newStaffSurname.equals("-1")) break;

                        System.out.println("Address(-1 to return): ");
                        String newStaffAddress = scanner.next();
                        if (newStaffAddress.equals("-1")) break;

                        try {
                            System.out.println("Age(-1 to return): ");
                            newStaffAge = scanner.nextInt();
                            if (newStaffAge == -1) break;
                            else if (newStaffAge < 0) {
                                System.out.println("Inappropriate age");
                                continue;
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Inappropriate input");
                            scanner.next();
                            continue;
                        }
                        try {
                            System.out.println("Payment(-1 to return): ");
                            newStaffPayment = scanner.nextInt();
                            if (newStaffPayment == -1) break;
                            else if (newStaffPayment < 0) {
                                System.out.println("Inappropriate payment");
                                continue;
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Inappropriate input");
                            scanner.next();
                            continue;
                        }

                        addStaff(newStaffName, newStaffSurname, newStaffAddress, newStaffAge, newStaffPayment, fastFoodRestaurant);
                    }


                    break;
                case "Remove":

                    while (true) {
                        System.out.println("Name(-1 to return): ");
                        String removedStaffName = scanner.next();
                        if (removedStaffName.equals("-1")) break;

                        System.out.println("Surname(-1 to return): ");
                        String removedStaffSurname = scanner.next();
                        if (removedStaffSurname.equals("-1")) break;
                        boolean flag = removeStaff(removedStaffName, removedStaffSurname, fastFoodRestaurant);
                        if (flag) break Staff;
                        else {
                            continue;
                        }
                    }
                    break;
                case "Back":
                    return "Back";
                default:
                    System.out.println("Wrong choice");

            }
        }
    }

    public String choose(String... chooses) {

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

    public void saveEdit(Person person) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();

        if (person.getClass() == Waiter.class) {
            File waiterFile = new File(waitersPath);
            FileReader fileReader = new FileReader(waiterFile.getAbsolutePath());
            JSONArray waiterArray = (JSONArray) jsonParser.parse(fileReader);
            for (int countStaff = 0; countStaff < waiterArray.size(); countStaff++) {
                String waiterKey = "waiter" + (countStaff + 1);

                JSONObject waiterObj = (JSONObject) waiterArray.get(countStaff);
                JSONObject waiterProp = (JSONObject) waiterObj.get(waiterKey);
                if (person.getName().equals(waiterProp.get("name"))) {

                    waiterProp.put("age", person.getAge());
                    waiterProp.put("address", person.getAddress());
                    waiterProp.put("payment", ((Waiter) person).getPayment());

                    waiterObj.put(waiterKey, waiterProp);
                    waiterArray.set(countStaff, waiterObj);
                    String edited = waiterArray.toJSONString();

                    PrintWriter writer = new PrintWriter(waiterFile);
                    writer.print("");
                    writer.close();

                    try {
                        FileWriter fileWriter = new FileWriter(waitersPath);
                        fileWriter.write(edited);
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }
        } else if (person.getClass() == Cashier.class) {
            File cashierFile = new File(cashiersPath);
            FileReader fileReader = new FileReader(cashierFile.getAbsolutePath());
            JSONArray cashierArray = (JSONArray) jsonParser.parse(fileReader);
            for (int countStaff = 0; countStaff < cashierArray.size(); countStaff++) {
                String cashierKey = "cashier" + (countStaff + 1);

                JSONObject cashierObj = (JSONObject) cashierArray.get(countStaff);
                JSONObject cashierProp = (JSONObject) cashierObj.get(cashierKey);
                if (person.getName().equals(cashierProp.get("name"))) {

                    cashierProp.put("age", person.getAge());
                    cashierProp.put("address", person.getAddress());
                    cashierProp.put("payment", ((Cashier) person).getPayment());

                    cashierObj.put(cashierKey, cashierProp);
                    cashierArray.set(countStaff, cashierObj);
                    String edited = cashierArray.toJSONString();

                    PrintWriter writer = new PrintWriter(cashierFile);
                    writer.print("");
                    writer.close();

                    try {
                        FileWriter fileWriter = new FileWriter(cashiersPath);
                        fileWriter.write(edited);
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }

        } else if (person.getClass() == Chief.class) {
            File chiefFile = new File(chiefsPath);
            FileReader fileReader = new FileReader(chiefFile.getAbsolutePath());
            JSONArray chiefArray = (JSONArray) jsonParser.parse(fileReader);
            for (int countStaff = 0; countStaff < chiefArray.size(); countStaff++) {
                String chiefKey = "chief" + (countStaff + 1);

                JSONObject chiefObj = (JSONObject) chiefArray.get(countStaff);
                JSONObject chiefProp = (JSONObject) chiefObj.get(chiefKey);
                if (person.getName().equals(chiefProp.get("name"))) {
                    chiefProp.put("age", person.getAge());
                    chiefProp.put("address", person.getAddress());
                    chiefProp.put("payment", ((Chief) person).getPayment());

                    chiefObj.put(chiefKey, chiefProp);
                    chiefArray.set(countStaff, chiefObj);
                    String edited = chiefArray.toJSONString();

                    PrintWriter writer = new PrintWriter(chiefFile);
                    writer.print("");
                    writer.close();

                    try {
                        FileWriter fileWriter = new FileWriter(chiefsPath);
                        fileWriter.write(edited);
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }

        }
        System.out.println("Successful edit");
    }

    public void addStaff(String name, String surname, String address, int age, int payment, Restaurant fastFoodRestaurant) throws IOException, ParseException {

        JSONParser jsonParser = new JSONParser();
        FileReader fileReader;
        String key;
        PrintWriter writer;
        int count;

        Add:
        while (true) {
            String choose = choose("Chief", "Waiter", "Cashier");
            switch (choose) {
                case "Chief":
                    File chiefFile = new File(chiefsPath);
                    fileReader = new FileReader(chiefFile.getAbsolutePath());
                    JSONArray chiefArray = (JSONArray) jsonParser.parse(fileReader);
                    JSONObject chief = new JSONObject();
                    chief.put("name", name);
                    chief.put("surname", surname);
                    chief.put("address", address);
                    chief.put("age", age);
                    chief.put("payment", payment);


                    count = chiefArray.size() + 1;
                    key = "chief" + count;
                    JSONObject object = new JSONObject();
                    object.put(key, chief);
                    chiefArray.add(object);

                    writer = new PrintWriter(chiefFile);
                    writer.print("");
                    writer.close();
                    String nChief = chiefArray.toJSONString();
                    try {
                        FileWriter fileWriter = new FileWriter(chiefsPath);
                        fileWriter.write(nChief);
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Chief newChief = new Chief(name, surname, address, age, payment);
                    fastFoodRestaurant.getStaff().addChief(newChief);


                    System.out.println("Staff added");
                    break Add;
                case "Waiter":
                    File waiterFile = new File(waitersPath);
                    fileReader = new FileReader(waiterFile.getAbsolutePath());
                    JSONArray waiterArray = (JSONArray) jsonParser.parse(fileReader);
                    JSONObject waiter = new JSONObject();
                    waiter.put("name", name);
                    waiter.put("surname", surname);
                    waiter.put("address", address);
                    waiter.put("age", age);
                    waiter.put("payment", payment);

                    count = waiterArray.size() + 1;
                    key = "waiter" + count;
                    JSONObject object2 = new JSONObject();
                    object2.put(key, waiter);
                    waiterArray.add(object2);

                    writer = new PrintWriter(waiterFile);
                    writer.print("");
                    writer.close();
                    String nWaiter = waiterArray.toJSONString();
                    try {
                        FileWriter fileWriter = new FileWriter(waitersPath);
                        fileWriter.write(nWaiter);
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Waiter newWaiter = new Waiter(name, surname, address, age, payment);
                    fastFoodRestaurant.getStaff().addWaiter(newWaiter);

                    System.out.println("Staff added");
                    break Add;
                case "Cashier":
                    File cashierFile = new File(cashiersPath);
                    fileReader = new FileReader(cashierFile.getAbsolutePath());
                    JSONArray cashierArray = (JSONArray) jsonParser.parse(fileReader);
                    JSONObject cashier = new JSONObject();
                    cashier.put("name", name);
                    cashier.put("surname", surname);
                    cashier.put("address", address);
                    cashier.put("age", age);
                    cashier.put("payment", payment);

                    count = cashierArray.size() + 1;
                    key = "cashier" + count;
                    JSONObject object3 = new JSONObject();
                    object3.put(key, cashier);
                    cashierArray.add(object3);

                    writer = new PrintWriter(cashierFile);
                    writer.print("");
                    writer.close();
                    String nCashier = cashierArray.toJSONString();
                    try {
                        FileWriter fileWriter = new FileWriter(cashiersPath);
                        fileWriter.write(nCashier);
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Cashier newCashier = new Cashier(name, surname, address, age, payment);
                    fastFoodRestaurant.getStaff().addCashier(newCashier);
                    System.out.println("Staff added");
                    break Add;
                case "Back":
                    break;

                default:
                    System.out.println("Wrong choice");
                    break;
            }
        }
    }

    public boolean removeStaff(String name, String surname, Restaurant fastFoodRestaurant) throws IOException, ParseException {
        boolean flag = true;
        JSONParser jsonParser = new JSONParser();
        PrintWriter writer;
        File chiefFile = new File(chiefsPath);
        File waiterFile = new File(waitersPath);
        File cashierFile = new File(cashiersPath);
        FileReader fileReader;
        Remove:
        while (true) {
            String c = choose("Chief", "Waiter", "Cashier");
            switch (c) {
                case "Chief":
                    fileReader = new FileReader(chiefFile.getAbsolutePath());
                    JSONArray chiefArray = (JSONArray) jsonParser.parse(fileReader);

                    for (int countStaff = 0; countStaff < chiefArray.size(); countStaff++) {
                        String chiefKey = "chief" + Integer.toString(countStaff + 1);
                        JSONObject chiefObj = (JSONObject) chiefArray.get(countStaff);
                        JSONObject chiefProp = (JSONObject) chiefObj.get(chiefKey);
                        if (name.equals(chiefProp.get("name")) && surname.equals(chiefProp.get("surname"))) {
                            chiefArray.remove(countStaff);
                            writer = new PrintWriter(chiefFile);
                            writer.print("");
                            writer.close();
                            String nChief = chiefArray.toJSONString();
                            try {
                                FileWriter fileWriter = new FileWriter(chiefsPath);
                                fileWriter.write(nChief);
                                fileWriter.flush();
                                fileWriter.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            fastFoodRestaurant.getStaff().getChief().removeIf(a -> name.equals(a.getName()) && surname.equals(a.getSurname()));


                            System.out.println("Staff removed");
                            break Remove;
                        }
                    }
                    System.out.println("Staff not found");
                    break;
                case "Waiter":
                    fileReader = new FileReader(waiterFile.getAbsolutePath());
                    JSONArray waiterArray = (JSONArray) jsonParser.parse(fileReader);
                    for (int countStaff = 0; countStaff < waiterArray.size(); countStaff++) {
                        String waiterKey = "waiter" + Integer.toString(countStaff + 1);
                        JSONObject waiterObj = (JSONObject) waiterArray.get(countStaff);
                        JSONObject waiterProp = (JSONObject) waiterObj.get(waiterKey);
                        if (name.equals(waiterProp.get("name")) && surname.equals(waiterProp.get("surname"))) {
                            waiterArray.remove(countStaff);
                            writer = new PrintWriter(waiterFile);
                            writer.print("");
                            writer.close();
                            String nWaiter = waiterArray.toJSONString();
                            try {
                                FileWriter fileWriter = new FileWriter(waitersPath);
                                fileWriter.write(nWaiter);
                                fileWriter.flush();
                                fileWriter.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            fastFoodRestaurant.getStaff().getWaiter().removeIf(a -> name.equals(a.getName()) && surname.equals(a.getSurname()));

                            System.out.println("Staff removed");
                            break Remove;
                        }
                    }
                    System.out.println("Staff not found");
                    break;
                case "Cashier":
                    fileReader = new FileReader(cashierFile.getAbsolutePath());
                    JSONArray cashierArray = (JSONArray) jsonParser.parse(fileReader);

                    for (int countStaff = 0; countStaff < cashierArray.size(); countStaff++) {

                        String cashierKey = "cashier" + Integer.toString(countStaff + 1);
                        JSONObject cashierObj = (JSONObject) cashierArray.get(countStaff);
                        JSONObject cashierProp = (JSONObject) cashierObj.get(cashierKey);

                        if (name.equals(cashierProp.get("name")) && surname.equals(cashierProp.get("surname"))) {
                            cashierArray.remove(countStaff);
                            writer = new PrintWriter(cashierFile);
                            writer.print("");
                            writer.close();
                            String nCashier = cashierArray.toJSONString();
                            try {
                                FileWriter fileWriter = new FileWriter(cashiersPath);
                                fileWriter.write(nCashier);
                                fileWriter.flush();
                                fileWriter.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            fastFoodRestaurant.getStaff().getCashier().removeIf(a -> name.equals(a.getName()) && surname.equals(a.getSurname()));


                            System.out.println("Staff removed");
                            break Remove;
                        }
                    }
                    System.out.println("Staff not found");
                    break;
                case "Back":
                    flag = false;
                    break Remove;
                default:
                    System.out.println("Wrong choice");
            }
        }
        return flag;
    }


}
