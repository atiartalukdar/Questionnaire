package info.atiar.questionnaire;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import bp.Info;
import bp.SP;
import butterknife.BindView;
import butterknife.ButterKnife;
import mailassistant.GMail;

public class Page7 extends AppCompatActivity {
    Context mContext;
    Activity mActivity;

    StorageReference storageReference;
    String d1="",d2="",d3="",d4="";

    String fromEmail = "esagetest@gmail.com";
    String fromPassword = "eSageTest0221";
    List toEmailList;
    String toEmail = "swajan.talukdar@gmail.com";
    String emailSubject = "New Record "+ SP.getCurrentTimeUsingCalendar();
    String emailBody = "";
    //tipically last page
    @BindView(R.id.input_haveYouFinished)    EditText _haveYouFinished;
    @BindView(R.id.input_email)    EditText _email;
    @BindView(R.id.input_layout_email)    TextInputLayout _layoutEmail;
    @BindView(R.id.input_layout_haveYouFinished)    TextInputLayout _layoutHaveYouFinished;

    String page3Image1,page3Image2,page9q7Image,page10q8Image,page11q10Image,page13q12Image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page7);
        ButterKnife.bind(this);
        mContext = this;
        mActivity = this;
        storageReference = FirebaseStorage.getInstance().getReference("ans");
        toEmailList = new ArrayList();


            toEmailList.add(toEmail);

    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void next(View view) {

        if (_email.getTag().equals("email")){
            generateEmailBody();
            if (!isEmailValid(_email.getText().toString())){
                _email.setError("Enter Valid Email");
            }else {
                toEmailList.add(_email.getText().toString());
                _email.setTag("notEmail");
                try{
                    new SendMailTask(mActivity).execute(fromEmail,
                            fromPassword, toEmailList, emailSubject, emailBody);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }


        }else{
            new UploadImagesTOServer().execute(); // initialize asynchronous task
            SP.setPreference(mContext, Info.key_page7_haveYouFinished, Info.q_page7_haveYouFinished+_haveYouFinished.getText().toString());

            _layoutHaveYouFinished.setVisibility(View.GONE);
            _layoutEmail.setVisibility(View.VISIBLE);
            _email.setTag("email");
        }

    }

    String downloadLink = "";
    private String uploadImage(final String fileName, String path){
        Bitmap bitmap = BitmapFactory.decodeFile(path);

        if (bitmap != null){
            final StorageReference fileReference = storageReference.child(fileName
                    + "."+"jpg");


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = fileReference.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Log.e("Data Upload: ", "Failed");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                    Log.e("Data Upload: ", "success");
                    Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String dlink = uri.toString();
                            Log.e("download url : ", dlink);
                            //TODO: update sharedpreferences with the download link
                            downloadLink = dlink;
                            SP.setPreference(mContext,fileName+"link",dlink);

                        }
                    });

                }
            });

        }else {
            Toast.makeText(getApplicationContext(),"No Image Found", Toast.LENGTH_SHORT).show();
            downloadLink = "nolink";
        }

        return downloadLink;
    }

    public class UploadImagesTOServer extends AsyncTask<Void, Void, Void> {

        private final ProgressDialog dialog = new ProgressDialog(mContext);

        protected void onPreExecute() {
            this.dialog.setMessage("Loading...");
            this.dialog.setCancelable(false);
            this.dialog.show();
        }
        @Override
        protected Void doInBackground(Void... params) {
           SP.setPreference(mContext,"d1",uploadImage(Info.fileName_page9q7_image,SP.getPreference(mContext,Info.key_page9q7_image)));
           SP.setPreference(mContext,"d2",uploadImage(Info.fileNam_page10q8_image,SP.getPreference(mContext,Info.key_page10q8_image)));
           SP.setPreference(mContext,"d3",uploadImage(Info.fileNam_page11q10_image,SP.getPreference(mContext,Info.key_page11q10_image)));
           SP.setPreference(mContext,"d4",uploadImage(Info.fileNam_page13q12_image,SP.getPreference(mContext,Info.key_page13q12_image)));
           return null;
        }

        protected void onPostExecute(Void result) {


            generateEmailBody();

            Log.e("Atiar downloadURL: ", "d1 " + getSPVal("d1"));
            Log.e("Atiar downloadURL: ", "d2 " + getSPVal("d2"));
            Log.e("Atiar downloadURL: ", "d3 " + getSPVal("d3"));
            Log.e("Atiar downloadURL: ", "d4 " + getSPVal("d4"));
            // Here if you wish to do future process for ex. move to another activity do here
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    public class SendMailTask extends AsyncTask {

        private ProgressDialog statusDialog;
        private Activity sendMailActivity;

        public SendMailTask(Activity activity) {
            sendMailActivity = activity;
        }

        protected void onPreExecute() {
            statusDialog = new ProgressDialog(sendMailActivity);
            statusDialog.setMessage("Getting ready...");
            statusDialog.setIndeterminate(false);
            statusDialog.setCancelable(false);
            statusDialog.show();
        }

        @Override
        protected Object doInBackground(Object... args) {
            try {
                Log.i("SendMailTask", "About to instantiate GMail...");
                publishProgress("Processing input....");
                GMail androidEmail = new GMail(args[0].toString(),
                        args[1].toString(), (List) args[2], args[3].toString(),
                        args[4].toString());
                publishProgress("Preparing mail message....");
                androidEmail.createEmailMessage();
                publishProgress("Sending...");
                androidEmail.sendEmail();
                publishProgress("Email Sent.");
                Log.i("SendMailTask", "Mail Sent.");
            } catch (Exception e) {
                publishProgress(e.getMessage());
                Log.e("SendMailTask", e.getMessage(), e);
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Object... values) {
            statusDialog.setMessage(values[0].toString());

        }

        @Override
        public void onPostExecute(Object result) {
            statusDialog.dismiss();
            Intent intent = new Intent(Page7.this,LastActivity.class);
            startActivity(intent);
            finish();
        }}



    private String getSPVal(String key){
        return SP.getPreference(mContext,key);
    }

    public String createImageLink(String imageLink){

        String link = "<img border=\"0\" src=\"" + imageLink + "\"width=\"304\" height=\"228\"/>";
        return link;
    }

    private void generateEmailBody(){
        emailBody = getSPVal(Info.key_name) + "<br>"+
                getSPVal(Info.key_dob) + "<br>" +
                getSPVal(Info.key_far) + "<br>" +
                getSPVal(Info.key_gender) + "<br>" +
                getSPVal(Info.key_type) + "<br>" +
                getSPVal(Info.key_thinking) + "<br>" +
                getSPVal(Info.key_thinking_relatives) + "<br>" +
                getSPVal(Info.key_balance) + "<br>" +
                getSPVal(Info.key_balance_reason) + "<br>" +
                getSPVal(Info.key_stroke) + "<br>" +
                getSPVal(Info.key_sad) + "<br>" +
                getSPVal(Info.key_personality) + "<br>" +
                getSPVal(Info.key_personality_specify) +"<br>" +
                "<br> <br>"+
                getSPVal(Info.key_page2_difficultToThink) + "<br>" +
                getSPVal(Info.key_page2_todayDataFromMemory)  + "<br>" +
                "<br> <br>"+
                createImageLink(Info.q_page3_1stImageLink) +
                getSPVal(Info.key_page3_1stImageAns)   + "<br>" +
                createImageLink(Info.q_page3_2ndImageLink) +
                getSPVal(Info.key_page3_2ndImageAns)   + "<br>" +
                "<br> <br>"+
                getSPVal(Info.key_page4_1)  + "<br>" +
                getSPVal(Info.key_page4_2)  + "<br>" +
                getSPVal(Info.key_page4_3)  + "<br>" +
                "<br> <br>"+
                getSPVal(Info.key_page6_animals) + "<br>" +
                "<br> <br>"+
                createImageLink(getSPVal(Info.fileName_page9q7_image+"link")) +
                createImageLink(getSPVal(Info.fileNam_page10q8_image+"link")) +
                createImageLink(getSPVal(Info.fileNam_page11q10_image+"link")) +
                createImageLink(getSPVal(Info.fileNam_page13q12_image+"link")) +
                "<br> <br>"+
                getSPVal(Info.key_page7_haveYouFinished);

    }
}
