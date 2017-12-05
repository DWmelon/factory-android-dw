package com.yongheng.network;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.yongheng.MainApplication;
import com.yongheng.R;
import com.yongheng.application.BaseActivity;
import com.yongheng.application.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melon on 2016/8/29.
 */

public class ResponseErrorManager {

    private List<OnResponseErrorListener> listeners = new ArrayList<>();

    public void registerToastShow(OnResponseErrorListener listener) {
        listeners.add(listener);
    }

    public void unregisterToastShow(OnResponseErrorListener listener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    public void handleErrorMsg(String jsonStr) {
        if (TextUtils.isEmpty(jsonStr)) return;
        try {
            JSONObject object = JSONObject.parseObject(jsonStr);
            handleErrorMsg(object);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //先判断code是否存在 存在的话错误码不为0或200则根据错误码处理
    //如果为0或200 则判断data是否存在
    public void handleErrorMsg(JSONObject jsonObj) {
        if (jsonObj == null) return;
        int code;
        String msg;
        if (jsonObj.containsKey(Constant.KEY_CODE)) {
            code = jsonObj.getIntValue(Constant.KEY_CODE);

            if (jsonObj.containsKey(Constant.KEY_DATA) && jsonObj.getString(Constant.KEY_DATA) != null) {
            } else {
                //code = ResultCode.ERROR_RESULT_NONE;
            }
        } else {
            code = ResultCode.ERROR_RESULT_NONE;
        }

        if (jsonObj.containsKey(Constant.KEY_MSG) && jsonObj.getString(Constant.KEY_MSG) != null) {
            msg = jsonObj.getString(Constant.KEY_MSG);
        } else {
            msg = "";
        }

        handleErrorMsg(code, msg);
    }

    public void handleErrorMsg(int code, String msg) {
        if (code != ResultCode.SUCCESS_0 && code != ResultCode.SUCCESS_200) {
            if (isAppOnForeground()) {
                handleEventByContext(MainApplication.getTopContext(), code, msg);
            }
        }
    }

    private void chooseLastContext(int code, String msg) {
        OnResponseErrorListener listener = null;
        if (listeners.size() > 0) {
            listener = listeners.get(listeners.size() - 1);
        }
        if (listener == null) return;
        listener.onHandleResponseError(code, msg);

    }

    public void handleEventByContext(final Context context, final int code, String msg) {
//        showErrorToast(context, code, msg);
    }


//    public static String showErrorToast(Context context, int code, String msg) {
//        if (com.ipin.lib.utils.NetworkUtils.isNetworkAvailable(context)) {
//            if (code > 400 && code < 600) {
//                code = ResultCode.ERROR_RESULT_SERVER_ERROR;
//            }
//            switch (code) {
//                case ResultCode.ERROR_SYS_ERROR:
//                    if (DebugConfig.isDebug){
//                        show具体报错内容
//                        showToast(context,msg);
//                    }else {
//                        showToast(context,context.getString(R.string.server_error));
//                    }
//                    return msg;
//                case ResultCode.ERROR_IDENTIFY_MSG:
//                    showToast(context, msg + ":" + code);
//                    return msg;
//                case ResultCode.ERROR_RESULT_NONE:
//                    showToast(context, context.getString(R.string.unknow_exception) + ":" + code);
//                    return context.getString(R.string.unknow_exception);
//                case ResultCode.ERROR_UNKNOW:
//                    showToast(context, context.getString(R.string.login_fail, context.getString(R.string.unknow_exception)) + ":" + code);
//                    return context.getString(R.string.login_fail, context.getString(R.string.unknow_exception));
//                case ResultCode.ERROR_RETRIEVE_ACCOUNT_NO_EXIST:
//                    showToast(context, context.getString(R.string.profile_retrieve_account_no_exist) + ":" + code);
//                    return context.getString(R.string.profile_retrieve_account_no_exist);
//                case ResultCode.ERROR_SIGN_ACCOUNT_EXIST:
//                    showToast(context, context.getString(R.string.signup_account_exist) + ":" + code);
//                    return context.getString(R.string.signup_account_exist);
//                case ResultCode.ERROR_RETRIEVE_REPEAT_REQUEST:
//                    showToast(context, context.getString(R.string.profile_retrieve_repeat_request) + ":" + code);
//                    return context.getString(R.string.profile_retrieve_repeat_request);
//                case ResultCode.ERROR_CARD_LEVEL_LOW:
//                    showToast(context, context.getString(R.string.activation_activate_numpw_high) + ":" + code);
//                    return context.getString(R.string.activation_activate_numpw_high);
//                case ResultCode.ERROR_ACTIVATION_CARD_OVERDUE:
//                case ResultCode.ERROR_CARD_EXPIRE:
//                    showToast(context, context.getString(R.string.activation_activate_already_overdue) + ":" + code);
//                    return context.getString(R.string.activation_activate_already_overdue);
//                case ResultCode.ERROR_ACTIVATION_CARD_NUMWORRY://卡号格式错误
//                case ResultCode.ERROR_ACTIVATION_CARD_NOEXIST://卡号不存在
//                case ResultCode.ERROR_ACTIVATION_CARD_NOTMATCH://卡号和密码不匹配
//                    showToast(context, context.getString(R.string.activation_activate_numpw_error) + ":" + code);
//                    return context.getString(R.string.activation_activate_numpw_error);
//                case ResultCode.ERROR_ACTIVATION_CARD_USED://卡号已经被激活过了
//                    showToast(context, context.getString(R.string.activation_activate_already_title) + ":" + code);
//                    return context.getString(R.string.activation_activate_already_title);
//                case ResultCode.ERROR_UNSUPPORT_PROVINCE:
//                    showToast(context, context.getString(R.string.no_prov_data_warn) + ":" + code);
//                    return context.getString(R.string.no_prov_data_warn);
//                case ResultCode.ERROR_TOKEN_INCORRECT:
//                    showToast(context, context.getString(R.string.error_token_incorrect) + ":" + code);
//                    return context.getString(R.string.error_token_incorrect);
//                case ResultCode.ERROR_PHONE_NO_EXIST:
//                case ResultCode.ERROR_PHONE_NO_EXIST_IPIN:
//                    showToast(context, context.getString(R.string.error_phone_no_exist) + ":" + code);
//                    return context.getString(R.string.error_phone_no_exist);
//                case ResultCode.ERROR_PHONE_CAPTCHA:
//                    showToast(context, context.getString(R.string.error_phone_captcha) + ":" + code);
//                    return context.getString(R.string.error_phone_captcha);
//                case ResultCode.ERROR_LOGIN_PAW_ERROR:
//                case ResultCode.ERROR_PHONE_PASSWORD:
//                case ResultCode.ERROR_PHONE_PASSWORD_IPIN:
//                    showToast(context, context.getString(R.string.error_phone_no_exist) + ":" + code);
//                    return context.getString(R.string.error_phone_no_exist);
//                case ResultCode.ERROR_ACTIVATION_ACCOUNT_SANMLEVEL:
//                    该账号已经绑定其他同级别点卡
//                    showToast(context, context.getString(R.string.activation_activate_numpw_already) + ":" + code);
//                    return context.getString(R.string.activation_activate_numpw_already);
//                case ResultCode.ERROR_NO_EXIST_USER_SCORE_INFO:
//                    return "";
//                case ResultCode.ERROR_PHONE_SERVICE:
//                case ResultCode.ERROR_DEFAULT_CODE:
//                case ResultCode.ERROR_RESULT_SERVER_ERROR:
//                    showToast(context, context.getString(R.string.server_error) + ":" + code);
//                    return context.getString(R.string.server_error);
//                case ResultCode.ERROR_PERMISSION_DENIED:
//                    showToast(context,context.getString(R.string.error_permission_denied_tip) + ":" + code);
//                    return context.getString(R.string.error_permission_denied_tip);
//                default:
//                    if (!TextUtils.isEmpty(msg)) {
//                        showToast(context, msg + ":" + code);
//                        return msg;
//                    } else {
//                        showToast(context, context.getString(R.string.network_error) + ":" + code);
//                        return context.getString(R.string.network_error);
//                    }
//            }
//
//        } else {
//
//            showToast(context, context.getString(R.string.error_network_not_available));
//            return context.getString(R.string.error_network_not_available);
//        }
//    }

    private static void showToast(Context context, String msg){
        Toast.makeText(context,msg, Toast.LENGTH_SHORT).show();
    }

    private boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) MainApplication.getContext().getSystemService(
                Context.ACTIVITY_SERVICE);
        String packageName = MainApplication.getContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

}
