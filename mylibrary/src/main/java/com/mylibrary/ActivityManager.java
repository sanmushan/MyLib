package com.mylibrary;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class ActivityManager {

    private static List<Activity> activitys = new ArrayList<>();


    /**
     * 添加Activity到栈
     */
    public static void addActivity(Activity activity) {
        try {
//          if (activityStack == null) {
//              activityStack = new HashMap<Activity, Activity>();
//          }
//          activityStack.put(activity, activity);

            activitys.add(activity);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public static void removeActivity(Activity activity) {
//	  if(activityStack.size() < 1) return;
//	  activityStack.remove(activity);
        if(!removeBool) return;
        for (int i = 0; i < activitys.size(); i++) {
            if(activitys.get(i).equals(activity)) {
                activitys.remove(i);
                break;
            }
        }
    }

    public static boolean activityIsShow(Class<?> cls) {
        for (int i = 0; i < activitys.size(); i++) {
            if(activitys.get(i).getClass().getName().equals(cls.getName())) {
                return true;
            }
        }
        return false;
        //return activityStack.get(cls.getName()) != null;
    }

    private static boolean removeBool = true;

    /**
     * 结束指定的Activity(重载)
     */
    public static void finishActivity(Class<?> cls) {
        try {
            removeBool = false;
            List<Activity> as = new ArrayList<>();
            for (int i = activitys.size()-1; i >= 0; i--) {
                if(activitys.get(i).getClass().getName().equals(cls.getName())) {
                    Activity activity = activitys.remove(i);
                    as.add(activity);
                }
            }


            for (int i = 0; i < as.size(); i++) {
                as.get(i).finish();
            }
            removeBool = true;
            //Activity activity = activityStack.get(cls.getName());
            //if(activity != null) {
            //	  activity.finish();
            // }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

//  /**
//   * 关闭除了指定activity以外的全部activity 如果cls不存在于栈中，则栈全部清空
//   *
//   * @param cls
//   */
//  public static void finishOthersActivity(Class<?> cls) {
//      try {
//    	  List<Activity> activitys = new ArrayList<>();
//          Iterator<Activity> it = activityStack.values().iterator();
//          while(it.hasNext()) {
//        	  Activity activity = it.next();
//        	  if(activity.getClass().getName().equals(cls.getName())) {
//        		  continue;
//        	  }
//        	  //删除
//        	  activitys.add(activity);
//          }
//          for (int i = 0; i < activitys.size(); i++) {
//        	  activitys.get(i).finish();
//          }
//      } catch (Exception ex) {
//          ex.printStackTrace();
//      }
//  }

    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        try {
            List<Activity> t_activitys = new ArrayList<>();
//          Iterator<Activity> it = activityStack.values().iterator();
//          while(it.hasNext()) {
//        	  activitys.add(it.next());
//          }
//          activityStack.clear();
//          for (int i = 0; i < activitys.size(); i++) {
//        	  activitys.get(i).finish();
//          }
            for (int i = activitys.size()-1; i >=0; i--) {
                t_activitys.add(activitys.get(i));
            }
            for (int i = t_activitys.size()-1; i >=0; i--) {
                t_activitys.get(i).finish();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 应用程序退出
     */
    public static void AppExit(Context context) {
        try {
            finishAllActivity();
            android.app.ActivityManager activityMgr = (android.app.ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            System.exit(0);
        }
    }

    public static void startActivityAndFinish(Activity activity, Class<?> cls) {
        activity.startActivity(new Intent(activity, cls));
        activity.finish();
    }
    public static void startActivity(Activity activity, Class<?> cls) {
        activity.startActivity(new Intent(activity, cls));
    }
}