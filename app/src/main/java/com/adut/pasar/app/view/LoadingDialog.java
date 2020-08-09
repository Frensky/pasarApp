package com.adut.pasar.app.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

import com.adut.pasar.app.R;

public class LoadingDialog {

    private ProgressDialog mProgressDialog;

    protected TextView titleView;
    protected TextView contentView;

    private String mMessage;
    private String mTitle;

    public void setTitleAndContent(String title, String content){
        mMessage = content;
        mTitle = title;
    }

    public LoadingDialog(Context p_context) {
        mProgressDialog = new ProgressDialog(p_context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        mProgressDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        mMessage = "Mohon Tunggu";
        mTitle = "Loading....";

    }

    public final LoadingDialog setOnCancelListener(DialogInterface.OnCancelListener p_listener) {
        mProgressDialog.setOnCancelListener(p_listener);
        return this;
    }

    private void initView(){
        titleView = (TextView) mProgressDialog.findViewById(R.id.loading_dialog_title);
        contentView = (TextView) mProgressDialog.findViewById(R.id.loading_dialog_content);
    }

    public final void show() {
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.dialog_loading);

        initView();

        titleView.setText(mTitle);
        contentView.setText(mMessage);
    }

    public final void dismiss() {
        mProgressDialog.dismiss();
    }

    public final boolean isShowing() {
        return mProgressDialog.isShowing();
    }

}
