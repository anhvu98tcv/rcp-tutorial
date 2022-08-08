package rcptutorial;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

import rcptutorial.entity.Employee;

@Creatable
public class EmployeeCreateWindowContainer {

    @Inject
    private EModelService eModelService;
    

    
    @Inject
    private MApplication application;
    

    
    public void openWindowEdit(Employee employee) {
        MPart part = eModelService.createModelElement(MPart.class);
        part.setElementId("rcptutorial.part.0");
        part.setLabel("Create Label");
        part.setContributionURI("bundleclass://RCPTutorial/rcptutorial.part.CreatePart");
        part.getTransientData().put("employee", employee);
             
        MTrimmedWindow window = eModelService.createModelElement(MTrimmedWindow.class);
        window.getChildren().add(part);
        window.setWidth(250);
        window.setHeight(250);
        
        application.getChildren().add(window);
        window.setVisible(true);
    }
    
    
    public void openWindow() {
        MPart part = eModelService.createModelElement(MPart.class);
        part.setLabel("Create Label");
        part.setContributionURI("bundleclass://RCPTutorial/rcptutorial.part.CreatePart");
        
        
        MTrimmedWindow window = eModelService.createModelElement(MTrimmedWindow.class);
        window.getChildren().add(part);
        window.setWidth(250);
        window.setHeight(250);
        
        application.getChildren().add(window);
        
        window.setVisible(true);
    }
    
}