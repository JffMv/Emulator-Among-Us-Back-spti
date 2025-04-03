package co.edu.ing.escuela.backamongus.EndPoint;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import jakarta.websocket.server.ServerEndpointConfig;

@Component
public class CustomConfigurator extends ServerEndpointConfig.Configurator {

    private static ApplicationContext context;

    public static void setApplicationContext(ApplicationContext context) {
        CustomConfigurator.context = context;
    }

    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        return context.getBean(endpointClass);
    }
}
