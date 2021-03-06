package com.xpro.ebusalmoner.baseapi;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xpro.ebusalmoner.R;
import com.xpro.ebusalmoner.utils.ProgressDialogUtils;
import com.xpro.ebusalmoner.widget.CustomDialog;
import com.xpro.ebusalmoner.widget.CustomDialog1;
import com.xpro.ebusalmoner.widget.MyCommonDialog;
import com.xpro.ebusalmoner.widget.ZoomImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * @ClassName: BaseActivity
 * @Description: 基类，提示框公共方法
 * @author: houyang
 * @date: 2016年9月20日 下午7:15:32
 */
public class BaseActivity extends SuperActivity {
    protected RelativeLayout titleLayout;
    private CustomDialog choosePicDialog;
    public static final int PIC_TAKEPHOTO = 1004;
    public static final int PIC_ALBUM = 1005;
    public static final int PIC_OK = 1006;
    public static final int CROP_PHOTO = 1007;

//	public boolean isManager() {
//		if (mApp.getDefaultAccount() != null) {
//			// 教师版本返回
//			if (mApp.getDefaultAccount().getUserType() == 1
//					|| mApp.getDefaultAccount().getUserType() == 4) {
//				return true;
//			}
//
//			// 家长版本返回
//			else if (mApp.getDefaultAccount().getUserType() == 3) {
//				return false;
//			}
//		}
//		return true;
//	}

    /**
     * @param msg
     * @Title: showTip
     * @Description: 确定方法，无点击返回
     * @return: void
     */
    public void showTip(String msg) {
        final Dialog customDialog = new CustomDialog1(this,
                R.style.CustomDialogTheme, R.layout.dialog_custom);
        customDialog.show();
        TextView content = (TextView) customDialog
                .findViewById(R.id.custom_dialog_content);
        TextView divider = (TextView) customDialog
                .findViewById(R.id.custom_dialog_divider);
        TextView confirm = (TextView) customDialog
                .findViewById(R.id.custom_dialog_confirm);
        TextView cancel = (TextView) customDialog
                .findViewById(R.id.custom_dialog_cancel);
        content.setText(msg);
        divider.setVisibility(View.GONE);
        cancel.setVisibility(View.GONE);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
    }

    /**
     * @param msg
     * @param callBack
     * @Title: showTip
     * @Description: 确定方法，有点击返回
     * @return: void
     */
    public void showTip(String msg, final OnCustomDialogConfirmListener callBack) {
        final Dialog customDialog = new CustomDialog1(this,
                R.style.CustomDialogTheme, R.layout.dialog_custom);
        customDialog.setCancelable(false);
        customDialog.show();
        TextView content = (TextView) customDialog
                .findViewById(R.id.custom_dialog_content);
        TextView divider = (TextView) customDialog
                .findViewById(R.id.custom_dialog_divider);
        TextView confirm = (TextView) customDialog
                .findViewById(R.id.custom_dialog_confirm);
        TextView cancel = (TextView) customDialog
                .findViewById(R.id.custom_dialog_cancel);
        content.setText(msg);
        divider.setVisibility(View.GONE);
        cancel.setVisibility(View.GONE);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                callBack.onClick();
            }
        });
    }

    /**
     * @param msg
     * @param callBack
     * @Title: showConfirmTip
     * @Description: 取消确定方法，确定有点击返回
     * @return: void
     */
    public void showConfirmTip(String msg,
                               final OnCustomDialogConfirmListener callBack) {
        final Dialog customDialog = new CustomDialog1(this,
                R.style.CustomDialogTheme, R.layout.dialog_custom);
        customDialog.show();
        TextView content = (TextView) customDialog
                .findViewById(R.id.custom_dialog_content);
        TextView confirm = (TextView) customDialog
                .findViewById(R.id.custom_dialog_confirm);
        TextView cancel = (TextView) customDialog
                .findViewById(R.id.custom_dialog_cancel);
        content.setText(msg);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                callBack.onClick();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
    }

    /**
     * @param msg
     * @param confirm
     * @param cancel
     * @Title: showConfirmTip
     * @Description: 取消确定方法，均有点击返回
     * @return: void
     */
    public void showConfirmTip(String msg,
                               final OnCustomDialogConfirmListener confirm,
                               final OnCustomDialogConfirmListener cancel) {
        final Dialog customDialog = new CustomDialog1(this,
                R.style.CustomDialogTheme, R.layout.dialog_custom);
        customDialog.show();
        TextView content = (TextView) customDialog
                .findViewById(R.id.custom_dialog_content);
        TextView confirmBtn = (TextView) customDialog
                .findViewById(R.id.custom_dialog_confirm);
        TextView cancelBtn = (TextView) customDialog
                .findViewById(R.id.custom_dialog_cancel);
        content.setText(msg);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                confirm.onClick();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                cancel.onClick();
            }
        });
    }

    public interface OnCustomDialogConfirmListener {
        void onClick();
    }

    public void setTitle(String title) {
        titleLayout = (RelativeLayout) findViewById(R.id.relativelayout1);
        // if(isTeacher()) {
        // titleLayout.setBackgroundResource(R.drawable.title_top_bg);
        // }else {
        // titleLayout.setBackgroundResource(R.drawable.title_top_bg);
        // }
        TextView titleView = (TextView) findViewById(R.id.title);
        if (titleView != null)
            titleView.setText(title);
    }

    private MyCommonDialog dialog;
