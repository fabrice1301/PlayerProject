package eafc.peruwelz.playerproject.modelControl;

import eafc.peruwelz.playerproject.domain.TAlbum;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class AlbumModelSelection extends TAlbum {

        private final TAlbum album;

        private final BooleanProperty selectedAlbum;

        public AlbumModelSelection(TAlbum album, boolean selected) {
            this.album = album;
            this.selectedAlbum = new SimpleBooleanProperty(selected);
        }

        public TAlbum getAlbum() {
            return album;
        }

        public boolean isSelected() {
            return selectedAlbum.get();
        }

        public BooleanProperty selectedProperty() {
            return selectedAlbum;
        }

        public void setSelected(boolean selected) {
            this.selectedAlbum.set(selected);
        }
}

