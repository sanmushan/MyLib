package com.mylibrary.widget;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by zhumg on 3/23.
 */
public abstract class AfinalAdapter<DATA> extends BaseAdapter implements AdapterView.OnItemClickListener {

    //数据索引起点，如果有设置 headView , 则需要数据索引+1
    protected int index_start = 0;
    //UI类型，支持多少种类型的意思
    protected SparseArray<Integer> view_type_maps = new SparseArray<>();
    //数据
    protected List<DATA> datas;

    protected Context context;

    protected AdapterClickListener<DATA> listener;

    public void setAdapterClickListener(AdapterClickListener<DATA> listener) {
        this.listener = listener;
    }

    public AfinalAdapter(Context context, List<DATA> datas) {
        this.datas = datas;
        this.context = context;
    }

    public void haveHeadView() {
        this.index_start = 1;
    }


    public void addMore(List<DATA> more) {
        this.datas.addAll(more);
    }

    public void add(DATA data) {
        this.datas.add(data);
    }

    public void refresh(List<DATA> models) {
        this.datas.clear();
        this.datas.addAll(models);
    }

    public void remove(int position) {
        this.datas.remove(position);
    }

    public void removeAll() {
        this.datas.clear();
    }

    public List<DATA> getDatas() {
        return this.datas;
    }

    @Override
    public int getCount() {
        return this.datas.size();
    }

    @Override
    public DATA getItem(int position) {
        return this.datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (this.listener != null) {
            int p = position - index_start;
            if (p < 0) {
                return;
            }
            this.listener.onItemClick(parent, this.datas.get(p), p);
        }
    }


}
