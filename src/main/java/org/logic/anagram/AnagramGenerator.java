package org.logic.anagram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Oleh Dashkevych
 */
public class AnagramGenerator {

    private List<String> anagrams = new ArrayList<>();

    public List<String> generate(String word) {
        if (Objects.isNull(word) || word.isEmpty()) {
            return Collections.emptyList();
        }
        makeAnagram(word.toCharArray(), 0);
        return evaluateResult();
    }

    private void makeAnagram(char[] array, int letterPosition) {
        if (letterPosition == array.length - 1) {
            addWordIfNotExists(array);
        }
        else {
            for (int j = letterPosition; j < array.length; j++) {
                //swap a[i] with a[j]
                char temp = array[letterPosition];
                array[letterPosition] = array[j];
                array[j] = temp;
                makeAnagram(array, letterPosition + 1);
                //swap back
                temp = array[letterPosition];
                array[letterPosition] = array[j];
                array[j] = temp;
            }
        }
    }

    private void addWordIfNotExists(char[] wordChars) {
        String generatedWord = String.copyValueOf(wordChars);
        if (!anagrams.contains(generatedWord)) {
            anagrams.add(generatedWord);
        }
    }

    private List<String> evaluateResult() {
        List<String> result = new ArrayList<>(anagrams);
        anagrams.clear();
        return result;
    }
}

class Main {


    public static void main(String[] args) {
        System.out.println(anagrams("kote"));
    }

    // kote -> 24
// cat -> 6
    public static List<String> anagrams(String str){
        List<Character> characters = new ArrayList<>();
        for (char aChar : str.toCharArray()) {
            characters.add(aChar);
        }
        return formAnagrams(new ArrayList<>(), "", characters);
    }

    private static List<String> formAnagrams(List<String> result, String word, List<Character> characters) {
        if(characters.size() == 1) {
            result.add(word + characters.get(0));
            return result;
        }
        for (int i = 0; i < characters.size(); i++) {
            List<Character> newCharactersList = new ArrayList<>();
            newCharactersList.addAll(characters);
            newCharactersList.remove(i);
            formAnagrams(result, word + characters.get(i), newCharactersList);
        }
        return result;
    }

}
