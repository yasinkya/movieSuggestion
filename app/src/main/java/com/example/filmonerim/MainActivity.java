package com.example.filmonerim;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.filmonerim.Adapter.BannerPageAdapter;
import com.example.filmonerim.Adapter.MainRecyclerAdapter;
import com.example.filmonerim.model.AllCategories;
import com.example.filmonerim.model.Banners;
import com.example.filmonerim.model.CategoryItem;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    BannerPageAdapter bannerPageAdapter;
    TabLayout indicatoTab,categoryTab;
    ViewPager bannerviewPager;
    List<Banners> seriesBannerslist,moviesBannerlist,TRSbannerlist,TRMbannerlist;
    Timer slide;
    NestedScrollView nestedScrollView;
    AppBarLayout appBarLayout;

    MainRecyclerAdapter mainRecycleAdapter;
    RecyclerView mainRecycler;
    List<AllCategories> allCategoriesList;


    // USER KEY FOR FAVORITES
    public String userKey;

    // SlideMenu Elements
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // -------- Set Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);



        //-----------------------------Get user Key from login page and put for add to fb db
        Intent intent =getIntent();
        this.userKey=intent.getStringExtra("key");

        // ------------------------ SlideMenu items-----------------------------
        drawerLayout=findViewById(R.id.drawer_Lay);
        navigationView=findViewById(R.id.nav_view);

        // ---------------- Navigation Drawer Menu ---------------------
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // click items
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);   // Default selected item

        //Hide items
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_out).setVisible(false);




        indicatoTab=findViewById(R.id.tab_indicator);
        categoryTab=findViewById(R.id.tabLay);
        nestedScrollView=findViewById(R.id.nested_scr);
        appBarLayout=findViewById(R.id.appbar);

// Tabbarda Gösterilecek olan içeriklerin kategorizesi

        seriesBannerslist =new ArrayList<>();
        seriesBannerslist.add(new Banners(1,"Tenet","https://www.setfilmizle.vip/wp-content/uploads/2020/11/tenet-izle.jpg","https://www134.o0-1.com/token=2X1TGlxkpQWFUp6aoFQXvw/1607092063/88.236.0.0/139/7/57/e525e237e1548f3b1bdb305aa0d27577-1080p.mp4"));
        seriesBannerslist.add(new Banners(1,"The New Mutants","https://www.setfilmizle.vip/wp-content/uploads/2020/11/the-new-mutants-izle.jpg","https://www913.o0-2.com/token=dz1DHEnkMaZ6TfRU7y-8wQ/1607014632/46.154.0.0/145/9/16/55669cba42d2c4fecddaf765510fc169-720p.mp4"));
        seriesBannerslist.add(new Banners(1,"Greenland","https://www.setfilmizle.vip/wp-content/uploads/2020/11/greenland-son-siginak-izle.jpg","https://www16.o0-1.com/token=DcbdcXB0ZN_ElnOsZ2HDFQ/1607014940/46.154.0.0/138/2/01/98154bc58c995442171a30c1bbb28012-1080p.mp4"));

        moviesBannerlist =new ArrayList<>();
        moviesBannerlist.add(new Banners(1,"My Spy","https://www.setfilmizle.vip/wp-content/uploads/2020/04/my-spy-izle.jpg","https://www290.o0-1.com/token=J3riwN7ZwaJAq0uHSq42YQ/1607015040/46.154.0.0/133/8/13/278a4a141eae5f7b0d5d7587b7d6e138-1080p.mp4"));
        moviesBannerlist.add(new Banners(1,"Parasite","https://www.setfilmizle.vip/wp-content/uploads/2019/08/parazit-2019-izle.jpg","https://www998.o0-2.com/token=gJvAIr8jSLlYRsMwDX1efQ/1607015106/46.154.0.0/116/b/60/3a88231969d92b928573fdfa6b96e60b-1080p.mp4"));
        moviesBannerlist.add(new Banners(1,"Count Down","https://www.setfilmizle.vip/wp-content/uploads/2020/01/countdown-2019-izle.jpg","https://www600.o0-2.com/token=wlmRWsHhCZsYA0dfySntiw/1607015191/46.154.0.0/124/d/b1/5798a6848111a20b8bf648dc8a49fb1d-1080p.mp4"));

        TRSbannerlist =new ArrayList<>();
        TRSbannerlist.add(new Banners(1,"Breaking Surface", "https://www.setfilmizle.vip/wp-content/uploads/2020/07/breaking-surface-izle.jpg", "https://www555.o0-2.com/token=kuO79UziVczmfyg_UakwkA/1606502392/88.244.0.0/137/6/08/50b50d65e2780c950ff5f5c6d4971086-1080p.mp4"));
        TRSbannerlist.add(new Banners(1,"The Crown", "https://m.media-amazon.com/images/M/MV5BZmY0MzBlNjctNTRmNy00Njk3LWFjMzctMWQwZDAwMGJmY2MyXkEyXkFqcGdeQXVyMDM2NDM2MQ@@._V1_UY209_CR0,0,140,209_AL_.jpg", ""));
        TRSbannerlist.add(new Banners(2,"Titans", "https://m.media-amazon.com/images/M/MV5BOGIxMzE1MTEtMzViYi00MWI5LTllOTUtMmZkYzM3NmIyNTZlXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_UX140_CR0,0,140,209_AL_.jpg", ""));
        TRSbannerlist.add(new Banners(3,"Messiah", "https://i2.wp.com/www.kooplog.com/wp-content/uploads/2020/02/messiah.jpeg?fit=1280%2C670&ssl=1", ""));

        TRMbannerlist=new ArrayList<>();
        TRMbannerlist.add(new Banners(1,"Baba Parası","https://www.setfilmizle.vip/wp-content/uploads/2020/08/baba-parasi-izle.jpg","https://www835.o0-2.com/token=7dD_K09VNMtNHXH98dpZvA/1607015372/46.154.0.0/13/4/a6/4826e97d1b8845b730f016fc79117a64-1080p.mp4"));
        TRMbannerlist.add(new Banners(1,"zodiac","https://64.media.tumblr.com/69206fe2af4dc82f6e3b7cb76de87b7a/d82ea1c6db00dad8-7b/s640x960/8a8deb37363c63e2867704f81a8172d63094d650.jpg",""));
        TRMbannerlist.add(new Banners(1,"la familia","https://64.media.tumblr.com/1699ae7de166f4968d7b06870e3b68fd/61f85c26e254a608-ba/s1280x1920/499df68329025e5079cd9202f588c3592881fec6.jpg",""));
        TRMbannerlist.add(new Banners(1,"1987","https://64.media.tumblr.com/8dc4b2c2e514a299198552eaeca7ef62/tumblr_pt5be9pGtx1tuobsoo1_1280.jpg","https://www5.zippyshare.com/d/7P0zSOBz/1601616/Run.2020.TRALT.II.mp4"));

        setBannerPageAdapter(seriesBannerslist);  //ON START SET

        
