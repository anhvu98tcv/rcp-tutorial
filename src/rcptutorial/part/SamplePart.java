package rcptutorial.part;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyAccessor;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.data.ReflectiveColumnPropertyAccessor;
import org.eclipse.nebula.widgets.nattable.extension.glazedlists.GlazedListsEventLayer;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultColumnHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultCornerDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultRowHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.layer.ColumnHeaderLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.CornerLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.RowHeaderLayer;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.viewport.ViewportLayer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;
import rcptutorial.EmployeeCreateWindowContainer;
import rcptutorial.entity.Employee;
import rcptutorial.handler.UpdateHandler;
import rcptutorial.util.Utils;

public class SamplePart {

	private List<Employee> employees = new ArrayList<Employee>();
	private Employee employeeSelected = null;
	EventList<Employee> personEventList;
	String[] propertyNames = { "id", "name", "email", "dateOfBirth", "joinedDate" };
	Map<String, String> propertyToLabelMap = new HashMap<String, String>();
	
	@Inject
	private MDirtyable dirty;
	
	@Inject
	EModelService modelService;
	
	@Inject
	MApplication app;
	
	@Inject
	EmployeeCreateWindowContainer container;
	
	public SamplePart() {
		
		Employee employee1 = new Employee(Utils.generateId(), "Nam", "a@gmail.com", new Timestamp(System.currentTimeMillis()),
				new Timestamp(System.currentTimeMillis()));
		Employee employee2 = new Employee(Utils.generateId(), "Tuan", "b@gmail.com", new Timestamp(System.currentTimeMillis()),
				new Timestamp(System.currentTimeMillis()));
		employees.add(employee1);
		employees.add(employee2);
		
		personEventList = GlazedLists.eventList(employees);	 
		
		propertyToLabelMap.put("id", "Id");
		propertyToLabelMap.put("name", "Name");
		propertyToLabelMap.put("email", "Email");
		propertyToLabelMap.put("dateOfBirth", "Date of birth");
		propertyToLabelMap.put("joinedDate", "Joined date");
		

	}

	@PostConstruct
	public void createComposite(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
	
		
		Button buttonAdd = new Button(parent, SWT.NONE);
		buttonAdd.setText("Add");
		Point bSize = buttonAdd.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		buttonAdd.setSize(bSize);
		buttonAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				container.openWindow();
			}

		});
		
		Button buttonUpdate = new Button(parent, SWT.NONE);
		buttonUpdate.setEnabled(false);
		buttonUpdate.setText("Update");
		buttonUpdate.setSize(bSize);
		buttonUpdate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				container.openWindowEdit(employeeSelected);
			}

		});
		
		Button buttonDelete = new Button(parent, SWT.NONE);
		buttonDelete.setEnabled(false);
		buttonDelete.setText("Delete");
		buttonDelete.setSize(bSize);
		buttonDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				personEventList.remove(employeeSelected);
			}

		});
		
		IColumnPropertyAccessor<Employee> columnPropertyAccessor = new ReflectiveColumnPropertyAccessor<Employee>(
				propertyNames);

		IDataProvider bodyDataProvider = new ListDataProvider<Employee>(personEventList, columnPropertyAccessor);
		DataLayer bodyDataLayer = new DataLayer(bodyDataProvider);

		GlazedListsEventLayer<Employee> glazedListsEventLayer = new GlazedListsEventLayer<>(bodyDataLayer,
				personEventList);
		
//		ObservableElementList<Employee>  observableElementList = new ObservableElementList<>(personEventList,
//        GlazedLists.beanConnector(Employee.class));
//		 GlazedListsEventLayer<Employee> glazedListsEventLayer = new GlazedListsEventLayer<>(bodyDataLayer,
//	                observableElementList);
		 
		SelectionLayer selectionLayer = new SelectionLayer(glazedListsEventLayer);
		ViewportLayer viewportLayer = new ViewportLayer(selectionLayer);

		// build the column header layer stack
		IDataProvider columnHeaderDataProvider = new DefaultColumnHeaderDataProvider(propertyNames, propertyToLabelMap);
		DataLayer columnHeaderDataLayer = new DataLayer(columnHeaderDataProvider);
		ILayer columnHeaderLayer = new ColumnHeaderLayer(columnHeaderDataLayer, viewportLayer, selectionLayer);

		// build the row header layer stack
		IDataProvider rowHeaderDataProvider = new DefaultRowHeaderDataProvider(bodyDataProvider);
		DataLayer rowHeaderDataLayer = new DataLayer(rowHeaderDataProvider, 100, 30);
		ILayer rowHeaderLayer = new RowHeaderLayer(rowHeaderDataLayer, viewportLayer, selectionLayer);

		// build the corner layer stack
		ILayer cornerLayer = new CornerLayer(
				new DataLayer(new DefaultCornerDataProvider(columnHeaderDataProvider, rowHeaderDataProvider)),
				rowHeaderLayer, columnHeaderLayer);

		// create the grid layer composed with the prior created layer stacks
		GridLayer gridLayer = new GridLayer(viewportLayer, columnHeaderLayer, rowHeaderLayer, cornerLayer);
		NatTable natTable = new NatTable(parent, gridLayer);	
		natTable.addMouseListener(new MouseListener() {

			@Override
			public void mouseDown(MouseEvent e) {
				int rowPos = natTable.getRowPositionByY(e.y);
				if (rowPos >= 1) {
					employeeSelected = personEventList.get(rowPos -1);
					enableButton(buttonUpdate, buttonDelete);
				}
				
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				int rowPos = natTable.getRowPositionByY(e.y);
				if (rowPos >= 1) {
					employeeSelected = personEventList.get(rowPos -1);
					enableButton(buttonUpdate, buttonDelete);
					container.openWindowEdit(employeeSelected);
				}
			}

			
		});
		
		GridDataFactory.fillDefaults().grab(true, true).applyTo(natTable);

	}

	@Persist
	public void save() {
		dirty.setDirty(false);
	}

	@Inject
	@Optional
	void dataEventSave(@EventTopic("base_topic/data_event_save") Employee data) {
		System.out.println("data_event_save");
		personEventList.add(data);
	}
	
	@Inject
	@Optional
	void dataEventUpdate(@EventTopic("base_topic/data_event_update") Employee data) {
		System.out.println("data_event_update");
		for (int i = 0; i< personEventList.size(); i++) {
			if (personEventList.get(i).getId().equals(data.getId())) {
				personEventList.get(i).setEmail(data.getEmail());
				personEventList.get(i).setName(data.getName());
				personEventList.get(i).setJoinedDate(data.getJoinedDate());
				personEventList.get(i).setDateOfBirth(data.getDateOfBirth());
			}
		}
		personEventList.add(data);
		personEventList.remove(personEventList.size()-1);
	
	}
	
	private void enableButton(Button buttonUpdate, Button buttonDelete) {
		buttonUpdate.setEnabled(true);
		buttonDelete.setEnabled(true);
		UpdateHandler.showUpdateMenu = true;
	}
}
