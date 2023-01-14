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
        hmHTMLToTextCodif.put( "&#162;", "¢");
        hmHTMLToTextCodif.put( "&#163;", "£");
        hmHTMLToTextCodif.put( "&#164;", "¤");
        hmHTMLToTextCodif.put( "&#165;", "¥");
        hmHTMLToTextCodif.put( "&#166;", "¦"); 
        hmHTMLToTextCodif.put( "&#167;", "§");
        hmHTMLToTextCodif.put( "&#168;", "¨");
        hmHTMLToTextCodif.put( "&#169;", "©");
        hmHTMLToTextCodif.put( "&#170;", "ª");
        hmHTMLToTextCodif.put( "&#171;", "«");
        hmHTMLToTextCodif.put( "&#172;", "¬");
        hmHTMLToTextCodif.put( "&#173;", "&shy;");
        hmHTMLToTextCodif.put( "&#174;", "®");
        hmHTMLToTextCodif.put( "&#175;", "¯");
        hmHTMLToTextCodif.put( "&#176;", "°");
        hmHTMLToTextCodif.put( "&#177;", "±");
        hmHTMLToTextCodif.put( "&#178;", "²");
        hmHTMLToTextCodif.put( "&#179;", "³");
        hmHTMLToTextCodif.put( "&#180;", "´");
        hmHTMLToTextCodif.put( "&#181;", "µ");
        hmHTMLToTextCodif.put( "&#182;", "¶");
        hmHTMLToTextCodif.put( "&#183;", "·");
        hmHTMLToTextCodif.put( "&#184;", "¸");
        hmHTMLToTextCodif.put( "&#185;", "¹");
        hmHTMLToTextCodif.put( "&#186;", "º");
        hmHTMLToTextCodif.put( "&#187;", "»");
        hmHTMLToTextCodif.put( "&#188;", "¼");
        hmHTMLToTextCodif.put( "&#189;", "½");
        hmHTMLToTextCodif.put( "&#190;", "¾");
        hmHTMLToTextCodif.put( "&#191;", "&iquest;");
        hmHTMLToTextCodif.put( "&#192;", "À");
        hmHTMLToTextCodif.put( "&#193;", "Á");
        hmHTMLToTextCodif.put( "&#194;", "Â");
        hmHTMLToTextCodif.put( "&#195;", "Ã");
        hmHTMLToTextCodif.put( "&#196;", "Ä");
        hmHTMLToTextCodif.put( "&#197;", "Å");
        hmHTMLToTextCodif.put( "&#198;", "Æ");
        hmHTMLToTextCodif.put( "&#199;", "Ç");
        hmHTMLToTextCodif.put( "&#200;", "È");
        hmHTMLToTextCodif.put( "&#201;", "É");
        hmHTMLToTextCodif.put( "&#202;", "Ê");
        hmHTMLToTextCodif.put( "&#203;", "Ë");
        hmHTMLToTextCodif.put( "&#204;", "Ì");
        hmHTMLToTextCodif.put( "&#205;", "Í");
        hmHTMLToTextCodif.put( "&#206;", "Î");
        hmHTMLToTextCodif.put( "&#207;", "Ï");
        hmHTMLToTextCodif.put( "&#208;", "Ð");
        hmHTMLToTextCodif.put( "&#209;", "Ñ");
        hmHTMLToTextCodif.put( "&#210;", "Ò");
        hmHTMLToTextCodif.put( "&#211;", "Ó");
        hmHTMLToTextCodif.put( "&#212;", "Ô");
        hmHTMLToTextCodif.put( "&#213;", "Õ");
        hmHTMLToTextCodif.put( "&#214;", "Ö");
        hmHTMLToTextCodif.put( "&#215;", "×");
        hmHTMLToTextCodif.put( "&#216;", "Ø");
        hmHTMLToTextCodif.put( "&#217;", "Ù");
        hmHTMLToTextCodif.put( "&#218;", "Ú");
        hmHTMLToTextCodif.put( "&#219;", "Û");
        hmHTMLToTextCodif.put( "&#220;", "Ü");
        hmHTMLToTextCodif.put( "&#221;", "Ý");
        hmHTMLToTextCodif.put( "&#222;", "Þ");
        hmHTMLToTextCodif.put( "&#223;", "ß");
        hmHTMLToTextCodif.put( "&#224;", "à");
        hmHTMLToTextCodif.put( "&#225;", "á");
        hmHTMLToTextCodif.put( "&#226;", "â");
        hmHTMLToTextCodif.put( "&#227;", "ã");
        hmHTMLToTextCodif.put( "&#228;", "ä");
        hmHTMLToTextCodif.put( "&#229;", "å");
        hmHTMLToTextCodif.put( "&#230;", "æ");
        hmHTMLToTextCodif.put( "&#231;", "ç");
        hmHTMLToTextCodif.put( "&#232;", "è");
        hmHTMLToTextCodif.put( "&#233;", "é");
        hmHTMLToTextCodif.put( "&#234;", "ê");
        hmHTMLToTextCodif.put( "&#235;", "ë");
        hmHTMLToTextCodif.put( "&#236;", "ì");
        hmHTMLToTextCodif.put( "&#237;", "í");
        hmHTMLToTextCodif.put( "&#238;", "î");
        hmHTMLToTextCodif.put( "&#239;", "ï");
        hmHTMLToTextCodif.put( "&#240;", "ð");
        hmHTMLToTextCodif.put( "&#241;", "ñ");
        hmHTMLToTextCodif.put( "&#242;", "ò");
        hmHTMLToTextCodif.put( "&#243;", "ó");
        hmHTMLToTextCodif.put( "&#244;", "ô");
        hmHTMLToTextCodif.put( "&#245;", "õ");
        hmHTMLToTextCodif.put( "&#246;", "ö");
        hmHTMLToTextCodif.put( "&#247;", "÷");
        hmHTMLToTextCodif.put( "&#248;", "ø");
        hmHTMLToTextCodif.put( "&#249;", "ù");
        hmHTMLToTextCodif.put( "&#250;", "ú");
        hmHTMLToTextCodif.put( "&#251;", "û");
        hmHTMLToTextCodif.put( "&#252;", "ü");
        hmHTMLToTextCodif.put( "&#253;", "ý");
        hmHTMLToTextCodif.put( "&#254;", "þ");
        hmHTMLToTextCodif.put( "&#255;", "ÿ");        
                
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
