/*
LinphoneLauncherActivity.java
Copyright (C) 2011  Belledonne Communications, Grenoble, France

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.habebe.projecthomework.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import com.habebe.projecthomework.R;
import com.habebe.projecthomework.service.MyService;

public class LauncherActivity extends Activity {

	public static  Uri EVENTS_URI;
	public static Uri REMINDERS_URI;

	private Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		EVENTS_URI = Uri.parse(getCalendarUriBase(LauncherActivity.this) + "events");
		REMINDERS_URI = Uri.parse(getCalendarUriBase(LauncherActivity.this) + "reminders");
		// Used to change for the lifetime of the app the name used to tag the logs

		// Hack to avoid to draw twice LinphoneActivity on tablets
/*        if (getResources().getBoolean(R.bool.isTablet)) {
        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }*/

		setContentView(R.layout.launcher);

		mHandler = new Handler();

		onServiceReady();
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	private String getCalendarUriBase(Activity act) {
		String calendarUriBase = null;
		Uri calendars = Uri.parse("content://calendar/calendars");
		Cursor managedCursor = null;
		try {
			managedCursor = act.managedQuery(calendars, null, null, null, null);
		} catch (Exception e) {
		}
		if (managedCursor != null) {
			calendarUriBase = "content://calendar/";
		} else {
			calendars = Uri.parse("content://com.android.calendar/calendars");
			try {
				managedCursor = act.managedQuery(calendars, null, null, null, null);
			} catch (Exception e) {
			}
			if (managedCursor != null) {
				calendarUriBase = "content://com.android.calendar/";
			}
		}
		return calendarUriBase;
	}

	protected void onServiceReady() {

		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent i = new Intent(getApplicationContext(), LoginActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(i);
				finish();
			}
		}, 1000);
	}

}


