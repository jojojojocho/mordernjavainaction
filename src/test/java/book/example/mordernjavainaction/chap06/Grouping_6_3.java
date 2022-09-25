package book.example.mordernjavainaction.chap06;

import book.example.mordernjavainaction.chap04.Dish;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static java.util.stream.Collectors.*;

/**
 * 6.3 그룹화
 *
 * @author : 조병상
 * @since : 2022-09-15
 */
public class Grouping_6_3 {

    List<Dish> menu = new Dish().makeDish();
    public enum CaloricLevel {DIET, NORMAL, FAT}

    /**
     * 6.3 그룹화 - Collectors.groupBy()
     */
    @DisplayName("같은 타입으로 그룹화하기. - 메서드 참조")
    @Test
    public void classifyByDishTypeRefMethod() {

        // when
        Map<Dish.Type, List<Dish>> classifiedMapByDishType =
                menu.stream().
                        collect(
                                groupingBy(Dish::getType));

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
    @DisplayName("같은 타입으로 그룹화 하기. - 람다식")
    @Test
    public void classifyByDishTypeLambda() {
        // when
        Map<CaloricLevel, List<Dish>> caloricLevelMap =
                menu.stream()
                        .collect(
                                groupingBy(dish -> {
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
        Map<Dish.Type, List<Dish>> over500CalDishGroupByMenuType =
                menu.stream().
                        collect(
                                groupingBy(Dish::getType,
                                        filtering(dish -> dish.getCalories() > 500, toList())));

        // then
        System.out.println(over500CalDishGroupByMenuType); // {OTHER=[french_fries, pizza], MEAT=[pork, beef], FISH=[]}
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
                        groupingBy(Dish::getType,
                                mapping(Dish::getName, toList())));

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
    public void groupByDishTagUsingFlatMapping() {
        // given
        Map<String, List<String>> dishTags = new HashMap<>();
        dishTags.put("pork", Arrays.asList("greasy", "salty"));
        dishTags.put("beef", Arrays.asList("salty", "roasted"));
        dishTags.put("chicken", Arrays.asList("fried", "crisp"));
        dishTags.put("french fries", Arrays.asList("greasy", "fried"));
        dishTags.put("rice", Arrays.asList("light", "natural"));
        dishTags.put("season fruit", Arrays.asList("fresh", "natural"));
        dishTags.put("pizza", Arrays.asList("tasty", "salty"));
        dishTags.put("prawns", Arrays.asList("tasty", "roasted"));
        dishTags.put("salmon", Arrays.asList("delicious", "fresh"));

        // when
        Map<Dish.Type, Set<String>> collect =
                menu.stream().collect(
                        groupingBy(Dish::getType,
                                flatMapping(dish -> dishTags.get(dish.getName()).stream(), toSet())));

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
     * 6.3.2 다수준 그룹화 - groupBy(분류 함수,groupBy(분류 함수))
     */
    @DisplayName("요리를 다수준으로 그룹화")
    @Test
    public void multiLevelGroupByDish() {
        // when
        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> multiGroupByDish =
                menu.stream()
                        .collect(
                                groupingBy(Dish::getType,
                                        groupingBy(dish -> {
                                            if (dish.getCalories() <= 400) {
                                                return CaloricLevel.DIET;

                                            } else if (dish.getCalories() <= 700) {
                                                return CaloricLevel.NORMAL;

                                            } else {
                                                return CaloricLevel.FAT;
                                            }
                                        })));

        // then
        System.out.println(multiGroupByDish);
        /*  결과 값.
         * {
         * MEAT={DIET=[chicken], NORMAL=[beef], FAT=[pork]},
         * FISH={NORMAL=[salmon], DIET=[prawns]},
         * OTHER={DIET=[rice, season fruit], NORMAL=[french fries, pizza]}
         * }
         */
    }

    /**
     * 6.3.3 서브그룹으로 데이터 수집 - groupBy(분류 함수, counting())
     */
    @DisplayName("각 그룹 별 요리의 갯수 counting")
    @Test
    public void countOfGroupByDish() {
        // when
        Map<Dish.Type, Long> groupByAndCountingDish =
                menu.stream()
                        .collect(
                                groupingBy(Dish::getType,
                                        counting()));

        // then
        System.out.println(groupByAndCountingDish); // {MEAT=3, OTHER=4, FISH=2}
    }

    /**
     * 6.3.3 서브그룹으로 데이터 수집 - groupBy(분류 함수, summarizingInt())
     */
    @DisplayName("분류한 요리들의 각 summary -번외")
    @Test
    public void summaryOfGroupByDish() {
        // when
        Map<Dish.Type, IntSummaryStatistics> summaryOfGroupByDish =
                menu.stream()
                        .collect(
                                groupingBy(Dish::getType,
                                        summarizingInt(Dish::getCalories)));

        // then
        System.out.println(summaryOfGroupByDish);
        /* 결과 값
         * {FISH=IntSummaryStatistics{count=2, sum=750, min=300, average=375.000000, max=450},
         * MEAT=IntSummaryStatistics{count=3, sum=1900, min=400, average=633.333333, max=800},
         * OTHER=IntSummaryStatistics{count=4, sum=1550, min=120, average=387.500000, max=550}}
         */
    }

    /**
     * 6.3.3 서브그룹으로 데이터 수집 - groupBy(분류 함수, maxBy())
     */
    @DisplayName("분류된 요리 중 최대 칼로리 값을 가진 요리를 구하기")
    @Test
    public void maxCalOfGroupByDish() {
        // when
        Map<Dish.Type, Optional<Dish>> maxCalOfGroupByDish =
                menu.stream()
                        .collect(
                                groupingBy(Dish::getType,
                                        maxBy(Comparator.comparing(Dish::getCalories))));
        // then
        System.out.println(maxCalOfGroupByDish);
        /* 결과
         * {
         * FISH=Optional[salmon],
         * MEAT=Optional[pork],
         * OTHER=Optional[pizza]
         * }
         */
    }

    /**
     * 6.3.3 서브그룹으로 데이터 수집 - groupBy(분류함수, collectAndThen(maxBy(), Optional::get)
     */
    @DisplayName("분류된 요리 중 최대 칼로리 값을 가진 요리를 구하기 - collectAndThen 버전")
    @Test
    public void maxCalOfGroupByDishUsingCollectAndThen() {
        // when
        Map<Dish.Type, Dish> maxCalOfGroupByDish =
                menu.stream()
                        .collect(
                                groupingBy(Dish::getType,
                                        collectingAndThen(
                                                maxBy(Comparator.comparing(Dish::getCalories)), Optional::get)));

        // then
        System.out.println(maxCalOfGroupByDish);
        /* 결과 값
         * {OTHER=pizza, MEAT=pork, FISH=salmon}
         */
    }


    /**
     * 6.3.3 서브그룹으로 데이터 수집 - groupBy(분류 함수, summingInt())
     * - groupingBy와 함께 사용하는 다른 컬렉터 예제
     */
    @DisplayName("분류한 요리들의 각 합계를 구하기.")
    @Test
    public void sumOfGroupByDish() {
        // when
        Map<Dish.Type, Integer> sumOfGroupByDish =
                menu.stream()
                        .collect(
                                groupingBy(Dish::getType,
                                        summingInt(Dish::getCalories)));

        // then
        System.out.println(sumOfGroupByDish);
        /* 결과 값
         * {MEAT=1900, OTHER=1550, FISH=750}
         */
    }

    /**
     * 6.3.3 서브그룹으로 데이터 수집 - groupBy(분류 함수, mapping())
     * - groupingBy와 함께 사용하는 다른 컬렉터 예제
     */
    @DisplayName("분류한 요리들을 매핑하기")
    @Test
    public void mappingOfGroupByDish() {
        // when
        Map<Dish.Type, Set<CaloricLevel>> calLevelOfGroupByAndMappingDish =
                menu.stream()
                        .collect(
                                groupingBy(Dish::getType,
                                        mapping(dish -> {
                                            if (dish.getCalories() <= 400) {
                                                return CaloricLevel.DIET;

                                            } else if (dish.getCalories() <= 700) {
                                                return CaloricLevel.NORMAL;

                                            } else {
                                                return CaloricLevel.FAT;

                                            }
                                        }, toCollection(HashSet::new))));

        // then
        System.out.println(calLevelOfGroupByAndMappingDish);
        /* 결과 값
         * {FISH=[NORMAL, DIET], OTHER=[NORMAL, DIET], MEAT=[FAT, NORMAL, DIET]}
         */
    }
}

