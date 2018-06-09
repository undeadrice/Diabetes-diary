package bruc.diary.connectivity.nutritionix;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class URLConnect{

	private final String applicationId;
	private final String applicationKey;
	public static final String url = "https://api.nutritionix.com/v1_1/search/";
	public static final String query = "?fields=item_name%2Cnf_calories%2Cnf_total_fat%2Cnf_sugars&appId=";
	private final String searchFieldsCredentials;
	//  https://api.nutritionix.com/v2/search/cheddar_cheese?fields=item_name%2Cnf_calories%2Cnf_total_fat&appId=b384ccf8&appKey=cc98da509a1cfa10cf5a66c0e7def3c5

	private URL connection;
	
	
	public URLConnect(String applicationId, String applicationKey) {
		this.applicationId = applicationId;
		this.applicationKey = applicationKey;
		this.searchFieldsCredentials = query + applicationId + "&appKey=" + applicationKey;	
	}
	

	public InputStream query(String item) throws IOException {
		connection = new URL(url + item + searchFieldsCredentials);
		System.out.println(connection.getQuery());
		return connection.openStream();
	}


	

	
	
}
