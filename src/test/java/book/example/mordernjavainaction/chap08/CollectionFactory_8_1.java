package book.example.mordernjavainaction.chap08;

import static java.util.Map.entry;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
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

        Assertions.assertThrows(UnsupportedOperationException.class,
            () -> friends.add("Kim"), "크기를 조절할 수 없기 때문에 exception 발생!!!");
    }

    /**
     * 8.1 컬렉션 팩토리
     */
    @DisplayName("리스트를 인수로 받는 HashSet 생성자 (요소 추가 가능)")
    @Test
    public void crateFriendHashSet() {

        HashSet<String> friends = new HashSet<>(Arrays.asList("Son", "Kane", "Dier"));

        friends.add("Kim");
        System.out.println(friends); // [Dier, Son, Kane, Kim]
    }

    /**
     * 8.1 컬렉션 팩토리
     */
    @DisplayName("스트림을 이용한 친구 리스트")
    @Test
    public void crateFriendSet() {

        Set<String> friendsSet = Stream.of("Son", "Kane", "Dier")
                                       .collect(Collectors.toSet());

        friendsSet.add("Kim");
        System.out.println(friendsSet); // [Dier, Son, Kane, Kim]
    }

    /**
     * 8.1.1 리스트 팩토리
     */
    @DisplayName("팩토리 메서드를 이용한 친구 리스트 만들어보기")
    @Test
    public void createListOfFriends() {

        List<String> friends = List.of("Son", "Kane", "Dier", "Hugo", "Ben",
            "Oliver", "Pierre-Emile", "Davinson", "Richarlison", "Bryan",
            "Emerson", "Ivan", "Cristian", "Fraser", "Dejan", " Djed", "Japhet", "Lucas");
        Set<String> friendsSet = Stream.of("Son", "Kane", "Dier")
                                       .collect(Collectors.toSet());

        /*
         * of 팩토리 메서드는 고정된 크기의 리스트가 할당 되기 때문에
         * 요소를 추가하려고 하면 UnsupportedOperationException가 발생한다.
         */
        Assertions.assertThrows(UnsupportedOperationException.class,
            () -> friends.add("Kim"), "크기를 조절할 수 없기 때문에 exception 발생!!!");
    }

    /**
     * 8.1.1 리스트 팩토리
     */
    @DisplayName("팩토리 메서드를 이용한 친구 List 만들어보기(요소 추가가능)")
    @Test
    public void changeableOfList() {

        List<String> friendsList = Stream.of("Son", "Kane", "Dier")
                                         .collect(Collectors.toList());

        friendsList.add("Kim");
        System.out.println(friendsList); // [Son, Kane, Dier, Kim]
    }


    /**
     * 8.1.1 리스트 팩토리
     */
    @DisplayName("팩토리 메서드를 이용한 친구 set 만들어보기(요소 추가가능)")
    @Test
    public void changeableOfSet() {

        Set<String> friendsSet = Stream.of("Son", "Kane", "Dier")
                                       .collect(Collectors.toSet());

        friendsSet.add("Kim");
        System.out.println(friendsSet); // [Dier, Son, Kane, Kim]
    }

    /**
     * 8.1.2 집합 팩토리
     */
    @DisplayName("팩토리 메서드를 이용한 친구 리스트 만들어보기(Set)")
    @Test
    public void createSetOfFriend() {
        Set<String> friendsSet = Set.of("Son", "Kane", "Dier", "Hugo", "Ben");

        Assertions.assertThrows(UnsupportedOperationException.class,
            () -> friendsSet.add("Kim"), "크기를 조절할 수 없기 때문에 exception 발생!!!");

    }

    /**
     * 8.1.3 맵 팩토리
     */
    @DisplayName("팩토리 메서드를 이용한 친구리스트 만들기 (Map)")
    @Test
    public void createMapOfFriend() {
        // when
        Map<String, Integer> friendMapUsingOf =
            Map.of("Son", 30, "Kane", 29, "Hugo", 36);

        Map<String, Integer> friendMapUsingOfEntries = Map.ofEntries(entry("Son", 30),
            entry("Kane", 29),
            entry("Hugo", 36));

        // then
        System.out.println(friendMapUsingOf); // {Hugo=36, Son=30, Kane=29}
        System.out.println(friendMapUsingOfEntries); // {Hugo=36, Son=30, Kane=29}
    }

    /**
     * 퀴즈 8-1 다음 코드를 실행한 결과는?
     */
    @DisplayName("코드를 실행한 결과는?")
    @Test
    public void resultOfRunCode(){
        // given
        List<String> actors = List.of("TOM", "SCARLETT");

        // then
//        actors.set(0,"Leonardo");
//        System.out.println(actors);
        Assertions.assertThrows(UnsupportedOperationException.class,
            () -> actors.set(0,"Leonardo"), "immutable 객체를 수정하려고 했기 때문에 오류발생");

    }


}
