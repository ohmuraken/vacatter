package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by apple on 2017/08/13.
 */

public class SquareHeightFragment extends AppCompatImageView {
  public SquareHeightFragment(@NonNull Context context){
    super(context);
  }
  public SquareHeightFragment(Context context, AttributeSet attrs){
    super(context, attrs);
  }
  public SquareHeightFragment(Context context, AttributeSet attrs, int defStyleAttr){
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    //縦横サイズを同じにする
    int height = getMeasuredHeight();
    setMeasuredDimension(height, height);
  }
}
