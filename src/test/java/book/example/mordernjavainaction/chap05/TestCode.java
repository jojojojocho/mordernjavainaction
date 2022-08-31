package book.example.mordernjavainaction.chap05;

import book.example.mordernjavainaction.chap04.Dish;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestCode {

    List<Dish> menu = new Dish().makeDishes();
    List<Dish> specialMenu = Arrays.asList(
            new Dish("seasonal fruit" , true , 120, Dish.Type.OTHER),
            new Dish("prawns" , false, 300, Dish.Type.FISH),
            new Dish("rice" , true, 350, Dish.Type.OTHER),
            new Dish("chicken" , false, 400, Dish.Type.MEAT),
            new Dish("french fries" , true, 530, Dish.Type.OTHER)
    );

    /**
     * 데이터 컬렉션 반복을 명시적으로 관리하는 외부 반복코드와 내부 반복 코드 비교
     * 요구사항 : 외부 반복의 vegetarianDishes와 내부반복의 vegetarianDishes의 데이터가 같은지 비교.
     */
    @DisplayName("외부 반복 코드와 내부 반복 코드 비교")
    @Test
    public void useExternalForeach(){

        //given
        List<Dish> vegetarianDishesUseForeach = new ArrayList<>(); // 외부 반복
        List<Dish> vegetarianDishesUseStream ; // 내부 반복

        //when
        // 채식이면 vegetarianDishesUseForeach 리스트에 추가 (외부 반복 코드)
        for (Dish dish : menu) {
            if(dish.isVegetarian()){
                vegetarianDishesUseForeach.add(dish);
            }
        }

        // 채식이면 vegetarianDishesUseStream 리스트에 추가 (내부 반복 코드)
        vegetarianDishesUseStream = menu.stream()
                .filter(Dish::isVegetarian)
                .collect(Collectors.toList());

        //then
        //외부 반복과 내부반복 각 인덱스의 요리 이름 비교
        for (int i =0; i<vegetarianDishesUseForeach.size(); i++){
            Assertions.assertThat(vegetarianDishesUseForeach.get(i)).isEqualTo(vegetarianDishesUseStream.get(i));
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
    public void usePredicateFilteringMethod(){
        //when
        //채식인 것들만 필터링 해서 리스트로 collect
        List<Dish> vegitarianMenu = menu.stream().filter(Dish::isVegetarian)
                .collect(Collectors.toList());

        //then
        //vegitarianMenu의 각 요소들이 채식인지 검증
        vegitarianMenu.stream().forEach(dish -> Assertions.assertThat(dish.isVegetarian()).isEqualTo(true));
    }


    /**
     * 요구사항(문제) : 리스트 안에 있는 짝수를 구하고, 중복된 값을 제거 해라.
     * 해결방법 : stream - filter - distinct - collect
     * 예상 되는 결과 값과 타입 : 각 요소들이 unique한 짝수로 이루어진 리스트
     * 검증 : 최초 리스트의 짝수의 count와 결과 리스트의 짝수 카운트를 비교.
     */
    @DisplayName("고유 요소 필터링")
    @Test
    public void useUniqueElementFilteringMethod(){
        //given
        List<Integer> numbers = Arrays.asList(1,2,1,3,3,2,4);
        
        //when
        //짝수일 경우에만 필터링 후 중복제거하여 리스트로 만듬.
        List<Integer> uniqueEvenNumberList = numbers.stream()
                .filter(number -> number % 2 == 0)
                .distinct()
                .collect(Collectors.toList());

        //then
        //중복제거 완료 후에는 2,4가 있을 것이므로 리스트 사이즈가 2 일 것이다.
        Assertions.assertThat(uniqueEvenNumberList.size()).isEqualTo(2);
    }

    /**
     * 5.2.1 : 프레디 케이트를 이용한 슬라이싱
     * 요구사항(문제) : dish 객체로 이루어진 정렬된 리스트 안의 요소 중 320칼로리 이하의 요리만 필터링 해라.
     * 해결 방법 : stream - takeWhile - collect
     * 예상되는 결과값 : 320칼로리 이하의 dish로 이루어진 리스트.
     * 검증 : 결과 리스트의 각 요소 별로 칼로리가 320이하 인지 개별 확인.
     */
    @DisplayName("takeWhile을 사용 한 슬라이싱")
    @Test
    public void useTakeWhileSlicingMeThod(){

        //when
        //filter를 이용한 필터링 코드
        List<Dish> filteredMenu = specialMenu.stream()
                .filter(dish -> dish.getCalories() < 320)
                .collect(Collectors.toList());

        //takeWhile을 이용한 슬라이싱 코드
        List<Dish> slicedMenu = specialMenu.stream()
                .takeWhile(dish -> dish.getCalories()<320)
                .collect(Collectors.toList());

        //then
        // 각 요소가 320이하 칼로리인지 검증.
        slicedMenu.stream().forEach(d -> Assertions.assertThat(d.getCalories() < 320));
    }

    /**
     * 5.2.1 : 프레디 케이트를 이용한 슬라이싱
     * 요구사항(문제) : dish 객체로 이루어진 정렬된 리스트 안의 요소 중 320칼로리 이상의 요리만 필터링 해라.
     * 해결 방법 : stream - dropWhile - collect
     * 예상되는 결과값 : 320칼로리 이하의 dish로 이루어진 리스트.
     * 검증 : 결과 리스트의 각 요소 별로 칼로리가 320이상 인지 개별 확인.
     */
    @DisplayName("dropWhile을 사용 한 슬라이싱")
    @Test
    public void useDropWhileSlicingMeThod(){

        //when
        //filter를 이용한 필터링 코드
        List<Dish> filteredMenu = specialMenu.stream()
                .filter(dish -> dish.getCalories() < 320)
                .collect(Collectors.toList());

        //dropWhile을 이용한 슬라이싱 코드
        List<Dish> slicedMenu = specialMenu.stream()
                .dropWhile(dish -> dish.getCalories()<320)
                .collect(Collectors.toList());

        //then
        // 각 요소가 320이상 칼로리인지 검증.
        slicedMenu.stream().forEach(d -> Assertions.assertThat(d.getCalories() > 320));
    }


    /**
     * 5.2.2 스트림 축소
     * 요구사항(문제) : 정렬된 dish 객체 List에서 300칼로리 이상의 요리들 중 요소 n개를 반환
     * 해결 방법 : stream - filter - limit - collect
     * 에상되는 결과 값 : 300칼로리 이상의 요리로 이루어진 size가 N 개인 리스트
     * 검증 : 1. 리스트 각 요소별로 300칼로리 이상인지 확인,
     *       2. 사이즈가 N개 인지 확인
     */
    @DisplayName("스트림 축소")
    @Test
    public void useLimit(){

        int N = 3; // 조건식에 부합 하는 것을 몇 개 반환할 것인가?

        //when
        //칼로리가 300초과 인 요리들을 N개 가져와서 리스트로 반환
        List<Dish> dishes = specialMenu.stream()
                .filter(dish-> dish.getCalories() > 300)
                .limit(N)
                .collect(Collectors.toList());

        //then
        //리스트의 각 요소들이 300칼로리 이상인지 확인
        dishes.stream().forEach(dish -> Assertions.assertThat(dish.getCalories() > 300));
        //리스트의 사이즈가 N인지 확인.
        Assertions.assertThat(dishes.stream().count()).isEqualTo(N);
    }

}
