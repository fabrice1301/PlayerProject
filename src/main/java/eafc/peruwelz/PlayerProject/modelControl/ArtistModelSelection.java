package eafc.peruwelz.PlayerProject.modelControl;

import eafc.peruwelz.PlayerProject.domain.TArtist;
import eafc.peruwelz.PlayerProject.domain.TGenre;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ArtistModelSelection extends TArtist {

    private final TArtist artist;

    private final BooleanProperty selectedArtist;

    public ArtistModelSelection(TArtist artist, boolean selected) {
        this.artist = artist;
        this.selectedArtist = new SimpleBooleanProperty(selected);
    }

    public TArtist getArtist() {
        return artist;
    }

    public boolean isSelected() {
        return selectedArtist.get();
    }

    public BooleanProperty selectedProperty() {
        return selectedArtist;
    }

    public void setSelected(boolean selected) {
        this.selectedArtist.set(selected);
    }
}
