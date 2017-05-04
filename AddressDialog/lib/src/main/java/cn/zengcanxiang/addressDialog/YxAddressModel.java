package cn.zengcanxiang.addressDialog;

import java.util.List;

/**
 * 省市区选择数据model
 */
public interface YxAddressModel<P, C, D> {

    /**
     * 提供省份数据
     */
    List<P> getProvince();

    /**
     * 提供选择省份之后的城市数据
     */
    List<C> getCity(P province);

    /**
     * 提供选择城市之后的区县数据
     */
    List<D> getDistrict(C city);

    /**
     * 显示的省份名称
     */
    String showProvinceName(P province);

    /**
     * 显示的城市名称
     */
    String showCityName(C city);

    /**
     * 显示的区县名称
     */
    String showDistrictName(D district);

    /**
     * 获取选择的数据
     */
    String getSelect(P province, C city, D district);
}
