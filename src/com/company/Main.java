package com.company;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import com.google.gson.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static HashMap<String, Double> beverageList = new HashMap();
    static HashMap<String, Double> mealList = new HashMap();

    //* Creating Staff
    /*static Chief chief = new Chief("John", "Morgan", "12 High Street", 27, 1000);
    static Cashier cashier = new Cashier("Jessica", "Clark", "23 Chapel Hill", 24, 700);
    static Waiter waiter = new Waiter("Jose", "White", "35 Rickey Road", 23, 400);
    static Waiter waiter2 = new Waiter("Nick", "Stone", "27 White Hill", 24, 400);*/
    static ArrayList<Cashier> lstCashier = new ArrayList<>();
    static ArrayList<Chief> lstChief = new ArrayList<>();
    static ArrayList<Waiter> lstWaiter = new ArrayList<>();
    static Staff staff = new Staff(lstCashier,lstChief,lstWaiter);

    public static void main(String[] args) throws IOException, ParseException {

        JSONParser jsonParser = new JSONParser();

        File waiterFile = new File("C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Staff\\Waiters.json");
        FileReader fileReader = new FileReader(waiterFile.getAbsolutePath());
        JSONArray waiterArray = (JSONArray) jsonParser.parse(fileReader);

        for (int countStaff = 0; countStaff < waiterArray.size(); countStaff ++){
            String waiterKey = "waiter" + Integer.toString(countStaff + 1);

            JSONObject waiterObj =(JSONObject) waiterArray.get(countStaff);
            JSONObject waiterProp = (JSONObject) waiterObj.get(waiterKey );
            String sWage = waiterProp.get("age").toString();
            int wAge =  Integer.parseInt(sWage);
            String sPayment = waiterProp.get("payment").toString();
            int wPayment = Integer.parseInt(sPayment);

              Waiter waiter = new Waiter((String) waiterProp.get("name"), (String) waiterProp.get("surname"),
                      (String) waiterProp.get("address"), wAge, wPayment);

              staff.addWaiter(waiter);
        }

        File chiefFile = new File("C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Staff\\Chiefs.json");
        fileReader = new FileReader(chiefFile.getAbsolutePath());
        JSONArray chiefArray = (JSONArray) jsonParser.parse(fileReader);

        for (int countStaff = 0; countStaff < chiefArray.size(); countStaff ++){
            String chiefKey = "chief" + Integer.toString(countStaff + 1);

            JSONObject chiefObj =(JSONObject) chiefArray.get(countStaff);
            JSONObject chiefProp = (JSONObject) chiefObj.get(chiefKey );
            String sWage = chiefProp.get("age").toString();
            int cAge =  Integer.parseInt(sWage);
            String sPayment = chiefProp.get("payment").toString();
            int cPayment = Integer.parseInt(sPayment);

            Chief chief = new Chief((String) chiefProp.get("name"),(String) chiefProp.get("surname") ,(String) chiefProp.get("address"),
                    cAge,cPayment);
            staff.addChief(chief);
        }

        File cashierFile = new File("C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Staff\\Cashiers.json");
        fileReader = new FileReader(cashierFile.getAbsolutePath());
        JSONArray cashierArray = (JSONArray) jsonParser.parse(fileReader);

        for (int countStaff = 0; countStaff < cashierArray.size(); countStaff ++){
            String cashierKey = "cashier" + Integer.toString(countStaff + 1);

            JSONObject cashierObj =(JSONObject) cashierArray.get(countStaff);
            JSONObject cashierProp = (JSONObject) cashierObj.get(cashierKey );
            String sWage = cashierProp.get("age").toString();
            int cAge =  Integer.parseInt(sWage);
            String sPayment = cashierProp.get("payment").toString();
            int cPayment = Integer.parseInt(sPayment);

            Cashier cashier = new Cashier((String) cashierProp.get("name"),(String) cashierProp.get("surname") ,(String) cashierProp.get("address"),
                    cAge,cPayment);
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
                                        fastFoodRestaurant.Menu.getMenu();
                                        HashMap<String, Integer> order = new HashMap<>();
                                        double total = 0.0;

                                        label:
                                        while (true) {

                                            total = fastFoodRestaurant.takeOrder(order, total);
                                            if (total == -1.0) break;

                                            while (true) {
                                                String c = choose("Clear", "Continue", "Complete", "Cancel");
                                                switch (c) {
                                                    case "Clear":
                                                        order.clear();
                                                        break label;
                                                    case "Continue":
                                                        continue label ;
                                                    case "Complete":
                                                        break label;
                                                    case "Back":
                                                    case "Cancel":
                                                        break somelabel;
                                                    default:
                                                        System.out.println("Wrong choice");
                                                        break;

                                                }
                                            }
                                        }

                                        if (total == -1) break;
                                        if (order.size() == 0) break;
                                        int dayNumber = 0;
                                        try (Stream<Path> files = Files.list(Paths.get("C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Days"))) {
                                            dayNumber = (int) files.count();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        fastFoodRestaurant.bill(order, total,dayNumber);
                                        fastFoodRestaurant.addDailyBalance(total);
                                        lstReport.add(total);
                                        break;

                                    case "Exit":
                                        System.out.println("End of day");

                                        day.setReport(lstReport);
                                        day.setIncome(fastFoodRestaurant.getDailyBalance());

                                        JSONObject jsonObject = new JSONObject();

                                        Gson json = new Gson();

                                        String report = json.toJson(day);
                                        dayNumber = 0;
                                        try (Stream<Path> files = Files.list(Paths.get("C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Days"))) {
                                            dayNumber = (int) files.count();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        String n = Integer.toString(dayNumber);
                                        try {
                                            FileWriter fileWriter = new FileWriter("C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Days\\day" + n + ".json");
                                            fileWriter.write(report);
                                            fileWriter.flush();
                                            fileWriter.close();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        String fileName = "C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Days\\balance.json";
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
                                                FileWriter fileWriter = new FileWriter("C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Days\\balance.json");
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

                                    case "Back":
                                        break somelabel;
                                    default:
                                        System.out.println("Wrong choice");

                                }
                            }

                        }
                        else if (password.equals("Back") || password.equals("-1")){
                            break;
                        }
                        else {
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
                            while (true){
                                choose = choose("Financial","Staff");
                                FinancialStaff:
                                switch (choose){
                                    case "Financial":
                                        String fileName = "C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Days\\balance.json";
                                        File file = new File(fileName);
                                        JSONParser parser = new JSONParser();

                                        try {
                                            FileReader reader = new FileReader(file.getAbsolutePath());
                                            Object obj = parser.parse(reader);
                                            JSONObject jsonObj = (JSONObject) obj;
                                            double balance = (double) jsonObj.get("General balance");
                                            System.out.println("\nGeneral balance: "  + balance);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        int dayNumber = 0;
                                        try (Stream<Path> files = Files.list(Paths.get("C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Days"))) {
                                            dayNumber = (int) files.count();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        System.out.println("\nAll reports");
                                        for (int a = 1; a < dayNumber; a++){
                                            File dayFile = new File("C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Days\\day" + a + ".json");
                                            FileReader reader = new FileReader(dayFile.getAbsolutePath());
                                            Object obj = parser.parse(reader);
                                            JSONObject jsonObj = (JSONObject) obj;
                                            double income = (double) jsonObj.get("income");
                                            System.out.println("Day " + a +  " : " + income);
                                        }
                                        dayLabel:
                                        while (true){
                                            System.out.print("Choose day number to see information(-1 or \"Back\" to return): ");
                                            try {
                                                String chs = scanner.next() ;
                                                if (chs.equals("-1") || chs.equals("Back"))
                                                    break dayLabel;

                                                File dayFile = new File("C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Days\\day" + chs + ".json");
                                                FileReader reader = new FileReader(dayFile.getAbsolutePath());
                                                Object obj = parser.parse(reader);
                                                JSONObject jsonObject = (JSONObject) obj;
                                                ArrayList<Double> jsonArray = (ArrayList<Double>) jsonObject.get("report");
                                                for (int orderNum = 0; orderNum < jsonArray.size(); orderNum ++){
                                                    System.out.println("Customer " + (orderNum + 1) + " : " + jsonArray.get(orderNum));
                                                }






                                            }catch (FileNotFoundException e){
                                                //e.printStackTrace();
                                                System.out.println("Wrong choice");

                                            }


                                        }
                                        break;
                                    case "Staff":
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
                                                            break FinancialStaff;

                                                        System.out.print("Surname(-1 or \"Back\" to return):");
                                                        String Surname = scanner.next();
                                                        if (Surname.equals("-1") || Surname.equals("Back"))
                                                            break FinancialStaff;

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
                                                        }
                                                        else System.out.println("There is no person like that");
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
                                                                continue ;
                                                            }
                                                        } catch (InputMismatchException e) {
                                                            System.out.println("Inappropriate input");
                                                            scanner.next();
                                                            continue ;
                                                        }
                                                        try {
                                                            System.out.println("Payment(-1 to return): ");
                                                            newStaffPayment = scanner.nextInt();
                                                            if (newStaffPayment == -1) break;
                                                            else if (newStaffPayment < 0) {
                                                                System.out.println("Inappropriate payment");
                                                                continue ;
                                                            }
                                                        } catch (InputMismatchException e) {
                                                            System.out.println("Inappropriate input");
                                                            scanner.next();
                                                            continue ;
                                                        }

                                                        //addStaff(newStaffName, newStaffSurname, newStaffAddress, newStaffAge, newStaffPayment);
                                                    }


                                                    break ;
                                                case "Remove":
                                                    System.out.println("Name(-1 to return): ");
                                                    while (true) {
                                                         String removedStaffName = scanner.next();
                                                        if (removedStaffName.equals("-1")) break;

                                                        System.out.println("Surname(-1 to return): ");
                                                        String removedStaffSurname = scanner.next();
                                                        if (removedStaffSurname.equals("-1")) break;
                                                        boolean flag = removeStaff(removedStaffName, removedStaffSurname);
                                                        if (flag) break Staff;
                                                        else {
                                                            continue;
                                                        }
                                                    }
                                                    break ;
                                                case "Back":
                                                    break FinancialStaff;
                                                default:
                                                    System.out.println("Wrong choice");

                                            }
                                        }
                                    case "Back":
                                        break Manager;

                                    default:
                                        System.out.println("Wrong choice");
                                        break;
                                }

                            }
                        }
                        else if (password.equals("Back") || password.equals("-1")){
                            break;
                        }
                        else {
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

        if (choose.equals("Back") || choose.equals("-1")){
            result = "Back";
        }
        else {
            count = 1;
            for (String b : chooses) {

                if (choose.equals(b) || choose.equals(Integer.toString(count))) {
                    result =  b;
                    break;

                } else {
                    count += 1;
                    continue;
                }
            }

        }


        return result;
    }

    static void saveEdit(Person person ) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();

        if (person.getClass() == Waiter.class){
            File waiterFile = new File("C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Staff\\Waiters.json");
            FileReader fileReader = new FileReader(waiterFile.getAbsolutePath());
            JSONArray waiterArray = (JSONArray) jsonParser.parse(fileReader);
            for (int countStaff = 0; countStaff < waiterArray.size(); countStaff ++) {
                String waiterKey = "waiter" + Integer.toString(countStaff + 1);

                JSONObject waiterObj = (JSONObject) waiterArray.get(countStaff);
                JSONObject waiterProp = (JSONObject) waiterObj.get(waiterKey);
                if (person.getName().equals(waiterProp.get("name"))){
                    //int age = person.getAge();
                    waiterProp.put("age",person.getAge());
                    waiterProp.put("address",person.getAddress());
                    waiterProp.put("payment",((Waiter) person).getPayment());

                    waiterObj.put(waiterKey,waiterProp);
                    waiterArray.set(countStaff,waiterObj);
                    String edited = waiterArray.toJSONString();

                    PrintWriter writer = new PrintWriter(waiterFile);
                    writer.print("");
                    writer.close();

                    try {
                        FileWriter fileWriter = new FileWriter("C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Staff\\Waiters.json");
                        fileWriter.write(edited);
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{

                }
            }
        }

        else if(person.getClass() == Cashier.class){
            File cashierFile = new File("C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Staff\\Cashiers.json");
            FileReader fileReader = new FileReader(cashierFile.getAbsolutePath());
            JSONArray cashierArray = (JSONArray) jsonParser.parse(fileReader);
            for (int countStaff = 0; countStaff < cashierArray.size(); countStaff ++) {
                String cashierKey = "cashier" + Integer.toString(countStaff + 1);

                JSONObject cashierObj = (JSONObject) cashierArray.get(countStaff);
                JSONObject cashierProp = (JSONObject) cashierObj.get(cashierKey);
                if (person.getName().equals(cashierProp.get("name"))){
                    //int age = person.getAge();
                    cashierProp.put("age",person.getAge());
                    cashierProp.put("address",person.getAddress());
                    cashierProp.put("payment",((Cashier) person).getPayment());

                    cashierObj.put(cashierKey,cashierProp);
                    cashierArray.set(countStaff,cashierObj);
                    String edited = cashierArray.toJSONString();

                    PrintWriter writer = new PrintWriter(cashierFile);
                    writer.print("");
                    writer.close();

                    try {
                        FileWriter fileWriter = new FileWriter("C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Staff\\Cashiers.json");
                        fileWriter.write(edited);
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{

                }
            }

        }

        else if(person.getClass() == Chief.class){
            File chiefFile = new File("C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Staff\\Chiefs.json");
            FileReader fileReader = new FileReader(chiefFile.getAbsolutePath());
            JSONArray chiefArray = (JSONArray) jsonParser.parse(fileReader);
            for (int countStaff = 0; countStaff < chiefArray.size(); countStaff ++) {
                String chiefKey = "chief" + Integer.toString(countStaff + 1);

                JSONObject chiefObj = (JSONObject) chiefArray.get(countStaff);
                JSONObject chiefProp = (JSONObject) chiefObj.get(chiefKey);
                if (person.getName().equals(chiefProp.get("name"))){
                    //int age = person.getAge();
                    chiefProp.put("age",person.getAge());
                    chiefProp.put("address",person.getAddress());
                    chiefProp.put("payment",((Chief) person).getPayment());

                    chiefObj.put(chiefKey,chiefProp);
                    chiefArray.set(countStaff,chiefObj);
                    String edited = chiefArray.toJSONString();

                    PrintWriter writer = new PrintWriter(chiefFile);
                    writer.print("");
                    writer.close();

                    try {
                        FileWriter fileWriter = new FileWriter("C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Staff\\Chiefs.json");
                        fileWriter.write(edited);
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{

                }
            }

        }
        System.out.println("Successful edit");
    }

    static void addStaff(String name,String surname,String address,int age, int payment ) throws IOException, ParseException {

        JSONParser jsonParser = new JSONParser();
        FileReader fileReader;
        String key;
        PrintWriter writer;
        int count;

        Add:
        while (true) {
            String c = choose("Chief", "Waiter", "Cashier");
            switch (c) {
                case "Chief":
                    File chiefFile = new File("C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Staff\\Chiefs.json");
                    fileReader = new FileReader(chiefFile.getAbsolutePath());
                    JSONArray chiefArray = (JSONArray) jsonParser.parse(fileReader);
                    JSONObject chief = new JSONObject();
                    chief.put("name", name);
                    chief.put("surname", surname);
                    chief.put("address",address);
                    chief.put("age", age);
                    chief.put("payment", payment);


                    count = chiefArray.size() + 1;
                    key = "chief" + count;
                    JSONObject object = new JSONObject();
                    object.put(key,chief);
                    chiefArray.add(object);

                    writer = new PrintWriter(chiefFile);
                    writer.print("");
                    writer.close();
                    String nChief = chiefArray.toJSONString();
                    try {
                        FileWriter fileWriter = new FileWriter("C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Staff\\Chiefs.json");
                        fileWriter.write(nChief);
                        fileWriter.flush();
                        fileWriter.close();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }


                    System.out.println("Staff added");
                    break Add;
                case "Waiter":
                    File waiterFile = new File("C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Staff\\Waiters.json");
                    fileReader = new FileReader(waiterFile.getAbsolutePath());
                    JSONArray waiterArray = (JSONArray) jsonParser.parse(fileReader);
                    JSONObject waiter = new JSONObject();
                    waiter.put("name", name);
                    waiter.put("surname", surname);
                    waiter.put("address",address);
                    waiter.put("age", age);
                    waiter.put("payment", payment);

                    count = waiterArray.size() + 1;
                    key = "waiter" + count;
                    JSONObject object2 = new JSONObject();
                    object2.put(key,waiter);
                    waiterArray.add(object2);

                    writer = new PrintWriter(waiterFile);
                    writer.print("");
                    writer.close();
                    String nWaiter = waiterArray.toJSONString();
                    try {
                        FileWriter fileWriter = new FileWriter("C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Staff\\Waiters.json");
                        fileWriter.write(nWaiter);
                        fileWriter.flush();
                        fileWriter.close();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("Staff added");
                    break Add;
                case "Cashier":
                    File cashierFile = new File("C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Staff\\Cashiers.json");
                    fileReader = new FileReader(cashierFile.getAbsolutePath());
                    JSONArray cashierArray = (JSONArray) jsonParser.parse(fileReader);
                    JSONObject cashier = new JSONObject();
                    cashier.put("name", name);
                    cashier.put("surname", surname);
                    cashier.put("address",address);
                    cashier.put("age", age);
                    cashier.put("payment", payment);

                    count = cashierArray.size() + 1;
                    key = "cashier" + count;
                    JSONObject object3 = new JSONObject();
                    object3.put(key,cashier);
                    cashierArray.add(object3);

                    writer = new PrintWriter(cashierFile);
                    writer.print("");
                    writer.close();
                    String nCashier = cashierArray.toJSONString();
                    try {
                        FileWriter fileWriter = new FileWriter("C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Staff\\Cashiers.json");
                        fileWriter.write(nCashier);
                        fileWriter.flush();
                        fileWriter.close();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("Staff added");
                    break Add;
                case "Back":break ;

                default:
                    System.out.println("Wrong choice");
                    break ;
            }
        }
        }



    static boolean removeStaff(String name, String surname) throws IOException, ParseException {
        boolean flag = true;
        JSONParser jsonParser = new JSONParser();
        PrintWriter writer;
        File chiefFile = new File("C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Staff\\Chiefs.json");
        File waiterFile = new File("C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Staff\\Waiters.json");
        File cashierFile = new File("C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Staff\\Cashiers.json");
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
                                FileWriter fileWriter = new FileWriter("C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Staff\\Chiefs.json");
                                fileWriter.write(nChief);
                                fileWriter.flush();
                                fileWriter.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            System.out.println("Staff removed");
                            break Remove;
                        }
                    }
                    System.out.println("Staff not found");
                    break ;
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
                                FileWriter fileWriter = new FileWriter("C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Staff\\Waiters.json");
                                fileWriter.write(nWaiter);
                                fileWriter.flush();
                                fileWriter.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            System.out.println("Staff removed");
                            break Remove;
                        }
                    }
                    System.out.println("Staff not found");
                    break ;
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
                                FileWriter fileWriter = new FileWriter("C:\\Users\\User\\IdeaProjects\\Restaurant\\src\\Staff\\Cashiers.json");
                                fileWriter.write(nCashier);
                                fileWriter.flush();
                                fileWriter.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            System.out.println("Staff removed");
                            break Remove;
                        }
                    }
                    System.out.println("Staff not found");
                    break ;
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


