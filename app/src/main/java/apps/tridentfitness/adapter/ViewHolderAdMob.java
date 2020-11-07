package apps.tridentfitness.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import apps.tridentfitness.R;


class ViewHolderAdMob extends RecyclerView.ViewHolder {
    ViewHolderAdMob(final View view) {
        super(view);
        final AdView mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                view.findViewById(R.id.rel_ad).setVisibility(View.VISIBLE);
// Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.e("Home", "onAdFailedToLoad: " + errorCode);
// Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
// Code to be executed when an ad opens an overlay that
// covers the screen.
            }

            @Override
            public void onAdLeftApplication() {
// Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
// Code to be executed when the user is about to return
// to the app after tapping on an ad.
            }
        });
    }
}

