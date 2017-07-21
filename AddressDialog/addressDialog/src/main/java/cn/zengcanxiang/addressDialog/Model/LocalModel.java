package cn.zengcanxiang.addressDialog.Model;

import java.util.List;

/**
 * 本地数据model
 */
public interface LocalModel<P, C, D> extends BaseAddressModel<P, C, D> {
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

}
