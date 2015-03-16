package ch.hslu.mobpro.tqzimmer.ui_demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

public class ViewDemoActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_viewdemo);

        RatingBar rating = (RatingBar)findViewById(R.id.rbaRating);
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                TextView ratingText = (TextView)findViewById(R.id.tbxRating);
                ratingText.setText("Letzte Bewertung: " + rating);
            }
        });
    }
}
