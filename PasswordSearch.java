package com.example.password_cracker;// java.io for reading the txt files, java.util.scanner for taking user input
import javafx.scene.effect.ImageInput;

import java.io.*;
import java.util.*;

public class PasswordSearch {
    private static boolean matchFound = false;
    public static double time;
    public static int status = 0;

    public static String finalPassword;



    //Find Password method
    public void findPassword(String word) {
        //get user's password with scanner
        //TODO change scanner, to take password 

        String wordToSearch = word;
        finalPassword  = word;

        //try and catch for error exception 
        try {
            //open file and store into bufferedReader
            File file = new File("src/main/java/com/example/password_cracker/wordlist.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            //line will store each line of the txt file, attempts stores number of attempts
            String line;
            int[] attempts = new int[1];
            boolean wordFound = false;

            //loops through wordlist.txt line by line until it reaches the end or finds the password
            double start = System.currentTimeMillis();
            double end = System.currentTimeMillis();
            while ((line = bufferedReader.readLine()) != null) {
                attempts[0] += 1;
                //compares each word in wordlist to the inputted password, and if true prints the line it was found on then exits the loop
                if (line.equals(wordToSearch)) {
                    wordFound = true;
                    end = System.currentTimeMillis();
                    time = (end-start)/1000;
                    status = 1;
                    return;
                }
            }

            //if the word isn't found in the IRockYou word list then check the customWordList
            File file2 = new File("src/main/java/com/example/password_cracker/customWordList.txt");
            FileReader fileReader2 = new FileReader(file2);
            BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
            if (!wordFound) {
                while ((line = bufferedReader2.readLine()) != null) {
                    attempts[0] += 1;
                    //compares each word in wordlist to the inputted password, and if true prints the line it was found on then exits the loop
                    if (line.equals(wordToSearch)) {
                        wordFound = true;
                        end = System.currentTimeMillis();
                        time = (end-start)/1000;
                        status = 1;
                        return;
                    }
                }
            }

            //if the word isn't found in the customWordList you can attempt a true brute force(will take a long time)
            if (!wordFound) {
                if(wordToSearch.length() <= 5) {
                    findMatches(wordToSearch);
                    end = System.currentTimeMillis();
                }
                else {
                    status = 2;
                    return;
                }

            }
            if (matchFound) {
                time = (end - start) / 1000;
                status = 1;
                return;
            }
            status = 2;

            //close everything
            fileReader.close();
            bufferedReader.close();
            fileReader2.close();
            bufferedReader2.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //method for complete brute force checking every possible char
    public static void findMatches(String targetWord) {
        double start = System.currentTimeMillis();
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+-=[]{};:,.<>?"; // Characters to cycle through
        int maxLength = targetWord.length();

        generateCombinations(characters, "", maxLength); // Generate combinations

        double end = System.currentTimeMillis();


        if (!matchFound) {
            System.out.println("No match found.");
        }
    }

    //generates the combinations for each word length
    private static void generateCombinations(String characters, String currentCombination, int maxLength) {
        if(matchFound){
            return;
        }
        if (currentCombination.length() == maxLength) { // Check if combination length matches target length
            if (currentCombination.equals(ResultsController.password)) { // Check if current combination matches inputted word
                System.out.println("Match found: " + currentCombination);
                matchFound = true;
            }
            return;
        }

        for (int i = 0; i < characters.length(); i++) {
            generateCombinations(characters, currentCombination + characters.charAt(i), maxLength); // Recursive call
        }
    }
}
