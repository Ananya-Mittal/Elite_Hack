BASE_YIELD = {
    "wheat": 3.0,
    "rice": 4.0
}

PRICE = {
    "wheat": 22000,
    "rice": 18000
}


def estimate_yield(crop: str, ndvi: float) -> float:
    historical_ndvi = 0.55
    return round(BASE_YIELD[crop] * (ndvi / historical_ndvi), 2)


def estimate_revenue(crop: str, yield_tons: float) -> int:
    return int(yield_tons * PRICE[crop])
