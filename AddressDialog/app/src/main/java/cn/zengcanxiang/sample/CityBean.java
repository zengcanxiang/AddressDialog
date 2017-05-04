package cn.zengcanxiang.sample;

/**
 * 城市bean
 */
public class CityBean {
    private int cityId;
    private String cityName;
    private int provinceId;

    public CityBean() {
    }

    public CityBean(int cityId, String cityName, int provinceId) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.provinceId = provinceId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
