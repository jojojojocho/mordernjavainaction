package book.example.mordernjavainaction.chap04;

import lombok.*;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Dish {

    //요리 이름
    private String name;

    //채식인지
    private boolean vegetarian;

    //칼로리
    private int calories;

    //요리 타입(MEAT, FISH, OTHER)
    private Type type;

    public boolean isVegetarian(){
        return this.vegetarian;
    }

    @Override
    public String toString(){
        return name;
    }

    public enum Type{MEAT, FISH, OTHER}

    /**
     * 5장에 추가된 메서드
     * 요리가 담긴 리스트 만들기
     */
    public static List<Dish> makeDish(){
        return Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH)
        );
    }

    public static enum CaloricLevel {DIET, NORMAL, FAT}
}
