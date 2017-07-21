package cn.zengcanxiang.addressDialog.Model;


public interface BaseAddressModel<P, C, D> {

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
