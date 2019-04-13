package my.mehrezapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.client.android.Intents;
import com.google.zxing.qrcode.encoder.QRCode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ViewArticle extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextQrcode;
    private EditText editTextDesigniation;
    private EditText  editTextcodearticle  ;
    private Button  buttonModif;

    private Button  buttonsupp;
    private Button  buttonretour;
    private String Article_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_article);
        Intent intent =getIntent();
        Article_ID=intent.getStringExtra(Config.Article_ID);


        editTextQrcode=(EditText)findViewById(R.id.qrcode);
        editTextDesigniation=(EditText)findViewById(R.id.desi);
        editTextcodearticle=(EditText)findViewById(R.id.codearticle);
        buttonModif=(Button)findViewById(R.id.btn_modif);
        buttonretour=(Button)findViewById(R.id.btn_retour);
        buttonsupp=(Button)findViewById(R.id.btn_supp);

        buttonsupp.setOnClickListener(this);
        buttonModif.setOnClickListener(this);
        buttonretour.setOnClickListener(this);
        editTextQrcode.setText(Article_ID);
        getEmployee();
    }


    private void getEmployee() {
        class GetEmployee extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewArticle.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showEmployee(s);
            }



            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_EMP,Article_ID);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }

    private void showEmployee(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String Desi = c.getString(Config.TAG_Desi);
            String CodeArticle = c.getString(Config.TAG_CodeArticle);




            editTextDesigniation.setText(Desi);
            editTextcodearticle.setText(CodeArticle);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
      private void updateEmploye() {
    final String Desi = editTextDesigniation.getText().toString().trim();
    final String CodeArticle = editTextcodearticle.getText().toString().trim();
          final String QrCode = editTextQrcode.getText().toString().trim();

    class UpdateEmployee extends AsyncTask<Void,Void,String>{
        ProgressDialog loading;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(ViewArticle.this,"Updating...","Wait...",false,false);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();
            Toast.makeText(ViewArticle.this,s,Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(Void... params) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put(Config.KEY_Qrcode,QrCode);
            hashMap.put(Config.KEY_EMP_Nom,Desi);
            hashMap.put(Config.KEY_CodeArticle,CodeArticle);


            RequestHandler rh = new RequestHandler();

            String s = rh.sendPostRequest(Config.URL_UPDATE_Article,hashMap);

            return s;
        }
    }

    UpdateEmployee ue = new UpdateEmployee();
    ue.execute();
}
    private void deleteEmployee(){
        class DeleteEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewArticle.this, "Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(ViewArticle.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_DELETE_Article, Article_ID);
                return s;
            }
        }

        DeleteEmployee de = new DeleteEmployee();
        de.execute();
    }

private void confirmDeleteEmploye(){
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
    alertDialogBuilder.setMessage("Are you sure you want to delete this employee?");

    alertDialogBuilder.setPositiveButton("Yes",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    deleteEmployee();
                    startActivity(new Intent(ViewArticle.this,ViewAllEmployee.class));
                }
            });

    alertDialogBuilder.setNegativeButton("No",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {

                }
            });

    AlertDialog alertDialog = alertDialogBuilder.create();
    alertDialog.show();
}
 @Override
    public void onClick(View v) {
        if (v==buttonModif){
            updateEmploye();
            Intent i = new Intent(this,Acceuil.class);
            startActivity(i);
        }
 if (v==buttonsupp){
     confirmDeleteEmploye();

        }
 if (v==buttonretour){
         Intent i=  new Intent(ViewArticle.this,ViewAllEmployee.class);
     startActivity(i);
        }
    }

}