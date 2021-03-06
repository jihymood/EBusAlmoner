package com.xpro.ebusalmoner.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xpro.ebusalmoner.R;
import com.xpro.ebusalmoner.baseapi.BaseActivity.OnCustomDialogConfirmListener;
import com.xpro.ebusalmoner.entity.HistoryData_M;
import com.xpro.ebusalmoner.widget.CustomDialog1;

import java.util.List;


public class HistoryAdapter extends BaseAdapter {

    private List<HistoryData_M> list;
    private Context context;
    private LayoutInflater inflater;

    public HistoryAdapter(List<HistoryData_M> list, Context context) {
        super();
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        HistoryData_M message = (HistoryData_M) getItem(position);
        final String tel = message.getDriverTel();
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.break_dowm_item, null);
//			convertView=inflater.inflate(R.layout.message_item, null);
            holder.line = (TextView) convertView.findViewById(R.id.textView);
            holder.plateNumber = (TextView) convertView.findViewById(R.id.numberText);
            holder.time = (TextView) convertView.findViewById(R.id.timeText);
            holder.telImg = (ImageView) convertView.findViewById(R.id.telImg);
//			holder.address=(TextView) convertView.findViewById(R.id.address);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.line.setText(message.getLineName() + "故障");
        holder.plateNumber.setText(message.getCode());
        holder.time.setText(message.getHitchTime());
        holder.telImg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showConfirmTip("确定拨打电话", new OnCustomDialogConfirmListener() {

                    @Override
                    public void onClick() {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + tel));
                        context.startActivity(intent);
                    }
                }, new OnCustomDialogConfirmListener() {

                    @Override
                    public void onClick() {
                        // TODO Auto-generated method stub

                    }
                });
            }
        });
//		if((message.getState()+"").equals("0")){
//			holder.state.setText("已维修");
//			holder.state.setTextColor(color.new_green);
//		}else if((message.getState()+"").equals("1")){
//			holder.state.setText("已取消");
//			holder.state.setTextColor(Color.parseColor("#ff6700"));
//		}else{
//			holder.state.setText("进场维修");
//			holder.state.setTextColor(Color.parseColor("#ff6700"));
//		}
//		holder.address.setText(message.getAddress());

        return convertView;
    }

    class ViewHolder {
        TextView line;
        TextView plateNumber;
        TextView time;
        ImageView telImg;
//		TextView state;
//		TextView address;
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
        final Dialog customDialog = new CustomDialog1(context,
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

}
