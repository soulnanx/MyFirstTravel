package br.com.epicdroid.travel.utils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.net.SocketTimeoutException;

public class ServiceUtils {

	public static String get(String path) throws Exception {
		String serviceReturn = null;

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(path);
			httpGet.addHeader("Content-Type", "application/json;");
			httpGet.setParams(setTimeout());

			HttpResponse response = httpclient.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();

			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
                serviceReturn = out.toString();
			} else {
				response.getEntity().getContent().close();
				throw new Exception(statusLine.getReasonPhrase());
			}
		} catch (SocketTimeoutException e) {
			throw new Exception();
		}
		return serviceReturn;
	}

    public static HttpParams setTimeout() {
        int ONE_MINUTE = 60000;
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, ONE_MINUTE);
        HttpConnectionParams.setSoTimeout(httpParameters, ONE_MINUTE);

        return httpParameters;
    }
}
