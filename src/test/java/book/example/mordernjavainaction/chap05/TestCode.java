package book.example.mordernjavainaction.chap05;

import book.example.mordernjavainaction.chap04.Dish;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestCode {




    /**
     * 데이터 컬렉션 반복을 명시적으로 관리하는 외부 반복코드
     */
    @DisplayName("외부 반복 코드")
    @Test
    public void useExternalForeach(){
        //given
        List<Dish> vegetarianDishes = new ArrayList<>();
        List<Dish> menu = new Dish().makeDishes();

        //when
        // 채식이면 vegetarianDishes 리스트에 추가
        for (Dish dish : menu) {
            if(dish.isVegetarian()){
                vegetarianDishes.add(dish);
            }
        }

        //then
    }
}
