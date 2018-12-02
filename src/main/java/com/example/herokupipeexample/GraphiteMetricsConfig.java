package com.example.herokupipeexample;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

@Configuration
public class GraphiteMetricsConfig {

    @Bean
    public MetricRegistry getRegistry() {
        return new MetricRegistry();
    }

    @Bean
    public GraphiteReporter getReporter(MetricRegistry registry) {

        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(System.getenv("GRAPHITE_HOST"));
        System.out.println(System.getenv("HOSTEDGRAPHITE_APIKEY"));
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------");

        Graphite graphite = new Graphite(new InetSocketAddress("GRAPHITE_HOST", 2003));
        GraphiteReporter reporter = GraphiteReporter.forRegistry(registry).prefixedWith(System.getenv("HOSTEDGRAPHITE_APIKEY"))
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .filter(MetricFilter.ALL)
                .build(graphite);

        reporter.start(1, TimeUnit.SECONDS);
        System.out.println("_________________________________________________________________:)))");
        return reporter;
    }
}