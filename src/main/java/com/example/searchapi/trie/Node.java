package com.example.searchapi.trie;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Setter @Getter
public class Node {
    private String data;
    private boolean isLast;
    private final Map<Integer, Node> childNodeMap = new HashMap<>();

    public boolean findNode(int key){
        return childNodeMap.containsKey(key);
    }
    public Node getNextNode(int key){
        return childNodeMap.get(key);
    }
    public Node addChildNode(int key){
        if(!findNode(key)){
            Node newNode = new Node();
            childNodeMap.put(key, newNode);
            return newNode;
        }
        return childNodeMap.get(key);
    }

    public List<Node> getChildNodes(){
        return childNodeMap.values().stream().toList();
    }
}
