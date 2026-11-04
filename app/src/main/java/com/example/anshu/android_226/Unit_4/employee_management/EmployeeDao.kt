package com.example.anshu.android_226.Unit_4.employee_management

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM employees ORDER BY name ASC")
    fun getAllEmployees(): Flow<List<EmployeeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employee: EmployeeEntity): Long

    @Delete
    suspend fun deleteEmployee(employee: EmployeeEntity): Int

    @Query("DELETE FROM employees")
    suspend fun deleteAllEmployees(): Int
}
