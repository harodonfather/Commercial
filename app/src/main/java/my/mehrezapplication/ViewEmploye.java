package my.mehrezapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ViewEmploye extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextcin;
    private EditText editTextnom;
    private EditText editTextprenom;
    private EditText editTexttel;
    private EditText editTextadr;
    private EditText editTextposte;
    private EditText editTextsalaire;
    private EditText editTextnumcarte;
    private EditText editTextetat;
    private Button  buttonModif;

    private Button  buttonsupp;
    private Button  buttonretour;
    private String cin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employe);
        Intent intent =getIntent();
        cin=intent.getStringExtra(Config.EMP_CIN);

        editTextcin=(EditText)findViewById(R.id.cin);
        editTextnom=(EditText)findViewById(R.id.nom);
        editTextprenom=(EditText)findViewById(R.id.prenom);
        editTexttel=(EditText)findViewById(R.id.telephonne);
        editTextadr=(EditText)findViewById(R.id.adrese);
        editTextposte=(EditText)findViewById(R.id.Poste);
        editTextsalaire=(EditText)findViewById(R.id.salaire);
        editTextnumcarte=(EditText)findViewById(R.id.numcarte);
        editTextetat=(EditText)findViewById(R.id.etat);
        buttonModif=(Button)findViewById(R.id.btn_modif);
        buttonretour=(Button)findViewById(R.id.btn_retour);
        buttonsupp=(Button)findViewById(R.id.btn_supp);

        buttonsupp.setOnClickListener(this);
        buttonModif.setOnClickListener(this);
        buttonretour.setOnClickListener(this);
        editTextcin.setText(cin);
        getEmployee();
    }


    private void getEmployee() {
        class GetEmployee extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewEmploye.this,"Fetching...","Wait...",false,false);
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
                String s = rh.sendGetRequestParam(Config.URL_GET_EMP,cin);
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
            String nom = c.getString(Config.TAG_NOM);
            String prenom = c.getString(Config.TAG_PRENOM);
            String tel = c.getString(Config.TAG_TEL);
            String adr = c.getString(Config.TAG_ADR);
            String poste = c.getString(Config.TAG_POSTE);
            String salaire = c.getString(Config.TAG_SALAIRE);
            String carte = c.getString(Config.TAG_CARTE);
             String etat = c.getString(Config.TAG_ETAT);


            editTextnom.setText(nom);
            editTextprenom.setText(prenom);
            editTexttel.setText(tel);
            editTextadr.setText(adr);
            editTextposte.setText(poste);
            editTextsalaire.setText(salaire);
            editTextnumcarte.setText(carte);
            editTextetat.setText(etat);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
      private void updateEmploye() {
    final String nom = editTextnom.getText().toString().trim();
    final String prenom = editTextprenom.getText().toString().trim();
    final String tel = editTexttel.getText().toString().trim();
    final String adr = editTextadr.getText().toString().trim();
    final String poste = editTextposte.getText().toString().trim();
    final String salaire = editTextsalaire.getText().toString().trim();
    final String cin = editTextcin.getText().toString().trim();
    final String carte = editTextnumcarte.getText().toString().trim();
    final String etat = editTextetat.getText().toString().trim();

    class UpdateEmployee extends AsyncTask<Void,Void,String>{
        ProgressDialog loading;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(ViewEmploye.this,"Updating...","Wait...",false,false);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();
            Toast.makeText(ViewEmploye.this,s,Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(Void... params) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put(Config.KEY_EMP_CIN,cin);
            hashMap.put(Config.KEY_EMP_Nom,nom);
            hashMap.put(Config.KEY_EMP_PRENOM,prenom);
            hashMap.put(Config.KEY_EMP_TEL,tel);
            hashMap.put(Config.KEY_EMP_ADR,adr);
            hashMap.put(Config.KEY_EMP_POSTE,poste);
            hashMap.put(Config.KEY_EMP_SALAIRE,salaire);
            hashMap.put(Config.KEY_EMP_CARTE,carte);
            hashMap.put(Config.KEY_EMP_ETAT,etat);

            RequestHandler rh = new RequestHandler();

            String s = rh.sendPostRequest(Config.URL_UPDATE_EMP,hashMap);

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
                loading = ProgressDialog.show(ViewEmploye.this, "Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(ViewEmploye.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_DELETE_EMP,cin);
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
                    startActivity(new Intent(ViewEmploye.this,ViewAllEmployee.class));
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
         Intent i=  new Intent(ViewEmploye.this,ViewAllEmployee.class);
     startActivity(i);
        }
    }

}