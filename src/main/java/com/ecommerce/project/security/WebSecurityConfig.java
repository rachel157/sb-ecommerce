package com.ecommerce.project.security;

import com.ecommerce.project.model.*;
import com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.repositories.ProductRepository;
import com.ecommerce.project.repositories.RoleRepository;
import com.ecommerce.project.repositories.UserRepository;
import com.ecommerce.project.security.jwt.AuthEntryPointJwt;
import com.ecommerce.project.security.jwt.AuthTokenFilter;
import com.ecommerce.project.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Set;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;


    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationProvider authenticationProvider) throws Exception {
        http.csrf(csrf->csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(
                    session ->
                            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                .requestMatchers("/h2-console/**").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/api/public/**").permitAll()
                                .requestMatchers("/api/admin/**").permitAll()
                                .requestMatchers(("/api/test/**")).permitAll()
                                .anyRequest().authenticated());

        http.authenticationProvider(authenticationProvider);
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.headers((headers) -> headers.frameOptions(
                HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        return http.build();

    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return(web -> web.ignoring().requestMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resource/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**"));
    }

    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, CategoryRepository categoryRepository, ProductRepository productRepository) {
        return args -> {
            Category c1 = new Category("Laptop");
            categoryRepository.save(c1);
            Category c2 = new Category("Camera");
            categoryRepository.save(c2);
            Category c3 = new Category("Other devices");
            categoryRepository.save(c3);

            Product p1 = new Product("MacBook Pro M2", "macbook_pro_m2.jpg", "Apple MacBook Pro with M2 chip", 20, 1999.99, 5.0, 1899.99, c1);
            Product p2 = new Product("Dell XPS 13", "dell_xps_13.jpg", "Compact and powerful Dell ultrabook", 15, 1499.99, 10.0, 1349.99, c1);
            Product p3 = new Product("HP Spectre x360", "hp_spectre_x360.jpg", "Convertible 2-in-1 laptop from HP", 25, 1399.99, 8.0, 1287.99, c1);
            Product p4 = new Product("Lenovo ThinkPad X1 Carbon", "thinkpad_x1_carbon.jpg", "Durable and reliable business laptop", 30, 1599.99, 7.5, 1479.99, c1);
            Product p5 = new Product("Asus ROG Zephyrus G14", "rog_zephyrus_g14.jpg", "High-performance gaming laptop from Asus", 10, 1799.99, 6.0, 1691.99, c1);
            Product p6 = new Product("Acer Swift 3", "acer_swift_3.jpg", "Lightweight laptop with great battery life", 40, 899.99, 12.0, 791.99, c1);
            Product p7 = new Product("Microsoft Surface Laptop 5", "surface_laptop_5.jpg", "Sleek design with excellent touch display", 18, 1299.99, 10.0, 1169.99, c1);
            Product p8 = new Product("MSI Stealth 15M", "msi_stealth_15m.jpg", "Slim gaming laptop with powerful GPU", 12, 1499.99, 9.0, 1364.99, c1);
            Product p9 = new Product("Gigabyte Aero 16", "gigabyte_aero_16.jpg", "Creator laptop with OLED display", 8, 1999.99, 7.0, 1859.99, c1);
            Product p10 = new Product("Razer Blade 14", "razer_blade_14.jpg", "Premium compact gaming laptop", 10, 2199.99, 5.0, 2089.99, c1);

            productRepository.save(p1);
            productRepository.save(p2);
            productRepository.save(p3);
            productRepository.save(p4);
            productRepository.save(p5);
            productRepository.save(p6);
            productRepository.save(p7);
            productRepository.save(p8);
            productRepository.save(p9);
            productRepository.save(p10);

            Product p11 = new Product("Canon EOS R6", "canon_eos_r6.jpg", "Full-frame mirrorless camera with 20MP sensor", 12, 2499.99, 10.0, 2249.99, c2);
            Product p12 = new Product("Sony Alpha a7 IV", "sony_a7_iv.jpg", "Versatile mirrorless camera for hybrid shooting", 10, 2699.99, 8.0, 2483.99, c2);
            Product p13 = new Product("Nikon Z6 II", "nikon_z6_ii.jpg", "Advanced mirrorless camera for video and photo", 14, 1999.99, 9.0, 1819.99, c2);
            Product p14 = new Product("Fujifilm X-T5", "fujifilm_xt5.jpg", "APS-C mirrorless with retro design", 16, 1699.99, 7.0, 1580.99, c2);
            Product p15 = new Product("Panasonic Lumix GH6", "lumix_gh6.jpg", "Micro Four Thirds camera for video creators", 8, 2199.99, 10.0, 1979.99, c2);
            Product p16 = new Product("Olympus OM-D E-M1 Mark III", "olympus_em1_mk3.jpg", "Lightweight pro camera with 5-axis stabilization", 9, 1799.99, 11.0, 1601.99, c2);
            Product p17 = new Product("GoPro HERO12 Black", "gopro_hero12.jpg", "Action camera with 5.3K video", 25, 449.99, 15.0, 382.49, c2);
            Product p18 = new Product("DJI Osmo Action 4", "dji_osmo_action_4.jpg", "Rugged action camera with dual screens", 20, 399.99, 10.0, 359.99, c2);
            Product p19 = new Product("Canon EOS M50 Mark II", "canon_m50_mk2.jpg", "Compact mirrorless for content creators", 18, 699.99, 12.0, 615.99, c2);
            Product p20 = new Product("Sony ZV-E10", "sony_zv_e10.jpg", "Vlogging camera with interchangeable lens", 22, 799.99, 10.0, 719.99, c2);

            productRepository.save(p11);
            productRepository.save(p12);
            productRepository.save(p13);
            productRepository.save(p14);
            productRepository.save(p15);
            productRepository.save(p16);
            productRepository.save(p17);
            productRepository.save(p18);
            productRepository.save(p19);
            productRepository.save(p20);

            Product p21 = new Product("Apple Watch Series 9", "apple_watch_9.jpg", "Smartwatch with advanced health tracking", 30, 499.99, 10.0, 449.99, c3);
            Product p22 = new Product("Amazon Echo Dot (5th Gen)", "echo_dot_5.jpg", "Smart speaker with Alexa voice assistant", 50, 59.99, 20.0, 47.99, c3);
            Product p23 = new Product("Google Nest Hub", "google_nest_hub.jpg", "Smart display for your smart home", 35, 99.99, 15.0, 84.99, c3);
            Product p24 = new Product("Samsung Galaxy Watch 6", "galaxy_watch_6.jpg", "Wearable with fitness and sleep tracking", 25, 399.99, 12.0, 351.99, c3);
            Product p25 = new Product("Oculus Quest 2", "oculus_quest_2.jpg", "All-in-one VR headset for gaming and more", 20, 299.99, 10.0, 269.99, c3);
            Product p26 = new Product("Kindle Paperwhite", "kindle_paperwhite.jpg", "E-reader with high-resolution display", 40, 139.99, 15.0, 118.99, c3);
            Product p27 = new Product("TP-Link Deco X60", "tplink_deco_x60.jpg", "Wi-Fi 6 Mesh System for whole-home coverage", 15, 249.99, 10.0, 224.99, c3);
            Product p28 = new Product("Anker PowerCore 10000", "anker_powercore_10000.jpg", "Portable power bank for phones and tablets", 60, 49.99, 20.0, 39.99, c3);
            Product p29 = new Product("Elgato Stream Deck", "elgato_stream_deck.jpg", "Customizable control pad for streamers", 12, 149.99, 10.0, 134.99, c3);
            Product p30 = new Product("Tile Pro Bluetooth Tracker", "tile_pro.jpg", "Item locator with extended range", 45, 39.99, 15.0, 33.99, c3);

            productRepository.save(p21);
            productRepository.save(p22);
            productRepository.save(p23);
            productRepository.save(p24);
            productRepository.save(p25);
            productRepository.save(p26);
            productRepository.save(p27);
            productRepository.save(p28);
            productRepository.save(p29);
            productRepository.save(p30);


            // Retrieve or create roles
            Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseGet(() -> {
                        Role newUserRole = new Role(AppRole.ROLE_USER);
                        return roleRepository.save(newUserRole);
                    });

            Role sellerRole = roleRepository.findByRoleName(AppRole.ROLE_SELLER)
                    .orElseGet(() -> {
                        Role newSellerRole = new Role(AppRole.ROLE_SELLER);
                        return roleRepository.save(newSellerRole);
                    });

            Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                    .orElseGet(() -> {
                        Role newAdminRole = new Role(AppRole.ROLE_ADMIN);
                        return roleRepository.save(newAdminRole);
                    });

            Set<Role> userRoles = Set.of(userRole);
            Set<Role> sellerRoles = Set.of(sellerRole);
            Set<Role> adminRoles = Set.of(userRole, sellerRole, adminRole);


            // Create users if not already present
            if (!userRepository.existsByUserName("user1")) {
                User user1 = new User("user1", "user1@example.com", passwordEncoder.encode("password1"));
                userRepository.save(user1);
            }

            if (!userRepository.existsByUserName("seller1")) {
                User seller1 = new User("seller1", "seller1@example.com", passwordEncoder.encode("password2"));
                userRepository.save(seller1);
            }

            if (!userRepository.existsByUserName("admin")) {
                User admin = new User("admin", "admin@example.com", passwordEncoder.encode("adminPass"));
                userRepository.save(admin);
            }

            // Update roles for existing users
            userRepository.findByUserName("user1").ifPresent(user -> {
                user.setRoles(userRoles);
                userRepository.save(user);
            });

            userRepository.findByUserName("seller1").ifPresent(seller -> {
                seller.setRoles(sellerRoles);
                userRepository.save(seller);
            });

            userRepository.findByUserName("admin").ifPresent(admin -> {
                admin.setRoles(adminRoles);
                userRepository.save(admin);
            });
        };
    }


}
