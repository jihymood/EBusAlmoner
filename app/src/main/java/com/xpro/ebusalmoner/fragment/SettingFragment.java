package com.xpro.ebusalmoner.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xpro.ebusalmoner.R;
import com.xpro.ebusalmoner.activity.AboutActivity;
import com.xpro.ebusalmoner.adapter.CaptianSettingDrawAdapter;
import com.xpro.ebusalmoner.baseapi.BaseActivity;
import com.xpro.ebusalmoner.baseapi.BaseFragment;
import com.xpro.ebusalmoner.constants.Constants;
import com.xpro.ebusalmoner.entity.NewVersionBody;
import com.xpro.ebusalmoner.entity.PersonalBody;
import com.xpro.ebusalmoner.logic.TaskLogic;
import com.xpro.ebusalmoner.utils.DialogUtils;
import com.xpro.ebusalmoner.utils.PreferencesUtils;
import com.xpro.ebusalmoner.widget.CircleImageView;
import com.xpro.ebusalmoner.widget.CustomDialog;
import com.xpro.ebusalmoner.widget.CustomDialog1;

import org.xutils.common.util.LogUtil;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * 我的 Created by zhangcz on 2016/4/25.
 */
public class SettingFragment extends BaseFragment implements
        View.OnClickListener {

    private final String TAG = "MeFragment";

    private View rootView;
    private RelativeLayout sex_relativeLayout, number_relativeLayout,
            about_relativelayout, settings_relativeLayout;
    private TextView nameTextView, sexTextView, phoneTextView, numberTextView;
    private EditText newNum_edittext,editIp;
    private CircleImageView mine_logo;
    private Button setBtn,btnIp;
    private ImageView mine_setting;
    private Dialog waitingDialog;
    private CustomDialog choosePicDialog;
    public static final int PIC_TAKEPHOTO = 1004;
    public static final int PIC_ALBUM = 1005;
    public static final int PIC_OK = 1006;
    public static final int CROP_PHOTO = 1007;
    private Uri imageUri;
    private DrawerLayout parentlayout;
    public ListView settingListView;
    public ArrayList<String> database;
    public CaptianSettingDrawAdapter adapter;

    public PopupWindow popupWindow;
    public TextView about_app;
    public TextView check_update;
    public TextView clear_store;

    private MyHandler handler;
    private TaskLogic logic;
    private String versionStr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup group = (ViewGroup) rootView.getParent();
            if (group != null) {
                group.removeView(rootView);
            }
        } else {
            LogUtil.e("MeFragment is start");
            rootView = inflater.inflate(R.layout.fragment_me, container, false);
        }

        setViews();
        mine_logo.setImageResource(R.mipmap.logo);
        setOnClickListener();

