package book.example.mordernjavainaction.chap06;

import java.util.List;
import java.util.stream.IntStream;

import static book.example.mordernjavainaction.chap06.TakeWhile.*;

/**
 * @author 조병상
 * @since 2022-09-26
 */

public class IsPrime {

    /**
     * 소수를 판별하는 메서드
     *
     * @param candidate 소수 판별하고 싶은 숫자 파라미터
     * @return 소수 판별 결과
     */
    public static boolean isPrime(int candidate){
        int candidateRoot = (int) Math.sqrt((double) candidate);

        boolean result = IntStream.rangeClosed(2, candidateRoot)
                .noneMatch(i -> candidate % i == 0);

        return result;
    }

    /**
     * takeWhile을 이용한 소수 판별 메서드
     *
     * @param primes
     * @param candidate
     * @return
     */
    public static boolean isPrime(List<Integer> primes, int candidate){
        int candidateRoot = (int) Math.sqrt((double) candidate);

        return primes.stream()
                .takeWhile(i-> i <= candidateRoot)
                .noneMatch(i -> candidate % i == 0);

    }

//    /**
//     * 직접 구현한 takeWhile을 이용한 소수 판별 메서드 (직접 구현했기 때문에 eager하다.)
//     * @param primes
//     * @param candidate
//     * @return
//     */
//    public static boolean isPrime(List<Integer> primes, int candidate){
//        int candidateRoot = (int) Math.sqrt((double) candidate);
//
//        return takeWhile(primes, i-> i <= candidateRoot)
//                .stream()
//                .noneMatch(i-> candidate%i==0);
//    }
}
