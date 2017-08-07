//*******************************************************************
//Author: Spencer E Reeves
//Last Modified: 08-07-2017

//Tests:
//  testReadText - Test that a unicode file can be read.
//  testGetWordsByAlphabetical - Tests the alphabetically sorted set.
//  testGetWordsByFrequency - Tests the "by-frequency" sorted set.
//  testGetWordFrequency - Tests the map containing all the words from the text.
//*******************************************************************

package com.pkc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import org.junit.Test;
import static org.junit.Assert.*;


public class ConcordanceUnitTests {
    
    public static final String BASE = System.getProperty("user.dir") + File.separator + 
                              "test" + File.separator + "sample_data" + File.separator ;
    public static final File ENGLISH_FILE = new File (BASE + "english_test.txt");
    public static final File UNICODE_FILE = new File (BASE + "unicode_test.txt");
    public static final File COUNT_FILE_1 = new File (BASE + "count_test_x1.txt");
    public static final File COUNT_FILE_2 = new File (BASE + "count_test_x4.txt");
    
    public ConcordanceUnitTests() {
        //TODO: Import and initialize logger
    }

    /**
     * Test of readText method.  This test validates no errors are thrown when
     * reading standard text files in various languages. IgnoreCase boolean is
     * tested but not validated.
     * @throws java.io.IOException
     */
    @Test
    public void testReadText() throws IOException {
        Concordance instance = new Concordance(Locale.getDefault());
        instance.readText(new InputStreamReader(new FileInputStream(ENGLISH_FILE), "UTF-8"), true);
        
        instance = new Concordance(Locale.US);
        instance.readText(new InputStreamReader(new FileInputStream(UNICODE_FILE), "UTF-8"), true);
        
        instance = new Concordance(Locale.US);
        instance.readText(new InputStreamReader(new FileInputStream(ENGLISH_FILE), "UTF-8"), false);
        
        instance = new Concordance(Locale.US);
        instance.readText(new InputStreamReader(new FileInputStream(UNICODE_FILE), "UTF-8"), false);
    }

    /**
     * Test of getWords with the Alphabetical ordering. This test validates that
     * the function returns a non empty, alphabetically sorted, set of entries.
     * Pre-determined defaults are compared against the returned set.
     * @throws java.io.IOException
     */
    @Test
    public void testGetWordsByAlphabetical() throws IOException {
        Concordance instance = new Concordance(Locale.US);
        instance.readText(new InputStreamReader(new FileInputStream(ENGLISH_FILE), "UTF-8"), false);
        
        assertEquals(instance.getWords(Concordance.Sort.ALPHABETIC, false).size(), 18);
        assertEquals(instance.getWords(Concordance.Sort.ALPHABETIC, false).first().getKey(), "be");
        assertTrue(instance.getWords(Concordance.Sort.ALPHABETIC, false).first().getValue() == 3);
        assertEquals(instance.getWords(Concordance.Sort.ALPHABETIC, false).last().getKey(), "Zoo");
        assertTrue(instance.getWords(Concordance.Sort.ALPHABETIC, false).last().getValue() == 1);
        
        instance = new Concordance(Locale.US);
        instance.readText(new InputStreamReader(new FileInputStream(ENGLISH_FILE), "UTF-8"), true);
        
        assertEquals(instance.getWords(Concordance.Sort.ALPHABETIC, true).size(), 14);
        assertEquals(instance.getWords(Concordance.Sort.ALPHABETIC, true).last().getKey(), "be");
        assertTrue(instance.getWords(Concordance.Sort.ALPHABETIC, true).last().getValue() == 4);
        assertEquals(instance.getWords(Concordance.Sort.ALPHABETIC, true).first().getKey(), "zoo");
        assertTrue(instance.getWords(Concordance.Sort.ALPHABETIC, true).first().getValue() == 2);
        
        instance = new Concordance(Locale.US);
        instance.readText(new InputStreamReader(new FileInputStream(UNICODE_FILE), "UTF-8"), false);
        
        assertEquals(instance.getWords(Concordance.Sort.ALPHABETIC, false).size(), 13);
        assertEquals(instance.getWords(Concordance.Sort.ALPHABETIC, false).first().getKey(), "Ahh");
        assertTrue(instance.getWords(Concordance.Sort.ALPHABETIC, false).first().getValue() == 1);
        assertEquals(instance.getWords(Concordance.Sort.ALPHABETIC, false).last().getKey(), "𐊅𐊆𐊇𐊈𐊉");
        assertTrue(instance.getWords(Concordance.Sort.ALPHABETIC, false).last().getValue() == 2);
        
        instance = new Concordance(Locale.US);
        instance.readText(new InputStreamReader(new FileInputStream(UNICODE_FILE), "UTF-8"), true);
        
        assertEquals(instance.getWords(Concordance.Sort.ALPHABETIC, true).size(), 13);
        assertEquals(instance.getWords(Concordance.Sort.ALPHABETIC, true).last().getKey(), "ahh");
        assertTrue(instance.getWords(Concordance.Sort.ALPHABETIC, true).last().getValue() == 1);
        assertEquals(instance.getWords(Concordance.Sort.ALPHABETIC, true).first().getKey(), "𐊅𐊆𐊇𐊈𐊉");
        assertTrue(instance.getWords(Concordance.Sort.ALPHABETIC, true).first().getValue() == 2);
    }

