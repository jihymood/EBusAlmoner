package com.xpro.ebusalmoner.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.xpro.ebusalmoner.R;
import com.xpro.ebusalmoner.adapter.MessageAdapter_S;
import com.xpro.ebusalmoner.baseapi.BaseFragment;
import com.xpro.ebusalmoner.entity.MessageDaily;

import java.util.ArrayList;
import java.util.List;


/**
 * 实施人员消息
 *
 * @author huangjh
 */
public class MessageFragment_S extends BaseFragment {
    private View view;
    private ListView listView;
    private MessageAdapter_S adapter;
    private List<MessageDaily> data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.fragment_message_msg_s, null);

        initData();
        initView();

        return view;
    }

    public void initData() {
        data = new ArrayList<MessageDaily>();
        data.add(new MessageDaily("今日暴风暴雪，请注意防寒保暖", "2015-12-20 15:22"));
        data.add(new MessageDaily("今日暴风暴雪，请注意防寒保暖", "2016-09-17 08:00"));
        data.add(new MessageDaily("今日暴风暴雪，请注意防寒保暖", "2016-09-17 08:00"));
        data.add(new MessageDaily("今日世界末日，请于13：00登陆诺亚方舟", "2016-09-17 08:00"));
        data.add(new MessageDaily("今天雾霾，出门请带口罩", "2016-12-20 15:22"));

    }

    public void initView() {
        listView = (ListView) view.findViewById(R.id.listView);
        adapter = new MessageAdapter_S(data, getActivity());
        listView.setAdapter(adapter);
//		listView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				// TODO Auto-generated method stub
//				startActivity(new Intent(getActivity(), HistoryInfoActivity.class));
//			}
//			
//		});
    }


}
