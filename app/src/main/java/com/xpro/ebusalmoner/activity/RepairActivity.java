package com.xpro.ebusalmoner.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.xpro.ebusalmoner.R;
import com.xpro.ebusalmoner.baseapi.BaseActivity;
import com.xpro.ebusalmoner.entity.Breakdown;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;


/**
 * 救济-->故障tab页
 *
 * @author huangjh
 */
@ContentView(R.layout.activity_breakdown)
public class RepairActivity extends BaseActivity {
    @ViewInject(R.id.back)
    private Button back;
    @ViewInject(R.id.breakList)
    private ListView breakList;

    private List<Breakdown> data;
    private ArrayAdapter<Breakdown> adapter;

    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setTitle("故障申请");
        back.setVisibility(View.GONE);

        initData();
        adapter = new ArrayAdapter<Breakdown>(this, R.layout.break_dowm_item, data);
        breakList.setAdapter(adapter);

    }

    public void initData() {
        data = new ArrayList<Breakdown>();
        data.add(new Breakdown("21路故障", "2012.09.17", "JN 0039", "江苏省南京市江宁区将军大道"));
        data.add(new Breakdown("21路故障", "2012.09.17", "JN 0039", "江苏省南京市江宁区将军大道"));
        data.add(new Breakdown("21路故障", "2012.09.17", "JN 0039", "江苏省南京市江宁区将军大道"));
        data.add(new Breakdown("21路故障", "2012.09.17", "JN 0039", "江苏省南京市江宁区将军大道"));
//		data.add(new Breakdown("21路故障", "JN 0039", "江苏省南京市江宁区将军大道","2012.09.17"));
//		data.add(new Breakdown("21路故障","JN 0039", "江苏省南京市江宁区将军大道", "2012.09.17"));
//		data.add(new Breakdown("21路故障", "JN 0039", "江苏省南京市江宁区将军大道","2012.09.17"));
//		data.add(new Breakdown("21路故障", "JN 0039", "江苏省南京市江宁区将军大道","2012.09.17"));
//		data.add(new Breakdown("21路故障", "JN 0039", "江苏省南京市江宁区将军大道","2012.09.17"));
    }

}
