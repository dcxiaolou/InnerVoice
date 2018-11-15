package com.android.dcxiaolou.innervoice;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ConsultActivity extends Activity implements View.OnClickListener {

    private TextView typeTextView, areaTextView, sortTextView, selectTextView;

    private LinearLayout typeLinearLayout, areaLinearLayout, sortLinearLayout, selectLinearLayout;

    private int clickNo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);

        typeTextView = (TextView) findViewById(R.id.type_text_view);
        areaTextView = (TextView) findViewById(R.id.area_text_view);
        sortTextView = (TextView) findViewById(R.id.sort_text_view);
        selectTextView = (TextView) findViewById(R.id.select_text_view);

        typeLinearLayout = (LinearLayout) findViewById(R.id.type_linear_layout);
        areaLinearLayout = (LinearLayout) findViewById(R.id.area_linear_layout);
        sortLinearLayout = (LinearLayout) findViewById(R.id.sort_linear_layout);
        selectLinearLayout = (LinearLayout) findViewById(R.id.select_linear_layout);

        typeTextView.setOnClickListener(this);
        areaTextView.setOnClickListener(this);
        sortTextView.setOnClickListener(this);
        selectTextView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.type_text_view:
                if (clickNo == 0) {
                    Drawable drawableRight = getResources().getDrawable(R.drawable.arrows_down);
                    typeTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null);
                    typeLinearLayout.setVisibility(View.VISIBLE);
                    areaLinearLayout.setVisibility(View.GONE);
                    sortLinearLayout.setVisibility(View.GONE);
                    selectLinearLayout.setVisibility(View.GONE);
                    clickNo = 1;
                } else {
                    Drawable drawableRight = getResources().getDrawable(R.drawable.arrows_right);
                    typeTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null);
                    typeLinearLayout.setVisibility(View.GONE);
                    areaLinearLayout.setVisibility(View.GONE);
                    sortLinearLayout.setVisibility(View.GONE);
                    selectLinearLayout.setVisibility(View.GONE);
                    clickNo = 0;
                }
                break;
            case R.id.area_text_view:
                if (clickNo == 0) {
                    Drawable drawableRight = getResources().getDrawable(R.drawable.arrows_down);
                    areaTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null);
                    typeLinearLayout.setVisibility(View.GONE);
                    areaLinearLayout.setVisibility(View.VISIBLE);
                    sortLinearLayout.setVisibility(View.GONE);
                    selectLinearLayout.setVisibility(View.GONE);
                    clickNo = 1;
                } else {
                    Drawable drawableRight = getResources().getDrawable(R.drawable.arrows_right);
                    areaTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null);
                    typeLinearLayout.setVisibility(View.GONE);
                    areaLinearLayout.setVisibility(View.GONE);
                    sortLinearLayout.setVisibility(View.GONE);
                    selectLinearLayout.setVisibility(View.GONE);
                    clickNo = 0;
                }
                break;
            case R.id.sort_text_view:
                if (clickNo == 0) {
                    Drawable drawableRight = getResources().getDrawable(R.drawable.arrows_down);
                    sortTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null);
                    typeLinearLayout.setVisibility(View.GONE);
                    areaLinearLayout.setVisibility(View.GONE);
                    sortLinearLayout.setVisibility(View.VISIBLE);
                    selectLinearLayout.setVisibility(View.GONE);
                    clickNo = 1;
                } else {
                    Drawable drawableRight = getResources().getDrawable(R.drawable.arrows_right);
                    sortTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null);
                    typeLinearLayout.setVisibility(View.GONE);
                    areaLinearLayout.setVisibility(View.GONE);
                    sortLinearLayout.setVisibility(View.GONE);
                    selectLinearLayout.setVisibility(View.GONE);
                    clickNo = 0;
                }
                break;
            case R.id.select_text_view:
                if (clickNo == 0) {
                    typeLinearLayout.setVisibility(View.GONE);
                    areaLinearLayout.setVisibility(View.GONE);
                    sortLinearLayout.setVisibility(View.GONE);
                    selectLinearLayout.setVisibility(View.VISIBLE);
                    clickNo = 1;
                } else {
                    typeLinearLayout.setVisibility(View.GONE);
                    areaLinearLayout.setVisibility(View.GONE);
                    sortLinearLayout.setVisibility(View.GONE);
                    selectLinearLayout.setVisibility(View.GONE);
                    clickNo = 0;
                }
                break;
            default:
                break;
        }
    }
}
