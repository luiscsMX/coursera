package module3;

//Java utilities libraries

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import processing.core.PApplet;
import processing.core.PShape;
import sun.java2d.pipe.SpanShapeRenderer;

import java.util.ArrayList;
import java.util.List;

//Processing library
//Unfolding libraries
//Parsing library

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	
	public void setup() {
		size(950, 600, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    // The List you will populate with new SimplePointMarkers
	    List<Marker> markers = new ArrayList<>();

	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	    // These print statements show you (1) all of the relevant properties 
	    // in the features, and (2) how to get one property and use it
	    if (earthquakes.size() > 0) {
			System.out.println(earthquakes.size());
	    	PointFeature f = earthquakes.get(0);
	    	System.out.println(f.getProperties());
	    	Object magObj = f.getProperty("magnitude");
	    	float mag = Float.parseFloat(magObj.toString());
	    	// PointFeatures also have a getLocation method
	    }

		for(int i = 0; i < earthquakes.size(); i++) {
			markers.add(i, createMarker(earthquakes.get(i)));
		}

		map.addMarkers(markers);
	    
	    //TODO: Add code here as appropriate
	}
		
	// A suggested helper method that takes in an earthquake feature and 
	// returns a SimplePointMarker for that earthquake
	private Marker createMarker(PointFeature feature)
	{
		SimplePointMarker marker = new SimplePointMarker(feature.getLocation());
		Float magnitude = getMagnitudeFromPointFeature(feature);

		if(magnitude < 4.0) {
			marker.setColor(color(0, 0, 255));
			marker.setRadius(5);
		}
		else if(magnitude <= 4.9) {
			marker.setColor(color(255, 255, 0));
			marker.setRadius(10);
		}
		else {
			marker.setColor(color(255, 0, 0));
			marker.setRadius(15);
		}

		return marker;
	}

	private float getMagnitudeFromPointFeature(PointFeature feature) {
		Object magnitude = feature.getProperty("magnitude");

		return Float.parseFloat(magnitude.toString());
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}

	// helper method to draw key in GUI
	private void addKey() 
	{
		int keyBox_x = 25;
		int keyBox_y = height / 4;

		PShape keyBox = createShape(RECT, keyBox_x, keyBox_y, width / 6, height / 2);
		keyBox.setFill(color(255, 255, 255));

		shape(keyBox);
		textAlign(CENTER);
		fill(color(0, 0, 0));
		text("Earthquake Key", keyBox_x, keyBox_y + 5, keyBox.getWidth(), keyBox.getHeight());

		fill(color(255, 0, 0));
		ellipse(keyBox_x + 17, keyBox_y + 57, 15, 15);
		fill(color(0, 0, 0));
		text("5.0+ Magnitude", keyBox_x, keyBox_y + 50, keyBox.getWidth(), keyBox.getHeight());

		fill(color(255, 255, 0));
		ellipse(keyBox_x + 17, keyBox_y + 82, 10, 10);
		fill(color(0, 0, 0));
		text("4.0+ Magnitude", keyBox_x, keyBox_y + 75, keyBox.getWidth(), keyBox.getHeight());

		fill(color(0, 255, 0));
		ellipse(keyBox_x + 17, keyBox_y + 107, 5, 5);
		fill(color(0, 0, 0));
		text("< 4.0 Magnitude", keyBox_x, keyBox_y + 100, keyBox.getWidth(), keyBox.getHeight());

	}
}
