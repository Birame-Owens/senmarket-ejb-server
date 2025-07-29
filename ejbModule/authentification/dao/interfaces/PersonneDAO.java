package authentification.dao.interfaces;



import dao.base.GenericDAO;
import model.utilisateur.Personnes;
import java.util.List;

/**
 * Interface DAO pour l'entité Personnes
 * Définit les opérations spécifiques à la gestion des personnes
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public interface PersonnesDAO extends GenericDAO<Personnes, Integer> {
    
    /**
     * Trouve une personne par son email
     * @param email L'email de la personne
     * @return La personne trouvée ou null
     */
    Personnes findByEmail(String email);
    
    /**
     * Trouve une personne par son numéro de téléphone
     * @param telephone Le numéro de téléphone
     * @return La personne trouvée ou null
     */
    Personnes findByTelephone(String telephone);
    
    /**
     * Trouve une personne par son UUID
     * @param uuid L'UUID de la personne
     * @return La personne trouvée ou null
     */
    Personnes findByUuid(String uuid);
    
    /**
     * Trouve toutes les personnes actives
     * @return Liste des personnes actives
     */
    List<Personnes> findAllActive();
    
    /**
     * Trouve les personnes par profil
     * @param profilId L'ID du profil
     * @return Liste des personnes avec ce profil
     */
    List<Personnes> findByProfil(Integer profilId);
    
    /**
     * Recherche des personnes par nom (partiel)
     * @param nom Le nom ou partie du nom à rechercher
     * @return Liste des personnes correspondantes
     */
    List<Personnes> searchByName(String nom);
    
    /**
     * Recherche des personnes par ville
     * @param ville La ville
     * @return Liste des personnes de cette ville
     */
    List<Personnes> findByVille(String ville);
    
    /**
     * Vérifie si un email existe déjà
     * @param email L'email à vérifier
     * @return true si l'email existe, false sinon
     */
    boolean emailExists(String email);
    
    /**
     * Vérifie si un téléphone existe déjà
     * @param telephone Le téléphone à vérifier
     * @return true si le téléphone existe, false sinon
     */
    boolean telephoneExists(String telephone);
    
    /**
     * Compte les personnes par profil
     * @param profilId L'ID du profil
     * @return Nombre de personnes avec ce profil
     */
    long countByProfil(Integer profilId);
    
    /**
     * Désactive une personne (soft delete)
     * @param personneId L'ID de la personne à désactiver
     * @return Nombre de lignes affectées
     */
    int deactivatePerson(Integer personneId);
    
    /**
     * Active une personne
     * @param personneId L'ID de la personne à activer
     * @return Nombre de lignes affectées
     */
    int activatePerson(Integer personneId);
    
    /**
     * Met à jour la dernière activité d'une personne
     * @param personneId L'ID de la personne
     * @return Nombre de lignes affectées
     */
    int updateLastActivity(Integer personneId);
}