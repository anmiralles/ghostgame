package pro.angelmiralles.ghostgame.view.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;

/**
 *
 * @author angelmiralles
 */
public class BaseBean implements Serializable {

    /**
     * Serialization
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Logger
     */
    private static Logger logger_ = Logger.getLogger(BaseBean.class.getName());
    
    // Constants
    protected final ResourceBundle msg = ResourceBundle.getBundle("messages");
    protected final ResourceBundle rsc = ResourceBundle.getBundle("images");

    //protected static final String DEFAULT_DICTIONARY_FILE = ""; 
    protected static final int MIN_WORD_LENGTH = 4;
    
    /**
     * Variables
     */
    public Properties prop = new Properties();

    public Properties getProp() {
        return prop;
    }
    public void setProp(Properties prop) {
        this.prop = prop;
    }
    
    /**
     * Constructor.
     */
    public BaseBean(){
        this.prop = loadProperties();
    }
    
    /**
     * Method to obtain jsf context.
     *
     * @return FacesContext
     */
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }
    
    /**
     * Method to load properties from conf file.
     * 
     * @return 
     */
    public Properties loadProperties(){
        InputStream input = null;
        Properties prop = new Properties();
        
	try {
 
		input = getClass().getClassLoader().getResourceAsStream("ghostgame.properties");
 
		// load a properties file
		prop.load(input);
 
	} catch (IOException ex) {
                logger_.severe("Properties not found.");
	} 
        
        return prop;
    }
}
