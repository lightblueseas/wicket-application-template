package application;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.WebPage;

import pages.HomePage;
import de.alpharogroup.wicket.bootstrap3.application.WicketBootstrap3Application;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 *
 * @see org.jaulp.wicket.prototype.Start#main(String[])
 */
public class WicketApplication extends WicketBootstrap3Application
{
	public static final int DEFAULT_HTTP_PORT = 9090;
	public static final int DEFAULT_HTTPS_PORT = 9443;
	/** The Constant logger. */
	protected static final Logger LOGGER = Logger.getLogger(WicketApplication.class.getName());

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return HomePage.class;
	}

	@Override
	public String getPackageToScan()
	{
		// TODO include in properties to overwrite ...
		return "pages";
	}

}
