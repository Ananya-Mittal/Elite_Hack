def calculate_score(ndvi, weather, soil, est_yield):
    score = 0

    if ndvi > 0.55:
        score += 40
    if weather == "Normal":
        score += 30
    if soil == "Healthy Soil":
        score += 20
    if est_yield > 3:
        score += 10

    return score
