package rcptutorial.part;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import rcptutorial.entity.Employee;
import rcptutorial.util.Utils;

@Creatable
public class CreatePart {

	static Text id;
	static Text employeeName;
	static Text employeeEmail;
	static DateTime dateOfBirth;
	static DateTime joinedDate;

	@Inject private EPartService service;
	
	@Inject
	IEventBroker broker;

	public CreatePart() {
		System.out.println("Create Part");
	}

	@PostConstruct
	public void createComposite(Composite parent) {
		System.out.println("@PostConstruct");
		
		
		Employee employeeUpdate = getEmployeeUpdate();
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		parent.setLayout(gridLayout);

		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);

		new Label(parent, SWT.NULL).setText("Name:");
		employeeName = new Text(parent, SWT.SINGLE | SWT.BORDER);
		gridData.horizontalSpan = 3;
		employeeName.setLayoutData(gridData);
		
		new Label(parent, SWT.NULL).setText("Email:");
		employeeEmail = new Text(parent, SWT.SINGLE | SWT.BORDER);
		gridData.horizontalSpan = 3;
		employeeEmail.setLayoutData(gridData);

		new Label(parent, SWT.NULL).setText("Date of birth:");
		dateOfBirth = new DateTime(parent, SWT.SINGLE | SWT.BORDER);
		gridData.horizontalSpan = 3;
		dateOfBirth.setLayoutData(gridData);

		new Label(parent, SWT.NULL).setText("Joined date:");
		joinedDate = new DateTime(parent, SWT.SINGLE | SWT.BORDER);
		gridData.horizontalSpan = 3;
		joinedDate.setLayoutData(gridData);
		
		if (employeeUpdate != null) {
			employeeName.setText(employeeUpdate.getName());
			employeeEmail.setText(employeeUpdate.getEmail());
			
			Calendar birthDateCal = Calendar.getInstance();
			birthDateCal.setTimeInMillis(employeeUpdate.getDateOfBirth().getTime());
			
			dateOfBirth.setDate(
					birthDateCal.get(Calendar.YEAR), 
					birthDateCal.get(Calendar.MONTH), 
					birthDateCal.get(Calendar.DAY_OF_MONTH));
			
			Calendar joinedDateCal = Calendar.getInstance();
			joinedDateCal.setTimeInMillis(employeeUpdate.getJoinedDate().getTime());
			
			joinedDate.setDate(
					joinedDateCal.get(Calendar.YEAR), 
					joinedDateCal.get(Calendar.MONTH), 
					joinedDateCal.get(Calendar.DAY_OF_MONTH));
			
		}

		Button enter = new Button(parent, SWT.PUSH);
		enter.setText("Save");
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
		gridData.horizontalSpan = 3;
		enter.setLayoutData(gridData);
		enter.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent event) {
				String checkDataMsg = checkDataMsg();
				if (employeeUpdate == null) {
					if (Utils.isNullOrEmpty(checkDataMsg)) {
						broker.send("base_topic/data_event_save", new Employee(Utils.generateId(), employeeName.getText(),
								employeeEmail.getText(), getTimeStamp(dateOfBirth), getTimeStamp(joinedDate)));
						enter.setEnabled(false);
					} else {
						MessageDialog.openInformation(parent.getShell(), "Info", checkDataMsg);
					}
				} else {
					if (Utils.isNullOrEmpty(checkDataMsg)) {
						broker.send("base_topic/data_event_update", 
								new Employee(
										employeeUpdate.getId(), 
										employeeName.getText(),
								employeeEmail.getText(), 
								getTimeStamp(dateOfBirth), 
								getTimeStamp(joinedDate)));
						enter.setEnabled(false);

					} else {
						MessageDialog.openInformation(parent.getShell(), "Info", checkDataMsg);
					}
				}
			}
		});
		

		parent.pack();
	}

	@PreDestroy
	public void closeWindows() {
		System.out.println("Close CreatePart Windows");
	}

	private String checkDataMsg() {
		String infoMsg = "";
		String employeeNameStr = employeeName.getText();
		String emailStr = employeeEmail.getText();

		if (!Utils.patternMatches(employeeNameStr, Utils.regexName)) {
			infoMsg = "Name is invalid";
		}

		if (!Utils.patternMatches(emailStr, Utils.regexEmail)) {
			infoMsg = "Email is invalid";
		}
		return infoMsg;
	}

	private Timestamp getTimeStamp(DateTime dtDate) {
		LocalDateTime dateTime = LocalDateTime.of(dtDate.getYear(), dtDate.getMonth() + 1, dtDate.getDay(),
				dtDate.getHours(), dtDate.getMinutes(), dtDate.getSeconds());
		return Timestamp.valueOf(dateTime);
	}
	
	private Employee getEmployeeUpdate() {
		Employee employeeUpdated = null;
		MPart part = service.findPart("rcptutorial.part.0");
		if (part != null) {
			if (part.getTransientData().get("employee") != null) {
				employeeUpdated = (Employee) part.getTransientData().get("employee");
			}
			
		}
		
		return employeeUpdated;
	}

}
