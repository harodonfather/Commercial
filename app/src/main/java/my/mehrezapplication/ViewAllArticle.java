package my.mehrezapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewAllArticle extends AppCompatActivity implements ListView.OnItemClickListener {
    private String JSON_STRING;
    ListView lv ;

    private Button buttonAdd;
    private Button  buttonannuler;

    //@Override
   // public void onClick(View v) {
     //   if (v==buttonAdd){
            //  getEmployee();
       //     startActivity(new Intent(ViewAllArticle.this,AjoutArticle.class));
         //   Toast.makeText(ViewAllArticle.this, "Bienvenue " , Toast.LENGTH_LONG).show();

        //}

  //  }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_article);
        lv= (ListView)findViewById(R.id.lv);



        lv.setOnItemClickListener(ViewAllArticle.this);
        buttonAdd=(Button)findViewById(R.id.btn_ajout);
        buttonannuler=(Button)findViewById(R.id.btn_anuuler);

        getJSON();
    }
    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
           jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String cin = jo.getString(Config.TAG_CIN);
                String prenom = jo.getString(Config.TAG_PRENOM);
                String carte = jo.getString(Config.TAG_CARTE);

                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_QrCode,cin);
                employees.put(Config.TAG_CodeArticle,carte);
                employees.put(Config.TAG_Desi,prenom);

                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListAdapter adapter = new SimpleAdapter(
                ViewAllArticle.this, list, R.layout.row_item,
                new String[]{Config.TAG_CIN,Config.TAG_CARTE,Config.TAG_PRENOM},
                new int[]{R.id.textViewCol1, R.id.textViewCol2 ,R.id.textViewCol3});

       lv.setAdapter(adapter);

    }
    private void getJSON() {

        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewAllArticle.this, "Fetching Data", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_ALLEMP);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(ViewAllArticle.this, ViewArticle.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String QrCode = map.get(Config.TAG_QrCode).toString();


        intent.putExtra(Config.Article_ID,QrCode);
        startActivity(intent);
    }






}





