package com.crossfireevents.app.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.crossfireevents.app.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Utility class for image loading and processing.
 */
public class ImageUtil {

    /**
     * Loads an image from a URL into an ImageView using Glide.
     *
     * @param context The context.
     * @param url The URL of the image.
     * @param imageView The ImageView to load the image into.
     * @param placeholder The placeholder drawable resource ID.
     */
    public static void loadImage(Context context, String url, ImageView imageView, @DrawableRes int placeholder) {
        if (context == null || imageView == null) {
            return;
        }
        
        RequestOptions options = new RequestOptions()
                .placeholder(placeholder)
                .error(placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }
    
    /**
     * Loads an image from a URL into an ImageView using Glide with default placeholder.
     *
     * @param context The context.
     * @param url The URL of the image.
     * @param imageView The ImageView to load the image into.
     */
    public static void loadImage(Context context, String url, ImageView imageView) {
        loadImage(context, url, imageView, R.drawable.ic_notification);
    }
    
    /**
     * Loads an image from a drawable resource into an ImageView using Glide.
     *
     * @param context The context.
     * @param resourceId The drawable resource ID.
     * @param imageView The ImageView to load the image into.
     */
    public static void loadDrawable(Context context, @DrawableRes int resourceId, ImageView imageView) {
        if (context == null || imageView == null) {
            return;
        }
        
        Glide.with(context)
                .load(resourceId)
                .into(imageView);
    }
    
    /**
     * Downloads a bitmap from a URL.
     * Note: This method should not be called on the main thread.
     *
     * @param src The URL of the image.
     * @return The downloaded bitmap, or null if an error occurred.
     */
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}