package rcptutorial.part;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class UpdatePart {

	private Shell shell;

	static Text employeeName;
	static Text employeeEmail;
	static Text dateOfBirth;
	static Text joinedDate;

	// ==================== 4. Constructors ===============================

	public UpdatePart() {
		shell = new Shell(Display.getCurrent());
	}

	// ==================== 6. Action Methods =============================

	public void open() {
		
		shell = new Shell(SWT.SYSTEM_MODAL | SWT.TITLE | SWT.BORDER | SWT.CLOSE | SWT.RESIZE);
		shell.setText("UPDATE EMPLOYEE");

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;

		shell.setLayout(gridLayout);

		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);

		new Label(shell, SWT.NULL).setText("Name:");
		employeeName = new Text(shell, SWT.SINGLE | SWT.BORDER);
		gridData.horizontalSpan = 2;
		employeeName.setLayoutData(gridData);

		new Label(shell, SWT.NULL).setText("Email:");
		employeeEmail = new Text(shell, SWT.SINGLE | SWT.BORDER);
		gridData.horizontalSpan = 2;
		employeeEmail.setLayoutData(gridData);

		new Label(shell, SWT.NULL).setText("Birthdate:");
		dateOfBirth = new Text(shell, SWT.SINGLE | SWT.BORDER);
		gridData.horizontalSpan = 2;
		dateOfBirth.setLayoutData(gridData);

		new Label(shell, SWT.NULL).setText("Joined date:");
		joinedDate = new Text(shell, SWT.SINGLE | SWT.BORDER);
		gridData.horizontalSpan = 2;
		joinedDate.setLayoutData(gridData);

		Button enter = new Button(shell, SWT.PUSH);
		enter.setText("Update");
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
		gridData.horizontalSpan = 3;
		enter.setLayoutData(gridData);
		enter.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				System.out.println("\nDog Name: ");
				System.out.println("Dog Breed: ");
				System.out.println("Owner Name: ");
				System.out.println("Owner Phone: ");
				System.out.println("Categories:");
			}
		});

		shell.pack();
		shell.open();
	}

	public void close() {
		// Don't call shell.close(), because then
		// you'll have to re-create it
		shell.setVisible(false);
	}
}
