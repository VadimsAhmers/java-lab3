package my.oktmo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OktmoAnalyzer {

    public void findPlacesSixLettersOrLesser(OktmoData data){

        Pattern pattern = Pattern.compile("[А-ЯЁа-яё]{1,2}(ово)");

        for (Place p : data.places){
            Matcher matcher = pattern.matcher(p.name);
            if (matcher.matches()) System.out.println(p);
        }
    }

    public void findPlacesBeginningAndEndingTheSameConsonant(OktmoData data){

        Pattern pattern = Pattern.compile("([А-Я]).*\\1", Pattern.CASE_INSENSITIVE + Pattern.UNICODE_CASE);

        for (Place p : data.places){
            Matcher matcher = pattern.matcher(p.name);
            if (matcher.matches()) System.out.println(p);
        }
    }
}
