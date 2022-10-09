package book.example.mordernjavainaction.chap07;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ForkJoinFramework_7_2 {

    /**
     * 7.2.1 RecursiveTask 활용
     *
     * ForkJoinSumCalculator 실행
     * 1. ForkJoinCalculator를 new ForkJoinFool().invoke(task)로 전달 한다. (task = numbers가 입력된 ForkJoinCalculator 객체)
     * 2. ForkJoinCalculator의 compute 메서드를 실행.
     * 3. compute 메서드는 병렬로 실행할 수 있을 만큼의 태스크 크기인지 확인한다.
     * 4. 크기가 크다면 2개로 분할해서 다시 ForJoinSumCalculator에 할당시킨다.
     * 5. 그러면 ForkJoinPool이 새로운 작업자 스레드에 새로 생성된 ForJoinSumCalculator를 할당.
     * 6. 이렇게 계속해서 재귀 방식으로 진행되어 leftResult와 rightResult를 더해주게 되면 최종 결과가 도출됨.
     */
    @DisplayName("ForkJoinSumCalculator 사용해보기")
    @Test
    public void usingForkJoinSumCalculator() {
        // given
        long N = 10_000_000L;

        // when
        long result = ForkJoinSumCalculator.forkJoinSum(N);

        // then
//        System.out.println(result);
        Assertions.assertThat(result).isEqualTo(50000005000000L);
        /*   벤치마크 결과
         *   Benchmark                                                    Mode  Cnt   Score   Error  Units
         *   parallelStreamBenchmark.ParallelStreamBenchmark.forkJoinSum  avgt    2  17.472          ms/op
         */
    }

    /*
     * 포크/조인 프레임워크를 제대로 사용하는 방법
     * 1. join 메서드를 태스크에 호출하면 태스크가 생산하는 결과가 준비될 때까지 호출자를 블록시킨다.
     *   따라서 두 서브태스크가 모두 시작된 다음에 join을 호출해야한다. (즉, fork와 compute가 다 실행되고 join()을 호출해야 함.)
     *   그렇지 않으면 각각의 서브태스크가 다른태스크가 끝나기를 기다리는 상황이 발생. => 순차알고리즘보다 느려질 수 있다.
     *
     * 2. RecursiveTask 내에서는 ForkJoinPool의 invoke 메서드를 사용하면 안된다. (compute와 fork로 해결할 것.)
     *   => 순차코드에서 병렬계산을 실행할 경우에만 invoke를 사용
     *
     * 3. 서브태스크에 fork 메소드를 호출해서 ForkJoinPool의 일정을 조절할 수 있다.
     *   왼쪽작업과 오른쪽 작업 모두 fork를 쓰는것보다 한쪽은 fork, 한쪽은 compute를 쓰는게 더 효율적이다.
     *   이유는 compute에서 사용하는 스레드는 재사용이 가능하기 때문에 불필요한 작업자 스레드에 대한 비용을 줄일 수 있다.
     *
     * 4. 포크/조인 프레임워크를 이용하는 병렬계산은 디버깅하기 어렵다. fork라 불리는 스레드에서도 compute를 호출하기 때문에..
     *
     * 5. 멀티코어에 포크/조인 프레임워크를 사용하는 것이 순차처리보다 무조건 빠르지는 않다.
     *   - 병렬 처리로 성능을 개선하려면 ?
     *     태스크를 독립적인 서브태스크로 분할 해도 결과에 영향을 끼지지 않아야 한다.
     *     각 서브태스크의 실행시간은 새로운 태스크를 포킹하는데 드는 시간보다 길어야 한다.
     *     최적화를 위해서는 벤치마킹을 통해 성능측정을 해야한다.
     *     컴파일러의 최적화는 순차버전에 집중되있을 수도 있다.(순차버전에서는 사용하지 않는 코드(죽은코드)는 계산하지 않고 알아서 넘어감.)
     */

    /*
     * 7.2.3 작업 훔치기
     * 작업 훔치기 기법을 통해 ForkJoinPool에서는 모든 스레드를 공정하게 분배한다.
     * 각각의 스레드는 자신에게 할당된 태스크를 포함하는 이중 연결리스트를 참조하면서 작업이 끝날 때 마다 큐의 헤드에서 다른 태스크를 가져와서 작업을 처리.
     * 풀에 있는 작업자 스레드의 태스크를 재분배하고 균형을 맞출 때 작업 훔치기가 실행된다.
     */

}
