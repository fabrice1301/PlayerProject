package eafc.peruwelz.playerproject.command;

/**
 * Interface représentant une commande générique pouvant être exécutée et retournant un résultat.
 * @param <R> Le type de résultat retourné par l'exécution de la commande.
 */
public interface Command<R> {

    /**
     * Exécute la commande et retourne un résultat.
     * @return Le résultat de l'exécution de la commande.
     */
    R execute();
}
