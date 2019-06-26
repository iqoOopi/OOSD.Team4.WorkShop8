package com.erict135.oosdteam4workshop8;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.erict135.oosdteam4workshop8.model.Coordinates;
import com.erict135.oosdteam4workshop8.model.Customer;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private final int VACATIONRADIUSKM = 200;
    private Coordinates myCoords;
    private Handler handler;
    private Handler handler2;
    private ArrayList<String> vacImages;
    private Vector<ImageView> imageViewVector;
    private File image;
    private Uri fileUri;
    private Random random;

    private FrameLayout.LayoutParams layoutParams;
    private ImageView ivImage;
    private FrameLayout container;

    private int deviceWidth,deviceHeight;
    private final int MAXIMAGES = 8;
    private AlphaAnimation animFadeIn,animFadeOut;

    private NavigationDrawerFragment mNavigationDrawerFragment;

    private CharSequence mTitle;

    private static Customer c;
    private static boolean loggedin;

    protected static String getToken(){return c.getTOKEN(); }
    public static boolean isLoggedin(){
        return loggedin;
    }
    public static void setAgentId(int agentId){c.setAgentId(agentId);}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loggedin = getIntent().getBooleanExtra("loggedin",false);

        setContentView(R.layout.activity_main);


        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

        if(loggedin) {
            Gson gson = new Gson();

            c = gson.fromJson(getIntent().getStringExtra("customer"), Customer.class);
            c.setTOKEN(getIntent().getStringExtra("token"));
            c = (c != null) ? c : new Customer();
            myCoords = new Coordinates();
            myCoords.setLatitude(51.08799399999999);
            myCoords.setLongitude(-114.124865);

            vacImages = new ArrayList<>();
            ArrayList<String> images = getAllImagesOnDevice();
            ArrayList<String> imagesWithGPS = getImagesWithGPS(images);
            vacImages = getVacationImages(imagesWithGPS);

//        ///
//        // Grab strFiles from here
//        ///
//
//        String strFiles = "";
//        Double distance;
//
//        Coordinates coords;
//        for (int i = 0; i < vacImages.size(); i++) {
//            coords = getGPSLocation(vacImages.get(i));
//            distance = getDistanceFromGPS(coords.getLatitude(), coords.getLongitude(), c.getCustHomeLatitude(), c.getCustHomeLongitude());
//            strFiles += distance + " : " + vacImages.get(i) + "\n";
//        }

            if (vacImages.size() > 0) {
                loadImages();
            } else {
                Toast.makeText(getBaseContext(), "Enable geotagging on your photos!", Toast.LENGTH_LONG).show();
            }
        }
