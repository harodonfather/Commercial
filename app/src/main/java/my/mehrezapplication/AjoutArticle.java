package my.mehrezapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;

public class AjoutArticle extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextQrcode;
    private EditText editTextDesigniation;


    private Button  buttonAdd;
    private Button  buttonannuler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_article);

        editTextQrcode=(EditText)findViewById(R.id.qrcode);
        editTextDesigniation=(EditText)findViewById(R.id.desi);

        buttonAdd=(Button)findViewById(R.id.btn_ajout);
        buttonannuler=(Button)findViewById(R.id.btn_anuuler);


        editTextQrcode.setOnClickListener(this);
            buttonAdd.setOnClickListener(this);
            buttonannuler.setOnClickListener(this);


    }
    private void add(){
        final String Qrcode = editTextQrcode.getText().toString().trim();
        final String Desi = editTextDesigniation.getText().toString().trim();


        class Add extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AjoutArticle.this,"Adding...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(AjoutArticle.this,s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_Qrcode,Qrcode);
                params.put(Config.KEY_Desi,Desi);
                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADDArticle, params);

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
    add();
    Toast.makeText(AjoutArticle.this,"Ajout Avec Succes", Toast.LENGTH_LONG).show();

    Intent i = new Intent(this,Acceuil.class);
    startActivity(i);
}
    if (v==buttonannuler){
        Toast.makeText(AjoutArticle.this,"Annuler", Toast.LENGTH_LONG).show();
    }
        if (v==editTextQrcode){
            Toast.makeText(AjoutArticle.this,"scanning", Toast.LENGTH_LONG).show();
            IntentIntegrator integrator =new IntentIntegrator(this);
            Toast.makeText(AjoutArticle.this,"2", Toast.LENGTH_LONG).show();
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            integrator.setPrompt("Scann");
            integrator.setCameraId(0);
            integrator.setBeepEnabled(false);
            integrator.setBarcodeImageEnabled(false);
            integrator.initiateScan();
    }}

}
