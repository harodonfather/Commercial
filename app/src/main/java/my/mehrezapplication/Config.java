
package my.mehrezapplication;
import java.*;
public class Config {

    //Address of our scripts of the CRUD
    public static final String  URL_ADD_affectation="http://applicationforstudent.net23.net/empl/InsertAffectation.php";
    public static final String  URL_GET_Allaffectation="http://applicationforstudent.net23.net/empl/getAllaffectation.php";
    public static final String  URL_GET_ALLCin="http://applicationforstudent.net23.net/empl/getAllCin.php";
    public static final String URL_ADD="http://applicationforstudent.net23.net/empl/addEmp.php";//domaine/chemin php
    public static final String URL_ADDStock="http://applicationforstudent.net23.net/empl/addStock.php";//domaine/chemin php
    public static final String URL_ADDArticle="http://127.0.0.1/Commercial/AjoutArticle.php";//domaine/chemin php
    public static final String URL_GET_ALL = "http://applicationforstudent.net23.net/empl/getAllEmp.php";
    public static final String URL_GET_ALLEMP = "http://applicationforstudent.net23.net/empl/getallEmploye.php";
    public static final String URL_GET_Heure = "http://applicationforstudent.net23.net/empl/getHeure.php?cin=";
    public static final String URL_GET_AllCarte = "http://applicationforstudent.net23.net/empl/getAllCarte.php";
    public static final String URL_GET_EMP = "http://applicationforstudent.net23.net/empl/getEmp.php?cin=";
    public static final String URL_UPDATE_EMP = "http://applicationforstudent.net23.net/empl/updateEmp.php?cin=&rfid=";
    public static final String URL_DELETE_EMP = "http://applicationforstudent.net23.net/empl/deleteEmp.php?cin=";
    public static final String URL_GETArticle = "http://applicationforstudent.net23.net/empl/getEmp.php?cin=";
    public static final String URL_UPDATE_Article = "http://applicationforstudent.net23.net/empl/updateArticle.php?Qrcode=&rfid=";
    public static final String URL_DELETE_Article = "http://applicationforstudent.net23.net/empl/deleteEmp.php?cin=";
    public static final String URL_DELETE_Affectation = "http://applicationforstudent.net23.net/empl/deleteAff.php?carte=";
    public static final String URL_LOGIN = " http://applicationforstudent.net23.net/empl/login.php?mdpass=";
    //Keys that will be used to send the request to php scripts

    public static final String KEY_EMP_CIN = "cin";
    public static final String KEY_EMP_Nom = "nom";
    public static final String KEY_EMP_PRENOM = "prenom";
    public static final String KEY_EMP_TEL = "tel";
    public static final String KEY_EMP_ADR = "adr";
    public static final String KEY_EMP_POSTE = "poste";
    public static final String KEY_EMP_SALAIRE = "salaire";
    public static final String KEY_EMP_CARTE = "carte";
    public static final String KEY_EMP_ETAT = "etat";
    public static final String KEY_mdpass = "mdpass";
//stock
    public static final String KEY_Qrcode ="Qrcode";
    public static final String KEY_Desi="Desi";
    public static final String KEY_Stock_PrixAchatTTC ="";
    public static final String KEY_Stock_PrixVenteTTC = "";
    public static final String KEY_Stock_Qte="";
    public static final String KEY_Stock_Marge="";
    public static final String KEY_Stock_QteMin="";
//Article
public static final String KEY_CodeArticle ="Qrcode";





    //JSON Tags
    public static final String TAG_user = "user";
    public static final String TAG_mdp = "mdp";
    public static final String TAG_Heure = "Heure";
    public static final String TAG_Date ="Date";
    public static final String TAG_CIN = "cin";
    public static final String TAG_NOM = "nom";
    public static final String TAG_PRENOM = "prenom";
    public static final String TAG_TEL = "tel";
    public static final String TAG_ADR = "adr";
    public static final String TAG_SALAIRE = "salaire";
    public static final String TAG_CARTE = "carte";
    public static final String TAG_POSTE = "poste";
    public static final String TAG_ETAT = "etat";

    public static final String TAG_QrCode = "";
    public static final String TAG_CodeArticle = "";
    public static final String TAG_Desi = "";

    public static final String TAG_JSON_ARRAY="result";



    //employee id to pass with intent
    public static final String EMP_CIN = "cin";
    public static final String Article_ID = "CodeArticle";
}