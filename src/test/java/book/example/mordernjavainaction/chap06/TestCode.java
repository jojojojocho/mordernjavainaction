package book.example.mordernjavainaction.chap06;

import book.example.mordernjavainaction.chap04.Dish;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author : 조병상
 * @since : 2022-09-15
 */
public class TestCode {

    List<Dish> menu = new Dish().makeDishes();


    /**
     * 6.2.1 스트림 값에서 최댓값 구하기.
     */
    @DisplayName("스트림 값에서 최댓값구하기.")
    @Test
    public void findMaxCaloriesOfDish(){
        //given
        Comparator<Dish> caloriesComparator = Comparator.comparingInt(Dish::getCalories);

        //when
        Optional<Dish> maxCaloriesDish = menu.stream().collect(Collectors.maxBy(caloriesComparator));

        //then
        System.out.println(maxCaloriesDish);
    }

    /**
     * 6.2.1 스트림 값에서 최솟값 구하기.
     */
    @DisplayName("스트림 값에서 최솟값구하기.")
    @Test
    public void findMinCaloriesOfDish(){
        //given
        Comparator<Dish> caloriesComparator = Comparator.comparingInt(Dish::getCalories);

        //when
        Optional<Dish> minCaloriesDish = menu.stream().collect(Collectors.minBy(caloriesComparator));

        //then
        System.out.println(minCaloriesDish);
    }

}
