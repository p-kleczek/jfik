package tests;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import main.EG1;
import main.ParseException;

public class Converter {

	private final static String nl = System.getProperty("line.separator");
	private static OutputStreamWriter outStream;
	private static Writer out;
	private static File f = new File("output.txt");
	private static FileOutputStream fos = null;

	static {
		try {
			fos = new FileOutputStream(f);
			outStream = new OutputStreamWriter(fos, "UTF8");
//			outStream = new OutputStreamWriter(System.out, "UTF8");
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		}

		out = outStream;
	}

	private static EG1 parser;

	public static void main(String[] args) throws ParseException, IOException {
		assert (args.length > 0);
		
		StringBuilder input = new StringBuilder();
		for (String arg : args) {
			input.append(arg);
			input.append(" ");
		}
		input.delete(input.length() - 1, input.length());
		input.append(System.getProperty("line.separator"));

		parser = new EG1(stringToInputStream(input.toString()));

		String s = parser.start().trim();
		printUtf(s);
		
		fos.close();
		
		System.out.println("### OUT:\n" + s);
	}

	private static void printUtf(String s) throws IOException {
		out.write(s + "\n");
		out.flush();
	}

	private static InputStream stringToInputStream(String s) {
		String extendedInput = s + nl;
		return new ByteArrayInputStream(extendedInput.getBytes());
	}
}
