package com.iw.iwmobile;

import com.codename1.io.*;
import com.codename1.io.URL;
import com.codename1.system.Lifecycle;
import com.codename1.ui.Button;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.webserver.WebServer;
import com.iw.iwmobile.comm.IwHttpRequesterCallBack;
import com.iw.iwmobile.comm.MobRecordset;
import com.iw.iwmobile.comm.MobRecordsetError;
import com.iw.iwmobile.extensions._fakeServerResponses.FakeGetEvolutions;
import com.iw.iwmobile.extensions.evolution.IwFormEvolutionNavig;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename One</a> for the purpose
 * of building native mobile applications using Java.
 */
public class IwEvolutionLeakMemoApp extends Lifecycle {

    @Override
    public void init(Object context) {
        super.init(context);

        // creates a local webserver
        // it is used to allow the Code Name One WebComponent provide all images locally
        // during IW medical evolutions editions.
        // Initially the images were saved in file system. On Android it works fine but in iOS
        // the WebComponent presented problems trying to access files. We create that approach
        // that uses a local web services to overcame that.
        // If the Code Name One team has any better solution, we are open to receiving suggestions.
        // We believe this local web server overcharges our app.
        try {
            initLocalWebServer();
        }
        catch (IOException ex) {
            Log.e(ex);
            Log.p("initWebsite "+ex.getMessage());
        }

        webServerRootPath = new File("httpdocs").getAbsolutePath();
//        server = new WebServer(webServerRootPath, 8888);
    }

    private WebServer server;
    private String webServerRootPath;
    private void initLocalWebServer() throws IOException {
        FileSystemStorage fs = FileSystemStorage.getInstance();
        File docRoot = new File("httpdocs");
        delTree(docRoot);
        docRoot.mkdir();
        // Creates fake index.tml file in web server root (thi is only for testing)
        String helloContent = "<!doctype html><html><head><title>Hello</title></head><body><h1>Hello World</h1><h1>Hello World</h1><h1>Hello World</h1><h1>Hello World</h1></body></html>";
        File indexHtml = new File(docRoot, "index.html");
        writeStringToFile(indexHtml, helloContent);

    }

    private void writeStringToFile(File file, String content) throws IOException {
        FileSystemStorage fs = FileSystemStorage.getInstance();
        try (OutputStream os = fs.openOutputStream(file.getAbsolutePath())) {
            Util.copy(new ByteArrayInputStream(content.getBytes("UTF-8")), os);
        }
    }

    private void delTree(File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                delTree(f);
            }
        }
        file.delete();
    }

//    public void runApp_standard() {
//        Form hi = new Form("Hi World", BoxLayout.y());
//
//        Button helloButton = new Button("Hello World");
//        hi.add(helloButton);
//        helloButton.addActionListener(e -> hello());
//        hi.getToolbar().addMaterialCommandToSideMenu("Hello Command",
//        FontImage.MATERIAL_CHECK, 4, e -> hello());
//        hi.show();
//    }

//    @Override
//    public void runApp() {
//        Form hi = new Form("Hi World", new GridLayout(2,1));
//        BrowserComponent browser = new BrowserComponent();
//        hi.add(browser);
//        hi.add(createHelloButton(browser));
//        hi.show();
//    }

    @Override
    public void runApp() {

        // for tests
//        IwHttpRequesterCallBack<Map<String, MobRecordset>> callback =
//                new IwHttpRequesterCallBack<Map<String,MobRecordset>>() {
//                    @Override
//                    public void onFailure(MobRecordsetError rsError) {
//                        Dialog.show("Alert", rsError.getTranslation(), "OK", null);
//                    }
//
//                    @Override
//                    public void onSuccess(Map<String,MobRecordset> resultMap) {
//                        Dialog.show("Alert", resultMap.toString(), "OK", null);
//                    }
//                };
//        new FakeGetEvolutions().execute(callback);

        Form f = new IwFormEvolutionNavig("898", "INCOWAY T", false, true, 1);
        f.show();
    }

    private Button createHelloButton (BrowserComponent bc) {
        Button btn = new Button("Load local hello page");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    bc.setURL(new URL("http://localhost:8888/index.html"));
                    bc.revalidate();

                } catch (Exception e) {
                    System.out.println("error:" + e.getMessage());
                }
            }
        });
    return btn;
    }

    private void hello() {
        Dialog.show("Hello Codename One", "Welcome to Codename One", "OK", null);
    }

}
