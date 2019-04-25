package com.jun.gtd.moudle.main;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.jun.bubble.BubbleDialog;
import com.jun.gtd.R;
import com.jun.gtd.base.App;
import com.jun.gtd.base.BaseActivity;
import com.jun.gtd.bean.TodoBean;
import com.jun.gtd.moudle.login.LoginActivity;
import com.jun.gtd.moudle.post.PostActivity;
import com.jun.gtd.utils.BubbleUtils;
import com.jun.gtd.utils.ScreenUtils;
import com.jun.gtd.utils.ToastUtils;
import com.jun.gtd.utils.ToolbarUtils;
import com.jun.gtd.utils.ViewEmptyUtils;


import java.util.ArrayList;
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


    private ClickListener mClickListener ;


    private int mSymbol = 1003 ;
    private int mType = 0 ;

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
        mTvLogin.setText(App.Login.isLogin()?getString(R.string.logout):getString(R.string.loginAndRegister));
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
            List<TodoBean> src = sort(todos,0);
            mTodoAdapter.setNewData(src);
            mTodoAdapter.expandAll();
            mTodoAdapter.notifyDataSetChanged();
        }else{
            ViewEmptyUtils.setEmpty(mTodoListView);
            mTodoAdapter.setNewData(todos);
            mTodoAdapter.expandAll();
            mTodoAdapter.notifyDataSetChanged();
        }
    }


    private List<TodoBean> sort(List<TodoBean> todos , int flag){
        switch(flag){
            case 0 :
                List<TodoBean> src = new ArrayList<>();
                List<TodoBean> doing = new ArrayList<>();
                List<TodoBean> done = new ArrayList<>();
                for(TodoBean todo : todos){
                    if(todo.getStatus()==0){
                        doing.add(todo);
                    }else {
                        done.add(todo);
                    }
                }
                src.add(new TodoBean(){
                    @Override
                    public int getItemType() {
                        return TodoAdapter.TYPE_TODO_TYPE;
                    }
                    @Override
                    public String getTitle() {
                        return getString(R.string.doing);
                    }
                }) ;
                src.addAll(doing);
                src.add(new TodoBean(){
                    @Override
                    public int getItemType() {
                        return TodoAdapter.TYPE_TODO_TYPE;
                    }
                    @Override
                    public String getTitle() {
                        return getString(R.string.done);
                    }
                });
                src.addAll(done) ;
                return src ;
            case 1 :

            default :
                return null ;
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
                    }
                    break ;
                case R.id.btn_category_all :
                    mBtnCategoryAll.setBackground(getResources().getDrawable(R.drawable.category_item_bg));
                    mBtnCategory1.setBackground(null);
                    mBtnCategory2.setBackground(null);
                    mBtnCategory3.setBackground(null);
                    mBtnCategory4.setBackground(null);
                    mSymbol = 1003 ;
                    mType = 0 ;
                    mDrawerLayout.closeDrawers();
                    mPresenter.requestGetTodo(mSymbol,mType,mRefresh);
                    break ;
                case R.id.btn_category_1 :
                    mBtnCategory1.setBackground(getResources().getDrawable(R.drawable.category_item_bg));
                    mBtnCategoryAll.setBackground(null);
                    mBtnCategory2.setBackground(null);
                    mBtnCategory3.setBackground(null);
                    mBtnCategory4.setBackground(null);
                    mSymbol = 1001 ;
                    mType = 1 ;
                    mDrawerLayout.closeDrawers();
                    mPresenter.requestGetTodo(mSymbol,mType,mRefresh);
                    break ;
                case R.id.btn_category_2 :
                    mBtnCategory2.setBackground(getResources().getDrawable(R.drawable.category_item_bg));
                    mBtnCategory1.setBackground(null);
                    mBtnCategoryAll.setBackground(null);
                    mBtnCategory3.setBackground(null);
                    mBtnCategory4.setBackground(null);
                    mSymbol = 1001 ;
                    mType = 2 ;
                    mDrawerLayout.closeDrawers();
                    mPresenter.requestGetTodo(mSymbol,mType,mRefresh);
                    break ;
                case R.id.btn_category_3 :
                    mBtnCategory3.setBackground(getResources().getDrawable(R.drawable.category_item_bg));
                    mBtnCategory1.setBackground(null);
                    mBtnCategory2.setBackground(null);
                    mBtnCategoryAll.setBackground(null);
                    mBtnCategory4.setBackground(null);
                    mSymbol = 1001 ;
                    mType = 3 ;
                    mDrawerLayout.closeDrawers();
                    mPresenter.requestGetTodo(mSymbol,mType,mRefresh);
                    break ;
                case R.id.btn_category_4 :
                    mBtnCategory4.setBackground(getResources().getDrawable(R.drawable.category_item_bg));
                    mBtnCategory1.setBackground(null);
                    mBtnCategory2.setBackground(null);
                    mBtnCategory3.setBackground(null);
                    mBtnCategoryAll.setBackground(null);
                    mSymbol = 1001 ;
                    mType = 4 ;
                    mDrawerLayout.closeDrawers();
                    mPresenter.requestGetTodo(mSymbol,mType,mRefresh);
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
            refresh();
        }else if(resultCode == PostActivity.POST_ACTIVITY_RESULT_CODE){
            mRefresh.setRefreshing(true);
            refresh();
        }
    }
}
