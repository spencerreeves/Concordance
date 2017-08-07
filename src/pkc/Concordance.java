//*******************************************************************
//Author: Spencer E Reeves
//Last Modified: 08-07-2017
//
//Restrictions:
//  English Apostrophes and Hyphenated words: This library does not support apostrophes
//    and hyphenated words commonly found in the english language.  I chose to forgo
//    this feature due to the cost vs benfit analysis.
//
//Design Decisions:
//  Use of MutableInt vs Integer in hashmap - One can create a custome wrapper class
//    that contains an integer and a function to increment that integer.  By doing
//    this, you can find a optimization in the code. I left it out to keep the code
//    as simple, clean, and readable as possible
//  Multithread vs singlethreaded - I choose to singlethread my application primarily
//    due to the costs of context switching.  It is possible to get a performance
//    gain under the right conditions with multithreading, but for the average file
//    the performance gain is minimal and the increase in complexity is large.
//
//Improvements:
//  Add a logger - Although java has a built in logger framework, there are other
//    libraries that are significantly better and more efficient.
//  Migrate to a multithread solution - Although this will not result in a significant
//    gain for the average case, this will provide a significant gain for the edge
//    cases with a very large file.
//  Use a MutableInt for the HashMap - This will have a performance increase for
//    when a word has already been added to the map.
//
//*******************************************************************
package com.pkc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.Collator;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

public class Concordance {
    
    private final Collator collator;
    private boolean ignoreCase = false;
    
    private final Comparator<Entry<String, Integer>> BY_ALPHABETIC;
    private final Comparator<Entry<String, Integer>> BY_FREQUENCY;

    private final HashMap<String, Integer> wordMap = new HashMap<>();
    private final TreeSet<Entry<String, Integer>> alphaSet;
    private final TreeSet<Entry<String, Integer>> freqSet;
    
    public enum Sort {
        ALPHABETIC,
        FREQUENCY;
    }
    
    /**
     * Instantiates a new concordance instance. 
     * @param locale - The local sorting to be used when reading a text.
     */
    public Concordance(Locale locale){
        collator = Collator.getInstance(locale);
        
        BY_ALPHABETIC = (o1, o2) -> {return collator.compare(o1.getKey(), o2.getKey());};
        BY_FREQUENCY = (o1, o2) -> {
            int comp = o2.getValue().compareTo(o1.getValue());
            return (comp == 0) ? collator.compare(o1.getKey(), o2.getKey()) : comp;};
        
        alphaSet = new TreeSet(BY_ALPHABETIC);
        freqSet = new TreeSet(BY_FREQUENCY);
    }
    
    /**
     * Clears the words from the map if there are any.
     */
    public void clear() {
        wordMap.clear(); 
        alphaSet.clear(); 
        freqSet.clear();
    }
    
    /**
     * Used to get the frequency of a specific word in the map.
     * @param word - The string to be searched for in the map
     * @return - Returns the frequency of the word.  0 if the word is not in the map
     */
    public int getWordFrequency(String word){
        return wordMap.getOrDefault(ignoreCase ? word.toLowerCase() : word, 0);
    }
    
    /**
     * Reads a text and maps the words to the number of times each occurs. This 
     *   will remove all punctuation, including connecting punctuation.
     * @param input - Reader to be used for reading the unicode text
     * @param ignoreCase - boolean for if the case should be ignored
     * @throws IOException 
     */
    public void readText(Reader input, boolean ignoreCase) throws IOException{
        try (BufferedReader reader = new BufferedReader(input)) {

            String line;
            StringBuilder sb = new StringBuilder();
            this.ignoreCase = ignoreCase;

            while ((line = reader.readLine()) != null) {
                line.codePoints().forEach(cp -> {
                    if (Character.isAlphabetic(cp)) {
                        sb.append(Character.toChars(cp));
                    } else if (sb.length() != 0) {
                        addWord(ignoreCase ? sb.toString().toLowerCase() : sb.toString());
                        sb.setLength(0);
                    }
                });
                if (sb.length() != 0) {
                    addWord(ignoreCase ? sb.toString().toLowerCase() : sb.toString());
                    sb.setLength(0);
                }
            }
        }
    }
    
    /**
     * Sorts the words if they have not already been sorted.  
     * @param order - The sorting method to be used.
     * @param reversed - Whether or not to reverse the set of words.
     * @return - A set of the words ordered defined by the parameters
     */
    public SortedSet<Entry<String,Integer>> getWords(Sort order, boolean reversed){
        TreeSet<Entry<String, Integer>> set = order.equals(Sort.ALPHABETIC) ? alphaSet : freqSet;
        
        if (set.isEmpty()) {
            wordMap.entrySet().forEach((entry) -> {set.add(entry);});
        }
        
        return reversed ? set.descendingSet() : set;
    }
    
    /**
     * Adds a word to the word map.
     * @param word 
     */
    private void addWord(String word) {
        Integer value = wordMap.get(word);
        if (value != null) {
            wordMap.replace(word, value + 1);
        } else {
            wordMap.put(word, 1);
        }
    }
}