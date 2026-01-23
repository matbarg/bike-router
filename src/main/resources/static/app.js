// Center on Vienna for now
const map = L.map("map").setView([48.2082, 16.3738], 13);

// OpenStreetMap tiles
L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
    attribution: "© OpenStreetMap contributors",
    maxZoom: 19
}).addTo(map);

const routesControl = L.control({position: "topright"});

let routesOverlay;

routesControl.onAdd = () => {
    routesOverlay = L.DomUtil.create("div", "overlay routes-overlay");
    routesOverlay.innerHTML = `
        <div class="overlay-header">
            <h3>Routes</h3>
        </div>
        <div class="routes-content"></div>`;
    L.DomEvent.disableClickPropagation(routesOverlay);
    return routesOverlay;
};

routesControl.addTo(map);

const instructionsControl = L.control({position: "bottomleft"});

let instructionsOverlay;

instructionsControl.onAdd = () => {
    instructionsOverlay = L.DomUtil.create("div", "overlay instructions-overlay hidden");
    instructionsOverlay.innerHTML = `
        <div class="overlay-header">
            <h3>Instructions</h3>
            <button class="close-overlay-btn" title="Close">✕</button>
        </div>
        <div class="instructions-content"></div>
    `;
    L.DomEvent.disableClickPropagation(instructionsOverlay);
    L.DomEvent.disableScrollPropagation(instructionsOverlay);
    return instructionsOverlay;
};

instructionsControl.addTo(map);

const requestControlDiv = document.getElementById("routing-controls");

const requestControl = L.control({position: "topleft"});

requestControl.onAdd = () => {
    return requestControlDiv;
};

L.DomEvent.disableClickPropagation(requestControlDiv);
requestControl.addTo(map);

let activeRoutes = [];
let routeCounter = 0;

let startPoint = null;
let endPoint = null;

let stopPoints = []
let stopCounter = 0;

map.on("click", e => {

    if (!startPoint) {
        startPoint = e.latlng;
        L.marker(startPoint).addTo(map).bindPopup(`Lat: ${startPoint.lat.toFixed(5)}, Lon: ${startPoint.lng.toFixed(5)}`).openPopup();
    } else if (stopPoints.length < stopCounter) {
        const stop = e.latlng;
        stopPoints.push(stop);
        L.marker(stop).addTo(map).bindPopup(`Lat: ${stop.lat.toFixed(5)}, Lon: ${stop.lng.toFixed(5)}`).openPopup();
    } else if (!endPoint) {
        endPoint = e.latlng;
        L.marker(endPoint).addTo(map).bindPopup(`Lat: ${endPoint.lat.toFixed(5)}, Lon: ${endPoint.lng.toFixed(5)}`).openPopup();
        makeRequest();
    } else {
        map.eachLayer(l => {
            if (l instanceof L.Marker || l instanceof L.GeoJSON) {
                map.removeLayer(l);
            }
        });
        startPoint = null;
        endPoint = null;
        routesOverlay.querySelector(".routes-content").innerHTML = "";
        instructionsOverlay.classList.add("hidden");
        instructionsOverlay.querySelector(".instructions-content").innerHTML = "";
        activeRoutes = [];
        stopPoints = [];
        removeRouteHighlight()
    }
});

function makeRequest() {
    const routePoints = [startPoint, ...stopPoints, endPoint];

    const requestOptionsForm = document.getElementById("request-options");

    if (requestOptionsForm.elements["use-preferences"].checked) {
        // do a single request with the preferences
        requestRoute(routePoints, {
            profile: "FAST",
            mode: "CUSTOM",
            speed: 20,
            preferences: getRequestPreferences(),
            color: "rgb(98,250,237)",
        });

        requestRoute(routePoints, {
            profile: "FAST",
            mode: "CUSTOM",
            speed: 20,
            preferences: {
                avoidBadSurfaces: "DEFAULT",
                avoidTraffic: "DEFAULT",
                avoidHills: "DEFAULT",
                preferBikeInfra: "DEFAULT",
                preferParks: "DEFAULT"
            },
            color: "rgb(234,101,43)",
        });
    } else {
        // test out each profile preset
        const requests = [{
            profile: "FAST",
            mode: "PRESET",
            speed: 20,
            color: "rgb(234,101,43)",
        }, {
            profile: "SAFE",
            mode: "PRESET",
            speed: 18,
            color: "rgba(0,102,255)",
        }, {
            profile: "COMFORT",
            mode: "PRESET",
            speed: 15,
            color: "rgba(174,90,244)",
        }, {
            profile: "SCENIC",
            mode: "PRESET",
            speed: 17,
            color: "rgba(58,223,80)",
        }];

        for (const r of requests) {
            requestRoute(routePoints, r);
        }
    }
}

function requestRoute(points, r) {
    const body = {
        profile: r.profile,
        mode: r.mode,
        points: points.map(p => ({
            "lat": p.lat,
            "lon": p.lng
        })),
        speed: r.speed,
        preferences: r.preferences
    };

    fetch('http://localhost:8080/api/route', {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(body)
    })
        .then(res => res.json())
        .then(route => addRoute(route, r.color));
}

function addRoute(route, color) {
    routeCounter++;
    const routeId = String(routeCounter);

    console.log(route);

    const layer = L.geoJSON(route, {
        style: {
            color: color,
            weight: 5,
            opacity: 0.8
        }
    }).addTo(map);

    activeRoutes.push({
        id: routeId,
        result: route,
        layer
    });

    const content = routesOverlay.querySelector(".routes-content");
    content.innerHTML += routeCardHTML(route, color, routeId);
}

