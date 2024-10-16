package dev.sargunv.traintracker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform