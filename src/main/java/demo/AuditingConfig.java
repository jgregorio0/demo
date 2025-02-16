package demo;

@Configuration
@EnableJpaAuditing
public class AuditingConfig {
    
    @Bean
    public AuditorAware<String> auditorProvider() {
        return new SpringSecurityAuditAwareImpl();
    }
}