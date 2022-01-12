package de.tum.`in`.tumcampusapp.component.ui.cafeteria

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.tum.`in`.tumcampusapp.component.ui.cafeteria.model.CafeteriaMenu
import io.reactivex.Flowable
import org.joda.time.DateTime

@Dao
interface CafeteriaMenuDao {

    @get:Query("SELECT DISTINCT date FROM cafeteriaMenu WHERE date >= date('now','localtime') ORDER BY date")
    val allDates: List<DateTime>

    @Query("DELETE FROM cafeteriaMenu")
    fun removeCache()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cafeteriaMenus: List<CafeteriaMenu>)

    @Query("SELECT strftime('%d-%m-%Y', date) FROM cafeteriaMenu " +
            "WHERE date > date('now','localtime') AND cafeteriaId=:cafeteriaId AND name=:dishName " +
            "ORDER BY date ASC")
    fun getNextDatesForDish(cafeteriaId: Int, dishName: String): Flowable<List<String>>

    @Query("SELECT id, cafeteriaId, date, dishType, name FROM cafeteriaMenu " +
            "WHERE cafeteriaId = :cafeteriaId AND date = :date " +
            "GROUP BY dishType ORDER BY dishType DESC")
    fun getCafeteriaMenus(cafeteriaId: Int, date: DateTime): List<CafeteriaMenu>
}
