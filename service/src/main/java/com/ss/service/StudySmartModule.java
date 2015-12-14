package com.ss.service;

import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.ss.common.constants.Constants;
import com.ss.common.enums.SSEnvironment;
import com.ss.database.clients.RepositoryClient;
import com.ss.database.util.HibernateConnector;
import com.ss.service.annotations.Resource;
import com.ss.service.config.StudySmartConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.atteo.evo.classindex.ClassIndex;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.PropertyNamingStrategy;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Created by rahul on 12/2/15.
 */
public class StudySmartModule implements Module {


    @Override
    public void configure(Binder binder) {
        Iterable<Class<?>> classes = ClassIndex.getAnnotated(Resource.class);
        Iterator<Class<?>> classIterator = classes.iterator();
        while (classIterator.hasNext()) {
            binder.bind(classIterator.next());
        }

    }

    @Provides
    @Singleton
    public ObjectMapper getObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        mapper.configure(SerializationConfig.Feature.WRITE_ENUMS_USING_TO_STRING, true);
        mapper.configure(DeserializationConfig.Feature.READ_ENUMS_USING_TO_STRING, true);
        return mapper;
    }

    @Provides @Singleton
    public JacksonJsonProvider jacksonJsonProvider(ObjectMapper mapper) {
        return new JacksonJsonProvider(mapper);
    }

    @Provides @Singleton
    public StudySmartConfiguration getConfiguration() throws Exception {
        com.fasterxml.jackson.databind.ObjectMapper configMapper = new com.fasterxml.jackson.databind.ObjectMapper(new YAMLFactory());
        try {
            return configMapper.readValue(Thread.currentThread().getContextClassLoader().getResourceAsStream("study-smart.yml"), StudySmartConfiguration.class);
        } catch (IOException e) {
            throw new Exception("Check Configuration File");
        }
    }

    @Provides @Singleton
    public SSEnvironment getEnvironment(){
        try {
            String systemEnviroment = System.getProperty(Constants.ENVIRONMENT_VARIABLE);
            if (systemEnviroment == null || systemEnviroment.isEmpty()) {
                InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("environment.properties");
                PropertiesConfiguration propsConfig = new PropertiesConfiguration();
                propsConfig.load(stream);
                systemEnviroment = propsConfig.getProperty(Constants.ENVIRONMENT_VARIABLE).toString();
            }
            return SSEnvironment.valueOf(systemEnviroment.toUpperCase());
        } catch (Exception e) {
            return SSEnvironment.LOCAL;
        }
    }

    @Singleton @Provides
    public HibernateConnector getHibernateConnector(SSEnvironment environment){
        return new HibernateConnector(environment);
    }

    @Singleton @Provides
    public RepositoryClient getRepositoryClient(HibernateConnector hibernateConnector){
        return new RepositoryClient(hibernateConnector);
    }

}
