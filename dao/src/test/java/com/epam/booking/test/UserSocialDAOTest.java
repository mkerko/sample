package com.epam.booking.test;

import com.epam.booking.IUserSocialDAO;
import com.epam.booking.config.TestConfig;
import com.epam.booking.entity.UserSocial;
import com.epam.booking.util.ColumnSensingReplacementDataSetLoader;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class,
        loader=AnnotationConfigContextLoader.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class,
        TransactionalTestExecutionListener.class,  DirtiesContextTestExecutionListener.class})
@DatabaseSetup(type = DatabaseOperation.CLEAN_INSERT, value = "classpath:data/init-data.xml")
@DatabaseTearDown(value = "classpath:data/no-data.xml", type = DatabaseOperation.DELETE_ALL)
@DbUnitConfiguration(dataSetLoader = ColumnSensingReplacementDataSetLoader.class)
public class UserSocialDAOTest {
    @Autowired
    private IUserSocialDAO dao;

    @Test
    public void shouldInsertUserAndReturnRefreshToken() {
        UserSocial userSocial = new UserSocial("2467164871624873642",
                "241241");
        dao.createSocialUser(userSocial);
        String actualResult = dao.getRefreshToken("2467164871624873642");
        assertEquals("241241", actualResult);
    }

}
