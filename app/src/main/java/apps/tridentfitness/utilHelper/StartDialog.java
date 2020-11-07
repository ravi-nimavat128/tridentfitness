package apps.tridentfitness.utilHelper;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import apps.tridentfitness.activities.RestActivity;
import apps.tridentfitness.R;


public class StartDialog extends Dialog {
    TextView txt_title, txt_content;
    Button btn_yes, btn_no;

    public StartDialog(final Context context, final int themeResId, final String layout, final String time, final String calories, final String total_exercise, final String name) {
        super(context, themeResId);
        setContentView(R.layout.activity_start_dialog);
        init();

        if (null != getWindow())
            getWindow().setBackgroundDrawableResource(R.color.transparent);

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), RestActivity.class);
                intent.putExtra("layout", layout);
                intent.putExtra("time", time);
                intent.putExtra("cal", calories);
                intent.putExtra("tot_ex", total_exercise);
                intent.putExtra("name", name);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(intent);
                dismiss();

            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void init() {
        txt_title = findViewById(R.id.txt_title);
        txt_content = findViewById(R.id.txt_content);
        btn_yes = findViewById(R.id.btn_yes);
        btn_no = findViewById(R.id.btn_no);
    }
}
