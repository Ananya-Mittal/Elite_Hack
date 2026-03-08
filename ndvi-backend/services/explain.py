def explain_score(ndvi, weather, yield_est):

    reasons = []

    if ndvi > 0.6:
        reasons.append("Healthy crop detected")

    if weather == "Normal":
        reasons.append("Stable rainfall")

    if yield_est > 3:
        reasons.append("High expected yield")

    if yield_est < 2:
        reasons.append("Low yield risk")

    return reasons