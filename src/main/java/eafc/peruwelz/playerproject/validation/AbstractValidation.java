package eafc.peruwelz.playerproject.validation;

/**
 * Classe abstraite représentant une règle de validation.
 * Cette classe permet de définir des règles de validation communes pour les sous-classes spécifiques.
 */
public abstract class AbstractValidation {

    /** Le message associé à la règle de validation. */
    private String ruleMessage;

    /** Indique si la validation est obligatoire. */
    private boolean mandatory;

    /** L'expression régulière utilisée pour valider la valeur. */
    private String regExp;

    /**
     * Constructeur de la classe AbstractValidation.
     *
     * @param ruleMessage Le message à afficher en cas d'échec de la validation.
     * @param mandatory Indique si la règle est obligatoire (true) ou non (false).
     */
    public AbstractValidation(String ruleMessage, boolean mandatory) {
        setMandatory(mandatory);
        setRuleMessage(ruleMessage);
        setRegExp("^.*$"); // Expression régulière par défaut qui accepte toutes les valeurs.
    }

    /**
     * Méthode abstraite permettant de valider la valeur en fonction de la règle.
     * Cette méthode doit être implémentée par les classes concrètes pour définir leur propre logique de validation.
     *
     * @param value La valeur à valider.
     * @return true si la valeur est valide, false sinon.
     */
    public abstract boolean isValid(String value);

    /**
     * Récupère le message associé à la règle de validation.
     *
     * @return Le message de la règle de validation.
     */
    public String getRuleMessage() {
        return ruleMessage;
    }

    /**
     * Définit le message associé à la règle de validation.
     *
     * @param ruleMessage Le message à définir.
     */
    public void setRuleMessage(String ruleMessage) {
        this.ruleMessage = ruleMessage;
    }

    /**
     * Vérifie si la validation est obligatoire.
     *
     * @return true si la validation est obligatoire, false sinon.
     */
    public boolean isMandatory() {
        return mandatory;
    }

    /**
     * Définit si la validation est obligatoire.
     *
     * @param mandatory true si la validation est obligatoire, false sinon.
     */
    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    /**
     * Récupère l'expression régulière utilisée pour valider la valeur.
     *
     * @return L'expression régulière.
     */
    public String getRegExp() {
        return regExp;
    }

    /**
     * Définit l'expression régulière utilisée pour valider la valeur.
     *
     * @param regExp L'expression régulière à définir.
     */
    public void setRegExp(String regExp) {
        this.regExp = regExp;
    }
}
