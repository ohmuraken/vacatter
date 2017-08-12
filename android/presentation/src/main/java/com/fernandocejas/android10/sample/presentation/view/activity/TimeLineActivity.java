package com.fernandocejas.android10.sample.presentation.view.activity;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.fernandocejas.android10.sample.presentation.R;

/**
 * Created by apple on 2017/08/12.
 */

public class TimeLineActivity extends BaseActivity {
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_time_line);
    LinearLayout cardLinear = (LinearLayout) this.findViewById(R.id.cardLinear);
    cardLinear.removeAllViews();
    for (int i = 0; i < 5; i++) {
      LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
      LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.test_card, null);
      CardView cardView = (CardView) linearLayout.findViewById(R.id.cardView);
      TextView textBox = (TextView) linearLayout.findViewById(R.id.textBox);
      textBox.setText("CardView" + i);
      cardView.setTag(i);
      cardView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Log.d("CLICKED_NUMBER",String.valueOf(v.getTag()));
        }
      });
      cardLinear.addView(linearLayout, i);
    }
  }
}
