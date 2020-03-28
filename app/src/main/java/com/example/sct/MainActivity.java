package com.example.sct;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import noman.googleplaces.NRPlaces;
import noman.googleplaces.Place;
import noman.googleplaces.PlacesException;
import noman.googleplaces.PlacesListener;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends FragmentActivity implements
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private static final int REQUEST_CODE_PERMISSIONS = 1000;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mFusedLocationClient;
    private ClusterManager<MyItem> mClusterManager;
    private DrawerLayout mDrawerLayout;
    private Context context = this;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), Index.class);
                startActivity(it);
            }
        });
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


    }



    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    public void onLastLocationButtonClicked(View view) {
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this ,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           ActivityCompat.requestPermissions(this,
                   new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_CODE_PERMISSIONS);
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    LatLng myLocation = new LatLng(location.getLatitude(),location.getLongitude());
                    mMap.addMarker(new MarkerOptions()
                    .position(myLocation)
                    .title("현재위치"));
                    mMap.isMyLocationEnabled();
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
                }
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions,@NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        switch (requestCode){
            case REQUEST_CODE_PERMISSIONS:
                if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                        (this ,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"권한 체크 거부됨",Toast.LENGTH_SHORT).show();
                }
        }
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
       MarkerOptions seoulMarker = new MarkerOptions();
        mMap = googleMap;
        LatLng semin = new LatLng(37.2706008,127.0135755999997);
        LatLng seoul = new LatLng(37.531382,126.980623);
        LatLng hankook = new LatLng(36.712624,127.824279);
        //mMap.addMarker(new MarkerOptions().position(semin).title("세민직업전문학교"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hankook));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(7));
        //Marker marker1 ;
       // marker1 = mMap.addMarker(new MarkerOptions().position(seoul).title("서울").snippet("20개"));
        //marker1.showInfoWindow();

        /*
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0312365041"));
                if(intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                }
            }
        });*/

        mClusterManager = new ClusterManager<>(this,mMap);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setOnInfoWindowClickListener(mClusterManager);


        addPersonItems();
        mClusterManager.cluster();


    }
  private void addPersonItems() {

      for (int i = 0; i < 1; i++) {
          PlaceType placeType = PlaceType.CHURCH;
          mClusterManager.addItem(new MyItem(37.426186, 126.991855, "과천", "경기도 과천시 별양동 1-19(요한지파)",placeType));
          mClusterManager.addItem(new MyItem(37.514013, 127.150503, "참빛교회", "경기도 하남시 감일동 176번지(요한지파)",placeType));
          mClusterManager.addItem(new MyItem(37.434796, 127.139678, "성남시온", "경기 성남시 중원구 성남동 2391(요한지파)",placeType));
          mClusterManager.addItem(new MyItem(37.317349, 126.842280, "안산시온", "경기 안산시 단원구 고잔2동 542-2번지 우진빌딩6층(요한지파)",placeType));
          mClusterManager.addItem(new MyItem(37.310020, 126.994343, "수원", "경기 수원시 장안구 파장동 209-1 대영플라자 3,4층(요한지파)",placeType));
          mClusterManager.addItem(new MyItem(36.991669, 127.100224, "평택", "경기 평택시 합정동 762-7 쌍용대신빌딩 4층(요한지파)",placeType));
          mClusterManager.addItem(new MyItem(37.310020, 126.994343, "이천", "경기 수원시 장안구 파장동 209-1 대영플라자 3,4층(요한지파)",placeType));
          mClusterManager.addItem(new MyItem(37.271274, 127.126589, "수지", "경기 용인시 기흥구 구갈동 581번지 아트프라자 8층(요한지파)",placeType));
          mClusterManager.addItem(new MyItem(35.183013, 126.908218, "광주", "광주 북구 오치동 992-6번지(베드로지파)",placeType));
          mClusterManager.addItem(new MyItem(34.794063, 126.387404, "목포", "전남 목포시 호남동 89번지(베드로지파)",placeType));
          mClusterManager.addItem(new MyItem(34.769877, 127.644454, "여수", "여수시 화장동 820번지(베드로지파)",placeType));
          mClusterManager.addItem(new MyItem(34.959369, 127.489821, "순천", "순천시 조곡동 370-1번지(베드로지파)",placeType));
          mClusterManager.addItem(new MyItem(35.110424, 126.893946, "송하", "광주 남구 송하동 46-2번지(베드로지파)",placeType));
          mClusterManager.addItem(new MyItem(35.104166, 128.957246, "부산", "부산광역시 사하구 하단1동 1165-17번지(부산야고보지파)",placeType));
          mClusterManager.addItem(new MyItem(35.217089, 128.587512, "마산", "경상남도 창원시 마산합포구 산호동 307-1번지 동일빌딩 2층(부산야고보지파)",placeType));
          mClusterManager.addItem(new MyItem(34.887185, 128.608171, "거제", "경상남도 거제시 장평동 139번지 가람빌딩 4층(부산야고보지파)",placeType));
          mClusterManager.addItem(new MyItem(35.341601, 129.036712, "양산", "경상남도 양산시 중부동 432-14번지 2층(부산야고보지파)",placeType));
          mClusterManager.addItem(new MyItem(35.156601, 128.661960, "진해", "창원시 진해구 여좌동 121-2번지 여좌신협2층(부산야고보지파)",placeType));
          mClusterManager.addItem(new MyItem(34.859246, 128.427180, "통영", "경상남도 통영시 무전동 1031-5번지 3층(부산야고보지파)",placeType));
          mClusterManager.addItem(new MyItem(35.497285, 128.743675, "밀양", "경상남도 밀양시 내이동 1188-17번지 3층(부산야고보지파)",placeType));
          mClusterManager.addItem(new MyItem(35.160329, 129.115245, "안드레교회", "부산시 수영구 광안1동 120-262(안드레지파)",placeType));
          mClusterManager.addItem(new MyItem(35.555366, 129.315017, "울산교회", "울산 중구 우정동 252-7번지(우암길65) 구청아예식장(안드레지파)",placeType));
          mClusterManager.addItem(new MyItem(35.204869, 128.691867, "창원교회", "경남 창원시 성산구 남산동 601-1번지 창남상가 3층(안드레지파)",placeType));
          mClusterManager.addItem(new MyItem(35.191190, 128.802159, "김해교회", "경남 김해시 장유면 대청리 304-2번지 위드3빌등 6층(안드레지파)",placeType));
          mClusterManager.addItem(new MyItem(33.489482, 126.489907, "제주교회", "제주 제주시 연동 261-5번지 3층(안드레지파)",placeType));
          mClusterManager.addItem(new MyItem(35.541107, 127.741004, "진주", "경남 함양군 함양읍 신천리 28번지 함양선교센터(안드레지파)",placeType));
          mClusterManager.addItem(new MyItem(33.250859, 126.562257, "제주", "제주 서귀포시 서귀동 289-8번지 2층 서귀포교회 교육관(안드레지파)",placeType));
          mClusterManager.addItem(new MyItem(35.839714, 128.566545, "대구", "대구 남구 대명 10동 1623-12번지 대덕빌딩 (다대오지파)",placeType));
          mClusterManager.addItem(new MyItem(36.019376, 129.358502, "포항", "경북 포항시 북구 죽도동 335-6번지 3층(다대오지파)",placeType));
          mClusterManager.addItem(new MyItem(36.127182, 128.341615, "구미", "경북 구미시 원평동 1023-7번지 2층(다대오지파)",placeType));
          mClusterManager.addItem(new MyItem(35.853212, 129.224453, "경주", "경북 경주시 동천동 813-5번지 6층",placeType));
          mClusterManager.addItem(new MyItem(36.564355, 128.724647, "안동선교센터", "경북 안동시 안흥동 283-2 3층(다대오지파)",placeType));
          mClusterManager.addItem(new MyItem(37.363899, 127.939753, "원주", "강원도 원주시 우산동 96-4번지(빌립지파)",placeType));
          mClusterManager.addItem(new MyItem(36.970232, 127.930859, "충주", "충주시 성서동 451번지 한양프라자 4,5층(빌립지파)",placeType));
          mClusterManager.addItem(new MyItem(37.855967, 127.748907, "춘천", "춘천시 석사동 291-10 승호빌딩 2,3,4층(빌립지파)",placeType));
          mClusterManager.addItem(new MyItem(37.784322, 128.871112, "강릉", "강릉시 죽헌동 320-8(빌립지파)",placeType));
          mClusterManager.addItem(new MyItem(37.529573, 129.105364, "동해", "동해시 평릉동 159-1 일성빌딩 2,3층(빌립지파)",placeType));
          mClusterManager.addItem(new MyItem(37.126185, 128.216128, "제천", "충북 제천시 화산동 963번지(빌립지파)",placeType));
          mClusterManager.addItem(new MyItem(37.635768, 126.832511, "화정시온", "경기 고양시 덕양구 화정동 967-1 동현빌딩 7,8,9층(시몬지파)",placeType));
          mClusterManager.addItem(new MyItem(37.492229, 126.909941, "영등포", "서울 동작구 신대방1동 565-22 4~7층 우성스포츠센터 4층(시몬지파)",placeType));
          mClusterManager.addItem(new MyItem(37.561189, 126.927276, "서대문", "서울 서대문구 창천동 490번지 홍익사랑빌딩 2층,5층(시몬지파)",placeType));
          mClusterManager.addItem(new MyItem(37.759947, 126.769634, "파주", "경기 파주시 금촌2동 932-28 뉴신화프라자 5층(시몬지파)",placeType));
          mClusterManager.addItem(new MyItem(37.543899, 126.837093, "시온교회", "서울시 강서구 화곡동 1034-13(바톨로매지파)",placeType));
          mClusterManager.addItem(new MyItem(37.502011, 126.771020, "부천교회", "부천시 원미구 중동 1146 헤이베라스7층(바톨로매지파)",placeType));
          mClusterManager.addItem(new MyItem(37.616469, 126.715738, "김포교회", "김포시 사우동 253-3 유림빌딩 505호(바톨로매지파)",placeType));
          mClusterManager.addItem(new MyItem(37.491288, 126.704397, "인천교회", "인천 부평구 산곡3동 369-277(마태지파)",placeType));
          mClusterManager.addItem(new MyItem(37.550149, 126.676533, "서인천시온교회", "인천 서구 연희동 686-4번지 계정빌딩 3층(마태지파)",placeType));
          mClusterManager.addItem(new MyItem(37.450156, 126.731738, "만수교회", "인천 남동구 만수1동 967-8번지 거신빌딩 5층(마태지파)",placeType));
          mClusterManager.addItem(new MyItem(37.457082, 126.691852, "주안교회", "인천 남동구 주안동 1543-11번지 세종빌딩 5층(마태지파)",placeType));
          mClusterManager.addItem(new MyItem(37.417078, 126.678092, "연수교회", "인천 연수구 연수동 592번지 연수브랫슬빌딩 401호(마태지파)",placeType));
          mClusterManager.addItem(new MyItem(37.534117, 126.737148, "계양교회", "인천 계양구 계산4동 1082-5 루비프라자 7층(마태지파)",placeType));
          mClusterManager.addItem(new MyItem(36.337898, 127.395247, "대전교회", "대전시 서구 용문동 244-8번지(맛디아지파)",placeType));
          mClusterManager.addItem(new MyItem(36.639453, 127.487087, "청주교회", "청주시 상당구 영동 76번지(맛디아지파)",placeType));
          mClusterManager.addItem(new MyItem(36.816134, 127.140626, "천안교회", "천안시 서북구 성정1동 713-51번지(맛디아지파)",placeType));
          mClusterManager.addItem(new MyItem(36.463942, 127.121756, "공주교회", "공주시 금성동 189-1번지(맛디아지파)",placeType));
          mClusterManager.addItem(new MyItem(36.780227, 127.008259, "아산교회", "아산시 온천동 79-7번지 2층(맛디아지파)",placeType));
          mClusterManager.addItem(new MyItem(36.782733, 126.457140, "서산교회", "서산시 동문동 888-11 그린필백화점 5층(맛디아지파)",placeType));
          mClusterManager.addItem(new MyItem(36.207812, 127.084740, "논산문화센터", "충남 논산시 화지동 41-4번지 2층(맛디아지파)",placeType));
          mClusterManager.addItem(new MyItem(36.076539, 126.688610, "서천모임방", "충남 서천군 서천읍 군사리 859-6번지(맛디아지파)",placeType));
          mClusterManager.addItem(new MyItem(36.345940, 126.603271, "자원봉사센터(보령)", "충남 보령시 동대동 1842번지 2층(맛디아지파)",placeType));
          mClusterManager.addItem(new MyItem(36.272940, 127.255411, "계룡모임방", "충남 계룡시 엄사면 금암동 147-10번지 4층(맛디아지파)",placeType));
          mClusterManager.addItem(new MyItem(37.646744, 127.062549, "성북", "7호선 중계역 하라프라자 4층(서울야고보지파)",placeType));
          mClusterManager.addItem(new MyItem(37.851458, 127.162993, "포천", "포천시 선단동 461-1 엘카운티 A동 2층 가호(서울야고보지파)",placeType));
          mClusterManager.addItem(new MyItem(35.863965, 127.082367, "전주시온교회", "전주시 덕진구 팔복동2가 774-3번지(도마지파)",placeType));
          mClusterManager.addItem(new MyItem(35.863965, 127.082367, "군산교회", "전북 군산시 조촌동 748-5(도마지파)",placeType));
          mClusterManager.addItem(new MyItem(35.565159, 126.855331, "정읍교회", "전북 정읍시 시기동 356-7 3층(도마지파)",placeType));
          mClusterManager.addItem(new MyItem(35.863965, 127.082367, "고창선교교회", "전북 고창읍 월곡리(간판:화평교회)(도마지파)",placeType));
          mClusterManager.addItem(new MyItem(35.863965, 127.082367, "온세상교회", "전주시 덕진구 팔복동2가 774-3번지(도마지파)",placeType));
          mClusterManager.addItem(new MyItem(35.839728, 128.566537, "대구", "대구광역시 남구 대명10동1623-12번지 대덕빌딩",placeType));
          mClusterManager.addItem(new MyItem(35.863965, 127.082367, "전주교회", "전주시 덕진구 팔복동2가 774-3번지(도마지파)",placeType));
          mClusterManager.addItem(new MyItem(35.863965, 127.082367, "전주교회", "전주시 덕진구 팔복동2가 774-3번지(도마지파)",placeType));




      }

  }


    private class RenderClusterInfoWindow extends DefaultClusterRenderer<MyItem> {

        RenderClusterInfoWindow(Context context, GoogleMap map, ClusterManager<MyItem> clusterManager) {
            super(context, map, clusterManager);
        }

        @Override
        protected void onClusterRendered(Cluster<MyItem> cluster, Marker marker) {
            super.onClusterRendered(cluster, marker);
        }

        @Override
        protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {
            markerOptions.title(item.getTitle());

            super.onBeforeClusterItemRendered(item, markerOptions);
        }

    }




}
