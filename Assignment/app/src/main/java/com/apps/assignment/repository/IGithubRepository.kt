package com.apps.assignment.repository

interface IGithubRepository {

    suspend fun searchPrefix(prefix: String)
}