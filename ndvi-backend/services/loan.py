def loan_decision(revenue, score):

    loan_limit = revenue * (score / 100) * 0.8

    if score >= 75:
        decision = "Approve"
    elif score >= 50:
        decision = "Manual Review"
    else:
        decision = "Reject"

    return round(loan_limit, 2), decision