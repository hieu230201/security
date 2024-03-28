package com.example.securitybase.util;

import com.example.securitybase.comon.enums.SortType;
import com.example.securitybase.comon.enums.SqlType;
import com.example.securitybase.exception.CustomServiceBusinessException;
import com.example.securitybase.model.atos.clones.bases.CloneableAto;
import com.example.securitybase.model.atos.clones.bases.InfoCloneable;
import com.example.securitybase.model.atos.clones.bases.ChildCloneableAto;
import com.example.securitybase.util.delegates.FuncDelegate;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//import vn.com.mbbank.cmv.model.atos.clones.bases.FileCloneableAto;

/**
 * @author caunv
 * @created_date 09/04/2021 - 1:41 PM
 */
@Component
public class SqlQueryUtil {

    public static class SqlQueryModel {
        private final NamedParameterJdbcTemplate jdbcTemplate;
        private final ApplicationContext context;


        SqlQueryModel(NamedParameterJdbcTemplate jdbcTemplate,
                      ApplicationContext context) {
            this.jdbcTemplate = jdbcTemplate;
            this.context = context;
        }

        private <T> List<T> queryForListPrimitive(String sql, MapSqlParameterSource parameters, Class<T> clazz) {
            return jdbcTemplate.queryForList(sql, parameters, clazz);
        }

        private <T> List<T> queryForListNotPrimitive(String sql, MapSqlParameterSource parameters, Class<T> clazz) {
            return jdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper<>(clazz));
        }

        @SuppressWarnings("unchecked")
        private <T> T queryForObjectPrimitive(String sql, MapSqlParameterSource parameters, Class<T> clazz) {
            var result = queryForListPrimitive(sql, parameters, clazz);
            if (result.isEmpty()) {
                return (T) CommonUtil.getDefaultValue(clazz);
            } else {
                return result.get(0);
            }
        }

        @SuppressWarnings("unchecked")
        private <T> T queryForObjectNotPrimitive(String sql, MapSqlParameterSource parameters, Class<T> clazz) {
            var result = queryForListNotPrimitive(sql, parameters, clazz);
            if (result.isEmpty()) {
                return (T) CommonUtil.getDefaultValue(clazz);
            } else {
                return result.get(0);
            }
        }

        public <T> T queryComplex(String sql, MapSqlParameterSource parameters, PreparedStatementCallback<T> action) {
            return jdbcTemplate.execute(sql, parameters, action);
        }

        public <T> T queryComplex(String sql, Map<String, Object> params, PreparedStatementCallback<T> action) {
            return queryComplex(sql, new MapSqlParameterSource(params), action);
        }

        public <T> T queryComplex(String sql, PreparedStatementCallback<T> action) {
            return queryComplex(sql, new HashMap<>(), action);
        }

        public <T> List<T> queryComplexForList(String sql, MapSqlParameterSource parameters, FuncDelegate.Func_2<ResultSet, T> action) {
            return queryComplex(sql, parameters, ps -> {
                var resultSet = ps.executeQuery();
                List<T> listData = new ArrayList<>();

                while (resultSet.next()) {
                    var dataSave = action.doAction(resultSet);
                    listData.add(dataSave);
                }
                return listData;
            });
        }

        public <T> List<T> queryComplexForList(String sql, Map<String, Object> params, FuncDelegate.Func_2<ResultSet, T> action) {
            return queryComplexForList(sql, new MapSqlParameterSource(params), action);
        }

        public <T> List<T> queryComplexForList(String sql, FuncDelegate.Func_2<ResultSet, T> action) {
            return queryComplexForList(sql, new HashMap<>(), action);
        }

