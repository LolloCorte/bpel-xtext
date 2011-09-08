package org.eclipse.xtext.bpel.xsd.tests;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDSchema;

import junit.framework.TestCase;


public class XsdUtilTest extends TestCase {

	XsdLoader xsdLoader;
	
	@Override
	protected void setUp() throws Exception {
		xsdLoader = new XsdLoader();
	}

	@Override
	protected void tearDown() throws Exception {
	}

	public void testLoadXsd() {
		XSDSchema xsdObject = loadShiporderSchema();
		System.out.println(xsdObject);
		assertTrue(xsdObject != null);
	}

	protected XSDSchema loadShiporderSchema() {
		String path = "./xsd/shiporder.xsd";
		XSDSchema xsdObject = xsdLoader.loadXsd(path);
		return xsdObject;
	}
	
	protected XSDSchema loadAnagraficaSchema() {
		String path = "./xsd/Anagrafica.xsd";
		XSDSchema xsdObject = xsdLoader.loadXsd(path);
		return xsdObject;
	}
	
	public void testShiporderDefinedTypes() {
		XSDSchema xsdSchema = loadShiporderSchema();
		// shiporder
		EList<XSDElementDeclaration> elementDeclarations = xsdSchema.getElementDeclarations();
		assertEquals(1, elementDeclarations.size());
		
		XSDElementDeclaration elementDeclaration = elementDeclarations.get(0);
		assertTrue(elementDeclaration != null);
	}
	
	public void testAnagraficaDefinedTypes() {
		XSDSchema xsdSchema = loadAnagraficaSchema();
		// shiporder
		EList<XSDElementDeclaration> elementDeclarations = xsdSchema.getElementDeclarations();
		assertEquals(1, elementDeclarations.size());
		
		XSDElementDeclaration elementDeclaration = elementDeclarations.get(0);
		assertTrue(elementDeclaration != null);
	}
}
