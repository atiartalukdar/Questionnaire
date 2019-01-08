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

public class Page3 extends AppCompatActivity {
    Context mContext;
    @BindView(R.id.input_image1_text)    EditText _image1Ans;
    @BindView(R.id.input_image2_text)    EditText _image2Ans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);
        ButterKnife.bind(this);
        mContext = this;
    }

    public void next(View v){
        setToSP(Info.key_page3_1stImageAns, Info.q_page3_1stImageAns + _image1Ans.getText().toString());
        setToSP(Info.key_page3_2ndImageAns, Info.q_page3_2ndImageAns + _image2Ans.getText().toString());

        Intent intent = new Intent(this,Page4.class);
        startActivity(intent);

    }

    private void setToSP(String key, String val){
        SP.setPreference(mContext, key,val);
    }

}
