package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.fernandocejas.android10.sample.presentation.R;

/**
 * Created by apple on 2017/08/15.
 */

public class OneButtonFragment extends Fragment {

  private TextView mTextView;
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    // 先ほどのレイアウトをここでViewとして作成します
    return inflater.inflate(R.layout.fragment_one_button, container, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    // TextViewをひも付けます
    mTextView = (TextView) view.findViewById(R.id.textView);
    // Buttonのクリックした時の処理を書きます
    view.findViewById(R.id.btn_sample).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mTextView.setText(mTextView.getText() + "!");
      }
    });
  }
}
