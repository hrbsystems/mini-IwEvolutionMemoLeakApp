/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iw.iwmobile;

//import com.codename1.components.InfiniteProgress;
//import com.codename1.components.ShareButton;
//import com.codename1.io.FileSystemStorage;
//import com.codename1.io.Log;
//import com.codename1.io.Storage;
//import com.codename1.io.Util;
//import com.codename1.location.Location;
//import com.codename1.location.LocationListener;
//import com.codename1.location.LocationManager;
//import com.codename1.ui.*;
//import com.codename1.ui.events.ActionEvent;
//import com.codename1.ui.events.ActionListener;
//import com.codename1.ui.layouts.BoxLayout;
//import com.codename1.ui.util.ImageIO;
//import com.codename1.util.Base64;
//import com.codename1.util.DateUtil;
//import com.codename1.util.MathUtil;
//import com.codename1.util.StringUtil;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Storage;
import com.codename1.io.URL;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import com.iw.iwmobile._fakelibs.StateMachine;
import com.iw.iwmobile.comm.*;
import com.iw.iwmobile.entities.*;
import com.iw.iwmobile.extensions.evolution.IwFormEvolutionNavig;
//import userclasses.StateMachine;

import javax.swing.plaf.UIResource;
import java.io.*;
import java.util.Calendar;
import java.util.*;

import static com.iw.iwmobile.IwConstantsInterface.*;

/**
 *
 * @author helio-mint32-2
 */
public  class Brain implements IwConstantsInterface {

  final public static String LOG_PREFIX = "DEBUG-IWMOBILE";

  final public static String IMAGE_WAZE_1 =
          "iw02-icones-waze-01.png";
  final public static String IMAGE_WAZE_2 =
          "iw02-icones-waze-02.png";
  final public static String IMAGE_NAVIGATE_2 =
          "iw02_icones_navigate_72x72.png";
  final public static String IMAGE_MAP_MARKER_1 =
          "iw02-icones-Map-Marker-01.png";
  final public static String IMAGE_MAP_MARKER_2 =
          "iw02-icones-Map-Marker-02.png";
  final public static String IMAGE_EDIT_ENABLED =
          "iw02-icone-editar-on.png";
  //w-icone-editar-on_32x32.png"; //icon_edit_32x32.png
  final public static String IMAGE_SEND_ENABLED =
          "iw02-icone-salvar-on.png";
  // "iw-icone-salvar-on_32x32.png"; //icon_send-01_32x32.png
  final public static String IMAGE_ADD_ENABLED =
          "iw02-icone-adicionar-on.png";
  //icon_add_32x32.png
  final public static String IMAGE_SEARCH_ENABLED =
          "iw02-icone-pesquisar-on.png";
  // "iw-icone-pesquisar-on_32x32.png"; //icon_find_48x32.png
  final public static String IMAGE_SEARCH_PARAMETERIZED_ENABLED =
          "iw02-icone-pesquisa-parametrizada-on.png";
  // "iw-icone-pesquisar-on_32x32.png"; //icon_find_48x32.png
  final public static String IMAGE_SEARCH_PARAMETERIZED_ACTIVE =
          "iw02-icone-pesquisa-parametrizada-on-yellow.png";

  final public static String IMAGE_VIEW_ENABLED =
          "iw02-icone-pesq_avancada-on.png";
  //icon_view_32x32.png
  final public static String IMAGE_SUMMARY_ENABLED =
          "iw02-icone-ficha_tecnica-on.png";
  final public static String IMAGE_EVOLUTION_ENABLED =
          "iw02-icone-ficha_tecnica-on.png";
  final public static String IMAGE_PRESCRIPTION_ENABLED =
          "iw02-icone-ficha_tecnica-on.png";
  final public static String IMAGE_EXCEPTION_ENABLED =
          "iw02-icone-ficha_tecnica-on.png";
  final public static String IMAGE_PHONE_CALL_ENABLED =
          "iw02-icone-chamada-on.png";
  // "icon_phone_blue_32x32.png"; //icon_phone_blue_32x32.png
  final public static String IMAGE_CAMERA_ENABLED =
          "iw02-icone-camera-on.png";
  // "camera_32x32.png"; //camera_32x32.png
  final public static String IMAGE_PATIENT_ENABLED =
          "paciente_128x128.png";
  final public static String IMAGE_SCHEDULE_ENABLED =
          "agenda_128x128.png";
  final public static String IMAGE_SYNC_OFFLINE_DATA =
          "iw02_icones_syncdata_128x128.png";
  final public static String IMAGE_REMOVE_ENABLED =
          "iw02-icone-delete-on.png";
  //"icon_delete_32x32.png";
  final public static String IMAGE_PHOTO_GALLERY_ENABLED =
          "iw02-icone-salvar-on.png";
  //"lupa4.png";
  final public static String IMAGE_PLACE_HOLDER_ENABLED =
          "lupa4.png";
  // fake image. Needs get correct image.

