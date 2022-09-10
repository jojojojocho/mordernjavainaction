package book.example.mordernjavainaction.chap05;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Trader {
    private final String name;
    private final String city;


    public String toString(){
        return "Trader : " + this.name + " in " + this.city;
    }
}
