<template>
  <div>
    <div class="Meal-details">
      <h1 id="title">Meal Details</h1>
      <p id="meal-name">Meal Name: {{ mealDetails.mealName }}</p>
      <p id="meal-type">Meal Type: {{ mealDetails.mealType }}</p>
      <p id="meal-quickMeal">
        Is This Meal a Quick Meal? {{ mealDetails.quickMeal }}
      </p>
      <p id="meal-date">Date: {{ mealDetails.logDay }}</p>
      <p id="meal-description">
        Description: {{ mealDetails.mealDescription }}
      </p>
    </div>
    <div class="food-details">
      <h2 class="food-title">Foods</h2>
      <div v-for="food in foods" :key="food.id">
        <p class="food-name">Food Name: {{ food.foodName }}</p>
        <p class="food-type">Food Type: {{ food.foodType }}</p>
        <p class="food-servingSize">Serving Size: {{ food.servingSize }}</p>
        <p class="food-numOfCalories">
          Number Of Calories: {{ food.calories }}
        </p>
        <button @click="deleteFood(food.id)">Delete Food</button>
      </div>
      <div class="meal-actions">
        <p class="error-message">{{ error }}</p>
        <button @click="$router.push({ name: 'editMeal' })">Edit Meal</button>
        <button @click="deleteMeal">Delete Meal</button>
        <button @click="$router.push({ name: 'showMeals' })">
          Show All Meals
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import FoodService from "../services/FoodService";
import MealService from "../services/MealService";
export default {
  data() {
    return {
      mealDetails: {},

      foods: [],

      error: "",
    };
  },

  created() {
    const mealId = this.$route.params.mealId;
    // this.getFoodsDetails(mealId);
    FoodService.getMealDetails(mealId)
      .then((response) => {
        this.mealDetails = response.data;
        this.getFoodsDetails();
      })
      .catch((error) => {
        console.error("Error fetching the meal details:", error);
      });
  },

  methods: {
    getFoodsDetails(){
        MealService.getFoodMeals(this.mealDetails.mealId)
        .then(response => {
            this.foods = response.data;
        })
        .catch(error => [
            console.error("Error fetching foods for meal:", error)
        ])
    },

    deleteMeal() {
      console.log("deleteMeal method called");
      MealService.deleteMeal(this.mealDetails.mealId)
        .then((response) => {
          console.log("response from server:", response);
          if (response.status === 204) {
            console.log("trying to navigate back");
            this.$router.push({ name: "showMeals" });
          } else {
            this.error = "Can Not Delete! Try Again";
          }
        })
        .catch((error) => {
          console.error("Error deleting meal: ", error);
        })
    },

     deleteFood(){
      console.log("deleteFood method called");
      MealService.deleteFoodById(this.food.foodId)
        .then((response) => {
          console.log("response from server:", response);
          if (response.status === 204) {
            console.log("trying to navigate back");
            this.$router.push({ name: "showMeals" });
          } else {
            this.error = "Can Not Delete! Try Again";
          }
        })
        .catch((error) => {
          console.error("Error deleting meal: ", error);
        });    
    }


  },

   
};
</script>

<style>
</style>