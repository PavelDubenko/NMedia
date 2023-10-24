package ru.netology.nmedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractoinListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.AndroidUtil
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel by viewModels<PostViewModel>()
        val adapter = PostsAdapter(object : OnInteractoinListener{
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.repostById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }
        })

        binding.list.adapter = adapter

        viewModel.data.observe(this) { posts ->
            val newPost = adapter.currentList.size < posts.size
            adapter.submitList(posts) {
                if (newPost) {
                    binding.list.smoothScrollToPosition(0)
                }
            }
        }
        viewModel.edited.observe(this) {post ->
            if (post.id != 0L) {
                binding.content.setText(post.content)
                binding.group.visibility = View.VISIBLE
                binding.edit.text = post.content
                binding.content.requestFocus()
            } else binding.group.visibility = View.GONE

        }

        binding.save.setOnClickListener {
            val text = binding.content.text.toString()
            if (text.isBlank()) {
                Toast.makeText(this, R.string.error_empty_content, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            viewModel.changeContent(text)
            viewModel.save()

            binding.content.setText("")
            binding.content.clearFocus()
            AndroidUtil.hideKeyboard(it)

        }
        binding.cancel.setOnClickListener {
            viewModel.save()
            binding.content.setText("")
            binding.content.clearFocus()
            AndroidUtil.hideKeyboard(it)
            binding.group.visibility = View.GONE
            return@setOnClickListener
        }
    }
}

