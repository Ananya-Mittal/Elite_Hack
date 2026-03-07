
import os
print(f"Current Directory: {os.getcwd()}")
print(f"Directory Contents: {os.listdir('.')}")

from fastapi import FastAPI
from models.request import CreditRequest

from services.ndvi import get_ndvi, crop_health
from services.weather import (
    get_rainfall,
    rainfall_status,
    get_soil_moisture,
    soil_status
)
from services.yield_calc import estimate_yield, estimate_revenue
from services.loan import loan_decision
from services.score import calculate_score
from services.explain import explain_score


app = FastAPI(title="Agri Credit Scoring System")


@app.post("/credit-score")
def credit_score(data: CreditRequest):
    lat, lon, crop = data.lat, data.lon, data.crop

    ndvi = get_ndvi(lat, lon)
    health = crop_health(ndvi)

    rainfall = get_rainfall(lat, lon)
    weather = rainfall_status(rainfall)

    soil = get_soil_moisture(lat, lon)
    soil_state = soil_status(soil)

    est_yield = estimate_yield(crop, ndvi)
    revenue = estimate_revenue(crop, est_yield)

    loan_limit, decision = loan_decision(revenue, health, weather)

    score = calculate_score(ndvi, weather, soil_state, est_yield)
    reasons = explain_score(ndvi, weather, soil_state, est_yield)

    return {
        "ndvi": ndvi,
        "crop_health": health,
        "weather": weather,
        "soil_status": soil_state,
        "estimated_yield": f"{est_yield} tons",
        "revenue": revenue,
        "loan_limit": loan_limit,
        "score": score,
        "decision": decision,
        "reasons": reasons
    }
