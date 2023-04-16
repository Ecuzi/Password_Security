package com.example.password_cracker;// java.io for reading the txt files, java.util.scanner for taking user input
import java.io.*;
import java.util.*;

public class PasswordSearch {
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
            System.out.println("made TRY");
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
                System.out.println("Made While");
                attempts[0] += 1;
                //compares each word in wordlist to the inputted password, and if true prints the line it was found on then exits the loop
                if (line.equals(wordToSearch)) {
                    wordFound = true;
                    end = System.currentTimeMillis();
                    time = end-start;
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
                        time = end-start;
                        status = 1;
                        return;
                    }
                }
            }

            //if the word isn't found in the customWordList you can attempt a true brute force(will take a long time)
            if (!wordFound) {

                    time = end;

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
    private static boolean findMatches(String word, int[] attempts) {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+-=[]{};:,.<>?";

        for (int length = 1; length <= word.length(); length++) {
            //generates the combinations for each word length
            Iterator<String> iterator = generateCombinations(characters, length).iterator();

            while (iterator.hasNext()) {
                attempts[0] += 1;
                String combination = iterator.next();
                if (combination.equals(word)) {
                    return true;
                }
            }
        }

        return false;
    }

    //generates the combinations for each word length
    private static Iterable<String> generateCombinations(String characters, int length) {
        return new Iterable<String>() {
            @Override
            public Iterator<String> iterator() {
                return new Iterator<String>() {
                    private int[] indices = new int[length];
                    private boolean done = false;

                    @Override
                    public boolean hasNext() {
                        return !done;
                    }

                    @Override
                    public String next() {
                        String combination = "";

                        for (int i = 0; i < length; i++) {
                            combination += characters.charAt(indices[i]);
                        }

                        incrementIndices();

                        return combination;
                    }

                    private void incrementIndices() {
                        for (int i = length - 1; i >= 0; i--) {
                            if (indices[i] < characters.length() - 1) {
                                indices[i]++;
                                return;
                            } else {
                                indices[i] = 0;
                            }
                        }

                        done = true;
                    }
                };
            }
        };
    }
}