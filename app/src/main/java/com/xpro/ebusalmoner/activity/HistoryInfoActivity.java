package com.xpro.ebusalmoner.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.xpro.ebusalmoner.R;
import com.xpro.ebusalmoner.adapter.HistoryAdapter;
import com.xpro.ebusalmoner.baseapi.BaseActivity;
import com.xpro.ebusalmoner.baseapi.BaseHandler;
import com.xpro.ebusalmoner.constants.Constants;
import com.xpro.ebusalmoner.constants.HttpUrls;
import com.xpro.ebusalmoner.entity.HistoryBody_M;
import com.xpro.ebusalmoner.entity.HistoryData_M;
import com.xpro.ebusalmoner.entity.HistoryImages_driver_M;
import com.xpro.ebusalmoner.entity.HistoryImages_trailer_M;
import com.xpro.ebusalmoner.entity.HistoryInfoData;
import com.xpro.ebusalmoner.entity.HistoryRoot_M;
import com.xpro.ebusalmoner.logic.TaskLogic;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 救济-->历史详情页
 *
 * @param <ImageControlView>
 * @author huangjh
 */
public class HistoryInfoActivity<ImageControlView> extends BaseActivity
        implements OnClickListener {
    private TextView locationText, lineText, driverNameText,
            cause_driverText, stateText, cause_trailerText,
            lineTimeText, numberText;
    private ImageView back;
    private ImageControlView imgControl;
    private ImageView driverTel;
    private LinearLayout breakdown_history, linear_driver, linear_trailer;
    private int state;
    private HistoryAdapter adapter1;
    private List<HistoryData_M> data;
    private List<HistoryData_M> data0;// 已完成
    private List<HistoryData_M> data1;// 待完成
    private List<HistoryData_M> data2;// 待完成
    private String lineStr, numberStr, nameStr,
            telStr, timeStr, latStr, lngStr,
            causeTrailerStr, causeDriverStr, faultId;
    private String cenpt1Addr = "";
    private List<HistoryImages_driver_M> imagesDriverList;
    private List<HistoryImages_trailer_M> imagesTrailerList;

    private MyHandler handler;
    private TaskLogic logic;

    private String json = "{\"success\": \"true\", "
            + "\"body\": [{\"id\": \"100\", "
            + "\"plateNumber\": \"1号拖车\",\"hitchLatitude\": "
            + "\"31.9813440513\", \"hitchLongitude\": \"118.7626883184\", "
            + "\"driverName\": \"科比\", \"driverTel\": \"18769029715\", "
            + "\"line\": \"108路\",\"state\":0,\"images_driver\":[{\"url\": \"http://www.7lili" +
            ".com/uploads/allimg/140915/0114512925-0.jpg\"},"
            + "{\"url\": \"http://img5.duitang.com/uploads/item/201406/17/20140617140412_JKnZU.thumb.700_0.jpeg\"}],"
            + "\"cause\":\"轮胎爆破\",\"images_trailer\":[{\"url\": \"http://img5.duitang" +
            ".com/uploads/item/201406/17/20140617140412_JKnZU.thumb.700_0.jpeg\"},"
            + "{\"url\": \"http://img5.duitang.com/uploads/item/201406/17/20140617140412_JKnZU.thumb.700_0.jpeg\"}],"
            + "\"hitchTime\": \"2013.02.12\",\"number\": \"JN-0039\"},{\"id\": \"100\", "
            + "\"plateNumber\": \"1号拖车\",\"hitchLatitude\": "
            + "\"31.9813440513\", \"hitchLongitude\": \"118.7626883184\", "
            + "\"driverName\": \"科比\", \"driverTel\": \"18769029715\", "
            + "\"line\": \"118路\",\"state\":1,\"images_driver\":[{\"url\": \"https://img3.doubanio.com/lpic/s1747553" +
            ".jpg\"},"
            + "{\"url\": \"http://img5.duitang.com/uploads/item/201406/17/20140617140412_JKnZU.thumb.700_0.jpeg\"}],"
            + "\"cause\":\"轮胎爆破\",\"images_trailer\":[{\"url\": \"http://img5.duitang" +
            ".com/uploads/item/201406/17/20140617140412_JKnZU.thumb.700_0.jpeg\"},"
            + "{\"url\": \"http://img5.duitang.com/uploads/item/201406/17/20140617140412_JKnZU.thumb.700_0.jpeg\"}],"
            + "\"hitchTime\": \"2014.02.12\",\"number\": \"JN-0039\"},{\"id\": \"100\", "
            + "\"plateNumber\": \"1号拖车\",\"hitchLatitude\": "
            + "\"31.9813440513\", \"hitchLongitude\": \"118.7626883184\", "
            + "\"driverName\": \"科比\", \"driverTel\": \"18769029715\", "
            + "\"line\": \"128路\",\"state\":2,\"images_driver\":[{\"url\": \"http://c.hiphotos.baidu" +
            ".com/image/pic/item/a5c27d1ed21b0ef459c93d5edfc451da81cb3e88.jpg\"},"
            + "{\"url\": \"http://img5.duitang.com/uploads/item/201406/17/20140617140412_JKnZU.thumb.700_0.jpeg\"}],"
            + "\"cause\":\"轮胎爆破\",\"images_trailer\":[{\"url\": \"http://img5.duitang" +
            ".com/uploads/item/201406/17/20140617140412_JKnZU.thumb.700_0.jpeg\"},"
            + "{\"url\": \"http://img5.duitang.com/uploads/item/201406/17/20140617140412_JKnZU.thumb.700_0.jpeg\"}],"
            + "\"hitchTime\": \"2015.02.12\",\"number\": \"JN-0039\"}], \"errorCode\": \"-1\",\"msg\": \"成功\"}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakdown_history);

        Log.e("HistoryInfoActivity", json);

        initView();
        getFromIntent();

        handler = new MyHandler(this);
        logic = new TaskLogic(handler, this);
        logic.faultDetail(faultId);


    }

    private void getFromIntent() {
        // TODO Auto-generated method stub
        Intent intent = getIntent();
        if (null != intent) {
            Bundle bundle = intent.getExtras();
            HistoryData_M subData = (HistoryData_M) bundle.get("HistoryData_M");
            if (subData != null) {
                faultId = subData.getId();
                lineStr = subData.getLineName();
                numberStr = subData.getCode();
                nameStr = subData.getDriverName();
                telStr = subData.getDriverTel();
                timeStr = subData.getHitchTime();
                causeDriverStr = subData.getCause();
                latStr = subData.getHitchLatitude();
                lngStr = subData.getHitchLongitude();
                state = subData.getState();
                imagesDriverList = subData.getImages_driver();
                imagesTrailerList = subData.getImages_trailer();
                Log.e("HistoryActivity", "subData" + subData.toString());


                lineText.setText(lineStr);
                numberText.setText(numberStr);
                driverNameText.setText("驾驶员：" + nameStr);
                lineTimeText.setText(timeStr);
                cause_driverText.setText(causeDriverStr);
                if ("1".equals(state + "")) {
                    stateText.setText("已修好");
                } else if ("2".equals(state + "")) {
                    stateText.setText("进场维修");
                } else if("".equals(state+"")){
                    stateText.setText("已取消");
                }

                LatLng latLng = new LatLng(Double.parseDouble(latStr), Double.parseDouble(lngStr));
                reverseGeoCode(latLng);

//                setImage();

            }
        }
    }

