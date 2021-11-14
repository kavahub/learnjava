package io.github.kavahub.learnjava.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RecapitalizeHelper {
    private final Set<String> STOP_WORDS = Stream.of("a", "an", "the", "and",
    "but", "for", "at", "by", "to", "or")
    .collect(Collectors.toSet());

  public String sentenceCase(List<String> words) {
      List<String> capitalized = new ArrayList<>();
      for (int i = 0; i < words.size(); i++) {
          String currentWord = words.get(i);
          if (i == 0) {
              capitalized.add(capitalizeFirst(currentWord));
          } else {
              capitalized.add(currentWord.toLowerCase());
          }
      }
      return String.join(" ", capitalized) + ".";
  }

  public String capitalizeMyTitle(List<String> words) {
      List<String> capitalized = new ArrayList<>();
      for (int i = 0; i < words.size(); i++) {
          String currentWord = words.get(i);
          if (i == 0 || !STOP_WORDS.contains(currentWord.toLowerCase())) {
              capitalized.add(capitalizeFirst(currentWord));
          } else {
              capitalized.add(currentWord.toLowerCase());
          }
      }
      return String.join(" ", capitalized);
  }

  private String capitalizeFirst(String word) {
      return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
  } 
}
