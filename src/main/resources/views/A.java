package views;waitingTrackListView.setContextMenu(null);
waitingTrackListView.setCellFactory(param -> {
    ListCell<TTrack> cell = new ListCell<>() {
        @Override
        protected void updateItem(TTrack track, boolean empty) {
            super.updateItem(track, empty);

            if (empty || track == null) {
                setText(null);
                setOnMouseClicked(null); // Nettoyer l'événement pour les cellules vides
            } else {
                setText(track.getTrackTitle());

                // Redéfinir l'événement de clic
                setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        if (event.getClickCount() == 1) {
                            System.out.println("1 clic");
                            selectWaitingTrackList();
                        } else if (event.getClickCount() == 2) {
                            System.out.println("2 clic");
                            updateListViewStyle();
                            loadWaitingTrackList();

                            // Réassigner l'événement après le double-clic pour éviter la perte
                            resetMouseEvents();
                        }
                    } else if (event.getButton() == MouseButton.SECONDARY) {
                        if (!isEmpty()) {
                            WaitingContextMenu.show(this, event.getScreenX(), event.getScreenY());
                        } else {
                            WaitingContextMenu.hide();
                            waitingTrackListView.getSelectionModel().clearSelection();
                        }
                    }
                });
            }
        }

        // Méthode pour réinitialiser les événements de clic
        private void resetMouseEvents() {
            setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (event.getClickCount() == 1) {
                        System.out.println("1 clic");
                        selectWaitingTrackList();
                    } else if (event.getClickCount() == 2) {
                        System.out.println("2 clic");
                        updateListViewStyle();
                        loadWaitingTrackList();
                    }
                } else if (event.getButton() == MouseButton.SECONDARY) {
                    if (!isEmpty()) {
                        WaitingContextMenu.show(this, event.getScreenX(), event.getScreenY());
                    } else {
                        WaitingContextMenu.hide();
                        waitingTrackListView.getSelectionModel().clearSelection();
                    }
                }
            });
        }
    };

    return cell;
});
