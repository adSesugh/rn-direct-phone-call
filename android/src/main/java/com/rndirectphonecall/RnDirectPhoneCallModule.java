package com.rndirectphonecall;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.telecom.TelecomManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@ReactModule(name = RnDirectPhoneCallModule.NAME)
public class RnDirectPhoneCallModule extends ReactContextBaseJavaModule {

  public static final String NAME = "RnDirectPhoneCall";
  private static RnDirectPhoneCallModule rnDirectPhoneCallModule;
  private final ReactApplicationContext reactContext;
  private static String number = "";
  private static Integer simIndex;
  private static final int CALL_PHONE_CODE = 100;
  private static final int READ_PHONE_STATE_CODE = 101;

  public RnDirectPhoneCallModule(ReactApplicationContext reactContext) {
    super(reactContext);
    if (rnDirectPhoneCallModule == null) {
      rnDirectPhoneCallModule = this;
    }
    this.reactContext = reactContext;
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  @ReactMethod
  public void andCall(String number, Integer simNumber) {
    RnDirectPhoneCallModule.number = Uri.encode(number);
    RnDirectPhoneCallModule.simIndex = simNumber;

    if (ContextCompat.checkSelfPermission(getReactApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED &&
        ContextCompat.checkSelfPermission(getReactApplicationContext(), Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED)
      {
      ActivityCompat.requestPermissions(getCurrentActivity(), new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_NUMBERS}, CALL_PHONE_CODE);
    } else {
      startCall();
    }
  }

  private static void startCall() {
    try {
      String url = "tel:" + RnDirectPhoneCallModule.number;
      Integer simCard = RnDirectPhoneCallModule.simIndex;

      final String[] simSlotName = {"extra_asus_dial_use_dualsim", "com.android.phone.extra.slot", "slot", "simslot", "sim_slot", "subscription", "Subscription", "phone", "com.android.phone.DialingMode", "simSlot", "slot_id", "simId", "simnum", "phone_type", "slotId", "slotIdx"};

      Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
      intent.setData(Uri.parse(url));
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      intent.putExtra("com.android.phone.force.slot", true);
      intent.putExtra("Cdma_Supp", true);

      for (String s : simSlotName) {
        intent.putExtra(s, simCard);
      }

      rnDirectPhoneCallModule.reactContext.startActivity(intent);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @RequiresApi(api = Build.VERSION_CODES.Q)
  @Override
  public Map<String, Object> getConstants() {

    final Map<String, Object> constants = new HashMap<>();

    try {
      TelephonyManager telManager = (TelephonyManager) getReactApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
      SubscriptionManager manager = (SubscriptionManager) getReactApplicationContext().getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);

      if (ContextCompat.checkSelfPermission(getReactApplicationContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(Objects.requireNonNull(getCurrentActivity()), new String[]{Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE_CODE);
      }
      List<SubscriptionInfo> subscriptionInfos = manager.getActiveSubscriptionInfoList();

        int sub = 0;
        for (SubscriptionInfo subInfo : subscriptionInfos) {
          CharSequence carrierName = subInfo.getCarrierName();
          CharSequence displayName = subInfo.getDisplayName();
          int simSlotIndex         = subInfo.getSimSlotIndex();

          constants.put("carrierName" + sub, carrierName.toString());
          constants.put("displayName" + sub, displayName.toString());
          constants.put("simSlotIndex"     + sub, simSlotIndex);
          sub++;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }

      return constants;
    }

    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CALL_PHONE_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
              startCall();
            }
        }
        if(requestCode == READ_PHONE_STATE_CODE){
          if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

          }
        }
    }
}
