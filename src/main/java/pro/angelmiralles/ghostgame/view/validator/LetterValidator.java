/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pro.angelmiralles.ghostgame.view.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import pro.angelmiralles.ghostgame.view.util.BaseBean;

/**
 *
 * @author angelmiralles
 */
public class LetterValidator extends BaseBean implements Validator{
    @Override
    public void validate(FacesContext arg0, UIComponent arg1, Object value)
            throws ValidatorException {

        Pattern pat = null;
        Matcher mat = null;
        pat = Pattern.compile("[a-zA-Z]");
        mat = pat.matcher(value.toString());
        if (!mat.find()) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, this.msg.getString("info_no_letter"), null));
        }

    }
}
