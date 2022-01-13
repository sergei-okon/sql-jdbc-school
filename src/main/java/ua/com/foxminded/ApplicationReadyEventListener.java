package ua.com.foxminded;


import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ua.com.foxminded.service.InitialData;

import java.io.IOException;

@Component
public class ApplicationReadyEventListener {
    private final InitialData initialData;

    public ApplicationReadyEventListener(InitialData initialData) {
        this.initialData = initialData;
    }

    @EventListener(ContextStartedEvent.class)
    public void InitializeData() throws IOException {

        System.out.println("Data initialization _______________________________________________");
        initialData.addCoursesToDataBase();
        System.out.println("------------------------------------------------------------------");
        initialData.addGroupsToDataBase();
        System.out.println("------------------------------------------------------------------");
        initialData.addStudentsToDataBase();
        System.out.println("------------------------------------------------------------------");
    }
}
