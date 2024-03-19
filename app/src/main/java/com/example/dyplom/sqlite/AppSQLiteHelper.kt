//package com.example.dyplom.sqlite
//
//import android.app.Application
//import android.database.sqlite.SQLiteDatabase
//import android.database.sqlite.SQLiteOpenHelper
//import javax.inject.Inject
//
//class AppSQLiteHelper @Inject constructor(private val app: Application) :
//    SQLiteOpenHelper(app, "db", null, 1) {
//    override fun onCreate(db: SQLiteDatabase) {
//        val sql = app.assets.open("dbAssets.sql").bufferedReader().use {
//            it.readText()
//        }
//        sql.split(";")
//            .filter { it.isNotBlank() }
//            .forEach{
//                db.execSQL(it)
//            }
//    }
//
//    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
//
//    }
//
//
//}