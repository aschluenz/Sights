package sights.sights.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import model.Place;
import sights.sights.R;

public class PlaceDetailActivity extends AppCompatActivity {

    private TextView mTitle;
    private ImageView PlaceImage;
    private ImageButton addButton;
    private Place mPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        mTitle = (TextView) findViewById(R.id.titleTextView);
        PlaceImage = (ImageView) findViewById(R.id.placeImage);
        addButton = (ImageButton) findViewById(R.id.btn_add);


    }
}
