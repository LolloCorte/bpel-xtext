/**
 * 
 */
package org.eclipse.xtext.bpel.util.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import junit.framework.TestCase;

import org.eclipse.xtext.bpel.util.FileUtils;
import org.junit.After;
import org.junit.Before;

/**
 * @author bettini
 * 
 */
public class FileUtilsTest extends TestCase {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	public void testTempFileName() {
		String file1 = FileUtils.getTempFileName();
		String file2 = FileUtils.getTempFileName();
		assertFalse(file1.equals(file2));
	}

	public void testCreateTempFile() throws IOException {
		File tempFile = FileUtils.createTempFile("foo");
		assertTrue(tempFile.exists());
		assertTrue(tempFile.getName().endsWith(".foo"));
		tempFile.delete();
	}

	public void testTempFileNameWithExtension() {
		String file1 = FileUtils.getTempFileName("foo");
		String file2 = FileUtils.getTempFileName("foo");
		assertFalse(file1.equals(file2));
		assertTrue(file1.endsWith(".foo"));
	}

	public void testWriteIntoFile() throws IOException {
		File tempFile = FileUtils.createTempFile("foo");
		String contents = "these\nare\ncontents\n";
		FileUtils.writeStringIntoFile(tempFile, contents);
		FileInputStream fis = new FileInputStream(tempFile);
		byte[] b = new byte[(int) tempFile.length()];
		fis.read(b);
		assertEquals(contents, new String(b));
		tempFile.delete();
	}
}
