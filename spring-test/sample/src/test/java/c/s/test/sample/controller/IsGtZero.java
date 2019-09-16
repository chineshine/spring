/**
 * 
 */
package c.s.test.sample.controller;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author chineshine
 * @date 2018年10月30日
 */
@Slf4j
public class IsGtZero extends BaseMatcher<Integer>{
	
	@Override
	public boolean matches(Object item) {
		return (Integer)item > 0;
	}

	@Override
	public void describeTo(Description description) {
		log.info("返回值需大于 0");
	}
	
}