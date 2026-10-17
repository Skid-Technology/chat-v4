package gg.vape.account;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gg.vape.Vape;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LicenseStatusClient {
   HttpURLConnection connection;
   private final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

   public LicenseStatus generateAccount(String licenseKey) {
      try {
         URL endpoint = new URL("http://api.thealtening.com/v2/generate?key=" + licenseKey);
         this.connection = (HttpURLConnection)endpoint.openConnection();
         this.connection.setRequestMethod("GET");
         this.connection.setUseCaches(false);
         BufferedReader responseReader = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
         StringBuffer responseBody = new StringBuffer();

         String responseLine;
         while ((responseLine = responseReader.readLine()) != null) {
            responseBody.append(responseLine);
            responseBody.append('\r');
         }

         responseReader.close();
         LicenseStatusResponse response = this.gson.fromJson(responseBody.toString(), LicenseStatusResponse.class);
         if (response.getToken() != null && !response.getToken().equals("")) {
            return new LicenseStatus(response.getToken());
         }
      } catch (Exception var8) {
         Vape.logThrowable(var8);

         try {
            if (this.connection.getResponseCode() == 403) {
               return null;
            }

            System.out.println("Unhandled error code: " + this.connection.getResponseCode());
         } catch (IOException var7) {
            Vape.logThrowable(var7);
         }
      }

      return null;
   }

   private static Exception preserveException(Exception error) {
      return error;
   }
}
