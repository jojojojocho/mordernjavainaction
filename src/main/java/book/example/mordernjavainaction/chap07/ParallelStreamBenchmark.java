package book.example.mordernjavainaction.chap07;

//@BenchmarkMode(Mode.AverageTime) // 벤치마크 대상 메소드를 실행하는 데 걸린 평균 시간 측정
//@OutputTimeUnit(TimeUnit.MILLISECONDS) // 벤치마크 결과를 밀리초 단위로 출력
//@Fork(value = 2, jvmArgs = {"-Xms4G", "-Xmx4G"}) // 4Gb의 힙공간을 제공한 환경에서 2번 벤치마크를 수행해 결과의 신뢰성확보
//@State(Scope.Thread)
//@BenchmarkMode(Mode.AverageTime)
//@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class ParallelStreamBenchmark {

//    private static final long N = 10_000_000L;
//
//    @Benchmark
//    public long sequentialSum() {
//        return Stream.iterate(1L, i -> i + 1)
//                .limit(N)
//                .reduce(0L, Long::sum);
//    }
//
//    @TearDown(Level.Invocation)
//    public void tearDown(){
//        System.gc();
//    }
//
//    @Benchmark
//    public long iterativeSum(){
//        long result= 0;
//        for(long i = 1L; i<=N; i++ ){
//            result += i;
//        }
//        return result;
//    }
//
//    @Benchmark
//    public long rangeSum(){
//        return LongStream.rangeClosed(1,N)
//                .reduce(0L, Long::sum);
//    }
//
//    @Benchmark
//    public long parallelRangedSum(){
//        return LongStream.rangeClosed(1,N)
//                .parallel()
//                .reduce(0L, Long::sum);
//    }


//
//
//
//    final Integer LIMIT_COUNT = 10000;
//    final List<Integer> array = new ArrayList<>();
//
//    @Setup
//    public void init() {
//        // 성능 측정 전 사전에 필요한 작업
//        for(int i = 0; i < LIMIT_COUNT; i++) {
//            array.add(i);
//        }
//    }
//
//    @Benchmark
//    public void originLoopWithGetSize() {
//        // 성능을 측정할 코드 작성
//        int size = array.size();
//        for(int i = 0; i < size; i++) {
//            processor(i);
//        }
//    }
//
//    Integer temp = 0;
//    public void processor(Integer i) {
//        temp = i;
//    }
//
//    public static void main(String[] args) throws IOException, RunnerException, RunnerException {
//        Options opt = new OptionsBuilder()
//                .include(ParallelStreamBenchmark.class.getSimpleName())
//                .warmupIterations(10)           // 사전 테스트 횟수
//                .measurementIterations(10)      // 실제 측정 횟수
//                .forks(1)                       //
//                .build();
//        new Runner(opt).run();                  // 벤치마킹 시작
//    }



}
