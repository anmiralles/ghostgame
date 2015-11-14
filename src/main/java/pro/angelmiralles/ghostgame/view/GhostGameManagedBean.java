package pro.angelmiralles.ghostgame.view;

import java.io.Serializable;
import java.util.Locale;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import pro.angelmiralles.ghostgame.view.util.BaseBean;
import pro.angelmiralles.ghostgame.view.util.ComputerPlayerBean;
import pro.angelmiralles.ghostgame.view.util.GhostDictionaryBean;

/**
 *
 * @author angelmiralles
 */
@ManagedBean
@SessionScoped
public class GhostGameManagedBean extends BaseBean implements Serializable {

    /**
     * Serialization
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Logger
     */
    private static Logger logger_ = Logger.getLogger(GhostGameManagedBean.class.getName());

    /**
     * Variables
     */
    private GhostDictionaryBean dictionary_ = null;
    private ComputerPlayerBean computer = new ComputerPlayerBean();
    private StringBuffer wordInPlay_ = new StringBuffer();
    private boolean gameOver_ = false;
    private String letter = "";
    private String strName = "";
    private String strUserMessage = "";

    public StringBuffer getWordInPlay_() {
        return wordInPlay_;
    }
    public void setWordInPlay_(StringBuffer wordInPlay_) {
        this.wordInPlay_ = wordInPlay_;
    }
    public String getLetter() {
        return letter;
    }
    public void setLetter(String letter) {
        this.letter = letter;
    }
    public String getStrName() {
        return strName;
    }
    public void setStrName(String strName) {
        this.strName = strName;
    }
    public String getStrUserMessage() {
        return strUserMessage;
    }
    public void setStrUserMessage(String strUserMessage) {
        this.strUserMessage = strUserMessage;
    }
    public boolean isGameOver_() {
        return gameOver_;
    }
    public void setGameOver_(boolean gameOver_) {
        this.gameOver_ = gameOver_;
    }

    /**
     * Constructor.
     */
    public GhostGameManagedBean() {
        dictionary_ = new GhostDictionaryBean(prop.getProperty("FilePath"));
        if (dictionary_.size() == 0) {
            throw new RuntimeException("Unable to load dictionary from file"
                    + ".");
        }
    }

    /**
     * Constructor.
     *
     * @param fileName The name of the dictionary file. This file is assumed to
     * be sorted in alphabetical order.
     */
    public GhostGameManagedBean(String fileName) {

    }

    /**
     * Add a letter to the word in play.
     */
    private void addLetter(char letter) {
        
        wordInPlay_.append(letter);
        dictionary_ = new GhostDictionaryBean(dictionary_, wordInPlay_.toString());

        if (dictionary_.size() == 0) {
            gameOver_ = true;
            getFacesContext().getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, this.msg.getString("info_no_pattern"), null));
        } else if ((dictionary_.size() == 1)
                && dictionary_.firstWord().equals(wordInPlay_.toString())) {
            gameOver_ = true;
            getFacesContext().getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, this.msg.getString("info_no_word"), null));
        }

        // Restart 
        this.letter = "";
    }

    /**
     * Return the word in play.
     */
    public String wordInPlay() {
        return wordInPlay_.toString();
    }

    /**
     * Method to play game
     * @return 
     */
    public String startPlaying() {
        getFacesContext().getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, this.msg.getString("info_first_letter"), null));
        return "fgame";
    }

    /**
     * Method for reading human's letter.
     */
    public void pickLetter() {

        if (!letter.equals("")) {
            // Human move
            addLetter(letter.toLowerCase().charAt(0));

            // Computer move
            if (!gameOver_) {
                addLetter(computer.play(wordInPlay_.toString()));
                
                if (!gameOver_)
                    getFacesContext().getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, this.msg.getString("info_next_letter"), null));
                else
                    this.strUserMessage = this.msg.getString("info_human_wins");
            } else {
                this.strUserMessage = this.msg.getString("info_computer_wins");
            }

            
        } else {
            this.letter="";
            getFacesContext().getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, this.msg.getString("info_no_letter"), null));
        }
    }
    
    /**
     * Method for changing idiom.
     *
     * @param event
     */
    public void changeLocale(ActionEvent event) {
        Locale locale = getFacesContext().getCurrentInstance().getViewRoot().getLocale();
        
        if (locale != null && locale.getLanguage().equals("es")) {
            getFacesContext().getCurrentInstance().getViewRoot().setLocale(new Locale("en"));
        } else {
            getFacesContext().getCurrentInstance().getViewRoot().setLocale(new Locale("es"));
        }
    }

    
    /**
     * Method to close user session and reset game.
     *
     * @return start page
     */
    public String closeSession() {
        getFacesContext().getCurrentInstance().getExternalContext().invalidateSession();

        getFacesContext().getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, this.msg.getString("info_close_session"), null));

        return "fstart";
    }

}
