package com.example.navigationdrawerfragment

import android.app.DownloadManager
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home_fragment.*

class MainActivity : AppCompatActivity() {

    lateinit var homefragment : HomeFragment
    var currentImageUrl : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(tool_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val drawerToggle : ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,drawer_layout,tool_bar,R.string.drawer_open,R.string.drawer_close
        ){
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                setTitle(R.string.app_name)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                setTitle(R.string.option)
            }
        }


//        tool_bar.setTitleTextColor(0xFFFFFF)

        tool_bar.setTitleTextColor(android.graphics.Color.WHITE)
        title = "Meme Share"

        homefragment = HomeFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment,homefragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()

        drawerToggle.isDrawerIndicatorEnabled = true
        drawer_layout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()


//        navigation view

        nav_view.setNavigationItemSelectedListener {
            item:MenuItem ->
            when(item.itemId){
                R.id.home -> {
                    loadfragment(HomeFragment())
                }
                R.id.trending -> {
                    loadfragment1(Fragment1())
                }
                else -> super.onOptionsItemSelected(item)
            }
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }

//        loadmeme()
    }

    fun loadmeme(){
        progressBar.visibility = View.VISIBLE
        val url = "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                currentImageUrl = response.getString("url")
                Glide.with(this).load(currentImageUrl).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
//                        if (progressBar!=null)progressBar.visibility = View.GONE
                        return false
                    }
                }).into(memeImageView)
            },
            {
                Toast.makeText(this, "Please Check Your Internet Connection", Toast.LENGTH_LONG).show()
            })

        // Add the request to the RequestQueue.
        MySingleton.getInstance(this,).addToRequestQueue(jsonObjectRequest)
    }



    fun loadfragment(frag1 : HomeFragment){
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment,frag1)
        ft.commit()
    }
    fun loadfragment1(frag2 : Fragment1){
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment,frag2)
        ft.commit()
    }
    fun shareMeme(view: View){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Checkout This Cool Meme $currentImageUrl")
        val chooser = Intent.createChooser(intent,"Share This Meme Using")
        startActivity(chooser)
    }

    fun nextMeme(view: View){
        loadmeme()
    }
}