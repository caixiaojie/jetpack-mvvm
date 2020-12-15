/*
 * Copyright 2020. hi-dhl (Jack Deng)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cxj.jetpackmvvm.ui.binding

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import androidx.databinding.BindingAdapter
import coil.Coil
import coil.load
import coil.request.ImageRequest
import coil.transform.BlurTransformation
import coil.transform.CircleCropTransformation
import coil.transform.GrayscaleTransformation
import coil.transform.RoundedCornersTransformation
import com.cxj.jetpackmvvm.App
import com.cxj.jetpackmvvm.R


/**
 * <pre>
 *     author: dhl
 *     date  : 2020/7/11
 *     desc  :
 * </pre>
 */
//普通用法
@BindingAdapter("bindingImage")
fun bindingImage(imageView: ImageView, @DrawableRes drawableResId: Int) {
    imageView.load(drawableResId) {
        crossfade(true)
        placeholder(R.color.default_color)
    }
}
@BindingAdapter("bindingImage")
fun bindingImage(imageView: ImageView, url: String) {
    imageView.load(url) {
        crossfade(true)
        placeholder(R.color.default_color)
    }
}
//固定大小
@BindingAdapter("bindSpecificSizeImage","width","height")
fun bindingSpecificSizeImage(imageView: ImageView, url: String, width: Int, height: Int) {
    imageView.load(url) {
        crossfade(true)
        placeholder(R.mipmap.ic_launcher_round)
        size(width,height)
    }
}
//圆形变化
@BindingAdapter("bindCircleImage")
fun bindingCircleImage(imageView: ImageView, url: String) {
    imageView.load(url) {
        crossfade(true)
        placeholder(R.mipmap.ic_launcher_round)
        transformations(CircleCropTransformation())
    }
}
//固定圆角变化
@BindingAdapter("bindRoundedCornerImage","radius")
fun bindingRoundedCornerImage(imageView: ImageView, url: String, radius: Float) {
    imageView.load(url) {
        crossfade(true)
        placeholder(R.mipmap.ic_launcher_round)
        transformations(RoundedCornersTransformation(radius))
    }
}
//自由 圆角变化
@BindingAdapter("bindRoundedCornerImage","topLeft","topRight","bottomLeft","bottomRight",
    requireAll = false)
fun bindingTargetRoundedCornerImage(imageView: ImageView, url: String,
                                    topLeft: Float = 0f,
                                    topRight: Float = 0f,
                                    bottomLeft: Float = 0f,
                                    bottomRight: Float = 0f) {
    imageView.load(url) {
        crossfade(true)
        placeholder(R.mipmap.ic_launcher_round)
        transformations(RoundedCornersTransformation(topLeft,topRight,bottomLeft,bottomRight))
    }
}
//模糊变化
@BindingAdapter("bindBlurImage","radius","sampling",requireAll = false)
fun bindingBlurImage(context: Context, imageView: ImageView,
                     url: String, radius: Float = 10f, sampling: Float = 1f) {
    imageView.load(url) {
        crossfade(true)
        placeholder(R.mipmap.ic_launcher_round)
        transformations(BlurTransformation(context,radius, sampling))
    }
}
//灰度变化
@BindingAdapter("bindGrayscaleImage")
fun bindingGrayscaleImage(imageView: ImageView, url: String) {
    imageView.load(url) {
        crossfade(true)
        placeholder(R.mipmap.ic_launcher_round)
        transformations(GrayscaleTransformation())
    }
}
