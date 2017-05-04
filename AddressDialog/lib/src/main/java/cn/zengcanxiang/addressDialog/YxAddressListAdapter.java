package cn.zengcanxiang.addressDialog;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * 省份选择适配器
 */
@SuppressWarnings("all")
public class YxAddressListAdapter extends RecyclerView.Adapter<YxAddressListAdapter.AddressViewHolder> {

    private List mList;
    private int mType;
    private Context mContext;
    private YxAddressModel mModel;

    private OnItemClickListener mOnItemClickListener;
    public int onItemOnClick = -1;

    public YxAddressListAdapter(List data, YxAddressModel model, int type, Context context) {
        mList = data;
        mModel = model;
        mType = type;
        mContext = context;
    }

    @Override
    public AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AddressViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.adapter_item_address_linkage, parent,
                false));
    }

    @Override
    public void onBindViewHolder(AddressViewHolder holder, final int position) {
        switch (mType) {
            case 0:
                holder.name.setText(mModel.showProvinceName(mList.get(position)));
                break;
            case 1:
                holder.name.setText(mModel.showCityName(mList.get(position)));
                break;
            case 2:
                holder.name.setText(mModel.showDistrictName(mList.get(position)));
                break;
        }

        if (position == onItemOnClick) {
            holder.name.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        } else {
            holder.name.setTextColor(ContextCompat.getColor(mContext, R.color.app_text_default_color));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position, mList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    class AddressViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        AddressViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item_address_linkage_text);
        }
    }

    interface OnItemClickListener {
        void onItemClick(int position, Object item);
    }

}
