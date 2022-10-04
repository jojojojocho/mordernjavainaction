package book.example.mordernjavainaction.chap07;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

/**
 * 7-1 병렬 스트림
 *
 * @author 조병상
 * @since 2022-10-04
 */
public class parallelStream_7_1 {

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



}
