package book.example.mordernjavainaction.chap06;

import java.util.List;
import java.util.function.Predicate;

/**
 * 퀴즈 6-3 자바 8로 takeWhile 흉내내기
 *
 * @author 조병상
 * @since 2022-10-02
 */
public class TakeWhile {

    public static <A> List<A> takeWhile(List<A> list, Predicate<A> p){
        int i=0;
        for( A item: list){
            if(!p.test(item)){
                return list.subList(0,i);
            }
            i++;
        }
        return list;
    }
}
