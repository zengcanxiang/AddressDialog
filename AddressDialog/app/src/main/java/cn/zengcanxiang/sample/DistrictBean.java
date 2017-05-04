package cn.zengcanxiang.sample;

/**
 * 区县bean
 */
public class DistrictBean {

    private int cityId;
    private int districtId;
    private String districtName;

    public DistrictBean() {
    }

    public DistrictBean(int cityId, int districtId, String districtName) {
        this.cityId = cityId;
        this.districtId = districtId;
        this.districtName = districtName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }
}
