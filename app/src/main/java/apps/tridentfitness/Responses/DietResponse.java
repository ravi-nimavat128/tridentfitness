package apps.tridentfitness.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DietResponse {

    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public class Result {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("diet_id")
        @Expose
        private String dietId;
        @SerializedName("diet_name")
        @Expose
        private String dietName;
        @SerializedName("diet_image")
        @Expose
        private String dietImage;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("calorie")
        @Expose
        private String calorie;
        @SerializedName("time")
        @Expose
        private String time;
        @SerializedName("qty")
        @Expose
        private String qty;
        @SerializedName("diet_food_image")
        @Expose
        private String dietFoodImage;
        @SerializedName("created_at")
        @Expose
        private String createdAt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDietId() {
            return dietId;
        }

        public void setDietId(String dietId) {
            this.dietId = dietId;
        }

        public String getDietName() {
            return dietName;
        }

        public void setDietName(String dietName) {
            this.dietName = dietName;
        }

        public String getDietImage() {
            return dietImage;
        }

        public void setDietImage(String dietImage) {
            this.dietImage = dietImage;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCalorie() {
            return calorie;
        }

        public void setCalorie(String calorie) {
            this.calorie = calorie;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getDietFoodImage() {
            return dietFoodImage;
        }

        public void setDietFoodImage(String dietFoodImage) {
            this.dietFoodImage = dietFoodImage;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

    }



}
