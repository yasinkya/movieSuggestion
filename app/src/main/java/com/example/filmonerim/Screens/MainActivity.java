package com.example.filmonerim.Screens;

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
import com.example.filmonerim.R;
import com.example.filmonerim.model.AllCategories;
import com.example.filmonerim.model.Banners;
import com.example.filmonerim.model.CategoryItem;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //FirebaseDatabase
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    FirebaseDatabase dataBase =FirebaseDatabase.getInstance();

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

        /*
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);
            Notification notification= new NotificationCompat.Builder(MainActivity.this,CHANNEL_1_ID)
                    .setContentTitle("WELCOME")
                    .setContentText(String.valueOf(list.get(1).getMovieId()))
                    .setSmallIcon(R.drawable.mov)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .build();

            notificationManager.notify(1,notification);*/


        seriesBannerslist =new ArrayList<>();

        readData(list -> {
            for (Integer i =0; i<list.size();i++){
                try {
                    seriesBannerslist.add(new Banners(
                            list.get(i).getMovieId(),
                            list.get(i).getMovieName(),
                            list.get(i).getMovieImgUrl(),
                            list.get(i).getMovieFileUrl()
                    ));
                    setBannerPageAdapter(seriesBannerslist);  //ON START SET
                }catch (Exception e){

                }

            }
        });




        //seriesBannerslist.add(banner);
        //seriesBannerslist.add(new Banners(1,"Tenet","https://www.mobilfilm.org/uploads/posts/2020-11/1606248647_tenet-mobil-indir.jpeg","OBawdbFF7M8"));
        //seriesBannerslist.add(new Banners(1,"Captive","https://www.mobilfilm.org/uploads/posts/2021-05/1622061927_captive-katherines-lullaby-mobil-indir.jpg","https://www58.zippyshare.com/d/PgwI9Ccn/37434/Captive.2020.TRALT.3gp"));
        //seriesBannerslist.add(new Banners(1,"Unicorn Store","https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcTmaiDaPhUwZKar0vusxEdM8ePDCl1f1TCoOH2JOljpCCim9OO1","https://doc-0c-24-docs.googleusercontent.com/docs/securesc/9jsgo988bi685jqimcm92fjtl4b76aje/od8c18oqq146lsp7hjd89hjjdn9tc9kh/1622387025000/04412419014754997686/12004855264844732216/1nkAHD4dR5k9j8GUy09Curgs9CC76P9y0?e=download&authuser=0&nonce=u0cvnskm7vm3m&user=12004855264844732216&hash=bpfgan0npniibgvkiv82d7o229k6sg49"));

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
        homeCatItemList.add(new CategoryItem(1,"Chernobyl","https://64.media.tumblr.com/ec7ab4470919ada6a1b4bbdb55a5dbc1/tumblr_pswgoaTGiO1tuobsoo1_1280.jpg","https://drive.google.com/file/d/1nkAHD4dR5k9j8GUy09Curgs9CC76P9y0/view?usp=sharing"));
        homeCatItemList.add(new CategoryItem(2,"Gattaca","https://64.media.tumblr.com/07a939107981389d5213072b21069bbb/tumblr_pxeuw6FJ3M1tuobsoo1_1280.jpg","https://r6---sn-u0g3uxax3-5nge.googlevideo.com/videoplayback?expire=1622252637&ei=_EexYKbxPIOpgAeW1ZegAw&ip=88.252.134.44&id=o-ACh6wlL7Un4YqLqlKkv-MRGTE5bNajmHERskvIg-6IER&itag=18&source=youtube&requiressl=yes&mh=qr&mm=31%2C29&mn=sn-u0g3uxax3-5nge%2Csn-nv47lney&ms=au%2Crdu&mv=m&mvi=6&pcm2cms=yes&pl=24&ctier=A&pfa=5&initcwndbps=657500&hightc=yes&vprv=1&mime=video%2Fmp4&ns=mR7a_KUn9PAu1n2HQ2SM8SwF&gir=yes&clen=44913882&ratebypass=yes&dur=1203.165&lmt=1540938972819852&mt=1622230852&fvip=6&fexp=24001373%2C24007246&c=WEB&txp=5431432&n=IJ4b9uHUDUWynRQKH9&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cctier%2Cpfa%2Chightc%2Cvprv%2Cmime%2Cns%2Cgir%2Cclen%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRQIgGe6utuDyQUT7MSjlMzq2v1A8pJhjZDrK3xgISHViyPICIQCOqSs8Rj7yuAXp2kqPm9Ig6owaZxVCeMC4Ee_ppGkG4g%3D%3D&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpcm2cms%2Cpl%2Cinitcwndbps&lsig=AG3C_xAwRAIgcyKm7AQ06Xa6lto1WcCvWyrb_VK_fgpEpU4TFGiQ4Q0CIEWXkCgj8fvP5r-ZrmDvP2AuTJ4uwlTps_Ty9wDVzQ9d"));
        homeCatItemList.add(new CategoryItem(3,"Creation","https://64.media.tumblr.com/69dd95732f7830896427958b9d68bbf0/tumblr_pssiqhUfnR1tuobsoo1_1280.jpg","https://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        homeCatItemList.add(new CategoryItem(4,"Altered Carbon","https://64.media.tumblr.com/e950c52ee46f2dae9e030eed1ab55c1e/tumblr_psskl7qmFc1tuobsoo1_r1_1280.jpg","https://cload13.cf/hls/oncayoksullukvarken-2020-trdubmp4-b4C2N6HscFH.mp4"));

        List<CategoryItem> homeCatItemList2 = new ArrayList<>();
        homeCatItemList2.add(new CategoryItem(1,"Joker","data:image/jpeg;base64,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","https://s330.fdfiles.net/download.php?name=Joker.2019.mp4&md5=0429f13319b33d3b9e9474c2585b9d5a&fid=oga0xspncsgz&uid=free&speed=59&till=1622387315&trycount=1&ip=81.215.160.21&sid=a4dc4012a97898c6462f321c792b6b8b&browser=0d9b315c9114fee54103aa841dbc2e27&did=2471170939&secure=1&sign=7fee1b00380335604d5a78ce1ff89225"));
        homeCatItemList2.add(new CategoryItem(1,"Musul","https://www.setfilmizle.vip/wp-content/uploads/2020/11/mosul-izle.jpg","https://www874.o0-2.com/token=3qZy9M5ykWkyXd2lkKWiYw/1607092212/88.236.0.0/143/4/d2/dae5385a27e5db4340562eeb1d751d24-720p.mp4"));
        homeCatItemList2.add(new CategoryItem(1,"Nuh Tepesi","https://www.setfilmizle.vip/wp-content/uploads/2020/10/nuh-tepesi-izle.jpg","https://www410.o0-1.com/token=EuqILmQJf-M9zfodSMhcdA/1607015655/46.154.0.0/133/5/79/8dfa61dfb05053cff28c064cc1aca795-1080p.mp4"));
        homeCatItemList2.add(new CategoryItem(1,"Holidate","https://www.setfilmizle.vip/wp-content/uploads/2020/11/holidate-izle.jpg","https://www1951.o0-4.com/token=VwcsMXGhxBaNtKHfXb-q8w/1607015976/46.154.0.0/132/c/26/c244850bf7def49863a8c49f38b4526c-1080p.mp4"));
        homeCatItemList2.add(new CategoryItem(1,"Love And Monsters","https://www.setfilmizle.vip/wp-content/uploads/2020/11/love-and-monsters-izle.jpg","https://www892.o0-2.com/token=zTHVFpC1hci9NI9fHuFI-g/1607016114/46.154.0.0/134/0/cc/7214ea598a85a66236976105315f3cc0-1080p.mp4"));

        //-------------- CREATE AND SET CATEGORY
        allCategoriesList = new ArrayList<>();
        allCategoriesList.add(new AllCategories(1,"Horror",homeCatItemList));
        allCategoriesList.add(new AllCategories(2,"Comedi",homeCatItemList2));
        allCategoriesList.add(new AllCategories(3,"Action",homeCatItemList));
        allCategoriesList.add(new AllCategories(3,"bbbbbb",homeCatItemList2));
        allCategoriesList.add(new AllCategories(3,"ccccc",homeCatItemList));

        setMainRecycler(allCategoriesList);

    }

    private void readData(FirebaseCallback firebaseCallback){

        List<Banners> banners= new ArrayList<>();
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds:snapshot.child("Films").child("Top").child("Series").getChildren()){

                    banners.add(new Banners(
                            Integer.parseInt(ds.getKey().toString()),
                            ds.child("Name").getValue().toString(),
                            ds.child("ImageUrl").getValue().toString(),
                            ds.child("VideoId").getValue().toString()
                    ));

                    /*  banner =new Banners(
                            Integer.parseInt(ds.getKey().toString()),
                            ds.child("Name").getValue().toString(),
                            ds.child("ImageUrl").getValue().toString(),
                            ds.child("VideoId").getValue().toString()
                    );

                    banner.setMovieId(Integer.parseInt(ds.getKey().toString()));
                    banner.setMovieName(ds.child("Name").getValue().toString());
                    banner.setMovieImgUrl(ds.child("ImageUrl").getValue().toString());
                    banner.setMovieFileUrl(ds.child("VideoId").getValue().toString());
                    */
                }
                firebaseCallback.onCallback(banners);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private interface FirebaseCallback {
        void onCallback(List<Banners> list);
    }


//  Navigation bar items
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_favs:
                Intent intent= new Intent(MainActivity.this, FavoritesActivity.class);
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