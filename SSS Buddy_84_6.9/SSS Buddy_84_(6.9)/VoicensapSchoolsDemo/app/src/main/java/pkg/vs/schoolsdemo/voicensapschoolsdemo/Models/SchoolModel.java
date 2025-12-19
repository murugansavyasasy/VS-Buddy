package pkg.vs.schoolsdemo.voicensapschoolsdemo.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class SchoolModel implements Serializable {

    public String schoolName;
    public String schoolAddress;
    public String schoolPhone;
    public ArrayList<String> filePaths;

    public SchoolModel(String schoolName, String schoolAddress, String schoolPhone, ArrayList<String> filePaths) {
        this.schoolName = schoolName;
        this.schoolAddress = schoolAddress;
        this.schoolPhone = schoolPhone;
        this.filePaths = filePaths != null ? filePaths : new ArrayList<>();
    }
}













//package pkg.vs.schoolsdemo.voicensapschoolsdemo.Models;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//
//public class SchoolModel implements Serializable {
//    public String schoolName;
//    public String schoolAddress;
//    public String schoolPhone;
//    public ArrayList<String> FilePath;
//
//    public SchoolModel(String schoolName, String schoolAddress, String schoolPhone, ArrayList<String> FilePath) {
//        this.schoolName = schoolName;
//        this.schoolAddress = schoolAddress;
//        this.schoolPhone = schoolPhone;
//        this.FilePath = FilePath;
//    }
//}
//
