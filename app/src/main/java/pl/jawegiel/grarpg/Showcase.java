package pl.jawegiel.grarpg;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Kuba on 2018-02-04.
 */

public class Showcase extends Activity {

    DrawerMenu[] menu;
    DrawerLayout dLayout;
    ListView dList;
    ArrayAdapter<String> adapter;
    boolean demo;
    SharedPreferences pref;
    ImageView swipe;
    ImageView swipe2;
    TextView opis;
    Button b;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showcase_layout);

        swipe = findViewById(R.id.zdjecie);
        swipe2 = findViewById(R.id.zdjecie2);
        opis = findViewById(R.id.opis2);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        Editor editor = pref.edit();


        b = findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("demo", true); // Storing boolean - true/false
                editor.apply();
                finish();
            }
        });



        dLayout = findViewById(R.id.demo_drawer_layout);
        dLayout.addDrawerListener(new DrawerLayout.DrawerListener() {


            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                if (slideOffset != 0) {

                    //Toast.makeText(getApplicationContext(),"s", Toast.LENGTH_SHORT).show();
                    invalidateOptionsMenu();
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                swipe.setImageResource(R.drawable.swipe_left);
                opis.setText("Aby zwinąć menu, przesuń palcem z powrotem lub naciśnij dowolne pole.");

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                swipe.setVisibility(View.GONE);
                swipe2.setVisibility(View.VISIBLE);
                opis.setText("W ten sam sposób możesz obsługiwać menu z prawej strony.");
                b.setEnabled(true);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }

    @Override
    public void onBackPressed() {
            startActivity(new Intent(this, MainActivity.class));
            finish();
    }

}
