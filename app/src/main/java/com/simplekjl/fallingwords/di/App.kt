package com.simplekjl.fallingwords.di

import android.app.Application
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.simplekjl.fallingwords.R
import com.simplekjl.fallingwords.data.model.Word
import com.squareup.moshi.Moshi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.dsl.module
import java.io.BufferedReader
import java.io.InputStreamReader


class App : Application() {
    val dataModule = createDataModule()

    private fun createDataModule() = module {
        factory { Moshi.Builder().build() }
    }

    override fun onCreate() {
        super.onCreate()
        //start koin
        readWordFromRaw()
        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@App)
            androidFileProperties()
            modules(
                listOf(
                    dataModule,
                )
            )
        }
    }

    private fun readWordFromRaw() {
        val rawFile = applicationContext.resources.openRawResource(R.raw.words_v2)
        val rd: String = BufferedReader(InputStreamReader(rawFile)).use { it.readText() }
        val gson = Gson()
        val listPersonType = object : TypeToken<List<Word>>() {}.type
        val wordsList: List<Word> = gson.fromJson(rd, listPersonType)
        Log.d("list", "readWordFromRaw: $wordsList")
    }
}