//        ///
//        // Wallpaper hi-jacker
//        ///
//
//        handler2 = new Handler();
//        Runnable runnable2 = new Runnable() {
//            @Override
//            public void run() {
//                WallpaperManager wallpaperManager = WallpaperManager.getInstance(getBaseContext());
//                try {
//                    Bitmap wallpaper = BitmapFactory.decodeFile(vacImages.get(random.nextInt(vacImages.size())));
//                    wallpaperManager.setBitmap(Bitmap.createScaledBitmap(wallpaper,deviceWidth,deviceHeight,false));
//                }catch (IOException e){
//
//                }
//                handler2.postDelayed(this,1000);
//            }
//        };
//        handler2.postDelayed(runnable2,0);
    }
    private void loadImages(){
        container = (FrameLayout) findViewById(R.id.fragment_container);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        deviceHeight=dm.heightPixels;
        deviceWidth=dm.widthPixels;

        imageViewVector = new Vector<>();
        random = new Random();

        ///
        // Background image loader
        ///
        handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                image = new File(vacImages.get(random.nextInt(vacImages.size())));
                fileUri = Uri.fromFile(image);
                ivImage = new ImageView(getBaseContext());

                layoutParams = new FrameLayout.LayoutParams(deviceWidth/4, deviceHeight/4);
                layoutParams.setMargins(random.nextInt(deviceWidth),random.nextInt(deviceHeight),0,0);
                ivImage.setLayoutParams(layoutParams);

                ivImage.setImageAlpha(60);
                ivImage.setBackgroundColor(Color.argb(0,0,0,0));

                Glide.with(getBaseContext()).load(fileUri).fitCenter().transition(withCrossFade(3000)).into(ivImage);

                animFadeOut = new AlphaAnimation(0.24f,0);
                animFadeOut.setDuration(1000);
                animFadeOut.setFillAfter(true);
                animFadeOut.setStartOffset(3000);

                ivImage.startAnimation(animFadeOut);

                imageViewVector.add(ivImage);
                container.addView(ivImage);

                if(imageViewVector.size()>MAXIMAGES){
                    ImageView tmp = imageViewVector.remove(0);
                    container.removeView(tmp);
                }

                handler.postDelayed(this,3000);
            }
        };
        handler.postDelayed(runnable, 0);
    }
    ///
    // Vacation images retrieval section
    ///
    private ArrayList<String> getAllImagesOnDevice() {

        ArrayList<String> allImages = new ArrayList<>();

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        String imagePath = "";
        while (cursor.moveToNext()) {
            imagePath = cursor.getString(column_index_data);
            allImages.add(imagePath);
        }
        return allImages;
    }
    private ArrayList<String> getImagesWithGPS(ArrayList<String> allImages){
        ArrayList<String> imagesWithGPS=new ArrayList<>();
        ExifInterface exif;
        try {
            for (int i = 0; i < allImages.size(); i++) {
                exif = new ExifInterface(allImages.get(i));
                if(exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE)!=null && exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE)!=null){
                    imagesWithGPS.add(allImages.get(i));
                }
            }
        }catch(IOException e){

        }
        return imagesWithGPS;
    }
    private ArrayList<String> getVacationImages(ArrayList<String> imagesWithGPS){
        ArrayList<String> vacImages = new ArrayList<>();
        for(int i=0;i<imagesWithGPS.size();i++){
            if(isVacationImage(imagesWithGPS.get(i))){
                vacImages.add(imagesWithGPS.get(i));
            }
        }
        return vacImages;
    }
    private boolean isVacationImage(String imagePath){
        ///
        // Distance will be in meters
        ///
        Coordinates coords = getGPSLocation(imagePath);
        double distance = getDistanceFromGPS(coords.getLatitude(),coords.getLongitude(),myCoords.getLatitude(),myCoords.getLongitude());
        distance/=1000;
        return distance>VACATIONRADIUSKM;
    }
    private double getDistanceFromGPS(double LAT1,double LON1,double LAT2,double LON2){
        ///
        // Haversine formula to find absolute distance between two
        // points on Earth given gps coordinates, lat lon.
        // Using radius of Earth = 6371000 meters
        // d = 2R * sqrt(sin^2((lat_2 - lat_1)/2) + (cos lat_1)(cos lat_2)sin^2((lon_2 - lon_1)/2))
        ///
        final double EARTHRADIUS = 6371000;
        LAT1= Math.toRadians(LAT1);
        LON1= Math.toRadians(LON1);
        LAT2= Math.toRadians(LAT2);
        LON2= Math.toRadians(LON2);
        double sinARG1 = Math.sin((LAT2-LAT1)/2)* Math.sin((LAT2-LAT1)/2);
        double sinARG2 = Math.sin((LON2-LON1)/2)* Math.sin((LON2-LON1)/2);
        double cosARG = Math.cos(LAT1)* Math.cos(LAT2);
        return 2*EARTHRADIUS* Math.asin(Math.sqrt(sinARG1+cosARG*sinARG2));
    }
    private Coordinates getGPSLocation(String path){
        Coordinates coords = new Coordinates();
        ExifInterface exif;
        try {
            exif = new ExifInterface(path);
            coords.setLatitude(convertExifToDegrees(exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE)) * (exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF).contains("N")?1:-1));
            coords.setLongitude(convertExifToDegrees(exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE)) * (exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF).contains("W")?-1:1));
        }catch (IOException e){

        }
        return coords;
    }
    private double convertExifToDegrees(String exif){
        ///
        // Example GPS Exif data: "51/1,9/1,15/1"
        ///
        String[] elements = exif.split(",");
        double hours = Double.valueOf(elements[0].substring(0,elements[0].indexOf('/')));
        double minutes = Double.valueOf(elements[1].substring(0,elements[1].indexOf('/')));
        double seconds = Double.valueOf(elements[2].substring(0,elements[2].indexOf('/')));
        double fractionalSeconds = (minutes*60 + seconds)/3600;
        return hours + fractionalSeconds;
    }
    ///
    // Navigator methods
    ///
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getFragmentManager();
        if(loggedin) {
            switch (position) {
                case 0:
                    try {
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, HomeFragment.newInstance(position + 1,c))
                                .commit();
                    }catch (IllegalStateException ex){
                        ///
                        // Fragment already active
                        ///
                    }
                    break;
                case 1:
                    try {
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, BrowseFragment.newInstance(position + 1, c))
                                .commit();
                    }catch (IllegalStateException ex){
                        ///
                        // Fragment already active
                        ///
                    }
                    break;
                case 2:
                    try {
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, BookingFragmentList.newInstance(position + 1, c))
                                .commit();
                    }catch (IllegalStateException ex){
                        ///
                        // Fragment already active
                        ///
                    }
                    break;
                case 3:
                    try {
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, AccountFragment.newInstance(position + 1,c))
                                .commit();
                    }catch (IllegalStateException ex){
                        ///
                        // Fragment already active
                        ///
                    }
                    break;
                case 4:
                    ///
                    // LOGOUT
                    ///
                    c = new Customer();
                    Toast.makeText(getApplicationContext(), "Logged out", Toast.LENGTH_LONG).show();
                    finish();
                    break;
                default:
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, HomeFragment.newInstance(position + 1,c))
                            .commit();
                    break;
            }
        }else{
            switch (position){
                case 0:
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, HomeFragment.newInstance(position + 1,c))
                            .commit();
                    break;
                case 1:
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, BrowseFragment.newInstance(position + 1,c))
                            .commit();
                    break;
                case 2:
                    c = new Customer();
                    finish();
                    break;
                default:
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, HomeFragment.newInstance(position + 1,c))
                            .commit();
                    break;
            }
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_home);
                break;
            case 2:
                mTitle = getString(R.string.title_browse);
                break;
            case 3:
                mTitle = getString(R.string.title_booking);
                break;
            case 4:
                mTitle = getString(R.string.title_account);
                break;
            case 5:
                mTitle = getString(R.string.title_logout);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }
}