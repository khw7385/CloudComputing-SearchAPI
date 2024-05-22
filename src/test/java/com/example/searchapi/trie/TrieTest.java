package com.example.searchapi.trie;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TrieTest {

    @Test
    void unicode_test(){
        Trie trie = new Trie();
        trie.insertAll(Arrays.asList("하늘", "한마음", "한가지", "홍차", "홍해인"));
    }
}
