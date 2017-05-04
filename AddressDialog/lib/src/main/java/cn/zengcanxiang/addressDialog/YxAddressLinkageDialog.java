package cn.zengcanxiang.addressDialog;

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

/**
 * 仿网易严选省市区地址选择dialog
 */
@SuppressWarnings("all")
public class YxAddressLinkageDialog extends Dialog {

    private YxAddressModel mDataModel;

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
     * 记录现在显示的位置类型
     */
    private int isShowType = 0;

    /**
     * 记录显示的数据源
     */
    private List mProvinces = new ArrayList<>(), mCities = new ArrayList<>(), mDistricts = new ArrayList<>();

    /**
     * 记录显示的adapter
     */
    private YxAddressListAdapter mProvinceAdapter, mCityAdapter, mDistrictAdapter;

    public YxAddressLinkageDialog(Context context, YxAddressModel model) {
        super(context, R.style.LinkageDialog);
        setContentView(R.layout.view_popup_address_linkage);
        this.mDataModel = model;
        this.mProvinces = mDataModel.getProvince();
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
        addressRecyclerView.setAdapter(mProvinceAdapter);
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
        addressRecyclerView = (RecyclerView) findViewById(R.id.view_address_list);
        magicIndicator = (MagicIndicator) findViewById(R.id.view_address_magic_indicator);
        confirmBtn = (TextView) findViewById(R.id.view_address_confirm);
        confirmBtn.setEnabled(false);
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
                if (!isCanTitleClick(index)) {
                    simplePagerTitleView.setNormalColor(ContextCompat.getColor(getContext(), titleTextColorNo));
                    simplePagerTitleView.setSelectedColor(ContextCompat.getColor(getContext(), titleTextColorNo));
                } else {
                    simplePagerTitleView.setNormalColor(ContextCompat.getColor(getContext(), titleTextColor));
                    simplePagerTitleView.setSelectedColor(ContextCompat.getColor(getContext(), titleTextColor));
                }

                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //切换
                        if (!isCanTitleClick(index)) {
                            return;
                        }
                        switchDataList(index);
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
        mDistrictAdapter = new YxAddressListAdapter(mDistricts, mDataModel, 2, getContext());
        mDistrictAdapter.setOnItemClickListener(new YxAddressListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object item) {
                mDistrictAdapter.onItemOnClick = position;
                titleValue.set(isShowType, mDataModel.showDistrictName(item));
                modifyTitleValue(isShowType);
                mDistrictAdapter.notifyDataSetChanged();
                confirmBtn.setEnabled(true);
                confirmBtn.setTextColor(ContextCompat.getColor(getContext(), titleTextColor));
            }
        });
    }

    private void initCityAdapter() {
        mCityAdapter = new YxAddressListAdapter(mCities, mDataModel, 1, getContext());

        mCityAdapter.setOnItemClickListener(new YxAddressListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object item) {
                mCityAdapter.onItemOnClick = position;
                titleValue.set(isShowType, mDataModel.showCityName(item));
                modifyTitleValue(isShowType);
                mDistricts = mDataModel.getDistrict(item);
                initDistrictAdapter();
                switchDataList(isShowType + 1);
                confirmBtn.setEnabled(false);
                confirmBtn.setTextColor(ContextCompat.getColor(getContext(), titleTextColorNo));
            }
        });
    }

    private void initProvinceAdapter() {
        mProvinceAdapter = new YxAddressListAdapter(mProvinces, mDataModel, 0, getContext());
        mProvinceAdapter.setOnItemClickListener(new YxAddressListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object item) {
                mProvinceAdapter.onItemOnClick = position;
                titleValue.set(isShowType, mDataModel.showProvinceName(item));
                modifyTitleValue(isShowType);
                mCities = mDataModel.getCity(item);
                initCityAdapter();
                if (isShowType == 2) {
                    mDistricts.clear();
                    mDistrictAdapter.notifyDataSetChanged();
                }
                switchDataList(isShowType + 1);
                confirmBtn.setEnabled(false);
                confirmBtn.setTextColor(ContextCompat.getColor(getContext(), titleTextColorNo));
            }
        });
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
     * 切换数据显示
     */
    private void switchDataList(int newShowType) {
        if (isShowType == newShowType) {
            return;
        }
        isShowType = newShowType;
        mFragmentContainerHelper.handlePageSelected(newShowType);
        if (isShowType == 0) {
            addressRecyclerView.setAdapter(mProvinceAdapter);
        } else if (isShowType == 1) {
            addressRecyclerView.setAdapter(mCityAdapter);
            mCityAdapter.notifyDataSetChanged();
        } else if (isShowType == 2) {
            addressRecyclerView.setAdapter(mDistrictAdapter);
            mDistrictAdapter.notifyDataSetChanged();
        }
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
}
