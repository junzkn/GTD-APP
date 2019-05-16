package com.jun.gtd.moudle.main;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.jun.bubble.BubbleDialog;
import com.jun.gtd.R;
import com.jun.gtd.base.App;
import com.jun.gtd.base.BaseActivity;
import com.jun.gtd.bean.TodoBean;
import com.jun.gtd.moudle.login.LoginActivity;
import com.jun.gtd.moudle.post.PostActivity;
import com.jun.gtd.net.Net;
import com.jun.gtd.utils.BubbleUtils;
import com.jun.gtd.utils.ScreenUtils;
import com.jun.gtd.utils.ToastUtils;
import com.jun.gtd.utils.ToolbarUtils;
import com.jun.gtd.utils.ViewEmptyUtils;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    private SwipeRefreshLayout mRefresh;
    private RecyclerView mTodoListView;
    private TodoAdapter mTodoAdapter;
    private FloatingActionButton mFabAdd;
    private TextView mContentBubbleDialogText;
    private BubbleDialog mContentBubbleDialog ;
    private BubbleDialog mTipBubbleDialog;
    private TextView mTvLogin ;
    private ImageView mImgUser ;
    private DrawerLayout mDrawerLayout ;
    private LinearLayout mBtnCategoryAll ,mBtnCategory1 ,mBtnCategory2 ,mBtnCategory3 ,mBtnCategory4;
    private LinearLayout mBtnHideDone , mBtnHideExpired ;
    private TextView mTvOrder1 , mTvOrder2 , mTvOrder3 ;


    private ClickListener mClickListener ;

    @ColorInt
    int theme = ContextCompat.getColor(App.getInstance(),R.color.colorAccent) ;
    @ColorInt
    int grey = ContextCompat.getColor(App.getInstance(),R.color.grey_500) ;

    private int mSymbol = 1003 ;
    private int mType = 0 ;
    private int mHideDone = 0  ;
    private int mHideExpired = 0 ;
    private int mTimeOrder = 0  ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter(new MainModel());
    }

    @Override
    protected void init() {
        mClickListener = new ClickListener() ;
        mTodoListView = findViewById(R.id.rv_list);
        mRefresh = findViewById(R.id.srl_refresh);
        mFabAdd = findViewById(R.id.fab_add);
        mTvLogin = findViewById(R.id.tv_login);
        mImgUser = findViewById(R.id.img_user);
        mDrawerLayout = findViewById(R.id.dl_root_container);
        mBtnCategoryAll = findViewById(R.id.btn_category_all);
        mBtnCategory1 = findViewById(R.id.btn_category_1);
        mBtnCategory2 = findViewById(R.id.btn_category_2);
        mBtnCategory3 = findViewById(R.id.btn_category_3);
        mBtnCategory4 = findViewById(R.id.btn_category_4);
        mBtnHideDone = findViewById(R.id.btn_hide_done);
        mBtnHideExpired = findViewById(R.id.btn_hide_expired) ;
        mTvOrder1 = findViewById(R.id.tv_order_default) ;
        mTvOrder2 = findViewById(R.id.tv_order_dcs) ;
        mTvOrder3 = findViewById(R.id.tv_order_acs) ;




        mTodoAdapter = new TodoAdapter(this,mPresenter);
        mTodoListView.setAdapter(mTodoAdapter);
        ToolbarUtils.initPaddingTopDiffBar(mTodoListView);
        ViewEmptyUtils.setEmpty(mTodoListView);

        mRefresh.setColorSchemeResources(R.color.colorAccent);

    }

    @Override
    protected void prepare() {
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        mFabAdd.setOnClickListener(mClickListener);
        mTvLogin.setOnClickListener(mClickListener);
        mImgUser.setOnClickListener(mClickListener);
        mBtnCategory1.setOnClickListener(mClickListener);
        mBtnCategory2.setOnClickListener(mClickListener);
        mBtnCategory3.setOnClickListener(mClickListener);
        mBtnCategory4.setOnClickListener(mClickListener);
        mBtnCategoryAll.setOnClickListener(mClickListener);
        mBtnHideDone.setOnClickListener(mClickListener);
        mBtnHideExpired.setOnClickListener(mClickListener);
        mTvOrder1.setOnClickListener(mClickListener);
        mTvOrder2.setOnClickListener(mClickListener);
        mTvOrder3.setOnClickListener(mClickListener);

        mTvLogin.setText(App.Login.isLogin()?getString(R.string.logout):getString(R.string.loginAndRegister));
        if(App.Login.isLogin()){
            Glide.with(mContext)
                    .load(Net.BASE_URL+"/resources/uu.png")
                    .placeholder(R.drawable.ic_user)
                    .into(mImgUser);
        }
        else{
            Glide.with(mContext)
                    .load(R.drawable.ic_user)
                    .into(mImgUser);
        }

        mTodoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MultiItemEntity entity = mTodoAdapter.getData().get(position);
                if(entity.getItemType()==TodoAdapter.TYPE_TODO){
                    final TodoBean todoBean = (TodoBean) entity ;
                    final String content = todoBean.getContent();
                    if(!TextUtils.isEmpty(content)){
                        View contentView = getLayoutInflater().inflate(R.layout.layout_content, null);
                        mContentBubbleDialogText = contentView.findViewById(R.id.tv_content);
                        mContentBubbleDialog = new BubbleDialog(mContext)
                                .setClickedView(view)
                                .addContentView(contentView)
                                .setRelativeOffset(-16)
                                .setBubbleLayout(BubbleUtils.get(mContext))
                                .setLayout(ScreenUtils.getScreenWidth(mContext)*2/3,400,0)
                                .setPosition(BubbleDialog.Position.TOP, BubbleDialog.Position.BOTTOM);
                        mContentBubbleDialogText.setText(content);
                        mContentBubbleDialogText.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View view) {
                                mContentBubbleDialog.dismiss();
                                PostActivity.launchActivity(mActivity,todoBean);
                            }
                        });
                        mContentBubbleDialog.show() ;
                    }
                }
            }
        });
        mTodoAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
                MultiItemEntity entity = mTodoAdapter.getData().get(position);
                final TodoBean todoBean = (TodoBean) entity ;
                if (entity.getItemType() == TodoAdapter.TYPE_TODO) {
                    View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_choose_todo_operate, null);
                    mTipBubbleDialog = new BubbleDialog(mContext)
                            .setClickedView(view)
                            .addContentView(contentView)
                            .setRelativeOffset(-16)
                            .setBubbleLayout(BubbleUtils.get(mContext))
                            .setPosition(BubbleDialog.Position.TOP, BubbleDialog.Position.BOTTOM);
                    contentView.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mTipBubbleDialog.dismiss();
                            mTodoAdapter.remove(position);
                            mPresenter.requestDeleteTodo(todoBean.getId());
                        }
                    });
                    contentView.findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mTipBubbleDialog.dismiss();
                            PostActivity.launchActivity(mActivity,todoBean);
                        }
                    });
                    mTipBubbleDialog.show();
                    return true;
                }
                return false;
            }
        });
        mRefresh.setRefreshing(true);
        refresh();
    }

    @Override
    public void displayTodoList(List<TodoBean> todos) {
        mRefresh.setRefreshing(false);
        if(todos!=null){
            List<TodoBean> src = sortByStatus(todos,mHideDone);
            mTodoAdapter.setNewData(src);
            mTodoAdapter.notifyDataSetChanged();
        }else{
            ViewEmptyUtils.setEmpty(mTodoListView);
            mTodoAdapter.setNewData(todos);
            mTodoAdapter.notifyDataSetChanged();
        }
    }

    private void sortByTime(List<TodoBean> todos, int flag) {
        if(flag==0) return ;
        else if(flag == 1){
            Collections.sort(todos, new Comparator<TodoBean>() {
                @Override
                public int compare(TodoBean o1, TodoBean o2) {
                    return o1.getCompleteDate() == o2.getCompleteDate() ? 0 : o1.getCompleteDate() > o2.getCompleteDate() ? -1 : 1;
                }
            });
        }else if(flag==2){
            Collections.sort(todos, new Comparator<TodoBean>() {
                @Override
                public int compare(TodoBean o1, TodoBean o2) {
                    return o1.getCompleteDate() == o2.getCompleteDate() ? 0 : o1.getCompleteDate() < o2.getCompleteDate() ? -1 : 1;
                }
            });
        }
    }

    private List<TodoBean> hideExpired(List<TodoBean> todos , int flag){
        if(flag==0) return todos;
        long currentTime = System.currentTimeMillis() - 86400000 ;
        List<TodoBean> l = new ArrayList<>() ;
        if( flag==1) {
            for(TodoBean todo : todos){
                if(todo.getCompleteDate()>currentTime){
                    l.add(todo);
                }
            }
        }
        return l ;
    }


    private List<TodoBean> sortByStatus(List<TodoBean> todos , int flag){
        switch(flag){
            case 0 :
                List<TodoBean> src0 = new ArrayList<>();
                List<TodoBean> doing0 = new ArrayList<>();
                List<TodoBean> done0 = new ArrayList<>();
                for(TodoBean todo : todos){
                    if(todo.getStatus()==0){
                        doing0.add(todo);
                    }else {
                        done0.add(todo);
                    }
                }
                src0.add(new TodoBean(){
                    @Override
                    public int getItemType() {
                        return TodoAdapter.TYPE_TODO_TYPE;
                    }
                    @Override
                    public String getTitle() {
                        return getString(R.string.doing);
                    }
                }) ;
                doing0 = hideExpired(doing0,mHideExpired);
                sortByTime(doing0,mTimeOrder);
                src0.addAll(doing0);
                src0.add(new TodoBean(){
                    @Override
                    public int getItemType() {
                        return TodoAdapter.TYPE_TODO_TYPE;
                    }
                    @Override
                    public String getTitle() {
                        return getString(R.string.done);
                    }
                });
                done0 = hideExpired(done0,mHideExpired);
                hideExpired(done0,mHideExpired);
                sortByTime(done0,mTimeOrder);
                src0.addAll(done0) ;
                return src0 ;
            case 1 :
                List<TodoBean> src1 = new ArrayList<>();
                List<TodoBean> doing1 = new ArrayList<>();
                for(TodoBean todo : todos){
                    if(todo.getStatus()==0){
                        doing1.add(todo);
                    }
                }
                src1.add(new TodoBean(){
                    @Override
                    public int getItemType() {
                        return TodoAdapter.TYPE_TODO_TYPE;
                    }
                    @Override
                    public String getTitle() {
                        return getString(R.string.doing);
                    }
                }) ;
                doing1 = hideExpired(doing1,mHideExpired);
                sortByTime(doing1,mTimeOrder);
                src1.addAll(doing1);
                return src1 ;
            default :
                return todos ;
        }
    }

    @Override
    public void guidLogin() {
        ToastUtils.info(getResources().getString(R.string.please_login));
        LoginActivity.launchActivity(mActivity);
    }

    @Override
    public void refresh() {
        mPresenter.requestGetTodo(mSymbol,mType,mRefresh);
    }


    private class ClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.fab_add :
                    if(!App.Login.isLogin()){
                        guidLogin();
                    }else{
                        PostActivity.launchActivity(mActivity,null);
                    }
                    break ;
                case R.id.tv_login:
                    mDrawerLayout.closeDrawers();
                    if(!App.Login.isLogin()){
                        LoginActivity.launchActivity(mActivity);
                    }else{
                        mPresenter.requestLogout();
                        mTvLogin.setText(getString(R.string.loginAndRegister));
                        Glide.with(mContext)
                                .load(R.drawable.ic_user)
                                .into(mImgUser);
                    }
                    break ;
                case R.id.btn_category_all :
                    mBtnCategoryAll.setBackground(getResources().getDrawable(R.drawable.category_item_bg));
                    ((TextView)mBtnCategoryAll.findViewById(R.id.tv1)).setTextColor(theme);
                    ((AppCompatImageView)mBtnCategoryAll.findViewById(R.id.img1)).setColorFilter(theme);
                    mBtnCategory1.setBackground(null);
                    ((TextView)mBtnCategory1.findViewById(R.id.tv1)).setTextColor(grey);
                    ((AppCompatImageView)mBtnCategory1.findViewById(R.id.img1)).setColorFilter(grey);
                    mBtnCategory2.setBackground(null);
                    ((TextView)mBtnCategory2.findViewById(R.id.tv1)).setTextColor(grey);
                    ((AppCompatImageView)mBtnCategory2.findViewById(R.id.img1)).setColorFilter(grey);
                    mBtnCategory3.setBackground(null);
                    ((TextView)mBtnCategory3.findViewById(R.id.tv1)).setTextColor(grey);
                    ((AppCompatImageView)mBtnCategory3.findViewById(R.id.img1)).setColorFilter(grey);
                    mBtnCategory4.setBackground(null);
                    ((TextView)mBtnCategory4.findViewById(R.id.tv1)).setTextColor(grey);
                    ((AppCompatImageView)mBtnCategory4.findViewById(R.id.img1)).setColorFilter(grey);
                    mSymbol = 1003 ;
                    mType = 0 ;
                    mPresenter.requestGetTodo(mSymbol,mType,mRefresh);
                    break ;
                case R.id.btn_category_1 :
                    mBtnCategory1.setBackground(getResources().getDrawable(R.drawable.category_item_bg));
                    ((TextView)mBtnCategory1.findViewById(R.id.tv1)).setTextColor(theme);
                    ((AppCompatImageView)mBtnCategory1.findViewById(R.id.img1)).setColorFilter(theme);
                    mBtnCategoryAll.setBackground(null);
                    ((TextView)mBtnCategoryAll.findViewById(R.id.tv1)).setTextColor(grey);
                    ((AppCompatImageView)mBtnCategoryAll.findViewById(R.id.img1)).setColorFilter(grey);
                    mBtnCategory2.setBackground(null);
                    ((TextView)mBtnCategory2.findViewById(R.id.tv1)).setTextColor(grey);
                    ((AppCompatImageView)mBtnCategory2.findViewById(R.id.img1)).setColorFilter(grey);
                    mBtnCategory3.setBackground(null);
                    ((TextView)mBtnCategory3.findViewById(R.id.tv1)).setTextColor(grey);
                    ((AppCompatImageView)mBtnCategory3.findViewById(R.id.img1)).setColorFilter(grey);
                    mBtnCategory4.setBackground(null);
                    ((TextView)mBtnCategory4.findViewById(R.id.tv1)).setTextColor(grey);
                    ((AppCompatImageView)mBtnCategory4.findViewById(R.id.img1)).setColorFilter(grey);
                    mSymbol = 1001 ;
                    mType = 1 ;
                    mPresenter.requestGetTodo(mSymbol,mType,mRefresh);
                    break ;
                case R.id.btn_category_2 :
                    mBtnCategory2.setBackground(getResources().getDrawable(R.drawable.category_item_bg));
                    ((TextView)mBtnCategory2.findViewById(R.id.tv1)).setTextColor(theme);
                    ((AppCompatImageView)mBtnCategory2.findViewById(R.id.img1)).setColorFilter(theme);
                    mBtnCategoryAll.setBackground(null);
                    ((TextView)mBtnCategoryAll.findViewById(R.id.tv1)).setTextColor(grey);
                    ((AppCompatImageView)mBtnCategoryAll.findViewById(R.id.img1)).setColorFilter(grey);
                    mBtnCategory1.setBackground(null);
                    ((TextView)mBtnCategory1.findViewById(R.id.tv1)).setTextColor(grey);
                    ((AppCompatImageView)mBtnCategory1.findViewById(R.id.img1)).setColorFilter(grey);
                    mBtnCategory3.setBackground(null);
                    ((TextView)mBtnCategory3.findViewById(R.id.tv1)).setTextColor(grey);
                    ((AppCompatImageView)mBtnCategory3.findViewById(R.id.img1)).setColorFilter(grey);
                    mBtnCategory4.setBackground(null);
                    ((TextView)mBtnCategory4.findViewById(R.id.tv1)).setTextColor(grey);
                    ((AppCompatImageView)mBtnCategory4.findViewById(R.id.img1)).setColorFilter(grey);
                    mSymbol = 1001 ;
                    mType = 2 ;
                    mPresenter.requestGetTodo(mSymbol,mType,mRefresh);
                    break ;
                case R.id.btn_category_3 :
                    mBtnCategory3.setBackground(getResources().getDrawable(R.drawable.category_item_bg));
                    ((TextView)mBtnCategory3.findViewById(R.id.tv1)).setTextColor(theme);
                    ((AppCompatImageView)mBtnCategory3.findViewById(R.id.img1)).setColorFilter(theme);
                    mBtnCategoryAll.setBackground(null);
                    ((TextView)mBtnCategoryAll.findViewById(R.id.tv1)).setTextColor(grey);
                    ((AppCompatImageView)mBtnCategoryAll.findViewById(R.id.img1)).setColorFilter(grey);
                    mBtnCategory2.setBackground(null);
                    ((TextView)mBtnCategory2.findViewById(R.id.tv1)).setTextColor(grey);
                    ((AppCompatImageView)mBtnCategory2.findViewById(R.id.img1)).setColorFilter(grey);
                    mBtnCategory1.setBackground(null);
                    ((TextView)mBtnCategory1.findViewById(R.id.tv1)).setTextColor(grey);
                    ((AppCompatImageView)mBtnCategory1.findViewById(R.id.img1)).setColorFilter(grey);
                    mBtnCategory4.setBackground(null);
                    ((TextView)mBtnCategory4.findViewById(R.id.tv1)).setTextColor(grey);
                    ((AppCompatImageView)mBtnCategory4.findViewById(R.id.img1)).setColorFilter(grey);
                    mSymbol = 1001 ;
                    mType = 3 ;
                    mPresenter.requestGetTodo(mSymbol,mType,mRefresh);
                    break ;
                case R.id.btn_category_4 :
                    mBtnCategory4.setBackground(getResources().getDrawable(R.drawable.category_item_bg));
                    ((TextView)mBtnCategory4.findViewById(R.id.tv1)).setTextColor(theme);
                    ((AppCompatImageView)mBtnCategory4.findViewById(R.id.img1)).setColorFilter(theme);
                    mBtnCategoryAll.setBackground(null);
                    ((TextView)mBtnCategoryAll.findViewById(R.id.tv1)).setTextColor(grey);
                    ((AppCompatImageView)mBtnCategoryAll.findViewById(R.id.img1)).setColorFilter(grey);
                    mBtnCategory2.setBackground(null);
                    ((TextView)mBtnCategory2.findViewById(R.id.tv1)).setTextColor(grey);
                    ((AppCompatImageView)mBtnCategory2.findViewById(R.id.img1)).setColorFilter(grey);
                    mBtnCategory3.setBackground(null);
                    ((TextView)mBtnCategory3.findViewById(R.id.tv1)).setTextColor(grey);
                    ((AppCompatImageView)mBtnCategory3.findViewById(R.id.img1)).setColorFilter(grey);
                    mBtnCategory1.setBackground(null);
                    ((TextView)mBtnCategory1.findViewById(R.id.tv1)).setTextColor(grey);
                    ((AppCompatImageView)mBtnCategory1.findViewById(R.id.img1)).setColorFilter(grey);
                    mSymbol = 1001 ;
                    mType = 4 ;
                    mPresenter.requestGetTodo(mSymbol,mType,mRefresh);
                    break ;
                case R.id.tv_order_default :
                    mTimeOrder=0 ;
                    mTvOrder1.setBackground(getResources().getDrawable(R.drawable.category_item_bg));
                    mTvOrder1.setTextColor(theme);
                    mTvOrder2.setBackground(null);
                    mTvOrder2.setTextColor(grey);
                    mTvOrder3.setBackground(null);
                    mTvOrder3.setTextColor(grey);
                    mPresenter.requestGetTodo(mSymbol,mType,mRefresh);
                    break ;
              case R.id.tv_order_dcs :
                    mTimeOrder=1 ;
                    mTvOrder2.setBackground(getResources().getDrawable(R.drawable.category_item_bg));
                    mTvOrder2.setTextColor(theme);
                    mTvOrder1.setBackground(null);
                    mTvOrder1.setTextColor(grey);
                    mTvOrder3.setBackground(null);
                    mTvOrder3.setTextColor(grey);
                    mPresenter.requestGetTodo(mSymbol,mType,mRefresh);
                    break ;
              case R.id.tv_order_acs :
                    mTimeOrder=2 ;
                    mTvOrder3.setBackground(getResources().getDrawable(R.drawable.category_item_bg));
                    mTvOrder3.setTextColor(theme);
                    mTvOrder2.setBackground(null);
                    mTvOrder2.setTextColor(grey);
                    mTvOrder1.setBackground(null);
                    mTvOrder1.setTextColor(grey);
                    mPresenter.requestGetTodo(mSymbol,mType,mRefresh);
                    break ;
                case R.id.btn_hide_done:
                    if(mHideDone==0){
                        mHideDone=1 ;
                        mPresenter.requestGetTodo(mSymbol,mType,mRefresh);
                        mBtnHideDone.setBackground(getResources().getDrawable(R.drawable.category_item_bg));
                        ((TextView)mBtnHideDone.findViewById(R.id.tv1)).setTextColor(theme);
                        ((AppCompatImageView)mBtnHideDone.findViewById(R.id.img1)).setColorFilter(theme);
                    }else{
                        mHideDone=0 ;
                        mPresenter.requestGetTodo(mSymbol,mType,mRefresh);
                        mBtnHideDone.setBackground(null);
                        ((TextView)mBtnHideDone.findViewById(R.id.tv1)).setTextColor(grey);
                        ((AppCompatImageView)mBtnHideDone.findViewById(R.id.img1)).setColorFilter(grey);
                    }
                    break ;
             case R.id.btn_hide_expired:
                    if(mHideExpired==0){
                        mHideExpired=1 ;
                        mPresenter.requestGetTodo(mSymbol,mType,mRefresh);
                        mBtnHideExpired.setBackground(getResources().getDrawable(R.drawable.category_item_bg));
                        ((TextView)mBtnHideExpired.findViewById(R.id.tv1)).setTextColor(theme);
                        ((AppCompatImageView)mBtnHideExpired.findViewById(R.id.img1)).setColorFilter(theme);
                    }else{
                        mHideExpired=0 ;
                        mPresenter.requestGetTodo(mSymbol,mType,mRefresh);
                        mBtnHideExpired.setBackground(null);
                        ((TextView)mBtnHideExpired.findViewById(R.id.tv1)).setTextColor(grey);
                        ((AppCompatImageView)mBtnHideExpired.findViewById(R.id.img1)).setColorFilter(grey);
                    }
                    break ;
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == LoginActivity.LOGIN_ACTIVITY_RESULT_CODE){
            mRefresh.setRefreshing(true);
            mTvLogin.setText(getString(R.string.logout));
            Glide.with(mContext)
                    .load(Net.BASE_URL+"/resources/uu.png")
                    .placeholder(R.drawable.ic_user)
                    .into(mImgUser);
            refresh();
        }else if(resultCode == PostActivity.POST_ACTIVITY_RESULT_CODE){
            mRefresh.setRefreshing(true);
            refresh();
        }
    }
}
