package com.angelaud.presentpoprojectapp

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.angelaud.presentpoprojectapp.adapter.StudentAdapter
import com.angelaud.presentpoprojectapp.database.StudentHandler
import com.angelaud.presentpoprojectapp.model.Student
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var studentAdapter: StudentAdapter? = null
    var fab: FloatingActionButton? = null
    var recyclerView: RecyclerView? = null
    var dbHandler: StudentHandler? = null
    var listStudent: List<Student> = ArrayList<Student>()
    var linearLayoutManager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initViews()
        initOperations()
        initDB()


//
//        fab.setOnClickListener { view ->
//            val i = Intent(this, AddStudentActivity::class.java)
//            startActivity(i)
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    fun initDB(){
        dbHandler = StudentHandler(this)
        listStudent = (dbHandler as StudentHandler).students()
        studentAdapter = StudentAdapter(studentList = listStudent, context = applicationContext)
        (recyclerView as RecyclerView).adapter = studentAdapter

    }

    fun initViews(){
        var toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.setTitle(getString(R.string.app_name))
        toolbar.setNavigationIcon(R.drawable.ic_arrow)
        setSupportActionBar(toolbar)
        fab = findViewById(R.id.fab) as FloatingActionButton
        recyclerView = findViewById(R.id.recycler_view) as RecyclerView
        studentAdapter = StudentAdapter(studentList = listStudent, context = applicationContext)
        linearLayoutManager = LinearLayoutManager(applicationContext)
        (recyclerView as RecyclerView).layoutManager = linearLayoutManager
    }
    fun initOperations(){
        fab?.setOnClickListener { view ->
            val i = Intent(applicationContext, AddStudentActivity::class.java)
            i.putExtra("Mode", "A")
            startActivity(i)
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_delete_all) {
            val dialog = AlertDialog.Builder(this).setTitle("Warning!!").setMessage("Click 'YES' Delete All Students")
                    .setPositiveButton("YES") { dialog, i ->
                        dbHandler!!.deleteAllStudent()
                        initDB()
                        dialog.dismiss()
                    }
                    .setNegativeButton("NO") { dialog, i ->
                        dialog.dismiss()
                    }
            dialog.show()
            return true
        }
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onResume() {
        super.onResume()
        initDB()
    }
}
