package home.twitterSearch;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class TwitterClient {
	static Logger logger=LogManager.getLogger(TwitterClient.class);
	DefaultHttpClient httpClient;
	private String tokenString;
	BufferedWriter bw = null;
	public TwitterClient() throws IOException{
		bw =new BufferedWriter(new FileWriter("./ids.txt"));
		httpClient = new DefaultHttpClient();
		HttpPost postRequest = new HttpPost(
				"https://api.twitter.com/oauth2/token");
		postRequest.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		postRequest.addHeader("Authorization", "Basic Q0xadjFpVU91M3JYMTFYRlpDam9Db1RFYjpXdmF6eFp5UDhvdzQ4TklhcHBUOGVNcnVnWk9CSURnMXN2cDFLV0hIQWw0REJDQjUwWQ==");
		postRequest.setEntity(new ByteArrayEntity("grant_type=client_credentials".getBytes()));
		HttpResponse tokenResp=null;
		tokenResp= httpClient.execute(postRequest);
		if(tokenResp.getStatusLine().getStatusCode()!=200){
			logger.error("response code != 200");
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
		
		long l =766196915214090240l;
		logger.info(Long.MAX_VALUE+"xxx");
		logger.info(l+"xxx");
	}
	final static int COUNT_SIZE=50;
	int testcount=0;
	public void getTargetFansList(String twitterName,long curl) throws IOException{
		HttpGet getRequest=new HttpGet("https://api.twitter.com/1.1/followers/ids.json?cursor="+curl+"&screen_name="+twitterName+"&count="+COUNT_SIZE);
		JSONObject json = getResponseJson(getRequest);
		JSONArray jsonArray = json.getJSONArray("ids");
		long nextCur = json.getLong("next_cursor");
		if (nextCur==0){
			return;
		}else{
			if (5<testcount++){
				return;
			}
			for (Object o : jsonArray) {
				bw.write(o.toString());
				bw.newLine();
				logger.info(o);
			}
			bw.flush();
			getTargetFansList(twitterName,nextCur);
		}
	}
	
	public JSONObject getResponseJson(HttpRequestBase rb){
		rb.addHeader("Authorization", "Bearer "+tokenString);
		rb.addHeader("Accept", "application/json; charset=utf-8");
		JSONObject tokenjson = null;
		try {
			HttpResponse execute=httpClient.execute(rb);
			if(execute.getStatusLine().getStatusCode()!=200){
				logger.error("response code != 200");
				System.exit(1);
			}
			tokenjson = new JSONObject(IOUtils.toString(execute.getEntity().getContent()));
		} catch (IOException e) {
			logger.error(e);
		}
		return tokenjson;
	}
	static public void main(String []ss){
		try {
			TwitterClient c = new TwitterClient();
			c.getTargetFansList("KwokMiles",-1L);
			c.end();
		} catch (IOException e) {
			logger.error(e);
		}
	}

	private void end() throws IOException {
		bw.flush();
		httpClient=null;
		
	}
}

