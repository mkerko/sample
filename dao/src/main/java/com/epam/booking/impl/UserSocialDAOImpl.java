package com.epam.booking.impl;
import com.epam.booking.IUserSocialDAO;
import com.epam.booking.entity.UserSocial;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserSocialDAOImpl implements IUserSocialDAO {
    private final SessionFactory factory;

    @Autowired
    public UserSocialDAOImpl(SessionFactory factory) {
        this.factory = factory;
    }

    private static final String HQL_GET_REFRESH_TOKEN = "FROM UserSocial user WHERE user.socialUserId= '";
    private static final String QUOTE = "'";

    @Override
    public void createSocialUser(UserSocial socialUser) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        if (socialUser != null) {
            session.save(socialUser);
            transaction.commit();
            session.close();
        }
    }

    @Override
    public String getRefreshToken(String userId) {
        String refreshToken;
        Session session = factory.openSession();
        String queryForSearch = HQL_GET_REFRESH_TOKEN + userId + QUOTE;
        Query query = session.createQuery(queryForSearch);
        UserSocial userSocial = (UserSocial) query.getSingleResult();
        refreshToken = userSocial.getRefreshToken();
        session.close();
        return refreshToken;
    }
}