  final public static String IMAGE_EDIT_DISABLED =
          "iw02-icone-editar-off.png";
  //icon_edit_32x32.png
  final public static String IMAGE_SEND_DISABLED =
          "iw02-icone-salvar-off.png";
  //icon_send-01_32x32.png
  final public static String IMAGE_ADD_DISABLED =
          "iw02-icone-adicionar-off.png";
  //icon_add_32x32.png
  final public static String IMAGE_SEARCH_DISABLED =
          "iw02-icone-pesquisar-on.png";
  //"iw-icone-pesquisar-off_32x32.png"; //icon_find_48x32.png
  final public static String IMAGE_VIEW_DISABLED =
          "iw02-icone-pesq_avancada-off.png";
  //icon_view_32x32.png
  final public static String IMAGE_SUMMARY_DISABLED =
          "iw02-icone-ficha_tecnica-off.png";
  final public static String IMAGE_EVOLUTION_DISABLED =
          "iw02-icone-ficha_tecnica-off.png";
  final public static String IMAGE_PRESCRIPTION_DISABLED =
          "iw02-icone-ficha_tecnica-off.png";
  final public static String IMAGE_PHONE_CALL_DISABLED =
          "iw02-icone-chamada-off.png";
  // icon_phone_blue_32x32.png"; //icon_phone_blue_32x32.png
  final public static String IMAGE_CAMERA_DISABLED =
          "iw02-icone-camera-off.png";
  //camera_32x32.png"; //camera_32x32.png
  final public static String IMAGE_PATIENT_DISABLED =
          "paciente_128x128.png";
  final public static String IMAGE_SCHEDULE_DISABLED =
          "agenda_128x128.png";
  final public static String IMAGE_REMOVE_DISABLED =
          "iw02-icone-delete-off.png";
  //"icon_delete_32x32.png";
  final public static String IMAGE_PHOTO_GALLERY_DISABLED =
          "iw02-icone-salvar-off.png";
  //"lupa4.png"; // fake photo. Needs find better.
  final public static String IMAGE_PLACE_HOLDER_DISABLED =
          "lupa4.png";
  // fake image. Needs get correct image.
  final public static String IMAGE_TICKED =
          "ticked.png";
  final public static String IMAGE_CHECK_IN =
          "iw02_icones_checkin_256.png";
  final public static String IMAGE_CHECK_OUT =
          "iw02_icones_checkout_256.png";
  final public static String IMAGE_SETTINGS =
          "iw02_icones_settings_256x256.png";
  final public static String IMAGE_SETTINGS_RED =
          "iw02_icones_settings_red_256x256.png";
  final public static String IMAGE_UNDO =
          "iw02-icone-undo.png";

  final public static String IMAGE_ARROW_UP =
          "iw02-icone-arrow-up.png";
  final public static String IMAGE_ARROW_DOWN =
          "iw02-icone-arrow-down.png";
  final public static String IMAGE_ARROW_REORDER_123 =
          "iw02-icone-arrow-reorder-123.png";
  final public static String IMAGE_GOOGLE_MAPS =
          "GoogleMaps.png";

  final static public String IMAGE_GET_PDF_FROM_SERVER = "image_Get_pdf_From_Server";
  final static public String IMAGE_BACK_INITIAL_PDF = "image_Get_pdf_From_Server";
  final static public String IMAGE_ATTACH_PDF = "image_Get_pdf_From_Server";
  final static public String IMAGE_DETACH_PDF = "image_Get_pdf_From_Server";


  private Brain() {
  }

  private static Brain singleBrainInstance;
  public static Brain getInstance() {
    if (singleBrainInstance == null) {
      singleBrainInstance = new Brain();
    }
    return singleBrainInstance;
  }

  String webServerRootPath;
  public void setWebServeRootPath(String webServerRootPath) {
    this.webServerRootPath = webServerRootPath;
  }
  public String getWebServeRootPath() {
    return this.webServerRootPath;
  }

  public boolean isOnlineMode() {
    return true;
  }
  public boolean isOfflineMode() {
    return false;
  }
  public void setOfflineMode(boolean b) {
    // here - do nothing
  }


  public String scaleImage1(String sFilePath) {
    return "";
  }

  public String scaleImage(String sFilePath) {
    return "";
  }

  public String scaleDownImage1(String sFilePath) {
    return "";
  };

  @Override
  public String getIwTranslation(String token) {
    return token;
  }

  //@Override
  Map<String, String> execConsistFunction() throws IwCommException {
    return new HashMap<String,String>();
  }

