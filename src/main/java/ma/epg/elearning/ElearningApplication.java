package ma.epg.elearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ElearningApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ElearningApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ElearningApplication.class, args);
        //System.out.println(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("etudiant123"));
       // System.out.println(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("prof123"));
        //System.out.println(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("admin123"));

    }
}
