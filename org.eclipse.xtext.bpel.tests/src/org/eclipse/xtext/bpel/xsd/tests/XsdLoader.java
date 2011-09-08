/**
 * 
 */
package org.eclipse.xtext.bpel.xsd.tests;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xsd.XSDPackage;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.util.XSDResourceFactoryImpl;

/**
 * @author bettini
 * 
 */
public class XsdLoader {

	public XSDSchema loadXsd(String filePath) {
		ResourceSet resourceSet = new ResourceSetImpl();

		// Register the appropriate resource factory to handle all file
		// extensions.
		//
		resourceSet
				.getResourceFactoryRegistry()
				.getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
						new XSDResourceFactoryImpl());

		// Register the package to ensure it is available during loading.
		//
		resourceSet.getPackageRegistry().put(XSDPackage.eNS_URI,
				XSDPackage.eINSTANCE);

		File file = new File(filePath);
		URI uri = file.isFile() ? URI.createFileURI(file.getAbsolutePath())
				: URI.createURI(filePath);
		
		Resource resource = resourceSet.getResource(uri, true);
		System.out.println("Loaded " + uri);
		
		return (XSDSchema) resource.getContents().get(0);
	}
}
