package c.s.demo.freemarkerdemo.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.core.TemplateElement;
import freemarker.ext.beans.StringModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * @author chineshine
 *
 */
@SuppressWarnings("deprecation")
@Service("freemarkerService")
public class FreemarkerService {

	@Autowired
	private Configuration configuration;

	/**
	 * 将值和模板组合进行解析成字符串
	 * 
	 * @param map 键值对
	 * @param uri  模板位置加名称
	 * @return String
	 * @throws IOException
	 * @throws TemplateException
	 */
	public String parseToString(Map<String, Object> map, String uri) throws IOException, TemplateException {
		Template template = configuration.getTemplate(uri);
		String str = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
		System.out.println(str);
		return str;
	}

	/**
	 * 将模板解析后写入文件
	 * @param filePath 写出的文件具体位置
	 * @param map
	 * @param template
	 * @throws IOException 
	 * @throws TemplateException 
	 */
	public void writeToFile(String filePath, Map<String, Object> map, String template) throws IOException, TemplateException {
			FileOutputStream fos = new FileOutputStream(filePath);
			String content = this.parseToString(map, template);
			fos.write(content.getBytes());
			fos.close();
	}

	public Set<String> referenceSet(String uri) throws TemplateModelException, IOException {
		Template template = configuration.getTemplate(uri);
		return this.referenceSet(template);
	}

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