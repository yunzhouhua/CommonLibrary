package com.yunzhou.libcommon.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.yunzhou.libcommon.utils.StringUtils;

/**
 * 网络链接状态工具类
 * Created by huayunzhou on 2017/9/25.
 */

public class NetStatusUtils {

    private static final String TAG = "NetStatusUtils";


    /**
     * 获取网络链接类型
     * {@link android.Manifest.permission#ACCESS_NETWORK_STATE}
     * @param context
     * @return
     */
    public static String getConnectionType(Context context){
        if(context == null){
            Log.e(TAG, "Context can't be null！");
            return StringUtils.EMPTY;
        }
        String netConnectionType = "";
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
                netConnectionType = "WIFI";
            }else if(networkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
                String strSubTypeName = networkInfo.getSubtypeName();
                //TD-SCDMA  networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        netConnectionType = "2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        netConnectionType = "3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        netConnectionType = "4G";
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (strSubTypeName.equalsIgnoreCase("TD-SCDMA") ||
                                strSubTypeName.equalsIgnoreCase("WCDMA") ||
                                strSubTypeName.equalsIgnoreCase("CDMA2000"))
                        {
                            netConnectionType = "3G";
                        }
                        else
                        {
                            netConnectionType = strSubTypeName;
                        }

                        break;
                }
            }
        }
        return netConnectionType;
    }
}
