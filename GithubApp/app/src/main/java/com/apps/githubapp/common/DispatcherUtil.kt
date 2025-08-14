package com.apps.githubapp.common

import kotlinx.coroutines.Dispatchers

object DispatcherUtil {

    private val IO = Dispatchers.IO
    private val Main = Dispatchers.Main
    private val Default = Dispatchers.Default

    fun getIoDispatcher() = IO
    fun getMainDispatcher() = Main
    fun getDefaultDispatcher() = Default
}