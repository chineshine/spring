package c.s.demo.freemarkerdemo;

import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class RTest {

	@Test
	public void test1() throws ClassNotFoundException {
		Class.forName("java.lang.String");
	}
	
	@Test
	public void test2() {
		String aString = "ssh.ip";
		int index =  aString.indexOf('.');
		System.out.println(aString.substring(0,index));
		System.out.println(aString.substring(index+1));
	}
	

	@Test
	public void test3() {
		JSONArray array = new JSONArray();
		array.add("a");
		array.add("b");
		array.add(1);
		JSONObject jsonObject =  new JSONObject();
		jsonObject.put("q", 1);
		jsonObject.put("w", "s");
		array.add(jsonObject);
		System.out.println(array.toJSONString());
		
	}
	
	@Test
	public void test4() {
		JSONObject json = new JSONObject();
		
		JSONArray sshArray = new JSONArray();
		sshArray.add("ip");
		sshArray.add("port");
		sshArray.add("user");
		json.put("ssh", sshArray);
		
		JSONObject arrayObject = new JSONObject();
		arrayObject.put("username", "1");
		arrayObject.put("password", 2);
		json.put("authObject", arrayObject);
		
		JSONArray authArray = new JSONArray();
		authArray.add("url");
		authArray.add("username");
		authArray.add("password");
		json.put("auth", authArray);
		//重复验证
		authArray.add("projectName");
		json.put("auth", authArray);
		System.out.println(json.toJSONString());
	}
	
	@Test
	public void test5() {
		JSONObject json = new JSONObject();
		JSONObject auth = new JSONObject();
		JSONObject authObject = new JSONObject();
		auth.put("obj",authObject);
		JSONArray array = new JSONArray();
		array.add("url");
		array.add("username");
		array.add("password");
		auth.put("strings", array);
		json.put("auth", auth);
		System.out.println(json.toJSONString());
	}
	
	@Test
	public void test6() {
		JSONObject jsonObject = new JSONObject();
		System.out.println(jsonObject==null);
		System.out.println(1);
		// 不会报空指针
		JSONObject a = jsonObject.getJSONObject("ssh");
		System.out.println(a==null);
		jsonObject.getJSONArray("ssh");
		System.out.println(0);
	}
}
