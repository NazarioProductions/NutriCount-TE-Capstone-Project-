package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FoodDto {
    private int foodId;
    @JsonProperty("name")
    private String foodName;
    private String foodType;
    @JsonProperty("serving_size_g")
    private int servingSize;
    private Double calories;
    private int numOfServings;
    private boolean quickFood;

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public int getServingSize() {
        return servingSize;
    }

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public int getNumOfServings() {
        return numOfServings;
    }

    public void setNumOfServings(int numOfServings) {
        this.numOfServings = numOfServings;
    }

    public boolean getQuickFood() {
        return quickFood;
    }

    public void setQuickFood(boolean quickFood) {
            quickFood = quickFood;
    }

    @Override
    public String toString() {
        return "FoodDto{" +
                "foodId=" + foodId +
                ", foodName='" + foodName + '\'' +
                ", foodType='" + foodType + '\'' +
                ", servingSize=" + servingSize +
                ", calories=" + calories +
                ", numOfServings=" + numOfServings +
                '}';
    }
}
