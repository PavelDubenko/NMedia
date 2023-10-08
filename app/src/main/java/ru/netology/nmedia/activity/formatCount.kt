package ru.netology.nmedia.activity

fun formatLikesCount(count: Int): String {
    if (count >= 1000_000) {
        val millions = count / 1000_000
        val thousands = (count % 1000_000) / 100_000
        return if (thousands == 0) {
            "$millions M"
        } else {
            "$millions.${thousands}M"
        }
    } else if (count in 1000 .. 10_000) {
        val thousands = count / 1000
        val hundreds = (count % 1000) / 100
        return if (hundreds == 0) {
            "$thousands K"
        } else {
            "$thousands.$hundreds K"
        }
    } else if (count in 10_000 .. 999_000){
        val thousands = count / 1000
        return "$thousands K"
    }
    return count.toString()
}

