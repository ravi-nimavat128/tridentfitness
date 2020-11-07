package apps.tridentfitness.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bigkoo.pickerview.MyOptionsPickerView;
import com.suke.widget.SwitchButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import apps.tridentfitness.BuildConfig;
import apps.tridentfitness.R;
import apps.tridentfitness.recivers.AlarmReceiver;
import apps.tridentfitness.recivers.LocalData;
import apps.tridentfitness.recivers.NotificationScheduler;
import apps.tridentfitness.utilHelper.SPmanager;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener, SwitchButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {
    RelativeLayout rele_timepicker;
    RelativeLayout rele_reset;
    RelativeLayout rele_circuit;
    RelativeLayout rele_remind;
    TextView txt_time;
    TextView txt_circuit;
    String TAG = "RemindMe";
    RelativeLayout rele_rateus;
    RelativeLayout rele_contactus;
    RelativeLayout rele_privecy;
    RelativeLayout rele_term;
    private int hour;
    private int minute;
    LocalData localData;
    private SwitchButton switch_button;
    private SwitchButton sound_switch;
    private SwitchButton guidance_switch;
    private SwitchButton halfway_switch;
    ImageView img_line;
    ImageView img_mute;
    ImageView img_full;
    ImageView img_next;
    ImageView btn_back;
    SeekBar vollume_seekbar;
    private AudioManager audioManager;
    private Dialog dialog;
    private ArrayList<String> items;
    private String circut_text;

    public SettingsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.WHITE);
        setContentView(R.layout.activity_settings);
        items = new ArrayList<String>();
        localData = new LocalData(getApplicationContext());
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        init();
        if (!SPmanager.getFirstTime(SettingsActivity.this)) {
            halfway_switch.setChecked(true);
            sound_switch.setChecked(true);
            guidance_switch.setChecked(true);
        }
    }

    private void init() {
        circut_text = SPmanager.getPreference(this, "circut");
        rele_timepicker = findViewById(R.id.rele_timepicker);
        rele_rateus = findViewById(R.id.real_rateus);
        rele_contactus = findViewById(R.id.real_contactus);
        rele_privecy = findViewById(R.id.rele_privecy);
        rele_term = findViewById(R.id.rele_term);
        txt_time = findViewById(R.id.txt_time);
        rele_timepicker.setOnClickListener(this);
        switch_button = findViewById(R.id.switch_button);
        img_line = findViewById(R.id.img_line);
        btn_back = findViewById(R.id.btn_back);
        img_mute = findViewById(R.id.img_mute);
        img_full = findViewById(R.id.img_full);
        img_next = findViewById(R.id.img_next);
        rele_reset = findViewById(R.id.rele_reset);
        halfway_switch = findViewById(R.id.halfway_switch);
        guidance_switch = findViewById(R.id.guidance_switch);
        sound_switch = findViewById(R.id.sound_switch);
        rele_circuit = findViewById(R.id.rele_circuit);
        txt_circuit = findViewById(R.id.txt_circuit);
        vollume_seekbar = findViewById(R.id.vollume_seekbar);
        rele_remind = findViewById(R.id.rele_remind);
        hour = localData.get_hour();
        minute = localData.get_min();
        txt_time.setText(getFormatedTime(hour, minute));
        setcheck();
        if (!localData.getReminderStatus())
            rele_timepicker.setAlpha(0.4f);
        vollume_seekbar.setMax(audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        vollume_seekbar.setProgress(audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC));
        onclickListners();
    }

    private void onclickListners() {
        switch_button.setOnCheckedChangeListener(this);
        rele_rateus.setOnClickListener(this);
        rele_contactus.setOnClickListener(this);
        rele_term.setOnClickListener(this);
        rele_privecy.setOnClickListener(this);
        halfway_switch.setOnCheckedChangeListener(this);
        guidance_switch.setOnCheckedChangeListener(this);
        sound_switch.setOnCheckedChangeListener(this);
        rele_timepicker.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        vollume_seekbar.setOnSeekBarChangeListener(this);
        img_mute.setOnClickListener(this);
        img_full.setOnClickListener(this);
        rele_circuit.setOnClickListener(this);
        rele_reset.setOnClickListener(this);
    }

    private void setcheck() {
        if (localData.getReminderStatus()) {
            img_line.setVisibility(View.VISIBLE);
            rele_remind.setVisibility(View.VISIBLE);
        } else {
            img_line.setVisibility(View.GONE);
            rele_remind.setVisibility(View.GONE);
        }
        switch_button.setChecked(localData.getReminderStatus());
        switch_button.toggle();     //switch state
        switch_button.toggle(false);//switch without animation
        switch_button.setShadowEffect(true);//disable shadow effect
        switch_button.setEnableEffect(false);
        if (SPmanager.getChecked(SettingsActivity.this)) {
            halfway_switch.setChecked(true);
        } else {
            halfway_switch.setChecked(false);
        }
        if (SPmanager.getCountdown(this)) {
            sound_switch.setChecked(true);
        } else {
            sound_switch.setChecked(false);
        }
        if (SPmanager.getGuidence(SettingsActivity.this)) {
            guidance_switch.setChecked(true);
        } else {
            guidance_switch.setChecked(false);
        }
        if (circut_text != null) {
            if (circut_text.equals(" ")) {
                txt_circuit.setText("1");
            } else {
                txt_circuit.setText("" + circut_text);
            }
        }
    }

    private void showTimePickerDialog(int h, int m) {
        TimePickerDialog builder = new TimePickerDialog(this, R.style.DialogTheme,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
                        Log.d(TAG, "onTimeSet: hour " + hour);
                        Log.d(TAG, "onTimeSet: min " + min);
                        localData.set_hour(hour);
                        localData.set_min(min);
                        txt_time.setText(getFormatedTime(hour, min));
                        NotificationScheduler.setReminder(SettingsActivity.this, AlarmReceiver.class, localData.get_hour(), localData.get_min());
                    }
                }, h, m, false);
        builder.show();
    }

    public String getFormatedTime(int h, int m) {
        final String OLD_FORMAT = "HH:mm";
        final String NEW_FORMAT = "hh:mm a";

        String oldDateString = h + ":" + m;
        String newDateString = "";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT, getCurrentLocale());
            Date d = sdf.parse(oldDateString);
            sdf.applyPattern(NEW_FORMAT);
            newDateString = sdf.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newDateString;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public Locale getCurrentLocale() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return getResources().getConfiguration().getLocales().get(0);
        } else {
            //noinspection deprecation
            return getResources().getConfiguration().locale;
        }
    }

    @Override
    public void onClick(View v) {
        int vieId = v.getId();
        switch (vieId) {
            case R.id.btn_back:
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.img_mute:
                vollume_seekbar.setProgress(0);
                break;
            case R.id.img_full:
                vollume_seekbar.setProgress(100);
                break;
            case R.id.real_rateus:
                openApplicationMarket(this, "");
                break;
            case R.id.real_contactus:
                gotoContactUs(this);
                break;
            case R.id.rele_privecy:
                privacyClick();
                break;
            case R.id.rele_term:
                termclick();
                break;
            case R.id.rele_circuit:
                final MyOptionsPickerView singlePicker = new MyOptionsPickerView(SettingsActivity.this);

                items.add("1");
                items.add("2");
                items.add("3");
                items.add("4");
                items.add("5");
                singlePicker.setPicker(items);
                singlePicker.setTitle(getString(R.string.circuit));
                singlePicker.setCyclic(false);
                singlePicker.setSelectOptions(0);
                singlePicker.setOnoptionsSelectListener(new MyOptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3) {
                        if (items.size() != 0) {
                            SPmanager.saveValue(SettingsActivity.this, "circut", items.get(options1));
                        }
                        circut_text = SPmanager.getPreference(SettingsActivity.this, "circut");
                        txt_circuit.setText(circut_text);
                    }
                });
                singlePicker.show();
                break;
            case R.id.rele_reset:
                openResetdialog();
                break;
            case R.id.txt_yes:
                reset();
                break;
            case R.id.txt_no:
                dialog.dismiss();
                break;
            case R.id.rele_timepicker:
                if (localData.getReminderStatus())
                    showTimePickerDialog(localData.get_hour(), localData.get_min());
                break;
        }
    }

    private void reset() {
        this.hour = 20;
        this.minute = 00;
        txt_time.setText(this.hour + ":" + this.minute);
        vollume_seekbar.setProgress(100);
        switch_button.setChecked(false);
        halfway_switch.setChecked(true);
        sound_switch.setChecked(true);
        guidance_switch.setChecked(true);
        img_line.setVisibility(View.GONE);
        rele_remind.setVisibility(View.GONE);
        if (items.size() != 0) {
            SPmanager.saveValue(dialog.getContext(), "circut", items.get(0));
        }
        circut_text = SPmanager.getPreference(dialog.getContext(), "circut");
        SPmanager.setChecked(SettingsActivity.this, true);
        SPmanager.setGuidence(SettingsActivity.this, true);
        txt_circuit.setText(circut_text);
        dialog.dismiss();
    }

    private void openApplicationMarket(Activity activity, String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://play.google.com/store/apps/details?id=" + getPackageName());
        stringBuilder.append(str);
        activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString())));
    }

    public void gotoContactUs(Activity activity) {
        activity.getIntent();
        Intent intent = new Intent("android.intent.action.SENDTO");
        intent.setData(Uri.parse("mailto:" + getString(R.string.email_id)));
        intent.putExtra("android.intent.extra.SUBJECT", "Contact Us");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n\n\n\n\n\n\n");
        stringBuilder.append(getHandSetInfo(activity));
        intent.putExtra("android.intent.extra.TEXT", stringBuilder.toString());
        activity.startActivity(intent);
    }

    private void privacyClick() {
        Intent intent = new Intent(this, Privecy.class);
        startActivity(intent);
    }

    private void termclick() {
        Intent intent = new Intent(this, TermOfUse.class);
        startActivity(intent);
    }

    private void openResetdialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.reset_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        TextView txt_yes, txt_no;
        txt_yes = dialog.findViewById(R.id.txt_yes);
        txt_no = dialog.findViewById(R.id.txt_no);

        txt_yes.setOnClickListener(this);
        txt_no.setOnClickListener(this);
    }

    public static String getHandSetInfo(Activity activity) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Application Info\n   App ID: ");
        stringBuilder.append(activity.getPackageName());
        stringBuilder.append("\n   App version: ");
        stringBuilder.append(BuildConfig.VERSION_NAME);
        stringBuilder.append("\n   Device: ");
        stringBuilder.append(Build.MODEL);
        stringBuilder.append("\n   Android version: ");
        stringBuilder.append(Build.VERSION.RELEASE);
        return stringBuilder.toString();
    }

    @Override
    public void onCheckedChanged(SwitchButton view, boolean isChecked) {

        localData.setReminderStatus(isChecked);
        int id = view.getId();
        switch (id) {
            case R.id.halfway_switch:
                if (isChecked) {
                    SPmanager.setChecked(SettingsActivity.this, true);
                } else {
                    SPmanager.setChecked(SettingsActivity.this, false);
                }
                break;
            case R.id.switch_button:
                if (isChecked) {
                    Log.d(TAG, "onCheckedChanged: true");
                    img_line.setVisibility(View.VISIBLE);
                    rele_remind.setVisibility(View.VISIBLE);
                    NotificationScheduler.setReminder(SettingsActivity.this, AlarmReceiver.class, localData.get_hour(), localData.get_min());
                    rele_timepicker.setAlpha(1f);
                } else {
                    Log.d(TAG, "onCheckedChanged: false");
                    img_line.setVisibility(View.GONE);
                    rele_remind.setVisibility(View.GONE);
                    NotificationScheduler.cancelReminder(SettingsActivity.this, AlarmReceiver.class);
                    rele_timepicker.setAlpha(0.4f);
                }
                break;
            case R.id.guidance_switch:
                if (isChecked) {
                    SPmanager.setGuidence(SettingsActivity.this, true);
                } else {
                    SPmanager.setGuidence(SettingsActivity.this, false);
                }
                break;
            case R.id.sound_switch:
                if (isChecked) {
                    SPmanager.setCountdown(SettingsActivity.this, true);
                } else {
                    SPmanager.setCountdown(SettingsActivity.this, false);
                }
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}
