package com.moge.ebox.phone.bettle.tools;

import java.util.Stack;

import android.app.Activity;
import android.content.Context;

public class AppManager
{
  private static Stack<Activity> activityStack;
  private static AppManager instance;

  public static AppManager getAppManager()
  {
    if (instance == null) {
      instance = new AppManager();
    }
    return instance;
  }

  public void addActivity(Activity activity)
  {
    if (activityStack == null) {
      activityStack = new Stack();
    }
    activityStack.add(activity);
  }

  public Activity currentActivity()
  {
    Activity activity = (Activity)activityStack.lastElement();
    return activity;
  }

  public void finishActivity()
  {
    Activity activity = (Activity)activityStack.lastElement();
    if (activity != null) {
      activity.finish();
      activity = null;
    }
  }

  public void finishActivity(Activity activity)
  {
    if (activity != null) {
      activityStack.remove(activity);
      activity.finish();
      activity = null;
    }
  }

  public void finishActivity(Class<?> cls)
  {
    for (Activity activity : activityStack)
      if (activity.getClass().equals(cls))
        finishActivity(activity);
  }

  public void finishAllActivity()
  {
    int i = 0; for (int size = activityStack.size(); i < size; i++) {
      if (activityStack.get(i) != null) {
        ((Activity)activityStack.get(i)).finish();
      }
    }
    activityStack.clear();
  }

  public void AppExit(Context context)
  {
    try
    {
      finishAllActivity();
    }
    catch (Exception localException)
    {
    }
  }
}