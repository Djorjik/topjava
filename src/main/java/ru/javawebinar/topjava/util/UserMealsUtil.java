package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed> filteredWithExceeded = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
            for( UserMealWithExceed userMealExceed: filteredWithExceeded){
                System.out.println(userMealExceed);
            }

    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesSumPerDate = new HashMap<>();
        for (UserMeal meal: mealList){
            LocalDate mealDate = meal.getDateTime().toLocalDate();
            caloriesSumPerDate.put(mealDate, caloriesSumPerDate.getOrDefault(mealDate,0) + meal.getCalories());
            }
        List< UserMealWithExceed> mealExceeded = new ArrayList<>();
        for (UserMeal meal: mealList){
            LocalDateTime mealDateTime = meal.getDateTime();
            if (TimeUtil.isBetween(mealDateTime.toLocalTime(), startTime, endTime)){
                mealExceeded.add(new UserMealWithExceed(mealDateTime,meal.getDescription(), meal.getCalories(),
                        caloriesSumPerDate.get(mealDateTime.toLocalDate()) > caloriesPerDay));
            }
        }
        return mealExceeded;
    }
}