        public <T> T queryComplexForObject(String sql, MapSqlParameterSource parameters, FuncDelegate.Func_2<ResultSet, T> action) {
            return queryComplex(sql, parameters, ps -> {
                var resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    return action.doAction(resultSet);
                }
                return null;
            });
        }

        public <T> T queryComplexForObject(String sql, Map<String, Object> params, FuncDelegate.Func_2<ResultSet, T> action) {
            return queryComplexForObject(sql, new MapSqlParameterSource(params), action);
        }

        public <T> T queryComplexForObject(String sql, FuncDelegate.Func_2<ResultSet, T> action) {
            return queryComplexForObject(sql, new HashMap<>(), action);
        }


        public <T> T queryForObject(String sql, MapSqlParameterSource parameters, Class<T> clazz) {
            if (CommonUtil.isPrimitiveTypeOrWrapper(clazz)) {
                return queryForObjectPrimitive(sql, parameters, clazz);
            }
            return queryForObjectNotPrimitive(sql, parameters, clazz);
        }

        public <T> T queryForObject(String sql, Map<String, Object> params, Class<T> clazz) {
            var parameters = new MapSqlParameterSource(params);
            return queryForObject(sql, parameters, clazz);
        }

        public <T> T queryForObject(String sql, Class<T> clazz) {
            var params = new HashMap<String, Object>();
            return queryForObject(sql, params, clazz);
        }


        public <T> List<T> queryForList(String sql, MapSqlParameterSource parameters, Class<T> clazz) {
            if (CommonUtil.isPrimitiveTypeOrWrapper(clazz)) {
                return queryForListPrimitive(sql, parameters, clazz);
            }
            return queryForListNotPrimitive(sql, parameters, clazz);
        }

        public <T> Page<T> queryForListPaging(String sql, MapSqlParameterSource parameters, Class<T> clazz, Pageable pageable) {
            Pageable paging =  PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
            if (pageable == null) {
                pageable = PageRequest.of(1, 20);
            } else {
                Integer page = pageable.getPageNumber() + 1;
                pageable = PageRequest.of(page, pageable.getPageSize());
            }

            StringBuilder countSql = new StringBuilder();
            countSql.append("SELECT COUNT(1) FROM (" + sql + ")");

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SELECT * FROM\n" +
                    "(\n" +
                    "    SELECT a.*, rownum r__\n" +
                    "    FROM\n" +
                    "    (\n");
            stringBuilder.append(sql);
            stringBuilder.append("\n");
            stringBuilder.append(") a\n" +
                    "    WHERE rownum < ((" + pageable.getPageNumber() + " * " + pageable.getPageSize() + ") + 1 )\n" +
                    ")\n" +
                    "WHERE r__ >= (((" + pageable.getPageNumber() + "-1) * " + pageable.getPageSize() + ") + 1)");


            Integer total = queryForObject(countSql.toString(), parameters, Integer.class);
            if (total == null) total = 0;
            List<T> tList;
            if (CommonUtil.isPrimitiveTypeOrWrapper(clazz)) {
                tList = queryForListPrimitive(stringBuilder.toString(), parameters, clazz);
            } else
                tList = queryForListNotPrimitive(stringBuilder.toString(), parameters, clazz);
            var page =  new PageImpl<T>(tList, paging, total);
            return page;

        }

        public <T> List<T> queryForList(String sql, Map<String, Object> params, Class<T> clazz) {
            var parameters = new MapSqlParameterSource(params);
            return queryForList(sql, parameters, clazz);
        }

        public <T> Page<T> queryForListPaging(String sql, Map<String, Object> params, Class<T> clazz, Pageable pageable) {
            var parameters = new MapSqlParameterSource(params);
            return queryForListPaging(sql, parameters, clazz, pageable);
        }

        public <T> List<T> queryForList(String sql, Class<T> clazz) {
            var params = new HashMap<String, Object>();
            return queryForList(sql, params, clazz);
        }


        public Integer nonQuery(String sql, MapSqlParameterSource parameters) {
            return jdbcTemplate.update(sql, parameters);
        }

        public Integer nonQuery(String sql, Map<String, Object> params) {
            var parameters = new MapSqlParameterSource(params);
            return nonQuery(sql, parameters);
        }

        public Integer nonQuery(String sql) {
            var params = new HashMap<String, Object>();
            return nonQuery(sql, params);
        }

    }

    public static class SqlQueryEntity {
        private final EntityManager entityManager;
        private final SqlQueryModel sqlQueryModel;
        private final ApplicationContext context;

        SqlQueryEntity(EntityManager entityManager, SqlQueryModel sqlQueryModel, ApplicationContext context) {
            this.entityManager = entityManager;
            this.sqlQueryModel = sqlQueryModel;
            this.context = context;
        }

        private <T> Query getQuery(String queryStr, SqlType sqlType, Map<String, Object> params, Class<T> entityClazz) {
            Query query;
            switch (sqlType) {
                case HQL:
                    query = entityManager.createQuery(queryStr, entityClazz);
                    break;
                case SQL:
                    query = entityManager.createNativeQuery(queryStr, entityClazz);
                    break;
                default:
                    return null;
            }
            if (params != null && !params.isEmpty()) {
                for (var param : params.entrySet()) {
                    query.setParameter(param.getKey(), param.getValue());
                }
            }
            return query;
        }

        @SuppressWarnings("unused")
        private <T> Query getQuery(String queryStr, SqlType sqlType, Class<T> entityClazz) {
            return getQuery(queryStr, sqlType, null, entityClazz);
        }

        @SuppressWarnings("unchecked")
        public <T> List<T> query(String queryStr, SqlType sqlType, Map<String, Object> params, Class<T> entityClazz) {
            var query = getQuery(queryStr, sqlType, params, entityClazz);
            if (query == null)
                return new ArrayList<>();
            return query.getResultList();
        }

        public <T> List<T> query(String queryStr, SqlType sqlType, Class<T> entityClazz) {
            return query(queryStr, sqlType, null, entityClazz);
        }

        @SuppressWarnings("unchecked")
        public <T> T querySingle(String queryStr, SqlType sqlType, Map<String, Object> params, Class<T> entityClazz) {
            var query = getQuery(queryStr, sqlType, params, entityClazz);
            if (query == null)
                return null;
            try {
                var list = query(queryStr, sqlType, params, entityClazz);
                if (list.isEmpty())
                    return null;
                return list.get(0);
            } catch (Exception ex) {
                return null;
            }
        }

        public <T> T querySingle(String queryStr, SqlType sqlType, Class<T> entityClazz) {
            return querySingle(queryStr, sqlType, null, entityClazz);
        }

        public <T> Integer nonQuery(String queryStr, SqlType sqlType, Map<String, Object> params, Class<T> entityClazz) {
            var query = getQuery(queryStr, sqlType, params, entityClazz);
            if (query == null)
                return 0;
            return query.executeUpdate();
        }

        public <T> Integer nonQuery(String queryStr, SqlType sqlType, Class<T> entityClazz) {
            return nonQuery(queryStr, sqlType, null, entityClazz);
        }

        public <T> List<T> queryHql(String hql, Map<String, Object> params, Class<T> entityClazz) {
            return query(hql, SqlType.HQL, params, entityClazz);
        }

        public <T> List<T> queryHql(String hql, Class<T> entityClazz) {
            return queryHql(hql, null, entityClazz);
        }

        public <T> List<T> querySql(String sql, Map<String, Object> params, Class<T> entityClazz) {
            return query(sql, SqlType.SQL, params, entityClazz);
        }

        public <T> List<T> querySql(String sql, Class<T> entityClazz) {
            return querySql(sql, null, entityClazz);
        }

        public <T> T querySingleHql(String hql, Map<String, Object> params, Class<T> entityClazz) {
            return querySingle(hql, SqlType.HQL, params, entityClazz);
        }

        public <T> T querySingleHql(String hql, Class<T> entityClazz) {
            return querySingleHql(hql, null, entityClazz);
        }

        public <T> T querySingleSql(String sql, Map<String, Object> params, Class<T> entityClazz) {
            return querySingle(sql, SqlType.SQL, params, entityClazz);
        }

        public <T> T querySingleSql(String sql, Class<T> entityClazz) {
            return querySingleSql(sql, null, entityClazz);
        }

        public <T> Integer nonQueryHql(String hql, Map<String, Object> params, Class<T> entityClazz) {
            return nonQuery(hql, SqlType.HQL, params, entityClazz);
        }

        public <T> Integer nonQueryHql(String hql, Class<T> entityClazz) {
            return nonQueryHql(hql, null, entityClazz);
        }

        public <T> Integer nonQuerySql(String sql, Map<String, Object> params, Class<T> entityClazz) {
            return nonQuery(sql, SqlType.SQL, params, entityClazz);
        }

        public <T> Integer nonQuerySql(String sql, Class<T> entityClazz) {
            return nonQuerySql(sql, null, entityClazz);
        }


        @SuppressWarnings("unchecked")
        public <T> Page<T> queryPaging(String queryStr, SqlType sqlType, Map<String, Object> params, Class<T> entityClazz,
                                       Pageable pageable) {

            var query = getQuery(queryStr, sqlType, params, entityClazz);

            if (query == null)
                return new PageImpl<T>(new ArrayList<>());


            List<T> result;
            if (pageable == null) {
                result = query.getResultList();
                return new PageImpl<T>(result, Pageable.unpaged(), result.size());
            }

            query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            query.setMaxResults(pageable.getPageSize());
            result = query.getResultList();


            var total = sqlQueryModel.queryForObject("SELECT COUNT(1) FROM (" + queryStr + ")", params, Integer.class);

            return new PageImpl<T>(result, pageable, total == null ? 0 : total);
        }

        public <T> Page<T> queryPaging(String queryStr, SqlType sqlType, Map<String, Object> params, Class<T> entityClazz,
                                       int page, int size, String sortBy, SortType sortType) {
            if (page < 0) {
                page = 0;
            }
            if (size < 1) {
                size = 1;
            }
            PageRequest pageRequest;
            if (sortBy == null || sortBy.isBlank()) {
                pageRequest = PageRequest.of(page, size);
            } else {
                if (sortType == SortType.DESC) {
                    pageRequest = PageRequest.of(page, size, Sort.by(sortBy).descending());
                } else {
                    pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
                }
            }
            return queryPaging(queryStr, sqlType, params, entityClazz, pageRequest);
        }

        public <T> Page<T> querySqlPaging(String sql, Map<String, Object> params, Class<T> entityClazz,
                                          Pageable pageable) {
            return queryPaging(sql, SqlType.SQL, params, entityClazz, pageable);
        }

        public <T> Page<T> querySqlPaging(String sql, Map<String, Object> params, Class<T> entityClazz,
                                          int page, int size, String sortBy, SortType sortType) {
            return queryPaging(sql, SqlType.SQL, params, entityClazz, page, size, sortBy, sortType);
        }

        public <T> Page<T> querySqlPaging(String sql, Class<T> entityClazz,
                                          Pageable pageable) {
            return querySqlPaging(sql, new HashMap<>(), entityClazz, pageable);
        }

        public <T> Page<T> querySqlPaging(String sql, Class<T> entityClazz,
                                          int page, int size, String sortBy, SortType sortType) {
            return querySqlPaging(sql, new HashMap<>(), entityClazz, page, size, sortBy, sortType);
        }


        public <T> Page<T> queryHqlPaging(String sql, Map<String, Object> params, Class<T> entityClazz,
                                          Pageable pageable) {
            return queryPaging(sql, SqlType.HQL, params, entityClazz, pageable);
        }

        public <T> Page<T> queryHqlPaging(String sql, Map<String, Object> params, Class<T> entityClazz,
                                          int page, int size, String sortBy, SortType sortType) {
            return queryPaging(sql, SqlType.HQL, params, entityClazz, page, size, sortBy, sortType);
        }

        public <T> Page<T> queryHqlPaging(String sql, Class<T> entityClazz,
                                          Pageable pageable) {
            return queryHqlPaging(sql, new HashMap<>(), entityClazz, pageable);
        }

        public <T> Page<T> queryHqlPaging(String sql, Class<T> entityClazz,
                                          int page, int size, String sortBy, SortType sortType) {
            return queryHqlPaging(sql, new HashMap<>(), entityClazz, page, size, sortBy, sortType);
        }

    }

    public static class SqlQueryable {
        public static class CloneInfoMapping {
            @JsonIgnore
            CloneableAto<?> cloneableAto;
            Long oldId;
            Long newId;

            @JsonIgnore
            public CloneableAto<?> getCloneableAto() {
                return cloneableAto;
            }

            @JsonIgnore
            public void setCloneableAto(CloneableAto<?> cloneableAto) {
                this.cloneableAto = cloneableAto;
            }

            public Long getOldId() {
                return oldId;
            }

            public void setOldId(Long oldId) {
                this.oldId = oldId;
            }

            public Long getNewId() {
                return newId;
            }

            public void setNewId(Long newId) {
                this.newId = newId;
            }
        }

        private final EntityManager entityManager;
        private final ApplicationContext context;

        SqlQueryable(EntityManager entityManager,
                     ApplicationContext context
        ) {
            this.entityManager = entityManager;
            this.context = context;
        }

        @SuppressWarnings({"rawtypes", "unchecked"})
        public List<CloneInfoMapping> doClone(List<InfoCloneable> cloneableList,
                                              InfoCloneable currentInfoCloneable, List<CloneInfoMapping> cloneInfoMappings)
                throws CustomServiceBusinessException {
            if (cloneInfoMappings == null) {
                cloneInfoMappings = new ArrayList<>();
            }
            if (cloneableList == null || cloneableList.isEmpty()) {
                return cloneInfoMappings;
            }


            List<InfoCloneable> listParent = new ArrayList<>();
            if (currentInfoCloneable != null) {
                listParent.add(currentInfoCloneable);
            } else {
                listParent = cloneableList.stream()
                        .filter(x -> !(x.getCloneable() instanceof ChildCloneableAto))
                        .collect(Collectors.toList());
            }

            for (var infoCloneable : listParent) {
                var childrenInfoCloneable = cloneableList.stream()
                        .filter(x -> {
                            if (x.getCloneable() instanceof ChildCloneableAto) {
                                return ((ChildCloneableAto<?>) x.getCloneable()).getParentCloneable() == infoCloneable.getCloneable();
                            }
                            return false;
                        }).collect(Collectors.toList());

                var cloneable = infoCloneable.getCloneable();
                var listData = infoCloneable.getCloneEntities();
                if (listData == null || listData.isEmpty())
                    continue;
                for (var data : listData) {
                    var oldId = cloneable.getId(data);
                    entityManager.detach(data);
                    var newData = infoCloneable.getCloneable().resetId(data);
                    newData = infoCloneable.doFactor(data);
                    newData = cloneable.getRepository().save(newData);
                    var newId = cloneable.getId(newData);

                    var cloneInfoMapping = new CloneInfoMapping();
                    cloneInfoMapping.setCloneableAto(cloneable);
                    cloneInfoMapping.setOldId(oldId);
                    cloneInfoMapping.setNewId(newId);
                    cloneInfoMappings.add(cloneInfoMapping);

//                    if (cloneable instanceof FileCloneableAto) {
//                        ((FileCloneableAto<?>) cloneable).doCloneFile(oldId, newId, context);
//                    }

                    if (!childrenInfoCloneable.isEmpty()) {
                        for (var childInfoCloneable : childrenInfoCloneable) {
                            var childCloneable = (ChildCloneableAto) childInfoCloneable.getCloneable();

                            var dataByParentId = childCloneable.getByParentId(oldId);
                            List<Object> newListData = new ArrayList<>();
                            for (var item : dataByParentId) {
                                entityManager.detach(item);
                                newListData.add(childCloneable.doCloneByParentId(item, newId));
                            }
                            childInfoCloneable.setCloneEntities(newListData);

                            doClone(cloneableList, childInfoCloneable, cloneInfoMappings);
                        }
                    }

                }
            }

            return cloneInfoMappings;
        }

    }

    @PersistenceContext
    EntityManager entityManager;

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ApplicationContext context;

    @Autowired
    public SqlQueryUtil(NamedParameterJdbcTemplate jdbcTemplate,
                        ApplicationContext context
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.context = context;
    }

    public SqlQueryModel queryModel() {
        return new SqlQueryModel(this.jdbcTemplate, context);
    }

    public SqlQueryEntity queryEntity() {
        return new SqlQueryEntity(this.entityManager, this.queryModel(), context);
    }

    public SqlQueryable queryable() {
        return new SqlQueryable(this.entityManager, context);
    }

}