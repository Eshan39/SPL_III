package javatcm.handlers;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.part.FileEditorInput;

import project.Main;
import project.MethodParser;

import org.eclipse.jface.dialogs.MessageDialog;

public class SampleHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		IWorkbenchPage iPage = window.getActivePage();
		IEditorPart iPart = iPage.getActiveEditor();
		IEditorInput input = iPart.getEditorInput();
		
		String pathname = null;

		if (input instanceof FileStoreEditorInput) {
			FileStoreEditorInput fStoreEditorInput = input.getAdapter(FileStoreEditorInput.class);
			pathname = fStoreEditorInput.getURI().getPath();
		} else if (input instanceof FileEditorInput) {
			FileEditorInput fEditorInput = input.getAdapter(FileEditorInput.class);
			pathname = fEditorInput.getURI().getPath();
		}
		
		File projectDir = new File("D:\\BSSE 8th Semester\\BSSE_8th_Semester\\SPL_3\\TestFolder");
		
		MethodParser mParser = new MethodParser();
		mParser.listOfMethod(projectDir);
		
		MessageDialog.openInformation(
				window.getShell(),
				"JavaTCM",
				"Hello, Eclipse world");
		return null;
	}
}
