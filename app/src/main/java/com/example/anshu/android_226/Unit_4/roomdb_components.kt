package com.example.anshu.android_226.Unit_4

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

//STEP -1
@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val age: Int
)

//STEP -2 DAO
@Dao
interface StudentDao2 {
    @Insert
    suspend fun insert(student: Student)

    @Query("SELECT * FROM students ORDER BY id DESC")
    fun getAllStudents(): Flow<List<Student>>

    @Delete
    suspend fun delete(student: Student)
}

//STEP -3 Database
@Database(
    entities = [Student::class],
    version = 1,
    exportSchema = false
)
abstract class StudentDatabase2 : RoomDatabase() {
    abstract fun studentDao(): StudentDao2
}