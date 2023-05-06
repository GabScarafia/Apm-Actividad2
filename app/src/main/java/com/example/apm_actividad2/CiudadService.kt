package com.example.apm_actividad2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.apm_actividad2.CiudadDb.Companion.COLUMN_CITY
import com.example.apm_actividad2.CiudadDb.Companion.COLUMN_COUNTRY
import com.example.apm_actividad2.CiudadDb.Companion.COLUMN_ID
import com.example.apm_actividad2.CiudadDb.Companion.COLUMN_POPULATION
import com.example.apm_actividad2.CiudadDb.Companion.TABLE_NAME

class CiudadService(context: Context) {

    private val databaseHelper = CiudadDb(context)

    fun addCity(city: City) : Boolean {
            val db = databaseHelper.writableDatabase

            val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_COUNTRY = ? AND $COLUMN_CITY = ?"
            val cursor = db.rawQuery(query, arrayOf(city.country, city.city))

            if (cursor.moveToFirst()) {
                cursor.close()
                db.close()
                return false
            }

            val values = ContentValues().apply {
                put(COLUMN_COUNTRY, city.country)
                put(COLUMN_CITY, city.city)
                put(COLUMN_POPULATION, city.population)
            }
            val result = db.insert(TABLE_NAME, null, values)
            db.close()

            return result >= 0
        }

    fun getCityByName(cityName: String): City? {
        val db = databaseHelper.readableDatabase

        val cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_ID, COLUMN_COUNTRY, COLUMN_CITY, COLUMN_POPULATION),
            "$COLUMN_CITY = ?",
            arrayOf(cityName),
            null,
            null,
            null,
            "1"
        )

        val city = if (cursor.moveToFirst()) {
            City(
                cursor.getLong(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3)
            )
        } else {
            null
        }

        cursor.close()
        db.close()

        return city
    }

    fun deleteCityByName(cityName: String) : Boolean {
        val db = databaseHelper.writableDatabase

        val res = db.delete(
            TABLE_NAME,
            "$COLUMN_CITY = ?",
            arrayOf(cityName)
        )
        db.close()
        return res > 0
    }

    fun deleteCitiesByCountry(country: String) : Boolean {
        val db = databaseHelper.writableDatabase

        val res = db.delete(
            TABLE_NAME,
            "$COLUMN_COUNTRY = ?",
            arrayOf(country)
        )
        db.close()
        return res > 0
    }

    fun updateCityPopulation(city: City) {
        val db = databaseHelper.writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_POPULATION, city.population)
        }

        db.update(
            TABLE_NAME,
            values,
            "$COLUMN_ID = ?",
            arrayOf(city.id.toString())
        )

        db.close()
    }
}