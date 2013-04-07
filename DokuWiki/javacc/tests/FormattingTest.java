package tests;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import main.EG1;
import main.ParseException;

import org.junit.Test;

public class FormattingTest {

	private EG1 parser;
	
	@Test
	public void testBold() throws ParseException {
		String text = "<sub>subscript</sub>**a**";
		parser = new EG1(stringToInputStream(text));
		parser.start();
	}

	@Test
	public void testBold2() throws ParseException {
		String text = "<sub>subscript</sub>**^%a**";
		parser = new EG1(stringToInputStream(text));
		parser.start();
	}
	
	@Test
	public void testUnderscored() throws ParseException {
		String text = "__subscript__**^%a**";
		parser = new EG1(stringToInputStream(text));
		parser.start();
	}

	private static InputStream stringToInputStream(String s) {
		return new ByteArrayInputStream(s.getBytes());
	}
}
