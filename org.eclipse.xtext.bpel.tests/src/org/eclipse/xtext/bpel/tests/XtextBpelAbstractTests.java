/**
 * 
 */
package org.eclipse.xtext.bpel.tests;

import java.util.List;

import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Variable;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.bpel.util.BpelModelUtil;

import junit.framework.TestCase;

/**
 * @author bettini
 * 
 */
public class XtextBpelAbstractTests extends TestCase {

	public BpelLoader bpelLoader;

	protected void setUp() throws Exception {
		super.setUp();
		bpelLoader = new BpelLoader();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	protected void assertStructuralFeature(ENamedElement namedElement,
			String expectedElementName, String expectedElementType) {
		assertEquals(expectedElementName, namedElement.getName());
		EStructuralFeature structuralFeature = (EStructuralFeature) namedElement;
		EClassifier elementType = structuralFeature.getEType();
		assertEquals(expectedElementType, elementType.getName());
	}

	protected List<Variable> loadTestProcessVariables() {
		String path = "./bpel/CopyProcess.bpel";
		Process process = bpelLoader.loadBpel(path);
		List<Variable> variables = BpelModelUtil
				.collectVisibleVariables(process);
		return variables;
	}

	protected Variable loadInputVariableWithMessageType() {
		List<Variable> variables = loadTestProcessVariables();
		Variable var = BpelModelUtil.getVariableByName(variables, "input");
		assertTrue(var != null);
		return var;
	}

}
