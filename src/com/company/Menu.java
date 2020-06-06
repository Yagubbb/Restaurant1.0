package com.company;

import java.util.HashMap;

public class Menu {
    private HashMap<String, Double> mealList, beverageList;

    public Menu(HashMap<String, Double> mealList, HashMap<String, Double> beverageList) {
        this.mealList = mealList;
        this.beverageList = beverageList;
    }

    public HashMap<String, Double> getMealList() {
        return mealList;
    }

    public HashMap<String, Double> getBeverageList() {
        return beverageList;
    }


    public void getMenu() {
        StringBuilder menu = new StringBuilder("\t  MENU \n\t  Meals");

        for (Object i : mealList.keySet()) {

            StringBuilder mealCost = new StringBuilder("\n" + i);
            for (int a = 0; a < (16 - i.toString().length()); a++) {
                mealCost.append(".");
            }
            menu.append(mealCost).append(mealList.get(i).toString());

        }
        menu.append("\n\tBeverages");

        for (Object i : beverageList.keySet()) {

            StringBuilder beverageCost = new StringBuilder("\n" + i);
            for (int a = 0; a < (16 - i.toString().length()); a++) {
                beverageCost.append(".");
            }
            menu.append(beverageCost).append(beverageList.get(i).toString());

        }

        System.out.println(menu);
    }

}
