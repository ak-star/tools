package com.lxh.tools.disklrucache;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.lak.tools.file.DiskLruCacheHelper;
import com.lxh.tools.R;

import java.io.IOException;

/**
 * Created by lawrence
 */

public class DiskLruCacheActivity extends AppCompatActivity {
    DiskLruCacheHelper helper = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disklrucache);

        try {
            helper = new DiskLruCacheHelper.Builder()
                    .build(DiskLruCacheActivity.this, "images");
        } catch (IOException e) {
            e.printStackTrace();
        }

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (helper != null) {
                    BitmapDrawable drawable = (BitmapDrawable) ContextCompat
                            .getDrawable(DiskLruCacheActivity.this, R.mipmap.i_test_circle);
                    helper.putBitmap("bitmap", drawable.getBitmap());
                }
            }
        });

        findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (helper != null) {
                    Bitmap bitmap = helper.getBitmap("bitmap");
                    if (bitmap != null) {
                        ((ImageView) findViewById(R.id.image)).setImageBitmap(bitmap);
                    }
                }
            }
        });
    }


}
