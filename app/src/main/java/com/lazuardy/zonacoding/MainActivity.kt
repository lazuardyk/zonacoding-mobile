package com.lazuardy.zonacoding

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lazuardy.zonacoding.data.ArticlesResponseItem
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.lazuardy.zonacoding.api.RetroInstance
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        getDataFromApi()
    }

    private fun setupRecyclerView(){
        articleAdapter = ArticleAdapter(arrayListOf(), object : ArticleAdapter.OnAdapterListener {
            override fun onClick(result: ArticlesResponseItem) {
//                startActivity(
//                    Intent(this@MainActivity, DetailActivity::class.java)
//                        .putExtra("intent_title", result.title)
//                        .putExtra("intent_image", result.image)
//                )
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
        for (result in results) if (result != null) {
            printLog( "title: ${result.title}" )
        }
        articleAdapter.setData(results)
    }
}