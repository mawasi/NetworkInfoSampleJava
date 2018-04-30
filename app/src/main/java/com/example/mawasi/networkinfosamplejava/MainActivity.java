package com.example.mawasi.networkinfosamplejava;

import android.net.Network;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Build;
import android.view.View;
import android.widget.TextView;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.List;
import java.util.ArrayList;

/*
参考
http://www.programing-style.com/android/android-api/android-button-click/
https://qiita.com/HideMatsu/items/2e6caec8265bcf2a2dcb
*/

public class MainActivity extends AppCompatActivity {

    private TextView _TextView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _TextView = findViewById(R.id.textView);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // NetworkInfoを取得
        DispNetworkInfo();
    }

    // NetworkInfoを取得の表示
    private void DispNetworkInfo()
    {
        ConnectivityManager connectivity = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivity.getActiveNetworkInfo();
        StringBuilder builder = new StringBuilder();
        builder.append("ActiveConnectType : " + activeNetworkInfo.getTypeName() + "\n");

        List<NetworkInfo> mobileinfos = GetNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        for (int i = 0; i < mobileinfos.size(); i++)
        {
            builder.append("Mobile[" + i + "] Type : " + mobileinfos.get(i).getTypeName() + "\n");
            builder.append("Mobile[" + i + "] IsAvailable : " + mobileinfos.get(i).isAvailable() + "\n");
            builder.append("Mobile[" + i + "] IsConnected : " + mobileinfos.get(i).isConnectedOrConnecting() + "\n");
        }

        _TextView.setText(new String(builder));
    }

    // ボタンクリックで呼ばれるやつ
    public void OnDispNetworkInfo(View view)
    {
        DispNetworkInfo();
    }

    // NetworkInfoを取得
    // 5.1以上は取れるだけ返す
    private List<NetworkInfo> GetNetworkInfo(int networkType)
    {
        List<NetworkInfo> infos = new ArrayList<NetworkInfo>();

        ConnectivityManager connectivity = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);

        // OS version が5.1以上向け
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1)
        {
            Network[] networks = connectivity.getAllNetworks();
            for (int i = 0; i < networks.length; i++)
            {
                NetworkInfo networkinfo = connectivity.getNetworkInfo(networks[i]);
                //		if (networkinfo.Type == ConnectivityManager.TYPE_MOBILE)
                {
                    infos.add(networkinfo);
                }
            }
        }
        else
        {
            // Lollipop以前の端末はこっちの処理
            NetworkInfo MobileInfo = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            infos.add(MobileInfo);
        }

        return infos;
    }
}
