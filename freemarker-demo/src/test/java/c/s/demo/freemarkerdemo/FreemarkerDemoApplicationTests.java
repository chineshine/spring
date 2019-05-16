package c.s.demo.freemarkerdemo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import c.s.demo.freemarkerdemo.service.FreemarkerService;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FreemarkerDemoApplicationTests {
	
	@Autowired
	private FreemarkerService freemarkerService;

	
	/**
	 1.  获取 .ftl 模板文件
	 2. 将 .ftl 文件解析并获取变量,方法已有 {@link FreemarkerService#referenceSet(freemarker.template.Template)}
	 3. 根据解析的变量通过 CMDB 去获取值
	 4.将变量的值注入到 模板中 {@link #test2()}
	 */
	
	@Test
	public void test2() throws IOException, TemplateException {
		JSONObject ssh = new JSONObject();
		Map<String, Object> map = new HashMap<>();
		ssh.put("ip", "10.211.55.3");
		ssh.put("user", "sin");
		map.put("ssh", ssh);
		JSONObject osAuth = new JSONObject();
		osAuth.put("action", "start");
		osAuth.put("os_auth_url", "http://172.16.120.251:5000/v3");
		osAuth.put("os_auth_userName", "guan.xiaojue");
		osAuth.put("os_auth_Password", "123456");
		osAuth.put("os_auth_projectName", "wx_devops");
		osAuth.put("os_auth_domainName", "devops");
		osAuth.put("serverId", "2af0606d-c6de-48d9-89f3-2b538639dbd9");
		map.put("os_auth", osAuth);
		freemarkerService.writeToFile("e://a", map, "a.ftl");
	}
	
	/**
	 1. 获取模板解析并获取模板中所有变量
	 2. 递归解析子对象 
	 */
	@Test
	public void test3() throws TemplateModelException, IOException {
		Set<String> vars = freemarkerService.referenceSet("inventory.ftl");
		JSONObject json = new JSONObject();
		for(String var:vars) {
			System.out.println(var);
			this.variables(var, json);
		}
		System.out.println(json.toJSONString());
	}
	
	
	public void variables(String var , JSONObject parentObject) {
		int index =  var.indexOf('.');
		String entry = var.substring(0, index);
		String subEntry = var.substring(index+1);
		
		JSONObject thisObject = parentObject.getJSONObject(entry);
		thisObject = thisObject != null?thisObject:new JSONObject();
		if(hasSubObject(subEntry)) {
			JSONObject subObject = thisObject.getJSONObject("obj");
			subObject = subObject != null ? subObject: new JSONObject();
			this.variables(subEntry, subObject);
			thisObject.put("obj", subObject);
		}else {
			JSONArray array = thisObject.getJSONArray("strings");
			array = array!=null?array:new JSONArray();
			array.add(subEntry);
			thisObject.put("strings", array);
		}
		parentObject.put(entry, thisObject);
		
		
	}
	
	public Boolean hasSubObject(String var) {
		return var.indexOf('.') >= 0;
	}
	
	
	/**
	 1. 传递一个顶级对象名称:如 ssh
	 2. 根据这个顶级对象名称获取该对象所有的值,包括子对象的值
	 3. 关联对象的对应关系的规范定义
	 */
}
