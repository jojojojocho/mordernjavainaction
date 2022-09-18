package book.example.mordernjavainaction.chap06;

import book.example.mordernjavainaction.chap04.Dish;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author : 조병상
 * @since : 2022-09-15
 */
public class TestCode {

    List<Dish> menu = new Dish().makeDishes();


    /**
     * 6.2.1 스트림 값에서 최댓값과 최솟값 검색.
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
     * 6.2.1 스트림 값에서 최댓값과 최솟값 검색.
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
     * 6.2.2 요약 연산
     */
    @DisplayName("요리의 칼로리 총합 구하기 (Integer)")
    @Test
    public void sumOfDishCaloriesInt() {
        // when
        Integer sumOfDishCal = menu.stream().collect(Collectors.summingInt(Dish::getCalories));

        // then
        System.out.println(sumOfDishCal); // 4200
        Assertions.assertThat(sumOfDishCal).isEqualTo(4200);
    }

    /**
     * 6.2.2 요약 연산
     */
    @DisplayName("요리의 칼로리 총합 구하기 (Long)")
    @Test
    public void sumOfDishCaloriesLong() {
        // when
        Long sumOfDishCal = menu.stream().collect(Collectors.summingLong(Dish::getCalories));

        // then
        System.out.println(sumOfDishCal); // 4200
    }

    /**
     * 6.2.2 요약 연산
     */
    @DisplayName("요리의 칼로리 총합 구하기(Double)")
    @Test
    public void sumOfDishCaloriesDouble() {
        // when
        Double sumOfDishCal = menu.stream().collect(Collectors.summingDouble(Dish::getCalories));

        // then
        System.out.println(sumOfDishCal); // 4200.0

    }

    /**
     * 6.2.2 요약 연산
     */
    @DisplayName("요리의 칼로리 평균 구하기(Int, Long, Double)")
    @Test
    public void avgOfDishCalories() {
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
     * 6.2.2 요약 연산
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
     * 6.2.3 문자열 연결
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
                .collect(Collectors.joining(",","JoinedName : ", ""));

        // then
        System.out.println(joiningDishNames); // JoinedName : pork,beef,chicken,french_fries,rice,seasonfruit,pizza,prawns,salmon
    }



}
