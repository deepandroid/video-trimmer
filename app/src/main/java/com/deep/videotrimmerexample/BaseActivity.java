package com.deep.videotrimmerexample;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Deep Patel
 * (Sr. Android Developer)
 * on 2/6/2018
 */

public class BaseActivity extends AppCompatActivity {
    protected boolean shouldPerformDispatchTouch = true;
    protected long lastClickTime = 0;
    public TextView title;
    public Toolbar toolbar;
    private Snackbar snackbar;

    public setPermissionListener permissionListener;
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (snackbar != null && snackbar.isShown()) snackbar.dismiss();
    }

    public void showToastShort(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showToastLong(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void showSnackbar(View view, String msg, int LENGTH) {
        if (view == null) return;
        snackbar = Snackbar.make(view, msg, LENGTH);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        snackbar.show();
    }

    public void showSnackbar(View view, String msg, int LENGTH, String action, final OnSnackbarActionListener actionListener) {
        if (view == null) return;
        snackbar = Snackbar.make(view, msg, LENGTH);
        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        if (actionListener != null) {
            snackbar.setAction(action, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                    actionListener.onAction();
                }
            });
        }
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        snackbar.show();
    }

    public void setUpToolbar(String strTitle) {
        setUpToolbarWithBackArrow(strTitle, false);
    }

    public void setUpToolbarWithBackArrow(String strTitle) {
        setUpToolbarWithBackArrow(strTitle, true);
    }

    public void setUpToolbarWithBackArrow(String strTitle, boolean isBackArrow) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(isBackArrow);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_vector_arrow_back_black);
            title = (TextView) toolbar.findViewById(R.id.title);
            title.setText(strTitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void preventDoubleClick(View view) {
        /*// preventing double, using threshold of 1000 ms*/
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
            return;
        }
        lastClickTime = SystemClock.elapsedRealtime();
    }


    public void showSoftKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    public void hideSoftKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean ret = false;
        try {
            View view = getCurrentFocus();
            ret = super.dispatchTouchEvent(event);
            if (shouldPerformDispatchTouch) {
                if (view instanceof EditText) {
                    View w = getCurrentFocus();
                    int scrCords[] = new int[2];
                    if (w != null) {
                        w.getLocationOnScreen(scrCords);
                        float x = event.getRawX() + w.getLeft() - scrCords[0];
                        float y = event.getRawY() + w.getTop() - scrCords[1];

                        if (event.getAction() == MotionEvent.ACTION_UP
                                && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom())) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        }
                    }
                }
            }
            return ret;
        } catch (Exception e) {
            return ret;
        }
    }

    public void showPermissionSettingDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.need_permission);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.app_settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void requestAppPermissions(final String[] requestedPermissions,
                                      final int requestCode, setPermissionListener listener) {
        this.permissionListener = listener;
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (String permission : requestedPermissions) {
            permissionCheck = permissionCheck + ContextCompat.checkSelfPermission(this, permission);
        }
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, requestedPermissions, requestCode);
        } else {
            if (permissionListener != null) permissionListener.onPermissionGranted(requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                if (permissionListener != null) permissionListener.onPermissionGranted(requestCode);
                break;
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                if (permissionListener != null) permissionListener.onPermissionDenied(requestCode);
                break;
            } else {
                if (permissionListener != null)
                    permissionListener.onPermissionNeverAsk(requestCode);
                break;
            }
        }
    }


    public interface setPermissionListener {
        public void onPermissionGranted(int requestCode);

        public void onPermissionDenied(int requestCode);

        public void onPermissionNeverAsk(int requestCode);
    }
}
