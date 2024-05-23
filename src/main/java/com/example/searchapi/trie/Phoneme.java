package com.example.searchapi.trie;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public class Phoneme {
    private static final List<Character> firstSoundList = Arrays.asList('ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ' , 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'); // 19개
    private static final List<Character> midSoundList = Arrays.asList('ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ'); // 21개
    private static final List<Character> lastSoundList = Arrays.asList(null, 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'); // 28개

    private static final Integer HANGUL_BASE_CODE = 44032;
    private static final Integer FIRST_SOUND_SIZE = firstSoundList.size();
    private static final Integer MID_SOUND_SIZE = midSoundList.size();
    private static final Integer LAST_SOUND_SIZE = lastSoundList.size();
    public static final Integer LAST_SOUND_NULL_INDEX = 40;

    private Integer firstSoundIdx;
    private Integer midSoundIdx;
    private Integer lastSoundIdx;

    public static Integer getFirstSoundIdxForOnlyFirstSound(char ch){
        return Arrays.binarySearch(firstSoundList.toArray(), ch);
    }

    public static Integer getFirstSoundIdx(char ch) {
        return ((ch - HANGUL_BASE_CODE) / LAST_SOUND_SIZE) / MID_SOUND_SIZE; // 0 ~ 18
    }

    public static Integer getMidSoundIdx(char ch) {
        return ((ch - HANGUL_BASE_CODE) / LAST_SOUND_SIZE) % MID_SOUND_SIZE + FIRST_SOUND_SIZE; // 19 ~ 39
    }

    public static Integer getLastSoundIdx(char ch) {
        return (ch - HANGUL_BASE_CODE) % LAST_SOUND_SIZE + FIRST_SOUND_SIZE + MID_SOUND_SIZE; // 40 ~ 67
    }

    public static Phoneme from(Character koreanCharacter){
        Integer firstSoundIdx = Phoneme.getFirstSoundIdx(koreanCharacter);
        Integer midSoundIdx = Phoneme.getMidSoundIdx(koreanCharacter);
        Integer lastSoundIdx = Phoneme.getLastSoundIdx(koreanCharacter);

        return new Phoneme(firstSoundIdx, midSoundIdx, lastSoundIdx);
    }
}
