/**
 * 
 */
package org.eclipse.xtext.bpel.wsdl.tests;

import java.util.List;

import org.eclipse.bpel.model.Variable;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xtext.bpel.tests.XtextBpelAbstractTests;
import org.eclipse.xtext.bpel.util.BpelModelUtil;
import org.eclipse.xtext.bpel.util.Wsdl2EcoreUtil;
import org.eclipse.xtext.bpel.util.Xsd2EcoreUtil;

/**
 * @author bettini
 * 
 */
public class Wsdl2EcoreUtilTest extends XtextBpelAbstractTests {

	public void testVariableWithMessageType() {
		Message messageType = getInputVariableMessageType();
		EClassifier messageEClassifier = messageType.eClass();
		System.out.println(messageEClassifier);
		Part part = (Part) messageType.getEParts().get(0);
		XSDElementDeclaration elementDeclaration = part.getElementDeclaration();
		EClassifier eClassifier = Xsd2EcoreUtil
				.getEClassifierFromXSDElementDeclaration(elementDeclaration);
		System.out.println(eClassifier);
		List<? extends ENamedElement> elements = BpelModelUtil
				.getEClassifierElements(eClassifier);
		System.out.println(elements);
	}

	public void testEClassifierFromMessage() {
		Message messageType = getInputVariableMessageType();
		EClass messageEClass = Wsdl2EcoreUtil
				.getEClassifierFromWSDLMessage(messageType);

		assertTrue(messageEClass != null);
		assertEquals(messageType.getQName().getLocalPart(),
				messageEClass.getName());

		List<EStructuralFeature> features = messageEClass
				.getEAllStructuralFeatures();
		assertEquals(2, features.size());
		
		EStructuralFeature feature = features.get(0);
		assertStructuralFeature(feature, "payload", "CopyProcessRequestType");
		feature = features.get(1);
		assertStructuralFeature(feature, "name", "String");
	}

	protected Message getInputVariableMessageType() {
		Variable var = loadInputVariableWithMessageType();
		Message messageType = var.getMessageType();
		return messageType;
	}
}
