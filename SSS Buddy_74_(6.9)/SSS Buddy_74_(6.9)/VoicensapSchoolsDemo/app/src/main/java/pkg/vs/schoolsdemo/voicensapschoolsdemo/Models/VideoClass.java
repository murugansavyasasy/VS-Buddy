package pkg.vs.schoolsdemo.voicensapschoolsdemo.Models;

import java.io.Serializable;

public class VideoClass implements Serializable {

    String VideoName, VideoURL, Videotype;

    public String getVideoName() {
        return VideoName;
    }

    public void setVideoName(String videoName) {
        VideoName = videoName;
    }


    public String getVideoLink() {
        return VideoURL;
    }


    public void setVideoLink(String videoLink) {
        VideoURL = videoLink;
    }

    public String getVideotype() {
        return Videotype;
    }


    public void setVideotype(String videotype) {
        videotype = videotype;
    }

    public VideoClass(String VideoName, String VideoLink, String videotype) {

        this.VideoName = VideoName;
        this.VideoURL = VideoLink;
        this.Videotype = videotype;


    }
}
