package tests;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.io.ObjectInputStream.GetField;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import main.EG1;
import main.ParseException;

import org.junit.Test;

@SuppressWarnings("unused")
public class PrimitiveTest {

	private static class TestContent {
		public String input;
		public String output;

		public TestContent(String input, String output) {
			this.input = input;
			this.output = output;
		}

	}

	private static final int METHOD_NAME_WIDTH = 20;
	private static final String blanks;

	private static OutputStreamWriter outStream;
	private static Writer out;

	static {
		try {
			outStream = new OutputStreamWriter(System.out, "UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		out = outStream;

		char[] c = new char[METHOD_NAME_WIDTH];
		Arrays.fill(c, ' ');
		blanks = new String(c);
	}

	private static final String image = "{{www.g.pow ?10x5|ala}}";
	private static final String url = "www.ala.pow";
	private static final String wiki = "www:ala:pow";
	private static final String word = "abc";

	private static EG1 parser;

	@Test
	public void testWord() throws ParseException, IOException {
		test();
	}

	@Test
	public void testNumber() throws ParseException, IOException {
		test();
	}

	@Test
	public void testPlainText1() throws ParseException, IOException {
		// niby-bold
		test();
	}

	@Test
	public void testBold() throws ParseException, IOException {
		test();
	}

	@Test
	public void testBold1() throws ParseException, IOException {
		test();
	}

	@Test
	public void testBold2() throws ParseException, IOException {
		test();
	}
	
	@Test
	public void testBold3() throws ParseException, IOException {
		test();
	}

	@Test
	public void testMixedMarkup() throws ParseException, IOException {
		test();
	}

	@Test
	public void testSimple1() throws ParseException, IOException {
		test();
		//TODO Tu musi byc zle, poniewaz // lapie otwarcie <i> a nie ma drugiego //
		//blad uzytkownika
	}

	@Test
	public void testSimple2() throws ParseException, IOException {
		test();
	}
	
	@Test
	public void testSimple3() throws ParseException, IOException {
		test();
	}
	
	@Test
	public void testSimpleNested() throws ParseException, IOException {
		test();
	}

	@Test
	public void testItalics1() throws ParseException, IOException {
		test();
	}
	
	@Test
	public void testUnderline1() throws ParseException, IOException {
		test();
	}

	@Test
	public void testListSimple2() throws ParseException, IOException {
		test();
	}

	@Test
	public void testEmail() throws ParseException, IOException {
		test();
	}
	
	@Test
	public void testUrl() throws ParseException, IOException {
		test();
	}

	@Test
	public void testHorizontalBreak() throws ParseException, IOException {
		test();
	}

	@Test
	public void testQuote() throws ParseException, IOException {
		test();
	}

	@Test
	public void testQuoteSimple() throws ParseException, IOException {
		test();
	}
	
	@Test
	public void testCode() throws ParseException, IOException {
		test();
	}
	
	@Test
	public void testLinks() throws ParseException, IOException {
		test();
	}
	
	@Test
	public void testListSimple() throws ParseException, IOException {
		test();
	}

	@Test
	public void testHeadline() throws ParseException, IOException {
		test();
	}

	@Test
	public void testPara() throws ParseException, IOException {
		test();
	}

	@Test
	public void testPlainText() throws ParseException, IOException {
		test();
	}

	@Test
	public void testImage() throws ParseException, IOException {
		test();
	}
	
	@Test
	public void testDel1() throws ParseException, IOException {
		test();
	}
	
	@Test
	public void testDel2() throws ParseException, IOException {
		test();
	}
	
	@Test
	public void testSup1() throws ParseException, IOException {
		test();
	}
	
	@Test
	public void testSub1() throws ParseException, IOException {
		test();
	}
	
	@Test
	public void testSubMix() throws ParseException, IOException {
		test();
	}
	
	@Test
	public void testNowiki1() throws ParseException, IOException {
		test();
	}
	
	@Test
	public void testNowiki2() throws ParseException, IOException {
		test();
	}
	
	@Test
	public void testNowiki3() throws ParseException, IOException {
		test();
	}
	
	@Test
	public void testCode1() throws ParseException, IOException {
		test();
	}
	
	@Test
	public void testCode2() throws ParseException, IOException {
		test();
	}
	
	@Test
	public void testFile1() throws ParseException, IOException {
		test();
	}

	private static void test() throws IOException, ParseException {
		String methodName = Thread.currentThread().getStackTrace()[2]
				.getMethodName();
		TestContent tc = getTextFileContent("javacc/tests/refs/" + methodName);
		parser = new EG1(stringToInputStream(tc.input));

		System.out.print(blanks.substring(methodName.length()) + methodName
				+ " :\t");
		String s = parser.start().trim();
		System.out.println(String.format("\n%s\n--\n%s\n**\n%s\n\n", tc.input, s, tc.output));
		assertEquals(tc.output, s);
	}

	private static void printUtf(String s) throws IOException {
		out.write(s + "\n");
		out.flush();
	}

	private static InputStream stringToInputStream(String s) {
		return new ByteArrayInputStream(s.getBytes());
	}

	private static TestContent getTextFileContent(String filename)
			throws IOException {
		final String nl = System.getProperty("line.separator");
		
		List<String> lines = Files.readAllLines(FileSystems.getDefault()
				.getPath(filename), StandardCharsets.UTF_8);

		StringBuilder input = new StringBuilder();
		StringBuilder output = new StringBuilder();

		boolean isInput = true;

		for (String line : lines) {
			if (line.equals("# OUT"))
				isInput = false;
			if (line.startsWith("#"))
				continue;
			
			StringBuilder buffer = isInput ? input : output;
			buffer.append(line + nl);
		}
		
		input.append(nl);

		return new TestContent(input.toString(), output.toString().trim());
	}
}
