package ${package}.presentermodules.home.cards.welcome;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import ${package}.bindery.AutoLoadPresenterModule;

@AutoLoadPresenterModule
public class Module extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(HomeCardPresenter.class, HomeCardPresenter.MyView.class, HomeCardView.class,
        		HomeCardPresenter.MyProxy.class);
    	
    }
}
