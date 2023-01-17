package com.iw.iwmobile.entities;

import com.codename1.io.FileSystemStorage;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.iw.iwmobile.Brain;
import com.iw.iwmobile.comm.*;
import com.iw.iwmobile.forms.IwDialogBase;

import java.util.Calendar;
import java.util.List;
import java.util.*;

import static com.codename1.ui.Component.LEFT;

/**
 *
 * @author Marcos
 */
public class Utilities {

    public static final int K_GLB_HISTORY_TYPE_ANTIMICROBIAL_CONTROL = 48;

    public static final int K_GLB_HISTORY_TRANSACTION_CREATE = 0;
    public static final int K_GLB_HISTORY_TRANSACTION_UPDATE = 1;
    public static final int K_GLB_HISTORY_TRANSACTION_DELETE = 2;
    public static final int K_GLB_HISTORY_TRANSACTION_INSERT_ITEM = 3;
    public static final int K_GLB_HISTORY_TRANSACTION_DELETE_ITEM = 5;

    public static MobRecordset setFilter(MobRecordset rs, MobField... filter) {
        MobRecordset rsFiltered = new MobRecordset();
        for (MobRow r : rs.rows) {
            boolean addRow = true;
            for (int x = 0; x < filter.length; x++) {

                String name = filter[x].getName();
                String value = filter[x].getValue();

                if (!hasFieldName(rs, name)) {
                    addRow = false;
                    break;
                }

                MobField field = r.field(name);
                field.toJson();

                if ((value == null && field.getValue() != null)
                        || (field.getValue() == null && value != null)) {
                    addRow = false;
                    break;
                }

                if (value == null) {
                    continue;
                }

                if (!value.equals(field.getValue())) {
                    addRow = false;
                    break;
                }
            }
            if (addRow) {
                rsFiltered.addRow(r);
            }
        }
        return rsFiltered;
    }

//    public static void showPdfFile(IwExtContext xCtx, String prefix, Long idImage) {
//
//        FileSystemStorage fs = FileSystemStorage.getInstance();
//        String fileName = fs.getAppHomePath()
//                + prefix
//                + "_"
//                + idImage.longValue()
//                + ".pdf";
//        if(!fs.exists(fileName)) {
//            new IwMobileWebUtilities().downloadUrlToFile(xCtx.getUserId(),
//                    Brain.getInstance().getPdfUrl_usingIwWeb2(idImage.longValue()), fileName, true);
//        }
//        Display.getInstance().execute(fileName);
//    }

