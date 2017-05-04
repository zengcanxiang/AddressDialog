package cn.zengcanxiang.sample;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import cn.zengcanxiang.addressDialog.YxAddressLinkageDialog;


public class MainActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void showDialog(View view) {
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
}
