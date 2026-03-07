def explain_score(ndvi, weather, soil, est_yield):
    reasons = []

    if ndvi > 0.55:
        reasons.append("Consistent crop health")
    if weather == "Normal":
        reasons.append("Stable rainfall")
    if soil == "Healthy Soil":
        reasons.append("Healthy soil moisture")
    if est_yield > 3:
        reasons.append("Good repayment capacity")

    return reasons
