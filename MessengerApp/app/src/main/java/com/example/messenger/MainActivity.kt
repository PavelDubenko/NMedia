package com.example.messenger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messenger.database.AppDatabase
import com.example.messenger.databinding.ActivityMainBinding
import com.example.messenger.model.Message
import com.example.messenger.ui.MessageAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize database
        database = AppDatabase.getDatabase(this)

        // Setup RecyclerView
        setupRecyclerView()

        // Setup send button
        setupSendButton()

        // Observe messages
        observeMessages()
    }

    private fun setupRecyclerView() {
        messageAdapter = MessageAdapter()
        binding.recyclerViewMessages.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = messageAdapter
        }
    }

    private fun setupSendButton() {
        binding.buttonSend.setOnClickListener {
            val messageText = binding.editTextMessage.text.toString().trim()
            if (messageText.isNotEmpty()) {
                sendMessage(messageText)
                binding.editTextMessage.text?.clear()
            }
        }
    }

    private fun sendMessage(text: String) {
        lifecycleScope.launch {
            val message = Message(
                text = text,
                sender = "Me",
                isReceived = false
            )
            database.messageDao().insertMessage(message)
        }
    }

    private fun observeMessages() {
        lifecycleScope.launch {
            database.messageDao().getAllMessages().collectLatest { messages ->
                messageAdapter.submitList(messages)
                // Scroll to bottom when new message arrives
                if (messages.isNotEmpty()) {
                    binding.recyclerViewMessages.scrollToPosition(messages.size - 1)
                }
            }
        }
    }
}
