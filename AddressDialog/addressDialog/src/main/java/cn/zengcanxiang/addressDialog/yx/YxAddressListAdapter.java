package cn.zengcanxiang.addressDialog.yx;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.zengcanxiang.baseAdapter.interFace.OnItemClickListener;
import com.zengcanxiang.baseAdapter.recyclerView.BaseViewHolder;
import com.zengcanxiang.baseAdapter.recyclerView.HelperAdapter;
import com.zengcanxiang.baseAdapter.recyclerView.HelperViewHolder;

import java.util.List;

import cn.zengcanxiang.addressDialog.R;

@SuppressWarnings("all")
public class YxAddressListAdapter extends HelperAdapter {

    private int mType;
    private YxAddressModel mModel;
    public int onItemOnClick = -1;

    public YxAddressListAdapter(List data, YxAddressModel model, int type, Context context) {
        super(data, context, R.layout.adapter_item_address_linkage);
        mModel = model;
        mType = type;
    }

    @Override
    protected void HelperBindData(HelperViewHolder viewHolder, int position, Object item) {
        switch (mType) {
            case 0:
                viewHolder.setText(R.id.item_address_linkage_text, mModel.showProvinceName(mList.get(position)));
                break;
            case 1:
                viewHolder.setText(R.id.item_address_linkage_text, mModel.showCityName(mList.get(position)));
                break;
            case 2:
                viewHolder.setText(R.id.item_address_linkage_text, mModel.showDistrictName(mList.get(position)));
                break;
        }

        if (position == onItemOnClick) {
            viewHolder.setTextColor(R.id.item_address_linkage_text, ContextCompat.getColor(mContext, R.color.colorAccent));
        } else {
            viewHolder.setTextColor(R.id.item_address_linkage_text, ContextCompat.getColor(mContext, R.color.app_text_default_color));
        }
    }

    @Override
    public void setOnItemClickListener(final OnItemClickListener listener) {
        OnItemClickListener proxy = new OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder holder, int position, Object item) {
                onItemOnClick = position;
                listener.onItemClick(holder, position, item);
            }
        };
        super.setOnItemClickListener(proxy);
    }
}