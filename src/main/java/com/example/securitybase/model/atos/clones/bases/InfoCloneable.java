package com.example.securitybase.model.atos.clones.bases;

import java.util.List;

/**
 * Info to do a clone [TEntity]
 * @author caunv
 * @created_date 29/04/2021 - 8:56 AM
 */
public interface InfoCloneable<TEntity> {
    /**
     * Get [Cloneable] of the [TEntity]
     * @return [Cloneable] of the [TEntity]
     */
    CloneableAto<TEntity> getCloneable();

    /**
     * Getter of records want to be clone
     * @return Records will be clone
     */
    List<TEntity> getCloneEntities();

    /**
     * Setter ò records want to be clone
     * @param entities: Records want to be clone
     */
    void setCloneEntities(List<TEntity> entities);

    /**
     * Factory, change property when Clone
     * @param entity: current data
     * @return newData
     */
    TEntity doFactor(TEntity entity);

}
