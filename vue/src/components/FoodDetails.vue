<!-- Change the component to meal search -->
<!-- this component will be where they seach for meals and then be taken to another component to see all the meals they've chosen -->
<template>
  <div>
    <!-- Juan's suggestion: I changed the title to 'My Foods'. Why? Because I said so! Sike, because this component is catered toward the food we're putting in the meal. 
    Don't like my idea? That's cool. Change it back: My Meals-->
    <h1 id="title">Add Foods</h1>
    <div class="main">
      <table id="tblFood">
        <thead id="tblhead">
          <!-- <tr> -->
            <!-- <th>Date</th>
            <th>Meal Type</th> -->
            <!-- <th>Number Of Servings</th> -->
            <!-- test if our database can search multiple strings  -->
            <!-- <th>Food Search</th> -->
            <!-- <th>Search Food</th> -->
          <!-- </tr> -->
        </thead>
        <tbody>
          <tr>
            <!-- <td>
              <input type="date" id="logDay" v-model="meal.logDay" />
              
            </td>
            <td>
              <select id="mealDropDown" v-model="meal.mealType">
                <option value="breakfast">Breakfast</option>
                <option value="Lunch">Lunch</option>
                <option value="Dinner">Dinner</option>
                <option value="Snack">Snack</option>
              </select>
            </td> -->
            <!-- <td> -->
            <!-- <input
                type="text"
                id="numOfServings"
                v-model="food.numOfServings"
                min="1"
              /> -->
            <!-- </td> -->
            <td>
              <label for="foodFilter"
                >Enter specific food names, including cooking style:
              </label>
              <input
                type="text"
                id="foodFilter"
                class="searchbar"
                v-model="search.foodName"
                placeholder="e.g Grilled Chicken Breast"
              />
            </td>
            <!-- <td>
                   <select id="quickMealFilter" v-model="search.isQuickMeal"/>
                        <option value="false">Show All</option>
                        <option value="true">Quick Meal</option>
               </td> -->
            <td>
              <!-- when this button is hit then it should be add to the meal's array/table -->
              <!-- need a is loading method might take this out -->
              <!-- <p class="tblfill-error">{{tableFilledError}}</p> -->
              <!-- :disabled="!isTableFilled" -->
              <button v-on:click="searchFood">Search Food</button>
            </td>
          </tr>
        </tbody>
      </table>

      <!-- add button to move to see all meals for the day -->
      <!-- create a show method -->
      <p class="error">{{ searchError }}</p>
      <table
        v-show="showTable"
        v-on:submit.prevent="addFood"
        id="tblFoodResults"
      >
        <thead>
          <tr>
            <th>Food Name</th>
            <th>Num. of Servings</th>
            <!--This was Food Type, orignally. -->
            <th>Serving Size</th>
            <th>Calories</th>
            <th>Quick Food?</th>
            <th>Add Food to Meal</th>
          </tr>
        </thead>

        <tbody>
          <tr v-for="foodItem in selectedFood" :key="foodItem.foodId">
            <!-- will need a v-for to show multiple results if going down that route -->
            <td>{{ foodItem.foodName }}</td>
            <!-- Looking to make a checkdown box for user to input the number of servings of each food to reflect the calorie intake. Was initially {{ foodItem.foodType }} -->
            <td>
              <select id="servingsDropDown" v-model="foodMealDto.numOfServings">
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
                <option value="6">6</option>
                <option value="7">7</option>
                <option value="8">8</option>
                <option value="9">9</option>
                <option value="10">10</option>
              </select>
            </td>
            <td>{{ foodItem.servingSize }}</td>
            <td>{{ foodItem.calories }}</td>
            <td>
              <p class="error">{{ isDuplicateError }}</p>
              <!-- going to need a method to turn isQuickMeal True -->
              <button v-on:click="addToQuickFoods(foodItem)">
                Add to Quick Foods
              </button>
            </td>
            <td>
              <!-- button should be disabled if there is no foods selected -->
              <!-- the meal method should save meals to an array-->
              <!-- v-bind:disabled="!selectedFood.length" -->
              <p class="success">{{ successMessage }}</p>
              <button v-on:click.prevent="saveFoodsToMeal(foodItem)">
                Add Food to Meal
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="butn-ShowMeals">
      <!-- this button will save the foods into a meal and transfer it to the next componenet -->
      <button @click="showMeal()">Show All Meals</button>
      <button @click="mealDetails(meal)"> Show Meal Details</button>
      <button v-on:click="newSearch">New Search</button>
    </div>
  </div>
</template>

