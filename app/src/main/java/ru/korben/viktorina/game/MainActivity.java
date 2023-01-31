package ru.korben.viktorina.game;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.text.SimpleDateFormat;

import ru.korben.viktorina.R;

public class MainActivity extends ListActivity implements View.OnClickListener {

    private Database db;
    private Cursor cursor;
    private static final int DELETE_ID = 1;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    int DIALOG_GOOD_RESULT = 1;
    int DIALOG_BAD_RESULT = 2;
    int DIALOG_WRONG = 3;
    int max_result = 7;
    int percentage_result = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStartTest = (Button) findViewById(R.id.btnStartTest);
        btnStartTest.setOnClickListener(this);
        db = new Database(this);
        db.open();
        fillData();
        registerForContextMenu(getListView());
    }

    private void fillData() {
        cursor = db.getAllData();
        startManagingCursor(cursor);
        String[] from = new String[] { Database.COLUMN_TEXT, Database.COLUMN_DATE };
        int[] to = new int[] { R.id.tvText, R.id.tvDate };
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.result_layout, cursor, from, to);
        setListAdapter(simpleCursorAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        cursor.moveToPosition(position);
        String countTrueAnswers = cursor.getString(cursor.getColumnIndex(Database.COLUMN_RESULT_VALUE));
        Log.d("myLogs", countTrueAnswers);
        if (Integer.parseInt(countTrueAnswers) > 60) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, "My result in QuizApp is: " + countTrueAnswers + "%" + " correct answers!");
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, null));
        } else if (Integer.parseInt(countTrueAnswers) < 60) {
            showDialog(DIALOG_WRONG);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 1, 0, R.string.delRec);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == DELETE_ID) {
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            db.delRec(acmi.id);
            cursor.requery();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, QuestionActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        int result = data.getIntExtra("trueAnswers", 0);
        percentage_result = result * 100/max_result;
        if (percentage_result > 60) {
            showDialog(DIALOG_GOOD_RESULT);
        } else if(percentage_result < 60) {
            showDialog(DIALOG_BAD_RESULT);
        }
        String datetime = sdf.format(System.currentTimeMillis());
        String text = String.valueOf(percentage_result);
        db.addRec(text, datetime, text);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder adb;
        if (id == 1) {
            adb = new AlertDialog.Builder(this);
            adb.setTitle(R.string.goodresult);
            adb.setPositiveButton(R.string.ok, onClickListener);
            adb.setNeutralButton(R.string.share, onClickListener);
            adb.setMessage("Your result is " + percentage_result + "%" + " correct answers!");
            return adb.create();
        }
        if (id == 2) {
            adb = new AlertDialog.Builder(this);
            adb.setTitle(R.string.badresult);
            adb.setPositiveButton(R.string.ok, onClickListener);
            adb.setMessage("Your result is " + percentage_result + "%" + " correct answers!");
            return adb.create();
        }
        if (id == 3 ) {
            adb = new AlertDialog.Builder(this);
            adb.setTitle(R.string.wrong);
            adb.setMessage(R.string.sorry);
            adb.setNegativeButton(R.string.ok, onClickListener);
            return adb.create();

        }
        return super.onCreateDialog(id);
    }

    DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case Dialog.BUTTON_POSITIVE:
                    fillData();
                    dialog.dismiss();
                    break;
                case Dialog.BUTTON_NEGATIVE:
                    dialog.dismiss();
                    break;
                case Dialog.BUTTON_NEUTRAL:
                    fillData();
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, "My result in QuizApp is: " + String.valueOf(percentage_result) + "%" + " correct answers!");
                    intent.setType("text/plain");
                    startActivity(Intent.createChooser(intent, null));
                    dialog.dismiss();
                    break;

            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
