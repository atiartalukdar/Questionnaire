package info.atiar.questionnaire;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LaunchingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launching);
        getSupportActionBar().hide();
    }

    public void startTest(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
