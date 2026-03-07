from pydantic import BaseModel
from typing import List

class CreditResponse(BaseModel):

    ndvi: float
    weather: str
    yield_est: float
    revenue: float
    loan_limit: float
    score: float
    decision: str
    reasons: List[str]