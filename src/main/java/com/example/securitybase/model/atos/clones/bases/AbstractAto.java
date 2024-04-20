package com.example.securitybase.model.atos.clones.bases;

import com.example.securitybase.util.ModelMapperUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.modelmapper.ExpressionMap;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AbstractAto<TEntity, TAto extends AbstractAto<TEntity, ?>> {

    @JsonIgnore
    public abstract Class<TEntity> getEntityClass();

    @JsonIgnore
    public abstract Class<TAto> getAtoClass();

    public TEntity toEntity() {
        return ModelMapperUtil.toObject(this, getEntityClass());
    }

    public TEntity toEntity(ExpressionMap<TAto, TEntity> expressionMap) {
        return ModelMapperUtil.toObject((TAto) this, getAtoClass(), getEntityClass(), expressionMap);
    }

    public TAto fromEntity(TEntity entity) {
        return ModelMapperUtil.toObject(entity, getAtoClass());
    }

    public <O> TAto fromEntity(TEntity entity, ExpressionMap<TEntity, TAto> expressionMap) {
        return ModelMapperUtil.toObject(entity, getEntityClass(), getAtoClass(), expressionMap);
    }
}
