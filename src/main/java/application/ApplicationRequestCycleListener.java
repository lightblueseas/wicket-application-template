package application;

import org.apache.wicket.request.component.IRequestablePage;

import de.alpharogroup.wicket.base.application.AbstractApplicationRequestCycleListener;
import pages.area.publicly.exception.ExceptionPage;

public class ApplicationRequestCycleListener extends AbstractApplicationRequestCycleListener
{
	private static final long serialVersionUID = 1L;

	@Override
	public IRequestablePage newExceptionPage(final Exception exception)
	{
		return new ExceptionPage(exception);
	}

}