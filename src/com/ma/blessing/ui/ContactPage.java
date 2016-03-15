package com.ma.blessing.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ma.blessing.R;
import com.ma.blessing.data.Contact;
import com.ma.blessing.db.DatabaseHelper;
import com.ma.blessing.ui.PinYinVerticalNavigation.NavigationListener;
import com.ma.blessing.util.IOUtil;

public class ContactPage extends FrameLayout implements NavigationListener {

    private ListView mListView;
    private PinYinVerticalNavigation mNavigationView;
    private TextView mNavigationTextView;

    // data
    private ContactAdapter mAdapter;
    private Map<String, Integer> mNavigationMap = new HashMap<>();

    public ContactPage(Context context) {
        this(context, null);
    }

    public ContactPage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ContactPage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.contact_page, this);
        mListView = (ListView) findViewById(R.id.contact_list);
        mNavigationTextView = (TextView) findViewById(R.id.navigation_text);
        mNavigationView = (PinYinVerticalNavigation) findViewById(R.id.navigation);
        mNavigationView.setNavigationListener(this);

        mAdapter = new ContactAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setDividerHeight(0);

        new LoadTask().execute();
    }

    @Override
    public void onNavigationStart(String currentChar) {
        mNavigationTextView.setText(currentChar);
        mNavigationTextView.setVisibility(View.VISIBLE);
        scrollList(currentChar);
    }

    @Override
    public void onNavigationFinished(String currentChar) {
        mNavigationTextView.setText(currentChar);
        mNavigationTextView.setVisibility(View.GONE);
        scrollList(currentChar);
    }

    @Override
    public void onNavigationChanged(String currentChar) {
        mNavigationTextView.setText(currentChar);
        scrollList(currentChar);
    }

    private void scrollList(String currentChar) {
        Integer position = mNavigationMap.get(currentChar);
        if (position != null) {
            mListView.setSelection(position);
        }
    }

    private class ContactAdapter extends BaseAdapter {

        private List<Contact> mContacts = new ArrayList<>();

        @Override
        public int getCount() {
            return mContacts.size();
        }

        @Override
        public Object getItem(int position) {
            return mContacts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = new ContactItemView(getContext());
            }

            if (convertView instanceof ContactItemView) {
                Contact contact = mContacts.get(position);
                String head = contact.nameAsChar.substring(0, 1);
                Integer otherStart = mNavigationMap.get(TAIL_CHAR);
                if (position > otherStart) {
                    head = null;
                } else if (position == otherStart) {
                    head = TAIL_CHAR;
                } else {
                    head = mNavigationMap.get(head) == position ? head : null;
                }
                ((ContactItemView) convertView).bindView(mContacts.get(position), head, TextUtils.isEmpty(head));
            }

            return convertView;
        }
    }

    private static final String MAX_NORMAL_CHAR = "Z";
    private static final String MIN_NORMAL_CHAR = "A";
    private static final String TAIL_CHAR = "#";
    private static final String HEAD_CHAR = ".";
    private static final int OFFSET = MAX_NORMAL_CHAR.compareTo(MIN_NORMAL_CHAR);

    private class LoadTask extends AsyncTask<Void, Void, List<Contact>> {

        private Map<String, Integer> mTempMap = new HashMap<>();

        @Override
        protected List<Contact> doInBackground(Void... params) {
            SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
            Cursor cursor = null;
            List<Contact> contacts = new ArrayList<>();
            List<Contact> trailList = new ArrayList<>();
            try {
                cursor = db.query(DatabaseHelper.CONTACT_TABLE,
                        null, null, null, null, null, "chars ASC");
                if (cursor != null) {
                    while(cursor.moveToNext()) {
                        Contact contact = Contact.parseFromCursor(cursor);
                        if (contact != null) {
                            String head = updateNavigationMap(contact, contacts.size());
                            if (head == null) {
                                trailList.add(contact);
                            } else {
                                int relativeValue = head.compareTo(MIN_NORMAL_CHAR);
                                if (relativeValue < 0 || relativeValue > OFFSET) {
                                    trailList.add(contact);
                                } else {
                                    contacts.add(contact);
                                }
                            }
                        }
                    }
                }

                mTempMap.put(HEAD_CHAR, 0);
                mTempMap.put(TAIL_CHAR, contacts.size());
                contacts.addAll(trailList);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtil.close(cursor);
            }
            return contacts;
        }

        private String updateNavigationMap(Contact contact, int position) {
            if (!TextUtils.isEmpty(contact.nameAsChar)) {
                String head = contact.nameAsChar.substring(0, 1);
                if (!mTempMap.containsKey(head)) {
                    mTempMap.put(head, position);
                }
                return head;
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Contact> contacts) {
            mNavigationMap = mTempMap;
            mAdapter.mContacts = contacts;
            mAdapter.notifyDataSetChanged();
        }
    }
}
