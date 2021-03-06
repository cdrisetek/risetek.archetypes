package ${package}.presentermodules.platformMenu;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import ${package}.place.root.RevealMenuEventHandler;
import ${package}.place.root.RootPresenter;

public class PagePresenter extends Presenter<PagePresenter.MyView, PagePresenter.MyProxy>
	implements MyUiHandlers, RevealMenuEventHandler {
    public interface MyView extends View, HasUiHandlers<MyUiHandlers> {
    	void clearMenuPanel();
    	void installRightMenu(AbstractPlatformBarMenu... menus);
    	void installMiddleMenu(AbstractPlatformBarMenu... menus);
    	void installLeftMenu(AbstractPlatformBarMenu... menus);
    	void showTip(AbstractPlatformBarMenu menu);
    	void showChooser(AbstractPlatformBarMenu menu);
    }

    private final PlaceManager placeManager;
    
    @ProxyStandard
    @NoGatekeeper
    public interface MyProxy extends Proxy<PagePresenter> {}

    
    @Inject
    public PagePresenter(EventBus eventBus, MyView view, MyProxy proxy, 
    		DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, view, proxy, RootPresenter.SLOT_MenuContent);
        this.placeManager = placeManager;
        getView().setUiHandlers(this);
    }

	@Override
	public void hideChooser() {
		getView().clearMenuPanel();
	}

	/**
	 * menus
	 */
	@Inject
	@Named("LoginMenu")
	AbstractPlatformBarMenu accountMenu;

	@Inject
	@Named("NavgatorMenu")
	AbstractPlatformBarMenu navMenu;

	@Override
	protected void onBind() {
        getView().installLeftMenu(navMenu);
        
        getView().installMiddleMenu();
        
        getView().installRightMenu(accountMenu);
	}
	
	@ProxyEvent
	@Override
	public void onRevealDefaultLinkColumn(RevealMenuEvent event) {
		if(isVisible())
			return;

		forceReveal();
	}

	@Override
	protected
	void onReveal() {
		navMenu.onAttach();
		accountMenu.onAttach();
	}
	
	@Override
	public void showTip(AbstractPlatformBarMenu menu) {
		getView().showTip(menu);
	}

	@Override
	public void onIconClick(AbstractPlatformBarMenu menu) {
		getView().showChooser(menu);
	}

	@Override
	public void gotoPlace(String token) {
		hideChooser();
		PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken(token).build();
		placeManager.revealPlace(placeRequest);
	}
}
