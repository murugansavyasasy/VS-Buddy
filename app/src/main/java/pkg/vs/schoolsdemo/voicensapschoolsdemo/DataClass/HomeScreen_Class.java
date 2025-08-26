package pkg.vs.schoolsdemo.voicensapschoolsdemo.DataClass;

/**
 * Created by devi on 7/9/2019.
 */

public class HomeScreen_Class {

    private String name;
    private int image;
    private  int id_;
    public HomeScreen_Class(String name, int img,int id) {
        this.name = name;
        this.image = img;
        this.id_ = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int thumbnail) {
        this.image= thumbnail;
    }

    public int getId() {
        return image;
    }

    public void setId(int id) {
        this.image= id;
    }


}
