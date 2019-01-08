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

public class Page4 extends AppCompatActivity {
    Context mContext;
    @BindView(R.id.input_page4q1)    EditText _q1;
    @BindView(R.id.input_page4q2)    EditText _q2;
    @BindView(R.id.input_page4q3)    EditText _q3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page4);
        ButterKnife.bind(this);

        mContext = this;

    }

    public void next(View view) {

        setToSP(Info.key_page4_1,Info.q_page4_1+_q1.getText().toString());
        setToSP(Info.key_page4_2,Info.q_page4_2+_q2.getText().toString());
        setToSP(Info.key_page4_3,Info.q_page4_3+_q3.getText().toString());

        Intent intent = new Intent(this,Page5.class);
        startActivity(intent);

    }

    private void setToSP(String key, String val){
        SP.setPreference(mContext, key,val);
    }

}
