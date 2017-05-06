package cn.zengcanxiang.addressDialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;


public class SFAddressLinkageDialog extends Dialog {
    public SFAddressLinkageDialog(@NonNull Context context) {
        super(context);
    }

    public SFAddressLinkageDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected SFAddressLinkageDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
