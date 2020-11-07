package apps.tridentfitness.fragments;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import apps.tridentfitness.Network.ApiUtils;
import apps.tridentfitness.Network.WebApi;
import apps.tridentfitness.R;
import apps.tridentfitness.Responses.DietResponse;
import apps.tridentfitness.Utils.SharedPrefreances;
import apps.tridentfitness.adapter.DietAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DietFraagment extends Fragment {

    DietAdapter dietAdapter;
    DietAdapter dietAdapter1;
    DietAdapter dietAdapter2;
    DietAdapter dietAdapter3;
    DietAdapter dietAdapter4;
    List<DietResponse.Result> productList = new ArrayList<>();

    ProgressDialog progressDialog;

    ImageView img_break,img_snack,img_lunch,img_evening_snack,img_dinner;
    RelativeLayout breakfast_layout,layout_morning,layout_lunch,layout_snack,layout_dinner;
    RecyclerView recycle_view_breakast,recycle_view_morning,recycle_view_lunch,recycle_view_snack,recycle_view_dinner;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.diet_plan_activity, container, false);




        img_break = (ImageView)view.findViewById(R.id.img_break);
        img_snack = (ImageView)view.findViewById(R.id.img_snack);
        img_lunch = (ImageView)view.findViewById(R.id.img_lunch);
        img_evening_snack = (ImageView)view.findViewById(R.id.img_evening_snack);
        img_dinner = (ImageView)view.findViewById(R.id.img_dinner);

        breakfast_layout = (RelativeLayout)view.findViewById(R.id.breakfast_layout);
        layout_morning = (RelativeLayout)view.findViewById(R.id.layout_morning);
        layout_lunch = (RelativeLayout)view.findViewById(R.id.layout_lunch);
        layout_snack = (RelativeLayout)view.findViewById(R.id.layout_snack);
        layout_dinner = (RelativeLayout)view.findViewById(R.id.layout_dinner);

        recycle_view_breakast = (RecyclerView)view.findViewById(R.id.recycle_view_breakast);
        recycle_view_morning = (RecyclerView)view.findViewById(R.id.recycle_view_morning);
        recycle_view_lunch = (RecyclerView)view.findViewById(R.id.recycle_view_lunch);
        recycle_view_snack = (RecyclerView)view.findViewById(R.id.recycle_view_snack);
        recycle_view_dinner = (RecyclerView)view.findViewById(R.id.recycle_view_dinner);


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        WebApi webApi = ApiUtils.getClient().create(WebApi.class);
        Call<DietResponse> call  = webApi.user_all_diet_foods(SharedPrefreances.getSharedPreferenceString(getActivity(),"id"));
        call.enqueue(new Callback<DietResponse>() {
            @Override
            public void onResponse(Call<DietResponse> call, Response<DietResponse> response) {
                progressDialog.dismiss();
                if (response.body().getStatus() == 1){


                    for (int i = 0; i < response.body().getResult().size();i++){
                        productList.add(response.body().getResult().get(i));
                    }

                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                    recycle_view_breakast.setLayoutManager(mLayoutManager);
                    recycle_view_breakast.setItemAnimator(new DefaultItemAnimator());
                    dietAdapter = new DietAdapter(getContext(), productList);
                    recycle_view_breakast.setAdapter(dietAdapter);


                }else {
                    Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<DietResponse> call, Throwable t) {
                Toast.makeText(getContext(),"Server error",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });










       /* RecyclerView.LayoutManager mLayoutManager0 = new LinearLayoutManager(getContext());
        recycle_view_morning.setLayoutManager(mLayoutManager0);
        recycle_view_morning.setItemAnimator(new DefaultItemAnimator());
        dietAdapter1 = new DietAdapter(getContext(), productList);
        recycle_view_morning.setAdapter(dietAdapter1);

        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getContext());
        recycle_view_lunch.setLayoutManager(mLayoutManager1);
        recycle_view_lunch.setItemAnimator(new DefaultItemAnimator());
        dietAdapter2 = new DietAdapter(getContext(), productList);
        recycle_view_lunch.setAdapter(dietAdapter2);

        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getContext());
        recycle_view_snack.setLayoutManager(mLayoutManager2);
        recycle_view_snack.setItemAnimator(new DefaultItemAnimator());
        dietAdapter3 = new DietAdapter(getContext(), productList);
        recycle_view_snack.setAdapter(dietAdapter3);

        RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(getContext());
        recycle_view_dinner.setLayoutManager(mLayoutManager3);
        recycle_view_dinner.setItemAnimator(new DefaultItemAnimator());
        dietAdapter4 = new DietAdapter(getContext(), productList);
        recycle_view_dinner.setAdapter(dietAdapter4);*/


        layout_dinner.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (img_dinner.getDrawable().getConstantState().equals(img_dinner.getContext().getDrawable(R.drawable.ic_plus).getConstantState())){
                    img_break.setImageResource(R.drawable.ic_plus);
                    img_snack.setImageResource(R.drawable.ic_plus);
                    img_lunch.setImageResource(R.drawable.ic_plus);
                    img_evening_snack.setImageResource(R.drawable.ic_plus);
                    img_dinner.setImageResource(R.drawable.ic_minus);


                    recycle_view_breakast.setVisibility(View.GONE);
                    recycle_view_morning.setVisibility(View.GONE);
                    recycle_view_lunch.setVisibility(View.GONE);
                    recycle_view_snack.setVisibility(View.GONE);
                    recycle_view_dinner.setVisibility(View.VISIBLE);

                }else {
                    img_break.setImageResource(R.drawable.ic_plus);
                    img_snack.setImageResource(R.drawable.ic_plus);
                    img_lunch.setImageResource(R.drawable.ic_plus);
                    img_evening_snack.setImageResource(R.drawable.ic_plus);
                    img_dinner.setImageResource(R.drawable.ic_plus);

                    recycle_view_breakast.setVisibility(View.GONE);
                    recycle_view_morning.setVisibility(View.GONE);
                    recycle_view_lunch.setVisibility(View.GONE);
                    recycle_view_snack.setVisibility(View.GONE);
                    recycle_view_dinner.setVisibility(View.GONE);

                }
            }
        });

        layout_snack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (img_evening_snack.getDrawable().getConstantState().equals(img_evening_snack.getContext().getDrawable(R.drawable.ic_plus).getConstantState())){
                    img_break.setImageResource(R.drawable.ic_plus);
                    img_snack.setImageResource(R.drawable.ic_plus);
                    img_lunch.setImageResource(R.drawable.ic_plus);
                    img_evening_snack.setImageResource(R.drawable.ic_minus);
                    img_dinner.setImageResource(R.drawable.ic_plus);

                    recycle_view_breakast.setVisibility(View.GONE);
                    recycle_view_morning.setVisibility(View.GONE);
                    recycle_view_lunch.setVisibility(View.GONE);
                    recycle_view_snack.setVisibility(View.VISIBLE);
                    recycle_view_dinner.setVisibility(View.GONE);

                }else {
                    img_break.setImageResource(R.drawable.ic_plus);
                    img_snack.setImageResource(R.drawable.ic_plus);
                    img_lunch.setImageResource(R.drawable.ic_plus);
                    img_evening_snack.setImageResource(R.drawable.ic_plus);
                    img_dinner.setImageResource(R.drawable.ic_plus);

                    recycle_view_breakast.setVisibility(View.GONE);
                    recycle_view_morning.setVisibility(View.GONE);
                    recycle_view_lunch.setVisibility(View.GONE);
                    recycle_view_snack.setVisibility(View.GONE);
                    recycle_view_dinner.setVisibility(View.GONE);

                }
            }
        });

        layout_lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (img_lunch.getDrawable().getConstantState().equals(img_lunch.getContext().getDrawable(R.drawable.ic_plus).getConstantState())){
                    img_break.setImageResource(R.drawable.ic_plus);
                    img_snack.setImageResource(R.drawable.ic_plus);
                    img_lunch.setImageResource(R.drawable.ic_minus);
                    img_evening_snack.setImageResource(R.drawable.ic_plus);
                    img_dinner.setImageResource(R.drawable.ic_plus);

                    recycle_view_breakast.setVisibility(View.GONE);
                    recycle_view_morning.setVisibility(View.GONE);
                    recycle_view_lunch.setVisibility(View.VISIBLE);
                    recycle_view_snack.setVisibility(View.GONE);
                    recycle_view_dinner.setVisibility(View.GONE);

                }else {
                    img_break.setImageResource(R.drawable.ic_plus);
                    img_snack.setImageResource(R.drawable.ic_plus);
                    img_lunch.setImageResource(R.drawable.ic_plus);
                    img_evening_snack.setImageResource(R.drawable.ic_plus);
                    img_dinner.setImageResource(R.drawable.ic_plus);

                    recycle_view_breakast.setVisibility(View.GONE);
                    recycle_view_morning.setVisibility(View.GONE);
                    recycle_view_lunch.setVisibility(View.GONE);
                    recycle_view_snack.setVisibility(View.GONE);
                    recycle_view_dinner.setVisibility(View.GONE);

                }
            }
        });
        layout_morning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (img_snack.getDrawable().getConstantState().equals(img_snack.getContext().getDrawable(R.drawable.ic_plus).getConstantState())){
                    img_break.setImageResource(R.drawable.ic_plus);
                    img_snack.setImageResource(R.drawable.ic_minus);
                    img_lunch.setImageResource(R.drawable.ic_plus);
                    img_evening_snack.setImageResource(R.drawable.ic_plus);
                    img_dinner.setImageResource(R.drawable.ic_plus);


                    recycle_view_breakast.setVisibility(View.GONE);
                    recycle_view_morning.setVisibility(View.VISIBLE);
                    recycle_view_lunch.setVisibility(View.GONE);
                    recycle_view_snack.setVisibility(View.GONE);
                    recycle_view_dinner.setVisibility(View.GONE);


                }else {
                    img_break.setImageResource(R.drawable.ic_plus);
                    img_snack.setImageResource(R.drawable.ic_plus);
                    img_lunch.setImageResource(R.drawable.ic_plus);
                    img_evening_snack.setImageResource(R.drawable.ic_plus);
                    img_dinner.setImageResource(R.drawable.ic_plus);

                    recycle_view_breakast.setVisibility(View.GONE);
                    recycle_view_morning.setVisibility(View.GONE);
                    recycle_view_lunch.setVisibility(View.GONE);
                    recycle_view_snack.setVisibility(View.GONE);
                    recycle_view_dinner.setVisibility(View.GONE);

                }
            }
        });
        breakfast_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (img_break.getDrawable().getConstantState().equals(img_break.getContext().getDrawable(R.drawable.ic_plus).getConstantState())){
                    img_break.setImageResource(R.drawable.ic_minus);
                    img_snack.setImageResource(R.drawable.ic_plus);
                    img_lunch.setImageResource(R.drawable.ic_plus);
                    img_evening_snack.setImageResource(R.drawable.ic_plus);
                    img_dinner.setImageResource(R.drawable.ic_plus);

                    recycle_view_breakast.setVisibility(View.VISIBLE);
                    recycle_view_morning.setVisibility(View.GONE);
                    recycle_view_lunch.setVisibility(View.GONE);
                    recycle_view_snack.setVisibility(View.GONE);
                    recycle_view_dinner.setVisibility(View.GONE);


                }else {
                    img_break.setImageResource(R.drawable.ic_plus);
                    img_snack.setImageResource(R.drawable.ic_plus);
                    img_lunch.setImageResource(R.drawable.ic_plus);
                    img_evening_snack.setImageResource(R.drawable.ic_plus);
                    img_dinner.setImageResource(R.drawable.ic_plus);

                    recycle_view_breakast.setVisibility(View.GONE);
                    recycle_view_morning.setVisibility(View.GONE);
                    recycle_view_lunch.setVisibility(View.GONE);
                    recycle_view_snack.setVisibility(View.GONE);
                    recycle_view_dinner.setVisibility(View.GONE);

                }
            }
        });
        return view;
    }


}
