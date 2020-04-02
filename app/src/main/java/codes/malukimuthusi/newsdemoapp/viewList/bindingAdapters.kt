package codes.malukimuthusi.newsdemoapp.viewList

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import codes.malukimuthusi.newsdemoapp.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, urlToImage: String?) {
    urlToImage?.let {
        val imgUri = urlToImage.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
}