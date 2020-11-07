package apps.tridentfitness.utilHelper;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by redixbit on 03-11-2018.
 */

public class SqliteHelper extends SQLiteAssetHelper {
    private static final String DB_NAME = "workout.db";
    private static final int DB_VER = 1;

    public SqliteHelper(Context context) {
        super(context, DB_NAME, null,DB_VER );
    }

}
