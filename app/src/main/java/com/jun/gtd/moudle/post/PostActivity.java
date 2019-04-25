package com.jun.gtd.moudle.post;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.jun.gtd.R;
import com.jun.gtd.base.App;
import com.jun.gtd.base.BaseActivity;
import com.jun.gtd.bean.TodoBean;
import com.jun.gtd.moudle.main.MainContract;
import com.jun.gtd.utils.InputUtils;
import com.jun.gtd.utils.SoftKeyUtils;
import com.jun.gtd.utils.ToastUtils;
import com.jun.gtd.utils.ToolbarUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;

import io.doist.datetimepicker.date.DatePicker;

public class PostActivity extends BaseActivity<PostPresenter> implements PostContract.View, View.OnClickListener {

    private EditText mEtTitle, mEtContent;
    private ImageView mBtnCalendar, mBtnCategory, mBtnPriority, mBtnOk;
    private FrameLayout mBtnBack;
    private LinearLayout mBtnPriorityHeight, mBtnPriorityMiddle, mBtnPriorityLow, mBtnPriorityDefault;
    private LinearLayout mBtnCategory1, mBtnCategory2, mBtnCategory3, mBtnCategory4;
    private ProgressBar mPbLoading;

    private LinearLayout mPriorityPicker;
    private LinearLayout mCategoryPicker;
    private DatePicker mDatePicker;

    private String mDate;
    private int mCategory;
    private int mPriority;
    private String mContent;

    private TodoBean mTodoBean;

    public static final int POST_ACTIVITY_REQUEST_CODE = 2001;
    public static final int POST_ACTIVITY_RESULT_CODE = 2002;

    private @ColorInt
    int grey = ContextCompat.getColor(App.getInstance(), R.color.grey_500);
    private @ColorInt
    int green = ContextCompat.getColor(App.getInstance(), R.color.green_500);
    private @ColorInt
    int red = ContextCompat.getColor(App.getInstance(), R.color.red_500);
    private @ColorInt
    int blue = ContextCompat.getColor(App.getInstance(), R.color.blue_500);
    private @ColorInt
    int black = ContextCompat.getColor(App.getInstance(), R.color.grey_1000);
    private @ColorInt
    int yellow = ContextCompat.getColor(App.getInstance(), R.color.yellow_500);
    private @ColorInt
    int theme = ContextCompat.getColor(App.getInstance(), R.color.colorAccent);


    public static void launchActivity(Activity context, TodoBean todoBean) {
        Intent intent = new Intent(context, PostActivity.class);
        intent.putExtra("todoBean", todoBean);
        context.startActivityForResult(intent, POST_ACTIVITY_REQUEST_CODE);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_post;
    }

    @Override
    protected PostPresenter initPresenter() {
        return new PostPresenter(new PostModel());
    }

    @Override
    protected void init() {
        ToolbarUtils.initPaddingTopDiffBar(findViewById(R.id.ll_top));
        initView();
        mDatePicker.setMinDate(System.currentTimeMillis());
        mDatePicker.setMaxDate(System.currentTimeMillis() + 356L * 24L * 60L * 60L * 1000L);
        initData();
        mEtTitle.requestFocus();
    }

    private void initData() {
        Intent intent = getIntent();
        mTodoBean = (TodoBean) intent.getExtras().get("todoBean");
        if (mTodoBean != null) {
            mDate = mTodoBean.getCompleteDateStr();
            mCategory = mTodoBean.getType();
            mPriority = mTodoBean.getPriority();
            mEtTitle.setText(mTodoBean.getTitle());
            mEtContent.setText(mTodoBean.getContent());
            mBtnOk.setColorFilter(theme);
            mBtnCategory.setColorFilter(mCategory==0?grey:theme);
            if (mDate != null && !TextUtils.isEmpty(mDate)) {
                mBtnCalendar.setColorFilter(theme);
            }
            switch (mPriority) {
                case TodoBean.PRIORITY_NOTURGENT_NOTIMPORTANT:
                    mBtnPriority.setColorFilter(black);
                    break;
                case TodoBean.PRIORITY_URGENT_IMPORTANT:
                    mBtnPriority.setColorFilter(red);
                    break;
                case TodoBean.PRIORITY_IMPORTANT_NOTURGENT:
                    mBtnPriority.setColorFilter(yellow);
                    break;
                case TodoBean.PRIORITY_URGENT_NOTIMPORTANT:
                    mBtnPriority.setColorFilter(blue);
                    break;
            }
        } else {
            mDate = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(System.currentTimeMillis());
            mCategory = 0;
            mPriority = TodoBean.PRIORITY_NOTURGENT_NOTIMPORTANT;
            mContent = "";
            mBtnCalendar.setColorFilter(grey);
            mBtnCategory.setColorFilter(grey);
            mBtnPriority.setColorFilter(grey);
            mBtnOk.setColorFilter(grey);
        }


    }

