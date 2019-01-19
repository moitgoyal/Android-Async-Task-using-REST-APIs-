package com.example.mg156.assignment8

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuItem
import java.util.ArrayList

class MainActivity : AppCompatActivity(),RecyclerViewFragment.OnFragmentInteractionListener  {

    lateinit var recFragment: Fragment
    lateinit var items: ArrayList<MovieData>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title=""


        if (savedInstanceState == null) {
            recFragment = RecyclerViewFragment.newInstance(R.id.recViewFragmentLayout.toString())
        }

        supportFragmentManager.beginTransaction().replace(R.id.task1framelayout,
                recFragment).commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        supportFragmentManager.putFragment(outState, "recFragment", recFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.home) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onFragmentInteraction(movie : MovieData){
        supportFragmentManager.beginTransaction().replace(R.id.task1framelayout, MovieFragment.newInstance(movie)).addToBackStack(null).commit()
    }
}

