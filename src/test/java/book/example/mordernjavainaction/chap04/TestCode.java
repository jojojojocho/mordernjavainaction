package book.example.mordernjavainaction.chap04;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;

public class TestCode {


    List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800,Dish.Type.MEAT ),
            new Dish("beef", false, 700,Dish.Type.MEAT ),
            new Dish("chicken", false, 400,Dish.Type.MEAT ),
            new Dish("french_fries", true, 530,Dish.Type.OTHER ),
            new Dish("rice", true, 350,Dish.Type.OTHER ),
            new Dish("season fruit", true, 120,Dish.Type.OTHER ),
            new Dish("pizza", true, 550,Dish.Type.OTHER ),
            new Dish("prawns", false, 300,Dish.Type.FISH ),
            new Dish("salmon", false, 450,Dish.Type.FISH )
    );

    @DisplayName("자바 7코드")
    @Test
    public void java7(){
        List<Dish> lowCaloricDishes = new ArrayList<>();
        //lowCaloricDishes에 dish객체(메뉴)를 받음
        for(Dish dish : menu){
            //칼로리가 400이하인 것들만 필터링
            if(dish.getCalories()<400) {
                lowCaloricDishes.add(dish);
            }
        }
        //lowCaloricDishes<Dish>를 칼로리 기준 오름차순으로 정렬
        Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
            @Override
            public int compare(Dish dish1, Dish dish2) {
                return Integer.compare(dish1.getCalories(), dish2.getCalories());
            }
        });
        //오름차순 정렬이 완료된 lowCaloricDishes<Dish>를 lowCaloricDishName<String>에 저장.
        List<String> lowCaloricDishName = new ArrayList<>();
        for (Dish dish : lowCaloricDishes) {
            lowCaloricDishName.add(dish.getName());
        }
        //결과 : 칼로리 오름차순으로 정렬된 lowCaloricDish가 만들어짐.
    }

    @DisplayName("자바 8코드")
    @Test
    public void java8(){
        List<String> lowCaloricDishesName = menu.stream()
                .filter(dish -> dish.getCalories() < 400) //칼로리가 400이하인 것들로만 필터링(400칼로리 이하의 요리 선택)
                .sorted(comparing(Dish::getCalories))  //칼로리를 기준으로 오름차순 정렬 (칼로리로 요리 정렬)
                .map(dish -> dish.getName()) // dish -> dish.getName (요리명 추출)
                .collect(toList()); // 리스트로 collect (모든 요리명을 리스트에 저장)
    }




}




