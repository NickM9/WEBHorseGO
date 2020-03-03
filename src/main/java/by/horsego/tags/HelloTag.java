package by.horsego.tags;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 *
 * Tag that sends a hello message.
 *
 * Class implementation of a custom tag that does not have a body. Extends class {@link TagSupport}.
 * Value {@link #userName} stores the user name, if the field has a null value
 * greeting takes place with no name.
 *
 * @see TagSupport
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class HelloTag extends TagSupport {

    private static final Logger logger = Logger.getLogger(HelloTag.class);
    private String userName;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int doStartTag() {
        JspWriter out = pageContext.getOut();

        try {
            if (userName == null || userName.equals("")){
                out.write("Hello!");
            } else {
                out.write("Hello, " + userName + "!");
            }
        } catch (IOException e) {
            logger.warn(e);
        }

        return SKIP_BODY;
    }
}
