package home.twitterSearch;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

public class TwitterClient {
	DefaultHttpClient httpClient;
	private String tokenString;
	public TwitterClient(){
		httpClient = new DefaultHttpClient();
		HttpPost postRequest = new HttpPost(
				"https://api.twitter.com/oauth2/token");
		postRequest.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		postRequest.addHeader("Authorization", "Basic Q0xadjFpVU91M3JYMTFYRlpDam9Db1RFYjpXdmF6eFp5UDhvdzQ4TklhcHBUOGVNcnVnWk9CSURnMXN2cDFLV0hIQWw0REJDQjUwWQ==");
		postRequest.setEntity(new ByteArrayEntity("grant_type=client_credentials".getBytes()));
		HttpResponse tokenResp=null;
		try {
			tokenResp= httpClient.execute(postRequest);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		if(tokenResp.getStatusLine().getStatusCode()!=200){
			System.out.println("response code != 200");
			System.exit(1);
		}
		JSONObject tokenjson = new JSONObject(tokenResp.getEntity().toString());
		tokenString = tokenjson.get("access_token").toString();
		System.out.println(tokenString);
	}
	static public void main(String []ss){
		new TwitterClient();
	}
}

