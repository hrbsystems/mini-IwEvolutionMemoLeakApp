package com.iw.iwmobile.components;

import com.codename1.ui.TextArea;
import com.codename1.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 *
 * @author helio
 */
public class IwFreeTextEditor extends TextArea {
    
    
    final private String initContent;
    HashMap hmHTMLToTextCodif = new HashMap();

    public IwFreeTextEditor(String html2Edit) {
        super();
        initializeHashMapCodif();
        this.initContent = onlyPlainText(html2Edit);
        this.setGrowByContent(true);
        this.setMaxSize(4000);
        this.setText(this.initContent);
    }
        
    private String onlyPlainText(String text) {
        String s0 = StringUtil.replaceAll(text, "<p>", "");
        String s1 = StringUtil.replaceAll(s0, "</p>", "\n");
        String s2 = StringUtil.replaceAll(s1, "&#160;", " ");
        
        Set<String> keys = hmHTMLToTextCodif.keySet();
        for (String key : keys){                
            String value = (String)hmHTMLToTextCodif.get(key);
            if (value.equals("")||value.equals(" ")||value.equals("\\")||value.equals(";")) continue;
            s2 = StringUtil.replaceAll(s2, key, value );
        }        
        
        s2 = StringUtil.replaceAll(s2, "&amp;", "&" );
        
        boolean beof = false;
        while (!beof) {
            if (s2.indexOf("<p ")>-1&&s2.indexOf("\">")>-1){
                int cstartIndex = s2.indexOf("<p ");
                int cendIndex = s2.indexOf("\">",cstartIndex);
                StringBuffer sb = new StringBuffer(s2);
                StringBuffer sb1 = sb.delete(cstartIndex,cendIndex+2);
                s2 = sb1.toString();
            }
            else{
                beof = true;
            }
        }
        
        return s2;
    }

