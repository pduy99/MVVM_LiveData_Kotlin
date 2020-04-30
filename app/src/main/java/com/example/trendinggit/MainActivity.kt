package com.example.trendinggit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trendinggit.adapters.RepoListAdapter
import com.example.trendinggit.databinding.ActivityMainBinding
import com.example.trendinggit.models.GitResponse
import com.example.trendinggit.models.Item
import com.example.trendinggit.viewmodels.RepoListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var repoListAdapter : RepoListAdapter
    private lateinit var repoListViewModel : RepoListViewModel
    private lateinit var mainBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        repoListViewModel = ViewModelProviders.of(this).get(RepoListViewModel::class.java);
        repoListViewModel.fetchRepoList()

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
            repoListViewModel.fetchRepoList()
            swipe_refresh_listrepo.isRefreshing = false;
        }
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
