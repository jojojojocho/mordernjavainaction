package book.example.mordernjavainaction.chap05;

import book.example.mordernjavainaction.chap04.Dish;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author : 조병상
 * @since : 2022-08-29
 */

public class TestCode {

    List<Dish> menu = new Dish().makeDishes();
    List<Dish> specialMenu = Arrays.asList(
            new Dish("seasonal fruit", true, 120, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER)
    );

    /**
     * 데이터 컬렉션 반복을 명시적으로 관리하는 외부 반복코드와 내부 반복 코드 비교
     * 요구사항 : 외부 반복의 vegetarianDishes와 내부반복의 vegetarianDishes의 데이터가 같은지 비교.
     */
    @DisplayName("외부 반복 코드와 내부 반복 코드 비교")
    @Test
    public void useExternalForeach() {

        //given
        List<Dish> vegetarianDishesUseForeach = new ArrayList<>(); // 외부 반복
        List<Dish> vegetarianDishesUseStream; // 내부 반복

        //when
        // 채식이면 vegetarianDishesUseForeach 리스트에 추가 (외부 반복 코드)
        for (Dish dish : menu) {
            if (dish.isVegetarian()) {
                vegetarianDishesUseForeach.add(dish);
            }
        }

        // 채식이면 vegetarianDishesUseStream 리스트에 추가 (내부 반복 코드)
        vegetarianDishesUseStream = menu.stream()
                .filter(Dish::isVegetarian)
                .collect(Collectors.toList());

        //then
        //외부 반복과 내부반복 각 인덱스의 요리 이름 비교
        for (int i = 0; i < vegetarianDishesUseForeach.size(); i++) {
            assertThat(vegetarianDishesUseForeach.get(i)).isEqualTo(vegetarianDishesUseStream.get(i));
        }
    }


    /**
     * 요구사항(문제) : 채식인 것을 필터링
     * 해결방법 : 스트림 - filter - collect를 사용 한다.
     * 예상되는 결과 값과 타입 : 채식인 것들이 걸러진 리스트.
     * 검증 : 마지막에 반환 된 리스트의 요소들이 채식인지 개별 확인.
     */
    @DisplayName("프레디 케이트로 필터링")
    @Test
    public void usePredicateFilteringMethod() {
        //when
        //채식인 것들만 필터링 해서 리스트로 collect
        List<Dish> vegitarianMenu = menu.stream().filter(Dish::isVegetarian)
                .collect(Collectors.toList());

        //then
        //vegitarianMenu의 각 요소들이 채식인지 검증
        vegitarianMenu.stream().forEach(dish -> assertThat(dish.isVegetarian()).isEqualTo(true));
    }


    /**
     * 요구사항(문제) : 리스트 안에 있는 짝수를 구하고, 중복된 값을 제거 해라.
     * 해결방법 : stream - filter - distinct - collect
     * 예상 되는 결과 값과 타입 : 각 요소들이 unique한 짝수로 이루어진 리스트
     * 검증 : 최초 리스트의 짝수의 count와 결과 리스트의 짝수 카운트를 비교.
     */
    @DisplayName("고유 요소 필터링")
    @Test
    public void useUniqueElementFilteringMethod() {
        //given
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);

        //when
        //짝수일 경우에만 필터링 후 중복제거하여 리스트로 만듬.
        List<Integer> uniqueEvenNumberList = numbers.stream()
                .filter(number -> number % 2 == 0)
                .distinct()
                .collect(Collectors.toList());

