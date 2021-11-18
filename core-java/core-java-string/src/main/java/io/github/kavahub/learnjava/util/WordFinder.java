package io.github.kavahub.learnjava.util;

import java.util.ArrayList;
import java.util.List;

import lombok.experimental.UtilityClass;

/**
 * 单词查找
 */
@UtilityClass
public class WordFinder {
    public List<Integer> findWord(String textString, String word) {
        int index = 0;
        List<Integer> indexes = new ArrayList<Integer>();
        String lowerCaseTextString = textString.toLowerCase();
        String lowerCaseWord = word.toLowerCase();

        while(index != -1) {
            index = lowerCaseTextString.indexOf(lowerCaseWord, index);
            if (index == -1) {
                break;
            }

            indexes.add(index);
            index++;
        }
        return indexes;
    }



    public List<Integer> findWordUpgrade(String textString, String word) {
        int index = 0;
        List<Integer> indexes = new ArrayList<Integer>();
        // StringBuilder output = new StringBuilder();
        String lowerCaseTextString = textString.toLowerCase();
        String lowerCaseWord = word.toLowerCase();
        int wordLength = 0;

        while(index != -1){
            // Slight improvement
            index = lowerCaseTextString.indexOf(lowerCaseWord, index + wordLength);  
            if (index != -1) {
                indexes.add(index);
            }
            wordLength = word.length();
        }
        return indexes;
    }   
}
