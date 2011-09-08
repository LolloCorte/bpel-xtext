/**
 * 
 */
package org.eclipse.xtext.bpel.util;

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDTypeDefinition;

/**
 * For getting EClassifier from WSDL Messages
 * 
 * @author bettini
 * 
 */
public class Wsdl2EcoreUtil {

	public static EClass getEClassifierFromWSDLMessage(Message message) {
		EClass eClass = createEClass(message.getQName().getLocalPart());

		@SuppressWarnings("unchecked")
		List<Part> parts = message.getEParts();

		for (Part part : parts) {
			String name = part.getName();

			XSDElementDeclaration elementDeclaration = part
					.getElementDeclaration();
			XSDTypeDefinition xsdTypeDefinition = part.getTypeDefinition();

			EClassifier elementClassifier = null;
			if (elementDeclaration != null) {
				elementClassifier = Xsd2EcoreUtil
						.getEClassifierFromXSDElementDeclaration(elementDeclaration);
			} else if (xsdTypeDefinition != null) {
				elementClassifier = Xsd2EcoreUtil
						.getEClassifierFromXSDTypeDefinition(xsdTypeDefinition);
			}

			if (elementClassifier != null) {
				createAndAddAttribute(eClass, elementClassifier, name);
			}
		}

		return eClass;
	}

	protected static EClass createEClass(String messageName) {
		EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		eClass.setName(messageName);
		return eClass;
	}

	protected static void createAndAddAttribute(EClass eClass,
			EClassifier attributeType, String attributeName) {
		EAttribute attribute = EcoreFactory.eINSTANCE.createEAttribute();
		attribute.setName(attributeName);
		attribute.setEType(attributeType);
		eClass.getEStructuralFeatures().add(attribute);
	}

}