  IwServiceCaller iwSrvCaller = new IwServiceCaller();
  public IwServiceCallerInterface getIwServiceCaller() {
      return iwSrvCaller;
  }


  public int getIwMobileImageWidth() {
    return 480;
  }


  final public int MENU_STANDARD = 1;
  public void setFormMenu(Form f, int menuType) {}


  public String fmtDate(Date d) {
    if (d == null) return "";
    Calendar c = Calendar.getInstance();
    c.setTime(d);
    return
            new StringBuilder()
                    .append(c.get(Calendar.DAY_OF_MONTH))
                    .append("/")
                    .append(c.get(Calendar.MONTH) + 1)
                    .append("/")
                    .append(c.get(Calendar.YEAR))
                    .toString();
  }

  public long getNumberOfDays(Date d1, Date d2) {
    long ONE_DAY = 1000 * 60 * 60 * 24;
    long n1 = d1.getTime();
    long n2 = d2.getTime();
    return (n2 - n1)/ONE_DAY;
  }

  public String fmtDate_dd_mm_yyyy(Date d) {
    if (d == null) return "";
    Calendar c = Calendar.getInstance();
    c.setTime(d);

    int day = c.get(Calendar.DAY_OF_MONTH);
    int month = c.get(Calendar.MONTH) + 1;
    int year = c.get(Calendar.YEAR);

    String sDay = (day < 10)? "0" + day : "" + day;
    String sMonth = (month < 10)? "0" + month : "" + month;
    String sYear = "" + year;
    return
            new StringBuilder()
                    .append(sDay)
                    .append("/")
                    .append(sMonth)
                    .append("/")
                    .append(sYear)
                    .toString();
  }

  public String fmtTime(Date d) {
    if (d == null) return "";
    Calendar c = Calendar.getInstance();
    c.setTime(d);
    return
            new StringBuilder()
                    .append(c.get(Calendar.HOUR_OF_DAY))
                    .append(":")
                    .append(

                            (c.get(Calendar.MINUTE) < 10)?
                                    "0" + c.get(Calendar.MINUTE)
                                    :
                                    c.get(Calendar.MINUTE)

                    )
                    .toString();
  }

  public String fmtDateTime (Date d) {
    if (d == null) return "";
    Calendar c = Calendar.getInstance();
    c.setTime(d);
    return fmtDate(d) + " " + fmtTime(d);
  }

  public String fmtDateTime_dd_mm_yyyy_hh_mm (Date d) {
    if (d == null) return "";
    Calendar c = Calendar.getInstance();
    c.setTime(d);
    return fmtDate_dd_mm_yyyy(d) + " " + fmtTime_hh_mm(d);
  }

  public String fmtTime_hh_mm(Date d) {
    if (d == null) return "";
    Calendar c = Calendar.getInstance();
    c.setTime(d);
    return
            new StringBuilder()
                    .append((c.get(Calendar.HOUR_OF_DAY) < 10)?"0" + c.get(Calendar.HOUR_OF_DAY):c.get(Calendar.HOUR_OF_DAY))
                    .append(":")
                    .append((c.get(Calendar.MINUTE) < 10)?"0" + c.get(Calendar.MINUTE):c.get(Calendar.MINUTE)).toString();
  }

  public String fmtAddress(
          String address,
          String complement,
          String district) {

    address = (address == null)? "" : address;
    complement = (complement == null)? "" : complement;
    district = (district == null)? "" : district;

    String resp =
            address
                    + " "
                    + complement
                    + " "
                    + district;

    if (resp.trim().length() == 0) {
      return "Endereço não cadastrado";
    }
    else {
      return resp;
    }

  }

    public Object getContext() {
      return new Object();
    }


//  public Image getImage(String image_name) {
//    return getStateMashine()
//            ._GetResources()
//            .getImage(image_name);
//  }

  public Image getImage(String image_name) {
    //todo

    InputStream is = IwEvolutionLeakMemoApp.class.getClassLoader().getResourceAsStream("/" + image_name);

    Image img = null;
    try {
      img = Image.createImage("/" + image_name);;
    }
    catch (IOException ioEx) {
      System.out.println("Error getting Image from Resources DIR");
    }
    return img;
  }

  private Form formEvolutionNavigInstance;
  public void setFormEvolutionNavigInstance(Form f) {
    this.formEvolutionNavigInstance = f;
  }
  public Form getFormEvolutionNavigInstance() {
    return this.formEvolutionNavigInstance;
  }
  private Form formAddEvolutionInstance;
  public void setFormAddEvolutionInstance(Form f) {
    this.formAddEvolutionInstance = f;
  }
  public Form getFormAddEvolutionInstance() {
    return this.formAddEvolutionInstance;
  }

}
