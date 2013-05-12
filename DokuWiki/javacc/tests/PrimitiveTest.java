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

// TODO: testy jako porównywanie zawartosci pliku stworzonego recznie z tym, co generuje parser (latwiejsze tworzenie testow)

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
	public void testPlainText() throws ParseException, IOException {
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
	public void testMixedMarkup() throws ParseException, IOException {
		test();
		//TODO nie przechodzi bo jedny pasujacy token to token maila
		//a "file" nie pasuje do regexa
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

	

	private static void test() throws IOException, ParseException {
		String methodName = Thread.currentThread().getStackTrace()[2]
				.getMethodName();
		TestContent tc = getTextFileContent("javacc/tests/refs/" + methodName);
		parser = new EG1(stringToInputStream(tc.input));

		System.out.print(blanks.substring(methodName.length()) + methodName
				+ " :\t");
		String s = parser.start();
		System.out.println(String.format("%s\n", s));
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

			if (isInput)
				input.append(line);
			else
				output.append(line);
		}

		return new TestContent(input.toString(), output.toString());
	}
}
