package com.angelaud.presentpoprojectapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.angelaud.presentpoprojectapp.model.Student

class StudentHandler(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private val DB_VERSION = 1
        private val DB_NAME = "StudentDB"
        private val TABLE_NAME = "Student"
        private val ID = "Id"
        private val STUDENT_IMAGE = "studentImage"
        private val STUDENT_NAME = "studentName"
        private val STUDENT_NUMBER = "studentNumber"
        private val STUDENT_ADDRESS = "studentAddress"
        private val STUDENT_AGE = "studentAge"
        private val STUDENT_GENDER = "studentGender"
        private val STUDENT_GRADE_SECTION = "gradeSection"
        private val MOTHER_NAME = "motherName"
        private val FATHER_NAME = "fatherName"
        private val MOTHER_CONTACT = "motherContact"
        private val FATHER_CONTACT = "fatherContact"
        private val STUDENT_BIRTHDAY = "birthday"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE =
                "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY, $STUDENT_NAME TEXT, $STUDENT_IMAGE BLOB, $STUDENT_ADDRESS TEXT, $STUDENT_NUMBER TEXT, $STUDENT_AGE TEXT, $STUDENT_GENDER TEXT, $STUDENT_GRADE_SECTION TEXT, $MOTHER_NAME TEXT, $FATHER_NAME TEXT, $MOTHER_CONTACT TEXT, $FATHER_CONTACT TEXT, $STUDENT_BIRTHDAY TEXT);"
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(DROP_TABLE)
        onCreate(db)

    }

    fun addStudent(student: Student): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(STUDENT_NAME, student.studentName)
        values.put(STUDENT_IMAGE, student.studentImage)
        values.put(STUDENT_ADDRESS, student.studentAddress)
        values.put(STUDENT_NUMBER, student.studentNumber)
        values.put(STUDENT_AGE, student.studentAge)
        values.put(STUDENT_GENDER, student.studentGender)
        values.put(STUDENT_GRADE_SECTION, student.gradeSection)
        values.put(MOTHER_NAME, student.motherName)
        values.put(FATHER_NAME, student.fatherName)
        values.put(MOTHER_CONTACT, student.motherContact)
        values.put(FATHER_CONTACT, student.fatherContact)
        values.put(STUDENT_BIRTHDAY, student.birthday)
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        Log.v("InsertedId", "$_success")
        return (Integer.parseInt("$_success") != -1)
    }

    fun getStudent(_id: Int): Student {
        val student = Student()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME WHERE $ID = $_id"
        val cursor = db.rawQuery(selectQuery, null)

        cursor?.moveToFirst()
        student.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
        student.studentName = cursor.getString(cursor.getColumnIndex(STUDENT_NAME))
        student.studentImage = cursor.getBlob(cursor.getColumnIndex(STUDENT_IMAGE)).toString()
        student.studentAddress = cursor.getString(cursor.getColumnIndex(STUDENT_ADDRESS))
        student.studentNumber = cursor.getString(cursor.getColumnIndex(STUDENT_NUMBER))
        student.studentAge = cursor.getString(cursor.getColumnIndex(STUDENT_AGE))
        student.studentGender = cursor.getString(cursor.getColumnIndex(STUDENT_GENDER))
        student.gradeSection = cursor.getString(cursor.getColumnIndex(STUDENT_GRADE_SECTION))
        student.motherName = cursor.getString(cursor.getColumnIndex(MOTHER_NAME))
        student.fatherName = cursor.getString(cursor.getColumnIndex(FATHER_NAME))
        student.motherContact = cursor.getString(cursor.getColumnIndex(MOTHER_CONTACT))
        student.fatherContact = cursor.getString(cursor.getColumnIndex(FATHER_CONTACT))
        student.birthday = cursor.getString(cursor.getColumnIndex(STUDENT_BIRTHDAY))

        cursor.close()
        return student
    }

    fun students(): List<Student> {
        val studentList = ArrayList<Student>()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val student = Student()
                    student.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    student.studentName = cursor.getString(cursor.getColumnIndex(STUDENT_NAME))
                    student.studentImage = cursor.getBlob(cursor.getColumnIndex(STUDENT_IMAGE)).toString()
                    student.studentAddress = cursor.getString(cursor.getColumnIndex(STUDENT_ADDRESS))
                    student.studentNumber = cursor.getString(cursor.getColumnIndex(STUDENT_NUMBER))
                    student.studentAge = cursor.getString(cursor.getColumnIndex(STUDENT_AGE))
                    student.studentGender = cursor.getString(cursor.getColumnIndex(STUDENT_GENDER))
                    student.gradeSection = cursor.getString(cursor.getColumnIndex(STUDENT_GRADE_SECTION))
                    student.motherName = cursor.getString(cursor.getColumnIndex(MOTHER_NAME))
                    student.fatherName = cursor.getString(cursor.getColumnIndex(FATHER_NAME))
                    student.motherContact = cursor.getString(cursor.getColumnIndex(MOTHER_CONTACT))
                    student.fatherContact = cursor.getString(cursor.getColumnIndex(FATHER_CONTACT))
                    student.birthday = cursor.getString(cursor.getColumnIndex(STUDENT_BIRTHDAY))
                    studentList.add(student)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return studentList
    }

    fun updateStudent(student: Student): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(STUDENT_NAME, student.studentName)
        values.put(STUDENT_IMAGE, student.studentImage)
        values.put(STUDENT_ADDRESS, student.studentAddress)
        values.put(STUDENT_NUMBER, student.studentNumber)
        values.put(STUDENT_AGE, student.studentAge)
        values.put(STUDENT_GENDER, student.studentGender)
        values.put(STUDENT_GRADE_SECTION, student.gradeSection)
        values.put(MOTHER_NAME, student.motherName)
        values.put(FATHER_NAME, student.fatherName)
        values.put(MOTHER_CONTACT, student.motherContact)
        values.put(FATHER_CONTACT, student.fatherContact)
        values.put(STUDENT_BIRTHDAY, student.birthday)
        val _success = db.update(TABLE_NAME, values, ID + "=?", arrayOf(student.id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun deleteStudent(_id: Int): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, ID + "=?", arrayOf(_id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1

    }

    fun deleteAllStudent(): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, null, null).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }
}