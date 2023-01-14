/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iw.iwmobile.components;

import com.codename1.capture.Capture;
import com.codename1.io.File;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Util;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.UIManager;
import com.iw.iwmobile.Brain;
import com.iw.iwmobile.forms.IwFormBase;

import java.io.IOException;

import static com.codename1.ui.ComponentSelector.$;


/**
 *
 * @author hrugani
 */
public class IwFormSelectPhoto_new extends IwFormBase {
        
    final private Button btnCamera;
    final private Button btnGallery;
    final private Button btnRemove;

    private String filePath;   // file path of original image
    private final MyLabelImage myLblImage;    
    
    final private Button btnCancel;
    final private Button btnConfirm;
    
    //private final String varName;

    public IwFormSelectPhoto_new(
            String varName,
            String varValue) {
       
        //this.varName = varName;
        this.filePath = varValue;

        // pre-init
        setTitle(getIwTranslation(TT_CHOOSE_GET_IMAGE_ACTION));
        btnCamera = createBtnCamera();
        btnGallery = createBtnGallery();
        btnRemove = createBtnRemove();
        myLblImage = new MyLabelImage(filePath);
        btnCancel = createBtnCancel();
        btnConfirm = createBtnConfirm();
        
        // init
        Container cOptions = 
            GridLayout.encloseIn(3, btnCamera, btnGallery, btnRemove);        
        Container cButtons =
            GridLayout.encloseIn(2, btnConfirm, btnCancel);

        setLayout(new BorderLayout());
        setScrollableY(false); 
        
        addComponent(BorderLayout.NORTH, cOptions);
        addComponent(BorderLayout.CENTER, myLblImage);
        addComponent(BorderLayout.SOUTH, cButtons);
                
        System.gc();
    }
        
    public String getImagePath() {
        return myLblImage.getImagePath();
    }
    
    public String getImageLocalhostUrl() {
        String resp;
        FileSystemStorage fs = FileSystemStorage.getInstance();
        try {
            File fIn  = new File(getScaledImagePath());
            String fInName = fIn.getName();
            String fOutName = Brain.getInstance().getWebServeRootPath() + "/" + fInName;
            if ("".equals(fInName) || ".".equals(fInName)) {
                resp = "";
            }
            else {
                Util.copy(
                    fs.openInputStream(getScaledImagePath()),
                    fs.openOutputStream(fOutName)    
                );
                resp = "http://localhost:8888/" + fInName;                
            }
        } catch (IOException ex) {
            resp = "http://localhost:8888/";
        }
        return resp;
    }
    
    public String getScaledImagePath() {
        return resize(getImagePath());
    }
                        
    String resize(String sFilePath) {
        String scaledFilePath;
        try {
            scaledFilePath = 
                Brain
                .getInstance()
                .scaleDownImage1(sFilePath);
        }
        catch (Exception e) {
            scaledFilePath = "";
        }
        return scaledFilePath;
    }

    private Button createBtnCamera() {

        Button btn = createMyButton("", FontImage.MATERIAL_CAMERA);
                
        btn.addActionListener((ActionListener) (ActionEvent evt) -> {
            String photoPath = getImageFromCamera();
            if (photoPath != null && !".".equals(photoPath.trim())) {
                myLblImage.setImage(photoPath);
            }
        });

        return btn;
    }
        
    private Button createBtnGallery() {
        Button btn = createMyButton("", FontImage.MATERIAL_PHOTO_LIBRARY);
        btn.addActionListener((ActionListener) (ActionEvent evt) -> {
            getImagefromGallery();
        });
        return btn;
    }
    
    private Button createBtnRemove () {
        Button btn = createMyButton("", FontImage.MATERIAL_DELETE);
        btn.addActionListener((ActionListener) (ActionEvent evt) -> {
            myLblImage.clearImage();
        });
        return btn;
    }
        
    public final int USER_ACTION_CONFIRM = 0;
    public final int USER_ACTION_CANCEL = 1;
    private int userAction = USER_ACTION_CANCEL; // Default action : Cancel
    public int getUserAction() {
        return this.userAction;
    }
    
    private Button createBtnConfirm() {
        Button btn = new Button(getIwTranslation(TT_CONFIRM));
        btn.addActionListener((ActionListener) (ActionEvent evt) -> {
            // back to template edition
            userAction = USER_ACTION_CONFIRM;
            getBackCommand().actionPerformed(null);
        });
        return btn;
    }
    
    private Button createBtnCancel() {
        Button btn = new Button(getIwTranslation(TT_CANCEL));
        btn.addActionListener((ActionListener) (ActionEvent evt) -> {
            // back to template edition
            userAction = USER_ACTION_CANCEL;
            getBackCommand().actionPerformed(null);
        });
        return btn;
    }
    
    private void getImagefromGallery() {
        
        ActionListener myActionListener = (ActionListener) (ActionEvent evt) -> {
            if (evt != null) {                
                String photoPath = (String) evt.getSource();
                if (photoPath != null && !".".equals(photoPath.trim())) {
                    myLblImage.setImage(photoPath);
                }                              
            }
        };

        Display.getInstance().openGallery(
            myActionListener,
            Display.GALLERY_IMAGE);                                                
                
    }
    
    private String getImageFromCamera() {
        String photoPath = takePicure();
        //Dialog.show("", photoPath, "OK", null);
        return photoPath;
    }
            
    private String takePicure() {    
        return
            Capture.capturePhoto(
                Brain.getInstance().getIwMobileImageWidth(), //480 Old Value (constant)
                -1
            );
    }
    
    private Button createMyButton(String text, char fontImageChar) { 
        
        FontImage fImg = 
            FontImage.createMaterial(
                fontImageChar,
                UIManager.getInstance().getComponentStyle("Button")
            );
        
        Button btn =
                $(new Button(getIwTranslation(text))) 
            .setIcon(fImg)
            .setMargin(10)
            .asComponent(Button.class);         
        //btn.getStyle().setAlignment(LEFT);
        btn.getAllStyles().setAlignment(CENTER);
        //btn.getAllStyles().setBorder(Border.createLineBorder(1, 0x000000));
        return btn;
    }    
        
    class MyLabelImage extends Label {

        String pathImg;

        public MyLabelImage(String pathImg) {
            this.pathImg = pathImg;
            setImage(pathImg);
        }

        public void setImage(String pathImg) {


            if (pathImg == null
                || pathImg.trim().equals("")
                || pathImg.trim().equals(".")) {

                clearImage();
                return;

            }

            this.pathImg = pathImg;

            Image img = null;
            try {
                img = Image.
                      createImage(pathImg).
                      scaledWidth(Brain.getInstance().getIwMobileImageWidth());
            }
            catch (Exception e) {
                System.out.println(
                    Brain.LOG_PREFIX
                    + " - IwFormSelectPhoto_new : " 
                    + e.getMessage()
                    );
            }
            this.setIcon(img);                

        }

        public String getImagePath() {
            return this.pathImg;
        }

        private void clearImage() {
            this.pathImg = ".";
            this.setIcon(null);
        }

    }
            
}




