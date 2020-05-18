package cn.example.activitytest;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {             // 销毁所有活动
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
            activities.clear();
        }
    }
}
