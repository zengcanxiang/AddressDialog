package cn.zengcanxiang.sample;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import cn.zengcanxiang.addressDialog.jd.JdAddressLinkageDialog;
import cn.zengcanxiang.addressDialog.yx.YxAddressLinkageDialog;


public class MainActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void showDialogLocal(View view) {
        ModelYx model = new ModelYx();
        final YxAddressLinkageDialog dialog = new YxAddressLinkageDialog(this, model);
        dialog.setConfirmClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, dialog.getSelect(), Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    public void showDialogNet(View view) {
        final ModelJd model1 = new ModelJd();
        final JdAddressLinkageDialog dialog1 = new JdAddressLinkageDialog<ProvinceBean, CityBean, DistrictBean>(this, model1) {
            @Override
            public void startProvince() {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        showProvince(model1.getProvince());
                    }
                }.start();
            }

            @Override
            public void startCity(final ProvinceBean province) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        showCity(model1.getCity(province));
                    }
                }.start();
            }

            @Override
            public void startDistrict(final CityBean city) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        showDistrict(model1.getDistrict(city));
                    }
                }.start();
            }
        };
        dialog1.setLoadView(getLayoutInflater().inflate(R.layout.load_view, null));
        dialog1.setTitleMaxSize(10);
        dialog1.setConfirmClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, dialog1.getSelect(), Toast.LENGTH_SHORT).show();
            }
        });
        dialog1.show();
    }
}
