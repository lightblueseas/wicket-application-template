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
package pages.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.filter.HeaderResponseContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.settings.JavaScriptLibrarySettings;
import org.apache.wicket.util.string.StringValue;
import org.apache.wicket.util.time.Duration;
import org.odlabs.wiquery.core.javascript.JsUtils;

import application.WicketApplication;
import application.WicketSession;
import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.markup.html.bootstrap.behavior.BootstrapBaseBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.block.Code;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.core.markup.html.bootstrap.html.HtmlTag;
import de.agilecoders.wicket.core.markup.html.bootstrap.html.IeEdgeMetaTag;
import de.agilecoders.wicket.core.markup.html.bootstrap.html.OptimizedMobileViewportMetaTag;
import de.agilecoders.wicket.core.settings.IBootstrapSettings;
import de.alpharogroup.resourcebundle.locale.ResourceBundleKey;
import de.alpharogroup.wicket.base.GenericBasePage;
import de.alpharogroup.wicket.base.util.parameter.PageParametersExtensions;
import de.alpharogroup.wicket.base.util.resource.ResourceModelFactory;
import de.alpharogroup.wicket.behaviors.BuildableChainableStatement;
import de.alpharogroup.wicket.behaviors.FaviconBehavior;
import de.alpharogroup.wicket.behaviors.JavascriptAppenderBehavior;
import de.alpharogroup.wicket.behaviors.JqueryStatementsBehavior;
import de.alpharogroup.wicket.bootstrap3.application.WicketBootstrap3Application;
import de.alpharogroup.wicket.components.footer.FooterMenuPanel;
import de.alpharogroup.wicket.components.footer.FooterPanel;
import de.alpharogroup.wicket.components.i18n.list.LinkListPanel;
import de.alpharogroup.wicket.components.link.DefaultTargets;
import de.alpharogroup.wicket.components.link.LinkItem;
import de.alpharogroup.wicket.header.contributors.HeaderResponseExtensions;
import de.alpharogroup.wicket.js.addon.sessiontimeout.SessionTimeoutJsGenerator;
import de.alpharogroup.wicket.js.addon.sessiontimeout.SessionTimeoutSettings;
import lombok.Getter;

/**
 * The Class ApplicationBasePage.
 *
 * @param <T>
 *            the generic type of the model
 */
