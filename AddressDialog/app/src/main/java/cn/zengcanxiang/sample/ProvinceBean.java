package cn.zengcanxiang.sample;

/**
 * 省份bean
 */
public class ProvinceBean {

    private int provinceId;
    private String provinceName;

    public ProvinceBean() {
    }

    public ProvinceBean(int provinceId, String provinceName) {
        this.provinceId = provinceId;
        this.provinceName = provinceName;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
}
