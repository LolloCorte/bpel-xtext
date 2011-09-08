package org.eclipse.xtext.bpel.tests;

import org.eclipse.xtext.bpel.bpel.tests.BpelLoaderTest;
import org.eclipse.xtext.bpel.bpel.tests.BpelModelUtilTest;
import org.eclipse.xtext.bpel.util.tests.FileUtilsTest;
import org.eclipse.xtext.bpel.wsdl.tests.Wsdl2EcoreUtilTest;
import org.eclipse.xtext.bpel.xsd.tests.Xsd2EcoreUtilTest;
import org.eclipse.xtext.bpel.xsd.tests.XsdUtilTest;


import junit.framework.Test;
import junit.framework.TestSuite;

public class XtextBpelAllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(XtextBpelAllTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(FileUtilsTest.class);
		suite.addTestSuite(Xsd2EcoreUtilTest.class);
		suite.addTestSuite(XsdUtilTest.class);
		suite.addTestSuite(BpelLoaderTest.class);
		suite.addTestSuite(Wsdl2EcoreUtilTest.class);
		suite.addTestSuite(BpelModelUtilTest.class);
		//$JUnit-END$
		return suite;
	}

}
