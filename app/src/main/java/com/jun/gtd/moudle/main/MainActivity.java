package com.jun.gtd.moudle.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
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

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View,SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout mRefresh;
    private RecyclerView mTodoListView;
    private TodoAdapter mTodoAdapter;
    private FrameLayout mFragmentContainerView;
    private FloatingActionButton mFabAdd;
    private View mBubbleDialogView;
    private TextView mContentBubbleDialogText;
    private View mHeadView;
    private TextView mHeadViewText;
    private BubbleDialog mBubbleDialog;
    private BubbleDialog mContentBubbleDialog ;
    private BubbleDialog mTipBubbleDialog;

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

        mTodoAdapter = new TodoAdapter(mContext,mPresenter);
        mTodoListView.setAdapter(mTodoAdapter);
        ToolbarUtils.initPaddingTopDiffBar(mTodoListView);
        ViewEmptyUtils.setEmpty(mTodoListView);

        mRefresh.setColorSchemeResources(R.color.colorAccent, R.color.green_500, R.color.purple_500, R.color.grey_500);

    }

    @Override
    protected void prepare() {
        mRefresh.setOnRefreshListener(this);
        mFabAdd.setOnClickListener(mClickListener);
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
        onRefresh();
    }

    @Override
    public void displayTodoList(List<TodoBean> todos) {
        mRefresh.setRefreshing(false);
        List<MultiItemEntity> src = sort(todos,0);
        mTodoAdapter.setNewData(src);
        mTodoAdapter.expandAll();
        mTodoAdapter.notifyDataSetChanged();
    }


    private List<MultiItemEntity> sort(List<TodoBean> todos , int flag){
        List<MultiItemEntity> src = new ArrayList<>();
        List<MultiItemEntity> doing = new ArrayList<>();
        List<MultiItemEntity> done = new ArrayList<>();
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
                return "doing";
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
                return "done";
            }
        });
        src.addAll(done) ;
        return src ;
    }

    @Override
    public void showChooseTodoCategory() {

    }

    @Override
    public void hideChooseTodoCategory() {

    }

    @Override
    public void guidLogin() {

    }

    @Override
    public void showDeleteTip(int position, TodoBean todoBean) {

    }

    @Override
    public void showLongClickDialog(View clickView, int position, TodoBean todoBean) {

    }

    @Override
    public void onRefresh() {
        mPresenter.requestGetTodo(mSymbol,mType,mRefresh);
    }


    private class ClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.fab_add :
                    if(App.Login.isLogin()){
                        PostActivity.launchActivity(mActivity,null);
                    }else {
                        LoginActivity.launchActivity(mActivity);
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
            onRefresh();
        }else if(resultCode == PostActivity.POST_ACTIVITY_RESULT_CODE){
            mRefresh.setRefreshing(true);
            onRefresh();
        }
    }
}
