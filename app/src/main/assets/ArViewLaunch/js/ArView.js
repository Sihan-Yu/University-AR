var World = {
	loaded: false,

	init: function initFn() {
		this.createModelAtLocation();
	},

	createModelAtLocation: function createModelAtLocationFn() {

		/*
			First a location where the model should be displayed will be defined. This location will be relative to the user.	
		*/
		var location = new AR.RelativeLocation(null, 5, 0, 2);

		/*
			Next the model object is loaded, the marker is given an image, followed by converting it to a drawable.
		*/

        var markerImage = new AR.ImageResource("assets/marker.png");

        var markerDrawable = new AR.ImageDrawable(markerImage, 5);
        
        /*
            Indicator is then given an image then loaded in as a drawable which is Anchored to where the marker is.
        */
        var indicatorImage = new AR.ImageResource("assets/indi.png");

        var indicatorDrawable = new AR.ImageDrawable(indicatorImage, 0.1, {
            verticalAnchor: AR.CONST.VERTICAL_ANCHOR.TOP
        });
       this.worldLoaded();
		/*
			Putting it all together the location and 3D model is added to an AR.GeoObject.
		*/
		var obj = new AR.GeoObject(location, {
            drawables: {
               cam: [markerDrawable],
               indicator: [indicatorDrawable]
            }
        });
	},
    
    

	worldLoaded: function worldLoadedFn() {
		World.loaded = true;
		var e = document.getElementById("loadingMessage");
		e.parentNode.removeChild(e);
	}
};

World.init();
