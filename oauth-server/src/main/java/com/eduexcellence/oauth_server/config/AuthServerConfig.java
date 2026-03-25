package com.eduexcellence.oauth_server.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@SuppressWarnings("deprecation")
@Configuration
public class AuthServerConfig {
	
	@Bean
	public RegisteredClientRepository getClient() {
		RegisteredClient apigateway = RegisteredClient.withId(UUID.randomUUID().toString())
				       .clientId("client")
				       .clientSecret("secret")
				       .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				       .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				       .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				       .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				       .redirectUri("http://localhost:8080/students")
				       .scope(OidcScopes.OPENID)
				       .scope("Read")
				       .tokenSettings(TokenSettings.builder().refreshTokenTimeToLive(Duration.ofDays(1)).accessTokenTimeToLive(Duration.ofMinutes(120)).build())
				       .clientSettings(ClientSettings.builder().requireProofKey(false).build())
				       .build();
		return new InMemoryRegisteredClientRepository(apigateway);
	}
	
	@Bean
	public UserDetailsService userDetails() {
		UserDetails xavier = User.withUsername("Xavier")
				.password("123")
				.roles("Read", "Write")
				.build();
		return new InMemoryUserDetailsManager(xavier);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
	public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException{
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(2048);
		
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		RSAPublicKey rsaPublicKey = (RSAPublicKey)keyPair.getPublic();
		RSAPrivateKey rsaPrivateKey = (RSAPrivateKey)keyPair.getPrivate();
		
		RSAKey rsaKey = new RSAKey.Builder(rsaPublicKey)
				.privateKey(rsaPrivateKey)
				.keyID(UUID.randomUUID().toString())
				.build();
		
		return ((jwkSelector, securityContext) -> jwkSelector.select(new JWKSet(rsaKey)));		
	}
	
	@Bean
	public AuthorizationServerSettings authorizationServerSettings() {
		return AuthorizationServerSettings.builder().build();
	}
	
	@SuppressWarnings("removal")
	@Bean
	@Order(1)
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
		httpSecurity.csrf().disable();
		
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(httpSecurity);
		httpSecurity.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults());
		
		httpSecurity.exceptionHandling(e -> e.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")));
		
		return httpSecurity.build();
	}
	
	@Bean
	@Order(2)
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception{
		httpSecurity.formLogin(Customizer.withDefaults());
		
		httpSecurity.authorizeHttpRequests(c -> c.anyRequest().authenticated());
		
		return httpSecurity.build();
	}

}
