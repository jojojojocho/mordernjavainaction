package book.example.mordernjavainaction.chap04;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter

public class Dish {

    //요리 이름
    private final String name;

    //채식인지
    private final boolean vegetarian;

    //칼로리
    private final int calories;

    //요리 타입(MEAT, FISH, OTHER)
    private final Type type;


    public boolean isVegetarian(){
        return this.vegetarian;
    }

    @Override
    public String toString(){
        return name;
    }

    public enum Type{MEAT, FISH, OTHER}
}
