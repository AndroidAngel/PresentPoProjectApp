package com.angelaud.presentpoprojectapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.angelaud.presentpoprojectapp.database.StudentHandler
import com.angelaud.presentpoprojectapp.model.Student
import kotlinx.android.synthetic.main.activity_student_profile.*

class StudentProfileActivity : AppCompatActivity() {

    var dbHandler: StudentHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initDB()
        initOperations()
    }
    fun initDB(){
        dbHandler = StudentHandler(this)
        val student: Student = dbHandler!!.getStudent(intent.getIntExtra("Id", 0))
        student_no_id.text = student.studentNumber
        student_name_id.text = student.studentName
        address_id.text = student.studentAddress
        profile_gender_id.text = student.studentGender
// TODO       age_id.text = student.studentAge
// TODO        gender_id.text = student.studentGender
//  TODO       birthday_id.text = student.birthday
//  TODO       student_profile_image_id.setImageResource()
        mother_name_id.text = student.motherName
        mother_contact_id.text = student.motherContact
        father_name.text = student.fatherName
        father_contact_id.text = student.fatherContact
        contact_id.text = student.motherContact

    }
    fun initOperations(){

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.profile_menu, menu)
        return true
    }override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_delete){
            val dialog = AlertDialog.Builder(this).setTitle("Info").setMessage("Click 'YES' to delete")
                    .setPositiveButton("YES") { dialog, i ->
                        val success = dbHandler?.deleteStudent(intent.getIntExtra("Id",0)) as Boolean
                        if (success)
                            finish()
                        dialog.dismiss()
                    }
                    .setNegativeButton("NO") { dialog, i ->
                        dialog.dismiss()
                    }
            dialog.show()
            return true
        }

        if (id == R.id.action_edit){
            dbHandler = StudentHandler(this)
            val student: Student = dbHandler!!.getStudent(intent.getIntExtra("Id", 0))
            val i = Intent(this, AddStudentActivity::class.java)
            i.putExtra("Mode", "E")
            i.putExtra("Id", student.id)
            startActivity(i)

        }
        return super.onOptionsItemSelected(item)


    }

    override fun onResume() {
        super.onResume()
        initDB()
    }
}
