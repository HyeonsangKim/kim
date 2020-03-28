package com.example.sct;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import noman.googleplaces.PlaceType;

public class MyItem implements ClusterItem {

    private final LatLng mPosition;
    private String mTitle;
    private String mSnippet;
    private double mlat;
    private double mlng;
    private  PlaceType mplaceType;
    public MyItem(double lat,double lng){
        mPosition = new LatLng(lat,lng);
    }
    public MyItem(double lat,double lng,String title,String snippet,PlaceType placeType){
        mPosition = new LatLng(lat,lng);
        mTitle = title;
        mSnippet = snippet;
        mlat = lat;
        mlng = lng;
        mplaceType = placeType;
    }
    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle;

    }
    public PlaceType getPlaceType() {
        return mplaceType;
    }

    public double getLat() {
        return mlat;
    }
    public double getLng() {
        return mlng;
    }

    @Override
    public String getSnippet() {
        return mSnippet;
    }

}
