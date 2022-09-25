package book.example.mordernjavainaction.chap06;

import book.example.mordernjavainaction.chap04.Dish;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.*;

/**
 * 6.4 분할
 *
 * @author : 조병상
 * @since : 2022-09-25
 */
public class Partitioning_6_4 {

    List<Dish> menu = new Dish().makeDish();

    public enum CaloricLevel {DIET, NORMAL, FAT}

    /**
     * 6.4 분할 - partitioningBy()
     */
    @DisplayName("채식과 채식이 아닌 요리로 partitioning")
    @Test
    public void classifyDishIsVegetarian() {
        // when
        Map<Boolean, List<Dish>> partitioningDish =
                menu.stream()
                        .collect(partitioningBy(Dish::isVegetarian));

        // then
        System.out.println(partitioningDish);
        /*
         * 결과 값.
         * {
         * false=[pork, beef, chicken, prawns, salmon],
         * true=[french fries, rice, season fruit, pizza]
         * }
         */

        System.out.println(partitioningDish.get(true)); // [french fries, rice, season fruit, pizza]
        System.out.println(partitioningDish.get(false)); // [pork, beef, chicken, prawns, salmon]
    }

    /**
     * 6.4.1 분할의 장점 - partitioningBy(groupBy())
     */
    @DisplayName("채식과 채식이 아닌 요리로 나누고, 요리 타입으로 분류하기")
    @Test
    public void partitioningAndGroupByDish() {
        // when
        Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishGroupbyType =
                menu.stream()
                        .collect(
                                partitioningBy(Dish::isVegetarian,
                                        groupingBy(Dish::getType)));

        // then
        System.out.println(vegetarianDishGroupbyType);
        /*
         * {
         * false={MEAT=[pork, beef, chicken], FISH=[prawns, salmon]},
         * true={OTHER=[french fries, rice, season fruit, pizza]}
         * }
         */
    }

    /**
     * 6.4.1 분할의 장점 - partitioningBy(maxBy())
     */
    @DisplayName(" 채식 요리인지 분류 후 최대 칼로리 값인 요리 찾기")
    @Test
    public void maxCalOfVegetarianDish() {
        // when
        Map<Boolean, Dish> maxCalOfVegetarianDish =
                menu.stream()
                        .collect(
                                partitioningBy(Dish::isVegetarian,
                                        collectingAndThen(maxBy(Comparator.comparing(Dish::getCalories)), Optional::get)));

        // then
        System.out.println(maxCalOfVegetarianDish); // {false=pork, true=pizza}
    }

    /**
     * 퀴즈 6-2 partioningBy 사용. - partitioningBy(partitioningBy())
     */
    @DisplayName("채식 요리로 나누고 500 칼로리 기준으로 또 나누기")
    @Test
    public void quiz6_2_1() {
        // when
        Map<Boolean, Map<Boolean, List<Dish>>> result =
                menu.stream()
                        .collect(
                                partitioningBy(Dish::isVegetarian,
                                        partitioningBy(dish -> dish.getCalories() < 500)));

        // then
        System.out.println(result);
        /* 결과 값
         * {
         * false={false=[pork, beef], true=[chicken, prawns, salmon]},
         * true={false=[french fries, pizza], true=[rice, season fruit]}
         * }
         */
    }

    /**
     * 퀴즈 6-2 partioningBy 사용. - partitioningBy(partitioningBy())
     * partitioningBy는 프레디케이트를 반환하는 함수이다. Dish.getType은 프레디케이트를 반환하지 않으므로 문제 6.2.2번은 성립하지 않음.
     */
    @DisplayName("채식 요리로 나누고 요리 타입으로 또 나누기")
    @Test
    public void quiz6_2_2() {
        // when
//                menu.stream()
//                        .collect(
//                                partitioningBy(Dish::isVegetarian,
//                                        partitioningBy(Dish::getType)));
//
//        // then
//        System.out.println(result);

    }

    /**
     * 퀴즈 6-2 partioningBy 사용. - partitioningBy(partitioningBy())
     */
    @DisplayName("채식 요리로 나누고 counting하기")
    @Test
    public void quiz6_2_3() {
        // when
        Map<Boolean, Long> result =
                menu.stream()
                        .collect(
                                partitioningBy(Dish::isVegetarian,
                                        counting()));

        // then
        System.out.println(result);
        /* 결과 값
         * {false=5, true=4}
         */
    }


}

