package book.example.mordernjavainaction.chap07;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;


/**
 * RecursiveTask를 상속받아 포크/조인 프레임워크에서 사용할 태스크를 생성.
 *
 * ForkJoinSumCalculator 실행 flow
 * 1. ForkJoinCalculator를 new ForkJoinFool().invoke(task)로 전달 한다. (task = numbers가 입력된 ForkJoinCalculator 객체)
 * 2. ForkJoinCalculator의 compute 메서드를 실행.
 * 3. compute 메서드는 병렬로 실행할 수 있을 만큼의 태스크 크기인지 확인한다.
 * 4. 크기가 크다면 2개로 분할해서 다시 ForJoinSumCalculator에 할당시킨다.
 * 5. 그러면 ForkJoinPool이 새로운 작업자 스레드에 새로 생성된 ForJoinSumCalculator를 할당.
 * 6. 이렇게 계속해서 재귀 방식으로 진행되어 leftResult와 rightResult를 더해주게 되면 최종 결과가 도출됨.

 *
 * @author 조병상
 * @since 2022-10-09
 */
public class ForkJoinSumCalculator extends RecursiveTask<Long> {
    private final long[] numbers; // 이 계산기에서 더할 숫자배열
    private final int start; // 초기 위치
    private final int end; // 마지막 위치
    public static final long THRESHOLD = 10_000; // 서브태스크로 나눌지를 결정하는 기준.

    /**
     * 메인 태스크 생성시 사용할 생성자
     *
     * @param numbers
     */
    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers,0, numbers.length);
    }

    /**
     * 서브태스크 생성시 사용할 생성자
     *
     * @param numbers
     * @param start
     * @param end
     */
    public ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }


    /**
     * RecursiveTask의 추상메서드를 재정의한 메서드.
     *
     * @return
     */
    @Override
    protected Long compute() {
        int length = this.end - this.start; // 이 태스크의 배열의 길이

        if(length <= THRESHOLD) {
            return computSequentially(); // 서브태스크 분할기준과 같거나 낮을 경우 순차적으로 계산한다.
        }

        ForkJoinSumCalculator leftTask =
                new ForkJoinSumCalculator(numbers, start, start + length / 2);
        leftTask.fork(); // leftTask를 비동기로 실행

        ForkJoinSumCalculator rightTask =
                new ForkJoinSumCalculator(numbers, start + length / 2, end);
        Long rightResult = rightTask.compute(); // rightTask를 동기로 실행

        Long leftResult = leftTask.join(); // 비동기로 실행한 결과를 가져옴.
        return leftResult + rightResult;


    }

    private long computSequentially() {
        long sum = 0;
        for(int i= this.start; i < this.end; i++){
            sum += numbers[i];
        }
        return sum;
    }

    public static long forkJoinSum(long n){
        long[] numbers = LongStream.rangeClosed(1,n).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        return new ForkJoinPool().invoke(task);
    }
}
