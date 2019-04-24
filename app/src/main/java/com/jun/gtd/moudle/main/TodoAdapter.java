package com.jun.gtd.moudle.main;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.jun.gtd.R;
import com.jun.gtd.base.App;
import com.jun.gtd.bean.TodoBean;
import com.jun.gtd.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import static com.jun.gtd.moudle.main.MainContract.PRIORITY_IMPORTANT_NOTURGENT;
import static com.jun.gtd.moudle.main.MainContract.PRIORITY_NOTURGENT_NOTIMPORTANT;
import static com.jun.gtd.moudle.main.MainContract.PRIORITY_URGENT_IMPORTANT;
import static com.jun.gtd.moudle.main.MainContract.PRIORITY_URGENT_NOTIMPORTANT;

public class TodoAdapter extends BaseMultiItemQuickAdapter<TodoBean, BaseViewHolder> {

    public static final int TYPE_TODO_TYPE = 0;
    public static final int TYPE_TODO      = 2;

    private int red = ContextCompat.getColor(App.getInstance(), R.color.red_700);
    private int yellow = ContextCompat.getColor(App.getInstance(), R.color.yellow_700);
    private int blue = ContextCompat.getColor(App.getInstance(), R.color.blue_800);
    private int black = ContextCompat.getColor(App.getInstance(), R.color.grey_1000);


    private MainPresenter presenter ;
    private MainActivity activity;

    private List<TodoBean> cache = new ArrayList<>();


    public TodoAdapter(MainActivity activity , MainPresenter presenter ) {
        super(new ArrayList<TodoBean>());
        this.presenter = presenter ;
        this.activity = activity ;
        addItemType(TYPE_TODO_TYPE, R.layout.item_todo_type);
        addItemType(TYPE_TODO, R.layout.item_todo);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final TodoBean item) {
        switch (helper.getItemViewType()){
            case TYPE_TODO_TYPE :
                if(item.getTitle().equals(mContext.getString(R.string.done))){
                    helper.setText(R.id.tv_title, item.getTitle())
                            .setTextColor(R.id.tv_title, ContextCompat.getColor(activity, R.color.grey_500))
                            .setBackgroundColor(R.id.line, ContextCompat.getColor(activity, R.color.grey_500));
                }
                else if(item.getTitle().equals(mContext.getString(R.string.doing))){
                    helper.setText(R.id.tv_title, item.getTitle())
                            .setTextColor(R.id.tv_title, ContextCompat.getColor(activity, R.color.colorAccent))
                            .setBackgroundColor(R.id.line, ContextCompat.getColor(activity, R.color.colorAccent));
                }

                break;
            case TYPE_TODO :
                final TextView title = helper.getView(R.id.tv_text);
                final AppCompatImageView checkBoxImage = helper.getView(R.id.img_check);
                final FrameLayout checkBox = helper.getView(R.id.btn_check);
                TextView date = helper.getView(R.id.tv_date);
                AppCompatImageView content = helper.getView(R.id.img_content) ;

                title.setText(item.getTitle());

                if(TextUtils.isEmpty(item.getContent())){
                    content.setVisibility(View.GONE);
                }else {
                    content.setVisibility(View.VISIBLE);
                }

                if(TextUtils.isEmpty(item.getCompleteDateStr())){
                    date.setVisibility(View.GONE);
                }else {
                    date.setText(item.getCompleteDateStr());
                    date.setVisibility(View.VISIBLE);
                }

                switch(item.getPriority() ){
                    case PRIORITY_URGENT_IMPORTANT:
                        title.setTextColor(red);
                        checkBoxImage.setColorFilter(red);
                        break ;
                    case PRIORITY_IMPORTANT_NOTURGENT:
                        title.setTextColor(yellow);
                        checkBoxImage.setColorFilter(yellow);
                        break;
                    case PRIORITY_URGENT_NOTIMPORTANT:
                        checkBoxImage.setColorFilter(blue);
                        title.setTextColor(blue);
                        break ;
                    case PRIORITY_NOTURGENT_NOTIMPORTANT:
                        title.setTextColor(black);
                        checkBoxImage.setColorFilter(black);
                        break;
                }

                if(item.getStatus() == 1){
                    title.setAlpha(0.3F);
                    checkBoxImage.setImageResource(R.drawable.ic_select);
                    checkBoxImage.setAlpha(0.3F);
                }else{
                    title.setAlpha(1.0F);
                    checkBoxImage.setAlpha(1.0F);
                    checkBoxImage.setImageResource(R.drawable.ic_unselect);
                }

                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(item.getStatus() == 0){
                            item.setStatus(1);
                            title.animate().setDuration(400).alpha(0.3F).start();
                            checkBoxImage.animate().setDuration(400).alpha(0.3F).start();
                            checkBoxImage.setImageResource(R.drawable.ic_select);
                        }else{
                            item.setStatus(0);
                            title.animate().setDuration(400).alpha(1.0F).start();
                            checkBoxImage.animate().setDuration(400).alpha(1.0F).start();
                            checkBoxImage.setImageResource(R.drawable.ic_unselect);

                        }
                        presenter.requestUpdateTodoStatus(item.getId(),item.getStatus());
                    }
                });


                break ;
        }
    }
}
