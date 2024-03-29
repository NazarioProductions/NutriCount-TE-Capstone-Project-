package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class Meals {

    private int mealId;
    private int userId;

    private String mealType;
    private String mealName;
    private String mealDescription;

    private String logDay;


    private boolean quickMeal;
    private int totalCalories;
    public Meals() {};

    public Meals(int mealId, int userId, String mealType, String mealName, String mealDescription, String logDay, boolean quickMeal) {
        this.mealId = mealId;
        this.userId = userId;
        this.mealType = mealType;
        this.mealName = mealName;
        this.mealDescription = mealDescription;
        this.logDay = logDay;
        this.quickMeal = quickMeal;
//        this.totalCalories = totalCalories;

    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getMealDescription() {
        return mealDescription;
    }

    public void setMealDescription(String mealDescription) {
        this.mealDescription = mealDescription;
    }

    public String getLogDay() {
        return logDay;
    }

    public void setLogDay(String logDay) {
        this.logDay = logDay;
    }

    public boolean getQuickMeal() {
        return quickMeal;
    }

    public void setQuickMeal(boolean quickMeal) {
        this.quickMeal = quickMeal;
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(int totalCalories) {
        this.totalCalories = totalCalories;
    }

    public void addNewFood(Food food) {
    }

}
