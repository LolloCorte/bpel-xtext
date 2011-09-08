/**
 * 
 */
package org.eclipse.xtext.bpel.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.bpel.model.proxy.XSDTypeDefinitionProxy;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.xml.namespace.XMLNamespacePackage;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDTypeDefinition;
import org.eclipse.xsd.ecore.XSDEcoreBuilder;
import org.eclipse.xsd.impl.XSDSchemaImpl;
import org.eclipse.xsd.util.XSDConstants;
import org.eclipse.xtext.EcoreUtil2;

/**
 * @author bettini
 * 
 */
public class Xsd2EcoreUtil {

	XSDEcoreBuilder xsdEcoreBuilder = new XSDEcoreBuilder();

	public Xsd2EcoreUtil(XSDSchema xsdSchema) {
		if (xsdSchema != null)
			xsdEcoreBuilder.generate(xsdSchema);
	}

	public List<EObject> getEObjects() {
		List<EObject> result = new ArrayList<EObject>(xsdEcoreBuilder
				.getTargetNamespaceToEPackageMap().values());
		result.remove(XMLNamespacePackage.eINSTANCE);
		return result;
	}

	public List<EPackage> getEPackages() {
		return EcoreUtil2.typeSelect(getEObjects(), EPackage.class);
	}

	public List<EClass> getEClasses() {
		List<EClass> eClasses = new LinkedList<EClass>();
		List<EPackage> ePackages = getEPackages();
		for (EPackage ePackage : ePackages) {
			eClasses.addAll(EcoreUtil2.getAllContentsOfType(ePackage,
					EClass.class));
		}

		// the first element seems to be DocumentRoot
		// and we don't need it
		if (eClasses.size() > 0) {
			if (eClasses.get(0).getName().equals("DocumentRoot")) {
				eClasses.remove(0);
			}
		}

		return eClasses;
	}

	public EClassifier getEClassifier(
			XSDElementDeclaration xsdElementDeclaration) {
		XSDTypeDefinition typeDefinition = xsdElementDeclaration
				.getTypeDefinition();
		return getEClassifier(typeDefinition);
	}

	protected EClassifier getEClassifier(XSDTypeDefinition typeDefinition) {
		return xsdEcoreBuilder.getEClassifier(typeDefinition);
	}
	
	public static EClassifier getEClassifierFromXSDTypeDefinition(
			XSDTypeDefinition type) {
		Xsd2EcoreUtil xsd2EcoreUtil = new Xsd2EcoreUtil(null);
		if (type.eIsProxy() && type instanceof XSDTypeDefinitionProxy) {
			type = resolveXSDTypeDefinitionProxy((XSDTypeDefinitionProxy) type);
		}
		return xsd2EcoreUtil.getEClassifier(type);
	}
	
	public static EClassifier getEClassifierFromXSDElementDeclaration(
			XSDElementDeclaration xsdElement) {
		return getEClassifierFromXSDTypeDefinition(xsdElement.getType());
	}

	/**
	 * Bpel does not seem to automatically resolve proxies for XML schema, such
	 * as (eProxyURI:
	 * #XSDTypeDefinition{http://www.w3.org/2001/XMLSchema}boolean) thus we do
	 * this manually.
	 * 
	 * @param proxy
	 * @return
	 */
	public static XSDTypeDefinition resolveXSDTypeDefinitionProxy(
			XSDTypeDefinitionProxy proxy) {
		String typename = proxy.getName();
		String namespace = proxy.getTargetNamespace();

		XSDSchema schema = null;

		if (XSDConstants.isSchemaForSchemaNamespace(namespace)) {
			schema = XSDSchemaImpl.getSchemaForSchema(namespace);
		} else if (XSDConstants.isSchemaInstanceNamespace(namespace)) {
			schema = XSDSchemaImpl.getSchemaInstance(namespace);
		}

		if (schema == null)
			return null;

		XSDTypeDefinition typeDef = schema.resolveTypeDefinition(namespace,
				typename);
		if (typeDef.getContainer() == null) {
			return null;
		}
		return typeDef;
	}

}
