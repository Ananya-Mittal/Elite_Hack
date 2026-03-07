import ee

# Initialize Earth Engine safely
try:
    ee.Initialize(project="agricultural-land-486413")
except Exception:
    ee.Authenticate()
    ee.Initialize(project="agricultural-land-486413")


def get_ndvi(lat, lon):
    try:
        # Create point geometry
        point = ee.Geometry.Point([lon, lat])

        # Sentinel-2 image collection
        image = (
            ee.ImageCollection("COPERNICUS/S2_SR_HARMONIZED")
            .filterBounds(point)
            .filterDate("2024-01-01", "2024-12-31")
            .median()
        )

        # NDVI calculation
        ndvi = image.normalizedDifference(["B8", "B4"]).rename("NDVI")

        # Extract value
        value = ndvi.reduceRegion(
            reducer=ee.Reducer.mean(),
            geometry=point,
            scale=10
        )

        result = value.getInfo()

        if result and "NDVI" in result:
            return round(result["NDVI"], 2)

        return 0.0

    except Exception as e:
        print("NDVI error:", e)
        return 0.0