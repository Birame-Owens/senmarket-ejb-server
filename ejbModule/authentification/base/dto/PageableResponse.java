package authentification.base.dto;



import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * DTO pour les réponses paginées
 * Étend BaseResponse avec des informations de pagination
 * 
 * @author SenMarket Team
 * @version 1.0
 * @param <T> Type des éléments contenus dans la page
 */
public class PageableResponse<T> extends BaseResponse {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Contenu de la page actuelle
     */
    private List<T> contenu;
    
    /**
     * Informations de pagination
     */
    private PaginationInfo pagination;
    
    /**
     * Informations de tri
     */
    private SortInfo tri;
    
    /**
     * Filtres appliqués
     */
    private Map<String, Object> filtres;
    
    /**
     * Statistiques sur les résultats
     */
    private StatistiquesResultats statistiques;
    
    /**
     * Classe interne pour les informations de pagination
     */
    public static class PaginationInfo {
        private int pageActuelle;          // Page actuelle (0-based)
        private int taillePage;            // Nombre d'éléments par page
        private long totalElements;        // Nombre total d'éléments
        private int totalPages;            // Nombre total de pages
        private boolean premierePage;      // Est-ce la première page ?
        private boolean dernierePage;      // Est-ce la dernière page ?
        private boolean aPagePrecedente;   // Y a-t-il une page précédente ?
        private boolean aPageSuivante;     // Y a-t-il une page suivante ?
        private int numeroPagePrecedente;  // Numéro de la page précédente (-1 si aucune)
        private int numeroPageSuivante;    // Numéro de la page suivante (-1 si aucune)
        
        // Constructeur
        public PaginationInfo(int pageActuelle, int taillePage, long totalElements) {
            this.pageActuelle = pageActuelle;
            this.taillePage = taillePage;
            this.totalElements = totalElements;
            this.totalPages = (int) Math.ceil((double) totalElements / taillePage);
            
            this.premierePage = (pageActuelle == 0);
            this.dernierePage = (pageActuelle >= totalPages - 1);
            this.aPagePrecedente = !premierePage;
            this.aPageSuivante = !dernierePage;
            
            this.numeroPagePrecedente = aPagePrecedente ? pageActuelle - 1 : -1;
            this.numeroPageSuivante = aPageSuivante ? pageActuelle + 1 : -1;
        }
        
        // Getters et Setters
        public int getPageActuelle() { return pageActuelle; }
        public void setPageActuelle(int pageActuelle) { this.pageActuelle = pageActuelle; }
        
        public int getTaillePage() { return taillePage; }
        public void setTaillePage(int taillePage) { this.taillePage = taillePage; }
        
        public long getTotalElements() { return totalElements; }
        public void setTotalElements(long totalElements) { this.totalElements = totalElements; }
        
        public int getTotalPages() { return totalPages; }
        public void setTotalPages(int totalPages) { this.totalPages = totalPages; }
        
        public boolean isPremierePage() { return premierePage; }
        public void setPremierePage(boolean premierePage) { this.premierePage = premierePage; }
        
        public boolean isDernierePage() { return dernierePage; }
        public void setDernierePage(boolean dernierePage) { this.dernierePage = dernierePage; }
        
        public boolean isAPagePrecedente() { return aPagePrecedente; }
        public void setAPagePrecedente(boolean aPagePrecedente) { this.aPagePrecedente = aPagePrecedente; }
        
        public boolean isAPageSuivante() { return aPageSuivante; }
        public void setAPageSuivante(boolean aPageSuivante) { this.aPageSuivante = aPageSuivante; }
        
        public int getNumeroPagePrecedente() { return numeroPagePrecedente; }
        public void setNumeroPagePrecedente(int numeroPagePrecedente) { this.numeroPagePrecedente = numeroPagePrecedente; }
        
        public int getNumeroPageSuivante() { return numeroPageSuivante; }
        public void setNumeroPageSuivante(int numeroPageSuivante) { this.numeroPageSuivante = numeroPageSuivante; }
    }
    
    /**
     * Classe interne pour les informations de tri
     */
    public static class SortInfo {
        private String champTri;           // Champ sur lequel trier
        private String direction;          // ASC ou DESC
        private boolean estTrie;           // Les résultats sont-ils triés ?
        
        public SortInfo() {
            this.estTrie = false;
        }
        
        public SortInfo(String champTri, String direction) {
            this.champTri = champTri;
            this.direction = direction;
            this.estTrie = true;
        }
        
        // Getters et Setters
        public String getChampTri() { return champTri; }
        public void setChampTri(String champTri) { this.champTri = champTri; }
        
        public String getDirection() { return direction; }
        public void setDirection(String direction) { this.direction = direction; }
        
        public boolean isEstTrie() { return estTrie; }
        public void setEstTrie(boolean estTrie) { this.estTrie = estTrie; }
    }
    
    /**
     * Classe interne pour les statistiques sur les résultats
     */
    public static class StatistiquesResultats {
        private long dureeRequete;         // Durée de la requête en ms
        private int nombreFiltres;         // Nombre de filtres appliqués
        private boolean resultatsComplets; // Tous les résultats sont-ils inclus ?
        private String sourceRequete;      // Source des données (cache, db, etc.)
        
        public StatistiquesResultats() {
            this.resultatsComplets = true;
            this.sourceRequete = "database";
        }
        
        // Getters et Setters
        public long getDureeRequete() { return dureeRequete; }
        public void setDureeRequete(long dureeRequete) { this.dureeRequete = dureeRequete; }
        