    /**
     * Test of getWords with the Frequency ordering. This test validates that
     * the function returns a non empty, sorted by frequency of the word, set of entries.
     * Pre-determined defaults are compared against the returned set.
     * @throws java.io.IOException
     */
    @Test
    public void testGetWordsByFrequency() throws IOException {
        Concordance instance = new Concordance(Locale.US);
        instance.readText(new InputStreamReader(new FileInputStream(ENGLISH_FILE), "UTF-8"), false);
        
        assertEquals(instance.getWords(Concordance.Sort.FREQUENCY, false).size(), 18);
        assertEquals(instance.getWords(Concordance.Sort.FREQUENCY, false).first().getKey(), "should");
        assertTrue(instance.getWords(Concordance.Sort.FREQUENCY, false).first().getValue() == 4);
        assertEquals(instance.getWords(Concordance.Sort.FREQUENCY, false).last().getKey(), "Zoo");
        assertTrue(instance.getWords(Concordance.Sort.FREQUENCY, false).last().getValue() == 1);
        
        instance = new Concordance(Locale.US);
        instance.readText(new InputStreamReader(new FileInputStream(ENGLISH_FILE), "UTF-8"), true);
        
        assertEquals(instance.getWords(Concordance.Sort.FREQUENCY, true).size(), 14);
        assertEquals(instance.getWords(Concordance.Sort.FREQUENCY, true).last().getKey(), "be");
        assertTrue(instance.getWords(Concordance.Sort.FREQUENCY, true).last().getValue() == 4);
        assertEquals(instance.getWords(Concordance.Sort.FREQUENCY, true).first().getKey(), "twice");
        assertTrue(instance.getWords(Concordance.Sort.FREQUENCY, true).first().getValue() == 1);
        
        instance = new Concordance(Locale.US);
        instance.readText(new InputStreamReader(new FileInputStream(COUNT_FILE_1), "UTF-8"), false);
        
        assertEquals(instance.getWords(Concordance.Sort.FREQUENCY, false).size(), 19);
        assertEquals(instance.getWords(Concordance.Sort.FREQUENCY, false).first().getKey(), "ᚠᚩᚱ");
        assertTrue(instance.getWords(Concordance.Sort.FREQUENCY, false).first().getValue() == 1);
        assertEquals(instance.getWords(Concordance.Sort.FREQUENCY, false).last().getKey(), "ᛞᚱᛁᚻᛏᚾᛖ");
        assertTrue(instance.getWords(Concordance.Sort.FREQUENCY, false).last().getValue() == 1);
        
        instance = new Concordance(Locale.US);
        instance.readText(new InputStreamReader(new FileInputStream(COUNT_FILE_2), "UTF-8"), true);
        
        assertEquals(instance.getWords(Concordance.Sort.FREQUENCY, true).size(), 19);
        assertEquals(instance.getWords(Concordance.Sort.FREQUENCY, true).last().getKey(), "ᚠᚩᚱ");
        assertTrue(instance.getWords(Concordance.Sort.FREQUENCY, true).last().getValue() == 4);
        assertEquals(instance.getWords(Concordance.Sort.FREQUENCY, true).first().getKey(), "ᛞᚱᛁᚻᛏᚾᛖ");
        assertTrue(instance.getWords(Concordance.Sort.FREQUENCY, true).first().getValue() == 4);
    }
    
