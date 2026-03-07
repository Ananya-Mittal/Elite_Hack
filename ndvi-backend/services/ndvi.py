import ee

try:
    ee.Initialize(project="agricultural-land-486413")
    EE_AVAILABLE = True
except Exception:
    EE_AVAILABLE = False


def get_ndvi(lat: float, lon: float) -> float:
    """
    Returns NDVI using Sentinel-2.
    If Earth Engine fails → safe fallback.
    """
    if not EE_AVAILABLE:
        return 0.6  # SAFE fallback

    try:
        point = ee.Geometry.Point([lon, lat])

        image = (
            ee.ImageCollection("COPERNICUS/S2_SR_HARMONIZED")
            .filterBounds(point)
            .filterDate("2023-06-01", "2023-10-01")
            .filter(ee.Filter.lt("CLOUDY_PIXEL_PERCENTAGE", 20))
            .median()
        )

        ndvi = image.normalizedDifference(["B8", "B4"])
        value = ndvi.reduceRegion(
            reducer=ee.Reducer.mean(),
            geometry=point,
            scale=10,
            bestEffort=True
        ).get("ndvi")

        result = value.getInfo()
        return round(result, 2) if result else 0.6

    except Exception:
        return 0.6


def crop_health(ndvi: float) -> str:
    if ndvi > 0.55:
        return "Good"
    elif ndvi > 0.3:
        return "Moderate"
    else:
        return "Poor"
