// Center on Vienna for now
const map = L.map("map").setView([48.2082, 16.3738], 13);

// OpenStreetMap tiles
L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
    attribution: "© OpenStreetMap contributors",
    maxZoom: 19
}).addTo(map);

const requests = [{
    profile: "fast",
    color: "#fff200",
}];

/*
{
    profile: "safe",
    color: "#0066ff",
}, {
    profile: "scenic",
    color: "#3adf50",
}
 */

for (const req of requests) {
    fetch("http://localhost:8080/api/test")
        .then(res => res.json())
        .then(route => {
            console.log(route);
            L.geoJSON(route, {
                style: {
                    color: req.color,
                    weight: 5
                }
            }).addTo(map);
        });
}
