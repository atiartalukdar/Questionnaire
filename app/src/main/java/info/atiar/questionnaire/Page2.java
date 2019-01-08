package info.atiar.questionnaire;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import bp.Info;
import bp.SP;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Page2 extends AppCompatActivity {
    Context mContext = null;

    @BindView(R.id.radioPersonality)    RadioGroup _difficultToThink;
    @BindView(R.id.difficultToThinkYes) RadioButton _difficultToThinkYes;
    @BindView(R.id.difficultToThinkNo)  RadioButton _difficultToThinkNo;
    @BindView(R.id.input_currentDate)   EditText _inputCurrentDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);
        ButterKnife.bind(this);
        mContext = getApplicationContext();

        getPreviousValue();

        _difficultToThink.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.difficultToThinkYes:
                        setToSP(Info.key_page2_difficultToThink,Info.q_page_2difficultToThink+_difficultToThinkYes.getText());
                        break;

                    case R.id.difficultToThinkNo:
                        setToSP(Info.key_page2_difficultToThink,Info.q_page_2difficultToThink+_difficultToThinkNo.getText());
                        break;
                }
            }
        });
    }

    public void next(View v){

        //Memory seted from onCreate
        setToSP(Info.key_page2_todayDataFromMemory,Info.q_page2_todayDataFromMemory+_inputCurrentDate.getText().toString());

        Intent intent = new Intent(this,Page3.class);
        startActivity(intent);
    }


    public void getPreviousValue(){
        Log.e("Page2 - Atiar - ",getSPVal(Info.key_name)+"\n"
                        + getSPVal(Info.key_dob)+"\n"
                +getSPVal(Info.key_far)+"\n"
                +getSPVal(Info.key_gender)+"\n"
                +getSPVal(Info.key_type)+"\n"
                +getSPVal(Info.key_thinking)+"\n"
                +getSPVal(Info.key_thinking_relatives)+"\n"
                +getSPVal(Info.key_balance)+"\n"
                +getSPVal(Info.key_balance_reason)+"\n"
                +getSPVal(Info.key_stroke)+"\n"
                +getSPVal(Info.key_sad)+"\n"
                +getSPVal(Info.key_personality)+"\n"
                +getSPVal(Info.key_personality_specify)+"\n"

        );
    }

    private String getSPVal(String key){
        return SP.getPreference(mContext,key);
    }

    private void setToSP(String key, String val){
        SP.setPreference(mContext, key,val);
    }


}
