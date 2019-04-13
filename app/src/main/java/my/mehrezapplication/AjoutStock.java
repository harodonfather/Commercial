package my.mehrezapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.DecimalFormat;
import java.util.HashMap;

import static java.lang.Double.parseDouble;

public class AjoutStock extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextQrcode;
    private EditText editTextDesigniation;
    private EditText editTextprixachatttc;
    private EditText editTextQte;
    private EditText editTextQteMin;
    private EditText  editTextprixVentettc  ;
    private int requestqrcode;

    private EditText editTextMarge;
    private Button  buttonQrCode;
    private Button  buttonAdd;
    private Button  buttonannuler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_stok);

        editTextQrcode=(EditText)findViewById(R.id.qrcode);
        editTextDesigniation=(EditText)findViewById(R.id.desi);
        editTextprixVentettc=(EditText)findViewById(R.id.prixVenteTTC);
        editTextQteMin=(EditText)findViewById(R.id.qteMin);
        editTextQte=(EditText)findViewById(R.id.qte);
        editTextprixachatttc=(EditText)findViewById(R.id.prixAchatTTC);
        //editTextQte=(EditText)findViewById(R.id.qte);

        editTextMarge=(EditText)findViewById(R.id.marge);
        buttonAdd=(Button)findViewById(R.id.btn_ajout);
        buttonannuler=(Button)findViewById(R.id.btn_anuuler);


        editTextQrcode.setOnClickListener(this);
            buttonAdd.setOnClickListener(this);
            buttonannuler.setOnClickListener(this);


    }
    private void add(){
        final String Qrcode = editTextQrcode.getText().toString().trim();
        final String Desi = editTextDesigniation.getText().toString().trim();
        final int Qte = Integer.parseInt(editTextQte.getText().toString());
        final int QteMin = Integer.parseInt(editTextQteMin.getText().toString());
        final double PrixAchatTTC = Double.parseDouble(editTextprixachatttc.getText().toString().trim());
        final double Marge = Double.parseDouble(editTextMarge.getText().toString());
        final double prixVenteTTC = Double.parseDouble(editTextprixVentettc.getText().toString()) ;


        class Add extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AjoutStock.this,"Adding...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(AjoutStock.this,s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_Qrcode,Qrcode);
                params.put(Config.KEY_Desi,Desi);
                params.put(Config.KEY_Stock_Qte,""+Qte);
                params.put(Config.KEY_Stock_QteMin,""+QteMin);
                params.put(Config.KEY_Stock_PrixAchatTTC,""+PrixAchatTTC);
                params.put(Config.KEY_Stock_PrixVenteTTC,""+prixVenteTTC);
                params.put(Config.KEY_Stock_Marge,""+Marge);

               // params.put(Config.KEY_EMP_POSTE,poste);







                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADDStock, params);
                return res;
            }
        }

        Add ae = new Add();
        ae.execute();
    }
    @Override
    protected  void onActivityResult(int  requestcode ,int resultCode, Intent data){

        IntentResult result=IntentIntegrator.parseActivityResult(requestcode,resultCode,data);
        if(result!=null){
            if(result.getContents()==null){

            }else
            {
                editTextQrcode.setText(result.getContents());
            }
        }
        else {
            super.onActivityResult(requestcode,resultCode,data);
        }

    }
    @Override
    public void onClick(View v) {
if (v==buttonAdd){
    //add();
    Intent i = new Intent(this,Acceuil.class);
    startActivity(i);
}
    if (v==buttonannuler){
        Toast.makeText(AjoutStock.this,"Annuler", Toast.LENGTH_LONG).show();
    }
        if (v==editTextQrcode){
            Toast.makeText(AjoutStock.this,"scanning", Toast.LENGTH_LONG).show();
            IntentIntegrator integrator =new IntentIntegrator(this);
            Toast.makeText(AjoutStock.this,"2", Toast.LENGTH_LONG).show();
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            integrator.setPrompt("Scann");
            integrator.setCameraId(0);
            integrator.setBeepEnabled(false);
            integrator.setBarcodeImageEnabled(false);
            integrator.initiateScan();
    }}

}
