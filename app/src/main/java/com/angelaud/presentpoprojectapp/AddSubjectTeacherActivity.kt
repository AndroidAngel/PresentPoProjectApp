package com.angelaud.presentpoprojectapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class AddSubjectTeacherActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_subject_teacher)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
}