    public static void sendUrlToBrowser(String title, String url, Command backCommand) {
        final BrowserComponent browser = new BrowserComponent();
        browser.setURL(url);

        Container cBar = new Container(new FlowLayout(LEFT));
        Style myStyle = UIManager.getInstance().getComponentStyle("Button");
        FontImage.createMaterial(FontImage.MATERIAL_DONE, myStyle);

        Button btnBack = new Button(FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, myStyle));
        btnBack.getAllStyles().setBgTransparency(255);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                browser.back();
            }
        });

        Button btnReload = new Button(FontImage.createMaterial(FontImage.MATERIAL_REFRESH, myStyle));
        btnReload.getAllStyles().setBgTransparency(255);
        btnReload.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                browser.reload();
            }
        });

        Button btnExit = new Button(FontImage.createMaterial(FontImage.MATERIAL_EXIT_TO_APP, myStyle));
        btnExit.getAllStyles().setBgTransparency(255);
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                backCommand.actionPerformed(null);
            }
        });

        cBar.add(btnBack).add(btnReload).add(btnExit);

        IwDialogBase f = new IwDialogBase(title); //, new BorderLayout());
        f.setLayout(new BorderLayout());
        f.add(BorderLayout.NORTH, cBar);
        f.add(BorderLayout.CENTER, browser);

        f.show(0, 0, 0, 0);
    }

    public static MobRecordset createRecordset(MobField... fields) {
        MobRecordset rs = new MobRecordset();
        MobRow r = new MobRow();
        for (int x = 0; x < fields.length; x++) {
            r.addField(fields[x]);
        }
        if (r.fields.size() > 0) {
            rs.addRow(r);
        }
        return rs;
    }

    public static MobRecordset createRsInput(String... names) {
        MobRecordset rsInput = new MobRecordset();
        for (int x = 0; x < names.length; x++) {
            MobRow r = new MobRow();
            r.addField(new MobField("NAME", MobField.STRING, names[x]));
            rsInput.addRow(r);
        }
        return rsInput;
    }

    public static MobRecordset updRecord(IwExtContextInterface xCtx, String tableName, MobRecordset rsInput) throws Exception {

        Map<String, Object> pMap = new HashMap<String, Object>();
//            r.addField(new MobField("_KEYNAME", MobField.STRING, "ID"));
//            r.addField(new MobField("_NEWROW", MobField.INTEGER, new Integer(0)));   
        if (!Utilities.hasFieldName(rsInput, "_KEYNAME")) {
            appendField(rsInput, "_KEYNAME", MobField.STRING, "ID");
        }
        if (!Utilities.hasFieldName(rsInput, "_NEWROW")) {
            for (MobRow r : rsInput.rows) {
                String keyName = r.field("_KEYNAME").getValue();
                if (keyName == null) {
                    throw new Exception("Atualização tabela " + tableName + " falhou. Recordset não identificou o KEYNAME");
                }
                if (r.field(keyName).objValue() == null) {
                    r.addField(new MobField("_NEWROW", MobField.INTEGER, new Integer(1)));
                } else {
                    r.addField(new MobField("_NEWROW", MobField.INTEGER, new Integer(0)));
                }
            }
        }
        pMap.put("rsInput", rsInput);
        pMap.put("TableName", tableName);
        pMap.put("Security", 0);

        Map<String, MobRecordset> resultMap = xCtx.execIwService("BOSetDpcExec", "MtsSetExeDpcTable", "UpdRecord", pMap);
        return resultMap.get("rsResult");
    }

    public static void appendField(MobRecordset rs, String name, String type, Object value) {
        for (MobRow r : rs.rows) {
            r.addField(new MobField(name, type, value));
        }
    }

    public static MobRow findRow(MobRecordset rs, String name, Object value) {
        name = name.toUpperCase();
        MobRow row = null;
        for (MobRow r : rs.rows) {
            if (r.field(name).objValue().equals(value)) {
                row = r;
                break;
            }
        }
        return row;
    }

    public static void delField(MobRecordset rs, String... fieldlist) {
        if (rs != null && rs.rows.size() > 0) {
            for (MobRow r : rs.rows) {
                for (int x = 0; x < fieldlist.length; x++) {
                    if (hasFieldName(r, fieldlist[x])) {
                        r.fields.remove(r.field(fieldlist[x].toUpperCase()));
                    }
                }
            }
        }
    }

    public static void delField(MobRow r, String... fieldlist) {
        if (r != null && r.fields.size() > 0) {
            for (int x = 0; x < fieldlist.length; x++) {
                if (hasFieldName(r, fieldlist[x])) {
                    r.fields.remove(r.field(fieldlist[x].toUpperCase()));
                }
            }
        }
    }


//    public static String getLower(IwExtContextInterface xCtx, String name, HashMap map) {
//        String value = null;
//        if ("MU".equalsIgnoreCase(name)) {
//            ArrayList list = CacheManager.getMeasurementUnitValues(xCtx);
//            Collections.sort(list);
//            value = (String) list.get(0);
//        } else if ("IDFREQUENCY".equalsIgnoreCase(name)) {
//            MobRecordset rsFrequency = CacheManager.getFrequency(xCtx, map);
//            if (rsFrequency != null && rsFrequency.rows.size() > 0) {
//                ArrayList<Long> list = new ArrayList<>();
//                for (MobRow r : rsFrequency.rows) {
//                    Long id = (Long) r.field("ID").objValue();
//                    list.add(id);
//                }
//                if (list.size() > 0) {
//                    Collections.sort(list);
//                    value = list.get(0).toString();
//                }
//            }
//        }
//        return value;
//    }

