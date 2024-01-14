@file:Suppress("DEPRECATION")

package swati4star.createpdf.database

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import java.util.Date

class DatabaseHelper(private val context: Context) {
    /**
     * To insert record in the database
     *
     * @param filePath      path of the file
     * @param operationType operation performed on file
     */
    fun insertRecord(filePath: String?, operationType: String?) {
        Insert().execute(History(filePath, Date().toString(), operationType))
    }

    @Suppress("OVERRIDE_DEPRECATION")
    @SuppressLint("StaticFieldLeak")
    private inner class Insert : AsyncTask<History, Unit, Unit>() {
        override fun doInBackground(vararg histories: History) {
            val db = AppDatabase.getDatabase(context.applicationContext)
            db.historyDao().insertAll(*histories)
        }
    }
}