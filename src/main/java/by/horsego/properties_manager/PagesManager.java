package by.horsego.properties_manager;

import java.util.ResourceBundle;

/**
 * The class provides access to a file with jsp page addresses.
 * To get the value by key, use the method {@link #getProperty(String)}
 *
 * @see #getProperty(String)
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class PagesManager {

    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("pages");

    private PagesManager(){}

    public static String getProperty(String key){
        return resourceBundle.getString(key);
    }

}
