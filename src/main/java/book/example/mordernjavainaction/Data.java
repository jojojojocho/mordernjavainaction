package book.example.mordernjavainaction;

import book.example.mordernjavainaction.chap04.Dish;
import book.example.mordernjavainaction.chap05.Trader;
import book.example.mordernjavainaction.chap05.Transaction;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * data class
 *
 * @author 조병상
 * @since 2022-10-19
 */
public class Data {

    public static List<Dish> menu() {
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

    public static List<Dish> specialMenu() {
        return Arrays.asList(
            new Dish("seasonal fruit", true, 120, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER)
        );
    }

    public static List<Transaction> transactions() {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        return Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
        );
    }

    public static Map<String, Integer> tottenhamHotspur(){
        Map<String, Integer> totMap =
            Map.ofEntries(
                Map.entry("Son", 30),
                Map.entry("Kane", 29),
                Map.entry("Hugo", 35),
                Map.entry("Dier", 28),
                Map.entry("Lucas", 30),
                Map.entry("Kulusevski", 22)
            );
        return totMap;
    }

}
