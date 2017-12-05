package com.yongheng.application;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yongheng.MainApplication;
import com.yongheng.R;
import com.yongheng.utils.ProgressBarUtil;
import com.yongheng.widgets.MaterialDialog;

/**
 * Created by melon on 2017/4/8.
 */

public class BaseActivity extends AppCompatActivity {

    private MaterialDialog mMaterialDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        MainApplication.addContext(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainApplication.removeContext();
    }

    public void showCommonAlert(int titleRes, int msgRes,final View.OnClickListener positiveListener, final View.OnClickListener negListener){
        showCommonAlert(getString(titleRes),getString(msgRes),R.string.ok,positiveListener,negListener);
    }

    public void showCommonAlert(String title, String msg, int posTxtResId, final View.OnClickListener positiveListener, final View.OnClickListener negListener) {
        if (mMaterialDialog != null) {
            if (mMaterialDialog.isShowing()) {
                mMaterialDialog.dismiss();
                mMaterialDialog = null;
            }
            mMaterialDialog = null;
        }
        mMaterialDialog = new MaterialDialog(this);
        mMaterialDialog.setTitle(title);
        mMaterialDialog.setMessage(msg);
        mMaterialDialog.setCancelable(true);
        mMaterialDialog.setCanceledOnTouchOutside(true);
        mMaterialDialog.setPositiveButton(posTxtResId, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
                if (positiveListener != null) {
                    positiveListener.onClick(v);
                }
            }
        });

        mMaterialDialog.setNegativeButton(R.string.no, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
                if (negListener != null) {
                    negListener.onClick(v);
                }

            }
        });
        mMaterialDialog.show();
    }

    public void showCommonAlert(int titleResId, int msgResId, final View.OnClickListener l) {
        showCommonAlert(titleResId, getString(msgResId), l);
    }

    public void showCommonAlert(int titleResId, String msg, final View.OnClickListener l) {
        if (mMaterialDialog != null) {
            if (mMaterialDialog.isShowing()) {
                mMaterialDialog.dismiss();
            }
            mMaterialDialog = null;
        }

        mMaterialDialog = new MaterialDialog(this);
        mMaterialDialog.setTitle(titleResId);
        mMaterialDialog.setMessage(msg);
        mMaterialDialog.setPositiveButton(R.string.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
                if (l != null) {
                    l.onClick(v);
                }
            }
        });
        mMaterialDialog.show();
    }

    public void showCommonAlert(int titleResId, int msgResId, int posTxtResId, final View.OnClickListener l) {
        showCommonAlert(titleResId, getString(msgResId), posTxtResId, l);
    }

    public void showCommonAlert(int titleResId, String msg, int posTxtResId, final View.OnClickListener l) {
        if (mMaterialDialog != null) {
            if (mMaterialDialog.isShowing()) {
                mMaterialDialog.dismiss();
            }
            mMaterialDialog = null;
        }
        mMaterialDialog = new MaterialDialog(this);
        mMaterialDialog.setTitle(titleResId);
        mMaterialDialog.setMessage(msg);
        mMaterialDialog.setPositiveButton(posTxtResId, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
                if (l != null) {
                    l.onClick(v);
                }
            }
        });
        mMaterialDialog.show();

    }


    public void showCommonAlert(int titleResId, int msgResId, int posTxtResId, boolean canceledOnTouchOutside, final View.OnClickListener l) {

        if (mMaterialDialog != null) {
            if (mMaterialDialog.isShowing()) {
                mMaterialDialog.dismiss();
            }
            mMaterialDialog = null;
        }
        mMaterialDialog = new MaterialDialog(this);
        mMaterialDialog.setTitle(titleResId);
        mMaterialDialog.setMessage(msgResId);
        mMaterialDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        mMaterialDialog.setPositiveButton(posTxtResId, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
                if (l != null) {
                    l.onClick(v);
                }
            }
        });
        mMaterialDialog.show();
    }

    public void showCommonAlert(int titleResId, int msgResId, int posTxtResId, int nagTxtResId, final View.OnClickListener l) {
        showCommonAlert(titleResId, msgResId, posTxtResId, nagTxtResId, l, null);
    }

    public void showCommonAlert(int titleResId, int msgResId, int posTxtResId, int nagTxtResId, final View.OnClickListener l, final View.OnClickListener listener) {

        if (mMaterialDialog != null) {
            if (mMaterialDialog.isShowing()) {
                mMaterialDialog.dismiss();
            }
            mMaterialDialog = null;
        }
        mMaterialDialog = new MaterialDialog(this);
        mMaterialDialog.setTitle(titleResId);
        mMaterialDialog.setMessage(msgResId);
        mMaterialDialog.setCancelable(true);
        mMaterialDialog.setCanceledOnTouchOutside(true);
        mMaterialDialog.setPositiveButton(posTxtResId, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
                if (l != null) {
                    l.onClick(v);
                }
            }
        });
        mMaterialDialog.setNegativeButton(nagTxtResId, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
                mMaterialDialog = null;
                if (listener != null) {
                    listener.onClick(v);
                }
            }
        });
        mMaterialDialog.show();
    }

    public void showProgress(){
        showProgress(R.string.progress_tip,false);
    }

    public void showProgress(int msgResId, boolean cancelable) {

        if (mMaterialDialog != null) {
            if (mMaterialDialog.isShowing()) {
                mMaterialDialog.dismiss();
            }
            mMaterialDialog = null;
        }

        mMaterialDialog = new MaterialDialog(this);
        if (mMaterialDialog != null) {

            View view = LayoutInflater.from(this).inflate(R.layout.layout_progressbar, null);
            TextView msgTv = (TextView) view.findViewById(R.id.content);
            msgTv.setText(msgResId);
            ProgressBar progressBar = (ProgressBar) view.findViewById(android.R.id.progress);
            final int materialBlue = getResources().getColor(R.color.progress_color);
            ProgressBarUtil.setupProgressDialog(progressBar, materialBlue);
            mMaterialDialog.setView(view);
        }

//        mMaterialDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                mMaterialDialog = null;
//            }
//        });
        mMaterialDialog.setCancelable(cancelable);
        mMaterialDialog.setMessage(getText(msgResId));
        mMaterialDialog.show();
    }

    public void hideProgress() {
        if (mMaterialDialog != null) {
            if (mMaterialDialog.isShowing()) {
                mMaterialDialog.dismiss();
            }
            mMaterialDialog = null;
        }
    }

}
