package com.example.password_cracker;

import java.util.*;
import java.io.*;

public class WordListGenerator {


    public void SentenceGenerator(List<String> sentences, List<String> words){
        //take cleanwords.txt and stores in a list        
        
        //makes combinations for 1 word
        for (String word : words) {
            sentences.add(word);
            sentences.add(word.toLowerCase());
            sentences.add(capitalize(word));
            sentences.add(capitalize(word.toLowerCase()));

            //makes combinations for 2 words
            for (String otherWord : words) {
                if (!otherWord.equals(word)) {
                    sentences.add(word + otherWord);
                    sentences.add(word.toLowerCase() + otherWord);
                    sentences.add(capitalize(word) + otherWord);
                    sentences.add(capitalize(word.toLowerCase()) + otherWord);
                    sentences.add(word + "_" + otherWord);
                    sentences.add(word.toLowerCase() + "_" + otherWord);
                    sentences.add(capitalize(word) + "_" + otherWord);
                    sentences.add(capitalize(word.toLowerCase()) + "_" + otherWord);
                }
            }

            //makes combinations for 3 words
            for (String otherWord1 : words) {
                if (!otherWord1.equals(word)) {
                    for (String otherWord2 : words) {
                        if (!otherWord2.equals(word) && !otherWord2.equals(otherWord1)) {
                            sentences.add(word + otherWord1 + otherWord2);
                            sentences.add(word.toLowerCase() + otherWord1 + otherWord2);
                            sentences.add(capitalize(word) + otherWord1 + otherWord2);
                            sentences.add(capitalize(word.toLowerCase()) + otherWord1 + otherWord2);
                            sentences.add(word + "_" + otherWord1 + "_" + otherWord2);
                            sentences.add(word.toLowerCase() + "_" + otherWord1 + "_" + otherWord2);
                            sentences.add(capitalize(word) + "_" + otherWord1 + "_" + otherWord2);
                            sentences.add(capitalize(word.toLowerCase()) + "_" + otherWord1 + "_" + otherWord2);
                        }
                    }
                }
            }
        }
    }

    //capitalizes letter
    private static String capitalize(String str) {
        if (str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    //method to replace letters with symbols
    public void SymbolReplacement(List<String> sentences){
        //list stores final outputted custom wordlist
        List<String> customWordList = new ArrayList<>();

        //loops through list
        for (String sentence: sentences){
            //generates each combonation and stores in replacements
            Set<String> replacements = (generateReplacements(sentence));

            //loops through replacements and add to customwordList 
            for (String replacement : replacements){
                customWordList.add(replacement);
            }
        }

        //write customWordList to a txt file call customWordList
        try {
            FileWriter writer = new FileWriter("com/example/password_cracker/customWordList.txt");
            for (String word : customWordList) {
                writer.write(word + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //method to replace letter with symbols
    public static Set<String> generateReplacements(String word) {

        //hashmap that stores the symbol replacements
        Map<Character, List<Character>> replacements = new HashMap<>();
        replacements.put('a', Arrays.asList('@'));
        replacements.put('b', Arrays.asList('8'));
        replacements.put('e', Arrays.asList('3'));
        replacements.put('i', Arrays.asList('!', '1'));
        replacements.put('l', Arrays.asList('1'));
        replacements.put('o', Arrays.asList('0'));
        replacements.put('s', Arrays.asList('5', 'Z', '$', 'z'));

        replacements.put('A', Arrays.asList('@'));
        replacements.put('B', Arrays.asList('8'));
        replacements.put('E', Arrays.asList('3'));
        replacements.put('I', Arrays.asList('!', '1'));
        replacements.put('L', Arrays.asList('1'));
        replacements.put('O', Arrays.asList('0'));
        replacements.put('S', Arrays.asList('5', 'Z', '$', 'z'));

        Set<String> results = new HashSet<>();
        results.add(word);

        //loops through each char of word and generates all combinations of symbols for a word
        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            if (replacements.containsKey(letter)) {
                Set<String> newResults = new HashSet<>();
                for (String result : results) {
                    for (char replacement : replacements.get(Character.toLowerCase(letter))) {
                        String newResult = result.substring(0, i) + replacement + result.substring(i + 1);
                        newResults.add(newResult);
                    }
                }
                results.addAll(newResults);
            }
        }

        return results;
    }
}
