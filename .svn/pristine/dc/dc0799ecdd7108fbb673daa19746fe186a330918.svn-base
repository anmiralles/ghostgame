package pro.angelmiralles.ghostgame.view.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Logger;

/**
 *
 * @author angelmiralles
 */
public class ComputerPlayerBean extends BaseBean implements Serializable{
    
    /**
     * Serialization
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Logger
     */
    private static Logger logger_ = Logger.getLogger(ComputerPlayerBean.class.getName());

    /**
     * Variables
     */
    private String name_ = "ComputerPlayer";
    private GhostDictionaryBean dictionary_ = new GhostDictionaryBean(prop.getProperty("FilePath"));
    private WordNodeBean currentNode_ = null;
    private String longestLosingWord_ = null;
  
    /**
     * Get the node for the specified string.
     *     
     * @param start The initial substring of a word.
     */
    private WordNodeBean wordNode(String start) {
        if (currentNode_ == null) {
            dictionary_ = new GhostDictionaryBean(dictionary_, start);
            currentNode_ = new WordNodeBean(start, dictionary_.listIterator());
        } else {
            currentNode_ = currentNode_.childNode(start);
        }

        return currentNode_;
    }

    /**
     * Compute the forced wins possible in the tree rooted at the specified
     * node. A forced win occurs from a particular node when either a child node
     * is a terminal or all of the grandchild nodes for a particular child node
     * contain a forced win.
     *     
     * @param wordNode The node representing the root of the tree to search.
     */
    private void findForcedWins(WordNodeBean wordNode) {
        boolean forcedWin = false;
        Collection<WordNodeBean> childNodes = wordNode.childNodes();
        Iterator<WordNodeBean> children = childNodes.iterator();
        WordNodeBean child = null;

        while (children.hasNext()) {
            child = children.next();

            boolean childForcedWin = true;
            if (!child.isTerminalNode()) {
                WordNodeBean grandchild = null;
                Iterator<WordNodeBean> grandchildren = child.childNodes().iterator();

                while (grandchildren.hasNext()) {
                    grandchild = grandchildren.next();

                    if (grandchild.isTerminalNode()) {
                        grandchild.flagForcedWin(false);
                    } else {
                        findForcedWins(grandchild);
                    }

                    if (!grandchild.isForcedWin()) {
                        childForcedWin = false;
                    }
                }
            }
            child.flagForcedWin(childForcedWin);

            if (child.isForcedWin()) {
                forcedWin = true;
            }
        }

        wordNode.flagForcedWin(forcedWin);
    }

    /**
     * Choose the next word to play.
     *     
     * @param wordNode The node representing the root of the tree to search.
     * @return A guaranteed winning word or the longest losing word if no forced
     * win exists.
     */
    private String chooseWord(WordNodeBean wordNode) {
        if (wordNode.isForcedWin() == null) {
            findForcedWins(wordNode);
        }

        return wordNode.isForcedWin() ? wordNode.firstWinningWord()
                : wordNode.longestLosingWord();
    }

    /**
     * Get the player's next letter. This method is inherited from GhostPlayer.
     *     
     * @param wordInPlay The word currently being played.
     */
    public char play(String wordInPlay) {
        WordNodeBean wordNode = wordNode(wordInPlay);

        if ((wordNode == null) || (wordNode.numberOfChildren() == 0)) {
            throw new RuntimeException("No matching word found.");
        }

        return chooseWord(wordNode).charAt(wordInPlay.length());
    }

}
