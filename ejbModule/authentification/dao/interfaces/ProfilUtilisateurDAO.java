package authentification.dao.interfaces;


import dao.base.GenericDAO;
import model.reference.ProfilsUtilisateur;
import java.util.List;

/**
 * Interface DAO pour l'entité ProfilsUtilisateur
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public interface ProfilsUtilisateurDAO extends GenericDAO<ProfilsUtilisateur, Integer> {
    
    /**
     * Trouve un profil par son code
     * @param code Le code du profil
     * @return Le profil trouvé ou null
     */
    ProfilsUtilisateur findByCode(String code);
    
    /**
     * Trouve tous les profils actifs
     * @return Liste des profils actifs
     */
    List<ProfilsUtilisateur> findAllActive();
    
    /**
     * Trouve les profils par niveau d'accès
     * @param niveauAcces Le niveau d'accès
     * @return Liste des profils correspondants
     */
    List<ProfilsUtilisateur> findByNiveauAcces(Integer niveauAcces);
    
    /**
     * Vérifie si un code de profil existe
     * @param code Le code à vérifier
     * @return true si le code existe, false sinon
     */
    boolean codeExists(String code);
}