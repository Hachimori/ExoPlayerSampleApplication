package com.example.exoplayersampleapplication

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.extractor.mp4.Mp4Extractor
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.AssetDataSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DataSpec
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*
        val player = SimpleExoPlayer.Builder(this).build()
        val cacheFile = File(cacheDir.canonicalPath + "/video_cache")
        val cache = SimpleCache(
            cacheFile,
            LeastRecentlyUsedCacheEvictor(1024 * 1024 * 24),
            ExoDatabaseProvider(this@MainActivity)
        )
        val cacheDataSource = CacheDataSource.Factory().apply {
            setCache(cache)
            setUpstreamDataSourceFactory(DefaultHttpDataSourceFactory(packageName))
        }

        playerView.player = player

        val extractorsFactory: ExtractorsFactory =
            DefaultExtractorsFactory().setMp4ExtractorFlags(Mp4Extractor.FLAG_WORKAROUND_IGNORE_EDIT_LISTS)

        player.setMediaSource(
            ProgressiveMediaSource.Factory(cacheDataSource, extractorsFactory).createMediaSource(
                MediaItem.fromUri(
                    "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"
                )
            )
        )

        player.prepare()
*/

        val player = SimpleExoPlayer.Builder(this).build()
        playerView.player = player

        val dataSpec = DataSpec(Uri.parse("asset:///android_asset/ABC2.mp4"))
        val assetDataSource = AssetDataSource(this)
        try {
            assetDataSource.open(dataSpec)
        } catch (e: AssetDataSource.AssetDataSourceException) {
            e.printStackTrace()
        }

        val dataSourceFactory: DataSource.Factory = DataSource.Factory { assetDataSource }
        val extractorsFactory: ExtractorsFactory =
            DefaultExtractorsFactory().setMp4ExtractorFlags(Mp4Extractor.FLAG_WORKAROUND_IGNORE_EDIT_LISTS)
        val source: MediaSource = ExtractorMediaSource(
            assetDataSource.uri!!,
            dataSourceFactory, extractorsFactory, null, null
        )
        player.prepare(source)

        //player.playWhenReady = true
    }
}
