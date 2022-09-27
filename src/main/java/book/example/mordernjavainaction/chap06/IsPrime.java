package book.example.mordernjavainaction.chap06;

import java.util.stream.IntStream;

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
}
