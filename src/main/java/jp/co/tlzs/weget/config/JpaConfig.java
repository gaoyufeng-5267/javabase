package jp.co.tlzs.weget.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableJpaAuditing
@EnableAsync
public class JpaConfig {

    private static final int corePoolSize = 4;
    private static final int queueCapacity = 25;
    private static final int maxPoolSize = 40;

    @Bean
    @ConditionalOnMissingBean
    ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(corePoolSize);   // corePoolSizeのThreadを最初に作る
        taskExecutor.setQueueCapacity(queueCapacity); // corePoolSizeが一杯になるとqueueCapacityまでキューイングする
        taskExecutor.setMaxPoolSize(maxPoolSize);   // queueCapacityを越えるとmaxPoolSizeを上限にThreadを増やす
        return taskExecutor;
    }

}
