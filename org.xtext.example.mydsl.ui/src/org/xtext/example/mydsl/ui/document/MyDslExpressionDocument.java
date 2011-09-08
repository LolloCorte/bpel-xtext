package org.xtext.example.mydsl.ui.document;

import org.eclipse.xtext.ui.editor.model.XtextDocument;

import com.google.inject.Provider;

public class MyDslExpressionDocument extends XtextDocument {
	
	private static Provider<XtextDocument>  provider=new Provider<XtextDocument>() {
		
		@Override
		public MyDslExpressionDocument get() {
			return new MyDslExpressionDocument();
		}
	};
	
	public static Provider<XtextDocument> getProvider(){
		return provider;
	}
	
//	@Override
//	protected IDocument createDocument(Object element) throws CoreException {
//		if (element instanceof TextEditorInput) {
//			
//			TextEditorInput input = (TextEditorInput) element;
//			Document doc = new MyDocument( input );
//			input.setDocument ( doc );
//			
//			return doc;
//		}		
//		return null;
//	
//	}
//	
//	class MyDocument extends XtextDocument{
//		
////		private Document document;
//		
//		public MyDocument(TextEditorInput input) {
//			System.out.println(input);
////			setInput(input.getResource());
////			document=new Document(input);
//		}
//	}
}
