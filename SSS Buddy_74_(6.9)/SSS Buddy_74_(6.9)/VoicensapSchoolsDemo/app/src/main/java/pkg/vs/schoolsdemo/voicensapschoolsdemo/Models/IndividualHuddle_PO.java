package pkg.vs.schoolsdemo.voicensapschoolsdemo.Models;

/**
 * Created by devi on 5/16/2019.
 */

public class IndividualHuddle_PO {

        String Country;
        String RatePerMinuteInbound;



        public IndividualHuddle_PO(String country, String rateperminInbound ){
            this.Country=country;
            this.RatePerMinuteInbound=rateperminInbound;


        }
        public String getCountry() {
            return Country;

        }

        public void setCountry(String country) {
            this.Country = country;

        }
        public String getRatePerMinuteInbound() {
            return RatePerMinuteInbound;

        }

        public void setRatePerMinuteInbound(String ratePerMinuteInbound) {
            this.RatePerMinuteInbound = ratePerMinuteInbound;

        }

    }

