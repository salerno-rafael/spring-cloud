package org.gradle;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.netflix.config.AbstractPollingScheduler;
import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicConfiguration;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.FixedDelayPollingScheduler;
import com.netflix.config.PolledConfigurationSource;
import com.netflix.config.sources.URLConfigurationSource;

public class ArchaiusSandBox {
	
	static {
		System.setProperty("archaius.configurationSource.defaultFileName", "archaius.properties");
	}

	public String configuration() throws IOException {
		installConfig();
		ConfigurationManager.getDeploymentContext().setDeploymentEnvironment(System.getenv("environment"));
		ConfigurationManager.loadCascadedPropertiesFromResources("archaius");
		return DynamicPropertyFactory.getInstance().getStringProperty("myprop", "NOT FOUND").get();
	}

	private void installConfig() {
		if (!ConfigurationManager.isConfigurationInstalled()) {
			AbstractPollingScheduler scheduler = new FixedDelayPollingScheduler(100, 1000, true);
			PolledConfigurationSource source = new URLConfigurationSource();
			DynamicConfiguration dynamicConfig = new DynamicConfiguration(source, scheduler);
			ConfigurationManager.install(dynamicConfig);
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println("Currrent Environment Variable = "+ System.getenv("environment"));
		ArchaiusSandBox arch = new ArchaiusSandBox();
		System.out.println(arch.configuration());
		TimeUnit.SECONDS.sleep(10);
		System.out.println(arch.configuration());
	}
}
