package com.example.guruapp_1

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_librarycafe.*
import org.w3c.dom.Text
import java.util.*

class LibraryCafeActivity : AppCompatActivity() {

    lateinit var grazieMenu: Button

    var grazie: String = "grazie"
    var resetNum: Int = 3
    lateinit var myHelper: myDBHelper
    lateinit var sqlDB: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_librarycafe)

        grazieMenu = findViewById<Button>(R.id.libcafe_button1)

        myHelper = myDBHelper(this)

        grazieMenu.setOnClickListener() {
//            sqlDB = myHelper.writableDatabase
//            sqlDB.execSQL("DELETE FROM guruTBL WHERE gName = '" + grazie + "';")
//            Toast.makeText(applicationContext, "삭제됨", Toast.LENGTH_SHORT).show()
//            sqlDB.execSQL("INSERT INTO guruTBL VALUES ( '" + grazie + "' , " + resetNum + ");")
//            Toast.makeText(applicationContext, "입력됨", Toast.LENGTH_SHORT).show()
            var intent = Intent(this, GrazieActivity::class.java)
            startActivity(intent)
        }

        registerForContextMenu(libcafe_button1)

        disabledGrazie()
//        setBtnColor()
    }

       private fun disabledGrazie() {
            val cal = Calendar.getInstance()
                cal.set(Calendar.HOUR_OF_DAY, 18)
                cal.set(Calendar.MINUTE, 30)
                cal.set(Calendar.SECOND, 0)

            val timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    libcafe_button1.setBackgroundResource(R.drawable.button_background4)
                    libcafe_button1.setOnClickListener() {
                        false
                    }
                    unregisterForContextMenu(libcafe_button1)
                }
            }, cal.time)

           cal.set(Calendar.HOUR_OF_DAY, 8)
           cal.set(Calendar.MINUTE, 30)
           cal.set(Calendar.SECOND, 0)

           timer.schedule(object : TimerTask() {
               override fun run() {
                   libcafe_button1.setOnClickListener() {
                       true
                   }
                   registerForContextMenu(libcafe_button1)
               }
           }, cal.time)
        }


        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            super.onCreateContextMenu(menu, v, menuInfo)

            when (v?.id) {
                R.id.libcafe_button1 -> {
                    menu?.setHeaderTitle("북카페 그라찌에 혼잡도")
                    menuInflater.inflate(R.menu.popup_menu_libcafe, menu)
                }
            }
        }

        override fun onContextItemSelected(item: MenuItem): Boolean {
            when (item?.itemId) {
                R.id.select1_libcafe -> {
                    libcafe_button1.setBackgroundResource(R.drawable.button_background1)
                    sqlDB = myHelper.writableDatabase
                    sqlDB.execSQL("UPDATE guruTBL SET gNumber = " + 1 + " WHERE gName = '" + grazie + "';")
//                    sqlDB = myHelper.readableDatabase
//                    var cursor: Cursor
//                    cursor = sqlDB.rawQuery("SELECT gNumber FROM guruTBL WHERE gName = '" + grazie + "';")

                    Toast.makeText(applicationContext, "혼잡", Toast.LENGTH_SHORT).show()
                }
                R.id.select2_libcafe -> {
                    libcafe_button1.setBackgroundResource(R.drawable.button_background2)
                    sqlDB = myHelper.writableDatabase
                    sqlDB.execSQL("UPDATE guruTBL SET gNumber = " + 2 + " WHERE gName = '" + grazie + "';")
                    Toast.makeText(applicationContext, "보통", Toast.LENGTH_SHORT).show()
                }
                R.id.select3_libcafe -> {
                    libcafe_button1.setBackgroundResource(R.drawable.button_background3)
                    sqlDB = myHelper.writableDatabase
                    sqlDB.execSQL("UPDATE guruTBL SET gNumber = " + 3 + " WHERE gName = '" + grazie + "';")
                    Toast.makeText(applicationContext, "여유", Toast.LENGTH_SHORT).show()
                }
            }
            return super.onContextItemSelected(item)
        }

//        private fun setBtnColor() {
//            sqlDB = myHelper.readableDatabase
//            var cursor: Cursor
//            var bgNum: Int
//            cursor = sqlDB.rawQuery("SELECT * FROM guruTBL WHERE gName = '" + grazie + "';",null)
//
//            if(cursor.moveToNext()) {
//                bgNum = cursor.getInt(0)
//                bgNum = cursor.getInt(1)
//                if (bgNum == 1) libcafe_button1.setBackgroundResource(R.drawable.button_background1)
//                else if (bgNum == 2) libcafe_button1.setBackgroundResource(R.drawable.button_background2)
//                else if (bgNum == 3) libcafe_button1.setBackgroundResource(R.drawable.button_background3)
//            }
//        }

        inner class myDBHelper(context: Context) : SQLiteOpenHelper(context, "guruDB", null, 1) {
            override fun onCreate(db: SQLiteDatabase?) {
                db!!.execSQL("CREATE TABLE guruTBL (gName CHAR(50) PRIMARY KEY, gNumber INTEGER);")
            }

            override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
                db!!.execSQL("DROP TABLE IF EXISTS guruTBL")
                onCreate(db)
            }
        }
}




