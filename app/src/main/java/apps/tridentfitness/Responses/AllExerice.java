package apps.tridentfitness.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllExerice {


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
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("workout_id")
        @Expose
        private String workoutId;
        @SerializedName("workout_name")
        @Expose
        private String workoutName;
        @SerializedName("time")
        @Expose
        private String time;
        @SerializedName("sets")
        @Expose
        private String sets;
        @SerializedName("keps")
        @Expose
        private String keps;
        @SerializedName("exercise_gif")
        @Expose
        private String exerciseGif;
        @SerializedName("exercise_image")
        @Expose
        private String exerciseImage;
        @SerializedName("created_at")
        @Expose
        private String createdAt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getWorkoutId() {
            return workoutId;
        }

        public void setWorkoutId(String workoutId) {
            this.workoutId = workoutId;
        }

        public String getWorkoutName() {
            return workoutName;
        }

        public void setWorkoutName(String workoutName) {
            this.workoutName = workoutName;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getSets() {
            return sets;
        }

        public void setSets(String sets) {
            this.sets = sets;
        }

        public String getKeps() {
            return keps;
        }

        public void setKeps(String keps) {
            this.keps = keps;
        }

        public String getExerciseGif() {
            return exerciseGif;
        }

        public void setExerciseGif(String exerciseGif) {
            this.exerciseGif = exerciseGif;
        }

        public String getExerciseImage() {
            return exerciseImage;
        }

        public void setExerciseImage(String exerciseImage) {
            this.exerciseImage = exerciseImage;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

    }
}
