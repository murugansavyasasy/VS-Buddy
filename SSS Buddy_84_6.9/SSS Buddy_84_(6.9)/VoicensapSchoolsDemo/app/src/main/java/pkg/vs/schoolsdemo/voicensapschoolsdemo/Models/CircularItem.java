package pkg.vs.schoolsdemo.voicensapschoolsdemo.Models;

import java.util.ArrayList;

public class CircularItem {
    public String title;
    public String description;
    public ArrayList<FileItem> filePaths = new ArrayList<>();

    public CircularItem(String title, String description, ArrayList<FileItem> filePaths) {
        this.title = title;
        this.description = description;
        if (filePaths != null) this.filePaths = filePaths;
    }

    public static class FileItem {
        public String url;
        public String type;

        public FileItem(String url, String type) {
            this.url = url;
            this.type = type != null ? type : "IMAGE";
        }
    }
}