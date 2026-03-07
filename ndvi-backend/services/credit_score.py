import pandas as pd

def get_credit_score(ndvi, rainfall):

    X = pd.DataFrame([{
        "ndvi": ndvi,
        "rainfall": rainfall
    }])

    prediction = model.predict(X)[0]

    return round(float(prediction),2)