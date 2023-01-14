/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iw.iwmobile.extensions;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.iw.iwmobile.Brain;
import com.iw.iwmobile.comm.IwExtContextInterface;
import com.iw.iwmobile.comm.MobField;
import com.iw.iwmobile.comm.MobRecordset;
import com.iw.iwmobile.comm.MobRow;
import com.iw.iwmobile.components.IwButton;
import com.iw.iwmobile.forms.IwFormBase;

import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Gustavo
 */
public class IwDialogReSeqDiagnostic extends IwFormBase {
    public final int USER_ACTION_DONOTHING = -1;
    private int userAction = USER_ACTION_DONOTHING; // Default Action
    private final Button btnCancel;
    private final Button btnOk;
    private Container pnlCenter;
    private MobRecordset rsSource;
    private Container diagnosticList;
    private HashMap hmDiagnosticList = new HashMap();
    private IwExtContextInterface xCTx;
    
    public IwDialogReSeqDiagnostic(IwExtContextInterface xCTx, String title) {    
        super();
        this.xCTx = xCTx;
        setTitle(title);
        
        btnCancel = createBtnCancel();
        btnOk = createBtnOk();
        Button btSeqUp = createSeqUp_Button();
        Button btSeqDown = createSeqDown_Button();

        pnlCenter = new Container(BoxLayout.y());
        pnlCenter.setScrollableY(true);
        pnlCenter.setScrollableX(false);
        
        Container southContainer = new Container(new BorderLayout());
        Container southButtons = new Container(new GridLayout(1,4));
        southButtons.addComponent(btSeqUp);
        southButtons.addComponent(btSeqDown);
        southButtons.addComponent(btnOk);
        southButtons.addComponent(btnCancel);
        
        southContainer.addComponent(BorderLayout.CENTER,southButtons);
        
        setLayout(new BorderLayout());
        addComponent(BorderLayout.CENTER, pnlCenter);
        addComponent(BorderLayout.SOUTH, southContainer);  
        
    }    
    
