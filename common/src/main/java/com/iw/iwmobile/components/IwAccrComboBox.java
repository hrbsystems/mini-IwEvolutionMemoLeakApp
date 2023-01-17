/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iw.iwmobile.components;

import com.codename1.components.Accordion;
import com.codename1.components.SpanLabel;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Container;
import com.codename1.ui.RadioButton;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author hrugani
 
 Class that substitutes a ComboBox.
 "Codename One" ComboxBox is a class that must be avoided
 Advertisement from Blog Site of "Codename One".
 
 IMPORTANT: The Class definition of items must implements
 toString() method. This method will be called to show
 respective item description allowing users select one of them.
 * @param <T>
 * 
 */
public class IwAccrComboBox<T> extends Accordion {

    private T selectedItem;
    private ArrayList<T> items;
    
    private final ArrayList<MyRadioButton> rbItems =
            new ArrayList<MyRadioButton>();
    
    final private String label;
    private final MyHeader cHeader;
    private final MyBody cBody;
    
    public IwAccrComboBox(
            String label,
            T selectedItem,
            ArrayList<T> items) {
        
        this.label = label;
        
        if (items == null) {
            this.items = new ArrayList<T>();
        }
        else {
            this.items = items;
        }
        
        this.selectedItem = selectedItem;  
        
        this.cHeader = new MyHeader();
        this.cBody = new MyBody();
        
        addContent(cHeader, cBody);
        
        refresh();
        
                        
    }
  
    
    public IwAccrComboBox(
            String label,
            T selectedItem,
            T[] items) {
        
        this.label = label;
        
        this.items = new ArrayList<T>();
        if (items != null) {
            this.items.addAll(Arrays.asList(items));
        }
        
        this.selectedItem = selectedItem;
        
        this.cHeader = new MyHeader();
        this.cBody = new MyBody();
        
        addContent(cHeader, cBody);
        
        refresh();
                        
    }
            
    ActionListener<ActionEvent> myActionListener;
    public void addActionListener(
            ActionListener<ActionEvent> actionListener) {
        myActionListener = actionListener;
    }
    
    private void refresh() {
        
        cBody.removeAll();
        cBody.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        this.rbItems.clear();
        ButtonGroup bg = new ButtonGroup();
        for (T item : items) {
            MyRadioButton rb = new MyRadioButton(item);
            rb.addActionListener(myActionListener);
            rbItems.add(rb);
            bg.add(rb);
            rb.setSelected(item == selectedItem);
            cBody.addComponent(rb);
        }
        
        cHeader.update();
        
    }

    private void refresh_old() {
        
        T selectedItemSaved =  getSelectedItem();
        
        cBody.removeAll();
        cBody.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        this.rbItems.clear();
        ButtonGroup bg = new ButtonGroup();
        for (T item : items) {
            MyRadioButton rb = new MyRadioButton(item);
            rb.addActionListener(myActionListener);
            rbItems.add(rb);
            bg.add(rb);
            cBody.addComponent(rb);
        }
        
        if (selectedItemSaved != null) {
            setSelectedItem(selectedItemSaved);
        }

        cHeader.update();
        
        revalidate();
    }
    
    
    public void addItem(T item) {
        items.add(item);
        refresh();
    }
    
    public void addItem_old(T item) {
        items.add(item);
        T selectedSaved = getSelectedItem();
        refresh();
        if (selectedSaved != null) {
            setSelectedItem(selectedSaved);
        }
    }
    
    public T getSelectedItem() {
        return selectedItem;
    }

    public T getSelectedItem_old() {
        for (MyRadioButton rb : rbItems) {
            if (rb.isSelected()) {
                return rb.getItem();
            }
        }
        return null;
    }

    public int getSelectedIndex() {
        int index = -1;
        for (T item : items) {
            index++;
            if (item == selectedItem) {
                this.myActionListener.actionPerformed(
                    new ActionEvent(
                        IwAccrComboBox.this,
                        ActionEvent.Type.Other
                    )
                );
                return index;
            }
        }
        return index;
    }
    public int getSelectedIndex_old() {
        int index = -1;
        for (MyRadioButton rb : rbItems) {
            if (rb.isSelected()) {
                index++;
                return index;
            }
        }
        return index;
    }
    
    
    public void setSelectedItem(T item) {
        for (MyRadioButton rb : rbItems) {
            if (item == rb.getItem()) {
                selectedItem = item;
                rb.setSelected(true);
                if (this.myActionListener != null) {
                    this.myActionListener.actionPerformed(
                        new ActionEvent(
                            IwAccrComboBox.this,
                            ActionEvent.Type.Other
                        )
                    );
                }
                cHeader.update();
            }
        }
        
    }
    
    public void setSelectedIndex(int i) {
        if (i < rbItems.size()) {
            rbItems.get(i).setSelected(true);
            selectedItem = rbItems.get(i).getItem();
            if (this.myActionListener != null) {
                this.myActionListener.actionPerformed(
                    new ActionEvent(
                        IwAccrComboBox.this,
                        ActionEvent.Type.Other
                    )
                );
            }
            cHeader.update();
        } 
    }
    
    
    public ArrayList<T> getItems() {
        return items;
    }
    
    public void setItems(ArrayList<T> items) {;
        this.items = items;
        this.selectedItem = null;
        refresh();
    }

    public void setItems(T[] itens) {
        ArrayList<T> alItens = new ArrayList<T>();
        alItens.addAll(Arrays.asList(itens));
        this.selectedItem = null;
        this.items = alItens;
        refresh();
    }
    
        
    class MyHeader extends Container {

        private final SpanLabel splDesc;
        
        public MyHeader() {
            setLayout(new GridLayout(1,1));
            this.splDesc = new SpanLabel();
            addComponent(this.splDesc);
        }
                
        public void update() {
            String s = "";
            if (label != null) {
                s += label + "\n";
            }
            T selected = getSelectedItem(); 
            if ( selected != null) {
                s += selected.toString();
            }
            else {
                s += "Selec. Opção...";
            }
            splDesc.setText(s);
            splDesc.revalidate();
        }
        
    }
    
    class MyBody extends Container {
        
        public MyBody() {
            super();
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
                    
                    selectedItem = MyRadioButton.this.getItem();

                    cHeader.update();
                    
                    //IwAccrComboBox.this.collapse(cBody);
                    IwAccrComboBox.this.collapse(
                        IwAccrComboBox.this.getCurrentlyExpanded()
                    );
                    IwAccrComboBox.this.animateLayout(250);
                    
                    if (myActionListener != null) {
                        myActionListener.actionPerformed(
                            //    evt
                            new ActionEvent(
                                IwAccrComboBox.this,
                                evt.getEventType(),
                                evt.getX(),
                                evt.getY()
                            )
                        );
                    }
                    
                }
            });
            getAllStyles().setMargin(20, 20, 0, 0);
        }
        
        public T getItem() {
            return item;
        }
        
        
    }
    
}
