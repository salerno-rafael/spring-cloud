package org.gradle;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration.AbstractConfiguration;

import com.netflix.config.AbstractPollingScheduler;
import com.netflix.config.ConcurrentCompositeConfiguration;
import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicConfiguration;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.config.FixedDelayPollingScheduler;
import com.netflix.config.PolledConfigurationSource;
import com.netflix.config.sources.URLConfigurationSource;

public class ArchaiusTest {
	
	 static {
		    System.setProperty("archaius.configurationSource.defaultFileName", "archaius.properties");
		  }
	 
	 public String propertie ; 
	 
	public void test() throws IOException {
	
		AbstractPollingScheduler scheduler = new FixedDelayPollingScheduler(100, 1000, 	true);
		PolledConfigurationSource source = new URLConfigurationSource(); 
		DynamicConfiguration dynamicConfig = new DynamicConfiguration(source, scheduler);
	     ConfigurationManager.install(dynamicConfig);
	     
		ConfigurationManager.loadCascadedPropertiesFromResources("archaius");
		DynamicStringProperty myprop = DynamicPropertyFactory.getInstance().getStringProperty("myprop","NOT FOUND");
		
		System.out.println(myprop.get());
		propertie = myprop.get();

	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println(System.getenv("env"));
		ArchaiusTest a=	new ArchaiusTest();
		a.test();
		TimeUnit.SECONDS.sleep(10);
		System.out.println("dasds333333adasdsad");
		System.out.println(a.propertie);
	}
}
