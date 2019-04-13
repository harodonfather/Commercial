package my.mehrezapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
       EditText Password;
        Button Entrer;
   private String mdpass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Password=(EditText)findViewById(R.id.editTextPassword);


        Entrer=(Button)findViewById(R.id.btn_entrer);

        Entrer.setOnClickListener(this);
    }
    private void getEmployee() {
        mdpass=Password.getText().toString();
        if (mdpass.equals("")){
            Toast.makeText(MainActivity.this, "saisit votre mots de passe  " , Toast.LENGTH_LONG).show();
        }

        Toast.makeText(MainActivity.this,"Vous Avez Tapez"+ mdpass, Toast.LENGTH_LONG).show();
        class GetEmployee extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this,"Fetching...","Wait...",false,false);
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
                String s = rh.sendGetRequestParam(Config.URL_LOGIN,mdpass);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }

    private void showEmployee(String json) {
        mdpass=Password.getText().toString();
String nom="";
        String user="";
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            nom = c.getString(Config.TAG_mdp);
             user = c.getString(Config.TAG_user);

           //editTextnom.setText(nom);


        } catch (JSONException e) {
            e.printStackTrace();
        }

if(nom.equals(mdpass)) {
    startActivity(new Intent(MainActivity.this,Acceuil.class));
    Toast.makeText(MainActivity.this, "Bienvenue " + user, Toast.LENGTH_LONG).show();
}else if(!(nom.equals(mdpass))) {
    Toast.makeText(MainActivity.this, "Mdp incorrecte" , Toast.LENGTH_LONG).show();
}


    }

    @Override
    public void onClick(View v) {
        if (v==Entrer){
          //  getEmployee();
            startActivity(new Intent(MainActivity.this,Acceuil.class));
            Toast.makeText(MainActivity.this, "Bienvenue " , Toast.LENGTH_LONG).show();

        }

    }
}