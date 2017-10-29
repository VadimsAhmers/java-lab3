
package my.oktmo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class OktmoMain {

 public static void main(String[] args) {
   int lineCount=0;
   try (
       BufferedReader br = new BufferedReader(
         new InputStreamReader(
           new FileInputStream("data-201710.csv")
           , "cp1251") 
           // читаем файл из двоичного потока 
           // в виде текста с нужной кодировкой
         ) 
   ) {
       String s;
       while ((s=br.readLine()) !=null ) { // пока readLine() возвращает не null
           lineCount++;
           System.out.println(s);
           if (lineCount==20) break; // пример частичного чтения первых 20 строк
       }
   } 
   catch (IOException ex) {
       System.out.println("Reading error in line "+lineCount);
       ex.printStackTrace();
   }
 }
 
}
