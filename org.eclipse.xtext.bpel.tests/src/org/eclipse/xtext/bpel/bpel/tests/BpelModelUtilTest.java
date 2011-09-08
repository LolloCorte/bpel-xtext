/**
 * 
 */
package org.eclipse.xtext.bpel.bpel.tests;

import java.util.List;

import org.eclipse.bpel.model.Variable;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.bpel.tests.XtextBpelAbstractTests;
import org.eclipse.xtext.bpel.util.BpelModelUtil;

/**
 * @author bettini
 * 
 */
public class BpelModelUtilTest extends XtextBpelAbstractTests {

	public void testBpelVariables() {
		List<Variable> variables = loadTestProcessVariables();
		assertEquals(6, variables.size());
	}

	public void testBooleanVariable() {
		Variable variable = loadTestProcessVariables().get(0);
		assertEquals("VariableBoolean", variable.getName());
		EClassifier eClassifier = BpelModelUtil
				.getVariableEClassifier(variable);
		assertEquals("Boolean", eClassifier.getName());
	}

	public void testCounterVariable() {
		Variable variable = loadTestProcessVariables().get(2);
		assertEquals("counter", variable.getName());
		EClassifier eClassifier = BpelModelUtil
				.getVariableEClassifier(variable);
		assertEquals("Long", eClassifier.getName());
	}

	public void testCopyProcessVariable() {
		Variable variable = loadTestProcessVariables().get(1);
		assertEquals("VariableCopyProcessRequest", variable.getName());
		EClassifier eClassifier = BpelModelUtil
				.getVariableEClassifier(variable);
		assertEquals("CopyProcessRequestType", eClassifier.getName());
		List<? extends ENamedElement> elements = BpelModelUtil
				.getVariableElements(variable);
		assertEquals(1, elements.size());
		ENamedElement namedElement = elements.get(0);

		assertStructuralFeature(namedElement, "input", "String");
	}

	public void testVariableWithMessageType() {
		Variable variable = loadInputVariableWithMessageType();
		EClassifier eClassifier = BpelModelUtil
				.getVariableEClassifier(variable);
		assertTrue(eClassifier != null);
		assertEquals("CopyProcessRequestMessage", eClassifier.getName());
		List<? extends ENamedElement> elements = BpelModelUtil
				.getVariableElements(variable);
		assertEquals(2, elements.size());

		ENamedElement feature = elements.get(0);
		assertStructuralFeature(feature, "payload", "CopyProcessRequestType");
		List<? extends ENamedElement> payloadElements = BpelModelUtil
				.getEClassifierElements(((EStructuralFeature) feature)
						.getEType());
		assertEquals(1, payloadElements.size());
		assertStructuralFeature(payloadElements.get(0), "input", "String");

		feature = elements.get(1);
		assertStructuralFeature(feature, "name", "String");
	}

	public void testVariableByName() {
		List<Variable> variables = loadTestProcessVariables();
		assertTrue(BpelModelUtil.getVariableByName(variables, "foo") == null);
		Variable var = BpelModelUtil.getVariableByName(variables,
				"VariableBoolean");
		assertEquals(variables.get(0), var);
	}

}
