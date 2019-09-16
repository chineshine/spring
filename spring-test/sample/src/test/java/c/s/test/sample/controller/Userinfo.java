/**
 * 
 */
package c.s.test.sample.controller;

import java.net.URI;

/**
 * @author chineshine
 *
 */
public class Userinfo {

	private static final String SERVER_ADDRESS = "http://127.0.0.1:8090";
	
	private String uri;
	private String username;
	private String password;
	private String grantType = "password";
	private String clientId = "sso";
	private String clientSecret = "sso";
	
	public static final Userinfo admin = new Userinfo(SERVER_ADDRESS + "/api/sso/oauth/token","admin","123456");
	
	@SuppressWarnings("unused")
	private String scope;
	
	public Userinfo(String uri,String username,String password) {
		this.uri = uri;
		this.username = username;
		this.password = password;
	}
	
	public String uri() {
		return this.uri;
	}
	
	public URI toURI() {
		return URI.create(this.uri);
	}
	
	public String toBody() {
		return "grant_type=" + grantType + "&client_id=" + clientId + "&client_secret=" + clientSecret
				+ "&username=" + username + "&password=" + password;
	}
}
