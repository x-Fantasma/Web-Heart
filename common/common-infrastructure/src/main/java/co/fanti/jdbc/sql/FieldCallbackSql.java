package co.fanti.jdbc.sql;

import co.fanti.exception.ExceptionTechnical;
import org.apache.commons.io.IOUtils;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

public class FieldCallbackSql implements ReflectionUtils.FieldCallback {

    public static final String YOU_MUST_ENTER_THE_NAME_OF_THE_FOLDER_OF_THE_SQL = "Se debe ingresar el nombre de la carpeta del sql";
    public static final String YOU_MUST_ENTER_THE_NAME_OF_THE_SQL = "Se debe ingresar el nombre del sql";
    public static final String SQL_FILE_NOT_FOUND = "No se enconro el archivo sql";
    private final static String SQL_PATH = "sql/";
    private final static String SQL_EXTENSION = ".sql";

    private Object object;

    public FieldCallbackSql(Object object) {
        this.object = object;
    }

    @Override
    public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {

        if(!field.isAnnotationPresent(Sql.class)) {
            return;
        }

        ReflectionUtils.makeAccessible(field);

        var folder = field.getDeclaredAnnotation(Sql.class).folder();
        var sql = field.getDeclaredAnnotation(Sql.class).sql();

        validateValue(folder, YOU_MUST_ENTER_THE_NAME_OF_THE_FOLDER_OF_THE_SQL);
        validateValue(sql, YOU_MUST_ENTER_THE_NAME_OF_THE_SQL);

        var sqlPath = SQL_PATH + folder;

        if(!sql.contains(SQL_EXTENSION)) {
            sqlPath += SQL_EXTENSION;
        }

        try(InputStream sqlStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(sqlPath)) {

            if(sqlStream == null) {
                throw new ExceptionTechnical(SQL_FILE_NOT_FOUND);
            }

            field.set(object, IOUtils.toString(sqlStream, StandardCharsets.UTF_8.name()));
            field.canAccess(false);

        } catch (IOException exception) {
            throw new ExceptionTechnical(SQL_FILE_NOT_FOUND, exception);
        }
    }


    private void validateValue(String value, String message) {
        if(value.isEmpty()) {
           throw new ExceptionTechnical(message);
        }
    }
}