function routeCardHTML(routeResult, color, routeId) {
    const distance = (routeResult.properties.distance / 1000).toFixed(2);
    const ascend = routeResult.properties.descend.toFixed(0);
    const descend = routeResult.properties.descend.toFixed(0);

    return `<div class="route-card" data-route-id="${routeId}" style="border-left-color: ${color}">
        <ul>
            <li><b>Profile:</b> ${routeResult.properties.profile}</li>
            <li><b>Time:</b> ${(routeResult.properties.time / 1000 / 60).toFixed(0)} min</li>
            <li><b>Distance:</b> ${distance} km</li>
            <li><b>↑</b> ${ascend} m (${(ascend / distance).toFixed(0)} m/km)</li>
            <li><b>↓</b> ${descend} m</li>
            <li><b>Calc Time:</b> ${routeResult.properties.calcTime} ms</li>
        </ul>
        <button class="show-instructions-btn">Show instructions</button>
    </div>`;
}

function routeInstructionsHTML(routeResult) {
    let instructionsHtml = "";

    routeResult.properties.instructions.forEach(i => {
        const instruction = `<div class='instruction-container'>
            <p>${i.description}</p>
            <p><b>Distance:</b> ${i.distance.toFixed(0)} m</p>
            <p><b>Time:</b> ${(i.time / 1000 / 60).toFixed(1)} min</p>
        </div>`;

        instructionsHtml += instruction;
    });

    return instructionsHtml;
}

routesOverlay.addEventListener("click", e => {
    const btn = e.target.closest(".show-instructions-btn");
    if (!btn) return;

    const routeCard = btn.closest(".route-card");
    const routeId = routeCard.dataset.routeId;

    console.log(activeRoutes);

    const route = activeRoutes.find(r => r.id === routeId);
    console.log(route);
    if (!route) return;

    const content = instructionsOverlay.querySelector(".instructions-content");
    content.innerHTML = routeInstructionsHTML(route.result);

    instructionsOverlay.classList.remove("hidden");
    highlightRoute(routeId);
});

instructionsOverlay.addEventListener("click", e => {
    if (!e.target.closest(".close-overlay-btn")) return;

    instructionsOverlay.classList.add("hidden");
    removeRouteHighlight();
});

function highlightRoute(routeId) {
    activeRoutes.forEach(r => {
        if (r.id === routeId) {
            r.layer.setStyle({
                opacity: 1
            });
            r.layer.bringToFront();
        } else {
            r.layer.setStyle({
                opacity: 0.4
            });
        }
    });
}

function removeRouteHighlight() {
    activeRoutes.forEach(r => {
        r.layer.setStyle({
            opacity: 0.8
        });
    });
}

function getRequestPreferences() {
    const preferencesForm = document.getElementById("request-preferences");

    return {
        avoidBadSurfaces: preferencesForm.elements["avoidBadSurfaces"].value,
        avoidTraffic: preferencesForm.elements["avoidHills"].value,
        avoidHills: preferencesForm.elements["avoidTraffic"].value,
        preferBikeInfra: preferencesForm.elements["preferBikeInfra"].value,
        preferParks: preferencesForm.elements["preferParks"].value,
    };
}

const stopCounterInput = document.getElementById("stop-counter");

document.querySelector(".inc-stop").addEventListener("click", () => {
    stopCounter++;
    stopCounterInput.value = stopCounter;
});

document.querySelector(".dec-stop").addEventListener("click", () => {
    if (stopCounter > 0) {
        stopCounter--;
        stopCounterInput.value = Math.max(0, stopCounter);
    }
});

document.getElementById("profile").addEventListener("change", e => {
    const selectedProfile = e.target.value;

    const profilePreferences = {
        FAST: {
            avoidBadSurfaces: "DEFAULT",
            avoidTraffic: "DEFAULT",
            avoidHills: "DEFAULT",
            preferBikeInfra: "DEFAULT",
            preferParks: "DEFAULT"
        },
        SAFE: {
            avoidBadSurfaces: "LOW",
            avoidTraffic: "LOW",
            avoidHills: "DEFAULT",
            preferBikeInfra: "HIGH",
            preferParks: "DEFAULT"
        },
        COMFORT: {
            avoidBadSurfaces: "HIGH",
            avoidTraffic: "DEFAULT",
            avoidHills: "HIGH",
            preferBikeInfra: "DEFAULT",
            preferParks: "DEFAULT"
        },
        SCENIC: {
            avoidBadSurfaces: "DEFAULT",
            avoidTraffic: "HIGH",
            avoidHills: "DEFAULT",
            preferBikeInfra: "DEFAULT",
            preferParks: "HIGH"
        }
    }

    selectProfilePreferences(profilePreferences[selectedProfile]);
});

function selectProfilePreferences(profile) {
    const preferencesForm = document.getElementById("request-preferences");

    preferencesForm.elements["avoidBadSurfaces"].value = profile.avoidBadSurfaces;
    preferencesForm.elements["avoidHills"].value = profile.avoidHills;
    preferencesForm.elements["avoidTraffic"].value = profile.avoidTraffic;
    preferencesForm.elements["preferBikeInfra"].value = profile.preferBikeInfra;
    preferencesForm.elements["preferParks"].value = profile.preferParks;
}

document.getElementById("use-preferences").addEventListener("change", e => {
   if (e.target.checked) {
       document.getElementById("preferences").classList.remove("hidden");
   } else {
       document.getElementById("preferences").classList.add("hidden");
   }
});