package controllers;

import db.DBController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import models.Bookmark;
import models.History;
import models.Search;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class SettingsController {
    public Button searchesBtn;

    public Button bookmarksBtn;

    public Button historyBtn;

    public ListView dataList;

    @FXML
    private void initialize() {
        // init DB session
        final Session session = HibernateInit.getSession();

        // searches
       searchesBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent mouseEvent) {
               // clearing list
               dataList.getItems().clear();

               // get data from db
               List<Search> searches = DBController.loadAllData(Search.class, session);

               // unpack data from objects
               List<String> searchesList = new ArrayList<>();

               for (Search search: searches) {
                   searchesList.add(search.getValue());
               }

               dataList.getItems().addAll(searchesList);
           }
       });

        // history
        historyBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // clearing list
                dataList.getItems().clear();

                // get data from db
                List<History> histories = DBController.loadAllData(History.class, session);

                // unpack data from objects
                List<String> historiesList = new ArrayList<>();

                for (History history: histories) {
                    historiesList.add(history.getTitle());
                }

                dataList.getItems().addAll(historiesList);
            }
        });

        // bookmarks
        bookmarksBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // clearing list
                dataList.getItems().clear();

                // get data from db
                List<Bookmark> bookmarks = DBController.loadAllData(Bookmark.class, session);

                // unpack data from objects
                List<String> bookmarksList = new ArrayList<>();

                for (Bookmark bookmark: bookmarks) {
                    bookmarksList.add(bookmark.getTitle());
                }

                dataList.getItems().addAll(bookmarksList);
            }
        });
    }
}
