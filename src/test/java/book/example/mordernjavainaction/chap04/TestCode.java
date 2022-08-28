package book.example.mordernjavainaction.chap04;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.origin.SystemEnvironmentOrigin;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        lowCaloricDishName.stream().forEach(System.out::println);
    }

    @DisplayName("자바 8코드")
    @Test
    public void java8() {
        List<String> lowCaloricDishesName = menu.stream()
                .filter(dish -> dish.getCalories() < 400) //칼로리가 400이하인 것들로만 필터링(400칼로리 이하의 요리 선택)
                .sorted(comparing(Dish::getCalories))  //칼로리를 기준으로 오름차순 정렬 (칼로리로 요리 정렬)
                .map(dish -> dish.getName()) // dish -> dish.getName (요리명 추출)
                .collect(toList()); // 리스트로 collect (모든 요리명을 리스트에 저장)
        lowCaloricDishesName.stream().forEach(System.out::println);
    }

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
        List<String> dishNames = new ArrayList<>(); // 요리의 이름들을 담을 리스트
        for (Dish dish : menu) {
            dishNames.add(dish.getName());      // 요리의 이름들을 dishNames 리스트에 담음.
        }
        dishNames.stream().forEach(System.out::println);    //dishNames 순회하면서 이름을 출력
    }

    @DisplayName("Iterator 객체를 사용하는 외부 반복")
    @Test
    public void useIterator() {
        List<String> dishNames = new ArrayList<>(); //For-each를 사용하는 외부 반복과 동일
        Iterator<Dish> iterator = menu.iterator();  //menu로 부터 iterator를 가져옴.

        while (iterator.hasNext()) {
            Dish dish = iterator.next();
            dishNames.add(dish.getName());          //iterator를 이용해 dish의 이름을 dishNames 리스트에 담음.
        }
        dishNames.stream().forEach(System.out::println);//dish의 이름을 출력 (For-each를 통한 외부반복과 결과가 같음.)
    }

    @DisplayName("스트림을 이용한 내부 반복")
    @Test
    public void useStream() {
        //stream을 이용한 내부반복 -> 선언형이라 가독성이 높다.
        List<String> dishNames = menu.stream()      //menu 스트림 생성.
                .map(Dish::getName)         //dish 객체를 dish.getname의 리턴값으로 매핑해준다.
                .collect(toList());         //리스트로 collect
        dishNames.stream()
                .forEach(System.out::println);// name를 순회하면서 각 요소의 이름을 출력
    }

    @DisplayName("외부반복을 내부반복으로 변환(외부반복코드)")
    @Test
    public void externalIterator(){
        List<String> highCaloriDishes = new ArrayList<>(); //조건에 해당하는 String을 담을 리스트변수
        Iterator<Dish> iterator = menu.iterator();  //menu로 부터 iterator를 가져온다
        while (iterator.hasNext()){
            Dish dish = iterator.next();
            if(dish.getCalories() > 300){
                highCaloriDishes.add(dish.getName());       //칼로리가 300보다 큰 요리들을 highCaloriDishes에 추가
            }
        }
        highCaloriDishes.stream().forEach(System.out::println); // highCaloriDishes 리스트에 있는 요리의 이름들을 출력
    }

    @DisplayName("외부반복을 내부반복으로 변환(내부반복코드)")
    @Test
    public void  internalIterator(){
        List<String> highCaloriDishes = menu.stream()   //요리의 스트림을 가져온다
                .filter(dish -> dish.getCalories() > 300)   // 요리의 스트림에서 요리의 칼로리가 300이 넘는 요리들만 필터링
                .map(dish -> dish.getName())        //필터링 된 요리객체를 요리 이름으로 매핑
                .collect(toList());         //리스트로 collect

        highCaloriDishes.stream().forEach(System.out::println);  //highCaloriDishes 를 출력
    }

    @DisplayName("스트림 연산")
    @Test
    public void streamOperation(){
        List<String> names = menu.stream()
                .filter(dish -> dish.getCalories() > 300)   // 중간연산 (Intermediate Operation)
                .map(Dish::getName)             // 중간연산 (Intermediate Operation)
                .limit(3)               // 중간연산 (Intermediate Operation)
                .collect(toList());             // 최종연산 (Termimal Operation)
    }
    // 중간연산은 다른 스트림을 반환 -> 따라서 중간연산을 이용해 질의를 작성 가능하다.
    // 중간연산의 중요한 특징은 최종 단말연산을 스트림파이프라인에 실행하기 전까지는 아무연산도 수행하지 않는다. -> 즉 lazy Operation 이다.

    @DisplayName("스트림 중간연산 확인해보기")
    @Test
    public void intermediateOperation(){
        List<String> names = menu.stream()
                .filter(dish -> {
                    System.out.println("filtering : " + dish.getName()); //스트림은 최종연산에서 모든 연산이 몰아서 처리되기 때문에 이때 출력 x
                    return dish.getCalories() > 300;
                })
                .map(dish -> {
                    System.out.println("mapping : " + dish.getName());  //스트림은 최종연산에서 모든 연산이 몰아서 처리되기 때문에 이때 출력 x
                    return dish.getName();
                })
                .limit(3)
                .collect(toList());                                 //최종연산인 이 때 모든 연산이 처리 되면서 출력문도 같이 출력된다.
        System.out.println(names);
    }

    @DisplayName("스트림 중간연산과 최종연산 이해도 확인 (중간연산과 최종연산 구분해보기)")
    @Test
    public void intermediateAndTerminalOperation(){
        long overThreeHundredsCal = menu.stream()
                .filter(dish -> dish.getCalories() > 300)   //중간연산
                .distinct()                                 //중간연산
                .limit(3)                            //중간연산
                .count();                                   //최종연산
        System.out.println(overThreeHundredsCal);           //칼로리 300 넘는 요리의 숫자 출력
    }
}






