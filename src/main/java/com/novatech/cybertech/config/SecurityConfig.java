package com.novatech.cybertech.config;


import com.novatech.cybertech.config.jwt.filter.JwtAuthFilter;
import com.novatech.cybertech.services.implementation.CustomUserDetailsService;
import com.novatech.cybertech.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Indique que c'est une classe de configuration Spring
@EnableWebSecurity // Active la prise en charge de la sécurité Web par Spring Security
public class SecurityConfig {

    // Définit la liste des URL à autoriser sans sécurité
    private static final String[] PUBLIC_URLS = {
            "/auth/**",
            "/h2-console/**",       // Accès à la console H2 (Très important en développement !)
            "/api/public/**",       // Exemple: Tous les endpoints sous /api/public/
            "/auth/register",       // Exemple: Endpoint d'enregistrement
            "/auth/login",          // Exemple: Endpoint de connexion (si géré sans sécurité initiale)
            "/products/list",       // Exemple: Liste publique des produits
            "/swagger-ui/**",       // Accès à Swagger UI (si utilisé)
            "/v3/api-docs/**",      // Accès à la définition OpenAPI (si utilisé)
            "/api/v1/services/**"
            // Ajoute ici toutes les autres URL spécifiques que tu veux rendre publiques
            // Utilise les patterns Ant :
            // - * : correspond à un segment de chemin (ex: /users/*)
            // - ** : correspond à zéro ou plusieurs segments de chemin (ex: /admin/**)
    };

    @Bean // Définit un bean géré par Spring
    public SecurityFilterChain securityFilterChain(final HttpSecurity http, final UserRepository userRepository) throws Exception {
        http
                // 1. Désactiver CSRF (Cross-Site Request Forgery) - Souvent fait pour les API stateless
                // Si tu utilises des formulaires web traditionnels avec sessions, tu pourrais vouloir le garder activé
                // et configurer sa gestion correctement. Pour H2 console, c'est souvent nécessaire de le désactiver.
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Configurer les règles d'autorisation
                .authorizeHttpRequests(authorize -> authorize
                        // Permettre l'accès public aux URL définies dans PUBLIC_URLS
                        .requestMatchers(PUBLIC_URLS).permitAll()
                        // Exiger une authentification pour toutes les autres requêtes
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider(userRepository))
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);


        // 3. (Optionnel) Configurer le type d'authentification si nécessaire pour les autres routes
        // Par exemple, si tu utilises l'authentification par formulaire :
        // .formLogin(form -> form
        //     .loginPage("/login") // Page de login personnalisée (si tu en as une)
        //     .permitAll() // Permettre à tous d'accéder à la page de login
        // )
        // Ou si tu utilises l'authentification HTTP Basic :
        // .httpBasic(Customizer.withDefaults())

        // 4. (Optionnel) Désactiver les mécanismes par défaut si tu gères l'authentification autrement (ex: JWT)


        //.formLogin(AbstractHttpConfigurer::disable) // Désactive le formulaire de login par défaut
        //.httpBasic(AbstractHttpConfigurer::disable); // Désactive l'authentification HTTP Basic par défaut


        // Construire et retourner la chaîne de filtres de sécurité configurée
        return http.build();
    }

    // --- Autres Beans Optionnels ---

    // @Bean // Si tu n'utilises pas l'encodeur par défaut (BCrypt) ou si tu veux le configurer
    // public PasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }

    // @Bean // Si tu as besoin d'un AuthenticationManager personnalisé
    // public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    //     return authenticationConfiguration.getAuthenticationManager();
    // }


    @Bean
    public AuthenticationProvider authenticationProvider(final UserRepository userRepository) {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService(userRepository));
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter();
    }


    @Bean
    public UserDetailsService userDetailsService(final UserRepository userRepository) {
        return new CustomUserDetailsService(userRepository);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}