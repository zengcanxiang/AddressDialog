package cn.zengcanxiang.addressDialog.jd;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zengcanxiang.baseAdapter.interFace.OnItemClickListener;
import com.zengcanxiang.baseAdapter.recyclerView.BaseViewHolder;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import cn.zengcanxiang.addressDialog.Model.NetworkModel;
import cn.zengcanxiang.addressDialog.R;

/**
 * 仿JD地址选择框(地址数据是网络请求)
 */
public abstract class JdAddressLinkageDialog extends Dialog {

    private NetworkModel mDataModel;

    private LinearLayout mMaskLayer;
    private MagicIndicator magicIndicator;
    private RecyclerView addressRecyclerView;
    private TextView confirmBtn;

    private FragmentContainerHelper mFragmentContainerHelper = new FragmentContainerHelper();
    private CommonNavigatorAdapter navigatorAdapter;
    private CommonNavigator commonNavigator;

    private List<String> titleValue = new ArrayList<>();
    private String proStr = "省份", cityStr = "城市", countyStr = "区县";

    /**
     * 标题最长显示文字长度
     */
    private int titleMaxSize = 5;
    /**
     * 标题文字颜色
     */
    private int titleTextColor = R.color.app_text_default_color;
    /**
     * 没有选择时,标题文字颜色
     */
    private int titleTextColorNo = R.color.app_gray_text;
    /**
     * 标题下方条块颜色
     */
    private int indicatorColor = R.color.colorAccent;

    /**
     * 记录显示的数据源
     */
    private List mProvinces = new ArrayList<>(), mCities = new ArrayList<>(), mDistricts = new ArrayList<>();

    /**
     * 记录显示的adapter
     */
    private JdAddressAdapter mProvinceAdapter, mCityAdapter, mDistrictAdapter;

    private Activity mContext;