    private void initView() {
        mBtnCalendar = findViewById(R.id.btn_calendar);
        mBtnCategory = findViewById(R.id.btn_category);
        mPbLoading = findViewById(R.id.pb_loading);
        mBtnPriority = findViewById(R.id.btn_priority);
        mEtTitle = findViewById(R.id.et_title);
        mEtContent = findViewById(R.id.et_content);
        mBtnOk = findViewById(R.id.btn_ok);
        mBtnBack = findViewById(R.id.btn_back);

        mBtnPriorityHeight = findViewById(R.id.btn_priority_height);
        mBtnPriorityMiddle = findViewById(R.id.btn_priority_middle);
        mBtnPriorityLow = findViewById(R.id.btn_priority_low);
        mBtnPriorityDefault = findViewById(R.id.btn_priority_default);
        mBtnCategory1 = findViewById(R.id.btn_category_1);
        mBtnCategory2 = findViewById(R.id.btn_category_2);
        mBtnCategory3 = findViewById(R.id.btn_category_3);
        mBtnCategory4 = findViewById(R.id.btn_category_4);

        mPriorityPicker = findViewById(R.id.priorityPicker);
        mCategoryPicker = findViewById(R.id.categoryPicker);
        mDatePicker = findViewById(R.id.datePicker);
    }

    @Override
    protected void prepare() {
        setListener();

        mEtTitle.postDelayed(new Runnable() {
            @Override
            public void run() {
                SoftKeyUtils.show(mActivity, mEtTitle);
            }
        }, 100);
        final View view = findViewById(R.id.rl_root_container);
        mEtTitle.postDelayed(new Runnable() {
            @Override
            public void run() {
                SoftKeyUtils.doMoveLayout(view, view, new View[]{mDatePicker,mPriorityPicker,mCategoryPicker}, 0);
            }
        }, 100);


    }

