
package my.oktmo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class OktmoMain {

 public static void main(String[] args) {

     OktmoData data = new OktmoData();
     OktmoReader reader = new OktmoReader();

     reader.readPlaces("data-201710.csv", data);

     data.printContent();
 }
 
}