        public int getNombreFiltres() { return nombreFiltres; }
        public void setNombreFiltres(int nombreFiltres) { this.nombreFiltres = nombreFiltres; }
        
        public boolean isResultatsComplets() { return resultatsComplets; }
        public void setResultatsComplets(boolean resultatsComplets) { this.resultatsComplets = resultatsComplets; }
        
        public String getSourceRequete() { return sourceRequete; }
        public void setSourceRequete(String sourceRequete) { this.sourceRequete = sourceRequete; }
    }
    
    // Constructeurs
    public PageableResponse() {
        super(true, "Résultats récupérés avec succès");
        this.filtres = new HashMap<>();
        this.tri = new SortInfo();
        this.statistiques = new StatistiquesResultats();
    }
    
    public PageableResponse(List<T> contenu, int pageActuelle, int taillePage, long totalElements) {
        this();
        this.contenu = contenu;
        this.pagination = new PaginationInfo(pageActuelle, taillePage, totalElements);
    }
    
    /**
     * Méthodes utilitaires
     */
    
    /**
     * Vérifie si la page contient des éléments
     */
    public boolean hasContenu() {
        return contenu != null && !contenu.isEmpty();
    }
    
    /**
     * Retourne le nombre d'éléments dans la page actuelle
     */
    public int getNombreElementsPage() {
        return contenu != null ? contenu.size() : 0;
    }
    
    /**
     * Ajoute un filtre
     */
    public void addFiltre(String nom, Object valeur) {
        if (this.filtres == null) {
            this.filtres = new HashMap<>();
        }
        this.filtres.put(nom, valeur);
        if (this.statistiques != null) {
            this.statistiques.setNombreFiltres(this.filtres.size());
        }
    }
    
    /**
     * Définit les informations de tri
     */
    public void setTriInfo(String champ, String direction) {
        if (this.tri == null) {
            this.tri = new SortInfo();
        }
        this.tri.setChampTri(champ);
        this.tri.setDirection(direction);
        this.tri.setEstTrie(true);
    }
    
    /**
     * Méthodes statiques pour créer des réponses paginées
     */
    
    /**
     * Crée une réponse paginée vide
     */
    public static <T> PageableResponse<T> empty() {
        PageableResponse<T> response = new PageableResponse<>();
        response.setPagination(new PaginationInfo(0, 0, 0));
        response.setMessage("Aucun résultat trouvé");
        return response;
    }
    
    /**
     * Crée une réponse paginée à partir d'une liste
     */
    public static <T> PageableResponse<T> of(List<T> contenu, int page, int taille, long total) {
        return new PageableResponse<>(contenu, page, taille, total);
    }
    
    /**
     * Crée une réponse paginée avec tri
     */
    public static <T> PageableResponse<T> of(List<T> contenu, int page, int taille, long total, 
                                            String champTri, String direction) {
        PageableResponse<T> response = new PageableResponse<>(contenu, page, taille, total);
        response.setTriInfo(champTri, direction);
        return response;
    }
    
    /**
     * Informations de navigation pour l'interface utilisateur
     */
    public Map<String, Object> getNavigationInfo() {
        Map<String, Object> nav = new HashMap<>();
        
        if (pagination != null) {
            nav.put("pageActuelle", pagination.getPageActuelle());
            nav.put("totalPages", pagination.getTotalPages());
            nav.put("totalElements", pagination.getTotalElements());
            nav.put("aPagePrecedente", pagination.isAPagePrecedente());
            nav.put("aPageSuivante", pagination.isAPageSuivante());
            nav.put("premierePage", pagination.isPremierePage());
            nav.put("dernierePage", pagination.isDernierePage());
        }
        
        return nav;
    }
    
    /**
     * Résumé textuel de la pagination
     */
    public String getResumePagination() {
        if (pagination == null || pagination.getTotalElements() == 0) {
            return "Aucun résultat";
        }
        
        int debut = pagination.getPageActuelle() * pagination.getTaillePage() + 1;
        int fin = Math.min(debut + pagination.getTaillePage() - 1, (int) pagination.getTotalElements());
        
        return String.format("Affichage de %d à %d sur %d résultats", 
                           debut, fin, pagination.getTotalElements());
    }
    
    // Getters et Setters
    public List<T> getContenu() {
        return contenu;
    }
    
    public void setContenu(List<T> contenu) {
        this.contenu = contenu;
    }
    
    public PaginationInfo getPagination() {
        return pagination;
    }
    
    public void setPagination(PaginationInfo pagination) {
        this.pagination = pagination;
    }
    
    public SortInfo getTri() {
        return tri;
    }
    
    public void setTri(SortInfo tri) {
        this.tri = tri;
    }
    
    public Map<String, Object> getFiltres() {
        return filtres;
    }
    
    public void setFiltres(Map<String, Object> filtres) {
        this.filtres = filtres;
    }
    
    public StatistiquesResultats getStatistiques() {
        return statistiques;
    }
    
    public void setStatistiques(StatistiquesResultats statistiques) {
        this.statistiques = statistiques;
    }
    
    @Override
    public String toString() {
        return "PageableResponse{" +
                "contenu=" + (contenu != null ? contenu.size() + " éléments" : "null") +
                ", pageActuelle=" + (pagination != null ? pagination.getPageActuelle() : "null") +
                ", totalElements=" + (pagination != null ? pagination.getTotalElements() : "null") +
                ", success=" + getSuccess() +
                '}';
    }
}