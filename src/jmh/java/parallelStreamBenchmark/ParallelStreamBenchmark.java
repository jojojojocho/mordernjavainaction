package parallelStreamBenchmark;


import book.example.mordernjavainaction.chap07.ForkJoinSumCalculator;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import sample.Sample;

import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 2, jvmArgs = {"-Xms4G" , "-Xmx4G"})

public class ParallelStreamBenchmark {

    private static final long N = 10_000_000L;

//    @TearDown(Level.Invocation)
//    public void tearDown(){
//        System.gc();
//    }


//    @Benchmark
//    public long sequential(){
//        return Stream.iterate(1L, i-> i+1).limit(N)
//                .reduce(0L,Long::sum);
//    }
//
//    @Benchmark
//    public long iterativeSum() {
//        long result = 0;
//        for(long i = 1L; i <= N; i++){
//            result +=i;
//        }
//        return result;
//    }
//
//    @Benchmark
//    public long rangedSum(){
//        return LongStream.rangeClosed(1,N)
//                .reduce(0L, Long::sum);
//    }
//
//    @Benchmark
//    public long parallelRangedSum(){
//        return LongStream.rangeClosed(1,N)
//                .parallel().reduce(0L, Long::sum);
//    }

    @Benchmark
    public long forkJoinSum(){
        long[] numbers = LongStream.rangeClosed(1,N).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        return new ForkJoinPool().invoke(task);
    }

//    public static void main(String[] args) throws IOException, RunnerException, RunnerException {
//        Options opt = new OptionsBuilder()
//                .include(ParallelStreamBenchmark.class.getSimpleName())
//                .warmupIterations(10)           // 사전 테스트 횟수
//                .measurementIterations(10)      // 실제 측정 횟수
//                .forks(2)                       //
//                .build();
//        new Runner(opt).run();                  // 벤치마킹 시작
//    }
}