;
    public void showPhoneDatil(final Context context, final String phonenumber) {
        dialog = new MyCommonDialog(context, "提示", "确定拨打电话", "取消", "确认");
        dialog.setOkListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing())
                    dialog.dismiss();
//                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
//                        + phonenumber));
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+phonenumber));
                context.startActivity(intent);
            }
        });
        dialog.setCancelListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing())
                    dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showSureDatil(final Context context, String hint) {
        dialog = new MyCommonDialog(context, "提示", hint, "取消", "确认");
        dialog.setOkListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing())
                    dialog.dismiss();
                finish();
            }
        });
        dialog.setCancelListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing())
                    dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 弹出框PopupWindow,点击图片查看大图
     */
    private PopupWindow popWindow;
    private int width_pop, height;// 屏幕高度、宽度
    private FrameLayout linear;

    @SuppressWarnings("deprecation")
    public void initPopupWindow(View view3, String path) {
        ProgressDialogUtils.showProgressDialog("加载中", this, true);
        LayoutInflater inflater = LayoutInflater.from(this);
        // 引入窗口配置文件
        View view1 = inflater.inflate(R.layout.image_pop1, null);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        width_pop = metric.widthPixels;
        height = metric.heightPixels;
        ZoomImageView image = (ZoomImageView) view1.findViewById(R.id.image);
        Button closeImage = (Button) view1.findViewById(R.id.close);
        linear = (FrameLayout) view1.findViewById(R.id.linear);
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
        // popWindow = new PopupWindow(view1, (int) (width_pop*0.8),
        // (int) (height*0.8), false);
        popWindow = new PopupWindow(view1, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, false);
        backgroundAlpha(0.5f);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setOutsideTouchable(true);
        popWindow.setFocusable(true);
        popWindow.setTouchable(true);
        popWindow.update();
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popWindow.dismiss();
                    return true;
                }
                return false;
            }

        });
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
        closeImage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popWindow.dismiss();
            }
        });

        new NormalLoadPictrue().getPicture(path, image);
        popWindow.showAtLocation(view3, Gravity.CENTER, 0, 0);
    }

    // 背景Alpha
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }

    /**
     * 加载图片类，显示大图
     */
    public class NormalLoadPictrue {
        private String uri;
        private ImageView imageView;
        private byte[] picByte;

        public void getPicture(String uri, ImageView imageView) {
            this.uri = uri;
            this.imageView = imageView;
            new Thread(runnable).start();
        }

        @SuppressLint("HandlerLeak")
        Handler handle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    if (picByte != null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(picByte,
                                0, picByte.length);
                        imageView.setImageBitmap(bitmap);
                        ProgressDialogUtils.dismissProgressBar();
                    }
                }
            }
        };

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(uri);
                    HttpURLConnection conn = (HttpURLConnection) url
                            .openConnection();
                    conn.setRequestMethod("GET");
                    conn.setReadTimeout(10000);

                    if (conn.getResponseCode() == 200) {
                        InputStream fis = conn.getInputStream();
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        byte[] bytes = new byte[1024];
                        int length = -1;
                        while ((length = fis.read(bytes)) != -1) {
                            bos.write(bytes, 0, length);
                        }
                        picByte = bos.toByteArray();
                        bos.close();
                        fis.close();

                        Message message = new Message();
                        message.what = 1;
                        handle.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

    }

    /**
     * 弹出框PopupWindow
     */
    private PopupWindow popWindow_delete;
    private int width_delete, height_delete;//屏幕高度、宽度
    private LinearLayout linear_delete,linear_pop2;

    public void initPopup_delete(View view3, ImageView imageView) {
        LayoutInflater inflater = LayoutInflater.from(this);
        // 引入窗口配置文件
        View view2 = inflater.inflate(R.layout.image_pop2, null);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        width_delete = metric.widthPixels;
        height_delete = metric.heightPixels;
        TextView text = (TextView) view2.findViewById(R.id.text);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除图片

            }
        });
        linear_pop2 = (LinearLayout) view2.findViewById(R.id.linear_pop2);
//        linear.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                popWindow.dismiss();
//                return true;
//            }
//        });
//        popWindow = new PopupWindow(view2, (int) (width_pop * 0.3),
//                (int) (width_pop * 0.2), false);
        popWindow = new PopupWindow(view2, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, false);
        backgroundAlpha(0.5f);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setOutsideTouchable(true);
        popWindow.setFocusable(true);
        popWindow.setTouchable(true);
        popWindow.update();
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popWindow.dismiss();
                    return true;
                }
                return false;
            }

        });
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
//        new NormalLoadPictrue().getPicture(path, image);
        view2.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth=view2.getMeasuredWidth();
        int popupHeight=view2.getMeasuredHeight();

        int[] location = new int[2];
        imageView.getLocationOnScreen(location);
        popWindow.showAtLocation(imageView, Gravity.NO_GRAVITY, location[0] + imageView.getWidth() / 2 - popupWidth / 2, location[1] - popupHeight);
//        popWindow.showAtLocation(view3, Gravity.CENTER, 0, 0);
    }

}
