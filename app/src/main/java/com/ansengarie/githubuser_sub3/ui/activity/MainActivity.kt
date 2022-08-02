package com.ansengarie.githubuser_sub3.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.ansengarie.githubuser_sub3.R
import com.ansengarie.githubuser_sub3.data.viewmodel.MainViewModel
import com.ansengarie.githubuser_sub3.databinding.ActivityMainBinding
import com.ansengarie.githubuser_sub3.ui.adapter.ListUserAdapter
import com.ansengarie.githubuser_sub3.utils.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var textQueryByUser: String
    private lateinit var factory: ViewModelFactory
    private val mainViewModel: MainViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        factory = ViewModelFactory.getInstance(this)

        mainBinding.svUser.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    textQueryByUser = query.toString()
                    clearFocus()
                    val getData = mainViewModel.getUser(textQueryByUser)
                    if (textQueryByUser.isEmpty() || getData.equals(null)) {
                        mainBinding.rvUsers.adapter = ListUserAdapter(emptyList())
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    textQueryByUser = newText.toString()
                    if (textQueryByUser.isEmpty()) {
                        mainBinding.rvUsers.adapter = ListUserAdapter(emptyList())
                    } else {
                        mainBinding.rvUsers.adapter = ListUserAdapter(emptyList())
                    }
                    return true
                }
            })
        }

        mainViewModel.toastText.observe(this) {
            it.getContentIfNotHandled()?.let { toastText ->
                Toast.makeText(
                    this, toastText, Toast.LENGTH_SHORT
                ).show()
            }
        }

        showRecyclerList()
        mainViewModel.listGithubUser.observe(this) { listGithubUser ->
            mainBinding.rvUsers.adapter = ListUserAdapter(listGithubUser)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.getThemeSetting().observe(this) { isNightMode ->
            if (isNightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun showRecyclerList() {
        mainBinding.rvUsers.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
        }
    }

    override fun onResume() {
        super.onResume()
        mainBinding.rvUsers.adapter = ListUserAdapter(emptyList())
    }

    private fun showLoading(isLoading: Boolean) {
        mainBinding.progressBarMain.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun themeDialog() {
        val modes = arrayOf("Night Mode", "Light Mode")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Theme")
        builder.setItems(modes) { _, which ->
            mainViewModel.saveThemeSetting(which == 0)
            Toast.makeText(this, "Apply " + modes[which], Toast.LENGTH_SHORT).show()
        }
        builder.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_fav -> {
                val intent = Intent(this, FavActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.btn_change_theme -> {
                themeDialog()
                true
            }
            else -> true
        }
    }
}