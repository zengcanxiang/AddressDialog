package cn.zengcanxiang.sample;

import java.util.ArrayList;
import java.util.List;

import cn.zengcanxiang.addressDialog.Model.NetworkModel;


public class ModelJd implements NetworkModel<ProvinceBean, CityBean, DistrictBean> {

    public List<ProvinceBean> getProvince() {
        List<ProvinceBean> list = new ArrayList<>();
        list.add(new ProvinceBean(1, "北京"));
        list.add(new ProvinceBean(2, "上海"));
        list.add(new ProvinceBean(3, "湖南"));
        return list;
    }

    public List<CityBean> getCity(ProvinceBean province) {
        List<CityBean> list = new ArrayList<>();
        list.add(new CityBean(11, province.getProvinceName() + "城市1", province.getProvinceId()));
        list.add(new CityBean(12, province.getProvinceName() + "城市2", province.getProvinceId()));
        list.add(new CityBean(13, province.getProvinceName() + "城市3", province.getProvinceId()));
        return list;
    }

    public List<DistrictBean> getDistrict(CityBean city) {
        List<DistrictBean> list = new ArrayList<>();
        list.add(new DistrictBean(city.getCityId(), 111, city.getCityName() + "区县1"));
        list.add(new DistrictBean(city.getCityId(), 112, city.getCityName() + "区县2"));
        list.add(new DistrictBean(city.getCityId(), 113, city.getCityName() + "区县3"));
        return list;
    }


    @Override
    public String showProvinceName(ProvinceBean province) {
        return province.getProvinceName();
    }

    @Override
    public String showCityName(CityBean city) {
        return city.getCityName();
    }

    @Override
    public String showDistrictName(DistrictBean district) {
        return district.getDistrictName();
    }

    @Override
    public String getSelect(ProvinceBean province, CityBean city, DistrictBean district) {
        return province.getProvinceName() + city.getCityName() + district.getDistrictName();
    }
}
