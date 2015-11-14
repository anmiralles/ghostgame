package pro.angelmiralles.ghostgame.view.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

/**
 * 
 * @author angelmiralles
 */
public class GhostDictionaryBean extends BaseBean implements Serializable {
    
    /**
     * Serialization
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Logger
     */
    private static Logger logger_= Logger.getLogger(GhostDictionaryBean.class.getName());
    
    /**
     * Variable
     */
    private LinkedList<String> wordList_ = new LinkedList<String>();
    
    /**
     * Constructor.
     */
    public GhostDictionaryBean(){}
    
    /**
     * Constructor
     *     
     * @param fileName The name of the dictionary file. This file is assumed to
     * be sorted in alphabetical order.
     */
    public GhostDictionaryBean(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String word = null;
            while ((word = reader.readLine()) != null) {
                addWord(word);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            logger_.severe("File " + fileName + " not found.");
        } catch (IOException ioException) {
            logger_.severe("Error reading file:  " + ioException.toString());
        }
    }
    
    /**
     * Constructor. This constructor builds a dictionary from all of the words in the specified
     * dictionary that begin with the specified string.
     *     
     * @param dictionary The base dictionary.
     * @param start The starting string.
     */
    public GhostDictionaryBean(GhostDictionaryBean dictionary, String start) {
        
        ListIterator<String> iterator = dictionary.wordList_.listIterator(0);
        String word = null;

        while (iterator.hasNext()) {
            if ((word = iterator.next()).startsWith(start)) {
                wordList_.addLast(word);
            }
        }
    }
    
    /**
     * 
     * @return an iterator to the underlying collection of words.
     */
    public ListIterator<String> listIterator() {
        return wordList_.listIterator(0);
    }

    /**
     * 
     * @return the number of words in the dictionary.
     */
    public long size() {
        return wordList_.size();
    }

    /**
     * 
     * @return the first word in the dictionary.
     */
    public String firstWord() {
        String firstWord = null;

        try {
            firstWord = wordList_.getFirst();
        } catch (NoSuchElementException e) {
        }

        return firstWord;
    }
    
    /**
     * Add a word to the dictionary if it meets two criteria:
     * <ul>
     * <li> The word must be at least MIN_WORD_LENGTH letters long.
     * <li> The word must not start with another valid word.
     * </ul>
     * Words are assumed to be added in alphabetical order, so the second
     * criteria can be evaluated simply by comparing the word with the last word
     * in the list.
     *     
     * @param word The word to add to the dictionary.
     */
    private void addWord(String word) {
        String lastWord = null;

        if (word.length() >= MIN_WORD_LENGTH) {
            try {
                lastWord = wordList_.getLast();
            } catch (NoSuchElementException e) {
            }

            if ((lastWord == null) || !word.startsWith(lastWord)) {
                wordList_.addLast(word);
            }
        }
    }
}
