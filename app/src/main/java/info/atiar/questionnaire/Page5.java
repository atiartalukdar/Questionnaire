package info.atiar.questionnaire;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Page5 extends AppCompatActivity {

    //this is question 6

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page5);
    }

    public void next(View view) {
        Intent intent = new Intent(this,Page9q7.class);
        startActivity(intent);
    }
}
