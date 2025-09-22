package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

public class Create_Moderator extends AppCompatActivity {

    EditText edName,edOrgnName,edMobileNumber,edEmailId;
    String Name,OrganisationName,MobileNumber,EmailId,EmailPattern;


    Button btnCreateModerator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__moderator);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);
        edName = (EditText) findViewById(R.id.edName);
        edOrgnName=(EditText)findViewById(R.id.edOrgnName);
        edMobileNumber=(EditText) findViewById(R.id.edMobileNumber);
        edEmailId=(EditText) findViewById(R.id.edEmailId);
        btnCreateModerator=(Button) findViewById(R.id.btnCreateModerator);

       btnCreateModerator.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Name = edName.getText().toString();
               OrganisationName = edOrgnName.getText().toString();
               MobileNumber = edMobileNumber.getText().toString();
               EmailId = edEmailId.getText().toString();
                EmailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";



               if (Name.isEmpty()) {
                   edName.setError("Enter Your  Name");
               }
               else if (OrganisationName.isEmpty()) {
                   edOrgnName.setError("Enter Your Organisation Name");
               } else if (MobileNumber.isEmpty()) {
                   edMobileNumber.setError("Enter Your MobileNumber number");
               } else  if (EmailId.isEmpty()) {
                   edEmailId.setError("Enter Your Email Address");
               }else {
                   if (EmailId.matches(EmailPattern)) {
                       //apicall
                       Toast.makeText(getApplicationContext(),"valid email address", Toast.LENGTH_SHORT).show();
                   } else {
                       Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
                   }
               }


           }
       });


    }
    }


