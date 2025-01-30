package eafc.peruwelz.playerproject.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan("eafc.peruwelz.PlayerProject.domain")
@EnableJpaRepositories("eafc.peruwelz.PlayerProject.repos")
@ComponentScan(basePackages = "eafc.peruwelz.PlayerProject.service")
@EnableTransactionManagement
public class DomainConfig {
}
