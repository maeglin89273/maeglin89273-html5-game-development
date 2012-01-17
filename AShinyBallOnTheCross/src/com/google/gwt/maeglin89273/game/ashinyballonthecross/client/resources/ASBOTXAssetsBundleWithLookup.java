/**
 * 
 */
package com.google.gwt.maeglin89273.game.ashinyballonthecross.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.maeglin89273.game.mengine.asset.AssetsBundleWithLookup;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;

/**
 * @author Maeglin Liao
 *
 */
public interface ASBOTXAssetsBundleWithLookup extends AssetsBundleWithLookup {
	public static final ASBOTXAssetsBundleWithLookup INSTANCE =GWT.create(ASBOTXAssetsBundleWithLookup.class);
	@Source("com/google/gwt/maeglin89273/game/ashinyballonthecross/client/resources/images/areas.png")
	ImageResource areas();

	@Source("com/google/gwt/maeglin89273/game/ashinyballonthecross/client/resources/images/buttons.png")
	ImageResource buttons();

	@Source("com/google/gwt/maeglin89273/game/ashinyballonthecross/client/resources/images/definers_icons.png")
	ImageResource definers_icons();

	@Source("com/google/gwt/maeglin89273/game/ashinyballonthecross/client/resources/images/gravity_indicator.png")
	ImageResource gravity_indicator();

	@Source("com/google/gwt/maeglin89273/game/ashinyballonthecross/client/resources/images/score_board.png")
	ImageResource score_board();

	@Source("com/google/gwt/maeglin89273/game/ashinyballonthecross/client/resources/images/shinyball.png")
	ImageResource shinyball();

	@Source("com/google/gwt/maeglin89273/game/ashinyballonthecross/client/resources/images/welcome_bg.png")
	ImageResource welcome_bg();

	@Source("com/google/gwt/maeglin89273/game/ashinyballonthecross/client/resources/levels/INTRO_level_1.json")
	DataResource INTRO_level_1();

	@Source("com/google/gwt/maeglin89273/game/ashinyballonthecross/client/resources/levels/INTRO_level_2.json")
	DataResource INTRO_level_2();

	@Source("com/google/gwt/maeglin89273/game/ashinyballonthecross/client/resources/levels/INTRO_level_3.json")
	DataResource INTRO_level_3();

	@Source("com/google/gwt/maeglin89273/game/ashinyballonthecross/client/resources/levels/INTRO_level_4.json")
	DataResource INTRO_level_4();

	@Source("com/google/gwt/maeglin89273/game/ashinyballonthecross/client/resources/levels/INTRO_level_5.json")
	DataResource INTRO_level_5();

	@Source("com/google/gwt/maeglin89273/game/ashinyballonthecross/client/resources/levels/welcome_level.json")
	DataResource welcome_level();

	@Source("com/google/gwt/maeglin89273/game/ashinyballonthecross/client/resources/images/MEngine_logo.png")
	ImageResource MEngine_logo();

	@Source("com/google/gwt/maeglin89273/game/ashinyballonthecross/client/resources/images/MStudios_landscape.png")
	ImageResource MStudios_landscape();

	
}
