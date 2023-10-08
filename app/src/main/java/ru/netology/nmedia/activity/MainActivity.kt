package ru.netology.nmedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel by viewModels<PostViewModel>()

       viewModel.data.observe(this) { post ->
           with(binding) {
               author.text = post.author
               published.text = post.published
               content.text = post.content
               likesCount.text = formatLikesCount(post.likes)
               repostsCount.text = formatLikesCount(post.reposts)
               likes.setImageResource(if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_likes_24)

           }
       }
        binding.likes.setOnClickListener {
            viewModel.like()
        }

        binding.reposts.setOnClickListener {
            viewModel.repost()
        }

    }
}