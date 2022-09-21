package book.example.mordernjavainaction.chap06;

import book.example.mordernjavainaction.chap04.Dish;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : 조병상
 * @since : 2022-09-15
 */
public class TestCode {

    List<Dish> menu = new Dish().makeDishes();


    /**
     * 6.2.1 스트림 값에서 최댓값과 최솟값 검색. - Collectors.maxBy()
     */
    @DisplayName("칼로리가 제일 높은 요리 구하기.")
    @Test
    public void findMaxCaloriesOfDish() {
        // given
        Comparator<Dish> caloriesComparator = Comparator.comparingInt(Dish::getCalories);

        // when
        Dish dishOfMaxCal = menu.stream().collect(Collectors.maxBy(caloriesComparator)).orElseThrow();

        // then
        System.out.println(dishOfMaxCal); // pork
    }

    /**
     * 6.2.1 스트림 값에서 최댓값과 최솟값 검색. - Collectors.minBy()
     */
    @DisplayName("칼로리가 제일 낮은 요리 구하기")
    @Test
    public void findMinCaloriesOfDish() {
        // given
        Comparator<Dish> caloriesComparator = Comparator.comparingInt(Dish::getCalories);

        // when
        Dish minOfDishCal = menu.stream().collect(Collectors.minBy(caloriesComparator)).orElseThrow();

        // then
        System.out.println(minOfDishCal); // season fruit
    }

    /**
     * 6.2.2 요약 연산 - Collectors.summingInt()
     */
    @DisplayName("요리의 칼로리 총합 구하기 (Integer)")
    @Test
    public void CalculateSumOfDishCaloriesInt() {
        // when
        Integer sumOfDishCal = menu.stream().collect(Collectors.summingInt(Dish::getCalories));

        // then
        System.out.println(sumOfDishCal); // 4200
        Assertions.assertThat(sumOfDishCal).isEqualTo(4200);
    }

    /**
     * 6.2.2 요약 연산 - Collectors.SummingLong()
     */
    @DisplayName("요리의 칼로리 총합 구하기 (Long)")
    @Test
    public void CalculateSumOfDishCaloriesLong() {
        // when
        Long sumOfDishCal = menu.stream().collect(Collectors.summingLong(Dish::getCalories));

        // then
        System.out.println(sumOfDishCal); // 4200
    }

    /**
     * 6.2.2 요약 연산 - Collectors.SummingDouble
     */
    @DisplayName("요리의 칼로리 총합 구하기(Double)")
    @Test
    public void CalculateSumOfDishCaloriesDouble() {
        // when
        Double sumOfDishCal = menu.stream().collect(Collectors.summingDouble(Dish::getCalories));

        // then
        System.out.println(sumOfDishCal); // 4200.0

    }

    /**
     * 6.2.2 요약 연산 - Collectors.averagingInt()
     */
    @DisplayName("요리의 칼로리 평균 구하기(Int, Long, Double)")
    @Test
    public void CalculateAvgOfDishCalories() {
        // when
        Double avgOfDishCalInt = menu.stream().collect(Collectors.averagingInt(Dish::getCalories));
        Double avgOfDishCalLong = menu.stream().collect(Collectors.averagingLong(Dish::getCalories));
        Double avgOfDishCalDouble = menu.stream().collect(Collectors.averagingDouble(Dish::getCalories));

        // then
        System.out.println(avgOfDishCalInt); // 466.6666666666667
        System.out.println(avgOfDishCalLong); // 466.6666666666667
        System.out.println(avgOfDishCalDouble); // 466.6666666666667
    }

    /**
     * 6.2.2 요약 연산 - Collectors.summarizingInt()
     */
    @DisplayName("두 개 이상의 연산을 한번에 수행")
    @Test
    public void calculateSummaryOfDishCalories() {
        // when
        IntSummaryStatistics summaryInt = menu.stream().collect(Collectors.summarizingInt(Dish::getCalories));
        LongSummaryStatistics summaryLong = menu.stream().collect(Collectors.summarizingLong(Dish::getCalories));
        DoubleSummaryStatistics summaryDouble = menu.stream().collect(Collectors.summarizingDouble(Dish::getCalories));

        // then
        System.out.println(summaryInt); // IntSummaryStatistics{count=9, sum=4200, min=120, average=466.666667, max=800}
        System.out.println(summaryInt.getMax()); // 800
        System.out.println(summaryInt.getMin()); // 120
        System.out.println(summaryInt.getAverage()); // 466.6666666666667
        System.out.println(summaryInt.getSum()); // 4200
        System.out.println(summaryInt.getCount()); // 9

        System.out.println(summaryLong); // LongSummaryStatistics{count=9, sum=4200, min=120, average=466.666667, max=800}
        System.out.println(summaryDouble); // DoubleSummaryStatistics{count=9, sum=4200.000000, min=120.000000, average=466.666667, max=800.000000}
    }

    /**
     * 6.2.3 문자열 연결 - Collectors.joining()
     */
    @DisplayName("모든 요리의 이름을 연결하기.")
    @Test
    public void joiningDishNames() {
        /*
         * joining 메서드는 내부적으로 StringBuiler를 이용하여 문자열을 하나로 만듬.
         * StringBuiler - 단일 쓰레드에서 성능 최고. => Not Thread Safe(동기화 x)
         * StringBuffer - 멀티 쓰레드 환경에서 Thread Safe(동기화 o)

         * 정리
         * String - 문자열 연산이 적고 멀티쓰레드 환경일 경우
         * StringBuffer - 문자열 연산이 많고 멀티쓰레드 환경일 경우
         * StringBuilder - 문자열 연산이 많고 단일쓰레드이거나 동기화를 고려하지 않아도 되는 경우
         */
        String joiningDishNames = menu.stream()
                .map(dish -> dish.getName().replaceAll(" ", ""))
                .collect(Collectors.joining(",", "JoinedName : ", ""));

        // then
        System.out.println(joiningDishNames); // JoinedName : pork,beef,chicken,french_fries,rice,seasonfruit,pizza,prawns,salmon
    }

