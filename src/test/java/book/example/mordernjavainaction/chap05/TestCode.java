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

    




}
