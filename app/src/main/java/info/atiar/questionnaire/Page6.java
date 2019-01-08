package info.atiar.questionnaire;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import bp.Info;
import bp.SP;
import butterknife.BindView;
import butterknife.ButterKnife;

//Question 9

public class Page6 extends AppCompatActivity {
    Context mContext;
    @BindView(R.id.input_animal1)    EditText _animal1;
    @BindView(R.id.input_animal2)    EditText _animal2;
    @BindView(R.id.input_animal3)    EditText _animal3;
    @BindView(R.id.input_animal4)    EditText _animal4;
    @BindView(R.id.input_animal5)    EditText _animal5;
    @BindView(R.id.input_animal6)    EditText _animal6;
    @BindView(R.id.input_animal7)    EditText _animal7;
    @BindView(R.id.input_animal8)    EditText _animal8;
    @BindView(R.id.input_animal9)    EditText _animal9;
    @BindView(R.id.input_animal10)    EditText _animal10;
    @BindView(R.id.input_animal11)    EditText _animal11;
    @BindView(R.id.input_animal12)    EditText _animal12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page6);
        ButterKnife.bind(this);
        mContext = this;

    }

    public void next(View view) {

        String animals =
                "1. " +  _animal1.getText().toString() +
                "2. " +  _animal2.getText().toString() + "<br>" +
                "3. " +  _animal3.getText().toString() +
                "4. " +  _animal4.getText().toString() + "<br>" +
                "5. " +  _animal5.getText().toString() +
                "6. " +  _animal6.getText().toString() + "<br>" +
                "7. " +  _animal7.getText().toString() +
                "8. " +  _animal8.getText().toString() + "<br>" +
                "9. " +  _animal9.getText().toString() +
                "10. " +  _animal10.getText().toString() + "<br>" +
                "11. " +  _animal11.getText().toString() +
                "12. " +  _animal12.getText().toString() + "<br>";


        setToSP(Info.key_page6_animals,Info.q_page6_animals + animals);
        Intent intent = new Intent(this,Page11q10.class);
        startActivity(intent);
    }

    private void setToSP(String key, String val){
        SP.setPreference(mContext, key,val);
    }

}