public abstract class ApplicationBasePage<T> extends GenericBasePage<T>
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant COPYRIGHT_URL. */
	protected static final String COPYRIGHT_URL = "TODO-set-copyright-url.com";

	/** The Constant NAVBAR_PANEL_ID. */
	protected static final String NAVBAR_PANEL_ID = "navbar";

	/** The Constant CONTAINER_PANEL_ID. */
	protected static final String CONTAINER_PANEL_ID = "container";

	/** The Constant FOOTER_PANEL_ID. */
	protected static final String FOOTER_PANEL_ID = "footer";

	/** The Constant FEEDBACK_PANEL_ID. */
	protected static final String FEEDBACK_PANEL_ID = "feedback";

	/** The feedback. */
	@Getter
	protected FeedbackPanel feedback;

	/**
	 * Default constructor that instantiates a new {@link ApplicationBasePage}.
	 */
	public ApplicationBasePage()
	{
		this(new PageParameters());
	}

	/**
	 * Instantiates a new {@link ApplicationBasePage} with the given model.
	 *
	 * @param model
	 *            the model
	 */
	public ApplicationBasePage(final IModel<T> model)
	{
		super(model);
	}

	/**
	 * Instantiates a new {@link ApplicationBasePage} with the given {@link PageParameters} object.
	 *
	 * @param parameters
	 *            the {@link PageParameters} object
	 */
	public ApplicationBasePage(final PageParameters parameters)
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
        add(new HtmlTag("html"));
        add(new OptimizedMobileViewportMetaTag("viewport"));
        add(new IeEdgeMetaTag("ie-edge"));
		add(new FaviconBehavior());
		add(new BootstrapBaseBehavior());
		final HeaderResponseContainer headerResponseContainer = new HeaderResponseContainer(
			WicketBootstrap3Application.FOOTER_FILTER_NAME,
			WicketBootstrap3Application.FOOTER_FILTER_NAME);
		add(headerResponseContainer);
        add(new Code("code-internal"));
	}

	/**
	 * Change theme.
	 *
	 * @param themeParameter
	 *            the theme parameter
	 */
	protected void changeTheme(final String themeParameter)
	{
		if ((themeParameter != null) && !themeParameter.isEmpty())
		{
			final IBootstrapSettings settings = Bootstrap.getSettings(getWicketApplication());
			settings.getActiveThemeProvider().setActiveTheme(themeParameter);
		}
	}

	/**
	 * sets the theme for the current user.
	 *
	 * @param pageParameters
	 *            current page parameters
	 */
	private void configureTheme(final PageParameters pageParameters)
	{
		newTheme(pageParameters.get("theme"));
	}

	/**
	 * Factory method for creating a new {@link Component} for the main container. This method is invoked in the
	 * constructor from the derived classes and have to be overridden so users can provide their own
	 * version of a new {@link Component} for the main container.
	 *
	 * @param id
	 *            the id
	 * @param model
	 *            the model
	 * @return the new {@link Component} for the main container
	 */
	public abstract Component newContainerPanel(final String id, final IModel<T> model);


	/**
	 * Gets the wicket application.
	 *
	 * @return the wicket application
	 */
	public WicketApplication getWicketApplication()
	{
		return WicketApplication.get();
	}

	/**
	 * Factory method for creating a new {@link Behavior} for the session timeout. This method is invoked in the
	 * constructor from the derived classes and have to be overridden so users can provide their own
	 * version of a new {@link Behavior} for the session timeout.
	 */
	protected void newSessionTimeoutBehavior() {
		final int sessionTimeout = WicketSession.get().getSessionTimeout();
		if (0 < sessionTimeout)
		{
			add(newSessionTimeoutBehavior("sessionTimeoutNotification", newSessionTimeoutSettings()));
		}
	}

	/**
	 * Factory method for creating a new {@link SessionTimeoutSettings} for the session timeout. This method is invoked in the
	 * constructor from the derived classes and have to be overridden so users can provide their own
	 * version of a new {@link SessionTimeoutSettings} for the session timeout.
	 */
	protected SessionTimeoutSettings newSessionTimeoutSettings() {
		final SessionTimeoutSettings settings;
		final int sessionTimeout = WicketSession.get().getSessionTimeout();
		final int oneThirdOfWarnAfter;
		if (0 < sessionTimeout)
		{
			oneThirdOfWarnAfter = (sessionTimeout * 1000) / 3;
		} else {
			oneThirdOfWarnAfter = (300 * 1000) / 3;
		}
		final int twoThirdOfWarnAfter = oneThirdOfWarnAfter * 2;
		settings = SessionTimeoutSettings.builder().build();
		settings.getTitle().setValue("Session timeout warning");
		settings.getMessage().setValue("Your session will be timeouted...");
		settings.getWarnAfter().setValue(oneThirdOfWarnAfter);
		settings.getRedirAfter().setValue(twoThirdOfWarnAfter);
		settings.getRedirUrl().setValue("/public/imprint");
		settings.getLogoutUrl().setValue("/public/imprint");
		return settings;
	}

	/**
	 * Factory method for creating a new {@link JavascriptAppenderBehavior}. This method is invoked in the
	 * constructor from the derived classes and have to be overridden so users can provide their own
	 * version of a new {@link JavascriptAppenderBehavior}.
	 *
	 * @param id the id
	 * @param settings the settings
	 * @return the new {@link JavascriptAppenderBehavior}
	 */
	protected JavascriptAppenderBehavior newSessionTimeoutBehavior(final String id, final SessionTimeoutSettings settings) {
		final SessionTimeoutJsGenerator generator = new SessionTimeoutJsGenerator(settings);
		final String jsCode = generator.generateJs();
		return JavascriptAppenderBehavior.builder().id(id).javascript(jsCode).build();
	}


	/**
	 * Factory method for creating a new {@link FeedbackPanel}. This method is invoked in the
	 * constructor from the derived classes and have to be overridden so users can provide their own
	 * version of a new {@link FeedbackPanel}.
	 *
	 * @param id
	 *            the id
	 * @param model
	 *            the model
	 * @return the new {@link FeedbackPanel}
	 */
	protected FeedbackPanel newFeedbackPanel(final String id, final IModel<T> model)
	{
		final NotificationPanel notificationPanel = new NotificationPanel(id);
		notificationPanel.setOutputMarkupId(true);
		notificationPanel.setOutputMarkupPlaceholderTag(true);
		notificationPanel.hideAfter(Duration.seconds(5));
		return notificationPanel;
	}

	/**
	 * Factory method for creating a new {@link Panel} for the footer area. This method is invoked in the
	 * constructor from the derived classes and have to be overridden so users can provide their own
	 * version of a new {@link Panel} for the footer area.
	 *
	 * @param id
	 *            the id
	 * @param model
	 *            the model
	 * @return the new {@link Panel} for the footer area
	 */
	protected Panel newFooterPanel(final String id, final IModel<T> model)
	{
		return new FooterPanel<List<LinkItem>>(id, newFooterLinkItems())
		{
			private static final long serialVersionUID = 1L;

			/**
			 * {@inheritDoc}
			 */
			@Override
			protected Component newFooterMenuPanel(final String id,
				final IModel<List<LinkItem>> model)
			{

				final FooterMenuPanel footerMenu = new FooterMenuPanel(id, model)
				{

					/** The Constant serialVersionUID. */
					private static final long serialVersionUID = 1L;

					/**
					 * {@inheritDoc}
					 */
					@Override
					protected Component newLinkListPanel(final String id,
						final IModel<List<LinkItem>> model)
					{
						final LinkListPanel listPanel = new LinkListPanel(id, model)
						{
							private static final long serialVersionUID = 1L;

							/**
							 * {@inheritDoc}
							 */
							@Override
							protected String getCurrentPageCssClass()
							{
								return "active";
							}

							/**
							 * {@inheritDoc}
							 */
							@Override
							protected Component newListComponent(final String id,
								final ListItem<LinkItem> item)
							{
								final LinkItem model = item.getModelObject();
								final Label itemLinkLabel = super.newItemLinkLabel("itemLinkLabel",
									model);
								itemLinkLabel.add(new AttributeAppender("class", " a"));
								final AbstractLink link = super.newAbstractLink(id, model);
								link.add(new AttributeAppender("class", " btn btn-default"));
								link.add(itemLinkLabel);
								return link;
							}
						};
						listPanel.add(new AttributeAppender("class", " btn"));
						return listPanel;
					}
				};
				// Add bootstrap class to ul element...
				add(new JqueryStatementsBehavior().add(
					new BuildableChainableStatement.Builder().label("find")
						.args(JsUtils.quotes("ul")).build()).add(
					new BuildableChainableStatement.Builder().label("addClass")
						.args(JsUtils.quotes("nav navbar-nav list-inline a")).build()));
				return footerMenu;
			}
		};
	}

	/**
	 * Factory method for creating a new list with the link items for the footer area.
	 *
	 * @return the new <code>{@link IModel}</code> with the link items for the footer area.
	 */
	protected IModel<List<LinkItem>> newFooterLinkItems()
	{
		final List<LinkItem> linkModel = new ArrayList<>();
		linkModel.add(LinkItem
			.builder()
			.url("http://www.alpharogroup.de/")
			.target(DefaultTargets.BLANK.getTarget())
			.linkClass(ExternalLink.class)
			// open in a new tab or window...
			.resourceModelKey(
				ResourceBundleKey.builder().key("main.footer.copyright.label")
					.defaultValue("\u0040 copyright {year} Design by {company}").build())
			.build());
//		linkModel.add(LinkItem
//			.builder()
//			.pageClass(ImprintPage.class)
//			.resourceModelKey(
//				ResourceBundleKey.builder().key("main.global.menu.masthead.label")
//					.defaultValue("Imprint").build()).build());
//		linkModel.add(LinkItem
//			.builder()
//			.pageClass(TermOfUsePage.class)
//			.resourceModelKey(
//				ResourceBundleKey.builder().key("main.global.menu.term.of.use.label")
//					.defaultValue("AGBs").build()).build());
		final IModel<List<LinkItem>> listModel = new ListModel<>(linkModel);
		return listModel;
	}

	/**
	 * Factory method that can be overwritten for new meta tag content for keywords.
	 *
	 * @return the new <code>{@link IModel}</code> for the keywords.
	 */
	@Override
	protected IModel<String> newKeywords()
	{
		return ResourceModelFactory.newResourceModel("page.meta.keywords", this,
			"wicket, template, project");
	}

	/**
	 * Factory method for creating a new theme from the given {@link StringValue} that comes from the page parameters.
	 *
	 * @param theme
	 *            the new theme to set.
	 */
	protected void newTheme(final StringValue theme)
	{
		changeTheme(PageParametersExtensions.getString(theme));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onConfigure()
	{
		super.onConfigure();
		configureTheme(getPageParameters());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void renderHead(final IHeaderResponse response)
	{
		super.renderHead(response);
		final JavaScriptLibrarySettings javaScriptSettings = getApplication()
			.getJavaScriptLibrarySettings();
		response.render(JavaScriptHeaderItem.forReference(javaScriptSettings.getJQueryReference()));
		Bootstrap.renderHead(response);
		HeaderResponseExtensions.renderHeaderResponse(response, ApplicationBasePage.class);
	}

}
