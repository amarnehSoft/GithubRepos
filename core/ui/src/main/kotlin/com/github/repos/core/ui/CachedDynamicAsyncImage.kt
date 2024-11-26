package com.github.repos.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.github.repos.core.designsystem.component.DynamicAsyncImage

@Composable
fun CachedDynamicAsyncImage(
    url: String,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val customImageLoader = remember {
        ImageLoader.Builder(context)
            .memoryCache {
                MemoryCache.Builder(context)
//                    .maxSizePercent(0.25) // Use 25% of available memory for cache
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    .maxSizeBytes(50L * 1024 * 1024) // 50 MB
                    .build()
            }
            .build()
    }

    DynamicAsyncImage(
        modifier = modifier,
        imageLoader = customImageLoader,
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .transformations(CircleCropTransformation())
            .build(),
        contentDescription = null,
    )
}