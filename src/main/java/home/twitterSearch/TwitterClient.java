package home.twitterSearch;

import java.io.BufferedInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

public class TwitterClient {
	Logger logger=LogManager.getLogger(TwitterClient.class);
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
			logger.info("response code != 200");
			System.exit(1);
		}
		logger.info("tokenResp.getEntity().isRepeatable()"+tokenResp.getEntity().isRepeatable());
		JSONObject tokenjson = null;
		try {
			tokenjson = new JSONObject(IOUtils.toString(tokenResp.getEntity().getContent()));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		tokenString = tokenjson.get("access_token").toString();
		logger.info(tokenString);
	}
	static public void main(String []ss){
		new TwitterClient();
	}
}

