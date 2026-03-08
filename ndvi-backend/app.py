from fastapi import FastAPI
from schemas.request_schema import CreditRequest

from services.satellite import get_ndvi
from services.weather import get_rainfall, rainfall_status
from services.yield_calc import estimate_yield, estimate_revenue
from services.loan import loan_decision
from services.explain import explain_score
from ml.predict_model import predict_score

import random

app = FastAPI(title="Agri Credit AI")


@app.post("/credit-score")
def credit_score(data: CreditRequest):

    lat = data.lat
    lon = data.lon
    crop = data.crop.lower()

    # 1️⃣ Satellite NDVI
    ndvi = get_ndvi(lat, lon)

    # 2️⃣ Rainfall
    rainfall = get_rainfall(lat, lon)
    rain_status = rainfall_status(rainfall)

    # 3️⃣ Simulated soil moisture (you don't have API yet)
    soil = round(random.uniform(0.3, 0.9), 2)

    # 4️⃣ Yield estimation
    est_yield = estimate_yield(crop, ndvi)

    # 5️⃣ Revenue
    revenue = estimate_revenue(crop, est_yield)

    # 6️⃣ ML credit score
    score = predict_score(ndvi, rainfall, soil, est_yield)

    # 7️⃣ Loan decision
    loan_limit, decision = loan_decision(revenue, score)

    # 8️⃣ Explanation
    reasons = explain_score(ndvi, rain_status, est_yield)

    # Crop health
    def crop_health(ndvi):
        if ndvi > 0.6:
            return "Good"
        elif ndvi > 0.4:
            return "Moderate"
        else:
            return "Poor"

    # Soil condition
    def soil_status(soil):
        if soil > 0.6:
            return "Healthy Soil"
        elif soil > 0.3:
            return "Dry Soil"
        else:
            return "Soil Data Unavailable"

    return {
        "score": int(score),
        "decision": decision,
        "loan_limit": int(loan_limit),
        "revenue": int(revenue),
        "estimated_yield": f"{est_yield} tons",
        "ndvi": round(ndvi, 2),
        "crop_health": crop_health(ndvi),
        "weather": rain_status,
        "soil_status": soil_status(soil),
        "reasons": reasons
    }