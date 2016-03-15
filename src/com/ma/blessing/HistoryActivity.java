package com.ma.blessing;

import java.text.SimpleDateFormat;
import java.util.Locale;

import com.ma.blessing.data.HistoryItem;
import com.ma.blessing.db.DatabaseHelper;
import com.ma.blessing.db.Columns.ContentStatus;
import com.ma.blessing.ui.HistoryItemView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ListView;

public class HistoryActivity extends TitleBarActivity {

    protected static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);

    public static final String EXTRA_STATUS = "extra_status";

    private ListView mListView;
    private HistoryAdapter mAdapter;
    private int mStatus;
    private LoadContentTask mLoadContentTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        mStatus = intent.getIntExtra(EXTRA_STATUS, ContentStatus.SEND_ALL);
        updateTitle();
        loadContent();
    }

    private void updateTitle() {
        String title = null;
        if (mStatus == ContentStatus.SEND_EDIT) {
            title = getString(R.string.edit_to_send);
        } else if (mStatus == ContentStatus.SEND_FAIL) {
            title = getString(R.string.send_failed_history);
        } else if (mStatus == ContentStatus.SEND_PENDING) {
            title = getString(R.string.pending_to_send);
        } else if (mStatus == ContentStatus.SEND_SUCCESS) {
            title = getString(R.string.send_success_history);
        } else {
            title = getString(R.string.history);
        }
        mTitleView.setText(title);
    }

    @Override
    protected void onSetupView() {
        super.onSetupView();
        mListView = new ListView(this);
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mContentLayout.addView(mListView, params);
    }

    @Override
    protected void onPause() {
        stopLoad();
        super.onPause();
    }

    protected void loadContent() {
        stopLoad();
        mLoadContentTask = new LoadContentTask();
        mLoadContentTask.execute();
    }

    protected void stopLoad() {
        if (mLoadContentTask != null) {
            mLoadContentTask.cancel(true);
        }
    }

    protected class LoadContentTask extends AsyncTask<Void, Void, Cursor> {

        private static final String SQL_QUERY = "SELECT * from contact," +
                " send_history WHERE contact.contact_id = " +
                " send_history.contact_id AND status = ?";

        @Override
        protected Cursor doInBackground(Void... params) {
            SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
            return db.rawQuery(SQL_QUERY, new String[] { String.valueOf(mStatus) });
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null) {
                if (mAdapter == null) {
                    mAdapter = new HistoryAdapter(HistoryActivity.this, cursor);
                    mListView.setAdapter(mAdapter);
                } else {
                    mAdapter.changeCursor(cursor);
                }
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    protected class HistoryAdapter extends CursorAdapter {

        public HistoryAdapter(Context context, Cursor c) {
            super(context, c, false);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return new HistoryItemView(HistoryActivity.this);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            if (view instanceof HistoryItemView) {
                HistoryItem item = HistoryItem.parseFromCursor(cursor);
                ((HistoryItemView) view).bindView(item, DATE_FORMAT);
            }
        }
    }
}
