package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;

import java.util.ArrayList;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter.UnreadChatCountAdapter;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.UnreadChatModel;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;
import retrofit2.Call;

public class UnreadCountChatScreen extends AppCompatActivity {


    RecyclerView RecyclerList;
    UnreadChatCountAdapter mAdapter;

    String vimsUserTypeId="",UserId="";
    Call<JsonArray> call;

    private ArrayList<UnreadChatModel> unReadChatsCountList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unread_chat_recycle);



        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        RecyclerList=(RecyclerView) findViewById(R.id.RecyclerList);

        vimsUserTypeId = SharedPreference_class.getSh_v_Usertype(UnreadCountChatScreen.this);
        UserId = SharedPreference_class.getUserid(UnreadCountChatScreen.this);

        unReadChatsCountList = (ArrayList<UnreadChatModel>) getIntent().getSerializableExtra("unreadList");
        mAdapter = new UnreadChatCountAdapter(unReadChatsCountList,UnreadCountChatScreen.this);
        LinearLayoutManager circularLayoutManager = new LinearLayoutManager(UnreadCountChatScreen.this);
        RecyclerList.setLayoutManager(circularLayoutManager);
        RecyclerList.setItemAnimator(new DefaultItemAnimator());
        RecyclerList.setAdapter(mAdapter);


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



//    private void (String msg) {
//        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//    }

    private void alert(String reason) {
        AlertDialog.Builder builder = new AlertDialog.Builder(UnreadCountChatScreen.this);
        builder.setTitle(reason);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();

    }
}

