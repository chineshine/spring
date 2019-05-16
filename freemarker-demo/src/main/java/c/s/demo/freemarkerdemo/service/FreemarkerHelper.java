package c.s.demo.freemarkerdemo.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import freemarker.core.TemplateElement;
import freemarker.ext.beans.StringModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * @desc 此帮助类由于解析freemarker模板,获取变量,将模板覆盖值等
 * @author chineshine
 *
 */
@Service
@SuppressWarnings("deprecation")
public class FreemarkerHelper {

	/**
	 * @desc 此处两个变量为常量,仅限此类使用
	 */
	private String strings = "strings";
	private String obj = "obj";

	@Autowired
	private Configuration configuration;

	/**
	 * @desc 将模板中提取出的变量转为 jsonObject
	 * @param var
	 * @param parentObject
	 */
	public void variableToJsonObject(String var, JSONObject parentObject) {
		int index = var.indexOf('.');
		String entry = var.substring(0, index);
		String subEntry = var.substring(index + 1);

		JSONObject thisObject = parentObject.getJSONObject(entry);
		thisObject = thisObject != null ? thisObject : new JSONObject();
		if (hasSubObject(subEntry)) {
			JSONObject subObject = thisObject.getJSONObject(obj);
			subObject = subObject != null ? subObject : new JSONObject();
			this.variableToJsonObject(subEntry, subObject);
			thisObject.put(obj, subObject);
		} else {
			JSONArray array = thisObject.getJSONArray(strings);
			array = array != null ? array : new JSONArray();
			array.add(subEntry);
			thisObject.put(strings, array);
		}
		parentObject.put(entry, thisObject);
	}

	/**
	 * @desc 判断变量是否有子对象
	 * @param var 变量
	 * @return
	 */
	private Boolean hasSubObject(String var) {
		return var.indexOf('.') >= 0;
	}

	/**
	 * @desc 提取 freemarker 模板中变量
	 * @param uri 模板位置+名称
	 * @return
	 * @throws TemplateModelException
	 * @throws IOException
	 */
	public Set<String> referenceSet(String uri) throws TemplateModelException, IOException {
		Template template = configuration.getTemplate(uri);
		return this.referenceSet(template);
	}

	/**
	 * @desc 提取 freemarker 模板中变量
	 * @param template 模板
	 * @return
	 * @throws TemplateModelException
	 */
	public Set<String> referenceSet(Template template) throws TemplateModelException {
		Set<String> result = new HashSet<>();
		TemplateElement rootTreeNode = template.getRootTreeNode();
		for (int i = 0; i < rootTreeNode.getChildCount(); i++) {
			TemplateModel templateModel = rootTreeNode.getChildNodes().get(i);
			if (!(templateModel instanceof StringModel)) {
				continue;
			}
			Object wrappedObject = ((StringModel) templateModel).getWrappedObject();
			if (!"DollarVariable".equals(wrappedObject.getClass().getSimpleName())) {
				continue;
			}

			try {
				Object expression = getInternalState(wrappedObject, "expression");
				switch (expression.getClass().getSimpleName()) {
				case "Identifier":
					result.add(getInternalState(expression, "name").toString());
					break;
				case "DefaultToExpression":
					result.add(getInternalState(expression, "lho").toString());
					break;
				case "BuiltinVariable":
					break;
				case "Dot":
					result.add(expression.toString());
					break;
				default:
					throw new IllegalStateException("Unable to introspect variable");
				}
			} catch (NoSuchFieldException | IllegalAccessException e) {
				throw new TemplateModelException("Unable to reflect template model");
			}
		}
		return result;
	}

	private Object getInternalState(Object o, String fieldName) throws NoSuchFieldException, IllegalAccessException {
		Field field = o.getClass().getDeclaredField(fieldName);
		boolean wasAccessible = field.isAccessible();
		try {
			field.setAccessible(true);
			return field.get(o);
		} finally {
			field.setAccessible(wasAccessible);
		}
	}
}
