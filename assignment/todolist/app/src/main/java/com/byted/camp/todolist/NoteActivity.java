package com.byted.camp.todolist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.byted.camp.todolist.beans.State;
import com.byted.camp.todolist.db.TodoContract;
import com.byted.camp.todolist.db.TodoDbHelper;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class NoteActivity extends AppCompatActivity {

    private EditText editText;
    private Button addBtn;

    private TodoDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        setTitle(R.string.take_a_note);

        editText = findViewById(R.id.edit_text);
        editText.setFocusable(true);
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.showSoftInput(editText, 0);
        }

        addBtn = findViewById(R.id.btn_add);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence content = editText.getText();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(NoteActivity.this,
                            "No content to add", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean succeed = saveNote2Database(content.toString().trim());
                if (succeed) {
                    Toast.makeText(NoteActivity.this,
                            "Note added", Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK);
                } else {
                    Toast.makeText(NoteActivity.this,
                            "Error", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

        dbHelper = new TodoDbHelper(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }


    private boolean saveNote2Database(String content) {
        // TODO 插入一条新数据，返回是否插入成功
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TodoContract.TodoList.COLUMN_NAME_THING, content);


        SimpleDateFormat df = new SimpleDateFormat("E,d MMM yyyy HH:mm:ss"/*, Locale.ENGLISH*/);
        Timestamp time1 = new Timestamp(System.currentTimeMillis());
        String time;
        time = df.format(time1);
        values.put(TodoContract.TodoList.COLUMN_NAME_TIME,time);

        State state = State.TODO;
        values.put(TodoContract.TodoList.COLUMN_NAME_STATE,state.intValue);


        long newRowID = db.insert(TodoContract.TodoList.TABLE_NAME,null,values);
        if(newRowID < 0)
            return false;
        else
            return true;
    }
}
