package com.ma.blessing.db;

public class Columns {

    public static final String CONTACT_ID = "contact_id";

    public static final String CONTACT_NAME = "name";

    public static final String CONTACT_EMAIL = "email";

    public static final String CONTACT_PHONE = "phone";

    public static final String CONTACT_CHARS = "chars";

    public static final String CONTACT_PREFERRED_PHONE = "preferred_phone";

    public static final String CONTACT_PREFFERED_EMAIL = "preferred_email";

    public static final String CONTACT_TAG = "tag";

    public static final String CONTACT_TAGS = "tags";

    public static final String CONTACT_SMS = "sms";

    public static final String CONTACT_PHOTO = "photo";

    // content
    public static final String CONTENT_CONTENT = "content";

    public static final String CONTENT_SEND_TIME = "send_time";

    public static final String CONTENT_UPDATE_TIME = "update_time";

    public static final String CONTENT_STATUS = "status";

    public static final class ContentStatus {
        public static final int SEND_SUCCESS = 8;
        public static final int SEND_FAIL = 4;
        public static final int SEND_PENDING = 2;
        public static final int SEND_EDIT = 1;
        public static final int SEND_ALL = SEND_EDIT | SEND_PENDING | SEND_FAIL | SEND_SUCCESS;
    }

}
