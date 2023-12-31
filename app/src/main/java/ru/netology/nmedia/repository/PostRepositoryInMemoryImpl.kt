package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl: PostRepository {
    private var nextId = 1L
    private var posts = listOf(
        Post(
        id = nextId++,
        author = "Нетология. Университет интернет-профессий будущего",
        published = "21 мая в 18:36",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        likedByMe = true,
        likes = 10,
        reposts = 11
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего" ,
            published = "18 сентября в 10:12",
            content = "Знаний хватит на всех: на следующей неделе разбираемся с разработкой мобильных приложений, учимся рассказывать истории и составлять PR-стратегию прямо на бесплатных занятиях",
            likedByMe = false,
            likes = 0,
            reposts = 0
        )
    )
    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(likedByMe = !it.likedByMe, likes = if(it.likedByMe) it.likes - 1 else it.likes + 1)
        }
        data.value = posts
    }

    override fun repostById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(reposts = it.reposts + 1)
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun save(post: Post) {
        posts = if(post.id == 0L) {
            listOf(post.copy(
                id = nextId++,
                author = "Me",
                published = "Now"
            )) + posts
        } else {
            posts.map { if (it.id != post.id) it else it.copy(content = post.content) }
        }

        data.value = posts
    }
}