//    public static String getFirst(IwExtContextInterface xCtx, String name, HashMap map) {
//        String value = null;
//        if ("MU".equalsIgnoreCase(name)) {
//            ArrayList list = CacheManager.getMeasurementUnitValues(xCtx);
//            value = (String) list.get(0);
//        } else if ("IDFREQUENCY".equalsIgnoreCase(name)) {
//            MobRecordset rsFrequency = CacheManager.getFrequency(xCtx, map);
//            if (rsFrequency != null && rsFrequency.rows.size() > 0) {
//                value = rsFrequency.rows.get(0).field("ID").getValue();
//            }
//        }
//        return value;
//    }


    public static String getErrorMessage(MobRecordsetError rs) {
        String error = "";
        for (MobRow r : rs.rows) {
//            error += r.field("translation").getValue() + "\n";
            error = r.field("translation").getValue() + "\n"; // para pegar apenas a última mensagem
        }
        return error;
    }

    public static void setChanged(MobRecordset rs, boolean value, String... names) {
        for (MobRow r : rs.rows) {
            for (MobField f : r.fields) {
                String name = f.getName();
                for (int x = 0; x < names.length; x++) {
                    if (name.equalsIgnoreCase(names[x])) {
                        f.setChanged(value);
                    }
                }
            }
        }
    }

    public static void setReadOnly(MobRecordset rs, boolean value, String... names) {
        for (MobRow r : rs.rows) {
            setReadOnly(r, value, names);
        }
    }

    public static void setReadOnly(MobRow r, boolean value, String... names) {
        for (MobField f : r.fields) {
            String name = f.getName();
            for (int x = 0; x < names.length; x++) {
                if (name.equalsIgnoreCase(names[x])) {
                    f.setReadOnly(value);
                }
            }
        }
    }

    public static void setToNullUnchangedFields(MobRecordset rs) {
        for (MobRow r : rs.rows) {
            for (MobField f : r.fields) {
                if (!f.changed()) {
                    f.setValue(null);
                    f.setChanged(false);
                }
            }
        }
    }

    public static void setNull(MobRecordset rs) {
        for (MobRow r : rs.rows) {
            if (hasFieldName(r, "_SETNULL")) {
                r.fields.remove(r.field("_SETNULL"));
            }
        }
        for (MobRow r : rs.rows) {
            r.fields.add(0, new MobField("_SETNULL", MobField.STRING, "|"));
        }
        for (MobRow r : rs.rows) {
            String _setNull = r.field("_SETNULL").getValue();
            for (int index = 1; index < r.fields.size(); index++) {
                MobField f = r.fields.get(index);
                if (f.changed() && f.objValue() == null) {
                    _setNull += index + "|";
                }
            }
            r.field("_SETNULL").setValue(_setNull);
        }
    }

    public static boolean isNewRow(MobRow r) {
        boolean value = false;
        if (hasFieldName(r, "_NEWROW")) {
            if ("1".equals(r.field("_NEWROW").getValue())) {
                value = true;
            }
        }
        return value;
    }

    public static String getDateString(Date d) {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(d);
    }

    public static MobRow getNewRow(MobRecordset rs) {
        MobRow r = new MobRow();
        for (MobField f : rs.rows.get(0).fields) {
            r.addField(new MobField(f.getName(), f.getType(), null));
        }
        return r;
    }

    public static Calendar getCurrentTime() {
        return Calendar.getInstance();
    }

    public static void copyValues(MobRow rSource, MobRow rTarget) {
        for (MobField f : rSource.fields) {
            String name = f.getName();
            if (hasFieldName(rTarget, name)) {
                rTarget.field(name).setValue(f.objValue());
            }
        }
    }

    public static void showTxt(MobRecordset rs) {
        int index = 0;
        for (MobRow r : rs.rows) {
            showTxt(r, index);
            index++;
        }
    }

    public static void showTxt(MobRow r, int rowNumber) {
        String s = "Row " + rowNumber + ":\n";
        int index = 0;
        for (MobField f : r.fields) {
            s += (index++) + ":  " + f.getName() + "(" + f.getType() + "): " + f.getValue() + " [changed=" + f.changed() + "]\n";
        }
        System.out.println(s);
    }

    public static void setNewRow(MobRecordset rs, String keyName) {
        for (MobRow r : rs.rows) {
            setNewRow(r, keyName);
        }
    }

    public static void setNewRow(MobRow r, String keyName) {
        keyName = keyName.toUpperCase();
        List<MobField> fields = r.fields;
        if (!hasFieldName(r, keyName)) {
            fields.add(0, new MobField(keyName, MobField.LONG, null));
        }
        r.field(keyName).setValue(null);

        if (!hasFieldName(r, "_NEWROW")) {
            fields.add(new MobField("_NEWROW", MobField.INTEGER, new Integer(1)));
        }
        r.field("_NEWROW").setValue(new Integer(1));

        if (!hasFieldName(r, "_KEYNAME")) {
            fields.add(new MobField("_KEYNAME", MobField.STRING, keyName));
        }
        r.field("_KEYNAME").setValue(keyName);

    }

    public static Date getDateFromCalendar(Calendar c) {
        return c.getTime();
    }

    public static Calendar getCalendarFromDate(Date d) {
        Calendar cal = null;
        if (d != null) {
            cal = Calendar.getInstance();
            cal.setTime(d);
        }
        return cal;
    }

    public static boolean hasFieldName(MobRow r, String name) {
        boolean value = false;
        for (MobField f : r.fields) {
            if (f.getName().equalsIgnoreCase(name)) {
                value = true;
                break;
            }
        }
        return value;
    }

    public static boolean hasFieldName(MobRecordset rs, String name) {
        boolean value = false;
        if (rs != null && rs.rows.size() > 0) {
            MobRow r = rs.rows.get(0);
            for (MobField f : r.fields) {
                if (f.getName().equalsIgnoreCase(name)) {
                    value = true;
                    break;
                }
            }
        }
        return value;
    }

    public static void setChangedAttributeOfAllFields(MobRecordset rs, boolean value) {
        for (Iterator i = rs.rows.iterator(); i.hasNext();) {
            setChangedAttributeOfAllFields((MobRow) i.next(), value);
        }
    }

    public static void setChangedAttributeOfAllFields(MobRow r, boolean value) {
        for (Iterator i = r.fields.iterator(); i.hasNext();) {
            ((MobField) i.next()).setChanged(value);
        }
    }

    public static MobRecordset clone(MobRecordset rs) {
        MobRecordset rsClone = null;
        if (rs != null) {
            rsClone = new MobRecordset();
            for (Iterator i = rs.rows.iterator(); i.hasNext();) {
                MobRow r = (MobRow) i.next();
                rsClone.addRow(clone(r));
            }
        }
        return rsClone;
    }

    public static MobRecordset clone(MobRecordset rs, boolean noData, boolean attributes) {
        MobRecordset rsClone = null;
        if (rs != null) {
            rsClone = new MobRecordset();
            for (Iterator i = rs.rows.iterator(); i.hasNext();) {
                MobRow r = (MobRow) i.next();
                rsClone.addRow(clone(r, noData, attributes));
            }
        }
        return rsClone;
    }

    public static MobRow clone(MobRow r) {
        return clone(r, false, false);
    }

    public static MobRow clone(MobRow r, boolean noData, boolean attributes) {
        MobRow rClone = null;
        if (r != null) {
            rClone = new MobRow();
            for (Iterator i = r.fields.iterator(); i.hasNext();) {
                MobField f = (MobField) i.next();
                if (noData) {
                    rClone.addField(new MobField(f.getName(), f.getType(), null));
                } else {
                    rClone.addField(new MobField(f.getName(), f.getType(), f.objValue()));
                }
                if (attributes) {
                    rClone.field(f.getName()).setReadOnly(f.isReadOnly());
                    rClone.field(f.getName()).setChanged(f.changed());
                }
            }
        }
        return rClone;
    }

    public static boolean isNumber(String s) {
        boolean value = false;
        try {
            Long.parseLong(s);
            value = true;
        } catch (Exception ex) {
            value = false;
        }
        return value;
    }

    public static void writeHistory(IwExtContextInterface xCtx, int historyType, int transactionType, Long id, String text) throws IwCommException {

        text = "Mobile\n" + text;
        String description = null;
        if (text != null && text.length() < 4000) {
            description = text;
            text = null;
        }

        MobRecordset rsInput = new MobRecordset();
        MobRow r = new MobRow();
        rsInput.addRow(r);
        r.addField(new MobField("ID", MobField.LONG, id));
        r.addField(new MobField("HISTORYTYPE", MobField.INTEGER, historyType));
        r.addField(new MobField("TRANSACTIONTYPE", MobField.INTEGER, transactionType));
        r.addField(new MobField("TRANSACTIONDATE", MobField.DATE, new Date()));
        r.addField(new MobField("IDPERSON", MobField.LONG, xCtx.getUserProfessionalId()));
        r.addField(new MobField("IDProfessional", MobField.LONG, null));
        r.addField(new MobField("Text", MobField.STRING, text));
        r.addField(new MobField("IDText", MobField.LONG, null));
        r.addField(new MobField("Description", MobField.STRING, description));
        r.addField(new MobField("TABLENAMETEXT", MobField.STRING, "GLBHISTORYTEXT"));
        r.addField(new MobField("_KEYNAME", MobField.STRING, "ID"));
        r.addField(new MobField("_NEWROW", MobField.INTEGER, new Integer(1)));

        Map<String, Object> pMap = new HashMap<String, Object>();
        pMap.put("rsInput", rsInput);
        pMap.put("TableName", "GLBHISTORY");
        Map<String, MobRecordset> resultMap = xCtx.execIwService("BOSetGlbConfig", "MtsSetCfgGlobal", "UpdRecord", pMap);
    }

    public static void delRecord(IwExtContextInterface xCtx, String tableName, MobRecordset rsFilter) throws IwCommException {
        if (!Utilities.hasFieldName(rsFilter, "_KEYNAME")) {
            appendField(rsFilter, "_KEYNAME", MobField.STRING, "ID");
        }
        if (!Utilities.hasFieldName(rsFilter, "_NEWROW")) {
            appendField(rsFilter, "_NEWROW", MobField.INTEGER, new Integer(0));
        }

        Map<String, Object> pMap = new HashMap<String, Object>();
        pMap.put("rsFilter", rsFilter);
        pMap.put("TableName", tableName);
        Map<String, MobRecordset> resultMap = xCtx.execIwService("BOSetDpcExec", "MtsSetExeDpcTable", "DelRecord", pMap);
    }

    public static MobRecordset executeService(IwExtContextInterface xCtx,
            String projectname, String classname, String servicename,
            MobRecordset rsInput, MobRecordset rsFilter) throws IwCommException {

        Map<String, Object> pMap = new HashMap<String, Object>();
        pMap.put("rsInput", rsInput);
        pMap.put("rsFilter", rsFilter);
        Map<String, MobRecordset> resultMap = xCtx.execIwService(projectname, classname, servicename, pMap);
        return resultMap.get("rsResult");
    }

    public static MobRecordset executeSql(IwExtContextInterface xCtx, String cmd) throws IwCommException {
        Map<String, Object> pMap = new HashMap<String, Object>();
        pMap.put("cmdSQL", cmd);
        pMap.put("rsParameters", new MobRecordset());
        pMap.put("rsFilter", new MobRecordset());
        Map<String, MobRecordset> resultMap = xCtx.execIwService("BOSetDpcExec", "MtsSetExeDpcTable", "ExecuteSQL", pMap);
        return resultMap.get("rsResult");
    }

    public static MobRecordset getRecord(IwExtContextInterface xCTx, String table, MobRecordset rsInput, MobRecordset rsFilter, String orderby) throws IwCommException {

        Map<String, Object> pMap = new HashMap<String, Object>();

        pMap.put("rsInput", rsInput == null ? new MobRecordset() : rsInput);
        pMap.put("rsFilter", rsFilter == null ? new MobRecordset() : rsFilter);
        pMap.put("TableName", table);
        pMap.put("OrderBy", orderby == null ? "" : orderby);
        pMap.put("Security", "0");

        Map<String, MobRecordset> resultMap = xCTx.execIwService("BOGetDpcExec", "MtsGetExeDpcTable", "GetRecord", pMap);
        return resultMap.get("rsResult");
    }

    public static MobRecordset getRecordScc(IwExtContextInterface xCTx,
            String tableNameA, String scColumn, String orderBy, boolean outerJoin, MobRecordset rsFilter) throws IwCommException {

        String scAlias = "";
        if (scColumn != null && !scColumn.trim().equals("")) {
            scColumn = (outerJoin ? "*" : "") + scColumn.trim();
            scAlias = scColumn.trim() + "Name";
        } else {
            scColumn = "";
        }

        Map<String, Object> pMap = new HashMap<String, Object>();

        pMap.put("TableName", tableNameA);
        pMap.put("SccColumn", scColumn);
        pMap.put("ScAlias", scAlias);
        pMap.put("rsSccCode", new MobRecordset());
        pMap.put("rsSccColumn", new MobRecordset());
        pMap.put("rsInput", new MobRecordset());
        pMap.put("rsFilter", rsFilter == null ? new MobRecordset() : rsFilter);
        pMap.put("OrderBy", (orderBy == null ? "" : orderBy));
        pMap.put("Security", 0);

        Map<String, MobRecordset> resultMap = xCTx.execIwService("BOGetDpcExec", "MtsGetExeDpcTable", "GetRecordScc", pMap);
        return resultMap.get("rsResult");
    }

    public static MobRecordset getRecordJoin(IwExtContextInterface xCTx,
            String tableNameA,
            String tableNameB,
            String orderBy,
            String join,
            MobRecordset rsInput,
            MobRecordset rsFilter) throws IwCommException {

        Map<String, Object> pMap = new HashMap<String, Object>();

        pMap.put("Security", 0);
        pMap.put("TableName", tableNameA);
        pMap.put("JoinTableName", tableNameB);
        pMap.put("OrderBy", orderBy);
        pMap.put("Join", join);
        pMap.put("rsInput", rsInput == null ? new MobRecordset() : rsInput);
        pMap.put("rsFilter", rsFilter == null ? new MobRecordset() : rsFilter);

        Map<String, MobRecordset> resultMap = xCTx.execIwService("BOGetDpcExec", "MtsGetExeDpcTable", "GetRecordJoin", pMap);
        return resultMap.get("rsResult");
    }

    public static MobRecordset getRecordSql(IwExtContextInterface xCTx, Long formid, Integer keyindex, MobRecordset rsFilter) throws IwCommException {

        Map<String, Object> pMap = new HashMap<String, Object>();

        if (keyindex == null) {
            keyindex = new Integer(0);
        }

        pMap.put("ID", new Long(0));
        pMap.put("FormID", formid);
        pMap.put("KeyIndex", keyindex);
        pMap.put("rsInput", new MobRecordset());
        pMap.put("rsFilter", rsFilter);

        Map<String, MobRecordset> resultMap = xCTx.execIwService("BOGetDpcExec", "MtsGetExeDpcTable", "GetRecordSql", pMap);
        return resultMap.get("rsResult");
    }

    public static MobRecordset getProfSpec(IwExtContextInterface xCTx, String regNumber, boolean filterByMedPrescriptor) throws IwCommException {

        Map<String, Object> pMap = new HashMap<String, Object>();

        MobRecordset rsFilter = new MobRecordset();
        MobRow rFilter = new MobRow();
        rFilter.addField(new MobField("REGISTRYNUMBER", MobField.STRING, regNumber));
        rFilter.addField(new MobField("ACTIVE", MobField.INTEGER, new Integer(1)));
        if (filterByMedPrescriptor) {
            rFilter.addField(new MobField("MEDPRESCRIPTOR", MobField.INTEGER, new Integer(1)));
        }
        rsFilter.addRow(rFilter);
        Utilities.showTxt(rsFilter);;
        pMap.put("rsInput", new MobRecordset());
        pMap.put("rsFilter", rsFilter);

        Map<String, MobRecordset> resultMap = xCTx.execIwService("BOGetGlbConfig", "MtsGetCfgGlobal", "GetProfSpec", pMap);
        return resultMap.get("rsResult");
    }

    public static Label getLabelMedium(boolean bold, String s, int foregroundcolor, int backgroundcolor) {
        Label label = new Label(s);
        Style style = label.getAllStyles();
        style.setFont(
                Font.createSystemFont(
                        Font.FACE_PROPORTIONAL,
                        (bold ? Font.STYLE_BOLD : Font.STYLE_PLAIN),
                        Font.SIZE_MEDIUM
                )
        );
        style.setBgTransparency(255);
        style.setFgColor(foregroundcolor);
        style.setBgColor(backgroundcolor);
        style.setMargin(3, 3, 5, 5);
        return label;
    }

    public static Label getLabelMedium(boolean bold, String s, int color) {
        Label label = new Label(s);
        Style style = label.getAllStyles();
        style.setFont(
                Font.createSystemFont(
                        Font.FACE_PROPORTIONAL,
                        (bold ? Font.STYLE_BOLD : Font.STYLE_PLAIN),
                        Font.SIZE_MEDIUM
                )
        );
        style.setFgColor(color);
        style.setMargin(3, 3, 5, 5);
        return label;
    }

    public static TextArea getTextAreaMedium(boolean bold, String s, int foregroundcolor, int backgroundcolor, boolean editable) {
        TextArea textarea = new TextArea(s);
        textarea.setEditable(editable);
        Style style = textarea.getAllStyles();
        style.setFont(
                Font.createSystemFont(
                        Font.FACE_PROPORTIONAL,
                        (bold ? Font.STYLE_BOLD : Font.STYLE_PLAIN),
                        Font.SIZE_MEDIUM
                )
        );
        style.setBgTransparency(255);
        style.setFgColor(foregroundcolor);
        style.setBgColor(backgroundcolor);
        style.setMargin(3, 3, 5, 5);
        return textarea;
    }

    public static Component setMargin(Component component, int top, int bottom, int left, int right) {
        component.getAllStyles().setMargin(top, bottom, left, right);
        return component;
    }

    public static TextArea getTextAreaMedium(boolean bold, String s, int foregroundcolor, boolean editable) {
        TextArea textarea = new TextArea(s);
        textarea.setEditable(editable);
        Style style = textarea.getAllStyles();
        style.setFont(
                Font.createSystemFont(
                        Font.FACE_PROPORTIONAL,
                        (bold ? Font.STYLE_BOLD : Font.STYLE_PLAIN),
                        Font.SIZE_MEDIUM
                )
        );
        style.setFgColor(foregroundcolor);
        style.setMargin(3, 3, 5, 5);
        return textarea;
    }

    public static Label getLabelLarge(boolean bold, String s, int foregroundcolor, int backgroundcolor) {
        Label label = new Label(s);
        Style style = label.getAllStyles();
        style.setFont(
                Font.createSystemFont(
                        Font.FACE_PROPORTIONAL,
                        (bold ? Font.STYLE_BOLD : Font.STYLE_PLAIN),
                        Font.SIZE_LARGE
                )
        );
        style.setBgTransparency(255);
        style.setFgColor(foregroundcolor);
        style.setBgColor(backgroundcolor);
        style.setMargin(3, 3, 5, 5);
        return label;
    }

    public static Label getLabelLarge(boolean bold, String s, int color) {
        Label label = new Label(s);
        Style style = label.getAllStyles();
        style.setFont(
                Font.createSystemFont(
                        Font.FACE_PROPORTIONAL,
                        (bold ? Font.STYLE_BOLD : Font.STYLE_PLAIN),
                        Font.SIZE_LARGE
                )
        );
        style.setFgColor(color);
        style.setMargin(3, 3, 5, 5);
        return label;
    }

    public static TextArea getTextAreaLarge(boolean bold, String s, int foregroundcolor, int backgroundcolor, boolean editable) {
        TextArea textarea = new TextArea(s);
        textarea.setEditable(editable);
        Style style = textarea.getAllStyles();
        style.setFont(
                Font.createSystemFont(
                        Font.FACE_PROPORTIONAL,
                        (bold ? Font.STYLE_BOLD : Font.STYLE_PLAIN),
                        Font.SIZE_LARGE
                )
        );
        style.setBgTransparency(255);
        style.setFgColor(foregroundcolor);
        style.setBgColor(backgroundcolor);
        style.setMargin(3, 3, 5, 5);
        return textarea;
    }

    public static TextArea getTextAreaLarge(boolean bold, String s, int foregroundcolor, boolean editable) {
        TextArea textarea = new TextArea(s);
        textarea.setEditable(editable);
        Style style = textarea.getAllStyles();
        style.setFont(
                Font.createSystemFont(
                        Font.FACE_PROPORTIONAL,
                        (bold ? Font.STYLE_BOLD : Font.STYLE_PLAIN),
                        Font.SIZE_LARGE
                )
        );
        style.setFgColor(foregroundcolor);
        style.setMargin(3, 3, 5, 5);
        return textarea;
    }

    public static Label getLabelSmall(boolean bold, String s, int foregroundcolor, int backgroundcolor) {
        Label label = new Label(s);
        Style style = label.getAllStyles();
        style.setFont(
                Font.createSystemFont(
                        Font.FACE_PROPORTIONAL,
                        (bold ? Font.STYLE_BOLD : Font.STYLE_PLAIN),
                        Font.SIZE_SMALL
                )
        );
        style.setBgTransparency(255);
        style.setFgColor(foregroundcolor);
        style.setBgColor(backgroundcolor);
        style.setMargin(3, 3, 5, 5);
        return label;
    }

    public static Label getLabelSmall(boolean bold, String s, int color) {
        Label label = new Label(s);
        Style style = label.getAllStyles();
        style.setFont(
                Font.createSystemFont(
                        Font.FACE_PROPORTIONAL,
                        (bold ? Font.STYLE_BOLD : Font.STYLE_PLAIN),
                        Font.SIZE_SMALL
                )
        );
        style.setFgColor(color);
        style.setMargin(3, 3, 5, 5);
        return label;
    }

    public static TextArea getTextAreaSmall(boolean bold, String s, int foregroundcolor, int backgroundcolor, boolean editable) {
        TextArea textarea = new TextArea(s);
        textarea.setEditable(editable);
        Style style = textarea.getAllStyles();
        style.setFont(
                Font.createSystemFont(
                        Font.FACE_PROPORTIONAL,
                        (bold ? Font.STYLE_BOLD : Font.STYLE_PLAIN),
                        Font.SIZE_SMALL
                )
        );
        style.setBgTransparency(255);
        style.setFgColor(foregroundcolor);
        style.setBgColor(backgroundcolor);
        style.setMargin(3, 3, 5, 5);
        return textarea;
    }

    public static TextArea getTextAreaSmall(boolean bold, String s, int foregroundcolor, boolean editable) {
        TextArea textarea = new TextArea(s);
        textarea.setEditable(editable);
        Style style = textarea.getAllStyles();
        style.setFont(
                Font.createSystemFont(
                        Font.FACE_PROPORTIONAL,
                        (bold ? Font.STYLE_BOLD : Font.STYLE_PLAIN),
                        Font.SIZE_SMALL
                )
        );
        style.setFgColor(foregroundcolor);
        style.setMargin(3, 3, 5, 5);
        return textarea;
    }

    public static void sendMessage(IwExtContextInterface xCTx, String idTargetUser, String msgNumber, String...parameters)  {
        
        MobRecordset rsInput = new MobRecordset();
        MobRow rInput = new MobRow();
        rsInput.addRow(rInput);
        rInput.addField(new MobField("IDMESSAGE", MobField.STRING, msgNumber));
        if (idTargetUser != null) {
            rInput.addField(new MobField("IDTARGETUSER", MobField.STRING, idTargetUser));
        }

        MobRecordset rsParameters = new MobRecordset();
        if (parameters != null) {
            for (int x = 0; x < parameters.length; x++) {
                MobRow r = new MobRow();
                r.addField(new MobField("VALUE", MobField.STRING, parameters[x]));
                rsParameters.addRow(r);
            }
        }
        Map<String, Object> pMap = new HashMap<String, Object>();
        pMap.put("rsInput", rsInput);
        pMap.put("rsParam", rsParameters);

        try {
            Map<String, MobRecordset> resultMap = xCTx.execIwService(
                    "BOSetIfrExec", "NetSetExeMessage", "SendMessage", pMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
               
    }  
    
    public static MobRecordset getAttribute(IwExtContextInterface xCTx, String tableName) throws IwCommException {

        MobRecordset rsFilter = new MobRecordset();
        MobRow r = new MobRow();
        r.addField(new MobField("TABLENAME", MobField.STRING, tableName));
        rsFilter.addRow(r);
        
        Map<String, Object> pMap = new HashMap<String, Object>();

        pMap.put("rsInput", new MobRecordset());
        pMap.put("rsFilter", rsFilter);
        pMap.put("Security", "0");

        Map<String, MobRecordset> resultMap = xCTx.execIwService("BOGetDpcConfig", "MtsGetCfgDpcTable","GetAttribute", pMap);
        return resultMap.get("rsResult");
    }

    public static String getStudioService(IwExtContextInterface xCTx, Long idservice) throws IwCommException {

        String name = null;
        MobRecordset rsFilter = new MobRecordset();
        MobRow r = new MobRow();
        r.addField(new MobField("A.ID", MobField.LONG, idservice));
        rsFilter.addRow(r);
        
        Map<String, Object> pMap = new HashMap<String, Object>();

        pMap.put("rsInput", new MobRecordset());
        pMap.put("rsFilter", rsFilter);
        pMap.put("Security", "0");

        Map<String, MobRecordset> resultMap = xCTx.execIwService("BOGetIfrConfig", "NetGetCfgProject", "GetStudioService", pMap);
        
        MobRecordset result = resultMap.get("rsResult");
        if (result != null && result.rows.size() > 0) {
            name = result.rows.get(0).field("NAME").getValue();
        }
        return name;
    }
}
