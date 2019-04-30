package com.jun.gtd.moudle.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.jun.gtd.R;
import com.jun.gtd.base.BaseActivity;
import com.jun.gtd.utils.InputUtils;
import com.jun.gtd.utils.ToastUtils;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    private Button mBtnSwitch, mBtnConfirm;
    private EditText mEtAccount,  mEtPassword;
    private View mLayoutContainer;
    private ProgressBar mPbLoading;
    private FrameLayout mRootView ;

    public static final int LOGIN_ACTIVITY_REQUEST_CODE = 1001;
    public static final int LOGIN_ACTIVITY_RESULT_CODE = 1002;


    @Override
    protected int getLayoutId() {
        return R.layout.login_activity;
    }

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter(new LoginModel());
    }

    @Override
    protected void init() {
        mRootView = findViewById(R.id.root_view) ;
        mBtnSwitch = findViewById(R.id.btn_switch);
        mBtnConfirm = findViewById(R.id.btn_confirm);
        mPbLoading = findViewById(R.id.pb_loading);
        mEtAccount = findViewById(R.id.et_account);
        mEtPassword = findViewById(R.id.et_password);
        mLayoutContainer = findViewById(R.id.ll_container);
        mBtnSwitch.setPaintFlags(mBtnSwitch.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    protected void prepare() {
        mBtnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogining()) {
                    switchToRegister();
                } else {
                    switchToLogin();
                }
            }
        });

        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogining()) {
                    mPresenter.requestLogin(InputUtils.toString(mEtAccount), InputUtils.toString(mEtPassword));
                } else {
                    mPresenter.requestRegister(InputUtils.toString(mEtAccount), InputUtils.toString(mEtPassword),null);
                }
            }
        });

        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    /**
     * 启动LoginActivity的方法
     * @param context 启动LoginActivity的上下文
     */
    public static void launchActivity(Activity context){
        context.startActivityForResult(new Intent(context,LoginActivity.class),LOGIN_ACTIVITY_REQUEST_CODE);
        context.overridePendingTransition(R.anim.activity_fade_in,0);

    }

    /**
     * 判断是否是登录状态
     * @return 返回是否是登录状态
     */
    private boolean isLogining() {
        return InputUtils.toString(mBtnSwitch).equals(getString(R.string.register));
    }

    @Override
    public void displayProgress() {
        mLayoutContainer.setVisibility(View.INVISIBLE);
        mPbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgress() {
        mPbLoading.setVisibility(View.GONE);
        mLayoutContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void switchToLogin() {
        mBtnSwitch.setText(R.string.register);
        mBtnConfirm.setText(R.string.login);
    }

    @Override
    public void switchToRegister() {
        mBtnSwitch.setText(R.string.login);
        mBtnConfirm.setText(R.string.register);
    }

    @Override
    public void loginSuccess() {
        ToastUtils.success(getString(R.string.success_login));
        setResult(LOGIN_ACTIVITY_RESULT_CODE);
        finish();
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.activity_fade_out);
    }
}
