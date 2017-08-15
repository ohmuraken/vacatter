package com.fernandocejas.android10.sample.presentation.view.activity;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.view.fragment.SquareHeightFragment;

public class TimeLineActivity extends BaseActivity implements HasComponent<UseCaseComponent> {

  private UseCaseComponent usecaseComponent;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_time_line);
    LinearLayout cardLinear = (LinearLayout) this.findViewById(R.id.cardLinear);
    FloatingActionButton registBtn = (FloatingActionButton) findViewById(R.id.btn_pic_regist);
    registBtn.setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(TimeLineActivity.this, MainCameraActivity.class);
        startActivity(intent);
        finish();
      }
    });
    cardLinear.removeAllViews();
    for (int i = 0; i < 5; i++) {
      LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
      LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.row_card, null);
      SquareHeightFragment imageView = (SquareHeightFragment)linearLayout.findViewById(R.id.post_image);
      Glide.with(this).load("http://goo.gl/h8qOq7").into(imageView);
      CardView cardView = (CardView) linearLayout.findViewById(R.id.cardView);
      TextView textBox = (TextView) linearLayout.findViewById(R.id.textBox);
      Button favBtn = (Button) linearLayout.findViewById(R.id.btn_favorite);

    // Generate Object Tree
    this.initializeInjector();
    if (savedInstanceState == null) {
      addFragment(R.id.fragmentContainer, new TweetCardFragment());
    }
  }

  private void initializeInjector() {
    this.usecaseComponent = DaggerUseCaseComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
  }

  @Override public UseCaseComponent getComponent() {
    return usecaseComponent;
  }
}
