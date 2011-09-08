/**
 * 
 */
package org.eclipse.xtext.bpel.bpel.tests;

import org.eclipse.bpel.model.Process;
import org.eclipse.xtext.bpel.tests.BpelLoader;

import junit.framework.TestCase;

/**
 * @author bettini
 *
 */
public class BpelLoaderTest extends TestCase {

	BpelLoader bpelLoader;
	
	protected void setUp() throws Exception {
		super.setUp();
		bpelLoader = new BpelLoader();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testBpelLoader() {
		String path = "./bpel/CopyProcess.bpel";
		Process process = bpelLoader.loadBpel(path);
		assertTrue(process != null);
	}

}
