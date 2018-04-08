package com.example.brett.firechat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_friends.*

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        var ab = getSupportActionBar()
        ab?.setTitle("FireChat")
        ab?.setSubtitle(("Chat Room"))

        // to get the back button in the action bar to shwo and work
        ab?.setDisplayHomeAsUpEnabled(true)

        if(this.intent.hasExtra("userEmail")){
            mUserEmail = this.intent.getStringExtra("userEmail")
            mUserImageUrl = this.intent.getStringExtra("userImageUrl")
        }
        else{
            Log.w("debug","Activity requires a logged in user")
        }

        attachRecyclerView()

        chatService.setupService( recyclerViewChat.context,{message -> addMessageToRecyclerView(message)})

        buttonSend.setOnClickListener({view -> sendMessage()})
    }

    private var mUserEmail: String = ""
    private var mUserImageUrl: String = ""

    private val chatService = FireChatService.instance

    private fun sendMessage(){
        chatService.sendMessage(mUserEmail,mUserImageUrl,sendText.text.toString())
    }

    private fun addMessageToRecyclerView(message: ChatData?){
        if(message != null){
            val cellData = CellData(message.fromEmail,message.fromImageUrl,message.message)
            addCellToRecyclerView(cellData)
            sendText.setText("")
        }
    }

    lateinit var adapter: CellViewAdapter

    private fun attachRecyclerView(){
        val manager = LinearLayoutManager(this)
        recyclerViewChat.setHasFixedSize(true)
        recyclerViewChat.layoutManager = manager
        recyclerViewChat.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        initializeRecyclerView()
    }

    private fun initializeRecyclerView(){
        adapter = CellViewAdapter{view, position -> rowTapped(position)}
        recyclerViewChat.adapter = adapter
    }

    private fun rowTapped(position: Int){
        Log.d("debug", adapter.rows[position].headerTxt + " " + adapter.rows[position].messageText)
    }

    private fun addCellToRecyclerView(cellData: CellData){
        adapter.addCellData(cellData)
        recyclerViewChat.smoothScrollToPosition(adapter.getCellCount() - 1)
    }
}
