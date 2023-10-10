package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.formatLikesCount
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

typealias LikeListener = (Post) -> Unit

class PostsAdapter(private val likeListener: LikeListener, private val shareListener: LikeListener): ListAdapter<Post, PostViewHolder>(PostDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(view, likeListener, shareListener )
    }



    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(private val binding: CardPostBinding, private val likeListener: LikeListener, private val shareListener: LikeListener): RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likesCount.text = formatLikesCount(post.likes)
            repostsCount.text = formatLikesCount(post.reposts)
            likes.setImageResource(if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_likes_24)
            likes.setOnClickListener {
              likeListener(post)
            }
            reposts.setOnClickListener {
              shareListener(post)
            }
        }
    }
}

object PostDiffCallback: DiffUtil.ItemCallback<Post>(){
    override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem

}