import requests

OPENMETEO_URL = "https://api.open-meteo.com/v1/forecast"

def get_rainfall(lat, lon):

    params = {
        "latitude": lat,
        "longitude": lon,
        "hourly": "rain"
    }

    try:
        r = requests.get(OPENMETEO_URL, params=params, timeout=10)
        data = r.json()

        rain_list = data.get("hourly", {}).get("rain", [])

        if len(rain_list) == 0:
            return 0.0

        rainfall = sum(rain_list[:24])

        return round(rainfall, 2)

    except Exception as e:
        print("Weather API error:", e)
        return 0.0


def rainfall_status(rainfall):

    if rainfall > 10:
        return "Flood Risk"
    elif rainfall > 3:
        return "Normal"
    else:
        return "Drought Risk"