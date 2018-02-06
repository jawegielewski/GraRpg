package pl.jawegiel.grarpg;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Gra extends AppCompatActivity {

    GameSurface gs;
    DrawerMenu[] menu;
    String[] menu2;
    DrawerLayout dLayout;
    ListView dList;
    ListView dList2;
    DrawerMenuAdapter adapter;
    ArrayAdapter<String> adapter2;
    boolean demo;
    SparseBooleanArray selections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //gs = new GameSurface(this, slideOffset);
        // Set fullscreen
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();

        setContentView(R.layout.gra_layout);




        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        demo = pref.getBoolean("demo", false);

        if(!demo)
        {
            Intent intent =new Intent(this,Showcase.class);
            startActivity(intent);
        }

        gs = findViewById(R.id.gameSurface);

        menu = new DrawerMenu[]
                {
                        new DrawerMenu("Wiadomości", R.drawable.icons8_speech_bubble_480, 2),
                        new DrawerMenu("Cloudy2", R.drawable.ic_action_name),
                        new DrawerMenu("Cloudy3", R.drawable.ic_action_name),
                        new DrawerMenu("Cloudy4", R.drawable.ic_action_name),
                        new DrawerMenu("Cloudy5", R.drawable.ic_action_name),
                        new DrawerMenu("Profil", R.drawable.icons8_user_male_640, 12),
                        new DrawerMenu("Program partnerski", R.drawable.partnericon, 555555),
                        new DrawerMenu("Ustawienia", R.drawable.icons8_settings_480),
                        new DrawerMenu("Statystyki", R.drawable.icons8_statistics_100),
                        new DrawerMenu("O programie", R.drawable.icons8_about_480),
                        new DrawerMenu("Wyjście", R.drawable.icons8_exit_80)
                };

        menu2 = new String[] { "1", "2", "3", "4" };

        dLayout = findViewById(R.id.drawer_layout);
        dLayout.addDrawerListener(new DrawerLayout.DrawerListener() {


            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {

                selections = dList.getCheckedItemPositions();
                for (int i = 0; i < selections.size(); i++) {
                    dList.setItemChecked(selections.keyAt(i), false);
                }

                int s = dList.getSelectedItemPosition();

                dList.setItemChecked(s, false);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });

        View header = (View)getLayoutInflater().inflate(R.layout.listview_header_row, null);
        header.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500));

        adapter = new DrawerMenuAdapter(this, R.layout.listview_item_row, menu);
        adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,menu2);

        dList = findViewById(R.id.left_drawer);
        dList.addHeaderView(header, null, false);
        dList.setAdapter(adapter);
        dList.setSelector(R.color.klikniecieWDrawerze);

        dList.setOnItemClickListener((AdapterView<?> arg0, View v, int position, long id) -> {
                dLayout.closeDrawers();
                Bundle args = new Bundle();
                args.putString("Menu", menu[position-1].getTitle());
                Fragment detail = new DetailFragment();
                detail.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, detail).commit();

                if(position==11)
                {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    startActivity(intent);
                    finish();
                }

        });

        dList2 = findViewById(R.id.right_drawer);
        dList2.setAdapter(adapter2);
        dList2.setSelector(R.color.klikniecieWDrawerze);

        dList2.setOnItemClickListener((AdapterView<?> arg0, View v, int position, long id) -> {

            dLayout.closeDrawers();
            Bundle args = new Bundle();
            args.putString("Menu", menu2[position]);
            Fragment detail = new DetailFragment();
            detail.setArguments(args);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, detail).commit();

        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", (DialogInterface dialog, int id) -> {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                })
                .setNegativeButton("No", (DialogInterface dialog, int id) -> {
                    dialog.cancel();
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }


}