        //then
        //중복제거 완료 후에는 2,4가 있을 것이므로 리스트 사이즈가 2 일 것이다.
        assertThat(uniqueEvenNumberList.size()).isEqualTo(2);
    }

    /**
     * 5.2.1 : 프레디 케이트를 이용한 슬라이싱
     * 요구사항(문제) : dish 객체로 이루어진 정렬된 리스트 안의 요소 중 320칼로리 이하의 요리만 필터링 해라.
     * 해결 방법 : stream - takeWhile - collect
     * 예상되는 결과값 : 320칼로리 이하의 dish로 이루어진 리스트.
     * 검증 : 결과 리스트의 각 요소 별로 칼로리가 320이하 인지 개별 확인.
     */
    @DisplayName("takeWhile을 사용 한 슬라이싱") //takeWhile은 정렬된 상태에서만 사용이 가능하다.
    @Test
    public void useTakeWhileSlicingMeThod() {

        //when
        //filter를 이용한 필터링 코드
        List<Dish> filteredMenu = specialMenu.stream()
                .filter(dish -> dish.getCalories() < 320)
                .collect(Collectors.toList());

        //takeWhile을 이용한 슬라이싱 코드
        List<Dish> slicedMenu = specialMenu.stream()
                .takeWhile(dish -> dish.getCalories() < 320)
                .collect(Collectors.toList());

        //then
        // 각 요소가 320이하 칼로리인지 검증.
        slicedMenu.stream().forEach(dish -> assertThat(dish.getCalories() < 320));
    }

    /**
     * 5.2.1 : 프레디 케이트를 이용한 슬라이싱
     * 요구사항(문제) : dish 객체로 이루어진 정렬된 리스트 안의 요소 중 320칼로리 이상의 요리만 필터링 해라.
     * 해결 방법 : stream - dropWhile - collect
     * 예상되는 결과값 : 320칼로리 이상의 dish로 이루어진 리스트.
     * 검증 : 결과 리스트의 각 요소 별로 칼로리가 320이상 인지 개별 확인.
     */
    @DisplayName("dropWhile을 사용 한 슬라이싱") // dropWhile은 정렬된 상태의 스트림에서만 사용이 가능하다.
    @Test
    public void useDropWhileSlicingMeThod() {

        //when
        //filter를 이용한 필터링 코드
        List<Dish> filteredMenu = specialMenu.stream()
                .filter(dish -> dish.getCalories() < 320)
                .collect(Collectors.toList());

        //dropWhile을 이용한 슬라이싱 코드
        List<Dish> slicedMenu = specialMenu.stream()
                .dropWhile(dish -> dish.getCalories() < 320)
                .collect(Collectors.toList());

        //then
        // 각 요소가 320이상 칼로리인지 검증.
        slicedMenu.stream().forEach(d -> assertThat(d.getCalories() > 320));
    }


    /**
     * 5.2.2 스트림 축소
     * 요구사항(문제) : 정렬된 dish 객체 List에서 300칼로리 이상의 요리들 중 요소 n개를 반환
     * 해결 방법 : stream - filter - limit - collect
     * 에상되는 결과 값 : 300칼로리 이상의 요리로 이루어진 size가 N 개인 리스트
     * 검증 : 1. 리스트 각 요소별로 300칼로리 이상인지 확인,
     */
    @DisplayName("스트림 축소")
    @Test
    public void useLimit() {

        int n = 10; // 조건식에 부합 하는 것을 몇 개 반환할 것인가?

        //when
        //칼로리가 300초과 인 요리들을 N개 가져와서 리스트로 반환
        List<Dish> dishes = specialMenu.stream()
                .filter(dish -> dish.getCalories() >= 300)
                .limit(n)
                .collect(Collectors.toList());

        //then
        //리스트의 각 요소들이 300칼로리 이상인지 확인
        dishes.stream().forEach(dish -> assertThat(dish.getCalories() >= 300));
        dishes.stream().forEach(dish -> System.out.println(dish.getName()));

        //리스트의 사이즈가 N인지 확인.
//        Assertions.assertThat(dishes.stream().count()).isEqualTo(N);
    }

    /**
     * 5.2.3 요소 건너 뛰기
     * 요구사항 (문제) : 300칼로리 이상의 처음 두 요리를 건너 뛴 다음에 300칼로리가 넘는 나머지 요리를 반환.
     * 해결 방법 : stream - filter - skip - collect
     * 예상되는 결과 값과 타입 : 300칼로리 이상의 요리 중 앞의 2개를 뺀 나머지 300칼로리 이상의 요리 리스트
     * 검증 : filter만 걸었을 때의 길이와 skip을 했을 때의 길이 + n 으로 검증 시도.
     */
    @DisplayName("요소 건너뛰기")
    @Test
    public void useSkip() {
        long n = 2;

        //when
        List<Dish> dishes = menu.stream().filter(dish -> dish.getCalories() > 300)
                .skip(n)                        //n개 만큼 건너뜀.
                .collect(Collectors.toList());

        //then
        assertThat(menu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .count()).isEqualTo(dishes.size() + n);

        dishes.stream().forEach(dish -> assertThat(dish.getCalories() > 300));
        dishes.stream().forEach(dish -> System.out.println(dish.getName()));
    }

    /**
     * 5-1 퀴즈 : 필터링
     * 요구사항 (문제) : 스트림을 이용해서 처음 등장하는 두개의 고기 요리를 필터링 하시오.
     * 해결방법 : stream - filter - limit
     * 예상 되는 결과 값 : dish 타입이 meat인 맨 앞 2가지 요리로 이루어진 리스트
     * 검증 방법
     * 1. size가 2인지 확인.
     * 2. 모든 요소가 meat 타입인지 확인.
     */

    @DisplayName("필터링")
    @Test
    public void filteringFirstTwoMeatDish() {

        //when
        //limit 사용
        List<Dish> useLimitTwoMeatDishList = menu.stream()
                .filter(dish -> dish.getType() == Dish.Type.MEAT)
                .limit(2)
                .collect(Collectors.toList());

        //then
        assertThat(useLimitTwoMeatDishList.size()).isEqualTo(2);  //size 검증
        useLimitTwoMeatDishList.stream().forEach(dish -> assertThat(dish.getType() == Dish.Type.MEAT)); //타입 검증


    }

    /**
     * 5.3 매핑
     * 특정 객체에서 특정 데이터를 선택하는 작업.
     */

    /**
     * 5.3.1 스트림의 각 요소에 함수 적용하기
     * 요구사항 (문제) : dish 객체를 가지고 있는 리스트에서 각 dish 객체를 dish.getName으로 매핑하고 싶다.
     * 로직 : stream - map - collect 사용
     * 예상 되는 결과 값: 요리 이름으로 구성된 리스트.
     * 검증방법 : dish 객체 리스트와 요리이름 리스트를 순회하면서 이름을 비교.
     */
    @DisplayName("스트림으로 각 요소에 함수 적용 1")
    @Test
    public void useMapMethodForDishToDishName() {

        //when
        List<String> dishNames = menu.stream()
                .map(dish -> dish.getName())
                .collect(Collectors.toList());

        //then
        for (int i = 0; i < menu.size(); i++) {
            assertThat(menu.get(i).getName()).isEqualTo(dishNames.get(i));
        }

    }

    /**
     * 5.3.1 스트림의 각 요소에 함수 적용하기
     * 요구사항 (문제) : 단어가 포함된 리스트가 주어졌을 때 각 요소(단어)별 길이를 알고 싶다.
     * 로직 : stream - map - collect 사용
     * 예상 되는 결과 값: 단어의 길이로 이루어진 리스트.
     * 검증방법 : 2개의 리스트를 순회해가면서 길이값을 비교.
     */

    @DisplayName("스트림으로 각 요소에 함수 적용 2")
    @Test
    public void useMapMethodForWordToWordLength() {
        //given
        List<String> words = Arrays.asList("Modern", "Java", "In", "Action");

        //when
        List<Integer> wordLengths = words.stream()
                .map(word -> word.length())
                .collect(Collectors.toList());

        //then
        for (int i = 0; i < words.size(); i++) {
            assertThat(words.get(i).length()).isEqualTo(wordLengths.get(i));
        }
    }

    /**
     * 5.3.1 스트림의 각 요소에 함수 적용하기
     * 요구사항 (문제) : dish 객체로 구성된 리스트가 주어 졌을 때 dishName의 길이를 알고싶다.
     * 로직 : stream - map - map - collect 사용
     * 예상 되는 결과 값: 요리 이름의 길이로 이루어진 리스트.
     * 검증방법 : menu 리스트를 순회하면서 요리 이름의 길이가 맞는지 비교.
     */


    @DisplayName("스트림으로 각 요소에 함수 적용 3")
    @Test
    public void useMapMethodForDishToDishNameLength() {

        //when
        List<Integer> dishNameLength = menu.stream()
                .map(dish -> dish.getName())
                .map(name -> name.length())
                .collect(Collectors.toList());

        //then
        for (int i = 0; i < menu.size(); i++) {
            assertThat(dishNameLength.get(i)).isEqualTo(menu.get(i).getName().length());
        }

    }

    /**
     * 5.3.2 스트림 평면화
     * 요구사항 (문제) : .
     * 로직 : stream - map - map - collect 사용
     * 예상 되는 결과 값: 요리 이름의 길이로 이루어진 리스트.
     * 검증방법 : menu 리스트를 순회하면서 요리 이름의 길이가 맞는지 비교.
     * <p>
     * 요구사항 (문제) : String타입의 단어로 이루어진 리스트를 받아 고유문자로 매핑해 반환.
     * 로직 : stream - map(split("")) - map(Arrays.stream) - distinct() - collect(toList)
     * 예상 되는 결과 값:
     * 1. stream => String
     * 2. map(split("")) => String[]
     * 3. map(Arrays.stream) => Stream<String>
     * 4. distinct() => Stream<String>
     * 5. collect(toList) => List<Stream<String>>
     * 검증 : 반환 타입 자체가 의도하던 타입이 아니라서 실패. 의도한 타입은 List<String>타입.
     */
    @DisplayName("map과 Array.stream 활용")
    @Test
    public void useMapAndArraysStream() {
        //given
        List<String> words = Arrays.asList("Modern", "Java", "In", "Action");

        //when
        /**
         * 로직 : stream - map(split) - distinct - collect
         * 실패 사유 - 반환타입이 List<String[]>이고, 중복도 제거하지 못하였으므로 요구사항을 충족하지 못함.
         */
        List<String[]> resultOfUseMap = words.stream()
                .map(word -> word.split(""))
                .distinct()
                .collect(Collectors.toList());
        //검증
        resultOfUseMap.stream()
                .forEach(strArr -> assertThat(strArr).isInstanceOf(String[].class));

        /**
         * map과 Arrays.stream 활용
         * 로직 : stream - map(split) - map(Arrays.stream) -distinct - collect
         * 실패 사유 - 반환 타입이 List<Stream<String>>이고, 중복도 제거하지 못하였으므로 요구사항에 부합하지 않음.
         */
        //given
        String[] arrayOfWords = {"Goodbye", "World"};
        //when
        Stream<String> streamOfWords = Arrays.stream(arrayOfWords);
        //then
        streamOfWords.forEach(string -> assertThat(string).isInstanceOf(String.class));


        //when
        List<Stream<String>> resultOfUseArraysStream = words.stream()
                .map(word -> word.split(""))
                .map(splitArr -> Arrays.stream(splitArr))
                .distinct()
                .collect(Collectors.toList());

        //then
        resultOfUseArraysStream.stream()
                .forEach(stream -> assertThat(stream).isInstanceOf(Stream.class));
    }

    /**
     * 5.3.2 스트림 평면화
     * 요구사항 (문제) : String타입의 단어로 이루어진 리스트를 받아 고유문자로 매핑해 반환.
     * 로직 : stream - map(split("")) - flatmap(Arrays.stream()) - collect(toList)
     * 예상 되는 결과 값: 중복문자가 없는 String타입을 요소로 가지고 있는  리스트
     * 검증방법 : Map의 키를 이용해 중복여부 검증
     */
    @DisplayName("flatMap 사용")
    @Test
    public void useFlatMap() {
        //given
        List<String> words = Arrays.asList("Modern", "Java", "In", "Action");

        //when
        List<String> result = words.stream()
                .map(word -> word.split(""))
                .flatMap(splitArr -> Arrays.stream(splitArr))
                .distinct()
                .collect(Collectors.toList());

        //then
        //맵에 character가 없으면 pass , 있으면 테스트 실패
        Map<String, String> validationMap = new HashMap<>();
        result.stream().forEach(character -> {
            assertThat(validationMap.getOrDefault(character, null)).isEqualTo(null);
            validationMap.put(character, character);
        });
        System.out.println(result);
    }


    /**
     * 5.2 퀴즈 1번 문제: 매핑
     * 요구사항 :숫자를 요소로 가지고 있는 리스트가 주어졌을 때 각 요소를 각 숫자의 제곱으로 변환하여 리스트를 반환.
     * ex) [1,2,3,4,5] => [1,4,9,16,25]
     * 로직 : stream - map - collect
     * 예상되는 결과 값: 각 요소의 제곱으로 이루어진 List<Integer>
     * 검증 : 각 요소별 제곱근이 맞는지 순회 하면서 확인
     */
    @DisplayName("숫자리스트를 숫자제곱리스트로 변환해서 반환하기")
    @Test
    public void applySquared() {
        //given
        int[] nArr = {1, 2, 3, 4, 5};

        //int[] -> List<Integer>
        List<Integer> nList = Arrays.stream(nArr).boxed().collect(Collectors.toList());

        //when
        //제곱으로 매핑
        List<Integer> squaredNumberList = nList.stream()
                .map(number -> number * number)
                .collect(Collectors.toList());

        //then
        for (int i = 0; i < nList.size(); i++) {
            Integer element = nList.get(i);
            assertThat(squaredNumberList.get(i)).isEqualTo(element * element);
        }
    }

    /**
     * 5.2 퀴즈 2번 문제: 매핑
     * 요구사항 : 숫자를 요소로 가지고 있는 숫자리스트가 2개 있을 때, 모든 숫자쌍의 경우의 수를 반환하시오.
     * ex) [1,2,3], [3,4] => [(1,3), (1,4), (2,3), (2,4), (3,3), (3,4)]
     * 로직 : 브루트 포스를 이용한 stream
     * list1 - stream - flatmap ( list2 - stream - map() ).collect
     * 예상되는 결과 값 : 숫자쌍으로 이루어진 리스트를 요소로 가지고 있는 리스트 => List<int[]>
     * 검증 : 브루트포스를 이용하여 숫자쌍을 조합하고 결과리스트의 각 요소와 비교
     */
    @DisplayName("5.2 퀴즈 2,3번문제")
    @Test
    public void mappingTwoNumberToOnePairListOfNumber() {
        //given
        int[] firstArr = {1, 2, 3};
        int[] secondArr = {3, 4};

        //int[] -> List<Integer>
        List<Integer> firstNumList = Arrays.stream(firstArr).boxed().collect(Collectors.toList());
        List<Integer> secondNumList = Arrays.stream(secondArr).boxed().collect(Collectors.toList());

        //when
        //pair 만들기
        List<int[]> result = firstNumList.stream()
                .flatMap(i -> secondNumList.stream()
                        .map(j -> new int[]{i, j}))
                .collect(Collectors.toList());

        //then
        int index = 0;
        while (index < result.size()) {
            for (int i : firstArr) {
                for (int j : secondArr) {
                    assertThat(result.get(index++)).isEqualTo(new int[]{i, j});
                }
            }
        }
        /**
         * 5.2 퀴즈 3번 문제: 매핑
         * 요구사항 : 5.2 퀴즈 2번 문제의 결과 값 중 pair의 합을 3으로 나누었을 때, 나머지가 0인 pair만 반환하시오.
         * 로직 : stream - flatmap( stream - filter - map ).collect
         * 예상되는 결과 값 : 합이 3으로 나누어 떨어지는  pair를 요소로 가지고있는 List<int[]>
         * 검증 : 결과 리스트의 최종 값이 3으로 나누어 떨어지는지 확인.
         */

        //when
        List<int[]> filteredResult = firstNumList.stream()
                .flatMap(i -> secondNumList.stream()
                        .filter(j -> (i + j) % 3 == 0)
                        .map(j -> new int[]{i, j}))
                .collect(Collectors.toList());

        //then
        filteredResult.stream()
                .forEach(pair -> assertThat(Arrays.stream(pair).sum() % 3).isEqualTo(0));
    }


    /**
     * 5.4 검색과 매칭 - allMatch, anyMatch, noneMatch, findFirst, findAny
     */

    /**
     * 5.4.1 프레디케이트가 적어도 한 요소와 일치하는지 확인 - anyMatch(하나라도 매치 하는지 확인)
     * 요구사항 : 이 요리가 채식 요리이면 print문 출력
     * 로직 : if 문과 stream().anyMatch를 이용한 분기
     * 예상되는 결과 값 : 현재 메뉴에는 채식 요리가 들어가 있으므로 채식 요리라는 print문이 출력.
     * 검증 : expected : 채식 요리를 포함 일 경우 pass
     */
    @DisplayName("프레디케이트가 적어도 한 요소와 일치하는지 확인")
    @Test
    public void useAnyMatch() {

        //when
        if (menu.stream().anyMatch(dish -> dish.isVegetarian())) {
            System.out.println("이 메뉴는 채식 요리 입니다.");
        }

        //then
        assertThat(menu.stream().anyMatch(dish -> dish.isVegetarian())).isEqualTo(true);
    }

    /**
     * 5.4.2. 프레디케이트가 모든 요소와 일치하는지 검사 - allMatch(모두 매치 하는지 확인)
     * 요구사항 : 메뉴의 모든 요소가 1000칼로리 미만인지 확인.
     * 로직 : if문과 stream() - allMatch 사용.
     * 예상되는 결과 값 : 모든 메뉴가 1000칼로리 미만이면 건강식이라는 프린트문이 출력
     * 검증 : expected : 1000칼로리 미만이라면 true / 아니라면 false
     */
    @DisplayName("프레디케이트가 모든 요소와 일치하는지 검사")
    @Test
    public void useAllMatch() {

        //when
        if (menu.stream().allMatch(dish -> dish.getCalories() < 1000)) {
            System.out.println("이 메뉴는 건강식 입니다.");
        }

        //then
        assertThat(menu.stream().allMatch(dish -> dish.getCalories() < 1000)).isEqualTo(true);

    }

    /**
     * 5.4.2. 프레디케이트가 모든 요소와 일치하는지 검사 - noneMatch(모두 매치하지 않는지 확인)
     * 요구사항 : dish.getCalories() >= 1000 조건이 모두 매치하지 않는지 검사
     * 로직 : stream - noneMatch
     * 예상 되는 결과 값 : 모든 메뉴가 1000칼로리 미만이면 건강식이라는 프린트문이 출력
     * 검증 : expected : 1000칼로리 미만 true / 1000칼로리 이상 false
     */

    @DisplayName("주어진 프레디케이트가 일치하는 요소가 없는지 확인.")
    @Test
    public void useNoneMatch() {

        //when
        if (menu.stream().noneMatch(dish -> dish.getCalories() >= 1000)) {
            System.out.println("이 메뉴는 건강식입니다.");
        }

        //then
        assertThat(menu.stream().noneMatch(dish -> dish.getCalories() >= 1000)).isEqualTo(true);
    }

    /**
     * 5.4.3. 요소 검색 - findAny
     * 요구사항 : 요리 중 채식인 요리가 있으면 반환
     * 로직 : stream - filter(isvegetarian) - findAny - orElseThrow()
     * 예상 되는 결과 값 : 단일 결과 값 dish 객체 or null
     * 검증 : 결과 값 dish 객체의 isVegetarian의 값이 true인지 확인.
     */
    @DisplayName("요소검색")
    @Test
    public void findAnyElement() throws NoSuchElementException {
        //when
        Optional<Dish> dish = menu.stream().filter(d -> d.isVegetarian()).findAny();
        //then
        if (dish.isPresent()) {
            dish.ifPresent(d -> assertThat(d.isVegetarian()).isEqualTo(true));
        } else {
            throw new NoSuchElementException();
        }

        /**
         * Optional Class의  메서드 사용해보기
         */
        //isPresent() : 존재하면 true 반환 없으면 false
        boolean present = menu.stream().filter(d -> d.isVegetarian()).findAny().isPresent();

        //ifPresent(Consumer<T>) : 존재하면 주어진 블록을 실행
        menu.stream().filter(d -> d.isVegetarian()).findAny().ifPresent(d -> System.out.println(d.getName()));

        //T get() : 존재하면 반환, 없으면 NoSuchElement 반환
        Dish dish1 = menu.stream().filter(d -> d.isVegetarian()).findAny().get();

        //T orElse(T other) : 값이 존재하면 값을 반환 값이 없으면 기본값 반환.
        Dish dish2 = menu.stream().filter(d -> d.isVegetarian()).findAny().orElse(new Dish());
    }

    /**
     * 5.4.4. 첫번째 요소 찾기
     * 요구사항 : 리스트 안에 3으로 나누어 떨어지는 제일 첫 번째 값의 제곱값을 구해라.
     * 로직 : stream - filter(x/3 == 0) - map(x*x) - findFirst
     * 예상 되는 결과 값 : 리스트의 3의 배수 중 가장 첫번째 값의 제곱값
     * 검증 : 결과값을 sqrt 후 3으로 나누었을 때 값이 0인지 체크
     */
    @DisplayName("첫 번째 요소 찾기")
    @Test
    public void findFirstElement() {
        //given
        List<Integer> listOfNumber = Arrays.asList(1, 2, 3, 4, 5);

        //when
        Optional<Integer> firstSquareDivisibleByThree = listOfNumber
                .stream()
                .filter(x -> x / 3 == 0)
                .map(x -> x * x)
                .findFirst();

        //then
        if (firstSquareDivisibleByThree.isPresent()) {
            firstSquareDivisibleByThree.ifPresent(number -> Assertions.assertThat(Math.sqrt(number) / 3).isEqualTo(0));
        } else {
            throw new NoSuchElementException();
        }
    }

    /*
     * 5.5 리듀싱
     * - 모든 스트림 요소를 전부 처리해서 값으로 도출 하는 것을 리듀싱 연산이라고 한다.
     * - 함수형 프로그래밍 언어 용어로는 이 과정이 마치 종이를 작은조각이 될 때 까지 반복해서 접는 것과 비슷하다는 의미로 폴드라고 부른다.
     */

    /**
     * 5.5.1 요소의 합
     * problem : 스트림의 각 요소의 합을 구해라
     * logic : for문 => 스트림으로 변환
     * expected result : sum of stream elements
     * validation : 외부반복을 이용한 요소의합, 요소의곱과 스트림을 이용한 요소의합, 요소의 곱을 비교.
     */
    @DisplayName("요소의 합")
    @Test
    public void sumOfElements() {
        /*
         *  외부 반복을 통한 요소의 합
         */
        int[] numbers = new int[]{4, 5, 3, 9};
        int sum = 0;
        for (int x : numbers) {
            sum += x;
        }
        /*
         *  외부 반복을 통한 요소의 곱
         */
        int multiply = 1;
        for (int x : numbers) {
            multiply *= x;
        }


        /*
         *  스트림을 사용한 요소의 합
         */
        int sumOfStream = Arrays.stream(numbers).reduce(0, Integer::sum);

        /*
         *  스트림을 사용한 요소의 곱
         */
        int multiplyOfStream = Arrays.stream(numbers).reduce(1, (a, b) -> (a * b));

        /*
         *  초깃값이 없는 reduce의 경우 Optional 타입으로 반환된다.
         */
        OptionalInt sumOfStreamNoInit = Arrays.stream(numbers).reduce((a, b) -> a + b);




        /*
         *  검증
         */
        Assertions.assertThat(sumOfStream).isEqualTo(sum);          // 초기값이 있는 요소의 합
        Assertions.assertThat(multiplyOfStream).isEqualTo(multiply);    //초기값이 있는 요소의 곱
        Assertions.assertThat(sumOfStreamNoInit.getAsInt()).isEqualTo(sum);      //초기값이 없는 요소의 합

    }

    /**
     * 5.5.1 요소의 합 - 최댓값, 최솟값
     * problem : 리스트에서 최댓값과 최솟값을 구해라
     * logic : find min and max value by using stream.
     * expected result : min value and max value
     * validation : array에서 구한 max, min과 List에서 구한 max, min을 비교
     */
    @DisplayName("요소의 합")
    @Test
    public void findMinMax() {
        //given
        int[] numbers = new int[]{4, 5, 3, 9};
        List<Integer> nList = Arrays.stream(numbers).boxed().collect(Collectors.toList());

        //when
        int maxFromArr = Arrays.stream(numbers).reduce(Math::max).orElseThrow();
        int minFromArr = Arrays.stream(numbers).reduce(Math::min).orElseThrow();

        Integer maxFromList = nList.stream().reduce(Math::max).orElseThrow();
        Integer minFromList = nList.stream().reduce(Math::min).orElseThrow();

        //then
        Assertions.assertThat(maxFromArr).isEqualTo(maxFromList);
        Assertions.assertThat(minFromArr).isEqualTo(minFromList);


    }

    /**
     * 퀴즈 5-3 리듀스
     * problem : map과 reduce를 이용하여 스트림의 요리개수를 계산.
     * logic : stream - map - reduce
     * expected result : 요리 갯수
     * validation : stream 안의 element의 갯수와 reduce결과를 비교
     */
    @DisplayName("스트림 안에있는 요리갯수를 계산")
    @Test
    public void countOfDishElement() {
        //when
        Integer count = menu.stream().map(dish -> 1)
                .reduce(0, (a, b) -> a + b);

        //then
        long cnt = menu.stream().count();

        Assertions.assertThat(count).isEqualTo(cnt);
        System.out.println(count);
        System.out.println(cnt);

    }

    /*
     * 5.6 실전연습
     */
    Trader raoul = new Trader("Raoul", "Cambridge");
    Trader mario = new Trader("Mario", "Milan");
    Trader alan = new Trader("Alan", "Cambridge");
    Trader brian = new Trader("Brian", "Cambridge");

    List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
    );


    /**
     * 실전문제 1
     * problem : 2011년에 일어난 모든 트랜잭션을 찾아 값을 오름차 순으로 정리하시오.
     * logic : stream - filter - sorted - collect
     * expected result : List<Transactions>
     * validation : 1. 2011년도의 Transaction인지 확인
     * 2. 오름차순인지 확인.
     */
    @DisplayName("실전문제 1")
    @Test
    public void problemOne() {
        //when
        List<Transaction> result = transactions.stream().filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());
        //then
        result.stream().forEach(transaction -> Assertions.assertThat(transaction.getYear()).isEqualTo(2011));

        int compare = 0;
        for (int i = 0; i < result.size(); i++) {
            Assertions.assertThat(result.get(i).getValue()).isGreaterThan(compare);
            compare = result.get(i).getValue();
        }
    }

    /**
     * 실전문제 2
     * problem : 거래자가 근무하는 모든 도시를 중복없이 나열하시오.
     * logic : stream - map - distinct - collect
     * expected result : List<String>
     * validation : 도시가 2개 이므로 카운트로 검증
     */
    @DisplayName("실전문제2")
    @Test
    public void problemTwo() {

        //when
        List<String> result = transactions.stream().map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());
        //then
        for (String city : result) {
            System.out.println(city);
        }
        Assertions.assertThat(result.stream().count()).isEqualTo(2);
    }

    /**
     * 실전문제 3
     * problem : Cambridge에서 근무하는 모든 거래자를 찾아서 이름 순으로 정렬하시오.
     * logic : stream - filter - map - distinct - sorted - collect
     * expected result : List<String>
     * validation : Cambridge에서 근무하는 사람은 총 3명이기 때문에 카운트로 검증.
     */
    @DisplayName("실전문제 3")
    @Test
    public void problemThree() {
        //when
        List<Trader> result = transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity() == "Cambridge")
                .map(transaction -> transaction.getTrader())
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());
        //then
        result.stream().forEach(name -> System.out.println(name.getName()));
        Assertions.assertThat(result.stream().count()).isEqualTo(3);
    }

    /**
     * 실전문제 4
     * problem : 모든 거래자의 이름을 알파벳순으로 정렬해서 반환하시오.
     * logic : stream - map - distinct - sorted - reduce
     * expected result : String
     * validation : print로 검증
     */
    @DisplayName("실전문제 4")
    @Test
    public void problemFour() {
        //when
        String sortedAlpabet = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (s1, s2) -> s1 + s2);
        //then
        System.out.println(sortedAlpabet);
