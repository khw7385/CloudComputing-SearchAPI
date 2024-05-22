package com.example.searchapi.trie;

import com.example.searchapi.exception.EnglishTagFoundException;

import java.util.*;

public class Trie {
    private final Node root = new Node();
    private static final Integer HANGUL_BASE_CODE = 44032;
    private static final Integer FIRST_SOUND_SIZE = 19;
    private static final Integer MID_SOUND_SIZE = 21;
    private static final Integer LAST_SOUND_SIZE = 28;
    private static final Integer LAST_SOUND_NULL_INDEX = 40;

    private void insert(String tag){
        Node node = this.root;

        for (int i = 0; i < tag.length(); i++){
            char ch = tag.charAt(i);

            Integer firstSound = this.getFirstSoundIdx(ch);
            Integer midSound = this.getMidSoundIdx(ch);
            Integer lastSound = this.getLastSoundIdx(ch);

            node = createNodeFromLetter(node, firstSound, midSound, lastSound);
        }
        node.setData(tag);
        node.setLast(true);
    }

    private Integer getFirstSoundIdx(char ch) {
        return ((ch - HANGUL_BASE_CODE) / LAST_SOUND_SIZE) / MID_SOUND_SIZE; // 0 ~ 18
    }

    private Integer getMidSoundIdx(char ch) {
        return ((ch - HANGUL_BASE_CODE) / LAST_SOUND_SIZE) % MID_SOUND_SIZE + FIRST_SOUND_SIZE; // 19 ~ 39
    }

    private Integer getLastSoundIdx(char ch) {
        return (ch - HANGUL_BASE_CODE) % LAST_SOUND_SIZE + FIRST_SOUND_SIZE + MID_SOUND_SIZE; // 40 ~ 67
    }

    private Node createNodeFromLetter(Node node, Integer firstSound, Integer midSound, Integer lastSound) {
        node = node.addChildNode(firstSound);
        node = node.addChildNode(midSound);

        if(lastSound != null){
            node = node.addChildNode(lastSound);
        }
        return node;
    }

    private boolean checkOnlyFirstSound(char ch) {
        return ch <= 'ㅎ';
    }

    private Node searchNodeFromLetter(Node node, Integer firstSound, Integer midSound, Integer lastSound) {
        if(node.findNode(firstSound)){
            node = node.getNextNode(firstSound);

            if(node.findNode(midSound)){
                node = node.getNextNode(midSound);

                if(!lastSound.equals(LAST_SOUND_NULL_INDEX)){
                    if(node.findNode(lastSound)){
                        node = node.getNextNode(lastSound);
                        return node;
                    }
                    return null;
                }
                return node;
            }
            return null;
        }
        return null;
    }

    private Node navigateToNode(Node node ,String searchText) {
        for (int i = 0; i < searchText.length(); i++) {
            char ch = searchText.charAt(i);

            if(checkOnlyFirstSound(ch)){
                Integer idx = Phoneme.getFirstSoundIdx(ch);
                node = node.getNextNode(idx);
            }else{
                Integer firstSound = this.getFirstSoundIdx(ch);
                Integer midSound = this.getMidSoundIdx(ch);
                Integer lastSound = this.getLastSoundIdx(ch);
                node = searchNodeFromLetter(node, firstSound, midSound, lastSound);
            }
            if(node == null) return null;
        }
        return node;
    }

    private void bfs(Node node, List<String> tagList) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);

        while(!queue.isEmpty()){
            node = queue.remove();
            List<Node> nextNodes = node.getChildNodes();

            for (Node nextNode : nextNodes){
                if(nextNode.isLast()){
                    tagList.add(nextNode.getData());
                }
                queue.add(nextNode);
            }
        }
    }

    public void insertAll(List<String> tagList){
        tagList.forEach(tag-> {
            if (tag.matches(".*[a-zA-Z].*")){
                throw new EnglishTagFoundException("태그 안에 영어가 존재합니다.");
            }
        });
        tagList.forEach(this::insert);
    }

    public List<String> searchTags(String searchText){
        List<String> tagList = new ArrayList<>();
        Node node = root;
        node = navigateToNode(node ,searchText);

        if(node == null) return tagList;

        bfs(node, tagList);

        return tagList;
    }
}
