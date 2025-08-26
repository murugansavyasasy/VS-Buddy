package pkg.vs.schoolsdemo.voicensapschoolsdemo.Models;

import java.io.Serializable;
import java.util.List;

public class AlertClass implements Serializable {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2
         * alert_type : calls_pending
         * alert_title : Calls Pending
         * alert_content : 8  are pending for more than 20minutes
         * created_on :
         * created_from : alert_sms_for_pending_calls
         * extrainfo1 : null
         * extrainfo2 : null
         */

        private int id;
        private String alert_type;
        private String alert_title;
        private String alert_content;
        private String created_on;
        private String created_from;
        private String extrainfo1;
        private String extrainfo2;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAlert_type() {
            return alert_type;
        }

        public void setAlert_type(String alert_type) {
            this.alert_type = alert_type;
        }

        public String getAlert_title() {
            return alert_title;
        }

        public void setAlert_title(String alert_title) {
            this.alert_title = alert_title;
        }

        public String getAlert_content() {
            return alert_content;
        }

        public void setAlert_content(String alert_content) {
            this.alert_content = alert_content;
        }

        public String getCreated_on() {
            return created_on;
        }

        public void setCreated_on(String created_on) {
            this.created_on = created_on;
        }

        public String getCreated_from() {
            return created_from;
        }

        public void setCreated_from(String created_from) {
            this.created_from = created_from;
        }

        public String getExtrainfo1() {
            return extrainfo1;
        }

        public void setExtrainfo1(String extrainfo1) {
            this.extrainfo1 = extrainfo1;
        }

        public String getExtrainfo2() {
            return String.valueOf(extrainfo2);
        }

        public void setExtrainfo2(String extrainfo2)
        {
            this.extrainfo2 = extrainfo2;
        }
    }
}
