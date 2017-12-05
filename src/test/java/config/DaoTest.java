package config;

import com.epam.evernote.JdbcTemplatePersonDao;
import com.epam.evernote.PersonDao;
import com.epam.evernote.PersonService;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

public class DaoTest {

    @Bean
    public PersonService personService() {
        return new PersonService();
    }

    @Bean
    public PersonDao jdbcPersonDao() {
        return new JdbcTemplatePersonDao();
    }

    @Bean
    public DataSource h2DataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("createPersonTable.sql") //script to create person table
                .build();
    }
}
