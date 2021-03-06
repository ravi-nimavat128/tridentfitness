package apps.tridentfitness.utilHelper;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

public abstract class CountDownTimer {

    private final long mMillisInFuture;
    private final long mCountdownInterval;
    private long mStopTimeInFuture;

    private boolean mCancelled = false;

    public CountDownTimer(long millisInFuture, long countDownInterval) {
        mMillisInFuture = millisInFuture;
        mCountdownInterval = countDownInterval;
    }

    public synchronized final void cancel() {
        mCancelled = true;
        mHandler.removeMessages(MSG);
    }

    public synchronized final CountDownTimer start() {
        mCancelled = false;
        if (mMillisInFuture <= 0) {
            onFinish();
            return this;
        }
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInFuture;
        onTick(mMillisInFuture);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG), mCountdownInterval);
        return this;
    }

    public abstract void onTick(long millisUntilFinished);

    public abstract void onFinish();

    private static final int MSG = 1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            synchronized (CountDownTimer.this) {
                if (mCancelled)
                    return;
                final long millisLeft = mStopTimeInFuture - SystemClock.elapsedRealtime();
                if (millisLeft <= 0) {
                    onFinish();
                } else {
                    onTick(millisLeft);
                    sendMessageDelayed(obtainMessage(MSG), mCountdownInterval);
                }
            }
        }
    };
}