package eafc.peruwelz.playerproject.command;

import org.springframework.stereotype.Component;

/**
 * Classe représentant une télécommande qui peut exécuter des commandes.
 * Elle permet de définir une commande à exécuter et de l'exécuter.
 */
@Component
public class RemoteControl {

    /** La commande actuelle à exécuter */
    private Command command;

    /**
     * Définit la commande à exécuter par la télécommande.
     *
     * @param command La commande à exécuter.
     * @return L'objet RemoteControl, pour permettre le chaînage des méthodes.
     */
    public RemoteControl setCommand(Command command) {
        this.command = command;
        return this; // Permet de chaîner les appels de méthode
    }

    /**
     * Exécute la commande actuellement définie.
     *
     * @return Le résultat de l'exécution de la commande.
     */
    public Object execute() {
        return command.execute(); // Exécute la commande et retourne son résultat
    }
}
