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

public class Scyb extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    final Context context = this;
    Button back3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scyb_bar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button back3 = findViewById(R.id.back3);
        DrawerLayout drawer = findViewById(R.id.drawer_layout3);
        NavigationView navigationView = findViewById(R.id.nav_view3);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), Index.class);
                startActivity(it);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            AlertDialog.Builder alertDialogBuiler = new AlertDialog.Builder(context);
            alertDialogBuiler.setTitle("주의 사항");
            alertDialogBuiler
                    .setMessage("맵에 표시한 교회들이 자리를 옮겼거나,사라진 경우가 있을 수도 있습니다.\n잘못된 정보나 숨겨져 있는 교회가 발견되면" +
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
        } else if (id == R.id.nav_gallery) {
            AlertDialog.Builder alertDialogBuiler3 = new AlertDialog.Builder(context);
            alertDialogBuiler3.setTitle("안내 사항");
            alertDialogBuiler3
                    .setMessage("신천지가 일으킨 사회적 물의 및 폐해는 많은 수의 판례들을 통해 법리적으로도 충분히 증명되었고, 종교적 표현의 자유는 일반적인 언론·출판에 비해 고도의 보장을 받게 된다고 판시된 바 있습니다 (대법원 96다19246 판결). 본 이의신청 글에 인용된 신천지 판례들뿐만 아니라 타 종교와 관련한 판례들(수원지법 성남지원 2014카합12; 서울고법 2014라990; 대법원 2014마2235 참조)에서도 일관적으로 드러나듯 특정 종교에 대한 개인 및 단체의 비판적 발언은 모두 법적으로 보호받아 왔음을 알 수 있습니다.\n" +
                            "\n" +
                            "더욱이 대한민국 국민이라면 모두가 보장 받는 기본적 인권 중 하나인 언론·출판의 자유(헌법 21조 1항)를 위해 제작한 앱입니다.")
                    .setCancelable(false)
                    .setPositiveButton("확인",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent it = new Intent(getApplicationContext(), Scyb.class);
                                    startActivity(it);

                                }
                            });
            AlertDialog alertDialog3 = alertDialogBuiler3.create();
            alertDialog3.show();
        } else if (id == R.id.nav_slideshow) {
            AlertDialog.Builder alertDialogBuiler = new AlertDialog.Builder(context);
            alertDialogBuiler.setTitle("안내 사항");
            alertDialogBuiler
                    .setMessage("맵에 표시한 교회들이 자리를 옮겼거나,사라진 경우가 있을 수도 있습니다.\n잘못된 정보나 숨겨져 있는 교회가 발견되면" +
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

        } else if (id == R.id.nav_tools) {
            Intent it = new Intent(getApplicationContext(), Gt.class);
            startActivity(it);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout3);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout3);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}




