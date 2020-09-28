package services;

import controllers.HibernateInit;
import models.History;
import models.Search;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Timestamp;
import java.util.Date;

public class HistoryService {

    public History save(Session session, String url, String title) {
        // save to db
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            History newHistory = new History();
            newHistory.setUrl(url);
            newHistory.setTitle(title);
            newHistory.setDatetime(new Timestamp(new Date().getTime()));

            session.clear();
            session.save(newHistory);
            transaction.commit();

            return newHistory;
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
            return null;
        }
    }

}
