package book.example.mordernjavainaction.chap04;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;

public class TestCode {


    List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french_fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH)
    );

    @DisplayName("자바 7코드")
    @Test
    public void java7() {
        List<Dish> lowCaloricDishes = new ArrayList<>();
        //lowCaloricDishes에 dish객체(메뉴)를 받음
        for (Dish dish : menu) {
            //칼로리가 400이하인 것들만 필터링
            if (dish.getCalories() < 400) {
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

<<<<<<< HEAD


=======
    @DisplayName("스트림은 단 한번만 소비 될 수 있다!!")
    @Test
    public void repeatOnce() {
        List<String> company = Arrays.asList("Apple", "Samsung", "LG", "Asus"); //회사이름들
        Stream<String> stream = company.stream();   // Sequence Of Elements (연속된 요소) 의 스트림
        stream.forEach(System.out::println);        //첫번째 순회 -> 제대로 작동
        stream.forEach(System.out::println);        // 두번째 순회 -> 이미 한번 돌았기 때문에 정상작동하지 않음.
    }

    @DisplayName("For-each를 사용하는 외부 반복")
    @Test
    public void useForeach() {
        List<String> names = new ArrayList<>(); // 요리의 이름들을 담을 리스트
        for (Dish dish : menu) {
            names.add(dish.getName());      // 요리의 이름들을 names 리스트에 담음.
        }
        names.stream().forEach(System.out::println);    //names를 순회하면서 이름을 출력
    }

    @DisplayName("Iterator 객체를 사용하는 외부 반복")
    @Test
    public void useIterator() {
        List<String> names = new ArrayList<>();     //For-each를 사용하는 외부 반복과 동일
        Iterator<Dish> iterator = menu.iterator();  //menu로 부터 iterator를 가져옴.

        while (iterator.hasNext()) {
            Dish dish = iterator.next();
            names.add(dish.getName());          //iterator를 이용해 dish의 이름을 names리스트에 담음.
        }
        names.stream().forEach(System.out::println);        //dish의 이름을 출력 (For-each를 통한 외부반복과 결과가 같음.)
    }

    @DisplayName("스트림을 이용한 내부 반복")
    @Test
    public void useStream() {
        //stream을 이용한 내부반복 -> 선언형이라 가독성이 높다.
        List<String> names = menu.stream()      //menu 스트림 생성.
                .map(Dish::getName)         //dish 객체를 dish.getname의 리턴값으로 매핑해준다.
                .collect(toList());         //리스트로 collect
        names.stream()
                .forEach(System.out::println);// name를 순회하면서 각 요소의 이름을 출력
    }
>>>>>>> parent of ae46b0a (.)

}




