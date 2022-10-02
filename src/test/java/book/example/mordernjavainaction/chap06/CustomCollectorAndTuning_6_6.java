package book.example.mordernjavainaction.chap06;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

/**
 * @author 조병상
 * @since 2022-10-02
 */

public class CustomCollectorAndTuning_6_6 {


    /**
     * 예제 6-6 n 이하의자연수를 소수와 비소수로 분류하기 -
     */
    @DisplayName(" n 이하의 자연수를 소수와 비소수로 분류하기")
    @Test
    public void classifyPrimeNumber(){

        // given
        int n= 1_000_000;

        // when
        Map<Boolean, List<Integer>> result =
                IntStream.rangeClosed(2, n).boxed()
                        .collect(partitioningBy(number -> IsPrime.isPrime(number)));

        // then
//        System.out.println(result);
        /* 결과 값
         * {
         * false=[4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20],
         * true=[2, 3, 5, 7, 11, 13, 17, 19]
         * }
         */
    }

    /**
     * 6.6.1 소수로만 나누기
     */
    @DisplayName("커스텀 컬렉터를 이용한 소수판별")
    @Test
    public void partitionPrimeNumbersWithCustomCollector(){
        // given
        int n = 1_000_000;

        // when
        Map<Boolean, List<Integer>> result =
                IntStream.rangeClosed(2, n).boxed()
                        .collect(new PrimeNumbersCollector());

        // then
//        System.out.println(result);
        /*
         * {
         * false=[4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20],
         * true=[2, 3, 5, 7, 11, 13, 17, 19]
         * }
         */
    }

    /**
     * 6.6.2 컬렉터 성능 비교
     */
    @DisplayName("컬렉터 성능비교")
    @Test
    public void collectorHarness(){

        long fastest = Long.MAX_VALUE;

        for(int i=0; i<10; i++){
            long start =System.nanoTime();
            partitionPrimeNumbersWithCustomCollector(); // 커스텀 컬렉터를 이용한 소수분리 - 81 msecs
//            classifyPrimeNumber(); // 커스텀 하지 않은 상태에서 소수 분리 -  124 msecs
            long duration = (System.nanoTime() - start) / 1_000_000;
            if(duration < fastest) fastest =duration;
        }

        System.out.println("Fastest Execution done in " +fastest + " msecs");

    }

}
