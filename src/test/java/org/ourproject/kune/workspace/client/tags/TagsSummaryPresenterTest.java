package org.ourproject.kune.workspace.client.tags;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ourproject.kune.workspace.client.search.SiteSearcher;

import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.TagCloudResult;
import cc.kune.core.shared.domain.TagCount;
import cc.kune.core.shared.dto.StateContainerDTO;

import com.calclab.suco.client.ioc.Provider;

public class TagsSummaryPresenterTest {

    private TagsSummaryPresenter tagsSummaryPresenter;
    private TagsSummaryView view;

    @SuppressWarnings("unchecked")
    @Before
    public void before() {
        final Session session = Mockito.mock(Session.class);
        final StateManager stateManager = Mockito.mock(StateManager.class);
        final SiteSearcher searcher = Mockito.mock(SiteSearcher.class);
        final Provider searcherProvider = Mockito.mock(Provider.class);
        Mockito.when(searcherProvider.get()).thenReturn(searcher);
        view = Mockito.mock(TagsSummaryView.class);
        tagsSummaryPresenter = new TagsSummaryPresenter(session, searcherProvider, stateManager);
        tagsSummaryPresenter.init(view);
    }

    @Test
    public void noTagsViewNotVisible() {
        final StateContainerDTO state = new StateContainerDTO();
        tagsSummaryPresenter.setState(state);
        Mockito.verify(view).setVisible(false);
    }

    @Test
    public void withTagsViewFalse() {
        final StateContainerDTO state = new StateContainerDTO();
        final ArrayList<TagCount> list = new ArrayList<TagCount>();
        state.setTagCloudResult(new TagCloudResult(list, 0, 0));
        tagsSummaryPresenter.setState(state);
        Mockito.verify(view).setVisible(false);
    }

    @Test
    public void withTagsViewVisible() {
        final StateContainerDTO state = new StateContainerDTO();
        final ArrayList<TagCount> list = new ArrayList<TagCount>();
        list.add(new TagCount("abc", 1L));
        state.setTagCloudResult(new TagCloudResult(list, 0, 0));
        tagsSummaryPresenter.setState(state);
        Mockito.verify(view).setVisible(true);
    }
}
