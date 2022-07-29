package co.fanti.jdbc.sql;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class AnnotationProcessorSql implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object object, String objectName) throws BeansException {
        this.processSqlAnnotation(object);
        return object;
    }

    @Override
    public Object postProcessAfterInitialization(Object object, String objectName) throws BeansException {
        return object;
    }


    private void processSqlAnnotation(Object object) {
        var objectClass = object.getClass();
        var fieldCallback = new FieldCallbackSql(object);
        ReflectionUtils.doWithFields(objectClass, fieldCallback);
    }
}