    /**
     * 6.2.4 범용 리듀싱 요약 연산. - Collectors.reducing()
     */
    @DisplayName("메뉴의 모든 칼로리 합계")
    @Test
    public void CalculateSumOfDishCalories() {
        // when
        Integer sumOfDishCal = menu.stream().collect(Collectors.reducing(0, Dish::getCalories, (a, b) -> a + b));

        // then
        System.out.println(sumOfDishCal);
        Assertions.assertThat(sumOfDishCal).isEqualTo(4200);
    }

    /**
     * 6.2.4 범용 리듀싱 요약 연산. - Collectors.reducing()
     */
    @DisplayName("한개의 인수를 가진 reducing 이용하기.")
    @Test
    public void findDishOfMaxCalories() {
        // when
        Dish dishOfMaxCal = menu.stream()
                .collect(Collectors.reducing(((dish, dish2) -> dish.getCalories() > dish2.getCalories() ? dish : dish2)))
                .orElseThrow();

        // then
        System.out.println(dishOfMaxCal); // pork
    }

    /**
     * 6.2.4 범용 리듀싱 요약 연산. - Collectors.reducing()
     * 컬렉션 프레임워크 유연성 : 같은 연산도 다양한 방식으로 수행할 수 있다.
     * 메뉴 칼로리의 합계 구하기.
     */
    @DisplayName("The sum of calories of the menu.")
    @Test
    public void CalculateTotalCalOfMenu() {
        // when
        Integer totalCaloriesOfMenu
                = menu.stream()
                .collect(Collectors.reducing(0,
                        Dish::getCalories,
                        Integer::sum));

        // then
        System.out.println(totalCaloriesOfMenu); // 4200
        Assertions.assertThat(totalCaloriesOfMenu).isEqualTo(4200);
    }

    /**
     * 6.2.4 범용 리듀싱 요약 연산 - stream().map().reduce()
     * 메뉴 칼로리의 합계 구하기.
     */
    @DisplayName("the sum of calories of the menu")
    @Test
    public void CalculateSumOfCalOfMenuUsingMapAndReduce() {
        // when
        Integer sumOfCalOfMenu
                = menu.stream()
                .map(Dish::getCalories)
                .reduce(Integer::sum)
                .orElse(0);
        // then
        System.out.println(sumOfCalOfMenu); // 4200
        Assertions.assertThat(sumOfCalOfMenu).isEqualTo(4200);
    }

    /**
     * 6.2.4 범용 리듀싱 요약 연산 - stream().mapToInt().sum()
     * 메뉴 칼로리의 합계 구하기.
     */
    @DisplayName("The sum of calories of the menu")
    @Test
    public void calculateSumOfCalOfMenu() {
        // when
        int sumOfCalOfMenu = menu.stream().mapToInt(Dish::getCalories).sum();

        // then
        System.out.println(sumOfCalOfMenu); // 4200
        Assertions.assertThat(sumOfCalOfMenu).isEqualTo(4200);
    }

    /**
     * 퀴즈 6-1 리듀싱으로 문자열 연결하기.
     */
    @DisplayName("6.2.3에서 사용한 reducing 컬렉터가 올바른 코드 찾기.")
    @Test
    public void joiningTheStrings() {
        // when
        String method1 = menu.stream()
                .map(Dish::getName)
                .collect(Collectors.reducing((s1, s2) -> s1 + s2))
                .get();

//        String method2 = menu.stream()
//                .collect(Collectors.reducing(((d1, d2) ->d1.getName() + d2.getName() )))
//                .get();

        String method3 = menu.stream().collect(Collectors.reducing("", Dish::getName, (s1, s2) -> s1 + "," + s2));

        // then
        System.out.println(method1); // porkbeefchickenfrench_friesriceseason fruitpizzaprawnssalmon
//        System.out.println(method2); // 컴파일 오류
        System.out.println(method3); // ,pork,beef,chicken,french_fries,rice,season fruit,pizza,prawns,salmon
    }

    /**
     * 6.3 그룹화 - Collectors.groupBy()
     */
    @DisplayName("같은 타입으로 그룹화하기. - 메서드 참조")
    @Test
    public void ClassifyByDishTypeRefMethod() {
        // when
        Map<Dish.Type, List<Dish>> classifiedMapByDishType = menu.stream().collect(Collectors.groupingBy(Dish::getType));

        // then
        System.out.println(classifiedMapByDishType.entrySet()); // [OTHER=[french_fries, rice, season fruit, pizza], MEAT=[pork, beef, chicken], FISH=[prawns, salmon]]

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
        System.out.println(caloricLevelMap); // {FAT=[pork], DIET=[chicken, rice, season fruit, prawns], NORMAL=[beef, french_fries, pizza, salmon]}
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
        System.out.println(dishNameGroupByDishType); // {OTHER=[french_fries, rice, season fruit, pizza], MEAT=[pork, beef, chicken], FISH=[prawns, salmon]}
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
        System.out.println(collect); // {FISH=[roasted, tasty, fresh, delicious], MEAT=[salty, greasy, roasted, fried, crisp], OTHER=[salty, greasy, natural, light, tasty, fresh, fried]}
    }


}
