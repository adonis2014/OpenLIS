package com.stately.openlis.ui;

import javafx.fxml.FXMLLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by bryte on 3/24/14.
 */
public class FxmlLoader
{
    private static final Logger logger = LoggerFactory.getLogger(FxmlLoader.class);

    public static <T>T load(String resource, Class t)
    {
        try
        {
            return (T)FXMLLoader.load(FxmlLoader.class.getResource(resource));
        }
        catch (Exception e)
        {
            logger.error("Error loading resource: " + resource);
        }
        return null;
    }
}
