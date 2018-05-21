/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hairsalon.main;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 *
 * @author Jacko
 */
public class privacyPolicyController implements Initializable {

    public static AnchorPane rootP;
    @FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private WebView webBrowser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        rootP = AnchorPane;
        WebEngine webEngine = webBrowser.getEngine();
        webEngine.load("http://localhost:33435/index.html#!/privacyPolicy");

    }
}
