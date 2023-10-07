package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.formatLikesCount

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
          id = 1,
          author = "Нетология. Университет интернет-профессий будущего",
          published = "21 мая в 18:36",
          content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
          likedByMe = true,
          likes = 10,
          reposts = 11
        )

        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likesCount.text = formatLikesCount(post.likes)
            repostsCount.text = formatLikesCount(post.reposts)

            if (post.likedByMe) {
                likes.setImageResource(R.drawable.ic_liked_24)
            }

            likes.setOnClickListener {
                post.likedByMe = !post.likedByMe
                post.likes += if (post.likedByMe) 1 else -1
                likes.setImageResource(if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_likes_24)
                likesCount.text = formatLikesCount(post.likes)
            }

            reposts.setOnClickListener {
                post.reposts += 1
                repostsCount.text = formatLikesCount(post.reposts)
            }
        }
    }
}