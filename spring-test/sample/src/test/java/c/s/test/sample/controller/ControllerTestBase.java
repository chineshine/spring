/**
 * 
 */
package c.s.test.sample.controller;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author chineshine
 * @date 2018年10月30日
 */
//@DirtiesContext
//@ActiveProfiles(profiles = "unit-test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class ControllerTestBase {

	public abstract TestRestTemplate getTestRestTemplate();

	protected String token;

	private String token() {
		// 获取 oauth2 token
//		Userinfo userinfo = Userinfo.admin;
//		ResponseEntity<OAuth2AccessToken> responseEntity = testRestTemplate.exchange(RequestEntity
//				.post(userinfo.toURI()).contentType(MediaType.APPLICATION_FORM_URLENCODED).body(userinfo.toBody()),
//				OAuth2AccessToken.class);
//		OAuth2AccessToken auth2AccessToken = responseEntity.getBody();
//		return auth2AccessToken.getTokenType() + " " + auth2AccessToken.getValue();
		return "";
	}

	@Before
	public void setup() {
		if (this.token == null) {
			this.token = token();
		}
	}

	protected HttpHeaders getHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
		return headers;
	}

	protected <T, R> T createOrUpdate(String uri, R body, HttpMethod method, Class<T> responseType) {
		HttpHeaders headers = this.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<R> requestEntity = new HttpEntity<>(body, headers);
		ResponseEntity<T> responseEntity = getTestRestTemplate().exchange(uri, method, requestEntity, responseType);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		return responseEntity.getBody();
	}

	protected <T> T searchOrDelete(String uri, Map<String, Object> variables, HttpMethod method,
			Class<T> responseType) {
		HttpHeaders headers = this.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<T> responseEntity = getTestRestTemplate().exchange(uri, method, requestEntity, responseType,
				variables);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		return responseEntity.getBody();
	}

	protected <T> T search(String uri, Map<String, Object> variables, Class<T> responseType) {
		// get的 url写法: url? a={a}&b={b}
		return this.searchOrDelete(uri, variables, HttpMethod.GET, responseType);
	}

	protected <T> T delete(String uri, Map<String, Object> variables, Class<T> responseType) {
		return this.searchOrDelete(uri, variables, HttpMethod.DELETE, responseType);
	}

	protected <T, E> T batchDelete(String uri, Collection<E> body, Class<T> responseType) {
		HttpHeaders headers = getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Collection<E>> requestEntity = new HttpEntity<>(body, headers);
		ResponseEntity<T> responseEntity = getTestRestTemplate().exchange(uri, HttpMethod.DELETE, requestEntity,
				responseType);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		return responseEntity.getBody();
	}

}