    public String getEditedHtml() {
        
        String s1 = getText();
        Set<String> keys = hmHTMLToTextCodif.keySet();
        for (String key : keys){                
            String value = (String)hmHTMLToTextCodif.get(key);
            if (value.equals("")||value.equals(" ")||value.equals("\\")||value.equals(";")) continue;
            s1 = StringUtil.replaceAll(s1, value, key);
        }        

        List<String> paragraphs= StringUtil.tokenize(s1, "\n");
        String resp = "";
        for (String p : paragraphs) {
            resp += "<p>" + p + "</p>";
        }
        
        return resp;
    }
    
    
    private void initializeHashMapCodif(){
        hmHTMLToTextCodif.put( "&#033;", "!");
        hmHTMLToTextCodif.put( "&#034;", "\"");
        //hmHTMLToTextCodif.put( "&#035;", "#");
        hmHTMLToTextCodif.put( "&#036;", "$");
        hmHTMLToTextCodif.put( "&#037;", "%");
        //hmHTMLToTextCodif.put( "&#038;", "&");
        hmHTMLToTextCodif.put( "&#039;", "'");
        hmHTMLToTextCodif.put( "&#040;", "(");
        hmHTMLToTextCodif.put( "&#041;", ")");
        hmHTMLToTextCodif.put( "&#042;", "*;");
        hmHTMLToTextCodif.put( "&#043;", "+");
        hmHTMLToTextCodif.put( "&#044;", ",");
        hmHTMLToTextCodif.put( "&#045;", "-");
        hmHTMLToTextCodif.put( "&#046;", ".");
        hmHTMLToTextCodif.put( "&#047;", "/");
        hmHTMLToTextCodif.put( "&#058;", ":");
        hmHTMLToTextCodif.put( "&#059;", ";");
        hmHTMLToTextCodif.put( "&#060;", "<");
        hmHTMLToTextCodif.put( "&#061;", "=");
        hmHTMLToTextCodif.put( "&#062;", ">");
        hmHTMLToTextCodif.put( "&#063;", "?");
        hmHTMLToTextCodif.put( "&#064;", "@");
        hmHTMLToTextCodif.put( "&#091;", "[");
        hmHTMLToTextCodif.put( "&#092;", "\\");
        hmHTMLToTextCodif.put( "&#093;", "]");
        hmHTMLToTextCodif.put( "&#094;", "^");
        hmHTMLToTextCodif.put( "&#095;", "_");
        hmHTMLToTextCodif.put( "&#096;", "`");
        hmHTMLToTextCodif.put( "&#123;", "{");
        hmHTMLToTextCodif.put( "&#124;", "|");
        hmHTMLToTextCodif.put( "&#125;", "}");
        hmHTMLToTextCodif.put( "&#126;", "~");
        hmHTMLToTextCodif.put( "&#162;", "??");
        hmHTMLToTextCodif.put( "&#163;", "??");
        hmHTMLToTextCodif.put( "&#164;", "??");
        hmHTMLToTextCodif.put( "&#165;", "??");
        hmHTMLToTextCodif.put( "&#166;", "??"); 
        hmHTMLToTextCodif.put( "&#167;", "??");
        hmHTMLToTextCodif.put( "&#168;", "??");
        hmHTMLToTextCodif.put( "&#169;", "??");
        hmHTMLToTextCodif.put( "&#170;", "??");
        hmHTMLToTextCodif.put( "&#171;", "??");
        hmHTMLToTextCodif.put( "&#172;", "??");
        hmHTMLToTextCodif.put( "&#173;", "&shy;");
        hmHTMLToTextCodif.put( "&#174;", "??");
        hmHTMLToTextCodif.put( "&#175;", "??");
        hmHTMLToTextCodif.put( "&#176;", "??");
        hmHTMLToTextCodif.put( "&#177;", "??");
        hmHTMLToTextCodif.put( "&#178;", "??");
        hmHTMLToTextCodif.put( "&#179;", "??");
        hmHTMLToTextCodif.put( "&#180;", "??");
        hmHTMLToTextCodif.put( "&#181;", "??");
        hmHTMLToTextCodif.put( "&#182;", "??");
        hmHTMLToTextCodif.put( "&#183;", "??");
        hmHTMLToTextCodif.put( "&#184;", "??");
        hmHTMLToTextCodif.put( "&#185;", "??");
        hmHTMLToTextCodif.put( "&#186;", "??");
        hmHTMLToTextCodif.put( "&#187;", "??");
        hmHTMLToTextCodif.put( "&#188;", "??");
        hmHTMLToTextCodif.put( "&#189;", "??");
        hmHTMLToTextCodif.put( "&#190;", "??");
        hmHTMLToTextCodif.put( "&#191;", "&iquest;");
        hmHTMLToTextCodif.put( "&#192;", "??");
        hmHTMLToTextCodif.put( "&#193;", "??");
        hmHTMLToTextCodif.put( "&#194;", "??");
        hmHTMLToTextCodif.put( "&#195;", "??");
        hmHTMLToTextCodif.put( "&#196;", "??");
        hmHTMLToTextCodif.put( "&#197;", "??");
        hmHTMLToTextCodif.put( "&#198;", "??");
        hmHTMLToTextCodif.put( "&#199;", "??");
        hmHTMLToTextCodif.put( "&#200;", "??");
        hmHTMLToTextCodif.put( "&#201;", "??");
        hmHTMLToTextCodif.put( "&#202;", "??");
        hmHTMLToTextCodif.put( "&#203;", "??");
        hmHTMLToTextCodif.put( "&#204;", "??");
        hmHTMLToTextCodif.put( "&#205;", "??");
        hmHTMLToTextCodif.put( "&#206;", "??");
        hmHTMLToTextCodif.put( "&#207;", "??");
        hmHTMLToTextCodif.put( "&#208;", "??");
        hmHTMLToTextCodif.put( "&#209;", "??");
        hmHTMLToTextCodif.put( "&#210;", "??");
        hmHTMLToTextCodif.put( "&#211;", "??");
        hmHTMLToTextCodif.put( "&#212;", "??");
        hmHTMLToTextCodif.put( "&#213;", "??");
        hmHTMLToTextCodif.put( "&#214;", "??");
        hmHTMLToTextCodif.put( "&#215;", "??");
        hmHTMLToTextCodif.put( "&#216;", "??");
        hmHTMLToTextCodif.put( "&#217;", "??");
        hmHTMLToTextCodif.put( "&#218;", "??");
        hmHTMLToTextCodif.put( "&#219;", "??");
        hmHTMLToTextCodif.put( "&#220;", "??");
        hmHTMLToTextCodif.put( "&#221;", "??");
        hmHTMLToTextCodif.put( "&#222;", "??");
        hmHTMLToTextCodif.put( "&#223;", "??");
        hmHTMLToTextCodif.put( "&#224;", "??");
        hmHTMLToTextCodif.put( "&#225;", "??");
        hmHTMLToTextCodif.put( "&#226;", "??");
        hmHTMLToTextCodif.put( "&#227;", "??");
        hmHTMLToTextCodif.put( "&#228;", "??");
        hmHTMLToTextCodif.put( "&#229;", "??");
        hmHTMLToTextCodif.put( "&#230;", "??");
        hmHTMLToTextCodif.put( "&#231;", "??");
        hmHTMLToTextCodif.put( "&#232;", "??");
        hmHTMLToTextCodif.put( "&#233;", "??");
        hmHTMLToTextCodif.put( "&#234;", "??");
        hmHTMLToTextCodif.put( "&#235;", "??");
        hmHTMLToTextCodif.put( "&#236;", "??");
        hmHTMLToTextCodif.put( "&#237;", "??");
        hmHTMLToTextCodif.put( "&#238;", "??");
        hmHTMLToTextCodif.put( "&#239;", "??");
        hmHTMLToTextCodif.put( "&#240;", "??");
        hmHTMLToTextCodif.put( "&#241;", "??");
        hmHTMLToTextCodif.put( "&#242;", "??");
        hmHTMLToTextCodif.put( "&#243;", "??");
        hmHTMLToTextCodif.put( "&#244;", "??");
        hmHTMLToTextCodif.put( "&#245;", "??");
        hmHTMLToTextCodif.put( "&#246;", "??");
        hmHTMLToTextCodif.put( "&#247;", "??");
        hmHTMLToTextCodif.put( "&#248;", "??");
        hmHTMLToTextCodif.put( "&#249;", "??");
        hmHTMLToTextCodif.put( "&#250;", "??");
        hmHTMLToTextCodif.put( "&#251;", "??");
        hmHTMLToTextCodif.put( "&#252;", "??");
        hmHTMLToTextCodif.put( "&#253;", "??");
        hmHTMLToTextCodif.put( "&#254;", "??");
        hmHTMLToTextCodif.put( "&#255;", "??");        
                
    }
    
    
//    private String onlyPlainText_Old(String text) {
//        String s1 = StringUtil.replaceAll(text, "<p>", "");
//        String s2 = StringUtil.replaceAll(s1, "</p>", "\n");
//        return s2;
//    }
//    
//    public String getEditedHtml_Old() {
//        List<String> paragraphs= StringUtil.tokenize(getText(), "\n");
//        String resp = "";
//        for (String p : paragraphs) {
//            resp += "<p>" + p + "</p>";
//         }
//        return resp;
//    }
    
}
