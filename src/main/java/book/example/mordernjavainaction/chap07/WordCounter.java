package book.example.mordernjavainaction.chap07;

/**
 * 문자열 스트림을 탐색하면서 단어 수를 세는 클래스
 *
 * @author 조병상
 * @since 2022-10-09
 */
public class WordCounter {

    private final int counter; // 단어 카운터
    private final boolean lastSpace; // 공백문자 여부

    public WordCounter(int counter, boolean lastSpace) {
        this.counter = counter;
        this.lastSpace = lastSpace;
    }

    /**
     * 문자하나하나 비교해가면서 카운트를 늘려나가는 메서드
     * 공백문자일 경우 lastSpace를 true로 해서 리턴하여 다음번에 공백문자가 아닐경우에 lastSpace가 true이면 count++
     *
     * @param c 문자
     * @return
     * */
    public WordCounter accumulate(Character c) {
        // 공백문자일 경우 lastSpace를 true로 해서 리턴해서 다음번에 카운터가 올라갈수 있게 한다. (다음 공백문자가 아닐경우에)
        if (Character.isWhitespace(c)) {
            return lastSpace ? this : new WordCounter(counter, true);
        // 공백문자가 아니면 lastSpace를 확인하고 true로 되어 있으면 앞에 단어가 있었던 것으로 간주하고 counter를 하나 올려서 리턴.
        } else {
            return lastSpace ? new WordCounter(counter + 1, false) : this;
        }
    }

    /**
     * 인수로 받은 wordCounter 객체 카운트에 현재 객체의 count를 더해서 리턴.
     *
     * @param wordCounter
     * @return
     */
    public WordCounter combine(WordCounter wordCounter){
        return new WordCounter(counter + wordCounter.counter, wordCounter.lastSpace);
    }

    public int getCounter(){
        return counter;
    }
}
