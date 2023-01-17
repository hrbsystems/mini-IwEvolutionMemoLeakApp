/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iw.iwmobile.components;

import com.codename1.components.Accordion;
import com.codename1.components.SpanLabel;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.UIManager;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author hrugani
 
 Class that substitutes a ComboBox with multiple selection.
 "Codename One" ComboxBox is a class that must be avoided
 Advertisement from Blog Site of "Codename One".
 
 IMPORTANT: The Class definition of items must implements
 toString() method. This method will be called to show
 respective item description allowing users select one of them.
 * @param <T>
 * 
 */
public class IwAccrMultiSelection<T> extends Accordion {

    private ArrayList<T> selectedItems;
    private ArrayList<T> items; 
    
    // for multiple selecions
    private ArrayList<CheckBox> chkItems = new ArrayList<>();
    
    // when MaxOneSelecion = true (use RadioButtons)
    private ArrayList<RadioButton> rbItems = new ArrayList<>();
    
    final private String label;
    private MyHeader cHeader;
    private MyBody cBody;
        
    public IwAccrMultiSelection(String label) {
        
        super();
        this.label = label;
        this.items = new ArrayList<T>();
        this.selectedItems = new ArrayList<T>();
        this.cHeader = new MyHeader();
        this.cBody = new MyBody(); 
        addContent(cHeader, cBody);
        refresh();
        
    }
    
    public IwAccrMultiSelection(
            String label,
            T[] selectedItems,
            T[] items) {

        this.label = label;
        
        this.items = new ArrayList<T>();
        if (items != null) {
            this.items.addAll(Arrays.asList(items));
        }
        
        this.selectedItems = new ArrayList<T>();
        if (selectedItems != null) {
            this.selectedItems.addAll(Arrays.asList(selectedItems));        
        }
        
        this.cHeader = new MyHeader();
        this.cBody = new MyBody(); 
        
        addContent(cHeader, cBody);
        
        refresh();
                
    }
    
    boolean bMaxOneSelection = false; // default - allows more than one selection
    public void setMaxOneSelection(boolean b) {
        this.bMaxOneSelection = b;
        refresh();
    }
    
    private void refresh() {
        if (this.bMaxOneSelection) {
            refreshUsingRadioButtonItems();
        }
        else {
            refreshUsingCheckBoxeItems();
        }
    }
    
    private void refreshUsingCheckBoxeItems() {
        
        cBody.removeAll();
        cBody.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        chkItems.clear();
        rbItems.clear();
        
        for (T item : items) {
            MyCheckBox chkItem = new MyCheckBox(item);
            chkItems.add(chkItem);
            chkItem.setSelected(selectedItems.contains(item));
            
            MyButtonClose btnItem = new MyButtonClose();
            
            Container cItem = new Container(new BorderLayout());
            cItem.addComponent(BorderLayout.CENTER, chkItem);
            cItem.addComponent(BorderLayout.EAST, btnItem);
            cItem.getAllStyles().setMarginTop(10);
            cItem.getAllStyles().setMarginBottom(10);
            cBody.addComponent(cItem);

        }
        
        cHeader.update();
        
    }

    private void refreshUsingRadioButtonItems() {
        
        cBody.removeAll();
        cBody.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        chkItems.clear();
        rbItems.clear();
        ButtonGroup bg = new ButtonGroup();
        
        for (T item : items) {
            
            MyRadioButton rbItem = new MyRadioButton(item);
            rbItems.add(rbItem);
            rbItem.setSelected(selectedItems.contains(item));
            bg.add(rbItem);
            
            MyButtonClose btnItem = new MyButtonClose();
            
            Container cItem = new Container(new BorderLayout());
            cItem.addComponent(BorderLayout.CENTER, rbItem);
            cItem.addComponent(BorderLayout.EAST, btnItem);
            cItem.getAllStyles().setMarginTop(10);
            cItem.getAllStyles().setMarginBottom(10);
            cBody.addComponent(cItem);

        }
        
        cHeader.update();
        
    }
    