    public JdAddressLinkageDialog(Context context, NetworkModel model) {
        super(context, R.style.LinkageDialog);
        setContentView(R.layout.view_jd_popup_address_linkage);
        mContext = (Activity) context;
        this.mDataModel = model;
        titleValue.add(proStr);
        titleValue.add(cityStr);
        titleValue.add(countyStr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        initStyle();
        initView();
        initAdapter();
        startProvince();
    }

    /**
     * 初始化dialog的一些配置、样式
     */
    private void initStyle() {
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        //显示在底部
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 初始化view
     */
    private void initView() {
        mMaskLayer = (LinearLayout) findViewById(R.id.view_address_mask_layer);
        addressRecyclerView = (RecyclerView) findViewById(R.id.view_address_list);
        magicIndicator = (MagicIndicator) findViewById(R.id.view_address_magic_indicator);
        confirmBtn = (TextView) findViewById(R.id.view_address_confirm);
        addressRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        commonNavigator = new CommonNavigator(getContext());
        navigatorAdapter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(titleValue.get(index));
                simplePagerTitleView.setTextSize(13);
                simplePagerTitleView.setMaxEms(titleMaxSize);
                simplePagerTitleView.setSingleLine(true);
                simplePagerTitleView.setMaxLines(1);

                if (isCanTitleClick(index)) {
                    simplePagerTitleView.setNormalColor(ContextCompat.getColor(getContext(), titleTextColor));
                    simplePagerTitleView.setSelectedColor(ContextCompat.getColor(getContext(), titleTextColor));
                } else {
                    simplePagerTitleView.setNormalColor(ContextCompat.getColor(getContext(), titleTextColorNo));
                    simplePagerTitleView.setSelectedColor(ContextCompat.getColor(getContext(), titleTextColorNo));
                }

                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //切换
                        if (isCanTitleClick(index)) {
                            mFragmentContainerHelper.handlePageSelected(index);
                            switch (index) {
                                case 0:
                                    addressRecyclerView.setAdapter(mProvinceAdapter);
                                    break;
                                case 1:
                                    addressRecyclerView.setAdapter(mCityAdapter);
                                    break;
                                case 2:
                                    addressRecyclerView.setAdapter(mDistrictAdapter);
                                    break;
                            }
                        }
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(ContextCompat.getColor(getContext(), indicatorColor));
                return indicator;
            }
        };
        commonNavigator.setAdapter(navigatorAdapter);
        magicIndicator.setNavigator(commonNavigator);
        mFragmentContainerHelper.attachMagicIndicator(magicIndicator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer();
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(getContext(), 10);
            }
        });
        mFragmentContainerHelper.handlePageSelected(0, false);
    }

    /**
     * 初始化adapter
     */
    private void initAdapter() {
        initProvinceAdapter();
        initCityAdapter();
        initDistrictAdapter();
    }

    private void initDistrictAdapter() {
        mDistrictAdapter = new JdAddressAdapter(mDistricts, mDataModel, 2, getContext());
        mDistrictAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder holder, int position, Object item) {
                confirmBtn.setEnabled(true);
                mDistrictAdapter.notifyDataSetChanged();
                titleValue.set(2, mDataModel.showDistrictName(item));
                modifyTitleValue(2);
            }
        });
    }

    private void initCityAdapter() {
        mCityAdapter = new JdAddressAdapter(mCities, mDataModel, 1, getContext());
        mCityAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder holder, int position, Object item) {
                mMaskLayer.setVisibility(View.VISIBLE);
                confirmBtn.setEnabled(false);
                titleValue.set(1, mDataModel.showCityName(item));
                modifyTitleValue(1);
                startDistrict(item);
            }
        });
    }

    private void initProvinceAdapter() {
        mProvinceAdapter = new JdAddressAdapter(mProvinces, mDataModel, 0, getContext());
        mProvinceAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder holder, int position, Object item) {
                mMaskLayer.setVisibility(View.VISIBLE);
                confirmBtn.setEnabled(false);
                titleValue.set(0, mDataModel.showProvinceName(item));
                modifyTitleValue(0);
                startCity(item);
            }
        });
    }

    /**
     * 设置确认点击事件
     */
    public void setConfirmClick(final View.OnClickListener listener) {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mProvinceAdapter.onItemOnClick == -1
                        || mCityAdapter.onItemOnClick == -1
                        || mDistrictAdapter.onItemOnClick == -1) {
                    return;
                }
                dismiss();
                listener.onClick(v);
            }
        };
        confirmBtn.setOnClickListener(onClickListener);
    }

    /**
     * 判断是否能够切换
     */
    private boolean isCanTitleClick(int index) {
        if (index < 1) {
            return true;
        }
        String temp = titleValue.get(index - 1);
        for (String value : titleValue) {
            if (temp.equals(proStr) || temp.equals(cityStr) || temp.equals(countyStr)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 修改标题数据
     */
    private void modifyTitleValue(int isShowType) {
        if (isShowType == 0) {
            titleValue.set(1, cityStr);
            titleValue.set(2, countyStr);
        } else if (isShowType == 1) {
            titleValue.set(2, countyStr);
        }
        navigatorAdapter.notifyDataSetChanged();
    }

    /**
     * 设置省份标题显示str
     */
    public void setProStr(String proStr) {
        this.proStr = proStr;
        titleValue.set(0, proStr);
    }

    /**
     * 设置城市标题显示str
     */
    public void setCityStr(String cityStr) {
        this.cityStr = cityStr;
        titleValue.set(1, cityStr);
    }

    /**
     * 设置区县标题显示str
     */
    public void setCountyStr(String countyStr) {
        this.countyStr = countyStr;
        titleValue.set(2, countyStr);
    }

    /**
     * 设置确认按钮文字
     */
    public void setConfirmText(CharSequence text) {
        confirmBtn.setText(text);
    }

    /**
     * 获取选择的数据
     */
    public String getSelect() {
        return mDataModel.getSelect(mProvinces.get(mProvinceAdapter.onItemOnClick),
                mCities.get(mCityAdapter.onItemOnClick),
                mDistricts.get(mDistrictAdapter.onItemOnClick));
    }

    /**
     * 设置加载view
     */
    public void setLoadView(View view) {
        mMaskLayer.removeAllViews();
        mMaskLayer.addView(view);
    }

    /**
     * 显示区县数据
     */
    public void showDistrict(final List districts) {
        runUiMethod(new Runnable() {
            @Override
            public void run() {
                mMaskLayer.setVisibility(View.GONE);
                mDistrictAdapter.replaceAll(districts);

                mFragmentContainerHelper.handlePageSelected(2);
                addressRecyclerView.setAdapter(mDistrictAdapter);
            }
        });
    }

    /**
     * 显示城市数据
     */
    public void showCity(final List citys) {
        runUiMethod(new Runnable() {
            @Override
            public void run() {
                mMaskLayer.setVisibility(View.GONE);
                mCityAdapter.replaceAll(citys);

                mFragmentContainerHelper.handlePageSelected(1);
                addressRecyclerView.setAdapter(mCityAdapter);
            }
        });
    }

    /**
     * 显示省数据
     */
    public void showProvince(final List provinces) {
        runUiMethod(new Runnable() {
            @Override
            public void run() {
                mMaskLayer.setVisibility(View.GONE);
                mProvinceAdapter.replaceAll(provinces);

                mFragmentContainerHelper.handlePageSelected(0);
                addressRecyclerView.setAdapter(mProvinceAdapter);
            }
        });
    }

    /**
     * 防止在子线程中请求数据，然后更新UI
     */
    private void runUiMethod(Runnable r) {
        mContext.runOnUiThread(r);
    }

    public abstract void startProvince();

    public abstract void startCity(Object province);

    public abstract void startDistrict(Object city);
}