//        handler = new MyHandler();
//        logic = new TaskLogic(handler, getActivity());
//        logic.getPersonal();

        return rootView;
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
//        logic.getPersonal();
    }

    /**
     * 初始化控件
     */
    public void setViews() {
        sex_relativeLayout = (RelativeLayout) rootView
                .findViewById(R.id.sex_relativeLayout);
        mine_logo = (CircleImageView) rootView.findViewById(R.id.mine_logo);
        sexTextView = (TextView) rootView.findViewById(R.id.sexTextView);
        phoneTextView = (TextView) rootView.findViewById(R.id.phoneTextView);
        nameTextView = (TextView) rootView.findViewById(R.id.mine_name);
        numberTextView = (TextView) rootView.findViewById(R.id.mine_code);
        setBtn = (Button) rootView.findViewById(R.id.set);
        mine_setting = (ImageView) rootView.findViewById(R.id.mine_setting);
        parentlayout = (android.support.v4.widget.DrawerLayout) rootView
                .findViewById(R.id.id_drawerlayout);
        editIp = (EditText) rootView.findViewById(R.id.editIp);
        btnIp = (Button) rootView.findViewById(R.id.btnIp);
    }

    /**
     * 监听事件
     */
    public void setOnClickListener() {
        sex_relativeLayout.setOnClickListener(this);
        mine_logo.setOnClickListener(this);
        mine_setting.setOnClickListener(this);
        editIp.setOnClickListener(this);
        btnIp.setOnClickListener(this);
    }

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case Constants.GET_PERSONAL_SUCCESS:
                    PersonalBody body = (PersonalBody) msg.obj;
                    nameTextView.setText(body.getName());
                    sexTextView.setText(body.getSex());
                    phoneTextView.setText(body.getPhoneNum());
                    numberTextView.setText(body.getCode());
                    break;
                case Constants.GET_PERSONAL_FAIL:
                    String message = (String) msg.obj;
                    break;

                case Constants.GET_NEWVERSION_SUCCESS:
                    NewVersionBody versionBody = (NewVersionBody) msg.obj;
                    versionStr = versionBody.getVersion();

                    break;
                case Constants.GET_NEWVERSION_FAIL:
                    String versionFail = (String) msg.obj;
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        // BaseActivity x = (BaseActivity) getActivity();
        switch (v.getId()) {
            case R.id.mine_logo:// 更换头像
                // getPhoto();
                break;
            case R.id.sex_relativeLayout:// 更改性别
                getPhoto_sex();
                break;
            case R.id.mine_setting:// 弹出右上角设置
                initPopWindow();
                break;
            case R.id.clear_store:// 清除缓存
                Toast.makeText(getActivity(), "缓存清除完毕", Toast.LENGTH_SHORT).show();
                break;
            case R.id.about_app:// 关于
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                intent.putExtra("AppVersion", getAppVersionName(getActivity()));
                startActivity(intent);
                break;
            case R.id.check_update:// 检查更新
                Toast.makeText(getActivity(), "当前已是最新版本", Toast.LENGTH_SHORT)
                        .show();
//                logic.getNewVersion();
                showConfirmTip("发现新版本,是否更新", new OnCustomDialogConfirmListener() {
                    @Override
                    public void onClick() {
                        // TODO Auto-generated method stub
//					Uri uri = Uri.parse(versionStr);
                        Uri uri = Uri.parse("http://www.baidu.com");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);

                    }
                }, new OnCustomDialogConfirmListener() {

                    @Override
                    public void onClick() {
                        // TODO Auto-generated method stub
                    }
                });
                break;
            case R.id.btnIp:
                String str=editIp.getText().toString();
                if (null == str || "".equals(str)) {

                }else{
                    PreferencesUtils.putString(getActivity(),"changeip",str);
                    Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
//                    HttpUrls.baseUrl(getActivity(), editIp.getText() + "");//修改ip地址
                }
                break;
        }
    }

    /**
     * 此处到最后为改变头像的代码，从手机内存中选取获取用拍照获取
     */

	/* 头像文件 */
    public static final String IMAGE_FILE_NAME = "temp_head_image111.JPEG";

    /* 请求识别码 */
    public static final int CODE_GALLERY_REQUEST = 0xa0;
    public static final int CODE_CAMERA_REQUEST = 0xa1;
    public static final int CODE_RESULT_REQUEST = 0xa2;

    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    private static int output_X = 480;
    private static int output_Y = 480;

    // 从本地相册选取图片作为头像
    private void choseHeadImageFromGallery() {
        Intent intentFromGallery = new Intent();
        // 设置文件类型
        intentFromGallery.setType("image/*");
        intentFromGallery.setAction(Intent.ACTION_PICK);
        getActivity().startActivityForResult(intentFromGallery,
                CODE_GALLERY_REQUEST);
    }

    // 启动手机相机拍摄照片作为头像
    private void choseHeadImageFromCameraCapture() {
        Intent intentFromCapture = new Intent(
                "android.media.action.IMAGE_CAPTURE");

        // 判断存储卡是否可用，存储照片文件
        if (hasSdcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                    .fromFile(new File(Environment
                            .getExternalStorageDirectory(), IMAGE_FILE_NAME)));
        }

        getActivity().startActivityForResult(intentFromCapture,
                CODE_CAMERA_REQUEST);
    }

    // 定义一个file变量，用于将图片变成文件
    File file;

    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    public void setImageToHeadView(Intent intent) {
        // Bundle extras = intent.getExtras();
        if (intent != null) {
            // Bitmap photo = extras.getParcelable("data");
            // 获得图片photo
            Bitmap photo = intent.getParcelableExtra("data");

            final Dialog dialog = DialogUtils.createWaitingDialog(
                    getActivity(), "设置头像中，请等待");
            // 讲图片变成文件
            saveBitmap(photo);

            // 显示等待框
            dialog.show();
            final String username = PreferencesUtils.getString(getActivity(),
                    "username");

        } else {
            Log.e("xpro", "intent is null");
        }
    }

    /**
     * 讲图片保存为文件
     *
     * @param bitmap
     */

    public void saveBitmap(Bitmap bitmap) {

        file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/temp_head_image111.JPEG");// 将要保存图片的路径
        Log.e("file", "file路径：" + file);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 讲图片变成数组
     *
     * @param bitmap
     */
    public byte[] bitmapBytes(Bitmap bitmap) {
        // file = new
        // File(Environment.getExternalStorageDirectory().getAbsolutePath(),
        // "temp_head_image.jpg");//将要保存图片的路径
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        // BufferedOutputStream bos = new BufferedOutputStream(new
        // FileOutputStream(file));
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        try {
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] x = bos.toByteArray();
        return x;
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        // 有存储的SDCard
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    private EditText dialog_changePwd_oldPwd_edittext,
            dialog_changePwd_newPwd_edittext, changePwd_comfirmPwd_edittext;
    private TextView dialog_changePwd_cancel, dialog_changePwd_comfirm;

    /**
     * 修改号码的dialog
     */
    private EditText dialog_changeNum_edittext,
            dialog_changeNum_comfirmNum_edittext;
    private TextView dialog_changeNum_cancel, dialog_changeNum_comfirm;

    public void busChooseDialogNum(
            final BaseActivity.OnCustomDialogConfirmListener callBack) {
        final Dialog customDialog = new CustomDialog1(getActivity(),
                R.style.CustomDialogTheme, R.layout.dialog_changenum);
        customDialog.show();
        dialog_changeNum_cancel = (TextView) customDialog
                .findViewById(R.id.dialog_changeNum_cancel);
        dialog_changeNum_comfirm = (TextView) customDialog
                .findViewById(R.id.dialog_changeNum_comfirm);
        dialog_changeNum_edittext = (EditText) customDialog
                .findViewById(R.id.dialog_changeNum_edittext);
        dialog_changeNum_comfirmNum_edittext = (EditText) customDialog
                .findViewById(R.id.dialog_changeNum_comfirmNum_edittext);

        // 取消事件
        dialog_changeNum_comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                callBack.onClick();
            }
        });

        dialog_changeNum_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
    }

    /**
     * 更改性别
     */

    private void getPhoto_sex() {
        LinearLayout lyDlg;
        Button btnChooseFemale, btnChooseMale, btnCancel;
        choosePicDialog = new CustomDialog(getActivity(), true);
        choosePicDialog.setCustomView(R.layout.dlg_choose_pic);
        Window window = choosePicDialog.getDialog().getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lyDlg = (LinearLayout) choosePicDialog.findViewById(R.id.dialog_layout);
        lyDlg.setPadding(0, 0, 0, 0);
        btnChooseFemale = (Button) choosePicDialog
                .findViewById(R.id.btnTakePhoto);
        btnChooseMale = (Button) choosePicDialog.findViewById(R.id.btnAlbum);
        btnCancel = (Button) choosePicDialog.findViewById(R.id.btnCancel);
        btnChooseFemale.setText("女");
        btnChooseMale.setText("男");
        // 女
        btnChooseFemale.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sexTextView.setText("女");
                // updateUserInfo(person);// ?
                // Log.e("UpdatePerson", person+"");
                choosePicDialog.dismiss();
            }
        });
        // 男
        btnChooseMale.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sexTextView.setText("男");
                // updateUserInfo(person);// ?
                choosePicDialog.dismiss();
            }
        });
        // 取消
        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicDialog.dismiss();
            }
        });
        choosePicDialog.setCancelable(true);
        choosePicDialog.show();
    }

    /**
     * 更换头像
     */
    private void getPhoto() {
        LinearLayout lyDlg;
        Button btnTakePhoto, btnAlbum, btnCancel;

        choosePicDialog = new CustomDialog(getActivity(), true);
        choosePicDialog.setCustomView(R.layout.dlg_choose_pic);

        Window window = choosePicDialog.getDialog().getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        lyDlg = (LinearLayout) choosePicDialog.findViewById(R.id.dialog_layout);
        lyDlg.setPadding(0, 0, 0, 0);

        btnTakePhoto = (Button) choosePicDialog.findViewById(R.id.btnTakePhoto);
        btnAlbum = (Button) choosePicDialog.findViewById(R.id.btnAlbum);
        btnCancel = (Button) choosePicDialog.findViewById(R.id.btnCancel);

        // 拍照
        btnTakePhoto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicDialog.dismiss();
                File outputImage = new File(Environment
                        .getExternalStorageDirectory(), "tempImage.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageUri = Uri.fromFile(outputImage);
                Intent intent_pz = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 下面这句指定调用相机拍照后的照片存储的路径
                intent_pz.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent_pz, PIC_TAKEPHOTO);
            }
        });

        // 相册
        btnAlbum.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicDialog.dismiss();
                File outputImage = new File(Environment
                        .getExternalStorageDirectory(), "tempImage.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageUri = Uri.fromFile(outputImage);
                Intent intent_xc = new Intent(Intent.ACTION_PICK, null);
                // intent_xc.setType("image/*");
                // intent_xc.putExtra("crop", true);
                // intent_xc.putExtra("scale", true);
                intent_xc
                        .setDataAndType(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                "image/*");
                intent_xc.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent_xc, PIC_ALBUM);
            }
        });

        // 取消
        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicDialog.dismiss();
            }
        });
        choosePicDialog.setCancelable(true);
        choosePicDialog.show();
    }

    /**
     * 头像的dialog
     */
    private Button btn_carema, btn_moile;

    public void busChooseDialogHeadImage(
            final BaseActivity.OnCustomDialogConfirmListener callBack,
            final BaseActivity.OnCustomDialogConfirmListener callBack1) {
        final Dialog customDialog = new CustomDialog1(getActivity(),
                R.style.CustomDialogTheme, R.layout.dialog_changephoto);
        customDialog.show();
        btn_carema = (Button) customDialog.findViewById(R.id.btn_carema);
        btn_moile = (Button) customDialog.findViewById(R.id.btn_moile);

        // 照相机
        btn_carema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                callBack.onClick();
            }
        });

        // 手机相册
        btn_moile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                callBack1.onClick();
            }
        });
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * 获取文件夹大小
     *
     * @param file File实例
     * @return long
     */
    public static long getFolderSize(java.io.File file) {

        long size = 0;
        try {
            java.io.File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);

                } else {
                    size = size + fileList[i].length();

                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // return size/1048576;
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "B";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    /**
     * 清除缓存
     */
    public Boolean deleteCache(String path) {
        File file = new File(path);
        File[] fileList = file.listFiles();
        if (fileList == null) {
            return true;
        } else {
            for (File f : fileList) {
                f.delete();
            }
            if (0 == getFolderSize(file)) {
                return true;
            } else {
                return false;
            }
        }
    }

    @SuppressWarnings("static-access")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PIC_TAKEPHOTO:// 拍照
                if (resultCode == getActivity().RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    // intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CROP_PHOTO);
                }
                break;
            case PIC_ALBUM:// 相册
                if (resultCode == getActivity().RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    // intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CROP_PHOTO);
                }
                break;
            case CROP_PHOTO:// 裁减
                if (resultCode == getActivity().RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getActivity()
                                .getContentResolver().openInputStream(imageUri));
                        mine_logo.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;

            default:
                break;
        }
    }

    @SuppressWarnings("deprecation")
    public void initPopWindow() {
        WindowManager wm = (WindowManager) this.getActivity().getSystemService(
                Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        // 设置布局

        View contentView = LayoutInflater.from(this.getActivity()).inflate(
                R.layout.popwindow_captain_me, null);
        clear_store = (TextView) contentView.findViewById(R.id.clear_store);
        clear_store.setOnClickListener(this);
        check_update = (TextView) contentView.findViewById(R.id.check_update);
        check_update.setOnClickListener(this);
        about_app = (TextView) contentView.findViewById(R.id.about_app);
        about_app.setOnClickListener(this);

        int px = Dp2Px(getActivity(), 119);
        int wpx = Dp2Px(getActivity(), 100);
        popupWindow = new PopupWindow(contentView, wpx, px);
        popupWindow.setContentView(contentView);
        popupWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED,
                View.MeasureSpec.UNSPECIFIED);
        int i = popupWindow.getContentView().getMeasuredWidth();

        // popupWindow.setBackgroundDrawable(getResources().getDrawable(
        // R.drawable.touming_bg));//
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true); // 设置点击menu以外其他地方以及返回键退出
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(mine_setting, -100, -15);

        backgroundAlpha(0.5f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
    }

    public int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    // 背景Alpha
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow()
                .getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        getActivity().getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getActivity().getWindow().setAttributes(lp);
    }

}