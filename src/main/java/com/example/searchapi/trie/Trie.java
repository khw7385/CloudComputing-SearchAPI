package com.example.searchapi.trie;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Trie {
    private final Node root = new Node();
    private void insert(String tag){
        Node node = this.root;
        for (int i = 0; i < tag.length(); i++){
            char ch = tag.charAt(i);

            int last_idx = (ch - 44032) % 28;
            int mid_idx = ((ch - 44032) / 28) % 21;
            int first_idx = ((ch - 44032) / 28) / 21;

            Character firstSound = Phoneme.getFirstSound(first_idx);
            Character midSound = Phoneme.getMidSound(mid_idx);
            Character lastSound = Phoneme.getLastSound(last_idx);

            node = node.findChildNode(firstSound);
            node = node.findChildNode(midSound);

            if(lastSound != null){
                node = node.findChildNode(lastSound);
            }
        }
        node.setData(tag);
        node.setLast(true);
    }
    public Trie insertAll(List<String> tagList){
        tagList.forEach(this::insert);
        return this;
    }
}
