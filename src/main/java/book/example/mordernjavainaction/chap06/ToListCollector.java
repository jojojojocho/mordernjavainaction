package book.example.mordernjavainaction.chap06;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * 커스텀 컬렉터 만들기
 *
 * @author 조병상
 * @since 2022-09-28
 *
 * @param <T>
 */

public class ToListCollector<T> implements Collector<T, List<T>, List<T>> {

    /*
     * 수집 연산의 시발점
     */
    @Override
    public Supplier<List<T>> supplier() {
        return ArrayList::new;
    }

    /*
     * 탐색한 항목을 누적하고 바로 누적자를 고친다.
     */
    @Override
    public BiConsumer<List<T>, T> accumulator() {
        return List::add;
    }

    /*
     * 각 서브콘텐츠를 하나의 콘텐츠로 합치는 combiner
     */
    @Override
    public BinaryOperator<List<T>> combiner() {
        return (list1, list2) -> { // 두 번째 콘텐츠와 합쳐서 첫 번째 누적자를 고친다. (계속해서 누적적
            list1.addAll(list2); // 변경된 첫번째 누적자를 반환한다.
            return list1;
        };
    }

    /*
     * 항등 함수
     */
    @Override
    public Function<List<T>, List<T>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(
                EnumSet.of(Characteristics.IDENTITY_FINISH, Characteristics.CONCURRENT));
    }
}
