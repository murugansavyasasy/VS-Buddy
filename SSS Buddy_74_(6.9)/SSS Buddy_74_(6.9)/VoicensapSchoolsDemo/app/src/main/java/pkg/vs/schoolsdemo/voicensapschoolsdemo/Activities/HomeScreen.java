package pkg.vs.schoolsdemo.voicensapschoolsdemo.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity.ChangePassword;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Activity.Login;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Adapter.HomeScreen_Adapter;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass.HomeScreen_Class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.Models.SharedPreference_class;
import pkg.vs.schoolsdemo.voicensapschoolsdemo.R;

public class HomeScreen extends AppCompatActivity {
    private RecyclerView recyclerView;
    private HomeScreen_Adapter adapter;
    private List<HomeScreen_Class> menulist;
    SharedPreferences sharepref;
    String Vims_usertype, Schl_usertype;

    //   AdView mAdView,adView2;

//    String Vims_usertype="1",Schl_usertype="MyTeam";
//    String Vims_usertype="19",Schl_usertype="ChannelPartner";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);

        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);

        menulist = new ArrayList<>();

        adapter = new HomeScreen_Adapter(this, menulist);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        Vims_usertype = SharedPreference_class.getSh_v_Usertype(HomeScreen.this);
        Schl_usertype = SharedPreference_class.getShSchlUsertype(HomeScreen.this);

//        Intent intent = getIntent();
//        Vims_usertype=intent.getStringExtra("VimsUserTypeId");

        HomeScreen_Menu();

//        mAdView = findViewById(R.id.adView);
//        adView2 = findViewById(R.id.adView2);
//
//        mAdView.setAdListener(new AdListener() {
//            @Override
//            public void onAdClicked() {
//
//                Log.d("adClicked","Clicked");
//                // Code to be executed when the user clicks on an ad.
//
//            }
//            @Override
//            public void onAdClosed() {
//
//                // Code to be executed when the user is about to return
//                // to the app after tapping on an ad.
//
//            }
//            @Override
//            public void onAdFailedToLoad(LoadAdError adError) {
//
//                // Code to be executed when an ad request fails.
//            }
//            @Override
//            public void onAdImpression() {
//
//                // Code to be executed when an impression is recorded
//                // for an ad.
//
//            }

//            @Override
//            public void onAdLoaded() {
//
//                // Code to be executed when an ad finishes loading.
//            }
//            @Override
//            public void onAdOpened() {
//
//                // Code to be executed when an ad opens an overlay that
//                // covers the screen.
//            }
//
//        });

//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//                Log.d("initializationStatus", initializationStatus.toString());
//
//            }
//        });
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
//        adView2.loadAd(adRequest);

    }

    private void HomeScreen_Menu() {
        int[] Icons = new int[]{
                R.drawable.schoolcreate,
                R.drawable.demolist,
                R.drawable.createpoc,
                R.drawable.demolist,
                R.drawable.circularlist,
                R.drawable.statusreport,
                R.drawable.collection,
                R.drawable.settings_image,
                R.drawable.verify,
                R.drawable.approve,
                R.drawable.customer_details,
                R.drawable.localconveyance,
                R.drawable.tour,
                R.drawable.invoice,
                R.drawable.pending_pay,
                R.drawable.toursettle,
                R.drawable.target_sales_1,
                R.drawable.director_approval,
                R.drawable.feedback,
                R.drawable.chat,
                R.drawable.alert_menu_1,
                R.drawable.create_moder,
                R.drawable.video_icon,
                R.drawable.zero_activity
        };


        String schoolLoginID = SharedPreference_class.getShSchlLoginid(HomeScreen.this);
        String vimsIDUser = SharedPreference_class.getUserid(HomeScreen.this);
        if (!schoolLoginID.equals("0")) {

            if ((Vims_usertype.equals("19"))) {
                HomeScreen_Class menus = new HomeScreen_Class("Create Demo", Icons[0], 0);
                menulist.add(menus);

                menus = new HomeScreen_Class("Demo List", Icons[1], 1);
                menulist.add(menus);

                menus = new HomeScreen_Class("Management Videos", Icons[22], 21);
                menulist.add(menus);
            } else {
                HomeScreen_Class menus = new HomeScreen_Class("Create Demo", Icons[0], 0);
                menulist.add(menus);

                menus = new HomeScreen_Class("Demo List", Icons[1], 1);
                menulist.add(menus);

//            menus = new HomeScreen_Class("Create POC/Live", Icons[2], 2);
//            menulist.add(menus);

                menus = new HomeScreen_Class("School List", Icons[3], 3);
                menulist.add(menus);

                menus = new HomeScreen_Class("Circular List", Icons[4], 4);
                menulist.add(menus);

                menus = new HomeScreen_Class("Status Report", Icons[5], 5);
                menulist.add(menus);

                menus = new HomeScreen_Class("Record Collection", Icons[6], 6);
                menulist.add(menus);

                menus = new HomeScreen_Class("Important Info", Icons[7], 7);
                menulist.add(menus);

                menus = new HomeScreen_Class("Zero Activity", Icons[23], 22);
                menulist.add(menus);


                if (Schl_usertype.equals("MyTeam")) {


                } else if (Schl_usertype.equals("Admin") || (Schl_usertype.equals("Support"))) {
//                menus = new HomeScreen_Class("Support Verify", Icons[8], 8);
//                menulist.add(menus);
//                Log.d("schooltype", Schl_usertype);
                    menus = new HomeScreen_Class("Alert", Icons[20], 21);
                    menulist.add(menus);

                }

                if ((Schl_usertype.equals("Support")) || (Schl_usertype.equals("MyTeam"))) {

                } else if (Schl_usertype.equals("Admin")) {
//                menus = new HomeScreen_Class("Approve POC", Icons[9], 9);
//                menulist.add(menus);

                }
            }
        }


        //VIMS


        if ((Vims_usertype.equals("19")) && (Schl_usertype.equals("ChannelPartner"))) {

        } else if ((Vims_usertype.equals("19")) || (Schl_usertype.equals("MyTeam"))) {

        } else {
            HomeScreen_Class menus = new HomeScreen_Class("Customer Details", Icons[10], 10);
            menulist.add(menus);
            menus = new HomeScreen_Class("Local Conveyence", Icons[11], 11);
            menulist.add(menus);
            menus = new HomeScreen_Class("Advance TourExpense", Icons[12], 12);
            menulist.add(menus);
            menus = new HomeScreen_Class("Tour Settlement", Icons[15], 15);
            menulist.add(menus);
//            menus = new HomeScreen_Class("Alert", Icons[20], 20);
//            menulist.add(menus);

        }

        if ((Vims_usertype.equals("19"))) {

        } else {
            HomeScreen_Class menus = new HomeScreen_Class("FeedBack", Icons[18], 18);
            menulist.add(menus);

            menus = new HomeScreen_Class("Chat", Icons[19], 19);
            menulist.add(menus);

            menus = new HomeScreen_Class("School Documents", Icons[21], 20);
            menulist.add(menus);

            menus = new HomeScreen_Class("Management Videos", Icons[22], 21);
            menulist.add(menus);
        }


        adapter.notifyDataSetChanged();
    }


    public static class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing = 0;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column - 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.changepassword: {
//
                Intent intent = new Intent(HomeScreen.this, ChangePassword.class);
                startActivity(intent);
                return (true);
            }


            case R.id.Logout: {
                sharepref = getSharedPreferences("Mypref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharepref.edit();
                editor.clear();
                editor.commit();
                Intent next = new Intent(HomeScreen.this, Login.class);
                startActivity(next);
                finish();
                return (true);
            }


            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}



