package pages.area.publicly.home;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.wicketstuff.annotation.mount.MountPath;

import pages.area.publicly.PubliclyBasePage;

@MountPath("public/home")
public class HomePage extends PubliclyBasePage<HomeModelBean> {
	private static final long serialVersionUID = 1L;

	@Override
	public Component newContainerPanel(final String id, final IModel<HomeModelBean> model)
	{
		return new HomePanel(id);
	}
}
