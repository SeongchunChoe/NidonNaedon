package com.codestorage.nidonnaedon.common

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.number.NumberRangeFormatter
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingMethod
import com.codestorage.nidonnaedon.R
import java.text.NumberFormat
import java.util.*

object BindAdapters {
    @BindingAdapter(value = ["near_background_near_idx", "near_background_my_idx"], requireAll = true)
    @JvmStatic
    fun bindingNearBackground(iv: ImageView, nearIdx: Int, myIdx: Int) {
        if(nearIdx != -1 && nearIdx == myIdx){
            iv.visibility = View.VISIBLE
        }else{
            iv.visibility = View.INVISIBLE
        }
    }

    @BindingAdapter(value = ["point_background_hole", "point_background_point"], requireAll = true)
    @JvmStatic
    fun bindingPointBackground(fl: FrameLayout, hole: String, point: String) {
        if(hole.isNullOrEmpty() || point.isNullOrEmpty() || point == "-"){
            fl.setBackgroundColor(fl.context.getColor(R.color.white))
        }else{
            val holeInt = hole.toInt()
            val pointInt = point.toInt()
            if(pointInt == holeInt){
                fl.setBackgroundResource(R.drawable.bg_double_par)
            }else if(pointInt == 1){
                fl.setBackgroundResource(R.drawable.bg_bogey)
            }else if(pointInt > 1){
                fl.setBackgroundResource(R.drawable.bg_double_bogey)
            }else if(pointInt == -1){
                fl.setBackgroundResource(R.drawable.bg_buddy)
            }else if(pointInt < -1){
                fl.setBackgroundResource(R.drawable.bg_eagle)
            }else {
                fl.setBackgroundColor(fl.context.getColor(R.color.white))
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @BindingAdapter("convert_money_type")
    @JvmStatic
    fun setVisible(v: TextView, winPoint: Int) {
        v.text = "${NumberFormat.getNumberInstance(Locale.US).format(winPoint)} 원"
    }

    /*
    @BindingAdapter("visible_not_null")
    @JvmStatic
    fun setVisible(v: View, text: String?) {
        if (text != null) {
            v.visibility = View.VISIBLE
        } else {
            v.visibility = View.GONE
        }
    }

    @BindingAdapter("idx_zero_gone")
    @JvmStatic
    fun setIdxZeroGone(v: View, idx: Long) {
        if (idx == 0L) {
            v.visibility = View.GONE
        } else {
            v.visibility = View.VISIBLE
        }
    }

    @BindingAdapter("idx_zero_visible")
    @JvmStatic
    fun setIdxZeroVisible(v: View, idx: Long) {
        if (idx == 0L) {
            v.visibility = View.VISIBLE
        } else {
            v.visibility = View.GONE
        }
    }

    @BindingAdapter("visible_size")
    @JvmStatic
    fun setVisible(v: View, size: Long) {
        if (size > 0) {
            v.visibility = View.VISIBLE
        } else {
            v.visibility = View.GONE
        }
    }

    @BindingAdapter("visible_size")
    @JvmStatic
    fun setVisible(v: View, list: Collection<Any>?) {
        v.visibility = list?.let {
            if (it.isNotEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }?:View.GONE
    }

    @BindingAdapter("uni:visible_if_empty")
    @JvmStatic
    fun bindingVisibleIfNull(v: View, text: String?) {
        if (text.isNullOrEmpty()) {
            v.visibility = View.VISIBLE
        } else {
            v.visibility = View.GONE
        }
    }

    @BindingAdapter("visible_if_true")
    @JvmStatic
    fun setVisibleIfTrue(v: View, isVisible: Boolean?) {
        if (isVisible == true) {
            v.visibility = View.VISIBLE
        } else {
            v.visibility = View.GONE
        }
    }

    @BindingAdapter("visible_if_false")
    @JvmStatic
    fun setVisibleIfFalse(v: View, isVisible: Boolean?) {
        if (isVisible == false) {
            v.visibility = View.VISIBLE
        } else {
            v.visibility = View.GONE
        }
    }

    @BindingAdapter("uni:visible_not_within_day")
    @JvmStatic
    fun setVisibleNotWithinDay(v: View, endDate: Long) {
        if (endDate.isPastDay()) {
            v.visibility = View.VISIBLE
        } else {
            v.visibility = View.GONE
        }
    }

    @BindingAdapter("uni:imageView_src")
    @JvmStatic
    fun bindingImageViewSrc(imageView: ImageView, @DrawableRes resId: Int) {
        imageView.setImageDrawable(
            AppCompatResources.getDrawable(
                imageView.context,
                resId
            )
        )
    }

    @BindingAdapter("uni:view_background")
    @JvmStatic
    fun bindingViewBg(v: View, @DrawableRes resId: Int) {
        v.setBackgroundResource(resId)
    }

    @BindingAdapter("uni:textView_textColor")
    @JvmStatic
    fun bindingTextColor(tv: TextView, @ColorRes resId: Int) {
        tv.setTextColor(ContextCompat.getColor(tv.context, resId))
    }

    @BindingAdapter("uni:textView_html")
    @JvmStatic
    fun bindingTextHtml(tv: TextView, str: String?) {
        str?.let {
            tv.text = HtmlCompat.fromHtml(it.replace("\n", "<BR>"), HtmlCompat.FROM_HTML_MODE_LEGACY)
            tv.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    @BindingAdapter("uni:imageView_glide_circle")
    @JvmStatic
    fun bindingImageViewGlideCircle(iv: ImageView, src: Any?) {
        Glide.with(iv)
            .load(src)
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(RequestOptions().circleCrop())
            .placeholder(R.drawable.ic_user_my_default)
            .into(iv)
    }

    @BindingAdapter(value = ["uni:imageView_glide_circle_url", "uni:imageView_glide_placeholder", "uni:imageView_glide_error"], requireAll = false)
    @JvmStatic
    fun bindingImageViewGlideCircleUrl(iv: ImageView, urlStr: String?, imageView_glide_placeholder: Drawable? = null, imageView_glide_error: Drawable? = null) {
        Glide.with(iv)
            .load(getGlideUrl(urlStr))
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(RequestOptions().circleCrop())
            .fallback(imageView_glide_placeholder?:iv.context.getDrawable(R.drawable.ic_user_my_default))
            .placeholder(imageView_glide_placeholder?:iv.context.getDrawable(R.drawable.ic_user_my_default))
            .error(imageView_glide_error?:iv.context.getDrawable(R.drawable.ic_user_my_default))
            .into(iv)
    }

    @BindingAdapter(value = ["uni:imageView_glide_url","uni:imageView_glide_placeholder","uni:imageView_glide_error","uni:imageView_glide_round", "uni:imageView_glide_blur"], requireAll = false)
    @JvmStatic
    fun bindingImageViewGlideUrl(iv: ImageView, imageView_glide_url: String?, imageView_glide_placeholder: Drawable? = null, imageView_glide_error: Drawable? = null, imageView_glide_round:Int? = null, imageView_glide_blur:Boolean? = null) {
        Glide.with(iv)
            .load(getGlideUrl(imageView_glide_url))
            .override(Target.SIZE_ORIGINAL)
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply {
                imageView_glide_round?.let {
                    apply(RequestOptions.bitmapTransform(RoundedCorners(it)))
                }
                imageView_glide_blur?.let {
                    if(imageView_glide_blur) {
                        apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
                    }
                }
                imageView_glide_placeholder?.let {
                    placeholder(it)
                }
                error(imageView_glide_error?:iv.context.getDrawable(R.drawable.img_default_detail_image))
                fallback(imageView_glide_placeholder?:iv.context.getDrawable(R.drawable.img_default_detail_image))
            }
//            .apply(RequestOptions().fi)
            .into(iv)
    }

    @BindingAdapter(value = ["uni:backgound_glide_url","uni:backgound_glide_placeholder","uni:backgound_glide_error","uni:backgound_glide_round", "uni:backgound_glide_blur"], requireAll = false)
    @JvmStatic
    fun bindingBackgoundGlideUrl(iv: View, imageView_glide_url: String?, imageView_glide_placeholder: Drawable? = null, imageView_glide_error: Drawable? = null, imageView_glide_round:Int? = null, imageView_glide_blur:Boolean? = null) {
        Glide.with(iv)
            .load(getGlideUrl(imageView_glide_url))
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .apply {
                imageView_glide_round?.let {
                    apply(RequestOptions.bitmapTransform(RoundedCorners(it)))
                }
                imageView_glide_blur?.let {
                    if(imageView_glide_blur) {

                        apply(RequestOptions.bitmapTransform(MultiTransformation<Bitmap>(
                            BlurTransformation(25, 3),
                            ColorFilterTransformation(Color.parseColor("#331f364d")))))
                    }
                }

                placeholder(imageView_glide_placeholder?:iv.context.getDrawable(R.drawable.img_default_detail_image))
                error(imageView_glide_error?:iv.context.getDrawable(R.drawable.img_default_detail_image))
                fallback(imageView_glide_placeholder?:iv.context.getDrawable(R.drawable.img_default_detail_image))
            }

            .into(object : CustomTarget<Drawable?>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable?>?
                ) {
                    iv.setBackground(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }

    @BindingAdapter("uni:imageView_glide_url_or_local", "uni:is_from_server")
    @JvmStatic
    fun bindingImageViewGlideUrlOrLocal(iv: AppCompatImageView, urlStr: String?, originalUrl: String?) {
        urlStr?.let {
            Glide.with(iv)
                .load(if(originalUrl.isNullOrEmpty()) File(it) else getGlideUrl(it))
                .transition(DrawableTransitionOptions.withCrossFade())
                .fallback(R.drawable.img_default_detail_image)
                .placeholder(R.drawable.img_default_detail_image)
                .error(R.drawable.img_default_detail_image)
                .into(iv)
        }
    }

    @BindingAdapter("uni:out_side_url")
    @JvmStatic
    fun bindingImageViewOutSide(iv: ImageView, urlStr: String) {
        Glide.with(iv)
            .load(urlStr)
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.img_default_detail_image)
            .into(iv)
    }

    @BindingAdapter("uni:imageView_glide")
    @JvmStatic
    fun bindingImageViewGlide(iv: ImageView, src: Any?) {
        Glide.with(iv)
            .load(src)
            .transition(DrawableTransitionOptions.withCrossFade())
            .fallback(R.drawable.img_default_detail_image)
            .placeholder(R.drawable.img_default_detail_image)
            .error(R.drawable.img_default_detail_image)
            .into(iv)
    }

    fun getGlideUrl(urlString: String?): GlideUrl{
        return GlideUrl(
        Web.SERVER + urlString,
            LazyHeaders.Builder()
                .addHeader("T-DNA", App.getToken())
                .build()
        )
    }

    @BindingAdapter("uni:textView_to_date_mills", "uni:textView_to_date_format")
    @JvmStatic
    fun bindingTextViewToDate(tv: TextView, mills: Long, format: String) {
        if(mills != 0L){
            tv.text = mills.toDateString(format)
        }
    }

    @BindingAdapter(value = ["uni:textView_date_mills", "uni:textView_date_format"], requireAll = false)
    @JvmStatic
    fun bindingTextViewDate(tv: TextView, mills: Long, format: String) {
        if(mills != 0L){
            tv.text = mills.toDateString(format = format)
        }
    }

    @BindingAdapter(value = ["uni:textView_date_with_default_mills", "uni:textView_date_with_default_format", "uni:textView_date_with_default_default"], requireAll = false)
    @JvmStatic
    fun bindingTextViewDateWithDefault(tv: TextView, mills: Long, format: String, defaultStr: String?) {
        if(mills != 0L){
            tv.text = mills.toDateString(format = format)
        }else{
            tv.text = defaultStr ?: "-"
        }
    }

    @BindingAdapter("uni:textView_to_date_mills", "uni:textView_to_date_format", "uni:textView_to_date_use_today", "uni:textView_to_date_use_yesterday")
    @JvmStatic
    fun bindingTextViewToDateWithTodayYesterday(tv: TextView, mills: Long, format: String, useToday: Boolean, useYesterday: Boolean) {
        if(mills != 0L){
            tv.text = mills.toDateString(format, tv.context, useToday, useYesterday)
        }
    }

    @SuppressLint("SetTextI18n")
    @BindingAdapter(value=["uni:textView_from_date_mills", "uni:textView_to_date_mills", "uni:textView_to_date_format"], requireAll = false)
    @JvmStatic
    fun bindingTextViewFromToDate(tv: TextView, fromMills: Long, toMills: Long, format: String) {
        if (fromMills != 0L && toMills != 0L) {
            tv.text = "${fromMills.toDateString(format = format)} ~ ${toMills.toDateString(format = format)}"
        }
    }

    @SuppressLint("SetTextI18n")
    @BindingAdapter("uni:textView_from_date_mills", "uni:textView_to_date_mills", "uni:textView_to_date_format", "uni:textView_is_always", "uni:textView_is_always_text")
    @JvmStatic
    fun bindingTextViewFromToDateWithAlways(tv: TextView, fromMills: Long, toMills: Long, format: String, isAlways: Boolean, alwaysStr: String) {
        if (!isAlways) {
            if (fromMills != 0L && toMills != 0L) {
                tv.text = "${fromMills.toDateString(format)} ~ ${toMills.toDateString(format)}"
            }
        }else{
            tv.text = alwaysStr
        }
    }

    @BindingAdapter("uni:textView_convert_gender")
    @JvmStatic
    fun bindingTextViewConvertGender(tv: TextView, code: String?) {
        code?.let {
            when (code) {
                "M" -> tv.setText(R.string.label_gender_m)
                "F" -> tv.setText(R.string.label_gender_f)
            }
        }
    }

    @BindingAdapter("uni:feed_result_type_color")
    @JvmStatic
    fun bindingSetFeedColor(view: View, feed_result_type_color:String?) {
        if("POSITIVE" == feed_result_type_color) { //긍정
            view.setBackgroundResource(R.drawable.feed_char_right_green_select)
        }else if ("USUALLY" == feed_result_type_color) { //보통
            view.setBackgroundResource(R.drawable.feed_char_right_yellow_select)
        }else if ("DENIAL" == feed_result_type_color) { //부정
            view.setBackgroundResource(R.drawable.feed_char_right_red_select)
        }else if("NONE" == feed_result_type_color){ //텍스트
            view.setBackgroundResource(R.drawable.feed_char_right_blue_select)
        }
    }

    @BindingAdapter("uni:feed_result_type_graph_color")
    @JvmStatic
    fun bindingSetFeedGraphColor(view: View, feed_result_type_color:String?) {
        if("POSITIVE" == feed_result_type_color) { //긍정
            view.setBackgroundResource(R.drawable.feed_char_graph_green_shape)
        }else if ("USUALLY" == feed_result_type_color) { //보통
            view.setBackgroundResource(R.drawable.feed_char_graph_yellow_shape)
        }else if ("DENIAL" == feed_result_type_color) { //부정
            view.setBackgroundResource(R.drawable.feed_char_graph_red_shape)
        }else if("NONE" == feed_result_type_color){ //텍스트
            view.setBackgroundResource(R.drawable.feed_char_graph_gray_shape)
        }
    }

    @BindingAdapter("uni:feed_result_type_image")
    @JvmStatic
    fun bindingSetFeedImage(view: ImageView, feed_result_type_name:String?) {
        view.visibility = View.VISIBLE
        if("POSITIVE" == feed_result_type_name){ //긍정
            view.setImageResource(R.drawable.ic_dna_safe)
        }else if("USUALLY" == feed_result_type_name) { //보통
            view.setImageResource(R.drawable.ic_dna_normal)
        }else if ("DENIAL" == feed_result_type_name){ //부정
            view.setImageResource(R.drawable.ic_dna_caution)
        }else{ //텍스트
            view.visibility = View.GONE
        }
    }

    @BindingAdapter("uni:feed_result_type_text_color")
    @JvmStatic
    fun bindingSetFeedTextColor(view: TextView, feed_result_type_color:String?) {
        if("POSITIVE" == feed_result_type_color) { //긍정
            view.setTextColor(ContextCompat.getColor(view.context, R.color.others_green))
        }else if("USUALLY" == feed_result_type_color) { //보통
            view.setTextColor(ContextCompat.getColor(view.context, R.color.others_yellow))
        }else if("DENIAL" == feed_result_type_color) { //부정
            view.setTextColor(ContextCompat.getColor(view.context, R.color.others_red))
        }
    }

    @BindingAdapter(value = ["uni:textView_comma_format", "uni:textView_comma_format_default"], requireAll = false)
    @JvmStatic
    fun bindingTextViewCommaFormat(tv: TextView, count: Int?, defaultStr: String?) {
        tv.text = count?.run { DecimalFormat("###,###").format(this) }?:defaultStr?:""
    }

    @BindingAdapter(value = ["uni:textView_comma_format", "uni:textView_comma_format_default"], requireAll = false)
    @JvmStatic
    fun bindingTextViewCommaFormat(tv: TextView, count: String?, defaultStr: String?) {
        tv.text = count?.run { DecimalFormat("###,###").format(this.replace(",","").toInt()) }?:defaultStr?:""
    }*/
}