import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class URLProperties {

	public String checkAuthUrl;
	public String authUrl;
	public String tableListUrl;
	public String addTableUrl;
	public String changeTimeUrl;
	public String getClientLastIdUrl;

	public URLProperties() {
		initProperty();
	}

	public void initProperty() {

		Properties prop = new Properties();
		Map<String, String> property = new HashMap<>();
		try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
			prop.load(input);

			checkAuthUrl = prop.getProperty("checkAuth");
			authUrl = prop.getProperty("auth");
			tableListUrl = prop.getProperty("tableList");
			addTableUrl = prop.getProperty("addTable");
			changeTimeUrl = prop.getProperty("changeTime");
			getClientLastIdUrl = prop.getProperty("lastClientId");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
