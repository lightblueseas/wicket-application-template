package application;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.Application;
import org.apache.wicket.IApplicationListener;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;

import de.alpharogroup.collections.ListExtensions;
import de.alpharogroup.wicket.base.application.plugins.ApplicationDebugSettingsPlugin;
import de.alpharogroup.wicket.base.util.application.ApplicationExtensions;
import de.alpharogroup.wicket.bootstrap3.application.WicketBootstrap3Application;
import net.ftlines.wicketsource.WicketSource;
import pages.area.publicly.home.HomePage;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 *
 * @see org.jaulp.wicket.prototype.Start#main(String[])
 */
public class WicketApplication extends WicketBootstrap3Application
{
	public static final int DEFAULT_HTTP_PORT = 19090;
	public static final int DEFAULT_HTTPS_PORT = 19443;
	/** The Constant logger. */
	private static final Logger LOGGER = Logger.getLogger(WicketApplication.class.getName());

	/**
	 * Gets the WicketApplication.
	 *
	 * @return the WicketApplication object.
	 */
	public static WicketApplication get()
	{
		return (WicketApplication)Application.get();
	}


	/**
	 * Gets the domain name.
	 *
	 * @return the domain name
	 */
	public String getDomainName()
	{
		return "jaulp-wicket-components.com";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return HomePage.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String[] newPackagesToScan()
	{
		final String[] packagesToScan = { "application" };
		return packagesToScan;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPackageToScan()
	{
		return ListExtensions.getFirst(newPackagesToScanAsList());
	}

	/**
	 * Factory callback method that returns the packages to scan as a {@link List} object.
	 *
	 * @return the {@link List} with the packages to scan
	 */
	protected List<String> newPackagesToScanAsList()
	{
		return Arrays.asList(newPackagesToScan());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getDefaultHttpPort()
	{
		return WicketApplication.DEFAULT_HTTP_PORT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getDefaultHttpsPort()
	{
		return WicketApplication.DEFAULT_HTTPS_PORT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Session newSession(final Request request, final Response response)
	{
		final WicketSession session = new WicketSession(request);
		session.bind();
		LOGGER.info("new session:" + session.getId());
		return session;
	}

	/**
	 * Called just before a the application configurations.
	 */
	@Override
	protected void onBeforeApplicationConfigurations()
	{
		super.onBeforeApplicationConfigurations();
	}

	@Override
	protected void onDeploymentModeSettings()
	{
		super.onDeploymentModeSettings();
	}

	@Override
	protected void onDevelopmentModeSettings()
	{
		super.onDevelopmentModeSettings();
		// Demonstration how to install the debug plugin...
		new ApplicationDebugSettingsPlugin()
		{
			/**
			 * The serialVersionUID
			 */
			private static final long serialVersionUID = 1L;

			/**
			 * {@inheritDoc}
			 */
			@Override
			protected void onConfigure(final WebApplication application)
			{
				super.onConfigure(application);
				// Adds the references from source code to the browser to reference in eclipse....
				WicketSource.configure(application);
			};
		}.install(this);

		// add an applicationListener...
		this.getApplicationListeners().add(new IApplicationListener()
		{
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void onAfterInitialized(final Application application)
			{
				LOGGER.info("Wicket application is initialized");
				// here can comes code that is needed after the application
				// initialization...
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void onBeforeDestroyed(final Application application)
			{
				LOGGER.info("Wicket application is destroyed");
				// here can comes code that is needed before the application
				// been destroyed...
			}
		});
		// strip wicket tags...
		this.getMarkupSettings().setStripWicketTags(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onGlobalSettings()
	{
		super.onGlobalSettings();
		ApplicationExtensions.setGlobalSettings(this, newHttpPort(), newHttpsPort(),
			FOOTER_FILTER_NAME, "UTF-8", "+*.css", "+*.png", "+*.woff2", "+*.js.map");
	}

}
