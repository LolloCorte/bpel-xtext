/**
 * 
 */
package org.eclipse.xtext.bpel.tests;

import java.io.File;
import java.util.Iterator;

import org.eclipse.bpel.fnmeta.ClasspathFunctionRegistryLoader;
import org.eclipse.bpel.fnmeta.FunctionLibrary;
import org.eclipse.bpel.fnmeta.model.util.FMResourceFactoryImpl;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.adapters.AdapterRegistry;
import org.eclipse.bpel.model.adapters.BasicBPELAdapterFactory;
import org.eclipse.bpel.model.resource.BPELResourceFactoryImpl;
import org.eclipse.bpel.model.resource.BPELResourceSetImpl;
import org.eclipse.bpel.validator.BPELReader;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;

import org.eclipse.wst.wsdl.WSDLElement;
import org.eclipse.wst.wsdl.internal.util.WSDLResourceFactoryImpl;
import org.eclipse.xsd.util.XSDResourceFactoryImpl;
import org.w3c.dom.Element;

/**
 * @see org.eclipse.bpel.validator.BPELReader
 * @see org.eclipse.bpel.validator.Main
 * 
 * @author bettini
 * 
 */
public class BpelLoader {

	public Process loadBpel(String filePath) {
		// Create the Quasi-Eclipse environment ...
		AdapterRegistry.INSTANCE.registerAdapterFactory(BPELPackage.eINSTANCE,
				BasicBPELAdapterFactory.INSTANCE);

		// Create a resource set.
		ResourceSet fResourceSet = new BPELResourceSetImpl();

		// Register the resource factories for .bpel, .wsdl, and .xsd resources.
		// - bpel reads BPEL resources (our model)

		fResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put("bpel", new BPELResourceFactoryImpl());

		fResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put("wsdl", new WSDLResourceFactoryImpl());
		// - wsdl reads WSDL resources (from wst project)

		// WSDL also needs to know about the extensions to WSDl that we provide,
		// namely
		// partner links, variable properties, etc.
		// We need to register them someplace here ...

		fResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put("xsd", new XSDResourceFactoryImpl());
		// - xsd reads WSDL resources (from wst project)

		fResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put("fnmeta", new FMResourceFactoryImpl());

		// The function library is late bound. When eclipse is running, this
		// information
		// is registered via the plugin's descriptor. On its own, we have to do
		// it another way.

		FunctionLibrary.INSTANCE
				.registerLoader(new ClasspathFunctionRegistryLoader(
						fResourceSet));

		String absolutePath = new File(filePath).getAbsolutePath();
		File file = new File(absolutePath);

		//
		// Step 1. Read the BPEL process using the Model API.
		BPELReader reader = new BPELReader();

		reader.read(file, fResourceSet);
		Process process = reader.getProcess();

		linkModels(process);

		return process;
	}

	void linkModels(EObject process) {

		//
		// Each extensible element points to the DOM element that
		// comprises it. This is done in the BPEL reader as well as
		// the WSDL readers. Here we add a pointer to the
		// emf objects from the DOM objects.

		Iterator<?> emfIterator = process.eAllContents();
		while (emfIterator.hasNext()) {
			Object obj = emfIterator.next();
			// This is because only ExtensibleElement has a reference to
			// a DOM element.
			if (obj instanceof WSDLElement) {
				WSDLElement wsdle = (WSDLElement) obj;
				Element el = wsdle.getElement();
				if (el != null) {
					el.setUserData("emf.model", obj, null); //$NON-NLS-1$
				}
			}
		}
	}
}
