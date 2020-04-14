package codes.malukimuthusi.newsdemoapp.viewList

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import codes.malukimuthusi.newsdemoapp.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, urlToImage: String?) {
    val sharedOptions = RequestOptions()
        .placeholder(R.drawable.loading_animation)
        .centerCrop()
        .error(R.drawable.ic_broken_image)

    //        val imgUri = urlToImage.toUri().buildUpon().scheme("https").build()
    Glide.with(imgView.context)
        .load(urlToImage)
        .apply(sharedOptions)
        .into(imgView)


}