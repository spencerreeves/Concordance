//*******************************************************************
//Author: Spencer E Reeves
//Last Modified: 08-07-2017
//
//Purpose: 
//  A simple demonstration of how to use the Concordance library.
//*******************************************************************

package com.pkc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConcordanceRunner {
    
    /**
     * Sample runner for concordance library. 
     * @param args Takes a single string -- path -- to a file to be parsed.
     */
    public static void main(String[] args) {
        //Check arguments
        if (args.length < 0) {
            throw new IllegalArgumentException("One argument expected: file path.");
        }

        //Create variables for parsing file
        File file = new File(args[0]);
        Reader reader;
        Concordance concordance;
        
        try {
            //Create reader for parsing
            reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
            
            //Instantiate, read, and sort file
            concordance = new Concordance(Locale.getDefault());
            concordance.readText(reader, true);
            SortedSet<Entry<String, Integer>> set = concordance.getWords(Concordance.Sort.ALPHABETIC, false);
            
            //Do stuff
            System.out.println(set.first());
            System.out.println(set.last());
            System.out.println(set);            
        } catch (IOException ex) {
            Logger.getLogger(ConcordanceRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
