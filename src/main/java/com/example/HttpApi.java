package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class HttpApi {
    @Value("${spring.datasource.url}")
    private String dbUrl;
    
    @Autowired
    private DataSource dataSource;
  
    @GetMapping("/")
    public String root() {
      // System.out.println("Accessing /");
      return "coucou";
    }

    @GetMapping("/titres")
    public List<String> titres (@RequestBody String userId) {
      ArrayList<String> L = new ArrayList<String>();
      L.add(userId);
      L.add("toto");
      L.add("titi");
      L.add("tata");
      return L;
    }

    @GetMapping("/user")
    public List<String> user (@RequestBody User u) {
      System.out.println(u);
      ArrayList<String> L = new ArrayList<String>();
      L.add(u.name);
      L.add(u.uid);
      L.add("" + u.age);
      return L;
    }

    @GetMapping("/db")
    String db(Map<String, Object> model) {
      try (Connection connection = dataSource.getConnection()) {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
        stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
        ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");
  
        ArrayList<String> output = new ArrayList<String>();
        while (rs.next()) {
          output.add("Read from DB: " + rs.getTimestamp("tick"));
        }
  
        model.put("records", output);
        return "db";
      } catch (Exception e) {
        model.put("message", e.getMessage());
        return "error";
      }
    }
  
    @Bean
    public DataSource dataSource() throws SQLException {
      if (dbUrl == null || dbUrl.isEmpty()) {
        return new HikariDataSource();
      } else {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        return new HikariDataSource(config);
      }
    }
  
}
