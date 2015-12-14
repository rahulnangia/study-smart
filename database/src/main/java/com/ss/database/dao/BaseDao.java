package com.ss.database.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Defines all basic CRUD operations
 *
 * Created by rahul on 12/2/15.
 */
public interface BaseDao {
    /**
     * Fetching any generic object (corresponding to a row) from the table based on its id
     *
     * @param clazz class of the object
     * @param key   id of the row
     * @param <T>   datatype of the class
     * @return object corresponding to the row
     */
    public <T> T get(Class<T> clazz, Serializable key);


    /**
     * Fetching all the objects for the given class (table)
     *
     * @param c   class of the objects (table) to be fetched
     * @param <T> datatype of the class
     * @return collection of objects representing the table
     */
    public <T> List<T> getAll(Class<T> c);


    /**
     * Save a new entity in the database
     *
     * @param entity entity to be saved
     * @param <T>
     * @return returns the saved entity
     */
    public <T> T save(Object entity);


    /**
     * Delete an entity from the database
     *
     * @param entity entity to be deleted
     */
    public <T> void delete(Object entity);


    /**
     * Delete all the collection of entities from the database
     *
     * @param entities collection of entities to be deleted
     */
    public <T> void deleteAll(Collection entities);


    /**
     * Save collection of entities in the database
     *
     * @param entities collection of entities to be saved
     */
    public <T> void saveAll(Collection entities);

    /**
     * SaveOrUpdate entity in the database
     *
     * @param entity
     */
    public <T> void saveOrUpdate(Object entity);

    /**
     * SaveOrUpdate collection of entities in the database
     *
     * @param entities
     */
    public <T> void saveOrUpdateAll(Collection entities);


    /**
     * Returns the number of entities in the database for the class (table)
     *
     * @param clazz clazz (table) for which count needs to be determined
     * @return number of rows
     */
    public <T> int getTotalCount(Class clazz);


    /**
     * Updates an entity in the database.
     * <i>Note</i>: This does not insert a new row in the database.
     *
     * @param entity entity to be updated
     * @param <T>    datatype of the entity
     * @return updated entity
     */
    public <T> T update(Object entity);
}
