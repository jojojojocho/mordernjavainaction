package book.example.mordernjavainaction.chap07;


import lombok.Data;

/**
 * 누적자 클래스
 *
 * @author 조병상
 * @since 2022-10-07
 */
@Data
public class Accumulator {
    public long total = 0;

    public void add(long value) {
        total += value;
    }
}