    private void setListener() {
        mBtnCalendar.setOnClickListener(this);
        mBtnCategory.setOnClickListener(this);
        mBtnPriority.setOnClickListener(this);
        mBtnOk.setOnClickListener(this);
        mBtnBack.setOnClickListener(this);
        mBtnPriorityHeight.setOnClickListener(this);
        mBtnPriorityMiddle.setOnClickListener(this);
        mBtnPriorityLow.setOnClickListener(this);
        mBtnPriorityDefault.setOnClickListener(this);
        mBtnCategory1.setOnClickListener(this);
        mBtnCategory2.setOnClickListener(this);
        mBtnCategory3.setOnClickListener(this);
        mBtnCategory4.setOnClickListener(this);

        mDatePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mBtnCalendar.setColorFilter(theme);
                mDate = String.format("%s-%s-%s", year, monthOfYear + 1, dayOfMonth);
                hideChooseCalender();
            }
        });

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
                    mBtnOk.setColorFilter(theme);
                }
            }
        });

    }

    @Override
    public void showChooseCalender() {
        SoftKeyUtils.hide(mActivity, mEtTitle);

        mDatePicker.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDatePicker.setVisibility(View.VISIBLE);
                mPriorityPicker.setVisibility(View.GONE);
                mCategoryPicker.setVisibility(View.GONE);
            }
        }, 100);
    }

    @Override
    public void hideChooseCalender() {
        mDatePicker.setVisibility(View.GONE);
        mEtTitle.postDelayed(new Runnable() {
            @Override
            public void run() {
                SoftKeyUtils.show(mActivity, mEtTitle);
            }
        }, 100);
    }

    @Override
    public void showChooseCategory() {
        SoftKeyUtils.hide(mActivity, mEtTitle);

        mCategoryPicker.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCategoryPicker.setVisibility(View.VISIBLE);
                mPriorityPicker.setVisibility(View.GONE);
                mDatePicker.setVisibility(View.GONE);
            }
        }, 100);
    }

    @Override
    public void hideChooseCategory() {
        mCategoryPicker.setVisibility(View.GONE);
        mEtTitle.postDelayed(new Runnable() {
            @Override
            public void run() {
                SoftKeyUtils.show(mActivity, mEtTitle);
            }
        }, 100);
    }

    @Override
    public void showChoosePriority() {
        SoftKeyUtils.hide(mActivity, mEtTitle);
        mPriorityPicker.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPriorityPicker.setVisibility(View.VISIBLE);
                mCategoryPicker.setVisibility(View.GONE);
                mDatePicker.setVisibility(View.GONE);
            }
        }, 100);
    }

    @Override
    public void hideChoosePriority() {
        mPriorityPicker.setVisibility(View.GONE);
        mEtTitle.postDelayed(new Runnable() {
            @Override
            public void run() {
                SoftKeyUtils.show(mActivity, mEtTitle);
            }
        }, 100);
    }

    @Override
    public void displayAddTodoIng() {
        mBtnOk.setEnabled(false);
        mPbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayAddTodoFinished() {
        mBtnOk.setEnabled(true);
        mPbLoading.setVisibility(View.GONE);
        SoftKeyUtils.hide(mActivity);
        setResult(POST_ACTIVITY_RESULT_CODE);
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        SoftKeyUtils.hide(mActivity);
        super.onBackPressed();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP)
        {
            finish();
        }
        return super.dispatchKeyEvent(event);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                SoftKeyUtils.hide(mActivity);
                onBackPressed();
                break;
            case R.id.btn_calendar:
                showChooseCalender();
                break;
            case R.id.btn_priority:
                showChoosePriority();
                break;
            case R.id.btn_category:
                showChooseCategory();
                break;
            case R.id.btn_category_1:
                hideChooseCategory();
                mBtnCategory.setColorFilter(theme);
                mCategory = 1;
                break;
            case R.id.btn_category_2:
                hideChooseCategory();
                mBtnCategory.setColorFilter(theme);
                mCategory = 2;
                break;
            case R.id.btn_category_3:
                hideChooseCategory();
                mBtnCategory.setColorFilter(theme);
                mCategory = 3;
                break;
            case R.id.btn_category_4:
                hideChooseCategory();
                mBtnCategory.setColorFilter(theme);
                mCategory = 4;
                break;
            case R.id.btn_priority_height:
                hideChoosePriority();
                mPriority = TodoBean.PRIORITY_URGENT_IMPORTANT;
                mBtnPriority.setColorFilter(red);
                break;
            case R.id.btn_priority_middle:
                hideChoosePriority();
                mPriority = TodoBean.PRIORITY_IMPORTANT_NOTURGENT;
                mBtnPriority.setColorFilter(yellow);
                break;
            case R.id.btn_priority_low:
                hideChoosePriority();
                mPriority = TodoBean.PRIORITY_URGENT_NOTIMPORTANT;
                mBtnPriority.setColorFilter(blue);
                break;
            case R.id.btn_priority_default:
                hideChoosePriority();
                mPriority = TodoBean.PRIORITY_NOTURGENT_NOTIMPORTANT;
                mBtnPriority.setColorFilter(black);
                break;
            case R.id.btn_ok:
                addTodo();
                break;

        }
    }

    private void addTodo() {
        String title = mEtTitle.getText().toString() ;
        if(TextUtils.isEmpty(title)) {
            ToastUtils.info(getString(R.string.please_input_title));
            return;
        }
        TodoBean todoBean = new TodoBean() ;
        todoBean.setUserid(App.Login.getUserId());
        todoBean.setTitle(title);
        todoBean.setContent(mEtContent.getText().toString().trim());
        todoBean.setType(mCategory);
        todoBean.setStatus(0);
        todoBean.setCompleteDateStr(mDate);
        todoBean.setPriority(mPriority);
        if(mTodoBean !=null){
            todoBean.setId(mTodoBean.getId());
            todoBean.setStatus(mTodoBean.getStatus());
            mPresenter.requestUpdate(todoBean);
        }else {
            mPresenter.requestAddTodo(todoBean);
        }
    }
}
