package com.example.searchapi.trie;

import com.example.searchapi.exception.NotKoreanTagFoundException;

import java.util.*;

public class Trie {
    private final Node root = new Node();

    private void insert(String tag){
        Node node = this.root;

        for (int i = 0; i < tag.length(); i++){
            char character = tag.charAt(i);

            Phoneme phonemes = Phoneme.from(character);

            node = createNodeFromLetter(node, phonemes);
        }
        node.setData(tag);
        node.setLast(true);
    }

    private Node createNodeFromLetter(Node node, Phoneme phonemes) {

        Integer firstSoundIdx = phonemes.getFirstSoundIdx();
        Integer midSoundIdx = phonemes.getMidSoundIdx();
        Integer lastSoundIdx = phonemes.getLastSoundIdx();

        node = node.addChildNode(firstSoundIdx);

        node = node.addChildNode(midSoundIdx);


        if(lastSoundIdx != null){
            node = node.addChildNode(lastSoundIdx);
        }
        return node;
    }

    private boolean checkOnlyFirstSound(char ch) {
        return ch <= 'ㅎ';
    }

    private Node searchNodeFromLetter(Node node, Phoneme phoneme) {
        Integer firstSoundIdx = phoneme.getFirstSoundIdx();
        Integer midSoundIdx = phoneme.getMidSoundIdx();
        Integer lastSoundIdx = phoneme.getLastSoundIdx();

        if(node.findNode(firstSoundIdx)){
            node = node.getNextNode(firstSoundIdx);

            if(node.findNode(midSoundIdx)){
                node = node.getNextNode(midSoundIdx);

                if(!lastSoundIdx.equals(Phoneme.LAST_SOUND_NULL_INDEX)){
                    if(node.findNode(lastSoundIdx)){
                        node = node.getNextNode(lastSoundIdx);
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
            char character = searchText.charAt(i);

            if(checkOnlyFirstSound(character)){
                Integer idx = Phoneme.getFirstSoundIdxForOnlyFirstSound(character);
                node = node.getNextNode(idx);
            }else{
                Phoneme phoneme = Phoneme.from(character);
                node = searchNodeFromLetter(node, phoneme);
            }
            if(node == null) return null;
        }
        return node;
    }

    private void searchTagsUsingBFS(Node node, List<String> tagList) {
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
    private void checkNotKoreanTag(String searchText) {
        if(searchText.matches("[가-힣ㄱ-ㅎ]+")){
            throw new NotKoreanTagFoundException();
        }
    }
    public void insertAll(List<String> tagList){
        tagList.forEach(this::checkNotKoreanTag);
        tagList.forEach(this::insert);
    }

    public List<String> searchTags(String searchText){
        checkNotKoreanTag(searchText);
        List<String> tagList = new ArrayList<>();
        Node node = root;
        node = navigateToNode(node ,searchText);

        if(node == null) return tagList;

        searchTagsUsingBFS(node, tagList);

        return tagList;
    }

}
