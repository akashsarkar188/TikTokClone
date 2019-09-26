package akashsarkar188.proxynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Profile extends AppCompatActivity {
    TextView tx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tx = findViewById(R.id.tx);

        tx.setOnTouchListener(new OnSwipeTouchListener(Profile.this){
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
            }

            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                finish();
            }
        });

        tx.setText("Profile For User ID \"" + getIntent().getIntExtra("id",0) + "\"\nDue to shortage of time could not build complete profile page");
    }
}
