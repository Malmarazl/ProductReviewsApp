package com.example.productreviewsapp.helpers

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RatingBar
import androidx.fragment.app.DialogFragment
import com.example.productreviewsapp.R
import com.example.productreviewsapp.detail.NewReviewListener


class DialogReviews(private val newReviewListener: NewReviewListener) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater

            val view: View = inflater.inflate(R.layout.dialog_review, null)

            builder.setView(view)
                .setPositiveButton(
                    R.string.button_send_review,
                    DialogInterface.OnClickListener { dialog, id ->
                        val editText = view.findViewById<EditText>(R.id.text_review)?.text.toString()
                        val ratingBar = view.findViewById<RatingBar>(R.id.rating_stars).rating

                        if(editText.isNotEmpty()) {

                            newReviewListener.sendNewReview(editText, ratingBar)

                            dialog.cancel()
                        }
                    })
                .setNegativeButton(
                    R.string.button_cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}