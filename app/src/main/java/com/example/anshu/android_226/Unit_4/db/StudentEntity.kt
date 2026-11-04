package com.example.anshu.android_226.Unit_4.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class StudentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val rollNumber: String,
    val courseCode: String
)