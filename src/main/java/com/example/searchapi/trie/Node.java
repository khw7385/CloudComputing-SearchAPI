package com.example.searchapi.trie;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Setter @Getter
public class Node {
    private String data;
    private boolean isLast;
    private final Map<Integer, Node> childNodes = new HashMap<>();
    public Node findChildNode(int key){
        if(!childNodes.containsKey(key)){
            Node newNode = new Node();
            childNodes.put(key, newNode);
            return newNode;
        }
        return childNodes.get(key);
    }
}
