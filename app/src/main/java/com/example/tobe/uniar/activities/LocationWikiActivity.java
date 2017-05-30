package com.example.tobe.uniar.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tobe.uniar.R;
import com.example.tobe.uniar.customise.CustomGroundOverlay;
import com.example.tobe.uniar.customise.CustomMarker;
import com.example.tobe.uniar.data.GroundOverlayData;
import com.example.tobe.uniar.data.MarkerData;

/**
 * LocationWikiActivity class is the activity of location wiki.
 * This activity starts when the window info of a marker from the MapsActivity is clicked.
 * More detailed description of a marker is shown here.
 *
 * @version 2.0
 * @since   2016-12-07
 */

public class LocationWikiActivity extends AppCompatActivity {

    private CustomMarker Marker;
    private CustomGroundOverlay GroundOverlay;
    private boolean buttonEditPressed = false;
    TextView Title;
    EditText Description;
    Button ButtonEdit;

    /**
     * onCreate runs at the start of the activity.
     * sets the title and description of the marker or ground overlay that is being passed from the MapsActivity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_wiki);

        //gets the views and buttons
        Title = (TextView) findViewById(R.id.Title);
        Description = (EditText) findViewById(R.id.Description);
        ButtonEdit = (Button) findViewById(R.id.ButtonEdit);

        //gets the values passed from the MapsActivity
        Bundle Extras = getIntent().getExtras();
        if(Extras != null) {
            // checks if the value's key is markerId
            if(Extras.getInt("markerId", -1) != -1) {
                // use the marker id passed from the MapsActivity to get the Custom Marker from MarkerData
                Marker = MarkerData.getMarker(Extras.getInt("markerId"));
                // sets the title and description
                Title.setText(Marker.getTitle());
                Description.setText(Marker.getDescription());
            }
            else if(Extras.getInt("groundOverlayId", -1) != -1) {
                // use the ground overlay id passed from the MapsActivity to get the custom gound overlay from GroundOverlayData
                GroundOverlay = GroundOverlayData.getGroundOverlay(Extras.getInt("groundOverlayId"));
                // sets the title and description
                Title.setText(GroundOverlay.getTitle());
                Description.setText(GroundOverlay.getDescription());
            }
        }
    }

    /**
     * This triggers when the edit button is clicked.
     * Allows user to edit description.
     * Save the new description when clicking again.
     * @param view the view that is being clicked
     */
    public void onButtonEditClick(View view) {
        if(!buttonEditPressed) {
            Description.setEnabled(true);
            ButtonEdit.setText("Save");
            buttonEditPressed = true;
        }
        else {
            Description.setEnabled(false);
            ButtonEdit.setText("Edit");
            buttonEditPressed = false;
            if(Marker != null) {
                Marker.setDescription(Description.getText().toString());
            }
            else if(GroundOverlay != null) {
                GroundOverlay.setDescription(Description.getText().toString());
            }
        }
    }
}
