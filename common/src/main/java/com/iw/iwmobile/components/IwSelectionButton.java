/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iw.iwmobile.components;

import com.codename1.components.MultiButton;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 *
 * @author helio
 */
public class IwSelectionButton extends IwButton {
    
    private final String OBJECT_SELECTED = "objectSelected";
    
    private final String noSelectionText;
    private final String back;
    private ArrayList<Object> objList; 
    private final Form backForm;

    //private MultiList mlOptions;
    Object selectObject;
    
    public IwSelectionButton(
            String noSelectionText,
            String backText,
            Form backForm) {
        
        super(noSelectionText, (Image) null);
        
        this.noSelectionText = noSelectionText;
        this.objList = objList = new ArrayList<Object>();
        this.backForm = backForm;
        this.back = backText;
        setText(noSelectionText);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                MyFormSeletion f = new MyFormSeletion();
                Command backCommand = new Command(back) {
                    @Override
                    public void actionPerformed(ActionEvent ev) {
                        getBackForm().showBack();
                    } 
                }; 
                f.setBackCommand(backCommand);
                f.show();
            }
        });
    }

    public IwSelectionButton(
            String noSelectionText,
            String backText,
            Form backForm,
            ArrayList<Object> objList ) {
        
        super(noSelectionText, (Image) null);
        
        this.noSelectionText = noSelectionText;
        this.objList = objList;
        this.backForm = backForm;
        this.back = backText;
        setText(noSelectionText);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                MyFormSeletion f = new MyFormSeletion();
                Command backCommand = new Command(back) {
                    @Override
                    public void actionPerformed(ActionEvent ev) {
                        getBackForm().showBack();
                    } 
                }; 
                f.setBackCommand(backCommand);
                f.show();
                f.repaint();
            }
        });
    }
    
    private Form getBackForm() {
        return this.backForm;
    }
    
    
//    @Override
//    public void addActionListener(ActionListener l) {
//       
//        MyFormSeletion f = new MyFormSeletion();
//        Command backCommand = new Command(back) {
//            @Override
//            public void actionPerformed(ActionEvent ev) {
//                 backForm.show();
//            } 
//        }; 
//        f.setBackCommand(backCommand);
//        f.show();             
//    }
    
    public void addItem(Object obj) {
        this.objList.add(obj);
    }
    
    
    public void setSelectedItem(Object obj) {
        if (obj == null) {
            this.selectObject = null;
            setText(noSelectionText);
        }
        else {
            if (objList.isEmpty()) {
                this.objList.add(obj);
                this.selectObject = obj;
                setText(obj.toString());
            }
            else {
                if (objList.contains(obj)) {
                    this.selectObject = obj;
                    setText(obj.toString());
                }
                else {
                    this.objList.add(obj);
                    this.selectObject = obj;
                    setText(obj.toString());
                }
            }
        }
        
    }   
    public Object getSelectedItem() {
       return this.selectObject;
    }
    
    private Runnable postSelectionThread;
    public void setPostSelectionAction(Runnable task) {
        this.postSelectionThread = task;
    }
    
    
//Esta Classe usa o componente MultiList, o qual começou a apresentar problemas
//após a atualização da CodeNameOne do dia 09/01/2020.
//Por isso foi comentada e passou-se a utilizar o componente MultiButton para
//seleção de itens.
//    class MyFormSeletion extends Form {
//
//        public MyFormSeletion() {
//            preInit();
//            init();
//            postInit();
//            setTitle(noSelectionText);
//        }
//        
//        private void preInit() {
//            mlOptions = createOptionsList();
//        }
//
//        private void init() {
//           Container c = getContentPane();
//           c.setLayout(new BorderLayout());
//           c.addComponent(BorderLayout.CENTER, mlOptions);
//        }
//
//        private void postInit() {
//            mlOptions.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent evt) {
//                    ListModel model = ((MultiList) evt.getSource()).getModel();
//                    int index = model.getSelectedIndex();
//                    HashMap<String,Object> itemMap = (HashMap<String,Object>) model.getItemAt(index);
//                    setSelectedItem(itemMap.get(OBJECT_SELECTED));
//                    System.out.println("Object Selected: " +  selectObject);
//                    if (postSelectionThread != null) {
//                        postSelectionThread.run();
//                    }
//                    backForm.show();
//                }
//            });
//        }
//
//        private MultiList createOptionsList() {
//            MultiList ml = new MultiList();
//            ListModel model = createOptionsListModel();
//            ml.setModel(model);        
//            return ml;
//        }
//
//        private ListModel createOptionsListModel() {
//
//            ArrayList itemList = new ArrayList();
//            HashMap<String,Object> itemMap;
//
//            for(Object obj : objList) {
//
//                String line1 = obj.toString();                    
//
//                itemMap = new HashMap<String,Object>();
//                itemMap.put("Line1", line1);
//                itemMap.put(OBJECT_SELECTED, obj);
//
//                itemList.add(itemMap);
//
//            }
//
//            DefaultListModel lm = new DefaultListModel(itemList);
//
//            return lm;
//
//        }
//    }  
    
    
    class MyFormSeletion extends Form {

        private TreeMap<String,HashMap> tmItems = new TreeMap<>();
                
        public MyFormSeletion() {
            preInit();
            init();
            postInit();
            setTitle(noSelectionText);
        }
        
        private void preInit() {}

        private void init() {
           Container c = getContentPane();
           c.setLayout(new BorderLayout());
           Container cList = createMultiButtonList();
           c.addComponent(BorderLayout.CENTER, cList);
           c.forceRevalidate();
        }

        private void postInit() {}

        private Container createMultiButtonList(){
            Container cList = new Container(new BoxLayout(BoxLayout.Y_AXIS)); 
            cList.setScrollableY(true);

            tmItems = new TreeMap<>();
            ArrayList itemList = new ArrayList();
            HashMap<String,Object> itemMap;

            for(Object obj : objList) {

                String line1 = obj.toString();
                
                if (tmItems.containsKey(line1)) continue; //avoid repetition error
                
                itemMap = new HashMap<String,Object>();
                
                itemMap.put("Line1", line1);
                itemMap.put(OBJECT_SELECTED, obj);
                itemList.add(itemMap);
                
                tmItems.put(line1,itemMap);

                MultiButton btn = new MultiButton();
                btn.setTextLine1(line1);
                btn.setName(line1);
                cList.add(btn);

                btn.addActionListener((evt) -> {
                    processSelection(btn);
                });

            }

            return cList;
        }
        
        private void processSelection(MultiButton btn){
            HashMap<String,Object> itemMap = (HashMap<String,Object>)tmItems.get(btn.getName());
            setSelectedItem(itemMap.get(OBJECT_SELECTED));
            System.out.println("Object Selected: " +  selectObject);
            if (postSelectionThread != null) {
                postSelectionThread.run();
            }
            backForm.show();
        }            
    }    
    
}
