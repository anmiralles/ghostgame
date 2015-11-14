package pro.angelmiralles.ghostgame.view.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.logging.Logger;

/**
 *
 * @author angelmiralles
 */
public class WordNodeBean extends BaseBean implements Serializable{
    
    /**
     * Serialization
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Logger
     */
    private static Logger logger_ = Logger.getLogger(WordNodeBean.class.getName());

    /**
     * Variables
     */
    private String string_ = null;
    private HashMap<String, WordNodeBean> childNodes_ = new HashMap<String, WordNodeBean>();
    private Boolean forcedWin_ = null;
    private String longestLosingWord_ = null;
    
    /**
     * Constructor. The single word constructor for the WordNode class.
     *     
     * @param word The base string for the node.
     */
    public WordNodeBean(String word) {
        string_ = word;
    }
    
    /**
     * Constructor. The full constructor for the WordNode class.
     *     
     * @param baseString The string that starts all words in the tree rooted at this node.
     * @param iterator An iterator that returns an alphabetical list of words all starting with the same string.
     */
    public WordNodeBean(String baseString, ListIterator<String> iterator) {
        string_ = baseString;

        while (iterator.hasNext()) {
            addTerminal(new WordNodeBean(iterator.next()));
        }
    }
    
    
    /**
     * Add a new terminal node to the tree.
     *     
     * @param node The new terminal node.
     */
    private void addTerminal(WordNodeBean node) {
        if (node.string_.length() == (string_.length() + 1)) {
            childNodes_.put(node.string_, node);
        } else {
            String key = node.string_.substring(0, string_.length() + 1);
            WordNodeBean child = childNodes_.get(key);
            if (child == null) {
                child = new WordNodeBean(key);
                childNodes_.put(key, child);
            }

            child.addTerminal(node);
        }
    }
    
    /**
     * Return the number of child nodes.
     */
    public long numberOfChildren() {
        return childNodes_.size();
    }

    /**
     * Return the child node associated with the specified string.
     *     
     * @param start The string that starts all the words rooted at the requested
     * child node.
     */
    public WordNodeBean childNode(String start) {
        WordNodeBean node = null;
        if (string_.equals(start)) {
            node = this;
        } else if ((string_.length() < start.length())
                && ((node = childNodes_.get(
                        start.substring(0, string_.length() + 1)))
                != null)) {
            node = node.childNode(start);
        }

        return node;
    }

    /**
     * Return the string that is the base for this node and all child nodes.
     */
    public String baseString() {
        return string_;
    }

    /**
     * Return the children of this node.
     */
    public Collection<WordNodeBean> childNodes() {
        return childNodes_.values();
    }

    /**
     * Return a boolean indicating whether or not this is a terminal node.
     */
    public boolean isTerminalNode() {
        return childNodes_.isEmpty();
    }

    /**
     * Flag whether or not the node is the root of a forced win. Note that this
     * is a flag reflecting the belief of the client of this class, not anything
     * intrinsic in the state of an instance of WordNodeBean.
     *     
     * @param forced A boolean indicating whether or not a win is forced from
     * this node.
     */
    public void flagForcedWin(boolean forced) {
        forcedWin_ = new Boolean(forced);
    }

    /**
     * Return a boolean indicating whether or not this node has been flagged a
     * forced win.
     *     
     * @return A Boolean indicating whether or not this node is the root of a
     * forced win. A return value of null means that the flag has not been set.
     */
    public Boolean isForcedWin() {
        return forcedWin_;
    }

    /**
     * @return the first terminal word found under this node that is a forced
     * win.
     */
    public String firstWinningWord() {
        String word = null;

        if (isTerminalNode() && isForcedWin()) {
            word = string_;
        } else {
            Iterator<String> keyIterator = childNodes_.keySet().iterator();
            while ((word == null) && (keyIterator.hasNext())) {
                word = childNodes_.get(keyIterator.next()).firstWinningWord();
            }
        }

        return word;
    }

    /**
     * Method to find longest losing word
     * 
     * @return the longest losing word reachable from this node.
     */
    public String longestLosingWord() {
        if (longestLosingWord_ == null) {
            longestLosingWord_ = string_;

            Iterator<String> keyIterator = childNodes_.keySet().iterator();
            WordNodeBean child = null;
            while (keyIterator.hasNext()) {
                child = childNodes_.get(keyIterator.next());
                if (!child.isForcedWin()
                        && (child.longestLosingWord().length()
                        > longestLosingWord_.length())) {
                    longestLosingWord_ = child.longestLosingWord();
                }
            }
        }

        return longestLosingWord_;
    }
}
