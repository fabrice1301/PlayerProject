package eafc.peruwelz.PlayerProject.modelControl;

import eafc.peruwelz.PlayerProject.domain.TGenre;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class GenreModelSelection extends TGenre {

        private final TGenre genre;

        private final BooleanProperty selectedGenre;

        public GenreModelSelection(TGenre genre, boolean selected) {
            this.genre = genre;
            this.selectedGenre = new SimpleBooleanProperty(selected);
        }

        public TGenre getGenre() {
            return genre;
        }

        public boolean isSelected() {
            return selectedGenre.get();
        }

        public BooleanProperty selectedProperty() {
            return selectedGenre;
        }

        public void setSelected(boolean selected) {
            this.selectedGenre.set(selected);
        }
}

