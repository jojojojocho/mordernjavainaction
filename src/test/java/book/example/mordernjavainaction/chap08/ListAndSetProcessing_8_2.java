package book.example.mordernjavainaction.chap08;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * <pre>
 * 8.2 리스트와 집합처리
 * 1. removeIf() - List, Set의 구현체나 상송받은 클래스에서 사용가능. 프레디케이트를 만족하는 요소를 제거
 * 2. replaceAll() - List 에서만 사용 가능. 단항 연산자 함수를 이용해 요소를 변경.
 * 3. sort() - 리스트 정렬.
 *
 * </pre>
 *
 * @author 조병상
 * @since 2022-10-19
 */
public class ListAndSetProcessing_8_2 {

    /*
     * 8.2.1 removeIf 메서드
     * "내부적으로 for-each 루프는 Iterator객체를 사용"
     */
    @DisplayName("ConcurrentModificationException을 일으키는 코드")
    @Test
    public void throwConcurrentModificationException() {

        // given
        List<Integer> integerList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            integerList.add(i);
        }

        // Exception이 발생하는 코드
//        for(Integer integerNumber : integerList){
//            if(integerNumber%2 ==0){
//                integerList.remove(integerNumber);
//                /*
//                 * java.util.ConcurrentModificationException 발생
//                 * 컬렉션(integerList)과 Iterator(iNum)가 동기화 되지 않음.
//                 * Iterator는 그대로인데, 컬렉션의 요소만 제거하려함.
//                 */
//            }
//        }

        /*
         * Exception이 발생하지 않는 코드
         */
        Iterator<Integer> iterator = integerList.iterator();
        while (iterator.hasNext()) {
            Integer integerNumber = iterator.next();
            if (integerNumber % 2 == 0) {
                iterator.remove();
            }
        }

    }

    /**
     * 8.2.1 removeIf 메서드
     */
    @DisplayName("removeIf를 이용한 리스트의 요소 제거")
    @Test
    public void removeElementOfList() {
        // given
        List<Integer> integerList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            integerList.add(i);
        }

        // when
        integerList.removeIf(integer -> {
            if(integer % 2 == 0){
                return true;
            } else {
                return false;
            }
        });

        // then
        integerList.forEach(integer -> Assertions.assertThat(integer)
                                                 .isOdd());
    }

    /**
     * 8.2.2 replaceAll 메서드 - stream 이용하기.
     */
    @DisplayName("리스트의 각 요소를 새로운 요소로 변경 - stream")
    @Test
    public void changeElementOfListUsingStream() {
        // given
        List<String> referenceCodes = Arrays.asList("a12", "C14", "b13");

        // 스트림을 사용한 리스트 요소 변경.
        List<String> result = referenceCodes.stream()
                                             .map(str -> Character.toUpperCase(str.charAt(0))
                                                 + str.substring(1))
                                             .collect(toList());
        System.out.println(result); // [A12, C14, B13]
    }

    /**
     * 8.2.2 replaceAll 메서드 - iterator 이용하기.
     */
    @DisplayName("리스트의 각 요소를 새로운 요소로 변경 - iterator ")
    @Test
    public void changeElementOfListUsingIterator() {
        // given
        List<String> referenceCodes = Arrays.asList("a12", "C14", "b13");

        // iterator를 이용한 리스트 요소 변경.
        for (ListIterator<String> iterator = referenceCodes.listIterator(); iterator.hasNext(); ) {
            String str = iterator.next();
            iterator.set(Character.toUpperCase(str.charAt(0)) + str.substring(1));
        }
        System.out.println(referenceCodes); // [A12, C14, B13]
    }

    /**
     * 8.2.2 replaceAll 메서드 - replaceAll 이용하기.
     */
    @DisplayName("리스트의 각 요소를 새로운 요소로 변경 - replaceAll")
    @Test
    public void changeElementOfListUsingReplaceAll() {
        // given
        List<String> referenceCodes = Arrays.asList("a12", "C14", "b13");

        // replaceAll을 이용한 리스트 요소 변경
        referenceCodes.replaceAll(str -> Character.toUpperCase(str.charAt(0)) + str.substring(1));
        System.out.println(referenceCodes); // [A12, C14, B13]
    }

}
