package org.apache.cordova.imageview;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;
import com.stfalcon.frescoimageviewer.ImageViewer;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ImageView extends CordovaPlugin {
    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        boolean res = true;
        if ("showImage".equals(action)) {
            this.showImage(args);
        }
        return res;
    }

    private void showImage(JSONArray args) throws JSONException {
        String imageUrl = args.getString(0);
        final int index = args.getInt(1);

        final ArrayList<String> urlList = new ArrayList<String>();
        JSONArray jsonArrayJson = new JSONArray(imageUrl);
        for (int i=0; i<jsonArrayJson.length(); i++) {
            JSONObject item = jsonArrayJson.getJSONObject(i);
            String url = item.getString("url");
            urlList.add(url);
        }

        if(!Fresco.hasBeenInitialized()){
            Context applicationContext = cordova.getActivity().getApplicationContext();
            Fresco.initialize(applicationContext);
        }

        Handler mainHandler = new Handler(Looper.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                GenericDraweeHierarchyBuilder hierarchyBuilder = GenericDraweeHierarchyBuilder.newInstance(cordova.getActivity().getResources())
                        .setProgressBarImage(new ProgressBarDrawable());
                ImageViewer show = new ImageViewer.Builder(cordova.getActivity(), urlList)
                        .setStartPosition(index)
                        .setCustomDraweeHierarchyBuilder(hierarchyBuilder)
                        .show();
            }
        };
        mainHandler.post(myRunnable);

    }
}