    public void addItem(T item) {
        items.add(item);
        refresh();
    }
    
    public ArrayList<T> getSelectedItems() {
        return selectedItems;
    }

    public int[] getSelectedItemsIndex() {        
        return new int[]{};
    }
    
    public void setSelectedItems(ArrayList<T> items) {
        
        this.selectedItems = new ArrayList<T>();
        
        if (items == null) {
            return;
        }

        for (T item : items) {
            if (this.items.contains(item)) {
                this.selectedItems.add(item);
            } 
        }
        
        refresh();

    }

    public void setSelectedItems(T[] items) {

        this.selectedItems = new ArrayList<T>();

        if (items == null) {
            return;
        }

        for (T item : items) {
            if (this.items.contains(item)) {
                this.selectedItems.add(item);
            } 
        }
        
        refresh();
    }
    
    public void setSelectedItemIndex(int i) {
        T item = items.get(i);
        if (!selectedItems.contains(item)) {
            selectedItems.add(item);
            refresh();
        }
    }
    
    public ArrayList<T> getItems() {
        return items;
    }
    
    public void setItems(ArrayList<T> itens) {
        this.selectedItems = new ArrayList<T>();
        this.items = itens;
        refresh();
    }

    public void setItems(T[] itens) {
        this.selectedItems = new ArrayList<T>();
        ArrayList<T> alItens = new ArrayList<T>();
        alItens.addAll(Arrays.asList(itens));
        this.items = alItens;
        refresh();
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // MyHeader Class definition
    ////////////////////////////////////////////////////////////////////////////
        
    class MyHeader extends Container {

        private final SpanLabel splDesc;
        
        public MyHeader() {
            setLayout(new GridLayout(1,1));
            this.splDesc = new SpanLabel();
            addComponent(this.splDesc);            
        }
        
        public void update()  {
            String s = "";
            if (label != null) {
                s += label + "\n";
            }
            ArrayList<T> selected = getSelectedItems(); 
            if (!selectedItems.isEmpty()) {
                s += selectedItems.size() + " selecionados";
            }
            else {
                s += "Selec. Opções...";
            }
            splDesc.setText(s);
            splDesc.revalidate();
        }
        
    }
    
    
    
    class MyBody extends Container {

        public MyBody() {
        }
        
    }
    
    class MyCheckBox extends CheckBox {

        T item;
        
        public MyCheckBox(T item) {
            super(item.toString());
            this.item = item;
            this.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    T item = MyCheckBox.this.getItem();
                    if (MyCheckBox.this.isSelected()) {
                        if (!selectedItems.contains(item)) {
                            selectedItems.add(item);
                        }
                    }
                    else {
                        if (selectedItems.contains(item)) {
                            selectedItems.remove(item);
                        }
                    }
                    cHeader.update();
                }
            });
        }
        
        public T getItem() {
            return item;
        }
        
        
    }
    
    class MyButtonClose extends Button {

        public MyButtonClose() {
            
            Image upArrowImg = 
                FontImage
                    .createMaterial(
                        FontImage.MATERIAL_ARROW_DROP_UP,
                        UIManager.getInstance().getComponentStyle("Label"));            
            setIcon(upArrowImg);
            
            setUIID("Label");
            
            addActionListener((ActionListener) (ActionEvent evt) -> {
                
                IwAccrMultiSelection.this.collapse(
                    IwAccrMultiSelection.this.getCurrentlyExpanded()
                );
                IwAccrMultiSelection.this.animateLayout(250);
                
            });
        }
        
    }
    
    
    class MyRadioButton extends RadioButton {

        T item;
        
        public MyRadioButton(T item) {
            super(item.toString());
            this.item = item;
            this.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    
                    ArrayList<T> aAux = new ArrayList<>();
                    aAux.add(MyRadioButton.this.getItem());
                    selectedItems = aAux;

                    cHeader.update();
                                        
                }
            });
            getAllStyles().setMargin(20, 20, 0, 0);
        }
        
        public T getItem() {
            return item;
        }
        
        
    }
    
    
    
}
