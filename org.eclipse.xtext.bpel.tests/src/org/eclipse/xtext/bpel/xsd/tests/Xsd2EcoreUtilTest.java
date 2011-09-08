package org.eclipse.xtext.bpel.xsd.tests;

import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xtext.bpel.util.Xsd2EcoreUtil;

public class Xsd2EcoreUtilTest extends TestCase {

	Xsd2EcoreUtil xsd2EcoreUtil;

	@Override
	protected void setUp() throws Exception {
		xsd2EcoreUtil = new Xsd2EcoreUtil(loadShiporderSchema());
	}

	@Override
	protected void tearDown() throws Exception {
	}

	protected XSDSchema loadShiporderSchema() {
		String path = "./xsd/shiporder.xsd";
		XsdLoader xsdLoader = new XsdLoader();
		XSDSchema xsdObject = xsdLoader.loadXsd(path);
		return xsdObject;
	}

	public void testCorrectConstruction() {
		Collection<EObject> eObjects = xsd2EcoreUtil.getEObjects();
		assertEquals(1, eObjects.size());
	}

	public void testCollectEPackages() {
		List<EPackage> ePackages = xsd2EcoreUtil.getEPackages();
		assertEquals(1, ePackages.size());
	}

	public void testCollectEClasses() {
		List<EClass> eClasses = xsd2EcoreUtil.getEClasses();
		System.out.println(eClasses);
		assertEquals(3, eClasses.size());
	}

	public void testGetEClassifier() {
		XSDSchema xsdSchema = loadShiporderSchema();
		EClassifier eClassifier = xsd2EcoreUtil.getEClassifier(xsdSchema
				.getElementDeclarations().get(0));
		assertEquals("ShiporderType", eClassifier.getName());
	}
}
