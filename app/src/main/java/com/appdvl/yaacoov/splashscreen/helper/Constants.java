/*
 * Copyright (c) 2015-2016 Filippo Engidashet. All Rights Reserved.
 * <p>
 *  Save to the extent permitted by law, you may not use, copy, modify,
 *  distribute or create derivative works of this material or any part
 *  of it without the prior written consent of Filippo Engidashet.
 *  <p>
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 */

package com.appdvl.yaacoov.splashscreen.helper;

/**
 * @author Filippo Engidashet
 * @version 1.0
 * @date today
 */
public class Constants {



    public static final class DATABASE {

        public static final String DB_NAME = "contacts";
        public static final int DB_VERSION = 1;
        public static final String TABLE_NAME = "contact";

        public static final String DROP_QUERY = "DROP TABLE IF EXIST " + TABLE_NAME;

        public static final String GET_CONTACTS_QUERY = "SELECT * FROM " + TABLE_NAME;

        public static final String CONTACT_ID = "contactId";
        public static final String ADDRESS = "address";
        public static final String EMAIL = "EMAIL";
        public static final String NAME = "name";
        public static final String GENDER = "gender";



        public static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "" +
                "(" + CONTACT_ID + " INTEGER PRIMARY KEY not null," +
                ADDRESS + " TEXT not null," +
                EMAIL + " TEXT not null," +
                NAME + " TEXT not null," +
                GENDER + " TEXT not null,";
    }

    public static final class REFERENCE {
        public static final String CONTACT = Config.PACKAGE_NAME + "contact";
    }

    private static final class Config {
        static final String PACKAGE_NAME = "com.appdvl.yaacoov.splashscreen";
    }


}
