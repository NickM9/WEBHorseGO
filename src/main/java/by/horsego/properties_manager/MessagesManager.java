package by.horsego.properties_manager;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class provides access to a file with localized messages for users.
 * To get the value by key, use the method {@link #getProperty(String, Locale)}
 *
 * @see #getProperty(String, Locale)
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class MessagesManager {

    private static ResourceBundle resourceBundle;

    private MessagesManager(){}

    public static String getProperty(String key, Locale locale){
        resourceBundle = ResourceBundle.getBundle("messages", locale);
        return resourceBundle.getString(key);
    }

}
