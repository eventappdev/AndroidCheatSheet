package com.androidprojects.androidcheatsheet.database;

import android.provider.BaseColumns;

/**
 * Created by Nikhitha Tarala on 2/28/2017.
 */

public class PartyList {


    //create innerclass and implement basecolumn creates _ID as default column for unique identification of rows
    public static final class PartyListInnerClass implements BaseColumns {


        public static final String TABLE_NAME="newList";
        public static final String COLUMN_NAME="name";
        public static final String COLUMN_LENGTH="size";
        public static final String COLUMN_TIMESTAMP="time";

    }
}
