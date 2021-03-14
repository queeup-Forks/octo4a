package com.octo4a.repository

import com.octo4a.utils.log
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

data class GithubAsset(val name: String, val url: String)

data class GithubRelease(val tagName: String, val zipballUrl: String, val body: String, val assets: List<GithubAsset>)

interface GithubRepository {
    suspend fun getNewestRelease(repository: String): GithubRelease
}

class GithubRepositoryImpl(private val httpClient: HttpClient): GithubRepository {
    private val baseUrl = "https://api.github.com/"

    override suspend fun getNewestRelease(repository: String): GithubRelease {
        return httpClient.get {
            url("${baseUrl}repos/${repository}/releases/latest")
            contentType(ContentType.Application.Json)
        }
    }
}