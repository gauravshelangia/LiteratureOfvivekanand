package com.vivekanand.literature.literatureofvivekanand;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vivekanand.literature.literatureofvivekanand.Constants.Constants;
import com.vivekanand.literature.literatureofvivekanand.adapter.ViewPagerAdapter;
import com.vivekanand.literature.literatureofvivekanand.sharedPreference.SharedPreferenceLoader;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Gallery extends AppCompatActivity {

    private ArrayList<Integer> images;
    private BitmapFactory.Options options;
    private ViewPager viewPager;
    private View btnNext, btnPrev;
    private FragmentStatePagerAdapter adapter;
    private LinearLayout thumbnailsContainer;
    private LinearLayout imageDesContainer;
    private TextView imageDescView;
    private SharedPreferenceLoader sharedPreferenceLoader;
    static int from = 0;
    static int to = 10;
    static boolean setFromThumbnail = false;

    private final static int[] resourceIDs = new int[]{R.drawable.photo1, R.drawable.photo2,
            R.drawable.photo3, R.drawable.photo4, R.drawable.photo5, R.drawable.photo6,
            R.drawable.photo7};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferenceLoader = new SharedPreferenceLoader(this);
        if (sharedPreferenceLoader.loadNightMode()) {
            setTheme(R.style.AppThemeNight);
        } else {
            setTheme(R.style.AppThemeDay);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        images = new ArrayList<>();

        //find view by id
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        setViewPagerListener();
        thumbnailsContainer = (LinearLayout) findViewById(R.id.container);
        imageDesContainer = (LinearLayout) findViewById(R.id.imageDes);
        imageDescView = findViewById(R.id.imageDescription1);
        btnNext = findViewById(R.id.next);
        btnPrev = findViewById(R.id.prev);

        btnPrev.setOnClickListener(onClickListener(0));
        btnNext.setOnClickListener(onClickListener(1));

        showImages();

    }

    private void setViewPagerListener(){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (!setFromThumbnail){
                    reloadPagerView(position);
                }else {
                    setFromThumbnail= false;
                }
                imageDescView.setText(Constants.imageDes[from+position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private View.OnClickListener onClickListener(final int i) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i > 0) {
                    //next page
                    if (viewPager.getCurrentItem() < viewPager.getAdapter().getCount() - 1) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    }
                } else {
                    //previous page
                    if (viewPager.getCurrentItem() > 0) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                    }
                }
            }
        };
    }

    private void setImagesData(int from, int to) {
//        AssetManager assetManager = getApplicationContext().getAssets();
//        Field[] drawablesFields = com.vivekanand.literature.literatureofvivekanand
//                .R.drawable.class.getFields();
//        ArrayList<Drawable> drawables = new ArrayList<>();
//        ArrayList<Integer> resImages = new ArrayList<>();
//        for (Field field : drawablesFields) {
//            try {
//                drawables.add(getResources().getDrawable(field.getInt(null)));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        images.clear();
        for (int i = from; i < to; i++) {
            images.add(Constants.imageIDs[i]);
        }
    }

    private void inflateThumbnails() {
        if (thumbnailsContainer.getChildCount()>0){
            thumbnailsContainer.removeAllViews();
        }
        for (int i = 0; i < images.size(); i++) {
            View imageLayout = getLayoutInflater().inflate(R.layout.item_image, null);
            ImageView imageView = (ImageView) imageLayout.findViewById(R.id.img_thumb);
            imageView.setOnClickListener(onChagePageClickListener(i));
            options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            options.inDither = false;
            options.inScaled=true;
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), images.get(i), options);
            imageView.setImageBitmap(bitmap);
            //set to image view
            imageView.setImageBitmap(bitmap);
            //add imageview

            thumbnailsContainer.addView(imageLayout);
        }
    }

    private View.OnClickListener onChagePageClickListener(final int i) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFromThumbnail=true;
                viewPager.setCurrentItem(i);
                reloadPagerView(i);
            }
        };
    }

    private void showImages() {
        setImagesData(from, to);

        // init viewpager adapter and attach
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), images);
        viewPager.setAdapter(adapter);

        inflateThumbnails();
    }

    private void restartApp() {
        Intent intent = new Intent(getApplicationContext(), Gallery.class);
        startActivity(intent);
        finish();
    }

    private void reloadPagerView(int imagePagerPosition){
        int a = imagePagerPosition % 10;
        if (a == 9) {
            int temp = to+8;
            if (temp <= 80) {
                from = to-2;
                to = to + 8;
                showImages();
            } else {
                from = 70;
                to = 80;
                showImages();
                Toast.makeText(getApplicationContext(), "please slide to the previous photos", Toast.LENGTH_SHORT).show();
            }
        }
        if (a == 0 ) {
            int temp = from - 8;
            if (temp >= 0){
                to = from+2;
                from = from - 8;
                showImages();
            } else {
                to = 10;
                from = 0;
                showImages();
                Toast.makeText(getApplicationContext(), "please slide to the next photos", Toast.LENGTH_SHORT).show();

            }
        }
        Runtime.getRuntime().gc();
    }
}
