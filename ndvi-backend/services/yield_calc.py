crop_prices = {
    "wheat": 22000,
    "rice": 20000
}


def estimate_yield(crop, ndvi):

    base = 2.5

    est = base + (ndvi * 2)

    return round(est, 2)


def estimate_revenue(crop, est_yield):

    price = crop_prices.get(crop, 20000)

    revenue = est_yield * price

    return revenue