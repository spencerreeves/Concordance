//*******************************************************************
//Author: Spencer E Reeves
//Last Modified: 08-07-2017

//Tests:
//  multiLangTest - Tests the ability to handle various languages.
//  stressTestDictionary - Tests the ability to handle a large breadth of words.
//  stressTestLargeFile - Tests the ability to handle large files.
//*******************************************************************

package com.pkc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import org.junit.Test;
import static org.junit.Assert.*;


public class ConcordanceStressTests {
    
    public final static String BASE = System.getProperty("user.dir") + File.separator + 
                              "test" + File.separator + "sample_data" + File.separator ;
    public final static File MULTI_LANG_FILE_1 = new File (BASE + "multi_lang_test_1.txt");
    public final static File MULTI_LANG_FILE_2 = new File (BASE + "multi_lang_test_2.txt");
    public final static File LARGE_FILE_NO_REPEAT = new File (BASE + "stress_large_no_repeat.txt");
    public final static File LARGE_FILE_WITH_REPEAT = new File (BASE + "stress_large_with_repeat.txt");
    
    public ConcordanceStressTests() {
        //TODO: Import and initialize logger
    }

    /**
     * Test multiple languages. This test uses multi languages, UTF-8 encoded files,
     * to validate the concordance abilities. The results are compared to manually
     * extracted material.
     * @throws java.io.IOException
     */
    @Test
    public void multiLangTest() throws IOException {
        Concordance instance = new Concordance(Locale.getDefault());
        instance.readText(new InputStreamReader(new FileInputStream(MULTI_LANG_FILE_1), "UTF-8"), true);
        assertEquals(instance.getWords(Concordance.Sort.ALPHABETIC, false).first().getKey(), "ænd");
        assertEquals(instance.getWords(Concordance.Sort.ALPHABETIC, false).last().getKey(), "ﻭﺗﺪﺭﻳﺲ");

        instance = new Concordance(Locale.getDefault());
        instance.readText(new InputStreamReader(new FileInputStream(MULTI_LANG_FILE_2), "UTF-8"), true);
        assertEquals(instance.getWords(Concordance.Sort.ALPHABETIC, false).first().getKey(), "µàæöþßéöÿ");
        assertEquals(instance.getWords(Concordance.Sort.ALPHABETIC, false).last().getKey(), "ﬁ");
    }
    
    /**
     * Test a dictionary based text file. This test is meant to read in and sort
     * a large dictionary text file.  The idea of a dictionary is that the words
     * do not repeat often, forcing the map to grow.
     * @throws java.io.IOException
     */
    @Test
    public void stressTestDictionary() throws IOException {
        Concordance instance = new Concordance(Locale.getDefault());
        instance.readText(new InputStreamReader(new FileInputStream(LARGE_FILE_NO_REPEAT), "UTF-8"), true);
        assertEquals(instance.getWords(Concordance.Sort.ALPHABETIC, false).first().getKey(), "a");
        assertEquals(instance.getWords(Concordance.Sort.ALPHABETIC, false).last().getKey(), "zygomatic");

        instance = new Concordance(Locale.getDefault());
        instance.readText(new InputStreamReader(new FileInputStream(LARGE_FILE_NO_REPEAT), "UTF-8"), true);
        assertEquals(instance.getWords(Concordance.Sort.FREQUENCY, true).first().getKey(), "zygomatic");
        assertEquals(instance.getWords(Concordance.Sort.FREQUENCY, true).last().getKey(), "the");
    }

    /**
     * Test a large file. This test is meant to read in a large file (700+ MB) and
     * sort the words accordingly. ALthough this will no deliver performance metrics
     * it will validate the ability to handle large files.
     * @throws java.io.IOException
     */
    @Test
    public void stressTestLargeFile() throws IOException {
        Concordance instance = new Concordance(Locale.getDefault());
        instance.readText(new InputStreamReader(new FileInputStream(LARGE_FILE_WITH_REPEAT), "UTF-8"), true);
        assertEquals(instance.getWords(Concordance.Sort.ALPHABETIC, false).first().getKey(), "a");
        assertEquals(instance.getWords(Concordance.Sort.ALPHABETIC, false).last().getKey(), "zero");

        instance = new Concordance(Locale.getDefault());
        instance.readText(new InputStreamReader(new FileInputStream(LARGE_FILE_WITH_REPEAT), "UTF-8"), false);
        assertEquals(instance.getWords(Concordance.Sort.FREQUENCY, true).first().getKey(), "zero");
        assertEquals(instance.getWords(Concordance.Sort.FREQUENCY, true).last().getKey(), "the");
    }
}