package authentification.base.dao;



import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Implémentation générique du DAO
 * Fournit les opérations CRUD de base pour toutes les entités
 * 
 * @author SenMarket Team
 * @version 1.0
 * @param <T> Type de l'entité
 * @param <ID> Type de l'identifiant
 */
public abstract class GenericDAOImpl<T, ID extends Serializable> implements GenericDAO<T, ID> {
    
    @PersistenceContext(unitName = "SenMarketPU")
    protected EntityManager entityManager;
    
    private Class<T> entityClass;
    
    /**
     * Constructeur qui détermine automatiquement la classe de l'entité
     */
    @SuppressWarnings("unchecked")
    public GenericDAOImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }
    
    @Override
    public T create(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("L'entité ne peut pas être null");
        }
        entityManager.persist(entity);
        entityManager.flush(); // Force l'insertion immédiate
        return entity;
    }
    
    @Override
    public T findById(ID id) {
        if (id == null) {
            return null;
        }
        return entityManager.find(entityClass, id);
    }
    
    @Override
    public T update(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("L'entité ne peut pas être null");
        }
        return entityManager.merge(entity);
    }
    
    @Override
    public void delete(ID id) {
        T entity = findById(id);
        if (entity != null) {
            delete(entity);
        }
    }
    
    @Override
    public void delete(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("L'entité ne peut pas être null");
        }
        
        // Si l'entité n'est pas managée, on la merge d'abord
        if (!entityManager.contains(entity)) {
            entity = entityManager.merge(entity);
        }
        entityManager.remove(entity);
    }
    
    @Override
    public List<T> findAll() {
        String queryString = "SELECT e FROM " + entityClass.getSimpleName() + " e";
        TypedQuery<T> query = entityManager.createQuery(queryString, entityClass);
        return query.getResultList();
    }
    
    @Override
    public List<T> findAll(int firstResult, int maxResults) {
        String queryString = "SELECT e FROM " + entityClass.getSimpleName() + " e";
        TypedQuery<T> query = entityManager.createQuery(queryString, entityClass);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }
    
    @Override
    public long count() {
        String queryString = "SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e";
        TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
        return query.getSingleResult();
    }
    
    @Override
    public boolean exists(ID id) {
        return findById(id) != null;
    }
    
    @Override
    public List<T> findByNamedQuery(String namedQuery) {
        TypedQuery<T> query = entityManager.createNamedQuery(namedQuery, entityClass);
        return query.getResultList();
    }
    
    @Override
    public List<T> findByNamedQuery(String namedQuery, Object... parameters) {
        TypedQuery<T> query = entityManager.createNamedQuery(namedQuery, entityClass);
        setParameters(query, parameters);
        return query.getResultList();
    }
    
    @Override
    public List<T> findByNamedQuery(String namedQuery, int firstResult, int maxResults, Object... parameters) {
        TypedQuery<T> query = entityManager.createNamedQuery(namedQuery, entityClass);
        setParameters(query, parameters);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }
    
    @Override
    public T findSingleByNamedQuery(String namedQuery, Object... parameters) {
        try {
            TypedQuery<T> query = entityManager.createNamedQuery(namedQuery, entityClass);
            setParameters(query, parameters);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Override
    public void flush() {
        entityManager.flush();
    }
    
    @Override
    public void refresh(T entity) {
        if (entity != null && entityManager.contains(entity)) {
            entityManager.refresh(entity);
        }
    }
    
    @Override
    public void detach(T entity) {
        if (entity != null && entityManager.contains(entity)) {
            entityManager.detach(entity);
        }
    }
    
    /**
     * Méthode utilitaire pour définir les paramètres d'une requête
     * Les paramètres sont passés par paires : nom, valeur, nom, valeur, etc.
     */
    protected void setParameters(Query query, Object... parameters) {
        if (parameters != null && parameters.length > 0) {
            if (parameters.length % 2 != 0) {
                throw new IllegalArgumentException("Les paramètres doivent être fournis par paires (nom, valeur)");
            }
            
            for (int i = 0; i < parameters.length; i += 2) {
                String paramName = (String) parameters[i];
                Object paramValue = parameters[i + 1];
                query.setParameter(paramName, paramValue);
            }
        }
    }
    
    /**
     * Méthode utilitaire pour exécuter une requête de mise à jour
     */
    protected int executeUpdate(String queryString, Object... parameters) {
        Query query = entityManager.createQuery(queryString);
        setParameters(query, parameters);
        return query.executeUpdate();
    }
    
    /**
     * Méthode utilitaire pour exécuter une requête nommée de mise à jour
     */
    protected int executeNamedUpdate(String namedQuery, Object... parameters) {
        Query query = entityManager.createNamedQuery(namedQuery);
        setParameters(query, parameters);
        return query.executeUpdate();
    }
    
    // Getters pour les classes filles
    protected EntityManager getEntityManager() {
        return entityManager;
    }
    
    protected Class<T> getEntityClass() {
        return entityClass;
    }
}