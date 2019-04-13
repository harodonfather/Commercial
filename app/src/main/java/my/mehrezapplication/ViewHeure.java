package my.mehrezapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewHeure extends AppCompatActivity {
    private String cin;
    private String JSON_STRING;
    ListView lv1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_heure);
        Intent intent =getIntent();
        cin=intent.getStringExtra(Config.EMP_CIN);
        getJSON();
    lv1=(ListView)findViewById(R.id.lv1);
    }
    
    private void showHeure() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String Date = jo.getString(Config.TAG_Date);
                String Heure = jo.getString(Config.TAG_Heure);


                HashMap<String,String> heure = new HashMap<>();
                heure.put(Config.TAG_Date,Date);
                heure.put(Config.TAG_Heure,Heure);


                list.add(heure);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListAdapter adapter = new SimpleAdapter(
                ViewHeure.this, list, R.layout.liste2colone,
                new String[]{Config.TAG_Date,Config.TAG_Heure},
                new int[]{R.id.textViewCol1, R.id.textViewCol2 });

        lv1.setAdapter(adapter);

    }
    private void getJSON() {



        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewHeure.this, "Fetching Data", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showHeure();
            }



            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_Heure,cin);
                return s;
            }
        }



        GetJSON gj = new GetJSON();
        gj.execute();

    }


}
