
package com.hktmxk.dataloader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.FileUtil;

/**
 * Test VolleyImageLoader
 * 
 * @author luzemin
 */

public class LoaderTest extends Activity {

    ImageLoader imageLoader;

    TextView topInfo;

    ListView imageList;

    ImageAdapter imageAdapter;

    List<ImageData> imageDatas = new ArrayList<LoaderTest.ImageData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        initImageLoader();
        initVolley();
        initDate();
        initView();
    }

    DiskCache productCache;

    private void initImageLoader() {
        String productPatch = FileUtil.SDCARD + "VollyImageLoader";
        FileUtil.checkAndCreatFolder(productPatch);
        File productPath = new File(productPatch);
        productCache = new UnlimitedDiskCache(productPath);
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .bitmapConfig(Bitmap.Config.ARGB_8888).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .diskCache(productCache).build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();
    }

    RequestQueue mQueue;

    private void initVolley() {
        mQueue = Volley.newRequestQueue(this, productCache);
    }

    class ImageData {
        String url;
        String info;
    }

    private void initDate() {
        for (String url : UrlConstants.IMAGES) {
            ImageData imageData = new ImageData();
            imageData.url = url;
            imageData.info = "null";
            imageDatas.add(imageData);
        }
    }

    private void initView() {

        topInfo = (TextView) findViewById(R.id.top_info);

        StringRequest request = new StringRequest(
                "http://blog.csdn.net/sun1021976312/article/details/23477445",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        topInfo.setText(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        topInfo.setText(error.getMessage());
                    }

                });
        request.setShouldCache(true);
        mQueue.add(request);
        imageList = (ListView) findViewById(R.id.image_list);
        imageAdapter = new ImageAdapter();
        imageList.setAdapter(imageAdapter);
    }

    class ImageAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return imageDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return imageDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(LoaderTest.this)
                        .inflate(R.layout.test_item, null);
            }

            ImageData imageData = imageDatas.get(position);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
            TextView loadInfo = (TextView) convertView.findViewById(R.id.load_info);
            // imageView.setImageResource(R.)
            imageView.setImageResource(android.R.color.transparent);
            imageLoader.displayImage(imageData.url, imageView);
            imageData.info = imageLoader.getDiskCache().getFile(imageData.url).getAbsolutePath();
            loadInfo.setText(imageData.info);
            return convertView;
        }

    }

}
