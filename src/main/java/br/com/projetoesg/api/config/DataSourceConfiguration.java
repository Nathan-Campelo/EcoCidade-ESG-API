package br.com.projetoesg.api.config;

import com.zaxxer.hikari.HikariDataSource;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

@Configuration
public class DataSourceConfiguration {

    @Bean
    public HikariDataSource dataSource(Environment environment) {
        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setJdbcUrl(resolveDatasourceUrl(environment));
        dataSource.setUsername(resolveUsername(environment));
        dataSource.setPassword(resolvePassword(environment));

        String driverClassName = environment.getProperty("spring.datasource.driver-class-name");
        if (StringUtils.hasText(driverClassName)) {
            dataSource.setDriverClassName(driverClassName);
        }

        return dataSource;
    }

    private String resolveDatasourceUrl(Environment environment) {
        String directUrl = firstNonBlank(
                environment.getProperty("spring.datasource.url"),
                environment.getProperty("DB_URL"),
                environment.getProperty("JDBC_DATABASE_URL")
        );

        if (StringUtils.hasText(directUrl)) {
            return directUrl;
        }

        String renderUrl = firstNonBlank(
                environment.getProperty("DATABASE_URL"),
                environment.getProperty("RENDER_DATABASE_URL")
        );

        if (StringUtils.hasText(renderUrl)) {
            return toJdbcUrl(renderUrl);
        }

        String host = environment.getProperty("DB_HOST", "localhost");
        String port = environment.getProperty("DB_PORT", "5432");
        String databaseName = environment.getProperty("DB_NAME", "projeto_esg");

        return "jdbc:postgresql://" + host + ":" + port + "/" + databaseName;
    }

    private String resolveUsername(Environment environment) {
        String username = firstNonBlank(
                environment.getProperty("spring.datasource.username"),
                environment.getProperty("DB_USERNAME"),
                environment.getProperty("DB_USER")
        );

        if (StringUtils.hasText(username)) {
            return username;
        }

        String renderUrl = firstNonBlank(
                environment.getProperty("DATABASE_URL"),
                environment.getProperty("RENDER_DATABASE_URL")
        );

        if (StringUtils.hasText(renderUrl)) {
            return readUserInfoPart(renderUrl, 0, "postgres");
        }

        return "postgres";
    }

    private String resolvePassword(Environment environment) {
        String password = firstNonBlank(
                environment.getProperty("spring.datasource.password"),
                environment.getProperty("DB_PASSWORD"),
                environment.getProperty("DB_PASS")
        );

        if (StringUtils.hasText(password)) {
            return password;
        }

        String renderUrl = firstNonBlank(
                environment.getProperty("DATABASE_URL"),
                environment.getProperty("RENDER_DATABASE_URL")
        );

        if (StringUtils.hasText(renderUrl)) {
            return readUserInfoPart(renderUrl, 1, "postgres");
        }

        return "postgres";
    }

    private String toJdbcUrl(String url) {
        URI uri = parseUri(url);
        String host = uri.getHost();
        int port = uri.getPort() == -1 ? 5432 : uri.getPort();
        String path = uri.getPath() == null ? "" : uri.getPath();
        String query = uri.getQuery();
        String databaseName = path.startsWith("/") ? path.substring(1) : path;

        String jdbcUrl = "jdbc:postgresql://" + host + ":" + port + "/" + databaseName;

        if (StringUtils.hasText(query)) {
            jdbcUrl += "?" + query;
        }

        return jdbcUrl;
    }

    private String readUserInfoPart(String url, int index, String defaultValue) {
        URI uri = parseUri(url);
        String userInfo = uri.getUserInfo();

        if (!StringUtils.hasText(userInfo)) {
            return defaultValue;
        }

        String[] parts = userInfo.split(":", 2);
        if (parts.length <= index || !StringUtils.hasText(parts[index])) {
            return defaultValue;
        }

        return parts[index];
    }

    private URI parseUri(String url) {
        try {
            return new URI(url);
        } catch (URISyntaxException exception) {
            throw new IllegalStateException("Nao foi possivel interpretar a URL do banco.", exception);
        }
    }

    private String firstNonBlank(String... values) {
        for (String value : values) {
            if (StringUtils.hasText(value)) {
                return value;
            }
        }

        return null;
    }
}
