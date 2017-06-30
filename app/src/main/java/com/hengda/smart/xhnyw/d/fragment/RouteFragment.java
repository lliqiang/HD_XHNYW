package com.hengda.smart.xhnyw.d.fragment;


import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.listener.OnItemClickListener;
import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.adapter.LCommonAdapter;
import com.hengda.smart.xhnyw.d.adapter.ViewHolder;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;
import com.hengda.smart.xhnyw.d.layoutmanager.ContentManager;
import com.hengda.smart.xhnyw.d.layoutmanager.TitleManager;
import com.hengda.smart.xhnyw.d.model.RouteBean;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class RouteFragment extends SupportFragment {
    private RouteBean.MsgBean msgBean;

    private RecyclerView mRecyclerView;
    private ImageView imgFolder;
    private ImageView dotImg;
    private TextView tvFolder;
    private boolean flag;
    private boolean itemFlag;
    private BaseItemAdapter itemAdapter;
    List<Object> list = new ArrayList<>();

    public RouteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        msgBean = (RouteBean.MsgBean) getArguments().getSerializable("msgBean");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route, container, false);
        initView(view);
        itemAdapter = new BaseItemAdapter();
        itemAdapter.register(RouteBean.MsgBean.ListBean.class, new TitleManager());
        itemAdapter.register(RouteBean.MsgBean.ListBean.ExhibitArrBean.class, new ContentManager(getActivity()));
        mRecyclerView.setAdapter(itemAdapter);
        for (int i = 0; i < msgBean.getList().size(); i++) {
            list.add(msgBean.getList().get(i));
            for (int j = 0; j < msgBean.getList().get(i).getExhibit_arr().size(); j++) {
                list.add(msgBean.getList().get(i).getExhibit_arr().get(j));
            }
        }
        itemAdapter.setDataItems(list);
        imgFolder.setOnClickListener(v -> {
            if (flag) {
                imgFolder.setImageResource(R.mipmap.img_up_fold);
                tvFolder.setVisibility(View.VISIBLE);
                dotImg.setImageResource(R.mipmap.img_dot_light);
            } else {
                imgFolder.setImageResource(R.mipmap.img_down_expand);
                tvFolder.setVisibility(View.GONE);
                dotImg.setImageResource(R.mipmap.img_dot_weight);
            }
            flag = !flag;
        });
        return view;
    }

    private void initView(View view) {
        ((TextView) view.findViewById(R.id.tv_route_tilte)).setText(msgBean.getRoad_title());
        ((TextView) view.findViewById(R.id.tv_time_route)).setText(msgBean.getRoad_time());
        ((TextView) view.findViewById(R.id.tv_count_routee)).setText(msgBean.getTotal_num());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_route);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false));
        imgFolder = (ImageView) view.findViewById(R.id.iv_fold_route);
        dotImg = (ImageView) view.findViewById(R.id.dot_route_intro);
        tvFolder = (TextView) view.findViewById(R.id.tv_descript_route);
        tvFolder.setText(msgBean.getRoad_description());
    }

    public static RouteFragment newInstance(RouteBean.MsgBean msgBean) {
        RouteFragment fragment = new RouteFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("msgBean", msgBean);
        fragment.setArguments(bundle);
        return fragment;
    }
}
