package com.devstack.pos.controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;

public class QRFormController {

    public ImageView qr;

    public void setQr(byte[] arr){
        Image image = new Image(new ByteArrayInputStream(arr));
        qr.setImage(image);
    }

}
