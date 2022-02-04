package com.mycompany.myapp.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.mycompany.myapp.domain.User.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Authority.class.getName());
            createCache(cm, com.mycompany.myapp.domain.User.class.getName() + ".authorities");
            createCache(cm, com.mycompany.myapp.domain.Jobs.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Countries.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Regions.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Cities.class.getName());
            createCache(cm, com.mycompany.myapp.domain.ZipCodes.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Addresses.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Phones.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Emails.class.getName());
            createCache(cm, com.mycompany.myapp.domain.PhoneTypes.class.getName());
            createCache(cm, com.mycompany.myapp.domain.ContactTypes.class.getName());
            createCache(cm, com.mycompany.myapp.domain.SocialKinds.class.getName());
            createCache(cm, com.mycompany.myapp.domain.SocialContacts.class.getName());
            createCache(cm, com.mycompany.myapp.domain.CompanyKinds.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Companies.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Companies.class.getName() + ".phones");
            createCache(cm, com.mycompany.myapp.domain.Companies.class.getName() + ".addresses");
            createCache(cm, com.mycompany.myapp.domain.Codes.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Teams.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Origins.class.getName());
            createCache(cm, com.mycompany.myapp.domain.CitizenFolders.class.getName());
            createCache(cm, com.mycompany.myapp.domain.MaritalStatus.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Citizens.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Citizens.class.getName() + ".phones");
            createCache(cm, com.mycompany.myapp.domain.Citizens.class.getName() + ".addresses");
            createCache(cm, com.mycompany.myapp.domain.Citizens.class.getName() + ".socials");
            createCache(cm, com.mycompany.myapp.domain.Citizens.class.getName() + ".emails");
            createCache(cm, com.mycompany.myapp.domain.Citizens.class.getName() + ".relations");
            createCache(cm, com.mycompany.myapp.domain.CitizensRelations.class.getName());
            createCache(cm, com.mycompany.myapp.domain.CitizensMeetings.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
