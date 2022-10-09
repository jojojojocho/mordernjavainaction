package book.example.mordernjavainaction.chap07;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Spliterator_7_3 {

    /**
     * 7.3.2 커스텀 Spliterator 구현하기
     */
    @DisplayName("예제 7-4")
    @Test
    public void countWordsIteratively() {
        String sentence = "It is followed by Purgatorio and Paradiso. " +
                "The Inferno describes Dante's journey through Hell, guided by the ancient Roman poet Virgil. " +
                "In the poem, Hell is depicted as nine concentric circles of torment located within the Earth; " +
                "it is the realm ... of those who have rejected spiritual values by yielding to bestial appetites " +
                "or violence, or by perverting their human intellect to fraud or malice against their fellowmen";

        int counter = 0;
        boolean lastSpace = true;

        for (char c : sentence.toCharArray()) {
            if (Character.isWhitespace(c)) {
                lastSpace = true;
            } else {
                if (lastSpace) counter++;
                lastSpace = false;
            }
        }

        System.out.println(counter); //69
        Assertions.assertThat(counter).isEqualTo(69);
    }

    /**
     * 7.3.2 커스텀 Spliterator 구현하기 - 함수형으로 단어수를 세는 메서드 재구현하기.
     * 순차 스트림의 WordCounter
     */
    @DisplayName("WordCounter를 이용한 리듀싱연산")
    @Test
    public void countWord() {
        // given
        String sentance = "It is followed by Purgatorio and Paradiso. " +
                "The Inferno describes Dante's journey through Hell, guided by the ancient Roman poet Virgil. " +
                "In the poem, Hell is depicted as nine concentric circles of torment located within the Earth; " +
                "it is the realm ... of those who have rejected spiritual values by yielding to bestial appetites " +
                "or violence, or by perverting their human intellect to fraud or malice against their fellowmen";

        Stream<Character> charStream = IntStream.range(0, sentance.length())
                .mapToObj(sentance::charAt);
        // when
        WordCounter result =
                charStream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);

        // then
        System.out.println(result.getCounter()); // 69
        Assertions.assertThat(result.getCounter()).isEqualTo(69);

    }

    /**
     * 7.3.2 커스텀 Spliterator 구현하기 - 함수형으로 단어수를 세는 메서드 재구현하기.
     */
    @DisplayName("병렬 스트림을 이용할 수 있는 클래스인 WordCounterSpliterator 사용하기")
    @Test
    public void usingParallelStreamCountWord(){
        // given
        String sentance = "It is followed by Purgatorio and Paradiso. " +
                "The Inferno describes Dante's journey through Hell, guided by the ancient Roman poet Virgil. " +
                "In the poem, Hell is depicted as nine concentric circles of torment located within the Earth; " +
                "it is the realm ... of those who have rejected spiritual values by yielding to bestial appetites " +
                "or violence, or by perverting their human intellect to fraud or malice against their fellowmen";

        // when
        Spliterator<Character> wordCounterSpliterator = new WordCounterSpliterator(sentance);
        Stream<Character> stream = StreamSupport.stream(wordCounterSpliterator, true);


        WordCounter result =
                stream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);

        // then
        System.out.println(result.getCounter());
        Assertions.assertThat(result.getCounter()).isEqualTo(69);

    }


}


