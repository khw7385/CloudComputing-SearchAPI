package com.example.searchapi.trie;

import com.example.searchapi.exception.EnglishTagFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrieTest {
    @Test
    void searchTest(){
        Trie trie = new Trie();
        trie.insertAll(Arrays.asList("하늘", "한마음", "한가지", "홍차", "홍해인", "홍길동", "한자", "한지", "하늘소", "현자", "혼자"));

        List<String> list = trie.searchTags("ㅎ");
        list.forEach(System.out::println);
        System.out.println("===================");

        List<String> list2 = trie.searchTags("하");
        list2.forEach(System.out::println);
        System.out.println("===================");

        List<String> list3 = trie.searchTags("호");
        list3.forEach(System.out::println);

        System.out.println("===================");

        List<String> list4 = trie.searchTags("한");
        list4.forEach(System.out::println);

        System.out.println("===================");

        List<String> list5 = trie.searchTags("홍");
        list5.forEach(System.out::println);

        System.out.println("===================");

        List<String> list6 = trie.searchTags("ㅍ");
        list6.forEach(System.out::println);

    }

    @Test
    void throwEnglishTagFoundException(){
        Trie trie = new Trie();

        Assertions.assertThatThrownBy(() -> {
            trie.insertAll(Arrays.asList("하늘", "sky", "한자"));
        }).isInstanceOf(EnglishTagFoundException.class);

    }
}
