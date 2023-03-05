import {APIKey} from "./constants";

const apiPrefix = "/api"

export const register = (credential, isHost) => {
    const registerUrl = `${apiPrefix}/register/${isHost ? "host" : "guest"}`;

    return fetch(registerUrl, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(credential)
    }).then(response => {
        if (response.status !== 200) {
            throw Error("Fail to register.");
        }
    });
};

export const login = (credential, isHost) => {
    const loginUrl = `${apiPrefix}/authenticate/${isHost ? "host" : "guest"}`;

    return fetch(loginUrl, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(credential)
    }).then(response => {
        if (response.status !== 200) {
            throw Error("Fail to log in.");
        }
        return response.json();
    });
};

export const getStaysByHost = () => {
    const auth = localStorage.getItem("Token");
    const stayUrl = `${apiPrefix}/stays`;

    return fetch(stayUrl, {
        headers: {
            Authorization: `Bearer ${auth}`
        }
    }).then(response => {
        console.log(response);
        if (response.status !== 200) {
            throw Error("Fail to get stay list by host.");
        }
        return response.json();
    });
};

export const uploadStay = stay => {
    const auth = localStorage.getItem("Token");
    const uploadStayUrl = `${apiPrefix}/stays`;

    return fetch(uploadStayUrl, {
        method: "POST",
        headers: {
            Authorization: `Bearer ${auth}`
        },
        body: stay
    }).then(response => {
        if (response.status !== 200) {
            throw Error("Fail to upload stay.");
        }
    });
};

export const searchStays = async query => {
    const auth = localStorage.getItem("Token");
    const checkin_date = query.date_range[0].format("YYYY-MM-DD");
    const checkout_date = query.date_range[1].format("YYYY-MM-DD");
    let lat, lng;
    await geoCoding(query.address)
        .then(response => {
            lat = response.lat;
            lng = response.lng;
        });
    const distance = query.distance;

    const searchStayUrl = distance === undefined ? `${apiPrefix}/search?guest_number=${query.guest_number}&checkin_date=${checkin_date}&checkout_date=${checkout_date}&lat=${lat}&lon=${lng}`
        : `${apiPrefix}/search?guest_number=${query.guest_number}&checkin_date=${checkin_date}&checkout_date=${checkout_date}&lat=${lat}&lon=${lng}&distance=${distance}`;

    return fetch(searchStayUrl, {
        headers: {
            Authorization: `Bearer ${auth}`
        }
    }).then(response => {
        if (response.status !== 200) {
            throw Error("Fail to search stays.");
        }
        return response.json();
    });
};

export const deleteStay = stayId => {
    const auth = localStorage.getItem("Token");
    const deleteStayUrl = `${apiPrefix}/stays/${stayId}`;

    return fetch(deleteStayUrl, {
        method: "DELETE",
        headers: {
            Authorization: `Bearer ${auth}`
        }
    }).then(response => {
        if (response.status !== 200) {
            throw Error("Fail to delete stay.");
        }
    });
};

export const addReservation = reservation => {
    const auth = localStorage.getItem("Token");
    const reservationUrl = `${apiPrefix}/reservations`;

    return fetch(reservationUrl, {
        method: "POST",
        headers: {
            Authorization: `Bearer ${auth}`,
            "Content-Type": "application/json"
        },
        body: JSON.stringify(reservation)
    }).then(response => {
        if (response.status !== 200) {
            throw Error("Fail to add reservatiotn.");
        }
    });
};

export const cancelReservation = reservationId => {
    const auth = localStorage.getItem("Token");
    const cancelReservationUrl = `${apiPrefix}/reservations/${reservationId}`;

    return fetch(cancelReservationUrl, {
        method: "DELETE",
        headers: {
            Authorization: `Bearer ${auth}`
        }
    }).then(response => {
        if (response.status !== 200) {
            throw Error("Fail to cancel reservation.");
        }
    });
};

export const getReservations = () => {
    const auth = localStorage.getItem("Token");
    const reservationUrl = `${apiPrefix}/reservations`;

    return fetch(reservationUrl, {
        headers: {
            Authorization: `Bearer ${auth}`
        }
    }).then(response => {
        if (response.status !== 200) {
            throw Error("Fail to get reservation list.");
        }
        return response.json();
    });
};

export const getReservationsByStay = stayId => {
    const auth = localStorage.getItem("Token");
    const reservationUrl = `${apiPrefix}/stays/reservations/${stayId}`;

    return fetch(reservationUrl, {
        headers: {
            Authorization: `Bearer ${auth}`
        }
    }).then(response => {
        if (response.status !== 200) {
            throw Error("Fail to get reservatiosn by stay.");
        }
        return response.json();
    });
};

export const geoCoding = address => {
    const encodeAddress = address.replace('#', '').replace(/\s/g, '+');
    const geoCodingUrl = `https://maps.googleapis.com/maps/api/geocode/json?address=${encodeAddress}&key=${APIKey}`;

    return fetch(geoCodingUrl)
        .then(response => response.json())
        .then(data => {
            const results = data.results;
            return results[0].geometry.location;
        });
};