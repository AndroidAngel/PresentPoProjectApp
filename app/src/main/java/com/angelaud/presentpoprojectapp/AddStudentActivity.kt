package com.angelaud.presentpoprojectapp

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.angelaud.presentpoprojectapp.database.StudentHandler
import com.angelaud.presentpoprojectapp.model.Student
import kotlinx.android.synthetic.main.activity_add_student.*

class AddStudentActivity : AppCompatActivity() {

    var dbHandler: StudentHandler? = null
    var isEditAddMode = false
    var radioBtnMale: RadioButton? = null
    var radioBtnFemale: RadioButton? = null
    var radioGroupGender: RadioGroup? = null
    var gender: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initDB()
        initOperations()

    }


    private fun initDB() {
        dbHandler = StudentHandler(this)
        add_btn_delete_id.visibility = View.INVISIBLE
        if (intent != null && intent.getStringExtra("Mode") == "E") {
            isEditAddMode = true
            val student: Student = dbHandler!!.getStudent(intent.getIntExtra("Id", 0))
            add_student_no_id.setText(student.studentNumber)
            add_student_name_id.setText(student.studentName)
            add_student_address_id.setText(student.studentAddress)
            radioButton_female_id.text = student.studentGender
            radioButton_male_id.text = student.studentGender
            //TODO   add_age_spinner_id
            //TODO  add_image_profile
            //TODO radioButton gender
            add_grade_section_id.setText(student.gradeSection)
            add_mother_name_id.setText(student.motherName)
            add_mother_contact_id.setText(student.motherContact)
            add_father_name_id.setText(student.fatherName)
            add_father_contact_id.setText(student.fatherContact)
            add_tv_birthday_id.text = student.birthday
        }
    }
    private fun  initOperations(){
        add_btn_save_id.setOnClickListener {
            var success: Boolean = false
            if (!isEditAddMode) {
                val student: Student = Student()
                student.studentNumber = add_student_no_id.text.toString()
                student.studentName = add_student_name_id.text.toString()
                student.studentAddress = add_student_address_id.text.toString()
                student.studentGender = radioButton_female_id.text.toString()
                student.studentGender = radioButton_male_id.text.toString()

                //TODO   add_age_spinner_id
                //TODO  add_image_profile
                //TODO radioButton gender
                student.gradeSection = add_grade_section_id.text.toString()
                student.motherName = add_mother_name_id.text.toString()
                student.motherContact = add_mother_contact_id.text.toString()
                student.fatherName = add_father_name_id.text.toString()
                student.fatherContact = add_father_contact_id.text.toString()
                student.birthday = add_tv_birthday_id.text.toString()
                success = dbHandler?.addStudent(student) as Boolean
            }else{
                val  student: Student = Student()
                student.id = intent.getIntExtra("Id",0)
                student.studentNumber = add_student_no_id.text.toString()
                student.studentName = add_student_name_id.text.toString()
                student.studentGender = radioButton_female_id.text.toString()
                student.studentGender = radioButton_male_id.text.toString()
                //TODO   add_age_spinner_id
                //TODO  add_image_profile
                //TODO radioButton gender
                student.studentAddress = add_student_address_id.text.toString()
                student.gradeSection = add_grade_section_id.text.toString()
                student.motherName = add_mother_name_id.text.toString()
                student.motherContact = add_mother_contact_id.text.toString()
                student.fatherName = add_father_name_id.text.toString()
                student.fatherContact = add_father_contact_id.text.toString()
                student.birthday = add_tv_birthday_id.text.toString()
                success = dbHandler?.updateStudent(student) as Boolean
            }
            if (success)
                finish()
            Toast.makeText(this, "Student save", Toast.LENGTH_SHORT).show()

            }
        add_btn_delete_id.setOnClickListener{
        val dialog = AlertDialog.Builder(this).setTitle("Info").setMessage("Click 'YES' Delete the Student.")
                .setPositiveButton("YES") { dialog, i ->
                    val success = dbHandler?.deleteStudent(intent.getIntExtra("Id", 0)) as Boolean
                    if (success)
                        finish()
                    dialog.dismiss()
                }
                .setNegativeButton("NO") { dialog, i ->
                    dialog.dismiss()
                }
            dialog.show()

        }

        add_btn_subject_advisory_id.setOnClickListener{
            TODO()
        }


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        val id = item.itemId
        if (id == android.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)

    }


}