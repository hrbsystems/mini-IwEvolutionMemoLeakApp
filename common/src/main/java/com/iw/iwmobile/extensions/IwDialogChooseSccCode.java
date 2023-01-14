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
import com.iw.iwmobile.comm.IwExtContextInterface;
import com.iw.iwmobile.comm.MobRecordset;
import com.iw.iwmobile.comm.MobRow;
import com.iw.iwmobile.forms.IwFormBase;

import java.util.HashMap;

import static com.codename1.push.PushContent.setTitle;
import static com.iw.iwmobile.IwConstantsInterface.TT_CANCEL;
import static com.iw.iwmobile.IwConstantsInterface.TT_OK;

/**
 *
 * @author Gustavo
 */
public class IwDialogChooseSccCode extends IwFormBase {
    public final int USER_ACTION_DONOTHING = -1;
    private int userAction = USER_ACTION_DONOTHING; // Default Action
    private final Button btnCancel;
    private final Button btnOk;
    private Container pnlCenter;
    private MobRecordset rsSource;
    private Container cSccCodes;
    private IwExtContextInterface xCTx;
    
    public IwDialogChooseSccCode(IwExtContextInterface xCTx, String title) {    
        super();
        this.xCTx = xCTx;
        setTitle(title);
        
        btnCancel = createBtnCancel();
        btnOk = createBtnOk();

        pnlCenter = new Container(BoxLayout.y());
        pnlCenter.setScrollableY(true);
        pnlCenter.setScrollableX(true);
        
        Container southContainer = new Container(new BorderLayout());
        Container southButtons = new Container(new GridLayout(1,2));
        southButtons.addComponent(btnOk);
        southButtons.addComponent(btnCancel);
        
        southContainer.addComponent(BorderLayout.CENTER,southButtons);
        
        setLayout(new BorderLayout());
        addComponent(BorderLayout.CENTER, pnlCenter);
        addComponent(BorderLayout.SOUTH, southContainer);  
        
    }    
    
    private Button createBtnOk() {
        Button btn = new Button(getIwTranslation(TT_OK));
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
        Button btn = new Button(getIwTranslation(TT_CANCEL));
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                userAction = USER_ACTION_DONOTHING;
                Display.getInstance().getCurrent().getBackCommand().actionPerformed(null);
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

    public HashMap getSelectedItem(){
        HashMap hmResult = null;
        for (int i=0;i<cSccCodes.getComponentCount();i++){
            Container cItem = (Container)cSccCodes.getComponentAt(i);
            CheckBox chk = (CheckBox)cItem.getComponentAt(0);
            if (chk.isSelected()){
                Long ID = Long.parseLong(chk.getText().substring(chk.getText().indexOf(":")+2));
                hmResult = (HashMap)hmSccCodeList.get(ID.longValue());
                break;
            }
        }       
        return hmResult;
    }
    
    private HashMap hmSccCodeList = null;
    private void showSearchResults(){
        if (rsSource.rows.size()>0){
            cSccCodes = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            hmSccCodeList = new HashMap();
            for(MobRow r : rsSource.rows) {
                Long ID = (Long)r.field("ID").objValue();
                String ScCodeName = "";
                if (r.field("CODENAME")==null){
                    ScCodeName = r.field("SCCODENAME").getValue();
                }
                else{
                    ScCodeName = r.field("CODENAME").getValue();
                }
                String AlternateName = r.field("ALTERNATENAME").getValue();
                
                Long IDAlternate = null;
                if (r.field("IDALTERNATE")!=null){
                    IDAlternate = (Long)r.field("IDALTERNATE").objValue();
                }
                Container cItem = new Container(new GridLayout(3,1));

                CheckBox line1 = new CheckBox(" "+"Código: "+ID.toString());
                line1.getStyle().setFont(Font.createSystemFont(Font.FACE_PROPORTIONAL,
                        Font.STYLE_BOLD,
                        Font.SIZE_LARGE));
                line1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        for(int i =0;i<cSccCodes.getComponentCount();i++) {
                            Container c = (Container)cSccCodes.getComponentAt(i);
                            CheckBox cx = (CheckBox)c.getComponentAt(0);
                            if (cx.isSelected()&&!cx.getText().equals(line1.getText())){
                                cx.setSelected(false);
                            }
                        }
                    }
                });
                line1.setOppositeSide(false);
                
                Label line2 = new Label(AlternateName);

                Label line3 = new Label(ScCodeName);

                cItem.addComponent(0,line1);
                cItem.addComponent(1,line2);
                cItem.addComponent(2,line3);
                        
                cItem.getStyle().setBorder(Border.createLineBorder(1));
                cSccCodes.add(cItem);

                HashMap hmSccCodeItem = new HashMap();
                hmSccCodeItem.put("ID", ID);
                hmSccCodeItem.put("SCCODENAME", ScCodeName);
                hmSccCodeItem.put("ALTERNATENAME", AlternateName);
                hmSccCodeItem.put("IDALTERNATE", IDAlternate);
                hmSccCodeList.put(ID.longValue(), hmSccCodeItem);
                
            }  
            cSccCodes.setScrollableX(true);
            pnlCenter.add(cSccCodes);
        }
        
    }
}
