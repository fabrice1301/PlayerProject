package eafc.peruwelz.playerproject.modelControl;

import eafc.peruwelz.playerproject.domain.TPlaylist;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class PlaylistModelSelection extends TPlaylist {

        private final TPlaylist playlist;


        private final BooleanProperty selectedPlaylist;

        public PlaylistModelSelection(TPlaylist genre, boolean selected) {
            this.playlist = genre;
            this.selectedPlaylist = new SimpleBooleanProperty(selected);
        }

        public TPlaylist getPlaylist() {
            return playlist;
        }

        public boolean isSelected() {
            return selectedPlaylist.get();
        }

        public BooleanProperty selectedProperty() {
            return selectedPlaylist;
        }

        public void setSelected(boolean selected) {
            this.selectedPlaylist.set(selected);
        }
}

