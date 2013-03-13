package com.cds.screensize;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.util.*;
import android.content.pm.*;

public class MainActivity extends Activity
{
	boolean isChecked = false;
	NaturalDisplayType naturalDisplay = NaturalDisplayType.Unknown;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main);

			TextView tvOrientation = (TextView) findViewById(R.id.tvOrientation);
			TextView tvRotation = (TextView) findViewById(R.id.tvRotation);
			Display display = getWindowManager().getDefaultDisplay();

			switch (display.getOrientation())
			{
				case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
					tvOrientation.setText(" Landscape");
					break;
				case ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE:
					tvOrientation.setText(" Reverse Landscape");
					break;
				case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
					tvOrientation.setText(" Portrait");
					break;
				case ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT:
					tvOrientation.setText(" Reverse Portrait");
					break;
				case ActivityInfo.SCREEN_ORIENTATION_BEHIND:
					tvOrientation.setText(" Behind");
					break;
				case ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR:
					tvOrientation.setText(" Full Sensor");
					break;
				case ActivityInfo.SCREEN_ORIENTATION_NOSENSOR:
					tvOrientation.setText(" No Sensor");
					break;
				case ActivityInfo.SCREEN_ORIENTATION_SENSOR:
					tvOrientation.setText(" Sensor");
					break;
				case ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE:
					tvOrientation.setText(" Sensor Landscape");
					break;
				case ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT:
					tvOrientation.setText(" Sensor Portrait");
					break;
				case ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED:
					tvOrientation.setText(" Unspecified");
					break;
				case ActivityInfo.SCREEN_ORIENTATION_USER:
					tvOrientation.setText(" User");
					break;
				default:
					tvOrientation.setText(" " + Integer.toString(display.getOrientation()));
					break;
			}

			switch (display.getRotation())
			{
				case Surface.ROTATION_0:
					tvRotation.setText(" Rotation 0");
					break;
				case Surface.ROTATION_180:
					tvRotation.setText(" Rotation 180");
					break;
				case Surface.ROTATION_270:
					tvRotation.setText(" Rotation 270");
					break;
				case Surface.ROTATION_90:
					tvRotation.setText(" Rotation 90");
					break;
				default:
					tvRotation.setText(" Unknown");
					break;
			}
		}

		
		if (!isChecked)
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
			if (getWindowManager().getDefaultDisplay().getRotation() == Surface.ROTATION_0)
			{
				naturalDisplay = NaturalDisplayType.Portriat;	
			}

			/*setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			if ((getWindowManager().getDefaultDisplay().getRotation() == Surface.ROTATION_0)
				|| getWindowManager().getDefaultDisplay().getRotation() == Surface.ROTATION_180)
			{
				naturalDisplay = NaturalDisplayType.Landscape;	
			}*/
			
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
			isChecked = true;
		}

		

		init();
    }

	private void init()
	{
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		//Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

		TextView tvNatural = (TextView) findViewById(R.id.tvNatural);
		TextView tvHeight = (TextView) findViewById(R.id.tvHeight);
		TextView tvWidth = (TextView) findViewById(R.id.tvWidth);
		TextView tvDensity = (TextView) findViewById(R.id.tvDensity);
		TextView tvDensityDpi =(TextView) findViewById(R.id.tvDensityDpi);
		TextView tvDensityType = (TextView) findViewById(R.id.tvDensityType);
		TextView tvScaledDensity = (TextView) findViewById(R.id.tvScaledDensity);
		TextView tvXdpi = (TextView) findViewById(R.id.tvXdpi);
		TextView tvYdpi = (TextView) findViewById(R.id.tvYdpi);
		TextView tvAspectRatio = (TextView) findViewById(R.id.tvAspectRatio);

		tvNatural.setText(" " + naturalDisplay.toString());
		tvHeight.setText(" " + Integer.toString(metrics.heightPixels));
		tvWidth.setText(" " + Integer.toString(metrics.widthPixels));
		tvDensity.setText(" " + Float.toString(metrics.density));
		tvDensityDpi.setText(" " + Integer.toString(metrics.densityDpi));
		tvDensityType.setText(getDensityType(metrics));
		tvScaledDensity.setText(" " + Float.toString(metrics.scaledDensity));
		tvXdpi.setText(" " + Float.toString(metrics.xdpi));
		tvYdpi.setText(" " + Float.toString(metrics.ydpi));
		tvAspectRatio.setText(getAspectRatio(metrics));
	}

	private CharSequence getAspectRatio(DisplayMetrics metrics)
	{
		String aspectRatio = " Unknown";
		// add code to check height and width instead of using metrics
		int height = Math.abs(metrics.heightPixels);
		int width = Math.abs(metrics.widthPixels);
		int dividend, divisor, modulo;

		if (height > width)
		{
			dividend = height;
			divisor = width;
		}
		else
		{
			dividend = width;
			divisor = height;
		}

		modulo = dividend % divisor;

		if (modulo != 0)
		{
			dividend = divisor;
			divisor = modulo;

			while (modulo != 0)
			{
				modulo = dividend % divisor;
				dividend = divisor;
				if (modulo != 0)
				{
					divisor = modulo;
				}
			}

			if (height > width)
			{
				dividend = height / divisor;
				divisor = width / divisor;
			}
			else
			{
				dividend = width / divisor;
				divisor = height / divisor;
			}

		}

		aspectRatio = " " + Integer.toString(dividend) + ":" 
			+ Integer.toString(divisor);

		return aspectRatio;
	}

	private CharSequence getDensityType(DisplayMetrics metrics)
	{
		String densityType = null;

		switch (metrics.densityDpi)
		{
			case metrics.DENSITY_HIGH:
				densityType = " DENSITY_HIGH";
				break;
			case metrics.DENSITY_LOW:
				densityType = " DENSITY_LOW";
				break;
			case metrics.DENSITY_MEDIUM:
				densityType = " DENSITY_MEDIUM";
				break;
			case metrics.DENSITY_TV:
				densityType = " DENSITY_TV";
				break;
			case metrics.DENSITY_XHIGH:
				densityType = " DENSITY_XHIGH";
				break;
			default:
				densityType = " Unknown";
				break;
		}

		return densityType;
	}

	enum NaturalDisplayType
	{
		Portriat,
		Landscape,
		Unknown
	}
}
