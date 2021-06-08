package com.gingerv.newsreader;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

public class ImageHandler {

    private final Activity context;

    public ImageHandler(Activity context) {
        this.context = context;
    }

    public void NewsImageRoundedCorner() {
        ImageRoundedCorner(R.id.button_news_tech, R.drawable.news_technology);
        ImageRoundedCorner(R.id.button_news_sports, R.drawable.news_sports);
        ImageRoundedCorner(R.id.button_news_health, R.drawable.news_health);
        ImageRoundedCorner(R.id.button_news_game, R.drawable.news_game);
        ImageRoundedCorner(R.id.button_news_fin_eco, R.drawable.news_fin_eco);
        ImageRoundedCorner(R.id.button_news_fashion, R.drawable.news_fashion);
        ImageRoundedCorner(R.id.button_news_automobile, R.drawable.news_automobile);
        ImageRoundedCorner(R.id.button_news_food, R.drawable.news_food);
    }

    public void ImageRoundedCorner(int id, int drawable) {
        // transform square pictures to rounded corner
        ImageView imageView = context.findViewById(id);
        RoundedBitmapDrawable circleDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), BitmapFactory.decodeResource(context.getResources(), drawable));
        circleDrawable.setCornerRadius(30f);
        imageView.setImageDrawable(circleDrawable);
    }
}
