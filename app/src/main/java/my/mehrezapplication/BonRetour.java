package my.mehrezapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class BonRetour extends AppCompatActivity implements OnItemSelectedListener,ListView.OnItemClickListener, View.OnClickListener {

   Spinner spinner1,spinner2;
    String JSON_STRING;
    String rfid,cin;

    ArrayList list1=new ArrayList();
    ArrayList list2=new ArrayList();
    String V1,V2;
    Button btn_ajout;

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonretour);

        btn_ajout=(Button)findViewById(R.id.btn_affecter);

        spinner1=(Spinner)findViewById(R.id.spinnercin);
        spinner2=(Spinner)findViewById(R.id.spinnerrfid);



        spinner2.setOnItemSelectedListener(this);
        spinner1.setOnItemSelectedListener(this);
        btn_ajout.setOnClickListener(this);

         getAllCin();
         getAllCarte();
        //  getJSONCin();

        lv= (ListView)findViewById(R.id.lv);
        lv.setOnItemClickListener(BonRetour.this);
        getJSON();

    }















































    private void getAllCin() {



        String urlm = Config.URL_GET_ALLCin;
        StringRequest stringRequest = new StringRequest(urlm, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //  String id = "";
                String cin = "";

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
                    //myarray = new String[result.length()];
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject c = result.getJSONObject(i);
                        //  id = c.getString("id");
                        cin = c.getString(Config.TAG_CIN);
                        list1.add(cin);
                        //myarray[i] = id;
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(BonRetour.this, android.R.layout.simple_spinner_dropdown_item, list1);
                    dataAdapter.setDropDownViewResource(R.layout.spinner_layout);
                    spinner1.setAdapter(dataAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BonRetour.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }






    private void getAllCarte() {



        String urlm = "http://applicationforstudent.net23.net/empl/getAllCarte.php";
        StringRequest stringRequest = new StringRequest(urlm, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

              //  String id = "";
                String rfid = "";

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray("carte");
                    //myarray = new String[result.length()];
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject c = result.getJSONObject(i);
                      //  id = c.getString("id");
                        rfid = c.getString("RFID");
                        list2.add(rfid);
                        //myarray[i] = id;
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(BonRetour.this, android.R.layout.simple_spinner_item, list2);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(dataAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BonRetour.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }























    private void showallaffectation(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String rfid = jo.getString(Config.TAG_CARTE);
                String cin = jo.getString(Config.TAG_CIN);
                String date = jo.getString(Config.TAG_Date);

                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_CARTE,rfid);
                employees.put(Config.TAG_CIN,cin);
                employees.put(Config.TAG_Date,date);

                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListAdapter adapter = new SimpleAdapter(
                BonRetour.this, list, R.layout.row_item,
                new String[]{Config.TAG_CARTE,Config.TAG_CIN,Config.TAG_Date},
                new int[]{R.id.textViewCol1, R.id.textViewCol2 ,R.id.textViewCol3});

        lv.setAdapter(adapter);

    }
    private void getJSON() {

        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(BonRetour.this, "Récupération des données", "Attendez...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showallaffectation();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_Allaffectation);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        rfid = map.get(Config.TAG_CARTE).toString();

        confirmDeleteAff();
    }
    private void confirmDeleteAff(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Etes-vous sûr de libérer la carte de cet employé ?");

        alertDialogBuilder.setPositiveButton("Oui",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteaff();
                        Intent i = new Intent(BonRetour.this,Acceuil.class);
                        startActivity(i);

                    }
                });

        alertDialogBuilder.setNegativeButton("Non",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
        private void deleteaff(){
            class DeleteEmployee extends AsyncTask<Void,Void,String> {
                ProgressDialog loading;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    loading = ProgressDialog.show(BonRetour.this, "Suppression En Cours...", "Attendez...", false, false);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    loading.dismiss();
                    Toast.makeText(BonRetour.this, s, Toast.LENGTH_LONG).show();
                }

                @Override
                protected String doInBackground(Void... params) {
                    RequestHandler rh = new RequestHandler();
                    String s = rh.sendGetRequestParam(Config.URL_DELETE_Affectation,rfid);
                    return s;
                }
            }

            DeleteEmployee de = new DeleteEmployee();
            de.execute();
        }





    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;

        if(spinner.getId() == R.id.spinnercin)
        {
             V1 = parent.getItemAtPosition(position).toString();


        }
        else if(spinner.getId() == R.id.spinnerrfid)
        {
             V2=parent.getItemAtPosition(position).toString();

        }}






    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void AffectationCarte(){

        final String cin = V1.trim();
        final String carte = V2.trim();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        final String date = df.format(Calendar.getInstance().getTime());



        class AffectationCarte extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(BonRetour.this,"Affectation En Cours...","Attendez...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(BonRetour.this,s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_EMP_CIN,cin);

                params.put(Config.KEY_EMP_CARTE,carte);
                params.put("date",date);







                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD_affectation, params);
                return res;
            }
        }

        AffectationCarte ae = new AffectationCarte();
        ae.execute();
    }

    @Override
    public void onClick(View v) {
        if (v==btn_ajout) {
            Toast.makeText(this,"ok"+V1,Toast.LENGTH_SHORT).show();
            AffectationCarte();
            Intent i = new Intent(this,Acceuil.class);
            startActivity(i);

        }}






}

