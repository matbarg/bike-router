# Starting Guide

### Step 1

Download the OSM data file `austria-latest.osm.pbf` from this link:
https://download.geofabrik.de/europe/austria.html

Put the file in `src/main/resources/data/`.

### Step 2

Start the application `BikeRouterApplication.java`.

On the first start, this will take a few minutes to generate the graph in `target/graph-cache`.

(Hint in case of failure: make sure the path reference in `util/GHConfig.java` matches the name of the OSM file.)

### Step 3

Open `localhost:8080` in the browser.