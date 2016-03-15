package com.ma.blessing;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ma.blessing.data.Contact;
import com.ma.blessing.db.DatabaseHelper;
import com.ma.blessing.ui.ContactItemView;
import com.ma.blessing.ui.ItemHeadView;

public class SearchActivity extends Activity {

    public static final String EXTRA_DATA_TYPE = "data_type";

    public static final String EXTRA_DATA = "data";
    public static final String EXTRA_TYPE_CONTACT = "contact";
    public static final String EXTRA_TYPE_MESSAGE = "message";

    private EditText mEditText;
    private TextView mSearchButton;
    private ListView mListView;

    private SearchAdapter mAdapter;
    private SearchTask mSearchTask;
    private Object mCurrentObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupView(this);
    }

    private void setupView(Context context) {

        mEditText = (EditText) findViewById(R.id.edit);
        mEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString();
                if (mCurrentObject instanceof Contact) {
                    Contact contact = (Contact) mCurrentObject;
                    if (TextUtils.equals(contact.name, query)) {
                        return;
                    }
                }
                if (mSearchTask != null) {
                    mSearchTask.cancel(true);
                }
                mSearchTask = new SearchTask(query);
                mSearchTask.execute();
                mCurrentObject = null;
                mSearchButton.setEnabled(false);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSearchButton = (TextView) findViewById(R.id.go_button);
        mSearchButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mCurrentObject != null) {
                    Intent intent = new Intent();
                    if (mCurrentObject instanceof Contact) {
                        intent.putExtra(EXTRA_DATA_TYPE, EXTRA_TYPE_CONTACT);
                        intent.putExtra(EXTRA_DATA, ((Contact)mCurrentObject).id);
                    }
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        mListView = (ListView) findViewById(R.id.search_list);
        mListView.setDividerHeight(0);
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                Object data = mAdapter.mSearchObjects.get(position);
                if (data instanceof Contact) {
                    mCurrentObject = data;
                    mEditText.setText(((Contact) data).name);
                    mSearchButton.setEnabled(true);
                }
            }
        });
    }

    private static final String SEARCH_MATCH_FORMAT = "name like ? or preferred_phone like ?";

    private class SearchTask extends AsyncTask<Void, Void, List<Object>> {

        private String mQuery;

        public SearchTask(String query) {
            mQuery = query;
        }

        @Override
        protected List<Object> doInBackground(Void... params) {
            SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
            String arge = "%" + mQuery + "%";
            Cursor cursor = db.query(DatabaseHelper.CONTACT_TABLE, null,
                    SEARCH_MATCH_FORMAT, new String[] {arge, arge}, null, null, null);
            List<Object> result = new ArrayList<>();
            if (cursor != null && cursor.getCount() > 0) {
                result.add("联系人");
                while (cursor.moveToNext()) {
                    Contact contact = Contact.parseFromCursor(cursor);
                    result.add(contact);
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<Object> result) {
            if (mAdapter == null) {
                mAdapter = new SearchAdapter(result);
                mListView.setAdapter(mAdapter);
            } else {
                mAdapter.mSearchObjects = result;
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    private static final int VIEW_TYPE_HEAD = 1;
    private static final int VIEW_TYPE_FOOT = 2;
    private static final int VIEW_TYPE_CONTACT = 3;
    private static final int VIEW_TYPE_MESSAGE = 4;

    private class SearchAdapter extends BaseAdapter {

        private List<Object> mSearchObjects;

        public SearchAdapter(List<Object> searchObjects) {
            mSearchObjects = searchObjects;
        }

        @Override
        public int getCount() {
            return mSearchObjects.size();
        }

        @Override
        public Object getItem(int position) {
            return mSearchObjects.get(position);
        }

        @Override
        public int getItemViewType(int position) {
            Object data = mSearchObjects.get(position);
            if (data instanceof String) {
                return VIEW_TYPE_HEAD;
            } else if (data instanceof Contact) {
                return VIEW_TYPE_CONTACT;
            }
            return VIEW_TYPE_CONTACT;
        }

        @Override
        public int getViewTypeCount() {
            return 4;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Object data = mSearchObjects.get(position);
            if (data instanceof String) {
                if (!(convertView instanceof ItemHeadView)) {
                    convertView = new ItemHeadView(SearchActivity.this);
                }
                ((ItemHeadView) convertView).setTitle((String)data);
            } else if (data instanceof Contact) {
                if (!(convertView instanceof ContactItemView)) {
                    convertView = new ContactItemView(SearchActivity.this);
                }
                ((ContactItemView) convertView).bindView((Contact) data, null, true);
            }
            return convertView;
        }
    }
}
