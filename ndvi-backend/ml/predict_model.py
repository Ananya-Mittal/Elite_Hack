import joblib
import numpy as np

model = joblib.load("ml/model.pkl")


def predict_score(ndvi, rainfall, soil, yield_est):

    features = np.array([[ndvi, rainfall, soil, yield_est]])

    score = model.predict(features)[0]

    score = max(0, min(100, score))

    return round(score,2)