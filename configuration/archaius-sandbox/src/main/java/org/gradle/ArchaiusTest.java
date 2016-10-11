package org.gradle;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import com.netflix.config.AbstractPollingScheduler;
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

	public String configuration() throws IOException {
		
		if (!ConfigurationManager.isConfigurationInstalled()) {
			AbstractPollingScheduler scheduler = new FixedDelayPollingScheduler(100, 1000, true);
			PolledConfigurationSource source = new URLConfigurationSource();
			DynamicConfiguration dynamicConfig = new DynamicConfiguration(source, scheduler);
			ConfigurationManager.install(dynamicConfig);
		}
		ConfigurationManager.getDeploymentContext().setDeploymentEnvironment(System.getenv("environment"));
		ConfigurationManager.loadCascadedPropertiesFromResources("archaius");
		DynamicStringProperty myprop = DynamicPropertyFactory.getInstance().getStringProperty("myprop", "NOT FOUND");
		return myprop.get();
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println(System.getenv("environment"));
		ArchaiusTest a = new ArchaiusTest();
		System.out.println(a.configuration());
		TimeUnit.SECONDS.sleep(10);
		System.out.println(a.configuration());
		System.out.println(new ArchaiusTest().configuration());
	}
}
