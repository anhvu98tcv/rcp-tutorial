package rcptutorial.handler;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.swt.widgets.Shell;

import rcptutorial.EmployeeCreateWindowContainer;

public class CreateHandler {
	
	@Inject
	EmployeeCreateWindowContainer container;

	@Execute
	public void execute(IWorkbench workbench, Shell shell) {
		container.openWindow();
	}

}