    /**
     * Test of getWordFrequency function. This test validates that when a valid 
     * word is provided the correct frequency is returned for that given word. 
     * Pre-determined defaults are compared against the returned number.
     * @throws java.io.IOException
     */
    @Test
    public void testGetWordFrequency() throws IOException {
        Concordance instance = new Concordance(Locale.US);
        instance.readText(new InputStreamReader(new FileInputStream(ENGLISH_FILE), "UTF-8"), false);
        
        assertTrue(instance.getWordFrequency("should") == 4);
        assertTrue(instance.getWordFrequency("Be") == 1);
        assertTrue(instance.getWordFrequency("NotAWord") == 0);
        
        instance = new Concordance(Locale.US);
        instance.readText(new InputStreamReader(new FileInputStream(COUNT_FILE_2), "UTF-8"), true);
        
        assertTrue(instance.getWordFrequency("ᚠᛇᚻ") == 4);
        assertTrue(instance.getWordFrequency("ᛦᚠᛇᚻ") == 0);
        assertTrue(instance.getWordFrequency("ᚷᛖᚻᚹᛦᛚᚳ") == 4);
        
        instance = new Concordance(Locale.US);
        instance.readText(new InputStreamReader(new FileInputStream(UNICODE_FILE), "UTF-8"), false);
        
        assertTrue(instance.getWordFrequency("𐊅𐊆𐊇𐊈𐊉") == 2);
        assertTrue(instance.getWordFrequency("𐊁𐊂𐊃𐊄𐊅𐊆𐊇𐊈𐊉𐊊𐊋𐊌𐊍𐊎𐊏") == 2);
        assertTrue(instance.getWordFrequency("𐊆𐊇𐊈𐊉𐊊𐊋𐊌𐊍𐊎") == 0);
    }  
    
    /**
     * Test clear function. This test validates that the mapping is cleared and
     * no words are retained.
     * @throws java.io.IOException
     */
    @Test
    public void testClear() throws IOException {
        Concordance instance = new Concordance(Locale.US);
        instance.readText(new InputStreamReader(new FileInputStream(ENGLISH_FILE), "UTF-8"), false);
        
        assertTrue(instance.getWordFrequency("should") == 4);
        instance.clear();
        assertTrue(instance.getWordFrequency("should") == 0);
        
        instance = new Concordance(Locale.US);
        instance.readText(new InputStreamReader(new FileInputStream(COUNT_FILE_2), "UTF-8"), true);
        
        assertTrue(instance.getWordFrequency("ᚠᛇᚻ") == 4);
        instance.clear();
        assertTrue(instance.getWordFrequency("ᚠᛇᚻ") == 0);
        
        instance = new Concordance(Locale.US);
        instance.readText(new InputStreamReader(new FileInputStream(UNICODE_FILE), "UTF-8"), false);
        
        assertTrue(instance.getWordFrequency("𐊅𐊆𐊇𐊈𐊉") == 2);
        instance.clear();
        assertTrue(instance.getWordFrequency("𐊅𐊆𐊇𐊈𐊉") == 0);
    }  
}