/*    *//**
     * 动态添加图片
     *//*
    public void setImage(List<String> list) {
        final ImageOptions options = new ImageOptions.Builder()
                // 设置加载过程中的图片
                // .setLoadingDrawableId(R.drawable.ic_launcher)
                // 设置加载失败后的图片
                .setFailureDrawableId(R.mipmap.logo)
                // 设置使用缓存
                .setUseMemCache(true)
                // 设置显示圆形图片
                .setCircular(true)
                // 设置支持gif
                .setIgnoreGif(false).build();

        for (int j = 0; j < imagesDriverList.size(); j++) {
            ImageView imageView = new ImageView(this);
            imageView.setPadding(10, 10, 10, 10);
            final String url = imagesDriverList.get(j).getUrl();
            x.image().bind(imageView, url, options);
            linear_driver.addView(imageView);

            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    initPopupWindow(breakdown_history, url);
                }
            });
        }
        for (int k = 0; k < imagesTrailerList.size(); k++) {
            ImageView imageView = new ImageView(this);
            imageView.setPadding(10, 10, 10, 10);
            final String url = imagesDriverList.get(k).getUrl();
            x.image().bind(imageView, url, options);
            linear_trailer.addView(imageView);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    initPopupWindow(breakdown_history, url);
                }
            });
        }
    }*/

    /**
     * 动态添加图片
     */
    public void setImage(List<String> list) {
        final ImageOptions options = new ImageOptions.Builder()
                // 设置加载过程中的图片
                // .setLoadingDrawableId(R.drawable.ic_launcher)
                // 设置加载失败后的图片
                .setFailureDrawableId(R.mipmap.logo)
                // 设置使用缓存
                .setUseMemCache(true)
                // 设置显示圆形图片
//                .setCircular(true)
                //设置图片大小
                .setSize(150,100)
                // 设置支持gif
                .setIgnoreGif(false).build();

        for (int k = 0; k < list.size(); k++) {
            ImageView imageView = new ImageView(this);
            imageView.setPadding(10, 10, 10, 10);
            final String url = list.get(k);
            x.image().bind(imageView, url, options);
            linear_trailer.addView(imageView);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    initPopupWindow(breakdown_history, url);
                }
            });
        }
    }

    public void initView() {

        lineText = (TextView) findViewById(R.id.line);
        numberText = (TextView) findViewById(R.id.lineNumber);
        driverNameText = (TextView) findViewById(R.id.driverName);
        lineTimeText = (TextView) findViewById(R.id.lineTime);
        locationText = (TextView) findViewById(R.id.location);
        cause_driverText = (TextView) findViewById(R.id.breakText);
        stateText = (TextView) findViewById(R.id.state);
        cause_trailerText = (TextView) findViewById(R.id.cause_trailer);

        back = (ImageView) findViewById(R.id.back);
        driverTel = (ImageView) findViewById(R.id.driverTel);
        linear_driver = (LinearLayout) findViewById(R.id.linear_driver);
        linear_trailer = (LinearLayout) findViewById(R.id.linear_trailer);
        breakdown_history = (LinearLayout) findViewById(R.id.breakdown_history);
        back.setOnClickListener(this);
        driverTel.setOnClickListener(this);
        setTitle("故障详情");
    }

    /**
     * 反地理编码得到地址信息
     *
     * @return
     */
    private void reverseGeoCode(LatLng latLng) {
        // 创建地理编码检索实例
        GeoCoder geoCoder = GeoCoder.newInstance();
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
        geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                // TODO Auto-generated method stub
                if (result == null
                        || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    // 没有检测到结果
                    Toast.makeText(HistoryInfoActivity.this, "抱歉，未能找到结果",
                            Toast.LENGTH_LONG).show();
                }
//				Toast.makeText(HistoryInfoActivity.this, "位置：" + result.getAddress(),
//						Toast.LENGTH_LONG).show();
                cenpt1Addr = result.getAddress();
                locationText.setText(cenpt1Addr);
                Log.e("HistoryInfoActivity", cenpt1Addr);
            }

            @Override
            public void onGetGeoCodeResult(GeoCodeResult result) {
                // TODO Auto-generated method stub
                if (result == null
                        || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    // 没有检测到结果
                }
            }
        });
    }


    /**
     * 测试接口
     */
    public void getData() {
        ImageOptions options = new ImageOptions.Builder()
                // 设置加载过程中的图片
                // .setLoadingDrawableId(R.drawable.ic_launcher)
                // 设置加载失败后的图片
                .setFailureDrawableId(R.mipmap.logo)
                // 设置使用缓存
                .setUseMemCache(true)
                // 设置显示圆形图片
                .setCircular(true)
                // 设置支持gif
                .setIgnoreGif(false).build();

        HistoryRoot_M root = JSON.parseObject(json, HistoryRoot_M.class);
        HistoryBody_M body = root.getBody();
        List<HistoryData_M> dataList = body.getData();
        if (dataList != null && dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                HistoryData_M subData = dataList.get(i);
                List<HistoryImages_driver_M> imagesDriverList = subData
                        .getImages_driver();
                List<HistoryImages_trailer_M> imagesTrailerList = subData
                        .getImages_trailer();
                int state1 = subData.getState();
                if (String.valueOf(state).equals(state1 + "")) { // 状态对应上，就解析那一组的图片
                    for (int j = 0; j < imagesDriverList.size(); j++) {
                        ImageView imageView = new ImageView(this);
                        imageView.setPadding(10, 10, 10, 10);
                        final String url = imagesDriverList.get(j).getUrl();
                        x.image().bind(imageView, url, options);
                        linear_driver.addView(imageView);

                        imageView.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                initPopupWindow(breakdown_history, url);
                            }
                        });
                    }
                    for (int k = 0; k < imagesTrailerList.size(); k++) {
                        ImageView imageView = new ImageView(this);
                        imageView.setPadding(10, 10, 10, 10);
                        final String url = imagesDriverList.get(k).getUrl();
                        x.image().bind(imageView, url, options);
                        linear_trailer.addView(imageView);
                        imageView.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                initPopupWindow(breakdown_history, url);
                            }
                        });
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.driverTel:
                showConfirmTip("确定拨打电话", new OnCustomDialogConfirmListener() {

                    @Override
                    public void onClick() {
                        // TODO Auto-generated method stub
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + telStr));
                        startActivity(intent);
                    }
                }, new OnCustomDialogConfirmListener() {

                    @Override
                    public void onClick() {
                        // TODO Auto-generated method stub

                    }
                });
                break;
            default:
                break;
        }
    }

    public class MyHandler extends BaseHandler {

        public MyHandler(Context context) {
            super(context);
        }

        @Override
        public void doHandle(Message msg) {
            super.doHandle(msg);
            switch (msg.what) {
                //获取历史详情成功
                case Constants.FAULTDETAIL_SUCCESS:
                    HistoryInfoData data = (HistoryInfoData) msg.obj;
                    if (data != null) {
                        String reason = data.getReason();
                        String photo = data.getPhotos();
                        String[] photos = photo.split(";");
                        List<String> urlList = new ArrayList<>();
                        for (int i = 0; i < photos.length; i++) {
                            String url = HttpUrls.showImageUrl(HistoryInfoActivity.this) + photos[i];
                            urlList.add(url);
                        }

                        setImage(urlList);
                    }
                    break;
                //获取历史详情失败

            }
        }
    }


}
