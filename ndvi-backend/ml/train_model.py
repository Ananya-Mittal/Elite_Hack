import pandas as pd
import numpy as np
from sklearn.ensemble import RandomForestRegressor
import joblib

# Generate training data
np.random.seed(42)

data_size = 1000

ndvi = np.random.uniform(0.2, 0.9, data_size)
rainfall = np.random.uniform(0, 15, data_size)
soil = np.random.uniform(0.2, 1.0, data_size)
yield_est = np.random.uniform(1, 5, data_size)

score = (
    ndvi * 40 +
    rainfall * 2 +
    soil * 25 +
    yield_est * 6
)

df = pd.DataFrame({
    "ndvi": ndvi,
    "rainfall": rainfall,
    "soil": soil,
    "yield": yield_est,
    "score": score
})

X = df[["ndvi", "rainfall", "soil", "yield"]]
y = df["score"]

model = RandomForestRegressor(n_estimators=200)

model.fit(X, y)

joblib.dump(model, "ml/model.pkl")

print("Model trained and saved.")