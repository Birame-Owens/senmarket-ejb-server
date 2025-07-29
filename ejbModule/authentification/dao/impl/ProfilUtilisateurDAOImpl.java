package authentification.dao.impl;

import dao.base.GenericDAOImpl;
import model.reference.ProfilsUtilisateur;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Implémentation DAO pour l'entité ProfilsUtilisateur
 * 
 * @author SenMarket Team
 * @version 1.0
 */
@Stateless
public class ProfilsUtilisateurDAOImpl extends GenericDAOImpl<ProfilsUtilisateur, Integer> implements ProfilsUtilisateurDAO {
    
    @Override
    public ProfilsUtilisateur findByCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }
        
        return findSingleByNamedQuery("ProfilsUtilisateur.findByCode", "code", code.trim());
    }
    
    @Override
    public List<ProfilsUtilisateur> findAllActive() {
        return findByNamedQuery("ProfilsUtilisateur.findAll");
    }
    
    @Override
    public List<ProfilsUtilisateur> findByNiveauAcces(Integer niveauAcces) {
        if (niveauAcces == null) {
            return List.of();
        }
        
        return findByNamedQuery("ProfilsUtilisateur.findByNiveauAcces", "niveau", niveauAcces);
    }
    
    @Override
    public boolean codeExists(String code) {
        if (code == null || code.trim().isEmpty()) {
            return false;
        }
        
        try {
            String queryString = "SELECT COUNT(p) FROM ProfilsUtilisateur p WHERE p.code = :code";
            TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
            query.setParameter("code", code.trim());
            return query.getSingleResult() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
