// Center on Vienna for now
const map = L.map("map").setView([48.2082, 16.3738], 13);

// OpenStreetMap tiles
L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
    attribution: "© OpenStreetMap contributors",
    maxZoom: 19
}).addTo(map);

let startPoint = null;
let endPoint = null;

map.on('click', e => {
    if (!startPoint) {
        startPoint = e.latlng;
        L.marker(startPoint).addTo(map).bindPopup(`Lat: ${startPoint.lat.toFixed(5)}, Lon: ${startPoint.lng.toFixed(5)}`).openPopup();
    } else if (!endPoint) {
        endPoint = e.latlng;
        L.marker(endPoint).addTo(map).bindPopup(`Lat: ${endPoint.lat.toFixed(5)}, Lon: ${endPoint.lng.toFixed(5)}`).openPopup();

        const requests = [{
            profile: "FAST",
            color: "rgba(255,242,0,0.8)",
        }, {
            profile: "SAFE",
            color: "rgba(0,102,255,0.8)",
        }, {
            profile: "COMFORT",
            color: "rgba(174,90,244,0.8)",
        }, {
            profile: "SCENIC",
            color: "rgba(58,223,80,0.8)",
        }];

        for (const r of requests) {
            requestRoute([startPoint, endPoint], r.profile, r.color);
        }
    } else {
        map.eachLayer(l => {
            if (l instanceof L.Marker || l instanceof L.GeoJSON) {
                map.removeLayer(l);
            }
        });
        startPoint = null;
        endPoint = null;
    }
});

function requestRoute(points, profile, color) {
    const body = {
        profile: profile,
        points: points.map(p => ({
            "lat": p.lat,
            "lon": p.lng
        }))
    };

    fetch('http://localhost:8080/api/route', {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(body)
    })
        .then(res => res.json())
        .then(route => {
            console.log(route);
            L.geoJSON(route, {
                style: {
                    color: color,
                    weight: 5
                }
            }).addTo(map);
        })
}

//testRequests();

function testRequests() {
    const requests = [{
        profile: "FAST",
        color: "rgba(255,242,0,0.8)",
    }, {
        profile: "SAFE",
        color: "rgba(0,102,255,0.8)",
    }, {
        profile: "COMFORT",
        color: "rgba(174,90,244,0.8)",
    }, {
        profile: "SCENIC",
        color: "rgba(58,223,80,0.8)",
    }];

    for (const req of requests) {
        fetch(`http://localhost:8080/api/test-profile?profile=${req.profile}`)
            .then(res => res.json())
            .then(route => {
                console.log(`Profile: ${req.profile}`);
                console.log(route);
                L.geoJSON(route, {
                    style: {
                        color: req.color,
                        weight: 5
                    }
                }).addTo(map);
            });
    }
}