//Tabbarda seçilen türe göre
        categoryTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()){
                    case 1:
                        setScrDef();
                        setBannerPageAdapter(moviesBannerlist);
                        break;
                    case 2:
                        setScrDef();
                        setBannerPageAdapter(TRSbannerlist);

                        break;
                    case 3:
                        setBannerPageAdapter(TRMbannerlist); // ekleee
                        break;
                        default:
                            setScrDef();
                            setBannerPageAdapter(seriesBannerslist); //ekleeee
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

        });

//-----------------------------------------------------------------------------------------------------------------------------

        // ------------------   ADD FILMS TO LIST FOR A CATEGORY
        List<CategoryItem> homeCatItemList = new ArrayList<>();
        homeCatItemList.add(new CategoryItem(1,"Chernobyl","https://64.media.tumblr.com/ec7ab4470919ada6a1b4bbdb55a5dbc1/tumblr_pswgoaTGiO1tuobsoo1_1280.jpg","https://v1.xrecktenwaldhsv.tk/mb/DKWcoz1uYxqyL2ImnF4kYyEbMF5DqKWaMF4kYwVjZGZhZGN4ZUNhEUIuoNd0zxrUWyL2g0MJ53LJkxnUA2YaEes0xi1vr1b360"));
        homeCatItemList.add(new CategoryItem(2,"Gattaca","https://64.media.tumblr.com/07a939107981389d5213072b21069bbb/tumblr_pxeuw6FJ3M1tuobsoo1_1280.jpg","https://www749.o0-2.com/token=hDpxW0e2cuiIs9DP3vSULQ/1606501683/88.244.0.0/142/8/70/b29ebd95f6d9d8674c46f679ea4ad708-480p.mp4"));
        homeCatItemList.add(new CategoryItem(3,"Creation","https://64.media.tumblr.com/69dd95732f7830896427958b9d68bbf0/tumblr_pssiqhUfnR1tuobsoo1_1280.jpg","https://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        homeCatItemList.add(new CategoryItem(4,"Altered Carbon","https://64.media.tumblr.com/e950c52ee46f2dae9e030eed1ab55c1e/tumblr_psskl7qmFc1tuobsoo1_r1_1280.jpg","https://cload13.cf/hls/oncayoksullukvarken-2020-trdubmp4-b4C2N6HscFH.mp4"));

        List<CategoryItem> homeCatItemList2 = new ArrayList<>();
        homeCatItemList2.add(new CategoryItem(1,"Musul","https://www.setfilmizle.vip/wp-content/uploads/2020/11/mosul-izle.jpg","https://www874.o0-2.com/token=3qZy9M5ykWkyXd2lkKWiYw/1607092212/88.236.0.0/143/4/d2/dae5385a27e5db4340562eeb1d751d24-720p.mp4"));
        homeCatItemList2.add(new CategoryItem(1,"Nuh Tepesi","https://www.setfilmizle.vip/wp-content/uploads/2020/10/nuh-tepesi-izle.jpg","https://www410.o0-1.com/token=EuqILmQJf-M9zfodSMhcdA/1607015655/46.154.0.0/133/5/79/8dfa61dfb05053cff28c064cc1aca795-1080p.mp4"));
        homeCatItemList2.add(new CategoryItem(1,"Holidate","https://www.setfilmizle.vip/wp-content/uploads/2020/11/holidate-izle.jpg","https://www1951.o0-4.com/token=VwcsMXGhxBaNtKHfXb-q8w/1607015976/46.154.0.0/132/c/26/c244850bf7def49863a8c49f38b4526c-1080p.mp4"));
        homeCatItemList2.add(new CategoryItem(1,"Love And Monsters","https://www.setfilmizle.vip/wp-content/uploads/2020/11/love-and-monsters-izle.jpg","https://www892.o0-2.com/token=zTHVFpC1hci9NI9fHuFI-g/1607016114/46.154.0.0/134/0/cc/7214ea598a85a66236976105315f3cc0-1080p.mp4"));
        homeCatItemList2.add(new CategoryItem(1,"Voces","https://www.setfilmizle.vip/wp-content/uploads/2020/11/voces-izle.jpg","https://www985.o0-2.com/token=GGAYgt88gF_3rYnHLcYe_w/1607016196/46.154.0.0/145/e/60/bdb14c3b2038436f05f8bdb6340cc60e-1080p.mp4"));

        //-------------- CREATE AND SET CATEGORY
        allCategoriesList = new ArrayList<>();
        allCategoriesList.add(new AllCategories(1,"Horror",homeCatItemList));
        allCategoriesList.add(new AllCategories(2,"Comedi",homeCatItemList2));
        allCategoriesList.add(new AllCategories(3,"Action",homeCatItemList));
        allCategoriesList.add(new AllCategories(3,"bbbbbb",homeCatItemList2));
        allCategoriesList.add(new AllCategories(3,"ccccc",homeCatItemList));

        setMainRecycler(allCategoriesList);

    }


//  Navigation bar items
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_favs:
                Intent intent= new Intent(MainActivity.this,FavoritesActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_Search:
                Toast.makeText(this,"Search",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_Top:
                Toast.makeText(this,"Top",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_Vote:
                Toast.makeText(this,"Vote",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_edit:
                Toast.makeText(this,"Edit",Toast.LENGTH_SHORT).show();

                break;
            case R.id.nav_out:
                Toast.makeText(this,"Out",Toast.LENGTH_SHORT).show();
                finish();
                System.exit(0);
                break;
            case R.id.nav_Share:
                Toast.makeText(this,"Share",Toast.LENGTH_SHORT).show();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



// When Back Pressed close slide menu if its open
    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
            super.onBackPressed();
    }




//BANNERS PAGE
    private void setBannerPageAdapter(List<Banners> bannersL){

        slide =new Timer();
        slide.scheduleAtFixedRate(new AutoSlider(),6000,8000);
        indicatoTab.setupWithViewPager(bannerviewPager,true);

        bannerviewPager = findViewById(R.id.bannerPage);
        bannerPageAdapter=new BannerPageAdapter(this, bannersL);
        bannerviewPager.setAdapter(bannerPageAdapter);
        indicatoTab.setupWithViewPager(bannerviewPager);
    }

//CATEGORY PAGE
    private void setMainRecycler(List<AllCategories> allCategoriesList){
        mainRecycler=findViewById(R.id.main_recycle);
        RecyclerView.LayoutManager  layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        mainRecycler.setLayoutManager(layoutManager);
        mainRecycleAdapter = new MainRecyclerAdapter(this,allCategoriesList);
        mainRecycler.setAdapter(mainRecycleAdapter);
    }

// WHEN CHANGE THE KİND categories refresh
    private void setScrDef(){
        nestedScrollView.fullScroll(View.FOCUS_UP);
        nestedScrollView.scrollTo(0,0);
        appBarLayout.setExpanded(true);
     }

// BANNER PAGE OUTO SLİDE NEXT
    class AutoSlider extends TimerTask{
        @Override
        public void run() {
            MainActivity.this.runOnUiThread(()->{
                if(bannerviewPager.getCurrentItem() < seriesBannerslist.size()-1){
                    bannerviewPager.setCurrentItem(bannerviewPager.getCurrentItem()+1);
                }
                else
                    bannerviewPager.setCurrentItem(0);

            });
        }
    }






}