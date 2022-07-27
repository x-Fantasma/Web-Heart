package co.fanti.jdbc;


import co.fanti.exception.ExceptionTechnical;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;

@Repository
public class CustomNamedParameterJdbcTemplate {

    public static final String ERROR_MAPPING_THE_NAME_AND_VALUE_OF_THE_OBJECT = "Error mapeando el nombre y valor del objeto";
    public static final String ID = "id";


    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CustomNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    public Long create(Object object, String sql) {
        var sqlParamSource = createParams(object);
        var keyHolder = new GeneratedKeyHolder();
        var idColumn = new String[] { ID };
        this.namedParameterJdbcTemplate.update(sql, sqlParamSource, keyHolder, idColumn);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public void update(Object object, String sql) {
        var sqlParamSource = createParams(object);
        this.namedParameterJdbcTemplate.update(sql, sqlParamSource);
    }



    private MapSqlParameterSource createParams(Object object) {

        var sqlParamSource = new MapSqlParameterSource();
        var fields = object.getClass().getDeclaredFields();

        for (Field field: fields) {
           try {
               if(!validateModifierStaticOrFinal(field)) {

                   ReflectionUtils.makeAccessible(field);
                   sqlParamSource.addValue(field.getName(), field.get(object));
                   field.canAccess(false);
               }
           }catch (Exception e) {
               throw new ExceptionTechnical(ERROR_MAPPING_THE_NAME_AND_VALUE_OF_THE_OBJECT, e);
           }
        }

        return sqlParamSource;
    }

    private boolean validateModifierStaticOrFinal(Field field) {
        return Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers());
    }

    private NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return this.namedParameterJdbcTemplate;
    }
}