    private Button createBtnOk() {
        Button btn = new Button(getIwTranslation("Ok"));
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                userAction = 1; 
                Display.getInstance().getCurrent().getBackCommand().actionPerformed(null);
            }
        });
        return btn;
    }

    private Button createBtnCancel() {
        Button btn = new Button(getIwTranslation("Sair"));
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                userAction = USER_ACTION_DONOTHING;
                Display.getInstance().getCurrent().getBackCommand().actionPerformed(null);
            }
        });
        return btn;
    }
    
    
    private Button createSeqUp_Button() {
        Image searchImg = Brain.getInstance().getImage(Brain.IMAGE_ARROW_UP);
        
        Button btn = new IwButton();
        btn.setIcon(searchImg);
        btn.setVisible(true); // Initial state
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Container cSelected = null;
                for (int i=0;i<diagnosticList.getComponentCount();i++){
                    Container ci = (Container)diagnosticList.getComponentAt(i);
                    CheckBox chki = (CheckBox)ci.getComponentAt(0);
                    if (chki.isSelected()){
                        cSelected = (Container)diagnosticList.getComponentAt(i);
                        break;
                    }
                }
                if (cSelected==null) return;
                
                int index = diagnosticList.getComponentIndex(cSelected);
                if (index>0){
                    Container cItemBefore = (Container)diagnosticList.getComponentAt(index-1);
                    int newindex = index-1;
                    Container newContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                    for (int i=0;i<diagnosticList.getComponentCount();i++){
                        if (i==newindex){
                            newContainer.add(createItemContainer(null,cSelected));
                            newContainer.add(createItemContainer(null,cItemBefore));
                            i++;
                        }
                        else{
                            newContainer.add(createItemContainer(null,(Container)diagnosticList.getComponentAt(i)));
                        }
                    }
                    pnlCenter.removeAll();
                    diagnosticList = newContainer;
                    pnlCenter.add(diagnosticList);
                    getContentPane().forceRevalidate();
                }
            }
        });
        return btn;
    }

    private Button createSeqDown_Button() {
        Image searchImg = Brain.getInstance().getImage(Brain.IMAGE_ARROW_DOWN);
        
        Button btn = new IwButton();
        btn.setIcon(searchImg);
        btn.setVisible(true); // Initial state
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Container cSelected = null;
                for (int i=0;i<diagnosticList.getComponentCount();i++){
                    Container ci = (Container)diagnosticList.getComponentAt(i);
                    CheckBox chki = (CheckBox)ci.getComponentAt(0);
                    if (chki.isSelected()){
                        cSelected = (Container)diagnosticList.getComponentAt(i);
                        break;
                    }
                }
                if (cSelected==null) return;
                
                int lastIndex = diagnosticList.getComponentCount()-1;
                int index = diagnosticList.getComponentIndex(cSelected);
                if (index<lastIndex){
                    Container cItemAfter = (Container)diagnosticList.getComponentAt(index+1);
                    int newindex = index+1;
                    Container newContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                    for (int i=diagnosticList.getComponentCount()-1;i>=0;i--){
                        if (i==newindex){
                            newContainer.add(createItemContainer(null,cSelected));
                            newContainer.add(createItemContainer(null,cItemAfter));
                            i--;
                        }
                        else{
                            newContainer.add(createItemContainer(null,(Container)diagnosticList.getComponentAt(i)));
                        }
                    }
                    pnlCenter.removeAll();
                    //invert all
                    diagnosticList = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                    for (int i=newContainer.getComponentCount()-1;i>=0;i--){
                        diagnosticList.add(createItemContainer(null,(Container)newContainer.getComponentAt(i)));
                    }
                    pnlCenter.add(diagnosticList);
                    getContentPane().forceRevalidate();
                }
            }
        });
        return btn;
    }
    
    public int getUserAction() {
        return this.userAction;
    }

    public void setSourceRecordset(MobRecordset rs){
        this.rsSource = rs;
        showSearchResults();
    }

    public MobRecordset getReSequenciedRecordset(){
        MobRecordset rsResult = new MobRecordset();
        for (int i=0;i<diagnosticList.getComponentCount();i++){
            Container cItem = (Container)diagnosticList.getComponentAt(i);
            Label labelID = (Label)cItem.getComponentAt(4);
            
            long IDItem = Long.parseLong(labelID.getText());

            MobField fID = new MobField("ID",MobField.LONG,IDItem);
            MobField fSeq = new MobField("SEQ",MobField.INTEGER,i+1);
            MobField fNewRow = new MobField("_NEWROW",MobField.INTEGER,0);
            MobField fKeyName = new MobField("_KEYNAME",MobField.STRING,"ID");
            
            MobRow r = new MobRow();
            r.addField(fID);
            r.addField(fSeq);
            r.addField(fNewRow);
            r.addField(fKeyName);
            rsResult.addRow(r);
                
        }       
        
        
        return rsResult;
    }
    
    private void showSearchResults(){
        if (rsSource.rows.size()>0){
            diagnosticList = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            for(MobRow r : rsSource.rows) {
                Long ID = (Long)r.field("ID").objValue();
                String ScDiagnostic = r.field("SCDIAGNOSTIC").getValue();
                String ScDiagnosticName = r.field("SCDIAGNOSTICNAME").getValue();
                String AlternateName = r.field("ALTERNATENAME").getValue();
                Integer Seq = (Integer)r.field("SEQ").objValue();
                Object sinceDate = r.field("SINCEDATE").objValue();
                String strSinceDate = "";
                if (sinceDate!=null){
                    strSinceDate = Brain.getInstance().fmtDateTime((Date)sinceDate);
                }
                Object untilDate = r.field("UNTILDATE").objValue();
                String strUntilDate = "";
                if (untilDate!=null){
                    strUntilDate = Brain.getInstance().fmtDateTime((Date)untilDate);
                }

                Container cItem = createItemContainer(r,null);
                        
                diagnosticList.add(cItem);
                hmDiagnosticList.put(ID.longValue(),Seq.intValue());

            }  
            diagnosticList.setScrollableX(false);
            
            pnlCenter.add(diagnosticList);
        }
        
    }
    
    private Container createItemContainer(MobRow r, Container c){
        
        Container cItem = new Container(new GridLayout(4,1));
        String ScDiagnostic = null;
        String ScDiagnosticName = null;
        String AlternateName = null;
        Integer Seq = null;
        Long ID = null;
        Object sinceDate = null;
        Object untilDate = null;
        String strSinceDate = "";
        String strUntilDate = "";
        CheckBox line1 = null;
        Label line2 = null;
        Label line3 = null;
        Label line4 = null;
        Label line5 = null;
        
        if (r!=null){
            ID = (Long)r.field("ID").objValue();
            ScDiagnostic = r.field("SCDIAGNOSTIC").getValue();
            ScDiagnosticName = r.field("SCDIAGNOSTICNAME").getValue();
            AlternateName = r.field("ALTERNATENAME").getValue();
            Seq = (Integer)r.field("SEQ").objValue();
            sinceDate = r.field("SINCEDATE").objValue();
            strSinceDate = "";
            if (sinceDate!=null){
                strSinceDate = Brain.getInstance().fmtDateTime((Date)sinceDate);
                strSinceDate = strSinceDate.substring(0,strSinceDate.indexOf(" "));
            }
            untilDate = r.field("UNTILDATE").objValue();
            strUntilDate = "";
            if (untilDate!=null){
                strUntilDate = Brain.getInstance().fmtDateTime((Date)untilDate);
                strUntilDate = strUntilDate.substring(0,strUntilDate.indexOf(" "));
            }
            line1 = new CheckBox(" "+Seq.toString());
            line1.setOppositeSide(false);
            
            line1.getStyle().setFont(Font.createSystemFont(Font.FACE_PROPORTIONAL,
                    Font.STYLE_BOLD,
                    Font.SIZE_LARGE));

            if (AlternateName==null) AlternateName = "";

            line2 = new Label(ScDiagnosticName+"["+ScDiagnostic+"]"+"["+AlternateName+"]");
            
            String strline3 = "Data Início: "+strSinceDate;
            line3 = new Label(strline3);

            String strline4 = "Data Resolução: "+strUntilDate;
            line4 = new Label(strline4);

            line5 = new Label(ID.toString());
            line5.getStyle().setFgColor(line5.getStyle().getBgColor());
            
        }
        else{
            CheckBox ln1 = (CheckBox)c.getComponentAt(0);
            line1 = new CheckBox(ln1.getText());
            line1.setOppositeSide(false);
            
            line1.getStyle().setFont(Font.createSystemFont(Font.FACE_PROPORTIONAL,
                Font.STYLE_BOLD,
                Font.SIZE_LARGE));
            line1.setSelected(ln1.isSelected());
            
            line2 = new Label(((Label)c.getComponentAt(1)).getText());
            line3 = new Label(((Label)c.getComponentAt(2)).getText());
            line4 = new Label(((Label)c.getComponentAt(3)).getText());
            line5 = new Label(((Label)c.getComponentAt(4)).getText());
            line5.getStyle().setFgColor(line5.getStyle().getBgColor());
            
        }


        cItem.addComponent(line1);
        cItem.addComponent(line2);
        cItem.addComponent(line3);
        cItem.addComponent(line4);
        cItem.addComponent(line5);
        
        line1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                for(int i =0;i<diagnosticList.getComponentCount();i++) {
                    Container c = (Container)diagnosticList.getComponentAt(i);
                    CheckBox cx = (CheckBox)c.getComponentAt(0);
                    Label lbID = (Label)c.getComponentAt(4);
                    Container cevt = (Container)evt.getComponent().getParent();
                    Label lbevt = (Label)cevt.getComponentAt(4);
                    if (cx.isSelected()&&!lbID.getText().equals(lbevt.getText())){
                        cx.setSelected(false);
                    }
                }
            }
        });
        
        cItem.getStyle().setBorder(Border.createLineBorder(1));
        cItem.getStyle().setMarginUnit(Style.UNIT_TYPE_DIPS);
        cItem.getStyle().setMargin(cItem.BOTTOM, 2);                
        
        return cItem;
    }
}
