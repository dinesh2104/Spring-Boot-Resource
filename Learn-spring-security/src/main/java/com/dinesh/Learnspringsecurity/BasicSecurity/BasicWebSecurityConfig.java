package com.dinesh.Learnspringsecurity.BasicSecurity;

import static org.springframework.security.config.Customizer.withDefaults;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class BasicWebSecurityConfig {
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
		http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//		http.formLogin(withDefaults());
		http.httpBasic(withDefaults());
		http.csrf().disable();
		http.headers().frameOptions().sameOrigin();
		return http.build();
	}

//	@Bean
//	public UserDetailsService user() {
//		var user = User.withUsername("dinesh").password("{noop}dinesh").roles("USER").build();
//		var admin = User.withUsername("admin").password("{noop}admin").roles("USER", "ADMIN").build();
//		return new InMemoryUserDetailsManager(user, admin);
//	}

	@Bean
	public UserDetailsService user(DataSource dataSource) throws SQLException {
		var user = User.withUsername("dinesh").password("dinesh").passwordEncoder((str) -> passEncode().encode(str))
				.roles("USER").build();
		var admin = User.withUsername("admin").password("admin").passwordEncoder((str) -> passEncode().encode(str))
				.roles("USER", "ADMIN").build();
		// System.out.println("----------------------" + dataSource.getConnection() +
		// "---------------------");
		var jdbc = new JdbcUserDetailsManager(dataSource);
		jdbc.createUser(user);
		jdbc.createUser(admin);
		return jdbc;
	}

	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
				.addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION).build();
	}

	@Bean
	public BCryptPasswordEncoder passEncode() {
		return new BCryptPasswordEncoder();
	}

}
