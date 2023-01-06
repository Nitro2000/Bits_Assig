package com.example.bitsdashboardassign

import android.app.Dialog
import android.content.ClipDescription
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.google.android.material.textfield.TextInputLayout

const val UTIL_TAG = "UTILS"

object Utils {

    fun bottomNavBarVisibility(activity: FragmentActivity, visibility: Int) {
        try {
            (activity as MainActivity).bottonNavBarVisibility(visibility)
        } catch (e: Exception) {
            Log.d(UTIL_TAG, "Bottom nav visibility error")
        }
    }

    fun showAddItemDialog(context: Context, showDescription: Boolean, title: String, nameTxt: String, descTxt: String, errorMess: (mess: String) -> Any,  callback: (name: String, desc: String) -> Any) {
        val dialog = Dialog(context).apply {
            setContentView(R.layout.add_item_dialogue)
        }
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val titleText = dialog.findViewById<TextView>(R.id.addTxt)
        val yes = dialog.findViewById<Button>(R.id.yes)
        val no = dialog.findViewById<Button>(R.id.no)
        val name = dialog.findViewById<TextInputLayout>(R.id.nameTxt)
        val desc = dialog.findViewById<TextInputLayout>(R.id.descTxt)

        titleText.text = title
        name.editText?.setText(nameTxt)
        desc.editText?.setText(descTxt)

        desc.visibility = if (showDescription) View.VISIBLE else View.GONE


        no.setOnClickListener {
            dialog.dismiss()
        }
        yes.setOnClickListener {
            val nameText = name.editText?.text.toString()
            val descText = desc.editText?.text.toString()
            if (nameText.isBlank() || (showDescription && desc.editText?.text.toString().isBlank())) {
                errorMess("Fields can't be empty")
            } else {
                dialog.dismiss()
                callback(nameText, descText)
            }
        }
        dialog.show()
    }
}