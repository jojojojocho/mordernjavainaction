package book.example.mordernjavainaction.chap06;

import book.example.mordernjavainaction.chap04.Dish;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 6.3 그룹화
 *
 * @author : 조병상
 * @since : 2022-09-15
 */
public class Grouping_6_3 {

    List<Dish> menu = new Dish().makeDishes();

    /**
     * 6.3 그룹화 - Collectors.groupBy()
     */
    @DisplayName("같은 타입으로 그룹화하기. - 메서드 참조")
    @Test
    public void ClassifyByDishTypeRefMethod() {

        // when
        Map<Dish.Type, List<Dish>> classifiedMapByDishType = menu.stream().collect(Collectors.groupingBy(Dish::getType));

        // then
        System.out.println(classifiedMapByDishType.entrySet()); //
        /* 결과 값
         * [
         * OTHER=[french_fries, rice, season fruit, pizza],
         * MEAT=[pork, beef, chicken], FISH=[prawns, salmon]
         * ]
         */
    }

    /**
     * 6.3 그룹화 - Collectors.groupBy()
     */
    public enum CaloricLevel {DIET, NORMAL, FAT}

    @DisplayName("같은 타입으로 그룹화 하기. - 람다식")
    @Test
    public void ClassifyByDishTypeLambda() {
        // when
        Map<CaloricLevel, List<Dish>> caloricLevelMap =
                menu.stream().collect(Collectors.groupingBy(dish -> {
                    if (dish.getCalories() <= 400) {
                        return CaloricLevel.DIET;
                    } else if (dish.getCalories() <= 700) {
                        return CaloricLevel.NORMAL;
                    } else {
                        return CaloricLevel.FAT;
                    }
                }));

        // then
        System.out.println(caloricLevelMap);
        /* 결과값
         * {
         * FAT=[pork],
         * DIET=[chicken, rice, season fruit, prawns],
         * NORMAL=[beef, french_fries, pizza, salmon]
         * }
         */
    }

    /**
     * 6.3.1 그룹화된 요소 조작. - Collectors.groupingBy(), Collectors.filtering()
     */
    @DisplayName("칼로리가 500이상인 요리들만 요리타입 기준으로 그룹화 하기 - (모든 타입 나오게)")
    @Test
    public void groupByDishTypeOver500CaloriesDish() {
        // when
        Map<Dish.Type, List<Dish>> over500CalDishesGroupByMenuType =
                menu.stream().collect(Collectors.groupingBy(
                        Dish::getType, Collectors.filtering(dish -> dish.getCalories() > 500, Collectors.toList())));

        // then
        System.out.println(over500CalDishesGroupByMenuType); // {OTHER=[french_fries, pizza], MEAT=[pork, beef], FISH=[]}
    }

    /**
     * 6.3.1 그룹화된 요소 조작 - Collectors.groupBy(), Collectors.mapping()
     */
    @DisplayName("요리 타입 기준으로 그룹화 해서 요리 이름으로 매핑")
    @Test
    public void groupByDishTypeAndMappingDishName() {
        // when
        Map<Dish.Type, List<String>> dishNameGroupByDishType =
                menu.stream().collect(
                        Collectors.groupingBy(Dish::getType, Collectors.mapping(Dish::getName, Collectors.toList())));

        // then
        System.out.println(dishNameGroupByDishType);

        /* 결과 값
         * {
         *  OTHER=[french_fries, rice, season fruit, pizza],
         *  MEAT=[pork, beef, chicken],
         *  FISH=[prawns, salmon]
         *  }
         */
    }

    /**
     * 6.3.1 그룹화된 요소 조작 - Collectors.groupBy(), Collectors.flatMapping()
     */
    @DisplayName("flatMapping을 이용한 요리 태그 추출")
    @Test
    public void groupByDishTagUsingFlatMapping(){
        // given
        Map<String, List<String>> dishTags = new HashMap<>();
        dishTags.put("pork",Arrays.asList("greasy","salty"));
        dishTags.put("beef",Arrays.asList("salty","roasted"));
        dishTags.put("chicken",Arrays.asList("fried","crisp"));
        dishTags.put("french fries",Arrays.asList("greasy","fried"));
        dishTags.put("rice",Arrays.asList("light","natural"));
        dishTags.put("season fruit",Arrays.asList("fresh","natural"));
        dishTags.put("pizza",Arrays.asList("tasty","salty"));
        dishTags.put("prawns",Arrays.asList("tasty","roasted"));
        dishTags.put("salmon",Arrays.asList("delicious","fresh"));

        // when
        Map<Dish.Type, Set<String>> collect =
                menu.stream().collect(
                Collectors.groupingBy(Dish::getType,
                        Collectors.flatMapping(dish -> dishTags.get(dish.getName()).stream(), Collectors.toSet())));

        // then
        System.out.println(collect);

        /* 결과 값
         * {
         * FISH=[roasted, tasty, fresh, delicious],
         * MEAT=[salty, greasy, roasted, fried, crisp],
         * OTHER=[salty, greasy, natural, light, tasty, fresh, fried]
         * }
         */
    }

    /**
     * 6.3.2 다수준 그룹화 - 두 가지 이상의 기준을 동시에 적용
     */

}
