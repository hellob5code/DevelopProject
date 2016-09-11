package com.moge.ebox.phone.bettle.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;

import org.apache.http.HttpException;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import com.vikaa.baseapp.R;

public class AppException extends Exception
  implements Thread.UncaughtExceptionHandler
{
  private static final boolean Debug = true;
  public static final byte TYPE_NETWORK = 1;
  public static final byte TYPE_SOCKET = 2;
  public static final byte TYPE_HTTP_CODE = 3;
  public static final byte TYPE_HTTP_ERROR = 4;
  public static final byte TYPE_JSON = 5;
  public static final byte TYPE_IO = 6;
  public static final byte TYPE_RUN = 7;
  private byte type;
  private int code;
  private Thread.UncaughtExceptionHandler mDefaultHandler;

  private AppException()
  {
    this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    Thread.setDefaultUncaughtExceptionHandler(this);
  }

  private AppException(byte type, int code, Exception excp) {
    super(excp);
    this.type = type;
    this.code = code;

    saveErrorLog(excp);
  }

  public int getCode() {
    return this.code;
  }
  public int getType() {
    return this.type;
  }

  public void makeToast(Context ctx)
  {
    switch (getType()) {
    case 3:
      String err = ctx.getString(R.string.http_status_code_error, new Object[] { Integer.valueOf(getCode()) });
      Toast.makeText(ctx, err, 0).show();
      break;
    case 4:
      Toast.makeText(ctx, R.string.http_exception_error, 0).show();
      break;
    case 2:
      Toast.makeText(ctx, R.string.socket_exception_error, 0).show();
      break;
    case 1:
      Toast.makeText(ctx, R.string.network_not_connected, 0).show();
      break;
    case 5:
      Toast.makeText(ctx, R.string.xml_parser_failed, 0).show();
      break;
    case 6:
      Toast.makeText(ctx, R.string.io_exception_error, 0).show();
      break;
    case 7:
      Toast.makeText(ctx, R.string.app_run_code_error, 0).show();
    }
  }

  public void saveErrorLog(Exception excp)
  {
    String errorlog = "errorlog.txt";
    String savePath = "";
    String logFilePath = "";
    FileWriter fw = null;
    PrintWriter pw = null;
    try
    {
      String storageState = Environment.getExternalStorageState();
      if (storageState.equals("mounted")) {
        savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/momoka/Log/";
        File file = new File(savePath);
        if (!file.exists()) {
          file.mkdirs();
        }
        logFilePath = savePath + errorlog;
      }

      if (logFilePath == "") {
        return;
      }
      File logFile = new File(logFilePath);
      if (!logFile.exists()) {
        logFile.createNewFile();
      }
      fw = new FileWriter(logFile, true);
      pw = new PrintWriter(fw);
      pw.println("--------------------" + new Date().toString() + "---------------------");
      excp.printStackTrace(pw);
      pw.close();
      fw.close();
    } catch (Exception e) {
      e.printStackTrace();

      if (pw != null) pw.close();
      if (fw != null) try { fw.close(); }
        catch (IOException localIOException1)
        {
        }
    }
    finally
    {
      if (pw != null) pw.close();
      if (fw != null) try { fw.close(); } catch (IOException localIOException2) {
        } 
    }
  }

  public static AppException http(int code) {
    return new AppException((byte)3, code, null);
  }

  public static AppException http(Exception e) {
    return new AppException((byte)4, 0, e);
  }

  public static AppException socket(Exception e) {
    return new AppException((byte)2, 0, e);
  }

  public static AppException io(Exception e) {
    if (((e instanceof UnknownHostException)) || ((e instanceof ConnectException))) {
      return new AppException((byte)1, 0, e);
    }
    if ((e instanceof IOException)) {
      return new AppException((byte)6, 0, e);
    }
    return run(e);
  }

  public static AppException json(Exception e) {
    return new AppException((byte)5, 0, e);
  }

  public static AppException network(Exception e) {
    if (((e instanceof UnknownHostException)) || ((e instanceof ConnectException))) {
      return new AppException((byte)1, 0, e);
    }
    if ((e instanceof HttpException)) {
      return http(e);
    }
    if ((e instanceof SocketException)) {
      return socket(e);
    }
    return http(e);
  }

  public static AppException run(Exception e) {
    return new AppException((byte)7, 0, e);
  }

  public static AppException getAppExceptionHandler()
  {
    return new AppException();
  }

  public void uncaughtException(Thread thread, Throwable ex)
  {
    if ((!handleException(ex)) && (this.mDefaultHandler != null))
      this.mDefaultHandler.uncaughtException(thread, ex);
  }

  private boolean handleException(Throwable ex)
  {
    if (ex == null) {
      return false;
    }

    Context context = AppManager.getAppManager().currentActivity();

    if (context == null) {
      return false;
    }

    String crashReport = getCrashReport(context, ex);
    Logger.i(crashReport);
    return true;
  }

  private String getCrashReport(Context context, Throwable ex)
  {
    PackageInfo pinfo = ((AppContext)context.getApplicationContext()).getPackageInfo();
    StringBuffer exceptionStr = new StringBuffer();
    exceptionStr.append("Version: " + pinfo.versionName + "(" + pinfo.versionCode + ")\n");
    exceptionStr.append("Android: " + Build.VERSION.RELEASE + "(" + Build.MODEL + ")\n");
    exceptionStr.append("Exception: " + ex.getMessage() + "\n");
    StackTraceElement[] elements = ex.getStackTrace();
    for (int i = 0; i < elements.length; i++) {
      exceptionStr.append(elements[i].toString() + "\n");
    }
    return exceptionStr.toString();
  }
}