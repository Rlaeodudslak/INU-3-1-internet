package test;

import java.util.ArrayList;
import java.util.List;


import homeworkpro.Crawler;
import org.junit.gen5.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class CrawlerTest {
	@Test
	public void test1() {
		List<String> test = Crawler.GetGmarketToken();
		List<String> expected = new ArrayList<String>();
		expected.add("123");
		expected.add("123");
		assertArrayEquals(test.toArray(),expected.toArray());
	}
	

	@Test
	public void test2() {
		List<String> test = Crawler.GetGmarketToken();
		List<String> expected = new ArrayList<String>();
		expected.add(test.get(0));
		expected.add("123");
		assertArrayEquals(test.toArray(),expected.toArray());
	}
}
