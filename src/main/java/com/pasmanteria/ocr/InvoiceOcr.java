package com.pasmanteria.ocr;


import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by adrian on 25.04.2017.
 */
public class InvoiceOcr {

    public String scanInvoice(String imageName, int leftX, int topY, int widthX, int heightY) {

        String result = new String();
        File imageFile = new File(".\\invoice_images\\"+imageName);
        ITesseract instance = new Tesseract();
        instance.setLanguage("pol");
        int height = 0;
        int width = 0;
        BufferedImage bimg;
        try {
            bimg = ImageIO.read(imageFile);
            width = bimg.getWidth();
            height = bimg.getHeight();
        } catch (IOException ex) {
            Logger.getLogger(InvoiceOcr.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            result = instance.doOCR(imageFile, new Rectangle(leftX, topY, widthX, heightY));
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }
}
