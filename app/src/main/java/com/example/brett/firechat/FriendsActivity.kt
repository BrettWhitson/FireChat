package com.example.brett.firechat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_friends.*

class FriendsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        var ab = supportActionBar
        ab?.setTitle("Firechat")
        ab?.setSubtitle(("Friends List"))

        // to get the back button in the action bar to show and work
        ab?.setDisplayHomeAsUpEnabled(true)

        if(this.intent.hasExtra("userEmail")){
            mUserEmail = this.intent.getStringExtra("userEmail")
            mUserImageUrl = this.intent.getStringExtra("userImageUrl")
        }
        else{
            Log.w("debug", "Activity required a logged in user")
        }

        attachRecyclerView()

        addCellToRecyclerView(CellData(mUserEmail, mUserImageUrl, "thats me 1"))
        addCellToRecyclerView(CellData(mUserEmail, mUserImageUrl, "thats me 2"))
        addCellToRecyclerView(CellData(mUserEmail, mUserImageUrl, "thats me 3"))
        addCellToRecyclerView(CellData(mUserEmail, mUserImageUrl, "thats me 4"))
        addCellToRecyclerView(CellData(mUserEmail, mUserImageUrl, "thats me 5"))

    }

    private var mUserEmail: String = ""
    private var mUserImageUrl: String = ""

    // Recycler view stuff

    lateinit var adapter: CellViewAdapter

    private fun attachRecyclerView(){
        val manager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = manager
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        initializeRecyclerView()
    }

    private fun initializeRecyclerView(){
        adapter = CellViewAdapter{view, position -> rowTapped(position)}
        recyclerView.adapter = adapter
    }

    private fun rowTapped(position: Int){
        Log.d("debug", adapter.rows[position].headerTxt + " " + adapter.rows[position].messageText)
    }

    private fun addCellToRecyclerView(cellData: CellData){
        adapter.addCellData(cellData)
        recyclerView.smoothScrollToPosition(adapter.getCellCount() - 1)
    }
}
