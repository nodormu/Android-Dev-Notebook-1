//Note:  This is using SQLite which is not good for larger databases such as the kind a monitoring system can generate.

//Define your tables as data classes annotated with @Entity. Define properties annotated with @ColumnInfo as columns in the tables.
//Define a data access object (DAO) as an interface annotated with @Dao. The DAO maps Kotlin functions to database queries.
//Use annotations to define @Insert, @Delete, and @Update functions.
//Use the @Query annotation with an SQLite query string as a parameter for any other queries.
//Create an abstract class that has a getInstance() function that returns a database.
//Use instrumented tests to test that your database and DAO are working as expected. You can use the provided tests as a template.


package com.example.android.trackmysleepquality.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SleepDatabaseDao {
    @Insert
    fun insert(night: SleepNight)

    @Update
    fun update(night: SleepNight)

    @Query("SELECT * from daily_sleep_quality_table WHERE nightId = :key")
    fun get(key: Long): SleepNight?

    @Query("DELETE FROM daily_sleep_quality_table")
    fun clear()

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
    fun getTonight(): SleepNight?

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC")
    fun getAllNights(): LiveData<List<SleepNight>>
}