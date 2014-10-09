
package com.hktmxk.dataloader;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class ImageLoaderHelper {

    public static DisplayImageOptions getDefaultDisplayOption() {
        return new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .bitmapConfig(Bitmap.Config.ARGB_8888).build();
    }
    
    public static ImageLoaderConfiguration getDefaultImageLoaderConfig(Context context,String cachePath){
        File cacheFolder = new File(cachePath);
        return new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(getDefaultDisplayOption())
                .diskCache(new UnlimitedDiskCache(cacheFolder)).build();
    }

}
