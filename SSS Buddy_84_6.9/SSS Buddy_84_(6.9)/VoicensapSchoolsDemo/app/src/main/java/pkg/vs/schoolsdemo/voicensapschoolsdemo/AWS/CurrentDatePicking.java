package pkg.vs.schoolsdemo.voicensapschoolsdemo.AWS;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CurrentDatePicking {

    public static String getCurrentDateTime() {
        // yyyy-MM-dd_HH-mm-ss is filename safe
        SimpleDateFormat sdf =
                new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault());

        Date currentDate = new Date();
        return sdf.format(currentDate);
    }
}
