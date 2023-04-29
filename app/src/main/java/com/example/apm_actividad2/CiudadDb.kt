package com.example.apm_actividad2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CiudadDb (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "city_database.db"

        const val TABLE_NAME = "cities"
        const val COLUMN_ID = "_id"
        const val COLUMN_COUNTRY = "country"
        const val COLUMN_CITY = "city"
        const val COLUMN_POPULATION = "population"

        const val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_COUNTRY TEXT, " +
                "$COLUMN_CITY TEXT, " +
                "$COLUMN_POPULATION INTEGER" +
                ")"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}

data class City(val id: Long?, val country: String, val city: String, var population: Int)

