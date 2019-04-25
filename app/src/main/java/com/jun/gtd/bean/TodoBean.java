package com.jun.gtd.bean;


import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.jun.gtd.moudle.main.TodoAdapter;

import java.io.Serializable;

public class TodoBean implements MultiItemEntity, Serializable {


    public static final int GET_BY_TYPE = 1001 ;
    public static final int GET_BY_STATUS = 1002;
    public static final int GET_ALL = 1003 ;

    public static final int PRIORITY_URGENT_IMPORTANT = 1;
    public static final int PRIORITY_IMPORTANT_NOTURGENT = 2;
    public static final int PRIORITY_URGENT_NOTIMPORTANT = 3;
    public static final int PRIORITY_NOTURGENT_NOTIMPORTANT = 0;

    private int id ;
    private String title ;
    private String content ;
    private int status ;
    private int type ;
    private int priority ;
    private int userid ;
    private long completeDate ;
    private String completeDateStr ;
    private long createDate ;
    private String createDateStr ;


    @Override
    public String toString() {
        return "TodoBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", priority=" + priority +
                ", userid=" + userid +
                ", completeDate=" + completeDate +
                ", completeDateStr='" + completeDateStr + '\'' +
                ", createDate=" + createDate +
                ", createDateStr='" + createDateStr + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public long getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(long completeDate) {
        this.completeDate = completeDate;
    }

    public String getCompleteDateStr() {
        return completeDateStr;
    }

    public void setCompleteDateStr(String completeDateStr) {
        this.completeDateStr = completeDateStr;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }


    @Override
    public int getItemType() {
        return TodoAdapter.TYPE_TODO;
    }
}
