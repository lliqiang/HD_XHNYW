package com.hengda.smart.xhnyw.d.view;

import android.content.Context;
import android.view.View;

import com.hengda.smart.xhnyw.d.R;

public class DialogCenter {

    static HProgressDialog progressDialog;
    static HDialogBuilder hDialogBuilder;

    /**
     * 显示ProgressDialog
     *
     * @param message
     * @param cancelable
     */
    public static void showProgressDialog(Context context, int message, boolean cancelable) {
        hideProgressDialog();
        progressDialog = new HProgressDialog(context);
        progressDialog
                .message(message)
                .tweenAnim(R.mipmap.progress_roate, R.anim.progress_rotate)
                .outsideCancelable(cancelable)
                .cancelable(cancelable)
                .show();
    }

    /**
     * 显示ProgressDialog
     *
     * @param message
     * @param cancelable
     */
    public static void showProgressDialog(Context context, String message, boolean cancelable) {
        hideProgressDialog();
        progressDialog = new HProgressDialog(context);
        progressDialog
                .message(message)
                .tweenAnim(R.mipmap.progress_roate, R.anim.progress_rotate)
                .outsideCancelable(cancelable)
                .cancelable(cancelable)
                .show();
    }

    /**
     * 隐藏ProgressDialog
     */
    public static void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    /**
     * 显示Dialog-CustomView
     *
     * @param context
     * @param view
     * @param dialogClickListener
     * @param txt
     */
    public static void showDialog(Context context,
                                  View view,
                                  final DialogClickListener dialogClickListener,
                                  int... txt) {
        hideDialog();
        hDialogBuilder = new HDialogBuilder(context);
        hDialogBuilder
                .withIcon(R.mipmap.ic_launcher)
                .title(txt[0])
                .setCustomView(view)
                .pBtnText(txt[1])
                .pBtnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogClickListener.p();
                    }
                })
                .cancelable(false);
        if (txt.length == 3) {
            hDialogBuilder
                    .nBtnText(txt[2])
                    .nBtnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogClickListener.n();
                        }
                    });
        }
        hDialogBuilder.show();
    }

    /**
     * 显示Dialog-CustomView
     *
     * @param context
     * @param view
     * @param dialogClickListener
     * @param txt
     */
    public static void showDialog(Context context,
                                  View view,
                                  final DialogClickListener dialogClickListener,
                                  String... txt) {
        hideDialog();
        hDialogBuilder = new HDialogBuilder(context);
        hDialogBuilder
                .withIcon(R.mipmap.ic_launcher)

                .title(txt[0])
                .setCustomView(view)
                .pBtnText(txt[1])
                .pBtnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogClickListener.p();
                    }
                })
                .cancelable(false);
        if (txt.length == 3) {
            hDialogBuilder
                    .nBtnText(txt[2])
                    .nBtnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogClickListener.n();
                        }
                    });
        }
        hDialogBuilder.show();
    }

    /**
     * 显示Dialog-Message
     *
     * @param context
     * @param dialogClickListener
     * @param txt
     */
    public static void showDialog(Context context,
                                  final DialogClickListener dialogClickListener,
                                  int... txt) {
        hideDialog();
        hDialogBuilder = new HDialogBuilder(context);
        hDialogBuilder
                .withIcon(R.mipmap.ic_launcher)

                .title(txt[0])
                .message(txt[1])
                .pBtnText(txt[2])
                .pBtnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogClickListener.p();
                    }
                })
                .cancelable(false);
        if (txt.length == 4) {
            hDialogBuilder
                    .nBtnText(txt[3])
                    .nBtnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogClickListener.n();
                        }
                    });
        }
        hDialogBuilder.show();
    }

    /**
     * 显示Dialog-Message
     *
     * @param context
     * @param dialogClickListener
     * @param txt
     */
    public static void showDialog(Context context,
                                  final DialogClickListener dialogClickListener,
                                  String... txt) {
        hideDialog();
        hDialogBuilder = new HDialogBuilder(context);
        hDialogBuilder
                .withIcon(R.mipmap.ic_launcher)

                .title(txt[0])
                .message(txt[1])
                .pBtnText(txt[2])
                .pBtnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogClickListener.p();
                    }
                })
                .cancelable(false);
        if (txt.length == 4) {
            hDialogBuilder
                    .nBtnText(txt[3])
                    .nBtnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogClickListener.n();
                        }
                    });
        }
        hDialogBuilder.show();
    }

    /**
     * 隐藏Dialog
     */
    public static void hideDialog() {
        if (hDialogBuilder != null) {
            hDialogBuilder.dismiss();
            hDialogBuilder = null;
        }
    }

}
