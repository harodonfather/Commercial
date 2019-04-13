package my.mehrezapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Acceuil extends AppCompatActivity {

    ListView list;

    String[] aff = {
            "Article",
            "Mes Article",
            "Bon Entrer",
            "Stock",
            "Facture Vente",
            "Liste Facture Vente",
            "Retour Vente",
            "Liste Bon Retour",
            "Cloture Caisse du Jours",
            "Bon Commande",
            "Deconnecter-Vous",


    };
    String[] web = {
            "AjoutArticle",
            "ViewAllArticle",
            "AjoutStock",
            "ConsultationStock",
            "FactureVente",
            "ListeFactureVente",
            "BonRetour",
            "ListeBonRetour",
            "ClotureCaisse",
            "BonCommande",
            "Deco",
    };
    Integer[] imageId = {
            R.mipmap.ajouter,
            R.mipmap.listedesarticle,
            R.mipmap.bonentrer,
            R.mipmap.stock,
            R.mipmap.facturevente,
            R.mipmap.listefactureventes,
            R.mipmap.bonretour,
            R.mipmap.listebonderetour,
            R.mipmap.cloturecaisse,
            R.mipmap.cloturecaisse,
            R.mipmap.a,


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        CustomList adapter = new CustomList(Acceuil.this, aff, imageId);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(Acceuil.this, "You Clicked at " + aff[+position], Toast.LENGTH_SHORT).show();
                String m = web[position];
                Class c = null;
                try {
                    c = Class.forName("my.mehrezapplication." + m);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }


                Intent i = new Intent(Acceuil.this, c);

                startActivity(i);


            }
        });
    }

}