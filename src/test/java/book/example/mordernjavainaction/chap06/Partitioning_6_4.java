package book.example.mordernjavainaction.chap06;

import book.example.mordernjavainaction.chap04.Dish;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static book.example.mordernjavainaction.chap04.Dish.Type;
import static book.example.mordernjavainaction.chap04.Dish.makeDish;
import static java.util.stream.Collectors.*;

/**
 * 6.4 분할
 *
 * @author : 조병상
 * @since : 2022-09-25
 */
public class Partitioning_6_4 {

    List<Dish> menu = makeDish();

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
        Map<Boolean, Map<Type, List<Dish>>> vegetarianDishGroupbyType =
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

    /**
     * 6.4.2 숫자를 소수와 비소수로 분할하기. - IntStream.rangeClosed().noneMatch()
     */
    @DisplayName("소수와 비소수로 분리하기")
    @Test
    public void isPrime() {
        //when
        boolean result = IsPrime.isPrime(5);

        // then
//        Assertions.assertThat(bool).isEqualTo(false);
        Assertions.assertThat(result).isEqualTo(true);
    }

    /**
     * 6.4.2 숫자를 소수와 비소수로 분할하기. - IsPrime을 이용하여 2~100까지 소수와 비소수로 나누어보기
     */
    @DisplayName("2~100 사이 소수와 비소수로 나누어 보기")
    @Test
    public void classifyPrimeAndNonPrime() {
        // when
        Map<Boolean, List<Integer>> classifiedNumber =
                IntStream.rangeClosed(2, 100).boxed()
                        .collect(partitioningBy(IsPrime::isPrime));

        // then
        System.out.println(classifiedNumber);
        /* 결과 값
         * {
         * false=[4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20, 21, 22, 24, 25, 26, 27, 28, 30, 32, 33,
         *  34, 35, 36, 38, 39, 40, 42, 44, 45, 46, 48, 49, 50, 51, 52, 54, 55, 56, 57, 58, 60, 62,
         *  63, 64, 65, 66, 68, 69, 70, 72, 74, 75, 76, 77, 78, 80, 81, 82, 84, 85, 86, 87, 88, 90,
         *  91, 92, 93, 94, 95, 96, 98, 99, 100],
         * true=[2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97]
         * }
         */
    }

    /**
     * 표 6-1 Collectors 클래스의 정적 팩토리 메서드 사용법 예제 - toList()
     * 스트림의 모든 항목을 리스트로 수집
     */
    @DisplayName("toList 사용법")
    @Test
    public void usingToList() {
        // when
        List<Dish> toList = menu.stream().collect(toList());

        // then
        System.out.println(toList); // [pork, beef, chicken, french fries, rice, season fruit, pizza, prawns, salmon]
        Assertions.assertThat(toList.size()).isEqualTo(9);
    }


    /**
     * 표 6-1 Collectors 클래스의 정적 팩토리 메서드 사용법 예제 - toSet()
     * 스트림의 모든 항목을 중복이 없는 집합으로 수집
     */
    @DisplayName("toSet 사용법")
    @Test
    public void usingToSet() {
        // when
        Set<Dish> toSet = menu.stream().collect(toSet());

        // then
        System.out.println(toSet); // [chicken, french fries, pork, salmon, prawns, season fruit, pizza, beef, rice]
        Assertions.assertThat(toSet.size()).isEqualTo(9);
    }

    /**
     * 표 6-1 Collectors 클래스의 정적 팩토리 메서드 사용법 예제 - toCollection
     * 스트림의 모든 항목을 발행자가 제공하는 컬렉션으로 수집
     */
    @DisplayName("toCollection 사용법")
    @Test
    public void usingToCollection() {
        // when
        ArrayList<Dish> toCollection =
                menu.stream()
                        .collect(toCollection(ArrayList::new));

        // then
        System.out.println(toCollection); // [pork, beef, chicken, french fries, rice, season fruit, pizza, prawns, salmon]
        Assertions.assertThat(toCollection.size()).isEqualTo(9);
    }

    /**
     * 표 6-1 Collectors 클래스의 정적 팩토리 메서드 사용법 예제 - counting
     * 스트림의 항목 수 계산
     */
    @DisplayName("counting 사용법")
    @Test
    public void usingCounting() {
        // when
        Long counting = menu.stream().collect(counting());

        // then
        System.out.println(counting); // 9
        Assertions.assertThat(counting).isEqualTo(9);
    }

    /**
     * 표 6-1 Collectors 클래스의 정적 팩토리 메서드 사용법 예제 - summingInt
     * 스트림의 정수 프로퍼티 값을 더함
     */
    @DisplayName("summingInt 사용법")
    @Test
    public void usingSummingInt() {
        // when
        Integer summingInt =
                menu.stream()
                        .collect(summingInt(Dish::getCalories));

        // then
        System.out.println(summingInt); // 4200
        Assertions.assertThat(summingInt).isEqualTo(4200);
    }

