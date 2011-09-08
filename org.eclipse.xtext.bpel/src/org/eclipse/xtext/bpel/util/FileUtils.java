package org.eclipse.xtext.bpel.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

	private static int index = 0;

	public static String getTempFileName() {
		return ".temp" + (++index);
	}

	public static String getTempFileName(String extension) {
		return getTempFileName() + "." + extension;
	}

	public static File createTempFile(String extension) throws IOException {
		return createTempFile("", extension);
	}

	public static File createTempFile(String tempPath, String extension) throws IOException {
		File file = new File(tempPath + getTempFileName(extension));
		file.delete();
		file.createNewFile();
		file.deleteOnExit();
		return file;
	}

	public static void writeStringIntoFile(File file, String contents)
			throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
		out.write(contents);
		out.flush();
	}

}
