/**
 * 
 */
package org.eclipse.xtext.bpel.util;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

/**
 * @author bettini
 * 
 */
public class ProjectUtils {

	public static IEditorPart getActiveEditorPart() {
		IEditorPart editorPart = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		return editorPart;
	}

	public static IResource getActiveEditorResource(IEditorPart editor) {
		IEditorInput input = editor.getEditorInput();
		if (!(input instanceof FileEditorInput))
			return null;
		return ((FileEditorInput) input).getFile();
	}

	public static IProject getActiveProject() {
		return getActiveEditorResource(getActiveEditorPart()).getProject();
	}

	public static IWorkspaceRoot getWorkspaceRoot() {
		return ResourcesPlugin.getWorkspace().getRoot();
	}

	public static File createTemporaryFileInCurrentProject(String extension,
			String contents) throws IOException {
		IWorkspaceRoot root = getWorkspaceRoot();
		String rootPath = root.getLocation().toOSString();
		File tempResourceFile = FileUtils.createTempFile(rootPath
				+ getActiveProject().getFullPath().toOSString() + "/",
				extension);
		FileUtils.writeStringIntoFile(tempResourceFile, contents);
		return tempResourceFile;
	}

	public static IFile getIFileFromFile(File file) {
		return getWorkspaceRoot().getFileForLocation(
				Path.fromOSString(file.getAbsolutePath()));
	}
}
