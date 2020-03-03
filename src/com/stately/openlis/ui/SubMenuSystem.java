/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stately.openlis.ui;

import com.stately.openlis.hl7.models.constant.Module;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fxml.MainUIController;
import fxml.MainUIController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * @author Edwin Kwame Amoakwa
 * @email edwin.amoakwa@gmail.com
 * @contact 0277115150
 */
public class SubMenuSystem extends VBox {

    private static final int animationDuration = 400;

    private final BorderPane stackPane;

    private MainUIController mainUIController;

    public Module currentModule;

    public Button workspaceUIButton = GlyphsDude.createIconButton(FontAwesomeIcon.ANCHOR, "Workspace", null, null, ContentDisplay.LEFT);


    public SubMenuSystem(MainUIController mainUIController) {
        this.mainUIController = mainUIController;
        stackPane = mainUIController.getConainet();


        loadScreen(Resources.DevicesUI, Resources.DevicesUI);
        loadScreen(Resources.MedicalSystemUI, Resources.MedicalSystemUI);
        loadScreen(Resources.WorkSpaceUI, Resources.WorkSpaceUI);
        loadScreen(Resources.LisConfigurationUI, Resources.LisConfigurationUI);
        loadScreen(Resources.TestOrdersUI, Resources.TestOrdersUI);

//        loadScreen("labels", MultipleScreenFramework.labelFXML);
//        setScreen("buttons");


        System.out.println("stack pane .....  " + stackPane);

    }


    public void initUi() {
        setSpacing(5);
        setPadding(new Insets(10));
        Button devicesButton = GlyphsDude.createIconButton(FontAwesomeIcon.ANCHOR, "Devices", null, null, ContentDisplay.LEFT);
        devicesButton.setMaxWidth(Double.MAX_VALUE);

        Button medicalSystems = GlyphsDude.createIconButton(FontAwesomeIcon.BOLT, "Medical Systems", null, null, ContentDisplay.LEFT);
        medicalSystems.setMaxWidth(Double.MAX_VALUE);

        FontAwesomeIconView awesomeIconView = new FontAwesomeIconView();
        awesomeIconView.setIcon(FontAwesomeIcon.ANCHOR);
        awesomeIconView.setGlyphSize(16);

        Button emrTestOrdersButton = GlyphsDude.createIconButton(FontAwesomeIcon.ANCHOR, "EMR Test Orders", null, null, ContentDisplay.LEFT);
        emrTestOrdersButton.setMaxWidth(Double.MAX_VALUE);


        workspaceUIButton.setMaxWidth(Double.MAX_VALUE);

        Button lisConfigurationUI = GlyphsDude.createIconButton(FontAwesomeIcon.ANCHOR, "LIS Config", null, null, ContentDisplay.LEFT);
        lisConfigurationUI.setMaxWidth(Double.MAX_VALUE);

        getChildren().add(lisConfigurationUI);

//        getChildren().add(medicalSystems);
//        getChildren().add(devicesButton);
        getChildren().add(emrTestOrdersButton);
        getChildren().add(workspaceUIButton);


        lisConfigurationUI.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainUIController.setPageTitle("LIS Configuration");
                setScreen(Resources.LisConfigurationUI);
            }
        });

        workspaceUIButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainUIController.setPageTitle("Workspace");
                setScreen(Resources.WorkSpaceUI);
            }
        });

        devicesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainUIController.setPageTitle("Devices");
                setScreen(Resources.DevicesUI);
            }
        });

        medicalSystems.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainUIController.setPageTitle("Medical Systems");
                setScreen(Resources.MedicalSystemUI);
            }
        });

        emrTestOrdersButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainUIController.setPageTitle("EMR Test Orders");
                setScreen(Resources.TestOrdersUI);
            }
        });

    }


    private HashMap<String, Node> screens = new HashMap<>();


    //Add the screen to the collection
    public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

    //Returns the Node with the appropriate name
    public Node getScreen(String name) {
        return screens.get(name);
    }


    public static Map<String, FXMLLoader> map = new LinkedHashMap();

    //Loads the fxml file, add the screen to the screens collection and
    //finally injects the screenPane to the controller.
    public boolean loadScreen(String name, String resource) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = (Parent) myLoader.load();
            System.out.println(name);
            map.put(name, myLoader);

//            ScreenController myScreenControler = ((ScreenController)myLoader.getController());
//            myScreenControler.setScreenPane(mainUIController.getConainet());
            addScreen(name, loadScreen);
            return true;
        } catch (Exception e) {
            System.err.println(name);
            System.out.println(e.getMessage());
            return false;
        }
    }

    //This method tries to displayed the screen with a predefined name.
    //First it makes sure the screen has been already loaded.  Then if there is more than
    //one screen the new screen is been added second, and then the current screen is removed.
    // If there isn't any screen being displayed, the new screen is just added to the root.

    public boolean setScreen(final String name) {
        if (screens.get(name) != null) {   //screen loaded
            final DoubleProperty opacity = stackPane.opacityProperty();

            if (!stackPane.getChildren().isEmpty()) {    //if there is more than one screen
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                        new KeyFrame(new Duration(animationDuration), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent t) {
                                stackPane.getChildren().remove(0);                    //remove the displayed screen
                                stackPane.getChildren().add(0, screens.get(name));     //add the screen
                                Timeline fadeIn = new Timeline(
                                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                        new KeyFrame(new Duration(animationDuration), new KeyValue(opacity, 1.0)));
                                fadeIn.play();
                            }
                        }, new KeyValue(opacity, 0.0)));
                fade.play();

            } else {
                stackPane.setOpacity(0.0);
                //no one else been displayed, then just show
                stackPane.setCenter(screens.get(name));       //no one else been displayed, then just show

                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(animationDuration / 2), new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
            return true;
        } else {
            System.out.println("screen hasn't been loaded!!! for " + name + "\n");
            return false;
        }


        /*Node screenToRemove;
        if(screens.get(name) != null){   //screen loaded
            if(!getChildren().isEmpty()){    //if there is more than one screen
                getChildren().add(0, screens.get(name));     //add the screen
                screenToRemove = getChildren().get(1);
                getChildren().remove(1);                    //remove the displayed screen
            }else{
             getChildren().add(screens.get(name));       //no one else been displayed, then just show
            }
            return true;
        }else {
            System.out.println("screen hasn't been loaded!!! \n");
            return false;
        }*/
    }


    //This method will remove the screen with the given name from the collection of screens
    public boolean unloadScreen(String name) {
        if (screens.remove(name) == null) {
            System.out.println("Screen didn't exist");
            return false;
        } else {
            return true;
        }
    }

}
