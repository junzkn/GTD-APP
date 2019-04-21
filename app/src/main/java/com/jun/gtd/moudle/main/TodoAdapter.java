package com.jun.gtd.moudle.main;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.jun.gtd.R;
import com.jun.gtd.base.App;
import com.jun.gtd.bean.TodoBean;

import java.util.ArrayList;

import static com.jun.gtd.moudle.main.MainContract.PRIORITY_IMPORTANT_NOTURGENT;
import static com.jun.gtd.moudle.main.MainContract.PRIORITY_NOTURGENT_NOTIMPORTANT;
import static com.jun.gtd.moudle.main.MainContract.PRIORITY_URGENT_IMPORTANT;
import static com.jun.gtd.moudle.main.MainContract.PRIORITY_URGENT_NOTIMPORTANT;

public class TodoAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_TODO_TYPE = 0;
    public static final int TYPE_TODO      = 2;

    private int colorPriority1 = ContextCompat.getColor(App.getInstance(), R.color.red_800);
    private int colorPriority2 = ContextCompat.getColor(App.getInstance(), R.color.green_800);
    private int colorPriority3 = ContextCompat.getColor(App.getInstance(), R.color.grey_800);
    private int colorPriority4 = ContextCompat.getColor(App.getInstance(), R.color.grey_1000);


    private MainPresenter presenter ;


    public TodoAdapter(MainPresenter presenter) {
        super(new ArrayList<MultiItemEntity>());
        this.presenter = presenter ;
        addItemType(TYPE_TODO_TYPE, R.layout.item_todo_type);
        addItemType(TYPE_TODO, R.layout.item_todo);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()){
            case TYPE_TODO_TYPE :
                if("doing".equals(((TodoBean) item).getTitle())) {
                    helper.setText(R.id.tv_title,mContext.getString(R.string.doing))
                            .setTextColor(R.id.tv_title, ContextCompat.getColor(mContext, R.color.purple_500))
                            .setBackgroundColor(R.id.line, ContextCompat.getColor(mContext, R.color.purple_500));
                }else if ("undoing".equals(((TodoBean) item).getTitle()))  {
                    helper.setText(R.id.tv_title,mContext.getString(R.string.undoing))
                            .setTextColor(R.id.tv_title, ContextCompat.getColor(mContext, R.color.grey_500))
                            .setBackgroundColor(R.id.line, ContextCompat.getColor(mContext, R.color.grey_500));
                }
                break;
            case TYPE_TODO :
                final TodoBean todoBean = (TodoBean) item;

                final TextView title = helper.getView(R.id.tv_text);
                CheckBox checkBox = helper.getView(R.id.cb_check);
                TextView date = helper.getView(R.id.tv_date);
                AppCompatImageView content = helper.getView(R.id.img_content) ;

                title.setText(todoBean.getTitle());

                if(TextUtils.isEmpty(todoBean.getContent())){
                    content.setVisibility(View.GONE);
                }else {
                    content.setVisibility(View.VISIBLE);
                }

                if(TextUtils.isEmpty(todoBean.getCompleteDateStr())){
                    date.setVisibility(View.GONE);
                }else {
                    date.setText(todoBean.getCompleteDateStr());
                    date.setVisibility(View.VISIBLE);
                }

                switch(todoBean.getPriority() ){
                    case PRIORITY_URGENT_IMPORTANT:
                        title.setTextColor(colorPriority1);
                        break ;
                    case PRIORITY_IMPORTANT_NOTURGENT:
                        title.setTextColor(colorPriority2);
                        break;
                    case PRIORITY_URGENT_NOTIMPORTANT:
                        title.setTextColor(colorPriority3);
                        break ;
                    case PRIORITY_NOTURGENT_NOTIMPORTANT:
                        title.setTextColor(colorPriority4);
                        break;
                }

                if(todoBean.getStatus() == 1){
                    title.setAlpha(0.3F);
                    checkBox.setChecked(true);
                }else{
                    title.setAlpha(1.0F);
                    checkBox.setChecked(false);
                }

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b){
                            todoBean.setStatus(1);
                            title.animate()
                                    .setDuration(400)
                                    .alpha(0.3F)
                                    .start();
                        }else {
                            todoBean.setStatus(0);
                            title.animate()
                                    .setDuration(400)
                                    .alpha(1.0F)
                                    .start();
                        }
                        presenter.requestUpdateTodoStatus(todoBean.getId(),todoBean.getStatus());
                    }
                });

                break ;
        }
    }
}
