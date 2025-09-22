package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

public class SettingsScreen extends AppCompatActivity implements View.OnClickListener {

    TextView lblAlternateNumber1, lblAlternateNumber2, lblMissedCallNumber1, lblMissedCallNumber2, lblMissedCallNumber3, lblMissedCallNumber4,
            lblMissedCallNumber5, lblHelpLine, lblSalesEnquiry, lblSupprortMail, lblAllMail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.settings_screen);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        lblAlternateNumber1 = (TextView) findViewById(R.id.lblAlternateNumber1);
        lblAlternateNumber2 = (TextView) findViewById(R.id.lblAlternateNumber2);
        lblMissedCallNumber1 = (TextView) findViewById(R.id.lblMissedCallNumber1);
        lblMissedCallNumber2 = (TextView) findViewById(R.id.lblMissedCallNumber2);
        lblMissedCallNumber3 = (TextView) findViewById(R.id.lblMissedCallNumber3);
        lblMissedCallNumber4 = (TextView) findViewById(R.id.lblMissedCallNumber4);
        lblMissedCallNumber5 = (TextView) findViewById(R.id.lblMissedCallNumber5);
        lblHelpLine = (TextView) findViewById(R.id.lblHelpLine);
        lblSalesEnquiry = (TextView) findViewById(R.id.lblSalesEnquiry);
        lblSupprortMail = (TextView) findViewById(R.id.lblSupprortMail);
        lblAllMail = (TextView) findViewById(R.id.lblAllMail);


        lblAlternateNumber1.setOnClickListener(this);
        lblAlternateNumber2.setOnClickListener(this);
        lblMissedCallNumber1.setOnClickListener(this);
        lblMissedCallNumber2.setOnClickListener(this);
        lblMissedCallNumber3.setOnClickListener(this);
        lblMissedCallNumber4.setOnClickListener(this);
        lblMissedCallNumber5.setOnClickListener(this);
        lblHelpLine.setOnClickListener(this);
        lblSalesEnquiry.setOnClickListener(this);
        lblSupprortMail.setOnClickListener(this);
        lblAllMail.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return (true);

                default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.lblAlternateNumber1:
                String alternatenumber1=lblAlternateNumber1.getText().toString();
                dialingAction(alternatenumber1);
                break;
            case R.id.lblAlternateNumber2:
                String alternatenumber2=lblAlternateNumber2.getText().toString();
                dialingAction(alternatenumber2);
                break;
            case R.id.lblMissedCallNumber1:
                String missedcallNumber1=lblMissedCallNumber1.getText().toString();
                dialingAction(missedcallNumber1);
                break;
            case R.id.lblMissedCallNumber2:
                String missedcallNumber2=lblMissedCallNumber2.getText().toString();
                dialingAction(missedcallNumber2);
                break;
            case R.id.lblMissedCallNumber3:
                String missedcallNumber3=lblMissedCallNumber3.getText().toString();
                dialingAction(missedcallNumber3);
                break;
            case R.id.lblMissedCallNumber4:
                String missedcallNumber4=lblMissedCallNumber4.getText().toString();
                dialingAction(missedcallNumber4);
                break;
            case R.id.lblMissedCallNumber5:
                String missedcallNumber5=lblMissedCallNumber5.getText().toString();
                dialingAction(missedcallNumber5);
                break;

            case R.id.lblHelpLine:
                String helpline=lblHelpLine.getText().toString();
                dialingAction(helpline);
                break;

            case R.id.lblSalesEnquiry:
                String enguiry=lblSalesEnquiry.getText().toString();
                dialingAction(enguiry);
                break;

            case R.id.lblSupprortMail:
                String SupportMail=lblSupprortMail.getText().toString();

                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                /* Fill it with Data */
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{SupportMail});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");

                /* Send it off to the Activity-Chooser */
                this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));

                break;

            case R.id.lblAllMail:
                String AllMail=lblAllMail.getText().toString();
                final Intent emailIntent1 = new Intent(android.content.Intent.ACTION_SEND);
                /* Fill it with Data */
                emailIntent1.setType("plain/text");
                emailIntent1.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{AllMail});
                emailIntent1.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
                emailIntent1.putExtra(android.content.Intent.EXTRA_TEXT, "Text");

                /* Send it off to the Activity-Chooser */
                this.startActivity(Intent.createChooser(emailIntent1, "Send mail..."));

                break;


            default:
                break;
        }
    }

    private void dialingAction(String alternatenumber1) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+alternatenumber1));
        startActivity(intent);
    }
}

