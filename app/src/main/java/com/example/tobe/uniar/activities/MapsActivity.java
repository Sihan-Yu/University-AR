package com.example.tobe.uniar.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tobe.uniar.R;
import com.example.tobe.uniar.data.GroundOverlayData;
import com.example.tobe.uniar.data.MarkerData;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraMoveListener;
import com.google.android.gms.maps.GoogleMap.OnGroundOverlayClickListener;
import com.google.android.gms.maps.GoogleMap.OnIndoorStateChangeListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.IndoorBuilding;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import org.json.JSONObject;

/**
 * The Maps Activity is the main activity that the Uniar Program will start running.
 * This is where the Map gets setup.
 * @version 2.0
 * @since   2016-12-07
 */

public class MapsActivity extends AppCompatActivity implements OnCameraMoveListener,
        OnGroundOverlayClickListener,
        OnIndoorStateChangeListener,
        OnInfoWindowClickListener,
        OnMapClickListener,
        OnMapReadyCallback,
        OnMarkerClickListener,
        OnMyLocationButtonClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    /**
     * Request code for location permission request.
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    // stores the previous activated floor and previous focused building
    private int previousFloor;
    private IndoorBuilding previousIndoorBuilding;
    private boolean mPermissionDenied = false;
    private GoogleMap mMap;
    // This manages the markers on the map
    private MarkerData mMarkerData;
    private Marker ClickedMarker;
    // This manages the ground overlays
    private GroundOverlayData mGroundOverlayData;
    // This is where the map camera initialise to
    private JsonObjectRequestUtils JsoObjReqUtils;
    private static final LatLng UONJUBILEE = new LatLng(52.952008, -1.186172);
    private LatLng mLastLatLng;
    GoogleApiClient mGoogleApiClient;
    Button ButtonGoHereFromCurrentLocation;
    Button ButtonDeleteRoute;
    Button ButtonMainMenu;
    ImageButton ButtonARView;
    ImageButton ButtonChatbot;
    private boolean mainMenuClicked = false;
    RelativeLayout fog;

    /**
     * onCreate runs at the start of the activity.
     * The fragment of Google map gets setup.
     * @param savedInstanceState passed on.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // make connection to Google Api Client (for getting current location)
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        ButtonGoHereFromCurrentLocation = (Button) findViewById(R.id.ButtonGoHereFromCurrentLocation);
        ButtonDeleteRoute = (Button) findViewById(R.id.ButtonDeleteRoute);
        ButtonMainMenu = (Button) findViewById(R.id.mainmenuButton);

        ButtonARView = (ImageButton) findViewById(R.id.ARButton);
        ButtonARView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapsActivity.this, ARViewActivity.class));
            }
        });

        ButtonChatbot = (ImageButton) findViewById(R.id.chatbotButton);
        ButtonChatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapsActivity.this, ChatBotDummyActivity.class));
            }
        });

        fog = (RelativeLayout) findViewById(R.id.fog);
        fog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    onButtonSetVisibility(ButtonMainMenu);
            }
        });
    }

    /**
     * This callback is triggered when the map is ready to be used.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     *
     * @param googleMap the map object
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // disable Toolbar which appears when a marker is clicked (toolbar has icon link to Google Map App)
        mMap.getUiSettings().setMapToolbarEnabled(false);
        //disable some gestures and enable the zoom control
        mMap.getUiSettings().setTiltGesturesEnabled(false);
        mMap.getUiSettings().setZoomGesturesEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        // set a listener for map being clicked
        mMap.setOnMapClickListener(this);
        // adds my location button
        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();
        // Move to place University of Nottingham Jubilee Campus
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(UONJUBILEE, 15));
        // setup markers
        mMarkerData = new MarkerData(mMap, this);
        // set a listener for marker and info window being clicked
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        // setup ground overlays
        mGroundOverlayData = new GroundOverlayData(mMap, this);
        // set a listener for ground overlay being clicked
        mMap.setOnGroundOverlayClickListener(this);
        // set a listener for camera move
        mMap.setOnCameraMoveListener(this);
        //set a listener for Indoor state change
        mMap.setOnIndoorStateChangeListener(this);
    }

    /**
     * This is triggered when map is clicked.
     *
     * @param latLng LatLng object of the place clicked.
     */
    @Override
    public void onMapClick(LatLng latLng) {
        ButtonGoHereFromCurrentLocation.setVisibility(View.GONE);
    }

    /**
     * This is triggered when my location button is clicked. Moves the camera to centre on the
     * user's location.
     *
     * @return false defined by Google Map api
     */
    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "You are here", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    /**
     * This is triggered when a marker is clicked.
     *
     * @param marker The marker object.
     * @return If true, camera will not move to the marker and info window will not appear.
     *         If false, camera will move to the marker and info window will appear.
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(this, "Marker Clicked", Toast.LENGTH_SHORT).show();
        ClickedMarker = marker;
        if(JsoObjReqUtils != null && JsoObjReqUtils.getRoute() != null && JsoObjReqUtils.getRoute().isVisible()) {
            return false;
        }
        else {
            ButtonGoHereFromCurrentLocation.setVisibility(View.VISIBLE);
        }
        return false; // does usual behavior (camera move and info window appear)
    }

    /**
     * This is triggered when info window is clicked.
     * Opens up a new activity of the location wiki page.
     *
     * @param marker the marker that was clicked
     */
    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, "Location Wiki", Toast.LENGTH_SHORT).show();
        // converts google's marker Id to int, m0 to 0, m1 to 1, etc.
        int markerId = Integer.parseInt(marker.getId().substring(1));
        // go to the location wiki activity and pass the marker ID on
        Intent intent = new Intent();
        intent.setClass(this, LocationWikiActivity.class);
        intent.putExtra("markerId", markerId);
        startActivity(intent);
    }

    /**
     * This is triggered when ground overlay is clicked.
     *
     * @param groundOverlay the ground overlay that was clicked
     */
    @Override
    public void onGroundOverlayClick(GroundOverlay groundOverlay) {
        // converts google's ground overlay Id to int, go0 to 0, go1 to 1, etc.
        int groundOverlayId = Integer.parseInt(groundOverlay.getId().substring(2));
        // go to the location wiki activity and pass the ground overlay ID on
        Intent intent = new Intent();
        intent.setClass(this, LocationWikiActivity.class);
        intent.putExtra("groundOverlayId", groundOverlayId);
        startActivity(intent);
    }

    /**
     * This is triggered when "go here from current location" button is clicked
     * Creates a path from the users current location to the destination they have clicked.
     *
     * @param view the view currently displayed
     */
    public void onButtonGoHereFromCurrentLocationClick(View view) {
        if(ClickedMarker != null) {
            String requestUrl = "https://maps.googleapis.com/maps/api/directions/json?origin=" +
                    mLastLatLng.latitude + "," + mLastLatLng.longitude +
                    "&destination=" +
                    ClickedMarker.getPosition().latitude + "," + ClickedMarker.getPosition().longitude +
                    "&mode=walking" +
                    "&key=" +
                    this.getString(R.string.google_direction_api_key);
            JsoObjReqUtils = new JsonObjectRequestUtils(this, mMap);
            JsoObjReqUtils.requestJsonObject(requestUrl);
        }
        ButtonGoHereFromCurrentLocation.setVisibility(View.GONE);
        ButtonDeleteRoute.setVisibility(View.VISIBLE);
    }

    public void onButtonDeleteRouteClick(View view) {
        JsoObjReqUtils.getRoute().setVisible(false);
        ButtonDeleteRoute.setVisibility(View.GONE);
    }

    /**
     * This function determines whether the menu has been opened and displays a translucent overlay
     * on top of the map if it is open.
     *
     * @param view the view currently displayed
     */
    public void onButtonSetVisibility(View view) {
     if(!mainMenuClicked) {
         //ButtonARView.setVisibility(View.VISIBLE);
         //ButtonSettings.setVisibility(View.VISIBLE);
         //ButtonChatbot.setVisibility(View.VISIBLE);
         fog.setVisibility(View.VISIBLE);
         mainMenuClicked = true;
         fog.setBackgroundColor(0xd9ffffff);
     }
     else {
         //ButtonARView.setVisibility(View.GONE);
         //ButtonSettings.setVisibility(View.GONE);
         //ButtonChatbot.setVisibility(View.GONE);
         fog.setVisibility(View.GONE);
         mainMenuClicked = false;
         fog.setBackgroundColor(0x00000000);
     }
    }

    /**
     * This is triggered when there is a change to Camera.
     * Hides markers when camera zoom is greater than 17, else show markers and hide all ground overlays.
     */
    @Override
    public void onCameraMove() {
        if(mMap.getCameraPosition().zoom > 17) {
            //Toast.makeText(this, "" + mMap.getCameraPosition().zoom, Toast.LENGTH_SHORT).show();
            mMarkerData.hideMarkers();
        }
        else {
            mMarkerData.showMarkers();
            mGroundOverlayData.hideGroundOverlays();
        }
    }

    /**
     * This is triggered when there is a change to the indoor floor level.
     * Hides the previous floor's ground overlays
     * Shows the current floor's ground overlays
     *
     * @param indoorBuilding represents building
     */
    @Override
    public void onIndoorLevelActivated(IndoorBuilding indoorBuilding) {
        // this calculation inverts floor level, because google's floor level index starts from top to bottom
        int currentFloor = Math.abs(indoorBuilding.getActiveLevelIndex()-indoorBuilding.getLevels().size()+1);
        mGroundOverlayData.hideGroundOverlays(previousFloor);
        mGroundOverlayData.showGroundOverlays(currentFloor);
        previousFloor = currentFloor;
        previousIndoorBuilding = indoorBuilding;
    }

    /**
     * This is triggered when a building is focused (the floor level selector pops up)
     * or a focused building is changed.
     * Shows the ground overlays at floor 0.
     */
    @Override
    public void onIndoorBuildingFocused() {
        if(previousIndoorBuilding == null) { // first time a building gets focused
            mGroundOverlayData.showGroundOverlays(0);
            previousFloor = 0;
        }
        else {
            // when changing focused building, reset the previous building's floor to 0
            // this makes sure all building stays at floor 0 when unfocused)
            previousIndoorBuilding.getLevels().get(previousIndoorBuilding.getLevels().size()-1).activate();
            mGroundOverlayData.hideGroundOverlays();
            mGroundOverlayData.showGroundOverlays(0);
        }
    }

    /**
     * This is triggered when there is a result for permission request.
     *
     * @param requestCode type of request
     * @param permissions permissions
     * @param grantResults grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // checks if the request is location permission request
        if(requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }
        if(PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        }
        else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    /**
     * This is called after onCreate or onRestart.
     */
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    /**
     * This is called when activity is no longer visible to the user.
     */
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    /**
     * This is called when the map fragment is resumed.
     * Checks if permission is granted.
     * If not granted, displays permission error.
     */
    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if(mPermissionDenied) {
            // Permission was not granted, display error dialog
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * This method checks permission then enable my location of the map.
     * If permission is missing then request for it.
     */
    private void enableMyLocation() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission to access to the location is missing
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
        else if(mMap != null) {
            // Access to the location has been granted to the app
            mMap.setMyLocationEnabled(true);
        }
    }

    /**
     * Displays the missing permission.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog.newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    /**
     * This invokes when the connect request has successfully completed.
     *
     * @param bundle Bundle of data by Google Play services. May be null if no content is provided by the service.
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            enableMyLocation();
        }
        else {
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                mLastLatLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            }
        }
    }

    /**
     * Called when the client is temporarily in a disconnected state.
     *
     * @param i The reason for the disconnection.
     */
    @Override
    public void onConnectionSuspended(int i) {

    }

    /**
     * Called when there was an error connecting the client to the service.
     *
     * @param connectionResult A ConnectionResult that can be used for resolving the error,
     *                         and deciding what sort of error occurred.
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    // currently unused, but may be useful in the future
    /**
     * Checks if the map is ready (which depends on whether the Google Play services APK is
     * available. This should be called prior to calling any methods on GoogleMap.
     */
    /*
    private boolean checkReady() {
        if (mMap == null) {
            Toast.makeText(this, R.string.map_not_ready, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    */

}
