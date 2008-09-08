package org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitesign;

import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.site.SiteToken;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.Hyperlink;

public class SiteSignInLinkPanel implements SiteSignInLinkView {
    private final Hyperlink signInHyperlink;

    public SiteSignInLinkPanel(final SiteSignInLinkPresenter presenter, final I18nUITranslationService i18n,
	    final WorkspaceSkeleton ws) {
	signInHyperlink = new Hyperlink();
	signInHyperlink.setText(i18n.t("Sign in to collaborate"));
	signInHyperlink.setTargetHistoryToken(SiteToken.signin.toString());
	ws.getSiteBar().add(signInHyperlink);
	ws.getSiteBar().addSpacer();
    }

    public void setVisible(final boolean visible) {
	signInHyperlink.setVisible(visible);
    }
}
