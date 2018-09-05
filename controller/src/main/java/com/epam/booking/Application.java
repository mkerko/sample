package com.epam.booking;

import com.epam.booking.config.WebConfig;
import com.epam.booking.config.WebServletConfiguration;
import com.epam.booking.entity.Room;
import com.epam.booking.entity.RoomTypeEnum;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages={"com.epam.booking"})
@Import({WebConfig.class, WebServletConfiguration.class})
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
