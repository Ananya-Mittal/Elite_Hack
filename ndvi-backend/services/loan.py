SAFE_LOAN_PERCENT = 0.6


def loan_decision(revenue: int, crop_health: str, weather: str):
    loan_limit = int(revenue * SAFE_LOAN_PERCENT)

    if crop_health == "Good" and weather == "Normal":
        decision = "Approve"
    else:
        decision = "High Risk"

    return loan_limit, decision
