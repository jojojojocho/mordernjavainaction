package book.example.mordernjavainaction.chap07;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * 7-1 병렬 스트림
 *
 * @author 조병상
 * @since 2022-10-04
 */
public class ParallelStream_7_1 {

    /**
     * 1부터 n까지의 모든 숫자의 합계를 반환하는 메서드 구현
     */
    public long sumOfNumber(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .reduce(0L, Long::sum);
    }

    /**
     * 1부터 n 까지의 모든 숫자의 합계를 반환하는 메서드를 호출하여 테스트
     */
    @DisplayName("1부터 n까지의 합계를 구하는 메서드 테스트")
    @Test
    public void testOfSumMethod() {
        // given
        long n = 10;

        // when
        long result = sumOfNumber(n);

        // then
        Assertions.assertThat(result).isEqualTo(55L);
    }

    /**
     * 전통적인 자바 방식으로 1부터 n까지 합을 구하는 메서드 구현하기
     */
    public long calculateSumUsingFor(long n) {
        long result = 0;
        for (long i = 0; i <= n; i++) {
            result += i;
        }
        return result;
    }

    /**
     * 전통적인 자바방식의 1부터 n까지의 합을 구하는 메서드 테스트
     */
    @DisplayName("전통적인 자바방식의 1부터 n까지의 합을 구하는 메서드 테스트")
    @Test
    public void oldVersionIterator() {
        // given
        long n = 10;

        // when
        long result = calculateSumUsingFor(n);

        // then
        Assertions.assertThat(result).isEqualTo(55L);
    }

    /**
     * 7.1.1 순차 스트림을 병렬 스트림으로 변환하기
     */
    @DisplayName("순차스트림을 병렬 스트림으로 변환하기")
    @Test
    public void streamToParallel() {
        long n = 10L;

        Long result =
                Stream.iterate(1L, i -> i + 1)
                        .limit(n)
                        .parallel()
                        .reduce(0L, Long::sum);

        System.out.println(result); // 55
    }

    /**
     * 7.2.2 스트림 성능 측정
     */
    @DisplayName("순차스트림과 병렬스트림의 성능 측정")
    @Test
    public void checkStreamPerformance(){

        /*
         * benchmark Test 진행한 결과 값
         *
         * Benchmark                                                           Mode  Cnt           Score   Error  Units
         * sample.Sample.hello                                                thrpt    2  3868837073.346          ops/s
         * parallelStreamBenchmark.ParallelStreamBenchmark.iterativeSum        avgt    2           2.179          ms/op
         * parallelStreamBenchmark.ParallelStreamBenchmark.parallelRangedSum   avgt    2           0.653          ms/op
         * parallelStreamBenchmark.ParallelStreamBenchmark.rangedSum           avgt    2           4.354          ms/op
         * parallelStreamBenchmark.ParallelStreamBenchmark.sequential          avgt    2          50.999          ms/op
         */
    }


    /**
     * 7.1.3 병렬스트림의 올바른 사용법 - sequential
     */
    @DisplayName("공유된 누적자를 이용한 sum - sequential")
    @Test
    public void sharedAccumulatorSumSequential(){
        // given
        long N = 1000;
        Accumulator accumulator = new Accumulator();

        // when
        LongStream.rangeClosed(1, N).forEach(accumulator::add);

        // then
        System.out.println(accumulator.getTotal()); // 500500
    }

    /**
     * 7.1.3 병렬스트림의 올바른 사용법 - parallel
     */
    @DisplayName("공유된 누적자를 이용한 sum - parallel")
    @Test
    public void sharedAccumulatorSumParallel(){
        // given
        long N = 1000;
        Accumulator accumulator = new Accumulator();

        // when
        LongStream.rangeClosed(1,N).parallel().forEach(accumulator::add);

        // then
        System.out.println(accumulator.getTotal()); // 487158
    }

    /*
     * 번외
     *
     * 병렬 스트림을 효과적으로 사용하기 위한 기준 / 지침
     *
     * 1. 확신이 서지 않으면 직접 측정 하기.
     *  - 순차 스트림을 병렬 스트림으로 변경하는 것은 간단하므로, 어떤 것을 사용할지 확인이 서지 않는다면,
     * 직접 벤치마크로 성능을 측정하여 정하는 것도 좋은 방법이다.
     *
     * 2. 박싱을 주의하자.
     *  - 자동 박싱, 언박싱은 성능에 영향을 끼치므로, 기본 특화 스트림인 IntStream, LongStream, DoubleStream을 사용하자.
     *
     * 3. 순차 스트림보다 병렬 스트림에서 성능이 떨어지는 연산에 주의하자.
     *  - limit, findFirst와 같이 요소의 순서에 의존하는 연산은 병렬 스트림에서 비싼 비용을 지불해야한다.
     *  가급적 비 정렬의 스트림에 병렬 스트림을 적용하자.
     *
     * 4. 스트림에서 수행하는 전체 파이프라인의 연산비용을 고려하자.
     *  - 요소의 개수 a, 하나를 처리하는 비용 b 라고 한다면 전체 비용은 a*b일 것이다.
     *  그 중에서도 하나를 처리하는 비용이 높다고 한다면, 병렬스트림을 고려해보자.
     *
     * 5. 소량의 데이터에서는 병렬스트림이 도움이 되지 않는다.
     *  - 데이터를 빠르게 처리하는 이익보다 병렬화하는 비용이 더 비싸다.
     *
     * 6. 스트림을 구성하는 자료구조가 적절한지 확인하자.
     *  - ArrayList는 LinkedList보다 분할비용이 적다. LinkedList는 분할하려면 전체를 탐색해야함. ArrayList는 요소 탐색없이 쉽게 분할가능.
     *  range()와 같은 정적 팩토리 메서드도 쉽게 분할이 가능하다. / spliterator를 구현하여 분해과정을 쉽게 제어 가능.
     *
     * 7. 스트림의 특성과 파이프라인의 중간연산이 스트림의 특성을 어떻게 바꾸는지에 따라 분해과정의 성능이 달라진다.
     *  - ex) sized 스트림은 같은 크기의 스트림으로 쉽게 분할.
     *  - ex) filter 메서드를 사용 할 경우 스트림의 길이를 예측할 수 없기 때문에 효과적인 병렬처리가 어렵다.
     *
     * 8. 최종연산의 병합과정의 비용을 살펴보라. (Collector의 cobiner메서드.)
     *  - 병합과정이 비싸다면 성능으로 얻는 이익과 상쇄 될 수 있다.
     *
     * 스트림 소스와 분해성 비교.
     * 1) ArrayList -- 훌륭함
     * 2) LinkedList -- 나쁨
     * 3) IntStream -- 훌륭함.
     * 4) Stream.iterate - 나쁨
     * 5) HashSet -- 훌륭함.
     * 6) TreeSet -- 훌륭함.
     */






}
