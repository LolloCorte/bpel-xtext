/**
 * 
 */
package org.eclipse.xtext.bpel.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDTypeDefinition;
import org.eclipse.xtext.bpel.BpelInformationResource;
import org.eclipse.xtext.resource.XtextResource;

/**
 * @author bettini
 * 
 */
public class BpelModelUtil {

	public static List<Variable> collectVisibleVariables(EObject eObject) {
		Variable[] visibleVariables = BPELUtil.getVisibleVariables(eObject);
		return Arrays.asList(visibleVariables);
	}

	public static List<? extends ENamedElement> getVariableElements(
			Variable variable) {
		EClassifier eClassifier = getVariableEClassifier(variable);
		return getEClassifierElements(eClassifier);
	}

	public static List<? extends ENamedElement> getEClassifierElements(
			EClassifier eClassifier) {
		if (eClassifier != null) {
			if (eClassifier instanceof EClass) {
				return ((EClass) eClassifier).getEAllStructuralFeatures();
			}
		}
		return new LinkedList<ENamedElement>();
	}

	public static EClassifier getBpelVariableEClassifier(EObject model,
			String varName) {
		Variable variable = getVariableByName(model, varName);
		if (variable == null)
			return null;
		return getVariableEClassifier(variable);
	}

	public static EClassifier getVariableEClassifier(Variable variable) {
		XSDTypeDefinition type = variable.getType();
		if (type != null)
			return Xsd2EcoreUtil.getEClassifierFromXSDTypeDefinition(type);

		XSDElementDeclaration xsdElement = variable.getXSDElement();
		if (xsdElement != null)
			return Xsd2EcoreUtil
					.getEClassifierFromXSDElementDeclaration(xsdElement);

		Message messageType = variable.getMessageType();
		if (messageType != null)
			return Wsdl2EcoreUtil.getEClassifierFromWSDLMessage(messageType);

		return null;
	}

	public static List<Variable> getVariablesFromResource(EObject model) {
		return getVariablesFromResource(model.eResource().getResourceSet());
	}

	public static List<Variable> getVariablesFromResource(
			ResourceSet resourceSet) {
		BpelInformationResource bpelInformationResource = getBpelInformationResource(resourceSet);

		if (bpelInformationResource != null)
			return bpelInformationResource.getVariables();

		return Collections.emptyList();
	}

	public static Object getBpelInformationResource(EObject object) {
		return getBpelInformationResource(object.eResource().getResourceSet());
	}

	public static BpelInformationResource getBpelInformationResource(
			ResourceSet resourceSet) {
		EList<Resource> contents = resourceSet.getResources();

		for (Resource resource : contents) {
			if (resource instanceof BpelInformationResource) {
				BpelInformationResource bpelInformationResource = (BpelInformationResource) resource;
				return bpelInformationResource;
			}
		}

		return null;
	}

	public static void addBpelVariablesToXtextResource(XtextResource resource,
			EObject modelObject) {
		List<Variable> collectVariables = BpelModelUtil
				.collectVisibleVariables(modelObject);

		ResourceSet resourceSet = resource.getResourceSet();
		BpelInformationResource bpelInformationResource = getBpelInformationResource(resourceSet);
		if (bpelInformationResource == null) {
			bpelInformationResource = new BpelInformationResource();
			resourceSet.getResources().add(bpelInformationResource);
		}

		bpelInformationResource.replaceBpelVariables(collectVariables);
	}

	public static void addBpelVariablesToXtextResource(EObject xtextObject,
			EObject modelObject) {
		addBpelVariablesToXtextResource(
				(XtextResource) xtextObject.eResource(), modelObject);
	}

	public static Variable getVariableByName(EObject model, String varName) {
		List<Variable> variables = BpelModelUtil
				.getVariablesFromResource(model);
		return getVariableByName(variables, varName);
	}

	public static Variable getVariableByName(List<Variable> variables,
			String varName) {
		for (Variable variable : variables) {
			if (variable.getName().equals(varName))
				return variable;
		}
		return null;
	}

	public static boolean hasBpelVariables(EObject object) {
		return getBpelInformationResource(object) != null;
	}

}
