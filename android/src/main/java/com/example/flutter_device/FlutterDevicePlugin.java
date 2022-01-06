package com.example.flutter_device;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import java.util.UUID;

/** FlutterDevicePlugin */
public class FlutterDevicePlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "flutter_device");
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    }else if (call.method.equals("getDeviceUUID")){
      result.success(getDeviceUUID(context));
    }
    else if (call.method.equals("getVersionCode")){
      result.success(getVersionCode(context));
    }
    else if (call.method.equals("getsystemMark")){
      result.success(getsystemMark());
    }
    else if (call.method.equals("getCurrentDeviceModel")){
      result.success(getCurrentDeviceModel());
    }
    else if (call.method.equals("getVersionName")){
      result.success(getVersionName(context));
    }
    else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  public static String getAndroidID(Context context)
  {
    String id = Settings.Secure.getString(
            context.getApplicationContext().getContentResolver(),
            Settings.Secure.ANDROID_ID
    );
    return id == null ? "" : id;
  }

  // 获取UUID
  public String getDeviceUUID(Context context)
  {
    String androidId = getAndroidID(context);
    UUID deviceUuid = new UUID(androidId.hashCode(), ((long)androidId.hashCode() << 32));
    String uuid;
    uuid = deviceUuid.toString().replace("-" , "");
    return uuid;
  }

  // 获取版本code
  public String getVersionCode(Context context)
  {
    int versionCode = 0;
    try
    {
      versionCode =  context.getPackageManager().getPackageInfo(context.getPackageName() , PackageManager.GET_CONFIGURATIONS).versionCode;
    }
    catch (PackageManager.NameNotFoundException e)
    {
      e.printStackTrace();
    }
    return versionCode+"";
  }

  // 获取版本Name
  public String getVersionName(Context context)
  {
    String versionName="";
    try
    {
      versionName =  context.getPackageManager().getPackageInfo(context.getPackageName() , PackageManager.GET_CONFIGURATIONS).versionName;
    }
    catch (PackageManager.NameNotFoundException e)
    {
      e.printStackTrace();
    }
    return versionName;
  }

  // 系统使用的SDK版本
  private String getsystemMark()
  {
    return Build.VERSION.SDK;
  }

  // 手机的型号
  private String getCurrentDeviceModel()
  {
    return Build.MODEL;
  }

}
