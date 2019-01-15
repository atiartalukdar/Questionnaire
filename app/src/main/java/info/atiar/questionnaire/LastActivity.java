package info.atiar.questionnaire;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last);
    }

    public void exit(View view) {
        finish();
        moveTaskToBack(true);
    }

    public void reStartTest(View view) {
        Intent intent = new Intent(LastActivity.this,LaunchingActivity.class);
        startActivity(intent);
        finish();
    }
}
