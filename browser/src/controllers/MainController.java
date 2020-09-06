package controllers;

import db.DBController;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.History;
import models.Search;
import org.controlsfx.control.textfield.TextFields;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.ListUtils;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

public class MainController {
    @FXML
    public WebView mainWebView;

    @FXML
    public TextField searchTextField;

    @FXML
    public Button searchBtn;

    @FXML
    public MenuItem settingsMenuItem;

    @FXML
    public Button newTab;

    @FXML
    public TabPane openedTabsPane;

    @FXML
    public Tab initTab;

    @FXML
    public MenuItem weatherMenuItem;

    @FXML
    private void initialize() {
        // load DB session
        final Session session = HibernateInit.getSession();


        // load initial page
        WebEngine engine = mainWebView.getEngine();
        engine.load("http://yandex.ru/");

        engine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                ObservableList<WebHistory.Entry> history = engine.getHistory().getEntries();
                System.out.println(history.get(history.size() - 1));

                // save to db
                Transaction transaction = null;
                try {
                    transaction = session.beginTransaction();

                    History newHistory = new History();

                    WebHistory.Entry historyObject = history.get(history.size() - 1);

                    newHistory.setUrl(historyObject.getUrl());
                    newHistory.setDatetime(new Timestamp(new Date().getTime()));

                    if (historyObject.getTitle().length() > 20) {
                        initTab.setText(historyObject.getTitle().substring(0, 20) + "..");
                    } else {
                        initTab.setText(historyObject.getTitle());
                    }

                    session.clear();
                    session.save(newHistory);
                    transaction.commit();
                } catch (HibernateException e) {
                    transaction.rollback();
                    e.printStackTrace();
                }

            }
        });

        // set autocomplete to search field
        searchTextField.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                List<Search> searches = DBController.loadAllData(Search.class, session);
                // unpack data
                List<String> searchesList = new ArrayList<>();
                for (Search search : searches) {
                    searchesList.add(search.getValue());
                }
                Set<String> searchSet = new TreeSet<>(searchesList);
                TextFields.bindAutoCompletion(searchTextField, searchSet);
            }
        });


        // creating a new tab
        newTab.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // creating a layout
                StackPane newTabLayout = new StackPane();
                // creating WebView
                WebView newTabWebView = new WebView();
                newTabWebView.setPrefHeight(1200);
                WebEngine newTabEngine = newTabWebView.getEngine();
                newTabEngine.load("http://yandex.ru/");

                newTabLayout.getChildren().add(newTabWebView);

                Tab newTab = new Tab("New Tab", newTabLayout);
                openedTabsPane.getTabs().add(newTab);

                newTab.getTabPane().setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
                newTab.setClosable(true);

                newTabEngine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
                    if (newState == Worker.State.SUCCEEDED) {
                        ObservableList<WebHistory.Entry> history = newTabEngine.getHistory().getEntries();
                        System.out.println(history.get(history.size() - 1));

                        // save to db
                        Transaction transaction = null;
                        try {
                            transaction = session.beginTransaction();

                            History newHistory = new History();

                            WebHistory.Entry historyObject = history.get(history.size() - 1);

                            newHistory.setUrl(historyObject.getUrl());
                            newHistory.setDatetime(new Timestamp(new Date().getTime()));

                            if (historyObject.getTitle().length() > 10) {
                                newTab.setText(historyObject.getTitle().substring(0, 10) + "..");
                            } else {
                                newTab.setText(historyObject.getTitle());
                            }

                            session.clear();
                            session.save(newHistory);
                            transaction.commit();
                        } catch (HibernateException e) {
                            transaction.rollback();
                            e.printStackTrace();
                        }

                    }
                });

            }
        });

        // open settings
        settingsMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showDialog("/fxml/settings.fxml");


            }
        });


        // ACTIONS
        // weather
        weatherMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showDialog("/fxml/weather.fxml");
            }
        });


        // search
        searchBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                if (searchTextField.getText().equals("")) {
                    return;
                }
                // get current tab
                StackPane layout = (StackPane) openedTabsPane.getSelectionModel().getSelectedItem().getContent();
                WebView webView = (WebView) layout.getChildren().get(0);

                webView.getEngine().load("https://yandex.ru/search/?text=" + searchTextField.getText());

                // save to db
                Transaction transaction = null;
                try {
                    transaction = session.beginTransaction();

                    Search newSearch = new Search();
                    newSearch.setValue(searchTextField.getText());

                    History newHistory = new History();
                    newHistory.setUrl("https://yandex.ru/search/?text=" + searchTextField.getText());
                    newHistory.setDatetime(new Timestamp(new Date().getTime()));

                    session.clear();
                    session.save(newSearch);
                    session.save(newHistory);
                    transaction.commit();

                    searchTextField.setText("");
                } catch (HibernateException e) {
                    transaction.rollback();
                    e.printStackTrace();
                }

                // set autocomplete with new data
                List<Search> searches = DBController.loadAllData(Search.class, session);
                // unpack data
                List<String> searchesList = new ArrayList<>();
                for (Search search : searches) {
                    searchesList.add(search.getValue());
                }
                Set<String> searchSet = new TreeSet<>(searchesList);
                TextFields.bindAutoCompletion(searchTextField, searchSet);
            }

        });
    }

    private void showDialog(String fxml) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);

        try {
            Parent settingsDialog = FXMLLoader.load(getClass().getResource(fxml));
            dialogStage.setScene(new Scene(settingsDialog));
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
