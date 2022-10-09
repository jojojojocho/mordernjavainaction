package book.example.mordernjavainaction.chap07;

import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * 단어수를 세는 것을 병렬로 실행하는 클래스
 *
 * @author 조병상
 * @since 2022-10-10
 */
public class WordCounterSpliterator implements Spliterator<Character> {

    private final String string; // 문자열
    private int currentChar = 0; // 인덱스

    public WordCounterSpliterator(String string) {
        this.string = string;
    }

    /**
     * 문자열에서 현재 인덱스에 해당하는 문자를 Consumer에게 제공한다음 인덱스를 증가시킴.
     * Consumer : 스트림을 탐색하면서 적용해야 하는 함수 집합이 작업을 처리할 수 있도록 소비한 문자를 전달하는 자바 내부 클래스.
     *
     * @param action
     * @return
     */
    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        // 현재 문자를 소비한다.
        action.accept(string.charAt(currentChar++));

        // 소비할 문자가 남아있으면 true 리턴
        return currentChar < string.length();
    }

    /**
     * 반복될 자료구조를 분할하는 로직
     *
     * @return
     */
    @Override
    public Spliterator<Character> trySplit() {
        int currentSize = string.length() - currentChar;

        // 문자열이 분할 기준값인 10 이하면 더 이상 분할되지 않음.
        if (currentSize < 10) {
            return null; // null 리턴시 분할이 중단 됨.
        }
        // 분할 로직
        for (int splitPos = currentSize / 2 + currentChar; splitPos < string.length(); splitPos++) {
            // 지금 문자가 공백이라면 새로운 spliterator를 만들어서 리턴한다.
            if (Character.isWhitespace(string.charAt(splitPos))) {
                Spliterator<Character> spliterator =
                        new WordCounterSpliterator(string.substring(currentChar, splitPos));

                currentChar = splitPos;
                return spliterator;
            }
        }
        return null;
    }

    /**
     * 탐색해야할 요소의 갯수
     *
     * @return
     */
    @Override
    public long estimateSize() {
        return string.length() - currentChar;
    }

    /**
     * ORDERED - 유의미한 순서가 있음.
     * DISTINCT - x,y 두 요소를 방문했을 경우 항상 false인 경우.(중복이 없다.)
     * SORTED - 탐색될 요소들은 미리 정렬되어 있는지 여부.
     * SIZED - 요소의 길이
     * NON-NULL - 탐색하는 모든요소는 NULL이 아님
     * IMMUTABLE - 이 Spliterator의 소스는 불변이다.
     * CONCURRENT - 동기화 없이 Spliterator의 소스를 여러스레드에서 동시에 고칠 수 있다.(thread safe하다)
     * SUBSIZED - 이 Spliterator와 분할되는 Spliterator들은 SIZE 특성을 가진다.
     *
     * @return
     */
    @Override
    public int characteristics() {
        return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    }
}
