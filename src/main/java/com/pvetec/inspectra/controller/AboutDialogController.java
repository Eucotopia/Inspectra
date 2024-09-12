package com.pvetec.inspectra.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.InputStream;
import java.util.Objects;

public class AboutDialogController {
    @FXML
    private ImageView logoImageView;

    @FXML
    private Text appNameText;

    @FXML
    private Text versionText;

    @FXML
    private Text descriptionText;

    @FXML
    private Text copyrightText;

    @FXML
    private Text contactText;

    @FXML
    private void initialize() {
        // Set the logo image if needed
        InputStream logoStream = getClass().getResourceAsStream("/path/to/logo.png");
        if (logoStream != null) {
            logoImageView.setImage(new Image(logoStream));
        }
        // Set dynamic content if needed
        appNameText.setText("Inspectra");
        versionText.setText("Version 1.0.0");
        descriptionText.setText("Inspectra is an advanced inspection tool for various applications.");
        copyrightText.setText("© 2024 PveTec. All rights reserved.");
        contactText.setText("For support, contact: support@pvetec.com");
    }
}