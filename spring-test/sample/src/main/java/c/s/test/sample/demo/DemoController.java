/**
 * 
 */
package c.s.test.sample.demo;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chineshine
 *
 */
@RequestMapping("/demo")
public class DemoController {

	@PostMapping("/hello")
	public String post() {
		return "post";
	}
	
	@GetMapping("/hello")
	public String get() {
		return "get";
	}
	
	@DeleteMapping("hello")
	public String delete() {
		return "delete";
	}
}
