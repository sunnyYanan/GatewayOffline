package senseHuge.gateway.util;

import java.util.Calendar;

import senseHuge.gateway.ui.Fragment_dataCenter;
import senseHuge.gateway.ui.Fragment_statusValueCheck;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class StatusDatePick extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {

	StringBuilder str = new StringBuilder("");

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		// dataCenter = new Fragment_dataCenter();

		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
		// Do something with the date chosen by the user
		str.append(year + "-" + (month + 1) + "-" + day + " ");
		Calendar time = Calendar.getInstance();
		Dialog timeDialog = new TimePickerDialog(this.getActivity(),
		// 绑定监听器
				new TimePickerDialog.OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker arg0, int hourOfDay,
							int minute) {
						// TODO Auto-generated method stub
						str.append(hourOfDay + ":" + minute);
						Fragment_statusValueCheck.statusDateShow.setText(str);
					}
				}, time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE),
				true);
		timeDialog.setTitle("请选择时间");
		timeDialog.show();
	}
}