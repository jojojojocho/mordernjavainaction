package book.example.mordernjavainaction.chap05;

import book.example.mordernjavainaction.chap04.Dish;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


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
    public void useNoneMatch(){

        //when
        if(menu.stream().noneMatch(dish -> dish.getCalories() >= 1000)){
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
        if(dish.isPresent()) {
            dish.ifPresent(d -> assertThat(d.isVegetarian()).isEqualTo(true));
        }else{
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
    public void findFirstElement(){
        //given
        List<Integer> listOfNumber = Arrays.asList(1,2,3,4,5);

        //when
        Optional<Integer> firstSquareDivisibleByThree = listOfNumber
                .stream()
                .filter(x -> x / 3 == 0)
                .map(x -> x * x)
                .findFirst();

        //then
        if(firstSquareDivisibleByThree.isPresent()){
            firstSquareDivisibleByThree.ifPresent(number -> Assertions.assertThat(Math.sqrt(number) / 3 ).isEqualTo(0));
        }else{
            throw new NoSuchElementException();
        }
    }

    /**
     * 5.5 리듀싱
     */
}



