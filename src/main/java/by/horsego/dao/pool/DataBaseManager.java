package by.horsego.dao.pool;

import java.util.ResourceBundle;

/**
 * The class provides access to the file of DB properties.
 * To get the value by key, use the method {@link #getProperty(String)}
 * 
 * @see #getProperty(String)
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class DataBaseManager {

    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("data_base");

    private DataBaseManager(){}

    public static String getProperty(String key){
        return resourceBundle.getString(key);
    }

}
