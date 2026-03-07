import requests
from datetime import date, timedelta


def get_rainfall(lat: float, lon: float) -> float:
    end = date.today()
    start = end - timedelta(days=365)

    url = (
        "https://archive-api.open-meteo.com/v1/archive"
        f"?latitude={lat}&longitude={lon}"
        f"&start_date={start}&end_date={end}"
        "&daily=rain_sum&timezone=auto"
    )

    try:
        data = requests.get(url, timeout=10).json()
        rain = data.get("daily", {}).get("rain_sum", [])
        return round(sum(rain), 2) if rain else 0.0
    except Exception:
        return 0.0


def rainfall_status(mm: float) -> str:
    if mm < 500:
        return "Drought Risk"
    elif mm > 1500:
        return "Flood Risk"
    return "Normal"


def get_soil_moisture(lat: float, lon: float):
    url = (
        "https://api.open-meteo.com/v1/forecast"
        f"?latitude={lat}&longitude={lon}"
        "&hourly=soil_moisture_0_1cm"
    )

    try:
        data = requests.get(url, timeout=10).json()
        values = data.get("hourly", {}).get("soil_moisture_0_1cm", [])
        return round(sum(values) / len(values), 2) if values else None
    except Exception:
        return None


def soil_status(value):
    if value is None:
        return "Soil Data Unavailable"
    return "Dry Soil" if value < 0.2 else "Healthy Soil"
