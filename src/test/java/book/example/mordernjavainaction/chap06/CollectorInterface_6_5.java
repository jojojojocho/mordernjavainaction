package book.example.mordernjavainaction.chap06;

import book.example.mordernjavainaction.chap04.Dish;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;

import static book.example.mordernjavainaction.chap04.Dish.*;

/**
 * @author 조병상
 * @since 2022-09-27
 */
public class CollectorInterface_6_5<T> {

    List<Dish> menu = makeDish();

    /*
     * =========supplier===========
     */

    /**
     * supplier - 새로운 결과 컨테이너 만들기.
     * supplier 메서드는 빈 결과로 이루어진 supplier를 반환해야함.
     * 즉, 빈 누적자 인스턴스를 만드는 파라미터가 없는 함수
     *
     * @return 빈누적자
     */
    public Supplier<List<T>> supplier1(){
        return ()-> new ArrayList<T>();
    }

    /**
     * 생성자 참조 방식으로 전달.
     *
     * @return
     */
    public Supplier<List<T>> supplier2(){
        return ArrayList::new;
    }

    /*
     * ===============accumulator===================
     */

    /**
     * accumulator 메서드 : 결과 컨테이너에 요소 추가하기.
     */
    public BiConsumer<List<T>, T> accumulator(){
        return (list, item) -> list.add(item);
    }

    public BiConsumer<List<T>, T> accumulator(List<T> list){
        return List::add;
    }


    /*
     * ===============finisher ==============
     */
    /**
     * finisher 메서드 : 최종 변환 값을 결과 컨테이너로 적용하기
     */
    public Function<List<T>,List<T>> finisher(){
        return Function.identity();
    }

    /*
     * ==================combiner=====================
     */
    /**
     * combiner 메서드 : 두 결과 컨테이너 병합
     */
    public BinaryOperator<List<T>> combiner(){
        return (list1, list2) ->{
            list1.addAll(list2);
            return list1;
        };
    }

    /**
     * 6.5.1 Collector 인터페이스의 메서드 살펴보기 - supplier 메서드
     */
    @DisplayName("supplier 만들기")
    @Test
    public void createSupplier(){

        // when
        Supplier<List<T>> listSupplier1 = supplier1();
        Supplier<List<T>> listSupplier2 = supplier2();

        // then
        System.out.println(listSupplier1);
        System.out.println(listSupplier2);

    }

    /**
     * 6.5.1 Collector 인터페이스의 메서드 살펴보기 - accumulator 메서드
     */
    @DisplayName("accumulator 만들기")
    @Test
    public void createAccumulator(){

        // when
        BiConsumer<List<T>, T> accumulator = accumulator();

        // then
        System.out.println(accumulator);
    }

    /**
     * 6.5.1 Collector 인터페이스의 메서드 살펴보기 - finisher 메서드
     */
    @DisplayName("finisher 만들기")
    @Test
    public void createFinisher(){

        // when
        Function<List<T>, List<T>> finisher = finisher();

        // then
        System.out.println(finisher);
    }

    /**
     * 6.5.1 Collector 인터페이스의 메서드 살펴보기 - combiner 메서드
     */
    @DisplayName("combiner 만들기")
    @Test
    public void createCombiner(){

        // when
        BinaryOperator<List<T>> combiner = combiner();

        // then
        System.out.println(combiner);
    }

    /**
     * 6.5.2 응용하기 (직접 만든 컬렉터를 적용해보기) - ToListCollector
     */
    @DisplayName("직접 작성한 커스텀 컬렉터 적용해보기")
    @Test
    public void usingCustomCollector(){
        // when
        List<Dish> result = menu.stream().collect(new ToListCollector<Dish>());

        // then
        System.out.println(result); // [pork, beef, chicken, french fries, rice, season fruit, pizza, prawns, salmon]

    }

    /**
     * 6.5.2 응용하기 - collect(ArrayList::new , List::add, List::addAll)
     */
    @DisplayName("컬렉터 구현을 만들지 않고도 커스텀 수집 수행하기")
    @Test
    public void customCollectingWithOutClassFile(){
        // when
        List<Dish> result = menu.stream().collect(ArrayList::new, List::add, List::addAll);

        // then
        System.out.println(result); // [pork, beef, chicken, french fries, rice, season fruit, pizza, prawns, salmon]
    
    }

    /*
     * 6.6 커스텀 컬렉터를 구현해서 성능 개선하기
     */

    /**
     * 6.6.1 소수로만 나누기
     */
}
