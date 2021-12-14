package com.lazuardy.zonacoding

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.lazuardy.zonacoding.api.RetroInstance
import com.lazuardy.zonacoding.data.ArticlesResponseItem
import com.lazuardy.zonacoding.data.SearchResponse
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var articleAdapter: ArticleAdapter
    private var backToGetArticles = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        getDataFromApi()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        val searchAction = menu!!.findItem(R.id.action_search)
        val searchView = searchAction.actionView as SearchView
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.queryHint = "Tutorial NextJS"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                showLoading(true)
                RetroInstance.api.getArticlesByQuery(query).enqueue(object: Callback<SearchResponse> {
                    override fun onResponse(
                        call: Call<SearchResponse>,
                        response: Response<SearchResponse>
                    ) {
                        if (response.isSuccessful) {
                            val searchResponse = response.body()
                            if (searchResponse?.total != 0) {
                                if (searchResponse != null) {
                                    showResult(searchResponse.data as List<ArticlesResponseItem>)
                                    backToGetArticles = true
                                }
                            } else {
                                Toast.makeText(this@MainActivity, "Artikel tidak ditemukan, silahkan coba lagi", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this@MainActivity, "Artikel tidak ditemukan, silahkan coba lagi", Toast.LENGTH_SHORT).show()
                        }
                        showLoading(false)
                    }

                    override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                        println(t.message)
                        showLoading(false)
                    }
                })
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        return if (id == R.id.action_contact_us) {
            startActivity(Intent(this@MainActivity, ContactUsActivity::class.java))
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun setupRecyclerView(){
        articleAdapter = ArticleAdapter(arrayListOf(), object : ArticleAdapter.OnAdapterListener {
            override fun onClick(result: ArticlesResponseItem) {
                val articleUrl = "https://zonacoding.com/article/" + result.slug
                startActivity(
                    Intent(this@MainActivity, ArticleActivity::class.java)
                        .putExtra("article_url", articleUrl)
                )
            }
        })
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = articleAdapter
        }
    }

    private fun getDataFromApi(){
        showLoading(true)
        RetroInstance.api.getArticles()
            .enqueue(object : Callback<List<ArticlesResponseItem>> {
                override fun onFailure(call: Call<List<ArticlesResponseItem>>, t: Throwable) {
                    printLog( t.toString() )
                    showLoading(false)
                }
                override fun onResponse(
                    call: Call<List<ArticlesResponseItem>>,
                    response: Response<List<ArticlesResponseItem>>
                ) {
                    showLoading(false)
                    if (response.isSuccessful) {
                        showResult( response.body()!! )
                    }
                }
            })
    }

    private fun printLog(message: String) {
        Log.d(TAG, message)
    }

    private fun showLoading(loading: Boolean) {
        when(loading) {
            true -> progressBar.visibility = View.VISIBLE
            false -> progressBar.visibility = View.GONE
        }
    }

    private fun showResult(results: List<ArticlesResponseItem>) {
        for (result in results) printLog( "title: ${result.title}" )
        articleAdapter.setData(results)
    }

    override fun onBackPressed() {
        if(backToGetArticles){
            getDataFromApi()
            backToGetArticles = false
        } else {
            super.onBackPressed()
        }
    }
}