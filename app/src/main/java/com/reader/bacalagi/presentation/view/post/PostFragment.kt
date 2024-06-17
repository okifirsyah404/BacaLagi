package com.reader.bacalagi.presentation.view.post

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentDashboardBinding
import com.reader.bacalagi.databinding.FragmentPostBinding

class PostFragment : BaseFragment<FragmentPostBinding>() {
    companion object {
        private const val PICK_IMAGE_REQUEST = 1
        private const val CAPTURE_IMAGE_REQUEST = 2
    }
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentPostBinding {
        return FragmentPostBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.ivUploadImage.ivUpload.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        binding.ivUploadImage.ivUpload.apply {
//            super.onActivityResult(requestCode, resultCode, data)
//            if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
//                val imageUri: Uri = data.data!!
//                val imageStream = contentResolver.openInputStream(imageUri)
//                val selectedImage = BitmapFactory.decodeStream(imageStream)
//                setImageBitmap(selectedImage)
//            }
//        }
//    }

    override fun initAppBar() {
        binding.mainToolbar.apply {
            mainToolbar.title = getString(R.string.appbar_title_post)
            mainToolbar.setNavigationIcon(R.drawable.ic_back)

            mainToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }
}