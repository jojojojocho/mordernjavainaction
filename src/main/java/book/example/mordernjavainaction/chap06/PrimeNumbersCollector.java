package book.example.mordernjavainaction.chap06;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static book.example.mordernjavainaction.chap06.IsPrime.*;
import static java.util.stream.Collector.Characteristics.*;

/**
 * 소수를 collect하는 구현체
 *
 * @author 조병상
 * @since 2022-10-03
 */

public class PrimeNumbersCollector
        implements Collector<Integer,Map<Boolean,List<Integer>>, Map<Boolean, List<Integer>>> {
    /**
     * 누적자를 만드는 함수를 반환
     * @return
     */
    @Override
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
      return ()-> new HashMap<Boolean,List<Integer>>(){{
              put(true, new ArrayList<Integer>());
              put(false, new ArrayList<Integer>());
          }
      };

    }

    /**
     * true인것은 true에 누적, false 인것은 false에 누적.
     *
     * @return
     */
    @Override
    public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
        return (Map<Boolean, List<Integer>> acc, Integer candidate) ->{
            acc.get( isPrime(acc.get(true),candidate) )
                    .add(candidate);
        };
    }

    /**
     * 병렬과정에 누적자끼리 더하는 방법
     *
     * @return
     */
    @Override
    public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
        return (Map<Boolean, List<Integer>> map1,Map<Boolean, List<Integer>> map2 ) -> {
            map1.get(true).addAll(map2.get(true));
            map1.get(false).addAll(map2.get(false));
            return map1;
        };
    }

    /**
     * 변환 과정이 필요 없으므로 항등함수 identity를 반환하도록 메서드 구현
     * @return
     */
    @Override
    public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
        return Function.identity();
    }

    /**
     * 커스텀 컬렉터는 CONCURRENT도 아니고 UNORDERED도 아니지만 IDENTITY_FINISH이므로 아래와 같이 구현.
     * UNORDERED - 스트림의 순서가 의미없는 경우.
     * CONCURRENT - 순서가 상관없는 경우 병렬 실행.
     * IDENTITY_FINISHER - 누적자와 결과타입이 같을경우 누적자를 바로 가져다 쓰게 끔 한다.- 속도향상.
    *
     * @return
     */
    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH));
    }
}