//        Assertions.assertThat(result.stream().count()).isEqualTo(4);
    }

    /**
     * 실전문제 5
     * problem : 밀라노에 거래자가 있는가?
     * logic : stream - anyMatch
     * expected result : boolean => true
     * validation : true인지 validation
     */
    @DisplayName("실전문제 5")
    @Test
    public void problemFive() {
        //when
        boolean traderInMilan = transactions.stream()
                .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));

        //then
        Assertions.assertThat(traderInMilan).isEqualTo(true);
    }

    /**
     * 실전문제 6
     * problem : Cambridge에 거주하는 거래자의 모든 트랜잭션 값을 출력하시오.
     * logic : stream - filter - foreach
     * expected result : 트랜잭션 값 출력
     * validation : 출력이므로 검증 x
     */
    @DisplayName("실전문제 6")
    @Test
    public void problemSix() {
        //when
        transactions.stream().filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(transaction -> transaction.getValue())
                .forEach(value -> System.out.println(value));
    }

    /**
     * 실전문제 7
     * problem : 전체 트랜잭션 중 최댓값은 얼마인가?
     * logic : stream - map - reduce  // stream - max - map
     * expected result : 최댓값
     * validation : 최대값인지 검증
     */
    @DisplayName("실전문제 7")
    @Test
    public void problemSeven() {
        //when
        Integer maxValue = transactions.stream()
                .map(transaction -> transaction.getValue())
//                .reduce((x,y) -> x>y ? x:y);
                .reduce(Math::max).orElseThrow();
        //then
        Assertions.assertThat(maxValue).isEqualTo(transactions.stream()
                .max(Comparator.comparing(Transaction::getValue))
                .map(transaction -> transaction.getValue())
                .orElseThrow());
    }

    /**
     * 실전문제 8
     * problem : 전체 트랜잭션 중 최솟값은 얼마인가?
     * logic : stream - map - reduce //  stream - max - map
     * expected result : 최솟값
     * validation : 최솟값인지 검증
     */
    @DisplayName("실전문제 8")
    @Test
    public void problemEight() {
        //when
        Integer minValue = transactions.stream()
                .map(transaction -> transaction.getValue())
                .reduce(Math::min)
                .orElseThrow();
        //then
        Assertions.assertThat(minValue).isEqualTo(transactions.stream().min(Comparator.comparing(Transaction::getValue))
                .map(transaction -> transaction.getValue())
                .orElseThrow());

    }

    /**
     * 5.7 숫자형 스트림
     * problem : 메뉴의 칼로리 합계 구하기
     * logic : stream - map - reduce
     * expected result : integer value
     * validation : 합이 맞는지 확인
     */
    @DisplayName("5.7 숫자형 스트림")
    @Test
    public void numberTypeStream() {
        //when
        Integer sumCalOfStream = menu.stream()
                .map(Dish::getCalories)
                .reduce(0, Integer::sum);

        int sumCal = 0;
        for (Dish dish : menu) {
            sumCal += dish.getCalories();
        }
        //then
        Assertions.assertThat(sumCalOfStream).isEqualTo(sumCal);
    }

    /**
     * 5.7.1 기본형 특화 스트림 - 숫자 스트림으로 매핑
     * problem : 메뉴의 칼로리 합계 구하기
     * logic : stream - mapToInt - sum
     * expected result : integer value
     * validation : 합이 맞는지 확인
     */
    @DisplayName("숫자스트림으로 매핑")
    @Test
    public void useNumberTypeStream() {
        //when
        int sumOfCalStream = menu.stream()
                .mapToInt(Dish::getCalories)
                .sum();

        int sumOfCal=0;
        for (Dish dish : menu) {
            sumOfCal+=dish.getCalories();
        }

        //then
        Assertions.assertThat(sumOfCalStream).isEqualTo(sumOfCal);
    }

    /**
     * 5.7.1 기본형 특화 스트림 - 객체 스트림으로 매핑
     * problem : boxed 이용해보기
     * logic : stream - mapToInt - boxed
     * expected result : integer value
     * validation : x
     */
    @DisplayName("객체 스트림으로 매핑")
    @Test
    public void useBoxed(){
        //when
        Stream<Integer> boxed = menu.stream().mapToInt(Dish::getCalories).boxed();
        Integer sumOfCal = boxed.reduce(0, (d1, d2) -> d1 + d2);

        System.out.println(sumOfCal);
    }

    /**
     * 5.7.1 기본형 특화 스트림 - 기본값 OptionalInt
     * problem : OptionalInt 사용해보기
     * logic : stream - mapToInt - max
     * expected result : OptionalInt value
     * validation : 최댓값인지 검증
     */
    @DisplayName("기본값 OptionalInt 사용법 익히기")
    @Test
    public void useOptionalInt(){
        //when
        OptionalInt optionalInt = menu.stream().mapToInt(Dish::getCalories).max();
        int maxVal = optionalInt.orElseThrow();

        int maxValForEach =0;
        for (Dish dish:menu){
            maxValForEach = dish.getCalories() > maxValForEach ? dish.getCalories() : maxValForEach;
        }
        //then
        Assertions.assertThat(maxVal).isEqualTo(maxValForEach);
    }

    /**
     * 5.7.2 숫자 범위
     * problem : 1 부터 100사이의 짝수를 구해라
     * logic : IntStream - rangeClosed - filter - count
     * expected result : long
     * validation : 1-100까지의 짝수는 50개이므로 cnt가 50인지 비교
     */
    @DisplayName("1부터 100까지 사이에 짝수 구하기")
    @Test
    public void findEvenNumberBetween1And100(){
        //when
        //이상, 미만
        long count = IntStream.range(0, 100).filter(number -> number % 2 == 0).count();
        //이상, 이하
        long cnt = IntStream.rangeClosed(2, 100).filter(number -> number % 2 == 0).count();

        //then
        System.out.println(count);
        System.out.println(cnt);
        Assertions.assertThat(count).isEqualTo(50);
        Assertions.assertThat(cnt).isEqualTo(50);
    }

    /**
     * 5.7.3 숫자 스트림 활용 - 피타고라스 수
     * problem : 피타고라스 수 스트림을 만들자
     * logic : 피타고라스의 정리 (a*a) + (b*b) = (c*c)  ===> a (1-100) , b (1-100) == c(Math.sqrt( (a*a) + (b*b) )
     */
    @DisplayName("pythagoreanTheorem")
    @Test
    public void pythagoreanTheorem(){
        //when
        List<int[]> intResult = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a -> IntStream.rangeClosed(1, 100)
                        .filter(b -> Math.sqrt((a * a) + (b * b)) % 1 == 0)
                        .mapToObj(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)})  // int -> obj
                )
                .limit(3)
                .collect(Collectors.toList());


        //개선된 방법
        List<double[]> doubleResult = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a -> IntStream.rangeClosed(1, 100)
                        .mapToObj(b -> new double[]{a, b, Math.sqrt(a * a + b * b)})
                        .filter(b -> b[2] % 1 == 0)
                )
                .collect(Collectors.toList());


        for(int[] integer : intResult){
            Arrays.stream(integer).forEach(o -> System.out.print(o+ " ,"));
            System.out.println();
        }

        for(double[] doub : doubleResult){
            Arrays.stream(doub).forEach(o -> System.out.print(o+ " ,"));
            System.out.println();
        }

    }

    /*
     * 5.8 스트림 만들기
     * - 스트림이 데이터 질의를 표현할 수있는 강력한 도구라는 것을 확인.
     * - 실제로 컬렉션을 통해 stream을 얻을 수 있었고, 연산을 통해 결과를 도출했음.
     * - 범위의 숫자를 만들어주는 IntStream의 range와 rangeClosed등의 메서드들도 살펴 보았음.
     */

    /**
     * 5.8.1 값으로 스트림 만들기
     * problem : 임의의 수를 인수로 받는 정적 메서드 Stream.of를 이용하여 스트림을 만들자.
     * logic : Stream.of()
     */
    @DisplayName("값으로 스트림 만들기")
    @Test
    public void valueToStream(){
        //given
        Stream<String> stream = Stream.of("Modern", "Java", "In", "Action");

        //when
//        stream.map(string -> string.toLowerCase()).forEach(s-> System.out.println(s));

        //Stream.empty()를 이용해 스트림 비우기
        Stream<String> emptyStream = Stream.empty();
//        stream.map(string -> string.toLowerCase()).forEach(s-> System.out.println(s));

    }

    /**
     * 5.8.2 null이 될 수 있는 객체로 스트림 만들기
     * problem : 자바 9에서 추가된 null이 될 수 있는 개체를 스트림으로 만들 수 있는 메서드를 활용해보자
     */
    @DisplayName("null이 될 수 있는 객체로 스트림 만들기")
    @Test
    public void useStreamOfNullable(){
        //적용 전
        String home = System.getProperty("home");
        Stream<String> notEmpty = home == null ? Stream.empty() : Stream.of("notEmpty");

        //적용 후
        Stream<String> values = Stream.ofNullable(System.getProperty("home"));

        //flatMap과 함께 사용
        Stream.of("config", "home", "user")
                .flatMap(key -> Stream.ofNullable(System.getProperty(key)));

    }
    /**
     * 5.8.3 배열로 스트림 만들기
     */
    @DisplayName("배열로 스트림 만들기")
    @Test
    public void arrayToStream(){
        //given
        int[] arr = new int[] {1,2,3,4,5,6,7,8,9,10};
        //when
        int sum = Arrays.stream(arr).sum();

        //then
        Assertions.assertThat(sum).isEqualTo(55);
    }

    /**
     * 5.8.4 파일로 스트림 만들기
     */
    @DisplayName("파일로 스트림 만들기")
    @Test
    public void fileToStream(){
        //given
        long uniqueWords = 0;
        try(Stream<String> lines =
                Files.lines(Paths.get("data.txt"), Charset.defaultCharset())) {
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
                    .distinct()
                    .count();
        } catch (IOException e) {
        }

    }
    /**
     * 5.8.5 무한 스트림 만들기
     */
    @DisplayName("무한 스트림 만들기")
    @Test
    public void useUnlimitedStream(){
        Stream.iterate(0, n-> n+2)
                .limit(20)
                .forEach(n -> System.out.println(n));
    }

    /**
     * 퀴즈 5.4 피보나치수열 집합을 만들자. / iterate - 값을 받아 새로운 값을 생성. 연속적으로 계산.
     * problem : 피보나치 수열의 집합 (0,1), (1,1), (1,2) ... 을 만들어라
     * logic : Stream - iterate - limit - foreach
     */
    @DisplayName("피보나치수열 집합을 만들자.")
    @Test
    public void makeFiboSet(){
        //use iterate
        Stream.iterate(new int[] {0,1} ,arr -> 4000> arr[0], arr -> new int[] {arr[1], arr[0]+arr[1]})
                .limit(20)
                .forEach(arr -> System.out.println("( " + arr[0] + ", "+ arr[1] + " )"));

    }

    /**
     * 5.8.5 iterate 메서드 - 값을 받아 새로운 값을 생성. 연속적으로 계산.
     * problem : iterate 메서드를 사용해보자
     * logic : IntStream - iterate - takeWhile - foreach
     */
    @DisplayName("generate 메서드")
    @Test
    public void useIterate(){
        //given
        IntStream.iterate(0, n->n+4)
                .takeWhile(n -> n<100)
                .forEach(System.out::println);

        IntStream.iterate(0, n-> n+3)
                .filter(n -> n<10)
                .limit(3)
                .forEach(System.out::println);
    }

    /**
     * 5.8.5 generate 메서드 - iterate와 마찬가지로 값을 받아 새로운 값을 생성 but, 연속적으로 계산하지 않음.
     * problem : generate 메서드를 사용해보자
     * logic :  Stream - generate - limit - for
     */
    @DisplayName("generate 메서드")
    @Test
    public void useGenerate(){
        //given
        Stream.generate(() -> Math.random())
                .limit(10)
                .forEach(System.out::println);

    }
}



