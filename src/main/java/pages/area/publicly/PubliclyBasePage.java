/**
 * Copyright (C) 2010 Asterios Raptis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pages.area.publicly;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.dropdown.DropDownButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.dropdown.MenuBookmarkablePageLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.dropdown.MenuDivider;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.dropdown.MenuHeader;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.GlyphIconType;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarComponents;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarDropDownButton;
import de.agilecoders.wicket.core.settings.IBootstrapSettings;
import de.agilecoders.wicket.core.settings.ITheme;
import de.alpharogroup.wicket.base.util.resource.ResourceModelFactory;
import pages.area.publicly.home.HomePage;
import pages.base.ApplicationBasePage;

/**
 * The class {@link PubliclyBasePage}.
 *
 * @author Asterios Raptis
 * @param <T>
 *            the generic type of the page model
 */
public abstract class PubliclyBasePage<T> extends ApplicationBasePage<T>
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The logger constant. */
	protected static final Logger LOG = Logger.getLogger(PubliclyBasePage.class.getName());

	/**
	 * Default constructor that instantiates a new {@link PubliclyBasePage}.
	 */
	public PubliclyBasePage()
	{
		this(new PageParameters());
	}

	/**
	 * Instantiates a new {@link PubliclyBasePage} with the given model.
	 *
	 * @param model the model
	 */
	public PubliclyBasePage(final IModel<T> model)
	{
		super(model);
	}

	/**
	 * Instantiates a new {@link PubliclyBasePage} with the given {@link PageParameters} object.
	 *
	 * @param parameters
	 *            the {@link PageParameters} object
	 */
	public PubliclyBasePage(final PageParameters parameters)
	{
		super(parameters);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onInitialize()
	{
		super.onInitialize();
		add(newNavbarPanel(NAVBAR_PANEL_ID, getModel()));
		add(feedback = newFeedbackPanel(FEEDBACK_PANEL_ID, getModel()));
		add(newContainerPanel(CONTAINER_PANEL_ID, getModel()));
		add(newFooterPanel(FOOTER_PANEL_ID, getModel()));
	}

	/**
	 * Factory method for creating a new {@link Component} for the navigation menu. This method is invoked in the
	 * constructor from the derived classes and have to be overridden so users can provide their own
	 * version of a new {@link Component} for the navigation menu.
	 *
	 * @param id
	 *            the id
	 * @param model
	 *            the model
	 * @return the new {@link Component} for the navigation menu.
	 */
	protected Component newNavbarPanel(final String id, final IModel<T> model) {
		return newNavbar(id);
	}

	/**
	 * Factory method for creating a new {@link Navbar} instance.
	 *
	 * @param markupId
	 *            The components markup id.
	 * @return a new {@link Navbar} instance
	 */
	protected Navbar newNavbar(final String markupId)
	{
		final Navbar navbar = new Navbar(markupId);

        navbar.setPosition(Navbar.Position.TOP);
        navbar.setInverted(true);

		final IModel<String> brandNameModel = ResourceModelFactory.newResourceModel(
			"global.slogan.mainhead.label", this);
		final IModel<String> overviewModel = ResourceModelFactory.newResourceModel(
			"global.menu.overview.label", this);
		// show brand name
		navbar.setBrandName(brandNameModel);

		navbar.addComponents(NavbarComponents.transform(Navbar.ComponentPosition.LEFT,
			new NavbarButton<Void>(HomePage.class, overviewModel).setIconType(GlyphIconType.home),
			newThemesNavbarDropDownButton())
				);

		return navbar;
	}


	/**
	 * Factory method for creating a new dropdown navbar item for the bootstrap themes.
	 *
	 * @return the component
	 */
	protected DropDownButton newThemesNavbarDropDownButton()
	{
		final DropDownButton dropdown = new NavbarDropDownButton(Model.of("Themes"))
		{
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isActive(final Component item)
			{
				return false;
			}

			@Override
			protected List<AbstractLink> newSubMenuButtons(final String buttonMarkupId)
			{
				final List<AbstractLink> subMenu = new ArrayList<AbstractLink>();
				subMenu.add(new MenuHeader(Model.of("all available themes:")));
				subMenu.add(new MenuDivider());

				final IBootstrapSettings settings = Bootstrap.getSettings(getApplication());
				final List<ITheme> themes = settings.getThemeProvider().available();

				for (final ITheme theme : themes)
				{
					final PageParameters params = new PageParameters();
					params.set("theme", theme.name());

					subMenu.add(new MenuBookmarkablePageLink<Page>(getPageClass(), params, Model
						.of(theme.name())));
				}
				return subMenu;
			}
		}.setIconType(GlyphIconType.book);
		return dropdown;
	}
}
