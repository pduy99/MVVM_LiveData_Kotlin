package com.example.trendinggit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trendinggit.adapters.RepoListAdapter
import com.example.trendinggit.databinding.ActivityMainBinding
import com.example.trendinggit.models.Item
import com.example.trendinggit.viewmodels.RepoListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var repoListAdapter : RepoListAdapter
    private lateinit var repoListViewModel : RepoListViewModel
    private lateinit var mainBinding : ActivityMainBinding
    private var period : String = "weekly"
    private var language : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(main_toolbar)

        repoListViewModel = ViewModelProviders.of(this).get(RepoListViewModel::class.java);
        repoListViewModel.fetchRepoList(period,language)

        repoListViewModel.repoList.observe(this, Observer(function = fun(repoList : List<Item>?){
            repoList?.let {
                initRecyclerView(repoList)
                repoListAdapter.notifyDataSetChanged()
            }
        }))

        repoListViewModel.isLoadingData.observe(this, Observer{value ->
            value?.let { show ->
                if(show) {
                    progressBar_isLoadingRepoList.visibility = View.VISIBLE
                }else{
                    progressBar_isLoadingRepoList.visibility = View.GONE
                }
            }
        })

        repoListViewModel.isEmpty.observe(this, Observer { value ->
            value?.let { show ->
                if(show){
                    container_notify_repolist_empty.visibility = View.VISIBLE
                    recyclerview_listTrending.visibility = View.GONE
                }else{
                    container_notify_repolist_empty.visibility = View.GONE
                    recyclerview_listTrending.visibility = View.VISIBLE
                }
            }
        })

        //Handle SwipeRefreshLayout
        swipe_refresh_listrepo.setOnRefreshListener {
            repoListViewModel.fetchRepoList(period,language)
            swipe_refresh_listrepo.isRefreshing = false;
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_toolbar,menu)

        val searchItem : MenuItem = menu!!.findItem(R.id.search_language)
        val searchView : SearchView = searchItem.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.queryHint = "Search language..."
        searchView.setOnCloseListener { true }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    language = query
                    repoListViewModel.fetchRepoList(period,language)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        searchView.setOnCloseListener(object : SearchView.OnCloseListener{
            override fun onClose(): Boolean {
                if(language.isNotEmpty()) {
                    language = ""
                    repoListViewModel.fetchRepoList(period, language)
                }
                return false
            }
        })




        return super.onCreateOptionsMenu(menu)
    }

    // Allow user choose trending period
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id : Int = item.itemId;
        when(id){
            R.id.get_daily -> {
                repoListViewModel.fetchRepoList("daily",language)
                period = "daily"
            }
            R.id.get_weekly -> {
                repoListViewModel.fetchRepoList("weekly",language)
                period = "weekly"
            }
            R.id.get_monthly -> {
                repoListViewModel.fetchRepoList("monthly",language)
                period = "monthly"
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun initRecyclerView(repoList : List<Item>?){
        repoListAdapter = RepoListAdapter(this,repoList!!)
        Log.d("LIST_SIZE", repoListViewModel.repoList.value!!.size.toString())
        mainBinding.recyclerviewListTrending.layoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.VERTICAL,false)
        mainBinding.recyclerviewListTrending.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        mainBinding.recyclerviewListTrending.adapter = repoListAdapter;
    }

    //Press back two times to exit application
    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }


}
