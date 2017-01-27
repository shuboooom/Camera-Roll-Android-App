package us.koller.cameraroll.adapter.album.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import us.koller.cameraroll.R;
import us.koller.cameraroll.data.AlbumItem;
import us.koller.cameraroll.util.Util;

public class GifViewHolder extends AlbumItemHolder {

    public GifViewHolder(View itemView) {
        super(itemView);
        ImageView indicator = (ImageView) itemView.findViewById(R.id.indicator);
        indicator.setImageResource(R.drawable.gif_indicator);
    }

    @Override
    public void loadImage(final ImageView imageView, final AlbumItem albumItem) {
        Context context = imageView.getContext();

        int screenWidth = Util.getScreenWidth((Activity) context);
        int columnCount = Util.getAlbumActivityGridColumnCount(context);
        //square image
        int[] imageDimens = {
                (int) ((float) screenWidth / columnCount),
                (int) ((float) screenWidth / columnCount)};

        Glide.with(context)
                .load(albumItem.getPath())
                .asGif()
                .thumbnail(0.1f)
                .skipMemoryCache(true)
                .override(imageDimens[0], imageDimens[1])
                .listener(new RequestListener<String, GifDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model,
                                               Target<GifDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GifDrawable resource, String model,
                                                   Target<GifDrawable> target, boolean isFromMemoryCache,
                                                   boolean isFirstResource) {
                        if (!albumItem.hasFadedIn) {
                            fadeIn();
                        }
                        resource.start();
                        return false;
                    }
                })
                .error(R.drawable.error_placeholder)
                .into(imageView);
    }
}
