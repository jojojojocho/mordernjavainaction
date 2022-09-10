package book.example.mordernjavainaction.chap05;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Transaction {
    private final Trader trader;
    private final int year;
    private final int value;

    public String toString(){
        return "{" + this.trader + ", " +
                "year : " + this.year+ ", "+
                "value : " + this.value + "}";
    }
}
