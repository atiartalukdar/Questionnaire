package info.atiar.questionnaire;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.google.firebase.FirebaseApp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import bp.Info;
import bp.SP;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {
    Calendar myCalendar = Calendar.getInstance();
    EditText name,dob,inputFar,inputBalance,inputPersonality;
    RadioGroup rbg, thinkingProblem, thinkingProblemRelative, balanceProblem, stroke, sad, personality;
    Button btn;
    Spinner spinner_1;

    Context mContext = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(MainActivity.this);
        Fabric.with(this, new Crashlytics());
        ButterKnife.bind(this);
        setContentView(R.layout.activity_main);
        mContext = this;


      /*  // TODO: Use your own attributes to track content views in your app
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("Tweet")
                .putContentType("Video")
                .putContentId("1234")
                .putCustomAttribute("Favorites Count", 20)
                .putCustomAttribute("Screen Orientation", "Landscape"));*/

        //Initializing
        name = findViewById(R.id.input_name);
        dob();
        inputFar = findViewById(R.id.input_far);
        gender();
        type();
        inputBalance = findViewById(R.id.input_balance);
        thinkingProblem();
        thinkingProblemRelative();
        balanceProblem();
        stroke();
        sad();
        personalityChanges();
        inputPersonality = findViewById(R.id.input_personality);



    }

    public void next(View v){
        SP.setPreference(mContext,Info.key_name,Info.q_name+getFullName());
        SP.setPreference(mContext,Info.key_dob,Info.q_dob+getDOB());
        setToSP(Info.key_far,Info.q_far+inputFar.getText().toString());
        //gender already set in function
        //type already set from function //type()
        //thinking also in is from function // thinkingProblem()
        //thinking also in is from function // thinkingProblemRelative()
        //Balance problem from function //balanceProblem()
        setToSP(Info.key_balance_reason,Info.q_balance_reason+inputBalance.getText());
        //Strok proble in function //stroke()
        //sad proble in function //sad()
        setToSP(Info.key_personality_specify,Info.q_personality_specify+inputPersonality.getText().toString());

        Intent intent = new Intent(this,Page2.class);
        startActivity(intent);
    }

    //1. get the name from edit text
    private String getFullName(){
        return name.getText().toString();
    }

    //Initializing the dob with pop up date picker
    private void dob(){
        dob  = findViewById(R.id.input_dob);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                dob.setText(sdf.format(myCalendar.getTime()));

            }

        };

        dob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    //2. get the dob from edit text
    private String getDOB(){
        return dob.getText().toString();
    }

    //3. How far school (added from button)

    //4. Initializing and set the gender to shared preference
    private void gender(){
        rbg = findViewById(R.id.radioGroup1);

        rbg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selected = rbg.getCheckedRadioButtonId();
                RadioButton gender = findViewById(selected);
                setToSP(Info.key_gender,Info.q_gender+gender.getText());
                //Toast.makeText(getApplicationContext(),gender.getText() +"",Toast.LENGTH_SHORT).show();
            }
        });

    }

    //5. Initializing and set the type to shared preference
    private void type(){
        spinner_1 = (Spinner) findViewById(R.id.spinnerType);
        List<String> list = new ArrayList<String>();
        list.add("Select Below");
        list.add("Asian");
        list.add("Black");
        list.add("Hispanic");
        list.add("White");
        list.add("Other");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_1.setAdapter(adapter);

        spinner_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(getApplicationContext(),spinner_1.getSelectedItem() +"",Toast.LENGTH_SHORT).show();
                setToSP(Info.key_type,Info.q_type+spinner_1.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(),spinner_1.getSelectedItem() +"Nothing selected",Toast.LENGTH_SHORT).show();

            }
        });


    }

    //6. Initializing and set the type to shared preference
    private void thinkingProblem(){

        thinkingProblem = findViewById(R.id.radioThinkingProblem);

        thinkingProblem.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selected = thinkingProblem.getCheckedRadioButtonId();
                RadioButton think = findViewById(selected);

                setToSP(Info.key_thinking,Info.q_thinking+think.getText());

            }
        });

    }

    //7. Initializing and set the type to shared preference
    private void thinkingProblemRelative(){

        thinkingProblemRelative = findViewById(R.id.radioThinkingProblemRelative);

        thinkingProblemRelative.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selected = thinkingProblemRelative.getCheckedRadioButtonId();
                RadioButton think = findViewById(selected);

                setToSP(Info.key_thinking_relatives,Info.q_thinking_relatives+think.getText());

            }
        });

    }

    //8. Initializing and set the balance problem to shared preference
    private void balanceProblem(){
        inputBalance.setVisibility(View.GONE);

        balanceProblem = findViewById(R.id.radioBalance);

        balanceProblem.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selected = balanceProblem.getCheckedRadioButtonId();
                RadioButton balanceProblem = findViewById(selected);

                setToSP(Info.key_balance,Info.q_balance+balanceProblem.getText());

                //if select yes then the reason added to shapreferefence form button function
                if (balanceProblem.getText().equals("Yes")){
                    inputBalance.setVisibility(View.VISIBLE);
                }if (balanceProblem.getText().equals("No")){
                    inputBalance.setVisibility(View.GONE);
                }
            }
        });

    }

    //9. Initializing and set the strok problem to shared preference
    private void stroke(){

        stroke = findViewById(R.id.radioStroke);

        stroke.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selected = stroke.getCheckedRadioButtonId();
                RadioButton stroke = findViewById(selected);

                setToSP(Info.key_stroke,Info.q_stroke+stroke.getText());

            }
        });

    }

  //10. Initializing and set the feel sad problem to shared preference
    private void sad(){

        sad = findViewById(R.id.radioSad);

        sad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selected = sad.getCheckedRadioButtonId();
                RadioButton sad = findViewById(selected);
                setToSP(Info.key_sad,Info.q_sad+sad.getText());

            }
        });

    }

    //11. Initializing and set the feel sad problem to shared preference
    private void personalityChanges(){
        inputPersonality = findViewById(R.id.input_personality);
        inputPersonality.setVisibility(View.GONE);

        personality = findViewById(R.id.radioPersonality);

        personality.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selected = personality.getCheckedRadioButtonId();
                RadioButton personality = findViewById(selected);

                setToSP(Info.key_personality,Info.q_personality+personality.getText());
                //if yes, the the reason added to sp from button function
                if (personality.getText().equals("Yes")){
                    inputPersonality.setVisibility(View.VISIBLE);
                }if (personality.getText().equals("No")){
                    inputPersonality.setVisibility(View.GONE);
                }
            }
        });

    }


    private void setToSP(String key, String val){
        SP.setPreference(mContext, key,val);
    }


}
