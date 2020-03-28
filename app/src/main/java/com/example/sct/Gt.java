package com.example.sct;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class Gt extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    final Context context = this;
    Button back4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gt_bar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        Button back4 = findViewById(R.id.back4);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout4);
        NavigationView navigationView = findViewById(R.id.nav_view4);
        back4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), Index.class);
                startActivity(it);
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent it = new Intent(getApplicationContext(), What_SC.class);
            startActivity(it);
        } else if (id == R.id.nav_gallery) {
            Intent it = new Intent(getApplicationContext(), Scyb.class);
            startActivity(it);
        } else if (id == R.id.nav_slideshow) {
            AlertDialog.Builder alertDialogBuiler = new AlertDialog.Builder(context);
            alertDialogBuiler.setTitle("주의 사항");
            alertDialogBuiler
                    .setMessage("맵에 표시한 교회들이 자리를 옮겼거나,사라진 경우가 있을 수도 있습니다.잘못된 정보나 숨겨져 있는 교회가 발견되면" +
                            "수시로 업데이트 하겠습니다.")
                    .setCancelable(false)
                    .setPositiveButton("확인",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent it = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(it);

                                }
                            });
            AlertDialog alertDialog = alertDialogBuiler.create();
            alertDialog.show();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout4);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout4);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}


