package com.vicangel.e_commerce_auction_server_system.infrastructure.importdata;

import javax.sql.DataSource;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Re-creates the database schema
 *
 * @apiNote if the application started like {@code ./gradlew bootRun --args='--data.refresh.start=true'}, then this component is executed
 */
@Component
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(name = "data.refresh.start", havingValue = "true")
public class DatabaseDataLoader implements ApplicationRunner {

  private final DataSource dataSource;

  @Override
  public void run(ApplicationArguments args) {
    log.info("DatabaseDataLoader started...");

    final var populator = new ResourceDatabasePopulator(new ClassPathResource("truncate-drop.sql"),
                                                        new ClassPathResource("schema.sql"),
                                                        new ClassPathResource("import.sql"));
    populator.execute(dataSource);

    log.info("DatabaseDataLoader finished...");
  }
}
