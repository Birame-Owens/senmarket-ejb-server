package authentification.base.dao;



import java.io.Serializable;
import java.util.List;

/**
 * Interface DAO générique pour toutes les entités du module authentification
 * Fournit les opérations CRUD de base
 * 
 * @author SenMarket Team
 * @version 1.0
 * @param <T> Type de l'entité
 * @param <ID> Type de l'identifiant
 */
public interface GenericDAO<T, ID extends Serializable> {
    
    /**
     * Persiste une nouvelle entité
     * @param entity L'entité à persister
     * @return L'entité persistée avec son ID généré
     */
    T create(T entity);
    
    /**
     * Trouve une entité par son ID
     * @param id L'identifiant de l'entité
     * @return L'entité trouvée ou null si non trouvée
     */
    T findById(ID id);
    
    /**
     * Met à jour une entité existante
     * @param entity L'entité à mettre à jour
     * @return L'entité mise à jour
     */
    T update(T entity);
    
    /**
     * Supprime une entité par son ID
     * @param id L'identifiant de l'entité à supprimer
     */
    void delete(ID id);
    
    /**
     * Supprime une entité
     * @param entity L'entité à supprimer
     */
    void delete(T entity);
    
    /**
     * Trouve toutes les entités
     * @return Liste de toutes les entités
     */
    List<T> findAll();
    
    /**
     * Trouve toutes les entités avec pagination
     * @param firstResult Premier résultat (0-based)
     * @param maxResults Nombre maximum de résultats
     * @return Liste paginée des entités
     */
    List<T> findAll(int firstResult, int maxResults);
    
    /**
     * Compte le nombre total d'entités
     * @return Nombre total d'entités
     */
    long count();
    
    /**
     * Vérifie si une entité existe par son ID
     * @param id L'identifiant à vérifier
     * @return true si l'entité existe, false sinon
     */
    boolean exists(ID id);
    
    /**
     * Exécute une requête nommée sans paramètres
     * @param namedQuery Nom de la requête nommée
     * @return Liste des résultats
     */
    List<T> findByNamedQuery(String namedQuery);
    
    /**
     * Exécute une requête nommée avec paramètres
     * @param namedQuery Nom de la requête nommée
     * @param parameters Paramètres de la requête (nom -> valeur)
     * @return Liste des résultats
     */
    List<T> findByNamedQuery(String namedQuery, Object... parameters);
    
    /**
     * Exécute une requête nommée avec pagination
     * @param namedQuery Nom de la requête nommée
     * @param firstResult Premier résultat (0-based)
     * @param maxResults Nombre maximum de résultats
     * @param parameters Paramètres de la requête
     * @return Liste paginée des résultats
     */
    List<T> findByNamedQuery(String namedQuery, int firstResult, int maxResults, Object... parameters);
    
    /**
     * Exécute une requête nommée qui retourne un seul résultat
     * @param namedQuery Nom de la requête nommée
     * @param parameters Paramètres de la requête
     * @return Le résultat unique ou null si aucun résultat
     */
    T findSingleByNamedQuery(String namedQuery, Object... parameters);
    
    /**
     * Force la synchronisation avec la base de données
     */
    void flush();
    
    /**
     * Rafraîchit l'état d'une entité depuis la base de données
     * @param entity L'entité à rafraîchir
     */
    void refresh(T entity);
    
    /**
     * Détache une entité du contexte de persistance
     * @param entity L'entité à détacher
     */
    void detach(T entity);
}