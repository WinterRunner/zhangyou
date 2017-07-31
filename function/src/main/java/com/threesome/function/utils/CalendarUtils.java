package com.threesome.function.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

/**
 * author: L.K.X
 * created on: 2017/7/27 上午9:59
 * description:
 */

public class CalendarUtils {

    //Android2.2版本以后的URL，之前的就不写了
    private static String calanderURL = "content://com.android.calendar/calendars";
    private static String calanderEventURL = "content://com.android.calendar/events";
    private static String calanderRemiderURL = "content://com.android.calendar/reminders";


    //请求动态权限

    public static void checkCalendarPermission(Activity activity){
        final int callbackId = 42;
        checkPermissions(activity,callbackId, Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR);
    }
    private static void checkPermissions(Activity activity, int callbackId, String... permissionsId) {
        boolean permissions = true;
        for (String p : permissionsId) {
            permissions = permissions && ContextCompat.checkSelfPermission(activity, p) == PERMISSION_GRANTED;
        }
        if (!permissions)
            ActivityCompat.requestPermissions(activity, permissionsId, callbackId);
    }


    //注意，调用此方法以前，一定要存在权限
    public static void insert(Activity context){

        String calId = getCalendarId(context);
        if (calId == null) {
            return;
        }
        try {

            //添加一个条目到特定的日历，我们需要配置一个日历项插入使用与ContentValues如下：
            ContentValues event = new ContentValues();
            //事件插入日历标识符
            event.put("calendar_id", calId);

            //活动的标题，描述和位置领域的一些基本信息。
            event.put("title", "标题");
            event.put("description", "我是描述的内容");
            event.put("eventLocation", "位置时间信息顶顶顶顶");

            //设置事件的开始和结束的信息如下
            Calendar mCalendar = Calendar.getInstance();

            //mCalendar.add(Calendar.DAY_OF_MONTH, 1);

            mCalendar.add(Calendar.MINUTE, 12);
            long start = mCalendar.getTime().getTime();


//        mCalendar.set(Calendar.HOUR_OF_DAY, 11);
            mCalendar.add(Calendar.MINUTE, 2);
            long end = mCalendar.getTime().getTime();

            event.put("dtstart", start);
            event.put("dtend", end);

            //设置一个全天事件的条目
            //event.put("allDay", 1); // 0 for false, 1 for true

            //事件状态暂定(0)，确认(1)或取消(2)
            event.put("eventStatus", 1);

            //控制是否事件触发报警，提醒如下
            event.put("hasAlarm", 1); // 0 for false, 1 for true

            //设置时区,否则会报错
            event.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());

            //一旦日历事件配置正确，我们已经准备好使用ContentResolver插入到相应的开放新日历的日历事件项：
            Uri eventsUri = Uri.parse(calanderEventURL);
            Uri newEvent = context.getContentResolver().insert(eventsUri, event);

            //设置什么时候提醒
            //Uri newEvent = getActivity().getContentResolver().insert(Uri.parse(calanderEventURL), event);
            long id = Long.parseLong(newEvent.getLastPathSegment());
            ContentValues values = new ContentValues();
            values.put("event_id", id);
            //提前10分钟有提醒
            values.put("minutes", 10);
            Uri uri = Uri.parse(calanderRemiderURL);
            context.getContentResolver().insert(uri, values);

            Toast.makeText(context, "插入事件成功!!!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String getCalendarId(Context context){
        String calendarId = checkId(context);
        if(calendarId==null){
            boolean addCalendarAccount = addCalendarAccount(context);
            if(addCalendarAccount){
                calendarId = checkId(context);
            }
        }
        return calendarId;
    }

    private static String checkId(Context context) {
        String calId = null;
        Cursor managedCursor = context.getContentResolver().query(Uri.parse(calanderURL), null, null, null, null);
        //现在检索的日历列表,可以遍历的结果如下
        if (managedCursor.moveToFirst()) {
            int idColumn = managedCursor.getColumnIndex("_id");
            int courser_name = managedCursor.getColumnIndex(CalendarContract.Calendars.ACCOUNT_NAME);
            do {
                if(CALENDARS_ACCOUNT_NAME.equals(managedCursor.getString(courser_name))){
                    calId = managedCursor.getString(idColumn);
                    break;
                }
            } while (managedCursor.moveToNext());
        }
        managedCursor.close();
        return calId;
    }


    private static String CALENDARS_NAME = "掌游计划";
    private static String CALENDARS_ACCOUNT_NAME = "zhangyou@gmail.com";
    private static String CALENDARS_ACCOUNT_TYPE = "com.threesome.exchange";
    private static String CALENDARS_DISPLAY_NAME = "掌游计划";

    private static boolean addCalendarAccount(Context context) {
        try {
            TimeZone timeZone = TimeZone.getDefault();
            ContentValues value = new ContentValues();
            value.put(CalendarContract.Calendars.NAME, CALENDARS_NAME);

            value.put(CalendarContract.Calendars.ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME);
            value.put(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE);
            value.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, CALENDARS_DISPLAY_NAME);
            value.put(CalendarContract.Calendars.VISIBLE, 1);
            value.put(CalendarContract.Calendars.CALENDAR_COLOR, Color.BLUE);
            value.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);//控制用户权限
            value.put(CalendarContract.Calendars.SYNC_EVENTS, 1);
            value.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, timeZone.getID());
            value.put(CalendarContract.Calendars.OWNER_ACCOUNT, CALENDARS_ACCOUNT_NAME);
            value.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 0);

            Uri calendarUri = Uri.parse(calanderURL);
            calendarUri = calendarUri.buildUpon()
                    .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                    .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME)
                    .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE)
                    .build();

            Uri result = context.getContentResolver().insert(calendarUri, value);
//        long id = result == null ? -1 : ContentUris.parseId(result);
//        return id;
            if(result!=null){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
