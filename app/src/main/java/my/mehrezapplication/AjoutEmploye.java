package my.mehrezapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class AjoutEmploye extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextcin;
    private EditText editTextnom;
    private EditText editTextprenom;
    private EditText editTexttel;
    private EditText editTextadr;
    private EditText editTextposte;
    private EditText editTextsalaire;
    private EditText editTextnumcarte;
    private EditText editTextetat;
    private Button  buttonAdd;
    private Button  buttonannuler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_employe);

        editTextcin=(EditText)findViewById(R.id.cin);
        editTextnom=(EditText)findViewById(R.id.nom);
        editTextprenom=(EditText)findViewById(R.id.prenom);
        editTexttel=(EditText)findViewById(R.id.telephonne);
        editTextadr=(EditText)findViewById(R.id.adrese);
        editTextposte=(EditText)findViewById(R.id.Poste);
        editTextsalaire=(EditText)findViewById(R.id.salaire);
        editTextnumcarte=(EditText)findViewById(R.id.numcarte);
        editTextetat=(EditText)findViewById(R.id.etat);
        buttonAdd=(Button)findViewById(R.id.btn_ajout);
        buttonannuler=(Button)findViewById(R.id.btn_anuuler);



            buttonAdd.setOnClickListener(this);
            buttonannuler.setOnClickListener(this);



    }
    private void addEmployee(){
        final String nom = editTextnom.getText().toString().trim();
        final String prenom = editTextprenom.getText().toString().trim();
        final String tel = editTexttel.getText().toString().trim();
        final String adr = editTextadr.getText().toString().trim();
        final String poste = editTextposte.getText().toString().trim();
        final String salaire = editTextsalaire.getText().toString().trim();
        final String cin = editTextcin.getText().toString().trim();
        final String carte = editTextnumcarte.getText().toString().trim();
        final String etat = editTextetat.getText().toString().trim();



        class AddEmployee extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AjoutEmploye.this,"Adding...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(AjoutEmploye.this,s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_EMP_CIN,cin);
                params.put(Config.KEY_EMP_Nom,nom);
                params.put(Config.KEY_EMP_PRENOM,prenom);
                params.put(Config.KEY_EMP_TEL,tel);
                params.put(Config.KEY_EMP_ADR,adr);
                params.put(Config.KEY_EMP_POSTE,poste);
                params.put(Config.KEY_EMP_SALAIRE,salaire);
                params.put(Config.KEY_EMP_CARTE,carte);
                params.put(Config.KEY_EMP_ETAT , etat);






                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD, params);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();
    }

    @Override
    public void onClick(View v) {
if (v==buttonAdd){
    addEmployee();
    Intent i = new Intent(this,Acceuil.class);
    startActivity(i);
}
    if (v==buttonannuler){
        editTextcin.setText(null);
        editTextetat.setText(null);
        editTextnumcarte.setText(null);
        editTextsalaire.setText(null);
        editTextposte.setText(null);
        editTexttel.setText(null);
        editTextnom.setText(null);
        editTextprenom.setText(null);
        editTextadr.setText(null);
        Intent intent=new Intent(this,Acceuil.class);
        startActivity(intent);
    }
    }
}
