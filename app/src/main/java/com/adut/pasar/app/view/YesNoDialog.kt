package com.adut.pasar.app.view

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.adut.pasar.app.R

class YesNoDialog :DialogFragment(){

    interface YesNoDialogListener {
        fun onDismmisClick()
        fun onYesClick()
    }

    private var listener: YesNoDialogListener? = null
    private var message:String? = null

    fun setListener(listener: YesNoDialogListener?) {
        this.listener = listener
    }

    fun setContent(content: String) {
        this.message = content
    }

    lateinit var yesNoDialog: Dialog
    protected var yesBtn: Button? = null
    protected var noBtn: Button? = null
    protected var contentText: TextView? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // TODO Auto-generated method stub
        yesNoDialog = Dialog(requireActivity())
        yesNoDialog.setCanceledOnTouchOutside(false)
        yesNoDialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        yesNoDialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        yesNoDialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        yesNoDialog.setContentView(R.layout.dialog_yesno)

        yesNoDialog.show()

        initViews()

        message?.let {
            contentText?.text = message
        }

        yesBtn?.setOnClickListener {
            listener?.onYesClick()
            dismiss()
        }

        noBtn?.setOnClickListener {
            listener?.onDismmisClick()
            dismiss()
        }

        return yesNoDialog
    }

    private fun initViews() {
        yesBtn = yesNoDialog!!.findViewById(R.id.dialog_yes_btn) as Button
        noBtn = yesNoDialog!!.findViewById(R.id.dialog_no_btn) as Button
        contentText = yesNoDialog!!.findViewById(R.id.dialog_content_text) as TextView
    }

    override fun dismiss() {
        listener = null
        super.dismiss()
    }
}