package apps.tridentfitness.Network;

import apps.tridentfitness.Responses.AllExerice;
import apps.tridentfitness.Responses.AllWorkout;
import apps.tridentfitness.Responses.CommonResponse;
import apps.tridentfitness.Responses.DietResponse;
import apps.tridentfitness.Responses.UserAllResponse;
import apps.tridentfitness.Responses.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WebApi {

    @FormUrlEncoded
    @POST("gim/apis/user/login")
    Call<LoginResponse> login(@Field("mobile_no") String mobile_no, @Field("password") String password, @Field("device_id") String device_id);

    @FormUrlEncoded
    @POST("gim/apis/user/all_Workout")
    Call<AllWorkout> all_Workout(@Field("day_name") String day_name);

    @FormUrlEncoded
    @POST("gim/apis/user/all_exercise")
    Call<AllExerice> all_exercise(@Field("workout_id") String workout_id);

    @FormUrlEncoded
    @POST("gim/apis/user/user_all_Workout")
    Call<UserAllResponse> user_all_Workout(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("gim/apis/user/check_device")
    Call<CommonResponse> check_device(@Field("mobile_no") String mobile_no, @Field("device_id") String device_id);


    @FormUrlEncoded
    @POST("gim/apis/user/user_all_diet_foods")
    Call<DietResponse> user_all_diet_foods(@Field("user_id") String user_id);



}
