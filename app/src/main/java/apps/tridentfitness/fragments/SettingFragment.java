package apps.tridentfitness.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import apps.tridentfitness.Utils.SharedPrefreances;
import apps.tridentfitness.activities.MainActivity;
import apps.tridentfitness.activities.Privecy;
import apps.tridentfitness.activities.SettingsActivity;
import apps.tridentfitness.activities.SplashScreenActivity;
import apps.tridentfitness.activities.TermOfUse;
import apps.tridentfitness.recivers.AlarmReceiver;
import apps.tridentfitness.recivers.LocalData;
import apps.tridentfitness.recivers.NotificationScheduler;
import apps.tridentfitness.utilHelper.SPmanager;

public class SettingFragment extends Fragment implements View.OnClickListener, SwitchButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {

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
    View view;
    RelativeLayout layout_logout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_settings, container, false);

        items = new ArrayList<String>();
        localData = new LocalData(getActivity());
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        init();
        if (!SPmanager.getFirstTime(getActivity())) {
            halfway_switch.setChecked(true);
            sound_switch.setChecked(true);
            guidance_switch.setChecked(true);
        }
        layout_logout = view.findViewById(R.id.layout_logout);
        layout_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefreances.setSharedPreferenceString(getActivity(),"login","0");
                Intent intent = new Intent(getActivity(), SplashScreenActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }




    private void init() {
        circut_text = SPmanager.getPreference(getActivity(), "circut");
        rele_timepicker = view.findViewById(R.id.rele_timepicker);
        rele_rateus = view.findViewById(R.id.real_rateus);
        rele_contactus = view.findViewById(R.id.real_contactus);
        rele_privecy = view.findViewById(R.id.rele_privecy);
        rele_term = view.findViewById(R.id.rele_term);
        txt_time = view.findViewById(R.id.txt_time);
        rele_timepicker.setOnClickListener(this);
        switch_button = view.findViewById(R.id.switch_button);
        img_line = view.findViewById(R.id.img_line);
        btn_back = view.findViewById(R.id.btn_back);
        img_mute = view.findViewById(R.id.img_mute);
        img_full = view.findViewById(R.id.img_full);
        img_next = view.findViewById(R.id.img_next);
        rele_reset = view.findViewById(R.id.rele_reset);
        halfway_switch = view.findViewById(R.id.halfway_switch);
        guidance_switch = view.findViewById(R.id.guidance_switch);
        sound_switch = view.findViewById(R.id.sound_switch);
        rele_circuit = view.findViewById(R.id.rele_circuit);
        txt_circuit = view.findViewById(R.id.txt_circuit);
        vollume_seekbar = view.findViewById(R.id.vollume_seekbar);
        rele_remind = view.findViewById(R.id.rele_remind);
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
        if (SPmanager.getChecked(getActivity())) {
            halfway_switch.setChecked(true);
        } else {
            halfway_switch.setChecked(false);
        }
        if (SPmanager.getCountdown(getActivity())) {
            sound_switch.setChecked(true);
        } else {
            sound_switch.setChecked(false);
        }
        if (SPmanager.getGuidence(getActivity())) {
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
        TimePickerDialog builder = new TimePickerDialog(getActivity(), R.style.DialogTheme,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
                        Log.d(TAG, "onTimeSet: hour " + hour);
                        Log.d(TAG, "onTimeSet: min " + min);
                        localData.set_hour(hour);
                        localData.set_min(min);
                        txt_time.setText(getFormatedTime(hour, min));
                        NotificationScheduler.setReminder(getActivity(), AlarmReceiver.class, localData.get_hour(), localData.get_min());
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
                Intent intent = new Intent(getActivity(), MainActivity.class);
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
                openApplicationMarket(getActivity(), "");
                break;
            case R.id.real_contactus:
                gotoContactUs(getActivity());
                break;
            case R.id.rele_privecy:
                privacyClick();
                break;
            case R.id.rele_term:
                termclick();
                break;
            case R.id.rele_circuit:
                final MyOptionsPickerView singlePicker = new MyOptionsPickerView(getActivity());

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
                            SPmanager.saveValue(getActivity(), "circut", items.get(options1));
                        }
                        circut_text = SPmanager.getPreference(getActivity(), "circut");
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
        SPmanager.setChecked(getActivity(), true);
        SPmanager.setGuidence(getActivity(), true);
        txt_circuit.setText(circut_text);
        dialog.dismiss();
    }

    private void openApplicationMarket(Activity activity, String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://play.google.com/store/apps/details?id=" + getActivity().getPackageName());
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
        Intent intent = new Intent(getActivity(), Privecy.class);
        startActivity(intent);
    }

    private void termclick() {
        Intent intent = new Intent(getActivity(), TermOfUse.class);
        startActivity(intent);
    }

    private void openResetdialog() {
        dialog = new Dialog(getActivity());
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
                    SPmanager.setChecked(getActivity(), true);
                } else {
                    SPmanager.setChecked(getActivity(), false);
                }
                break;
            case R.id.switch_button:
                if (isChecked) {
                    Log.d(TAG, "onCheckedChanged: true");
                    img_line.setVisibility(View.VISIBLE);
                    rele_remind.setVisibility(View.VISIBLE);
                    NotificationScheduler.setReminder(getActivity(), AlarmReceiver.class, localData.get_hour(), localData.get_min());
                    rele_timepicker.setAlpha(1f);
                } else {
                    Log.d(TAG, "onCheckedChanged: false");
                    img_line.setVisibility(View.GONE);
                    rele_remind.setVisibility(View.GONE);
                    NotificationScheduler.cancelReminder(getActivity(), AlarmReceiver.class);
                    rele_timepicker.setAlpha(0.4f);
                }
                break;
            case R.id.guidance_switch:
                if (isChecked) {
                    SPmanager.setGuidence(getActivity(), true);
                } else {
                    SPmanager.setGuidence(getActivity(), false);
                }
                break;
            case R.id.sound_switch:
                if (isChecked) {
                    SPmanager.setCountdown(getActivity(), true);
                } else {
                    SPmanager.setCountdown(getActivity(), false);
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
