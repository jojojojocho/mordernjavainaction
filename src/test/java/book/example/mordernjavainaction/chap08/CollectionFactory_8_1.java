package book.example.mordernjavainaction.chap08;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CollectionFactory_8_1 {

    /**
     * 8.1 컬렉션 팩토리
     */
    @DisplayName("정적 팩토리를 이용한 컬렉션 만들기")
    @Test
    public void crateFriendList() {
        /*
         * Arrays.asList는 고정된 크기의 리스트를 만든다. 그러므로 요소를 갱신하는 것은 괜찮지만,
         * 요소를 추가하려면 Unsupported OperationException이 발생한다.
         */
        List<String> friends = Arrays.asList("Son", "Kane", "Eric");

//        friends.add("Lucas"); // java.lang.UnsupportedOperationException
        friends.set(2, "Lucas");
        System.out.println(friends.get(2)); // Lucas
    }

    /**
     * 8.1 컬렉션 팩토리
     */
    @DisplayName("리스트를 인수로 받는 HashSet 생성자")
    @Test
    public void crateFriendSet() {

        HashSet<String> friends = new HashSet<>(Arrays.asList("Son", "Kane", "Dier"));


    }

    /**
     * 8.1 컬렉션 팩토리
     */
    @DisplayName("스트림을 이용한 친구 리스트")
    @Test
    public void crateFriendSet() {

        HashSet<String> friends = new HashSet<>(Arrays.asList("Son", "Kane", "Dier"));
        Set<String> friendsSet = Stream.of("Son", "Kane", "Dier")
                                       .collect(Collectors.toSet());

    }

    /**
     * 8.1.1 리스트 팩토리
     */
    @DisplayName("스트림을 이용한 친구 리스트")
    @Test
    public void createListOfFriends() {

        List<String> frends = List.of("Son", "Kane", "Dier");
        Set<String> friendsSet = Stream.of("Son", "Kane", "Dier")
                                       .collect(Collectors.toSet());

    }
}