    /**
     * 표 6-1 Collectors 클래스의 정적 팩토리 메서드 사용법 예제 - averagingInt
     * 스트림 항목의 정수 프로퍼티의 평균값 계산
     */
    @DisplayName("averagingInt 사용법")
    @Test
    public void usingAveragingInt() {
        // when
        Double averagingInt =
                menu.stream()
                        .collect(averagingInt(Dish::getCalories));

        // then
        System.out.println(averagingInt); // 466.6666666666667
        Assertions.assertThat(averagingInt).isEqualTo(466.6666666666667);

    }

    /**
     * 표 6-1 Collectors 클래스의 정적 팩토리 메서드 사용법 예제 - summarizingInt
     * 스트림 내 항목의 최댓값, 최솟값, 합계, 평균 등의 정수 정보 통계 수집
     */
    @DisplayName("summarizingInt 사용법")
    @Test
    public void usingSummarizingInt() {
        // when
        IntSummaryStatistics summarizingInt =
                menu.stream()
                        .collect(summarizingInt(Dish::getCalories));

        // then
        System.out.println(summarizingInt); // IntSummaryStatistics{count=9, sum=4200, min=120, average=466.666667, max=800}
        Assertions.assertThat(summarizingInt.getCount()).isEqualTo(9);
        Assertions.assertThat(summarizingInt.getSum()).isEqualTo(4200);

    }

    /**
     * 표 6-1 Collectors 클래스의 정적 팩토리 메서드 사용법 예제 - joining
     * 스트림의 각 항목에 toString 메서드를 호출한 결과 문자열 연결
     */
    @DisplayName("joining 사용법")
    @Test
    public void usingJoining() {
        // when
        String joining =
                menu.stream()
                        .map(Dish::getName)
                        .collect(joining(", "));

        // then
        System.out.println(joining); // pork, beef, chicken, french fries, rice, season fruit, pizza, prawns, salmon

    }

    /**
     * 표 6-1 Collectors 클래스의 정적 팩토리 메서드 사용법 예제 - maxBy
     * 주어진 비교자를 이용하여 스트림의 최댓값 요소를 Optional로 감싼 값을 반환, 스트림에 요소가 없을 때는 Optional.empty() 반환
     */
    @DisplayName("maxBy 사용법")
    @Test
    public void usingMaxBy() {
        // when

        Dish dish = menu.stream()
                .collect(maxBy(Comparator.comparing(Dish::getCalories))).orElseThrow();

        // then
        System.out.println(dish.getCalories()); // 800
        Assertions.assertThat(dish.getCalories()).isEqualTo(800);

    }

    /**
     * 표 6-1 Collectors 클래스의 정적 팩토리 메서드 사용법 예제 - reducing
     * 누적자를 초깃값으로 설정한 다음에 BinaryOperator로 스트림의 각 요소를 반복적으로 누적자와 합쳐 스트림을 하나의 값으로 리듀싱
     */
    @DisplayName("reducing 사용법")
    @Test
    public void usingReducing() {
        // when
        Integer reducing =
                menu.stream()
                        .collect(reducing(0, Dish::getCalories, Integer::sum));

        // then
        System.out.println(reducing); // 4200
        Assertions.assertThat(reducing).isEqualTo(4200);

    }

    /**
     * 표 6-1 Collectors 클래스의 정적 팩토리 메서드 사용법 예제 - collectingAndThen
     * 다른 컬렉터를 감싸고 그 결과에 변환 함수 적용.
     */
    @DisplayName("CollectingAndThen 사용법")
    @Test
    public void usingCollectingAndThen(){
        // when
        Integer collectingAndThen =
                menu.stream()
                        .collect(collectingAndThen(toList(), List::size));

        // then
        System.out.println(collectingAndThen); // 9
        Assertions.assertThat(collectingAndThen).isEqualTo(9);


    }

    /**
     * 표 6-1 Collectors 클래스의 정적 팩토리 메서드 사용법 예제 - groupingBy
     * 하나의 프로퍼티 값을 기준으로 스트림의 항목을 그룹화 하며 기준 프로퍼티값을 결과 맵의 키로 사용.
     */
    @DisplayName("groupingBy 사용법")
    @Test
    public void usingGroupingBy(){
        // when
        Map<Type, List<Dish>> groupingBy =
                menu.stream().collect(groupingBy(Dish::getType));

        // then
        System.out.println(groupingBy);
        /* 결과 값
        {
        OTHER=[french fries, rice, season fruit, pizza],
        MEAT=[pork, beef, chicken],
        FISH=[prawns, salmon]
        }
        */

    }

    /**
     * 표 6-1 Collectors 클래스의 정적 팩토리 메서드 사용법 예제 - partitioningBy
     * 프레디케이트를 스트림의 각 항목에 적용한 결과로 항목 분할
     */
    @DisplayName("partitioningBy 사용법")
    @Test
    public void usingParitioningBy(){
        // when
        Map<Boolean, List<Dish>> partitioningBy =
                menu.stream().collect(partitioningBy(Dish::isVegetarian));

        // then
        System.out.println(partitioningBy);
        /* 결과 값
         * {
         * false=[pork, beef, chicken, prawns, salmon],
         * true=[french fries, rice, season fruit, pizza]
         * }
         */

    }

}

