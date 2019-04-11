package com.jun.gtd.moudle.post;

import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.jun.gtd.R;
import com.jun.gtd.base.App;
import com.jun.gtd.base.BaseActivity;
import com.jun.gtd.utils.InputUtils;
import com.jun.gtd.utils.SoftKeyUtils;

public class PostActivity extends BaseActivity<PostPesenter> implements PostContract.View , View.OnClickListener{

    private EditText mEtTitle, mEtContent;
    private ImageView mBtnChooseCalendar, mBtnTypeList, mBtnPriority, mBtnOk;
    private FrameLayout mBtnBack ;
    private ProgressBar mPbLoading;

    private @ColorInt int grey = ContextCompat.getColor(App.getInstance(), R.color.grey_500);
    private @ColorInt int green = ContextCompat.getColor(App.getInstance(), R.color.green_500);


    @Override
    protected int getLayoutId() {
        return R.layout.activity_post;
    }

    @Override
    protected PostPesenter initPresenter() {
        return new PostPesenter(new PostModel());
    }

    @Override
    protected void init() {
        mBtnChooseCalendar = findViewById(R.id.btn_choose_calendar);
        mBtnTypeList = findViewById(R.id.btn_typeList);
        mPbLoading = findViewById(R.id.pb_loading);
        mBtnPriority = findViewById(R.id.btn_Priority);
        mEtTitle = findViewById(R.id.et_title);
        mEtContent = findViewById(R.id.et_content);
        mBtnOk = findViewById(R.id.btn_ok);
        mBtnBack = findViewById(R.id.btn_back) ;

        mBtnChooseCalendar.setColorFilter(grey);
        mBtnTypeList.setColorFilter(grey);
        mBtnPriority.setColorFilter(grey);
        mBtnOk.setColorFilter(grey);

        mEtTitle.requestFocus();
        SoftKeyUtils.show(this, mEtTitle);

    }

    @Override
    protected void prepare() {
        mBtnChooseCalendar.setOnClickListener(this);
        mBtnTypeList.setOnClickListener(this);
        mBtnPriority.setOnClickListener(this);
        mBtnOk.setOnClickListener(this);
        mBtnBack.setOnClickListener(this);


        mEtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (InputUtils.isEmpty(mEtTitle)) {
                    mBtnOk.setColorFilter(grey);
                } else {
                    mBtnOk.setColorFilter(green);
                }
            }
        });
    }

    @Override
    public void showChooseCalender() {

    }

    @Override
    public void hideChooseCalender() {

    }

    @Override
    public void showChooseTodoCategory() {

    }

    @Override
    public void hideChooseTodoCategory() {

    }

    @Override
    public void showChoosePriority() {

    }

    @Override
    public void hideChoosePriority() {

    }


    @Override
    public void displayAddTodoIng() {

    }

    @Override
    public void displayAddTodoFinished() {

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){

        }
    }
}
