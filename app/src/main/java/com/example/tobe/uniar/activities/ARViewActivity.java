package com.example.tobe.uniar.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tobe.uniar.R;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.wikitude.architect.ArchitectStartupConfiguration;
import com.wikitude.architect.ArchitectView;

public class ARViewActivity extends AppCompatActivity {

    private ArchitectView architectView;
    private static final int WIKITUDE_PERMISSIONS_REQUEST_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arview);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, WIKITUDE_PERMISSIONS_REQUEST_CAMERA);
        }
        this.architectView = (ArchitectView) this.findViewById(R.id.architectView);
        final ArchitectStartupConfiguration config = new ArchitectStartupConfiguration();
        config.setLicenseKey("dH7XkviWEY9BV4eRzBg25lLpSDlns5vn3gAxBqh/wgs44Z7yRYfHsG4+L6inp2rDF6Kq7SdyWmU9LNfzpUH+GlDDk60UuMzCaasyobptP69cXjW4uhII83kX6zyPhOYytGMNQwgJEAePJWejKo6p8M6gmmEvum/E7Pia1DoS3SlTYWx0ZWRfXwA/Yx5oiaHKWxHrvOHenMbIYaFlocscWFE6mBUaB6ClxrLQfL/o+dz8kHtKEGTmbwmUFIvdnA/dPqVYa7puSUgdm3xcPuuQA0ZA+SWfQn9OopCCxia8/xj6FyR1PXwniveOvrvGMG4/ZuH41SeD4hAHIPciSZ0o4d5LdmriZXIGrJCGnapPj9r0hto+QJ698TC7nH01iqwAKi5U5HUjmzX885h2cl2JyVtBvYMB8DgWhfjnmc37xMnt1XJYg2egjLtK485OwdYXQdnOYfMNxGIXx989BFT+Jic+RgH3auUzIImxlFGGRBDoWSz+4etQXriejhKj2YVuOvTPNKWQPg4/cBkn4dB53xdGRkpqNR+4yccGgIxDuDiGC4DXMGdctY2xo0Ufnw0wJD/T9W3sRgeK2UK1uMfyboByVB1KyVi/C2Znab21c5Z/e0UEZErs1WjtXFdNdqPZDlg2qoymWeKK5+dqKCIUEnsYkFs0VeWfh9j3y34MVzU=");
        this.architectView.onCreate(config);

    }

    //
//    Function creates the AR function by loading in home page of a website which point sto the javascript file.
//
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        architectView.onPostCreate();

        try {
            this.architectView.load("file:///android_asset/ArViewLaunch/index.html");
        } catch (Exception e) {
        }
    }

//
//  This passes on the states from android studio to the javascript AR function.
//

    @Override
    protected void onResume() {
        super.onResume();

        architectView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        architectView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();

        architectView.onPause();
    }
}