<script>
import moment from "moment";
import foodService from "../services/FoodService";
import FoodService from "../services/FoodService";
export default {
  data() {
    return {
      selectedFood: [],
      //change to false if not hardcoding
      showTable: false,
      tableFilledError: "",
      searchError: "",
      isDuplicateError: "",
      successMessage: "",
      quickFood: [],
      showForm: false,
      meal: {},
      foodMealDto: {
        //Edited data object based on Myron and Jared's meeting. Will mark what was updated below.
        mealId: 0, //ADDED
        foodId: 0,
        logDay: "", //ADDED
        calories: 0, //Was on line 61 under 'servingSize'
        foodName: "",
        // foodType: "", This is set to null, so we don't need it
        servingSize: 0,
        numOfServings: 0,
        quickFood: false, //Changed from 'isQuickFood' to 'quickFood' due to deserialization
      },
      search: {
        foodName: "",
        //isQuickMeal: "false",
      },

      //  updatedFood: {
      //   foodId: "",
      //   foodName: "",
      //   foodType: "",
      //   servingSize: "",
      //   calories: "",
      //   numOfServings: "",
      //   isQuickFood: "false",
      // },
    };
  },
  created(){
    let mealId = this.$route.params.mealId;
    console.log("This is the route id: ", this.$route.params.mealId);
    
    FoodService.getMealDetails(mealId)
      .then((response) => {
        this.meal = response.data;
      })
      .catch((error) => {
        console.error("Error fetching the meal details:", error);
      });
  },

  methods: {
    //  got from stackoverflow
    format_date(value) {
      if (value) {
        return moment(value).format("YYYYMMDD");
      }
    },

    // triggers the food search
    searchFood() {
      let searchName = this.search.foodName;
      let tableForm = this.checkingTableForm();
      //this is making sure all input is filled out
      if (!tableForm) {
        return;
      }

      this.searchError = "";

      foodService
        .getFoodByName(searchName)
        .then((response) => {
          //this checks if there is no response
          if (response.data.length === 0) {
            this.superSearch();
            this.searchError =
              "SuperSearch activated. (Calorie information based on 100 grams)";
            return;
          }
          this.selectedFood = response.data;
          this.showTable = true;
        })
        .catch((error) => {
          if (error.status === 404) {
            this.searchError = "Hmmm...this must be a new food we haven't heard about yet.";
          } else {
            this.searchError =
              "An error happen while searching. Please try again.";
          }
          console.error(error);
        });
    },

    //The wonderful SuperSearch!
    superSearch() {
      let searchName = this.search.foodName;
      let tableForm = this.checkingTableForm();
      if (!tableForm) {
        return;
      }
      this.searchError = "";
      foodService
        .superSearch(searchName)
        .then((response) => {
          if (response.data.length === 0) {
            this.searchError =
              "Hmmm...this must be a new food we haven't heard about yet.";
            return;
          }
          this.selectedFood = response.data;
          this.showTable = true;
        })
        .catch((error) => {
          if (error.status === 404) {
            this.searchError = "No results found for this search.";
          } else {
            this.searchError =
              "An error happen while searching. Please try again.";
          }
          console.error(error);
        });
    },

    //Juan's suggestion: I bet you've noticed all the green in your code and may be wondering why.
    //Food details page should be all about that: food. I took off the date and meal type too highlight the food search and info.
    //Why? The log day and meal type is already handled on the meals page. We don't need this info for the foods to add them to a meal.
    //If you don't like the suggestion, I understand. The original code is below to copy and paste back into the method.
    //if (!this.meal.logDay || !this.meal.mealType || !this.food.numOfServings || !this.search.foodName)

    checkingTableForm() {
      if (!this.search.foodName) {
        this.tableFilledError = "Please search a food."; //this error is not working (It works for me. - Juan)
        return false;
      }
      this.tableFilledError = "";
      return true;
    },

    // Adds the selected food item to selectedFood array
    //method not working
    addToQuickFoods(foodItem) {
      //Checks the food id to see if its already saved
      let isDuplicate = false;
      for (let i = 0; i < this.quickFood.length; i++) {
        if (this.quickFood[i].foodId === foodItem.foodId) {
          isDuplicate = true;
          break;
          //need a break to exit out the lopp if it finds true
        }
      }

      if (!isDuplicate) {
        this.quickFood.push(foodItem);
      } else {
        this.isDuplicateError =
          "ERROR: Already added to Quick Foods. Please Make Another Selection.";
      }
    },

    saveFoodsToMeal(foodObject) {
      //populate foodmealdto
      this.foodMealDto.mealId = this.meal.mealId;
      this.foodMealDto.foodId = foodObject.foodId;
      this.foodMealDto.logDay = "";
      this.foodMealDto.calories = foodObject.calories;
      this.foodMealDto.foodName = foodObject.foodName;
      this.foodMealDto.servingSize = foodObject.servingSize;
      this.foodMealDto.quickFood = foodObject.quickFood;

      this.successMessage = "";

      FoodService.addFoodToMeal(this.foodMealDto).then((response) => {
        if (response.status === 201) {
          this.successMessage = "Added food to your meal!";
        }
        // this.$router.push({ name: "foodDetails" });
      });
    },

    //takes selected food items and puts it in a meal object
    addToFoodMeals(foodItem) {
      this.updatedFood.push(foodItem);
    },

    //Resets the search input
    newSearch() {
      this.search.foodName = "";
      this.meal.logDay = "";
      this.meal.mealType = "";
      this.food.numOfServings = "";
      this.searchError = "";
      this.tableFilledError = "";
      this.showTable = false;
    },

    showMeal() {
      this.$router.push({ name: "showMeals" });
    },

    mealDetails(meal){
      this.$router.push({name:"mealDetails", params: {mealId: meal.mealId}});
    }
  },

  computed: {
    isTableFilled() {
      //Inital method was this ---> return this.search.foodName && this.meal.logDay && this.food.numOfServings && this.meal.mealType
      return this.search.foodName;
    },
    totalCaloriesForFood() {
      let servings = this.food.numOfServings;
      return servings * this.selectedFood.calories;
    },
  },

};
</script>

<style>
#title {
  text-align: center;
}

p.error {
  text-align: center;
  font-weight: bolder;
  font-style: italic;
}

#tblhead {
  text-align: center;
}

.searchbar {
  width: 500px;
}
</style>