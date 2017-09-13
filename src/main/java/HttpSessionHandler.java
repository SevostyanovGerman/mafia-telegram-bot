import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class HttpSessionHandler {

	private Map<String, String> authDetails;
	private final static Logger logger = LogManager.getRootLogger();
	URLProperties initProperty = new URLProperties();

	public HttpSessionHandler() {
	}

	public HttpSessionHandler(Map<String, String> authDetails) {

		this.authDetails = authDetails;
	}

	public void setAuthDetails(Map<String, String> authDetails) {

		this.authDetails = authDetails;
	}

	public User hasUserWithThisLoginOnServer() {

		User user = new User();
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(initProperty.checkAuthUrl);
		ObjectMapper objectMapper = new ObjectMapper();

		// Request parameters and other properties.
		List<NameValuePair> params = new ArrayList<>(2);

		params.add(new BasicNameValuePair("username", authDetails.get("username")));
		params.add(new BasicNameValuePair("password", authDetails.get("password")));
		try {
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			HttpResponse response = httpclient.execute(httppost);
			String json = EntityUtils.toString(response.getEntity(), "UTF-8");
			user = objectMapper.readValue(json, User.class);
		} catch (UnsupportedEncodingException e) {
			logger.error("Problem with encoding: " + e.getLocalizedMessage());
		} catch (IOException e) {
			logger.error("IOException: " + e.getLocalizedMessage());
		}
		return user;
	}

	private HttpClient connectToServer() {

		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(initProperty.authUrl);

		List<NameValuePair> params = new ArrayList<>(2);

		params.add(new BasicNameValuePair("username", authDetails.get("username")));
		params.add(new BasicNameValuePair("password", authDetails.get("password")));
		try {
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			httpclient.execute(httppost);
		} catch (UnsupportedEncodingException e) {
			logger.error("Problem with encoding: " + e.getLocalizedMessage());
		} catch (IOException e) {
			logger.error("IOException: " + e.getLocalizedMessage());
		}
		return httpclient;
	}

	public List<Board> getListOfBoards() throws IOException {

		HttpClient httpClient = connectToServer();
		HttpGet httpGet = new HttpGet(initProperty.tableListUrl);
		ObjectMapper objectMapper = new ObjectMapper();
		List<Board> result = new ArrayList<>();

		try {
			HttpResponse response = httpClient.execute(httpGet);
			String json = EntityUtils.toString(response.getEntity(), "UTF-8");
			result = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Board.class));

		} catch (ClientProtocolException e) {
			logger.error("Problem with protocol: " + e.getLocalizedMessage());
		}
		return result;
	}

	public List<String> getBoardListName() throws IOException {

		return getListOfBoards().stream().map(Board::toString).collect(Collectors.toList());
	}

	public List<String> getBoardListId() throws IOException {

		return getListOfBoards().stream().map(Board::getId).map(String::valueOf).collect(Collectors.toList());
	}

	public Board getBoardById(String id) throws IOException {

		return getListOfBoards().get(Integer.valueOf(id) - 1);
	}

	public void sendRequestOnAddTable(Map<String, String> tableDetails) {

		HttpClient httpClient = connectToServer();
		HttpPost httpPost = new HttpPost(initProperty.addTableUrl);

		List<NameValuePair> params = new ArrayList<>(2);

		params.add(new BasicNameValuePair("boardId", tableDetails.get("boardId")));
		params.add(new BasicNameValuePair("number", tableDetails.get("number")));
		params.add(new BasicNameValuePair("description", tableDetails.get("description")));

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();

			EntityUtils.consume(entity);
		} catch (IOException e) {
			logger.error("IOException: " + e.getLocalizedMessage());
		}


	}

	public void sendEditClientTimeStart(Map<String, String> tableDetails) {

		HttpClient httpClient = connectToServer();
		HttpPost httpPost = new HttpPost(initProperty.changeTimeUrl);

		//Этот метод вызывается после отправки клиентов на сервер и затем меняет время, поэтому getClientLastId возвращает последний ID учитывая новые
		int nextIdAfterLastClientId = getClientLastId() + 1;
		int amountOfNewClients = Integer.parseInt(tableDetails.get("number"));
		int nextPositionAfterOldClientId = nextIdAfterLastClientId - amountOfNewClients;//

		for (int i = nextPositionAfterOldClientId; i < nextIdAfterLastClientId; ++i) {
			List<NameValuePair> params = new ArrayList<>(2);
			String clientId = String.valueOf(i);

			params.add(new BasicNameValuePair("clientId", clientId));
			params.add(new BasicNameValuePair("hours", tableDetails.get("hours")));
			params.add(new BasicNameValuePair("minutes", tableDetails.get("minutes")));

			try {
				httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
				EntityUtils.consume(httpClient.execute(httpPost).getEntity());//release connection
			} catch (ClientProtocolException e) {
				logger.error("Problem with protocol: " + e.getLocalizedMessage());
			} catch (IOException e) {
				logger.error("IOException: " + e.getLocalizedMessage());
			}
		}

	}

	private int getClientLastId() {

		int lastId = 0;
		HttpClient httpClient = connectToServer();
		HttpGet httpGet = new HttpGet(initProperty.getClientLastIdUrl);

		try {
			HttpResponse response = httpClient.execute(httpGet);
			lastId = Integer.parseInt(EntityUtils.toString(response.getEntity(), "UTF-8"));
		} catch (ClientProtocolException e) {
			logger.error("Problem with protocol: " + e.getLocalizedMessage());
		} catch (IOException e) {
			logger.error("IOException: " + e.getLocalizedMessage());
		}
		return lastId;
	}
}