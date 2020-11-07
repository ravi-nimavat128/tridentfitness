package apps.tridentfitness.activities;

public class ClientModel {
    String img;
    String name;
    String time;

    public ClientModel(String img, String name, String time) {
        this.img = img;
        this.name = name;
        this.time = time;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
