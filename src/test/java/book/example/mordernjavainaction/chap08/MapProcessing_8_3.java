package book.example.mordernjavainaction.chap08;

import static book.example.mordernjavainaction.chap08.CacheExample.calculateDigest;

import book.example.mordernjavainaction.Data;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 8.3 맵 처리
 *
 * @author 조병상
 * @since 2022-10-20
 */
public class MapProcessing_8_3 {

    Map<String, Integer> totMap = Data.tottenhamHotspur();

    /**
     * 8.3.1 forEach 메서드
     */
    @DisplayName("키와 값을 반복하는 귀찮은 작업")
    @Test
    public void iterateMapDifficult() {
        for (Entry<String, Integer> entry : totMap.entrySet()) {
            String name = entry.getKey();
            Integer age = entry.getValue();
            System.out.println(
                "The player's name is " + name + " and age is " + age + " years old");
            /*
             * 결과값
             * The player's name is Hugo and age is 35 years old
             * The player's name is Kulusevski and age is 22 years old
             * The player's name is Kane and age is 29 years old
             * The player's name is Son and age is 30 years old
             * The player's name is Dier and age is 28 years old
             * The player's name is Lucas and age is 30 years old
             *
             */
        }
    }

    /**
     * 8.3.1 forEach 메서드
     */
    @DisplayName("Map의 forEach 메서드를 이용하면 간단하게 맵을 iterate 할 수 있다.")
    @Test
    public void iterateMapEasily() {
        // forEach 메소드를 이용해 Map을 쉽게 탐색 할 수 있다.
        totMap.forEach((name, age) ->
            System.out.println(
                "The player's name is " + name + " and age is " + age + " years old"));
        /*
         * 결과값
         * The player's name is Hugo and age is 35 years old
         * The player's name is Kulusevski and age is 22 years old
         * The player's name is Kane and age is 29 years old
         * The player's name is Son and age is 30 years old
         * The player's name is Dier and age is 28 years old
         * The player's name is Lucas and age is 30 years old
         *
         */

    }

    /**
     * 8.3.2 정렬 메서드 - Entry.comparingByKey()
     */
    @DisplayName("맵의 항목을 키 기준으로 정렬 할 수 있다.")
    @Test
    public void sortOfMapComparingOfKey() {
        // when
        List<Entry<String, Integer>> sorted = totMap.entrySet()
                                                    .stream()
                                                    .sorted(Entry.comparingByKey())
                                                    .collect(Collectors.toList());

        // then
        System.out.println(sorted); // [Dier=28, Hugo=35, Kane=29, Kulusevski=22, Lucas=30, Son=30]

    }

    /**
     * 8.3.2 정렬 메서드 - Entry.comparingByValue()
     */
    @DisplayName("맵의 항목을 값 기준으로 정렬 할 수 있다.")
    @Test
    public void sortOfMapComparingByValue() {
        // when
        List<Entry<String, Integer>> sorted = totMap.entrySet()
                                                    .stream()
                                                    .sorted(Entry.comparingByValue())
                                                    .collect(Collectors.toList());

        // then
        System.out.println(sorted); // [Kulusevski=22, Dier=28, Kane=29, Son=30, Lucas=30, Hugo=35]
    }

    /**
     * 8.3.3 getOrDefault 메서드 - npe 방지
     */
    @DisplayName("키가 존재 하지 않으면 Default 값을 반환한다.")
    @Test
    public void usingGetOrDefault(){

        // when
        Integer age = totMap.getOrDefault("Kim", 0);

        // then
        Assertions.assertThat(age).isEqualTo(0);
    }

    /**
     * 8.3.4 계산 패턴
     */
//    @DisplayName("캐시 하기")
//    @Test
//    public void usingComputeIfAbsent(){
//        // given
//        Map<String, byte[]> dataToHash = new HashMap<>();
//        List<String> lines = Arrays.asList(
//            " Nel   mezzo del cammin  di nostra  vita ",
//            "mi  ritrovai in una  selva oscura",
//            " che la  dritta via era   smarrita "
//        );
//
//        // when
//        lines.forEach( line -> dataToHash.computeIfAbsent(line, calculateDigest(line)) );
//
//        // then
//

    }




}
