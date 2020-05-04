package com.byted.camp.todolist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.byted.camp.todolist.db.TodoContract.SQL_CREATE_LISTS;
import static com.byted.camp.todolist.db.TodoContract.SQL_DELETE_TodoList;

/**
 * Created on 2019/1/22.
 *
 * @author xuyingyi@bytedance.com (Yingyi Xu)
 */
public class TodoDbHelper extends SQLiteOpenHelper {

    // TODO 定义数据库名、版本；创建数据库
    public static final int DATABASE_VERSION = 0;
    public static final String DATABASE_NAME = "Todo.db";

    public TodoDbHelper(Context context) {
        //Log.i("LINYUEBEI", "TodoDbHelper:before");
        //super(context,DATABASE_NAME,null,DATABASE_VERSION );
        super(context, "todo", null, 1);
        Log.i("LINYUEBEI", "TodoDbHelper:after");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_LISTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TodoList);
        onCreate(db);
        if (oldVersion == 1 && newVersion == 2) {
            db.execSQL("");
        }
    }

}
